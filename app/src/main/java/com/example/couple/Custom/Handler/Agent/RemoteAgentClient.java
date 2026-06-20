package com.example.couple.Custom.Handler.Agent;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Handler;
import android.os.Looper;
import android.util.Base64;

import com.example.couple.Model.Agent.AgentMessage;
import com.example.couple.Model.Agent.AgentSettings;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.BufferedWriter;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;

public class RemoteAgentClient implements AgentClient {
    private static final int TIMEOUT_MS = 30000;
    private static final int MAX_HISTORY_MESSAGES = 20;
    private static final int RECENT_HISTORY_MESSAGES = 12;
    private static final int RELEVANT_HISTORY_MESSAGES = 8;
    private static final int MAX_IMAGE_SIZE = 1280;
    private static final int IMAGE_JPEG_QUALITY = 82;

    @Override
    public AgentRequest send(Context context, AgentSettings settings, List<AgentMessage> history, AgentMessage userMessage,
                             AgentCallback callback) {
        RemoteAgentRequest request = new RemoteAgentRequest();
        Thread thread = new Thread(() -> {
            try {
                String reply = isGemini(settings)
                        ? sendGemini(context, settings, history, request)
                        : sendOpenAICompatible(context, settings, history, request);
                if (!request.isCancelled()) {
                    postSuccess(callback, new AgentMessage(AgentMessage.Role.ASSISTANT, reply), request);
                }
            } catch (Exception e) {
                if (!request.isCancelled()) {
                    postError(callback, formatError(e.getMessage()), request);
                }
            }
        });
        request.setThread(thread);
        thread.start();
        return request;
    }

    private boolean isGemini(AgentSettings settings) {
        String provider = settings.getProvider() == null ? "" : settings.getProvider().toLowerCase();
        String baseUrl = settings.getBaseUrl() == null ? "" : settings.getBaseUrl().toLowerCase();
        return provider.contains("gemini") || provider.contains("google")
                || baseUrl.contains("generativelanguage.googleapis.com");
    }

    private String sendOpenAICompatible(Context context, AgentSettings settings, List<AgentMessage> history,
                                        RemoteAgentRequest request) throws Exception {
        String baseUrl = trimTrailingSlash(settings.getBaseUrl());
        URL url = new URL(baseUrl + "/chat/completions");

        JSONObject body = new JSONObject();
        body.put("model", settings.getModel());
        body.put("messages", buildOpenAIMessages(context, history));
        body.put("temperature", 0.7);

        JSONObject response = postJson(url, body, settings.getApiKey(), false, request);
        JSONArray choices = response.getJSONArray("choices");
        if (choices.length() == 0) throw new Exception("AI không trả về nội dung.");
        return choices.getJSONObject(0)
                .getJSONObject("message")
                .optString("content", "")
                .trim();
    }

    private JSONArray buildOpenAIMessages(Context context, List<AgentMessage> history) throws Exception {
        JSONArray messages = new JSONArray();
        JSONObject systemMessage = new JSONObject();
        systemMessage.put("role", "system");
        systemMessage.put("content", "Bạn là agent trong app CoupleAndroid. Trả lời ngắn gọn, bằng tiếng Việt, ưu tiên hướng dẫn thao tác trong app.");
        messages.put(systemMessage);

        for (AgentMessage message : selectContextMessages(history)) {
            JSONObject item = new JSONObject();
            item.put("role", message.isUser() ? "user" : "assistant");
            if (message.hasImage()) {
                JSONArray content = new JSONArray();
                JSONObject textPart = new JSONObject();
                textPart.put("type", "text");
                textPart.put("text", message.getContent().isEmpty() ? "Hãy xem ảnh này." : message.getContent());
                content.put(textPart);

                for (int i = 0; i < message.getImageUris().size(); i++) {
                    String dataUrl;
                    try {
                        dataUrl = buildDataUrl(context, message.getImageUris().get(i),
                                message.getImageMimeType(i));
                    } catch (Exception ignored) {
                        continue;
                    }
                    JSONObject imageUrl = new JSONObject();
                    imageUrl.put("url", dataUrl);
                    JSONObject imagePart = new JSONObject();
                    imagePart.put("type", "image_url");
                    imagePart.put("image_url", imageUrl);
                    content.put(imagePart);
                }
                item.put("content", content);
            } else {
                item.put("content", message.getContent());
            }
            messages.put(item);
        }
        return messages;
    }

