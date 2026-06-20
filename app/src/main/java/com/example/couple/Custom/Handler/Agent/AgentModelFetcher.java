package com.example.couple.Custom.Handler.Agent;

import android.os.Handler;
import android.os.Looper;

import com.example.couple.Model.Agent.AgentSettings;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class AgentModelFetcher {
    private static final int TIMEOUT_MS = 30000;

    public static void fetch(AgentSettings settings, AgentModelCallback callback) {
        new Thread(() -> {
            try {
                List<String> models = isGemini(settings)
                        ? fetchGeminiModels(settings)
                        : fetchOpenAICompatibleModels(settings);
                Collections.sort(models);
                postSuccess(callback, models);
            } catch (Exception e) {
                postError(callback, "Lỗi tải model: " + e.getMessage());
            }
        }).start();
    }

    private static boolean isGemini(AgentSettings settings) {
        String provider = settings.getProvider() == null ? "" : settings.getProvider().toLowerCase();
        String baseUrl = settings.getBaseUrl() == null ? "" : settings.getBaseUrl().toLowerCase();
        return provider.contains("gemini") || provider.contains("google")
                || baseUrl.contains("generativelanguage.googleapis.com");
    }

    private static List<String> fetchOpenAICompatibleModels(AgentSettings settings) throws Exception {
        String baseUrl = trimTrailingSlash(settings.getBaseUrl());
        JSONObject response = getJson(new URL(baseUrl + "/models"), settings.getApiKey(), false);

        List<String> models = new ArrayList<>();
        JSONArray data = response.getJSONArray("data");
        for (int i = 0; i < data.length(); i++) {
            String id = data.getJSONObject(i).optString("id", "");
            if (!id.isEmpty()) models.add(id);
        }
        return models;
    }

    private static List<String> fetchGeminiModels(AgentSettings settings) throws Exception {
        String baseUrl = trimTrailingSlash(settings.getBaseUrl());
        if (!baseUrl.endsWith("/v1") && !baseUrl.endsWith("/v1beta")) {
            baseUrl = baseUrl + "/v1beta";
        }

        JSONObject response = getJson(new URL(baseUrl + "/models?key=" + settings.getApiKey()),
                null, true);

        List<String> models = new ArrayList<>();
        JSONArray data = response.getJSONArray("models");
        for (int i = 0; i < data.length(); i++) {
            JSONObject item = data.getJSONObject(i);
            if (!supportsGenerateContent(item)) continue;

            String name = item.optString("name", "");
            if (name.startsWith("models/")) name = name.substring("models/".length());
            if (!name.isEmpty()) models.add(name);
        }
        return models;
    }

    private static boolean supportsGenerateContent(JSONObject item) {
        JSONArray methods = item.optJSONArray("supportedGenerationMethods");
        if (methods == null) return true;

        for (int i = 0; i < methods.length(); i++) {
            if ("generateContent".equals(methods.optString(i))) return true;
        }
        return false;
    }

    private static JSONObject getJson(URL url, String apiKey, boolean skipBearer) throws Exception {
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        connection.setConnectTimeout(TIMEOUT_MS);
        connection.setReadTimeout(TIMEOUT_MS);
        connection.setRequestProperty("Content-Type", "application/json");
        if (apiKey != null && !apiKey.isEmpty() && !skipBearer) {
            connection.setRequestProperty("Authorization", "Bearer " + apiKey);
        }

        int status = connection.getResponseCode();
        InputStream stream = status >= 200 && status < 300
                ? connection.getInputStream()
                : connection.getErrorStream();
        String response = readStream(stream);
        connection.disconnect();

        if (status < 200 || status >= 300) {
            throw new Exception("HTTP " + status + ": " + response);
        }
        return new JSONObject(response);
    }

    private static String readStream(InputStream stream) throws Exception {
        if (stream == null) return "";
        BufferedReader reader = new BufferedReader(new InputStreamReader(stream, StandardCharsets.UTF_8));
        StringBuilder builder = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            builder.append(line);
        }
        reader.close();
        return builder.toString();
    }

    private static String trimTrailingSlash(String value) {
        String safeValue = value == null || value.trim().isEmpty()
                ? AgentSettingsHandler.DEFAULT_BASE_URL : value.trim();
        while (safeValue.endsWith("/")) {
            safeValue = safeValue.substring(0, safeValue.length() - 1);
        }
        return safeValue;
    }

    private static void postSuccess(AgentModelCallback callback, List<String> models) {
        new Handler(Looper.getMainLooper()).post(() -> callback.onSuccess(models));
    }

    private static void postError(AgentModelCallback callback, String errorMessage) {
        new Handler(Looper.getMainLooper()).post(() -> callback.onError(errorMessage));
    }
}