    private String sendGemini(Context context, AgentSettings settings, List<AgentMessage> history,
                              RemoteAgentRequest request) throws Exception {
        String baseUrl = trimTrailingSlash(settings.getBaseUrl());
        if (baseUrl.endsWith("/v1") || baseUrl.endsWith("/v1beta")) {
            baseUrl = trimTrailingSlash(baseUrl);
        } else {
            baseUrl = baseUrl + "/v1beta";
        }

        URL url = new URL(baseUrl + "/models/" + settings.getModel()
                + ":generateContent?key=" + settings.getApiKey());

        JSONObject body = new JSONObject();
        body.put("contents", buildGeminiContents(context, history));

        JSONObject response = postJson(url, body, null, true, request);
        JSONArray candidates = response.getJSONArray("candidates");
        if (candidates.length() == 0) throw new Exception("Gemini không trả về nội dung.");
        JSONArray parts = candidates.getJSONObject(0)
                .getJSONObject("content")
                .getJSONArray("parts");
        if (parts.length() == 0) throw new Exception("Gemini trả về nội dung rỗng.");
        return parts.getJSONObject(0).optString("text", "").trim();
    }

    private JSONArray buildGeminiContents(Context context, List<AgentMessage> history) throws Exception {
        JSONArray contents = new JSONArray();
        for (AgentMessage message : selectContextMessages(history)) {
            JSONObject content = new JSONObject();
            content.put("role", message.isUser() ? "user" : "model");

            JSONArray parts = new JSONArray();
            JSONObject part = new JSONObject();
            part.put("text", message.getContent().isEmpty() && message.hasImage()
                    ? "Hãy xem ảnh này." : message.getContent());
            parts.put(part);

            for (int i = 0; i < message.getImageUris().size(); i++) {
                String imageData;
                try {
                    imageData = readImageBase64(context, message.getImageUris().get(i));
                } catch (Exception ignored) {
                    continue;
                }
                JSONObject inlineData = new JSONObject();
                inlineData.put("mimeType", getImageMimeType(context, message.getImageUris().get(i),
                        message.getImageMimeType(i)));
                inlineData.put("data", imageData);

                JSONObject imagePart = new JSONObject();
                imagePart.put("inlineData", inlineData);
                parts.put(imagePart);
            }

            content.put("parts", parts);
            contents.put(content);
        }
        return contents;
    }

    private String buildDataUrl(Context context, String imageUri, String imageMimeType) throws Exception {
        return "data:" + getImageMimeType(context, imageUri, imageMimeType)
                + ";base64," + readImageBase64(context, imageUri);
    }

    private String getImageMimeType(Context context, String imageUri, String imageMimeType) {
        if (imageMimeType != null && !imageMimeType.trim().isEmpty()) {
            return imageMimeType;
        }
        try {
            String mimeType = context.getContentResolver().getType(Uri.parse(imageUri));
            if (mimeType != null && !mimeType.trim().isEmpty()) return mimeType;
        } catch (Exception ignored) {
        }
        return "image/jpeg";
    }

    private String readImageBase64(Context context, String imageUri) throws Exception {
        byte[] compressedImage = compressImage(context, imageUri);
        if (compressedImage.length > 0) {
            return Base64.encodeToString(compressedImage, Base64.NO_WRAP);
        }

        InputStream inputStream = context.getContentResolver().openInputStream(Uri.parse(imageUri));
        if (inputStream == null) throw new Exception("Không đọc được ảnh đã chọn.");

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        byte[] buffer = new byte[8192];
        int read;
        try {
            while ((read = inputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, read);
            }
        } finally {
            inputStream.close();
        }
        return Base64.encodeToString(outputStream.toByteArray(), Base64.NO_WRAP);
    }

    private byte[] compressImage(Context context, String imageUri) {
        try {
            Uri uri = Uri.parse(imageUri);
            BitmapFactory.Options boundsOptions = new BitmapFactory.Options();
            boundsOptions.inJustDecodeBounds = true;
            InputStream boundsStream = context.getContentResolver().openInputStream(uri);
            if (boundsStream == null) return new byte[0];
            BitmapFactory.decodeStream(boundsStream, null, boundsOptions);
            boundsStream.close();

            BitmapFactory.Options decodeOptions = new BitmapFactory.Options();
            decodeOptions.inSampleSize = calculateInSampleSize(boundsOptions, MAX_IMAGE_SIZE, MAX_IMAGE_SIZE);
            InputStream imageStream = context.getContentResolver().openInputStream(uri);
            if (imageStream == null) return new byte[0];
            Bitmap bitmap = BitmapFactory.decodeStream(imageStream, null, decodeOptions);
            imageStream.close();
            if (bitmap == null) return new byte[0];

            Bitmap scaledBitmap = scaleBitmap(bitmap, MAX_IMAGE_SIZE);
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            scaledBitmap.compress(Bitmap.CompressFormat.JPEG, IMAGE_JPEG_QUALITY, outputStream);
            if (scaledBitmap != bitmap) scaledBitmap.recycle();
            bitmap.recycle();
            return outputStream.toByteArray();
        } catch (Exception ignored) {
            return new byte[0];
        }
    }

    private int calculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight) {
        int height = options.outHeight;
        int width = options.outWidth;
        int inSampleSize = 1;

        while ((height / inSampleSize) > reqHeight || (width / inSampleSize) > reqWidth) {
            inSampleSize *= 2;
        }
        return inSampleSize;
    }

    private Bitmap scaleBitmap(Bitmap bitmap, int maxSize) {
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        int longestSide = Math.max(width, height);
        if (longestSide <= maxSize) return bitmap;

        float ratio = maxSize / (float) longestSide;
        return Bitmap.createScaledBitmap(bitmap,
                Math.round(width * ratio),
                Math.round(height * ratio),
                true);
    }

    private List<AgentMessage> selectContextMessages(List<AgentMessage> history) {
        if (history == null || history.isEmpty()) return new ArrayList<>();

        AgentMessage latestMessage = history.get(history.size() - 1);
        Set<String> queryTokens = tokenize(latestMessage.getContent());
        boolean latestMentionsImage = mentionsImage(latestMessage.getContent());
        boolean latestHasImage = latestMessage.hasImage();
        if (history.size() <= MAX_HISTORY_MESSAGES) {
            return buildSelectedMessages(history, createAllIndexes(history.size()), latestMessage,
                    queryTokens, latestMentionsImage, latestHasImage);
        }

        int recentStart = Math.max(0, history.size() - RECENT_HISTORY_MESSAGES);
        Set<Integer> selectedIndexes = new HashSet<>();

        for (int i = recentStart; i < history.size(); i++) {
            selectedIndexes.add(i);
        }

        List<ScoredMessage> scoredMessages = new ArrayList<>();
        for (int i = 0; i < recentStart; i++) {
            int score = scoreMessage(history.get(i), queryTokens, latestMessage.getContent());
            if (score > 0) scoredMessages.add(new ScoredMessage(i, score));
        }

        Collections.sort(scoredMessages, (first, second) -> {
            if (first.score != second.score) return second.score - first.score;
            return second.index - first.index;
        });

        for (int i = 0; i < scoredMessages.size()
                && selectedIndexes.size() < MAX_HISTORY_MESSAGES
                && i < RELEVANT_HISTORY_MESSAGES; i++) {
            selectedIndexes.add(scoredMessages.get(i).index);
        }

        List<Integer> orderedIndexes = new ArrayList<>(selectedIndexes);
        Collections.sort(orderedIndexes);

        List<AgentMessage> selectedMessages = new ArrayList<>();
        for (Integer index : orderedIndexes) {
            selectedMessages.add(prepareContextMessage(history.get(index), latestMessage,
                    index == history.size() - 1, queryTokens, latestMentionsImage, latestHasImage));
        }
        return selectedMessages;
    }

    private List<Integer> createAllIndexes(int size) {
        List<Integer> indexes = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            indexes.add(i);
        }
        return indexes;
    }

    private List<AgentMessage> buildSelectedMessages(List<AgentMessage> history, List<Integer> indexes,
                                                     AgentMessage latestMessage, Set<String> queryTokens,
                                                     boolean latestMentionsImage, boolean latestHasImage) {
        List<AgentMessage> selectedMessages = new ArrayList<>();
        for (Integer index : indexes) {
            selectedMessages.add(prepareContextMessage(history.get(index), latestMessage,
                    index == history.size() - 1, queryTokens, latestMentionsImage, latestHasImage));
        }
        return selectedMessages;
    }

    private AgentMessage prepareContextMessage(AgentMessage message, AgentMessage latestMessage,
                                               boolean isLatestMessage, Set<String> queryTokens,
                                               boolean latestMentionsImage, boolean latestHasImage) {
        if (!message.hasImage()) return message;
        if (shouldSendImages(message, latestMessage, isLatestMessage, queryTokens,
                latestMentionsImage, latestHasImage)) {
            return message;
        }

        return new AgentMessage(message.getRole(), message.getContent(), message.getModelName());
    }

    private boolean shouldSendImages(AgentMessage message, AgentMessage latestMessage,
                                     boolean isLatestMessage, Set<String> queryTokens,
                                     boolean latestMentionsImage, boolean latestHasImage) {
        if (isLatestMessage) return true;
        if (latestHasImage) return false;
        if (latestMentionsImage) return true;
        return scoreMessage(message, queryTokens, latestMessage.getContent()) > 0;
    }

    private boolean mentionsImage(String content) {
        if (content == null) return false;
        String normalizedContent = content.toLowerCase(Locale.US);
        return normalizedContent.contains("ảnh")
                || normalizedContent.contains("hình")
                || normalizedContent.contains("image")
                || normalizedContent.contains("photo")
                || normalizedContent.contains("picture");
    }

    private int scoreMessage(AgentMessage message, Set<String> queryTokens, String latestContent) {
        if (message == null || message.getContent() == null || queryTokens.isEmpty()) return 0;

        String content = message.getContent().toLowerCase(Locale.US);
        String normalizedLatest = latestContent == null ? "" : latestContent.trim().toLowerCase(Locale.US);
        int score = 0;

        if (normalizedLatest.length() >= 8 && content.contains(normalizedLatest)) score += 5;

        for (String token : queryTokens) {
            if (!content.contains(token)) continue;

            if (isNumberToken(token)) {
                score += 5;
            } else if (token.length() <= 3) {
                score += 1;
            } else {
                score += 2;
            }
        }

        return score;
    }

    private Set<String> tokenize(String value) {
        Set<String> tokens = new HashSet<>();
        if (value == null || value.trim().isEmpty()) return tokens;

        String normalized = value.toLowerCase(Locale.US)
                .replaceAll("[^0-9a-zA-ZÀ-ỹ]+", " ")
                .trim();
        if (normalized.isEmpty()) return tokens;

        String[] parts = normalized.split("\\s+");
        for (String part : parts) {
            if (part.length() >= 2) tokens.add(part);
        }
        return tokens;
    }

    private boolean isNumberToken(String token) {
        return token != null && token.matches("\\d+");
    }

    private static class ScoredMessage {
        int index;
        int score;

        ScoredMessage(int index, int score) {
            this.index = index;
            this.score = score;
        }
    }

    private JSONObject postJson(URL url, JSONObject body, String apiKey, boolean skipBearer,
                                RemoteAgentRequest request) throws Exception {
        HttpURLConnection connection = null;
        try {
            if (request.isCancelled()) throw new Exception("Request đã bị hủy.");
            connection = (HttpURLConnection) url.openConnection();
            request.setConnection(connection);
            connection.setRequestMethod("POST");
            connection.setConnectTimeout(TIMEOUT_MS);
            connection.setReadTimeout(TIMEOUT_MS);
            connection.setDoOutput(true);
            connection.setRequestProperty("Content-Type", "application/json");
            if (apiKey != null && !apiKey.isEmpty() && !skipBearer) {
                connection.setRequestProperty("Authorization", "Bearer " + apiKey);
            }

            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(
                    connection.getOutputStream(), StandardCharsets.UTF_8));
            writer.write(body.toString());
            writer.flush();
            writer.close();

            if (request.isCancelled()) throw new Exception("Request đã bị hủy.");
            int status = connection.getResponseCode();
            InputStream stream = status >= 200 && status < 300
                    ? connection.getInputStream()
                    : connection.getErrorStream();
            String response = readStream(stream, request);

            if (status < 200 || status >= 300) {
                throw new Exception("HTTP " + status + ": " + response);
            }
            return new JSONObject(response);
        } finally {
            if (connection != null) connection.disconnect();
            request.clearConnection(connection);
        }
    }

    private String readStream(InputStream stream, RemoteAgentRequest request) throws Exception {
        if (stream == null) return "";
        BufferedReader reader = new BufferedReader(new InputStreamReader(stream, StandardCharsets.UTF_8));
        StringBuilder builder = new StringBuilder();
        String line;
        try {
            while ((line = reader.readLine()) != null) {
                if (request.isCancelled()) throw new Exception("Request đã bị hủy.");
                builder.append(line);
            }
        } finally {
            reader.close();
        }
        return builder.toString();
    }

    private String trimTrailingSlash(String value) {
        String safeValue = value == null || value.trim().isEmpty()
                ? AgentSettingsHandler.DEFAULT_BASE_URL : value.trim();
        while (safeValue.endsWith("/")) {
            safeValue = safeValue.substring(0, safeValue.length() - 1);
        }
        return safeValue;
    }

    private String formatError(String message) {
        String parsedMessage = extractProviderErrorMessage(message);
        String safeMessage = message == null ? "" : message;
        String lowerMessage = parsedMessage.toLowerCase();
        if (safeMessage.contains("HTTP 503")
                || lowerMessage.contains("unavailable")
                || lowerMessage.contains("high demand")) {
            return "Model đang quá tải hoặc tạm thời không sẵn sàng. Bạn thử lại sau, hoặc chọn model khác trong Chat bot.";
        }
        return "Lỗi gọi AI: " + parsedMessage;
    }

    private String extractProviderErrorMessage(String message) {
        if (message == null || message.trim().isEmpty()) return "Không rõ lỗi.";
        int jsonStart = message.indexOf("{");
        if (jsonStart < 0) return message;

        try {
            JSONObject jsonObject = new JSONObject(message.substring(jsonStart));
            JSONObject error = jsonObject.optJSONObject("error");
            if (error != null) {
                String providerMessage = error.optString("message", "");
                if (!providerMessage.isEmpty()) return providerMessage;
            }
        } catch (Exception ignored) {
        }
        return message;
    }

    private void postSuccess(AgentCallback callback, AgentMessage message, AgentRequest request) {
        new Handler(Looper.getMainLooper()).post(() -> {
            if (!request.isCancelled()) callback.onSuccess(message);
        });
    }

    private void postError(AgentCallback callback, String errorMessage, AgentRequest request) {
        new Handler(Looper.getMainLooper()).post(() -> {
            if (!request.isCancelled()) callback.onError(errorMessage);
        });
    }

    private static class RemoteAgentRequest implements AgentRequest {
        private boolean cancelled = false;
        private HttpURLConnection connection;
        private Thread thread;

        synchronized void setConnection(HttpURLConnection connection) {
            this.connection = connection;
            if (cancelled && this.connection != null) this.connection.disconnect();
        }

        synchronized void clearConnection(HttpURLConnection connection) {
            if (this.connection == connection) this.connection = null;
        }

        synchronized void setThread(Thread thread) {
            this.thread = thread;
        }

        @Override
        public synchronized void cancel() {
            cancelled = true;
            if (connection != null) connection.disconnect();
            if (thread != null) thread.interrupt();
        }

        @Override
        public synchronized boolean isCancelled() {
            return cancelled;
        }
    }
}
