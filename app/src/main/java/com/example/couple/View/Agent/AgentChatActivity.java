package com.example.couple.View.Agent;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.ClipData;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.StyleSpan;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.couple.Base.View.ActivityBase;
import com.example.couple.Base.View.WidgetBase;
import com.example.couple.Custom.Handler.Agent.AgentCallback;
import com.example.couple.Custom.Handler.Agent.AgentChatHistoryHandler;
import com.example.couple.Custom.Handler.Agent.AgentClient;
import com.example.couple.Custom.Handler.Agent.AgentRequest;
import com.example.couple.Custom.Handler.Agent.AgentSettingsHandler;
import com.example.couple.Custom.Handler.Agent.MockAgentClient;
import com.example.couple.Custom.Handler.Agent.RemoteAgentClient;
import com.example.couple.Model.Agent.AgentChatSession;
import com.example.couple.Model.Agent.AgentMessage;
import com.example.couple.Model.Agent.AgentSettings;
import com.example.couple.R;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.io.InputStream;

public class AgentChatActivity extends ActivityBase {
    public static final String EXTRA_SESSION_ID = "EXTRA_SESSION_ID";
    private static final int REQUEST_MANAGE_MODEL = 2401;
    private static final int REQUEST_PICK_IMAGE = 2402;
    private static final int MODEL_LABEL_MAX_LENGTH = 24;

    LinearLayout linearMessages;
    ScrollView scrollMessages;
    EditText edtMessage;
    View scrollSelectedImages;
    LinearLayout linearSelectedImages;
    ImageButton btnPickImage;
    ImageButton btnSend;
    Button btnSelectModel;
    AgentClient agentClient;
    AgentChatSession currentSession;
    List<AgentMessage> messages = new ArrayList<>();
    boolean isWaitingResponse = false;
    int activeRequestId = 0;
    AgentRequest activeRequest;
    List<String> selectedImageUris = new ArrayList<>();
    List<String> selectedImageMimeTypes = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agent_chat);

        linearMessages = findViewById(R.id.linearMessages);
        scrollMessages = findViewById(R.id.scrollMessages);
        edtMessage = findViewById(R.id.edtMessage);
        scrollSelectedImages = findViewById(R.id.scrollSelectedImages);
        linearSelectedImages = findViewById(R.id.linearSelectedImages);
        btnPickImage = findViewById(R.id.btnPickImage);
        btnSend = findViewById(R.id.btnSend);
        btnSelectModel = findViewById(R.id.btnSelectModel);
        bindCurrentSession();
        bindAgentClient();
        bindSelectedModelLabel();

        bindEvents();
        setWaitingResponse(false);
    }

    @Override
    protected void onResume() {
        super.onResume();
        bindAgentClient();
        bindSelectedModelLabel();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != RESULT_OK || data == null) return;

        if (requestCode == REQUEST_PICK_IMAGE) {
            handlePickedImage(data);
            return;
        }

        if (requestCode != REQUEST_MANAGE_MODEL) return;

        String profileId = data.getStringExtra(AgentModelManageActivity.EXTRA_SELECTED_PROFILE_ID);
        if (profileId == null || profileId.trim().isEmpty()) return;
        setCurrentSessionProfile(profileId);
    }

    private void handlePickedImage(Intent data) {
        if (data == null) return;

        ClipData clipData = data.getClipData();
        if (clipData != null) {
            for (int i = 0; i < clipData.getItemCount(); i++) {
                addSelectedImage(clipData.getItemAt(i).getUri());
            }
        } else if (data.getData() != null) {
            addSelectedImage(data.getData());
        }
        renderSelectedImages();
    }

    private void addSelectedImage(Uri uri) {
        if (uri == null) return;
        try {
            getContentResolver().takePersistableUriPermission(uri,
                    Intent.FLAG_GRANT_READ_URI_PERMISSION);
        } catch (Exception ignored) {
        }
        selectedImageUris.add(uri.toString());
        selectedImageMimeTypes.add(getContentResolver().getType(uri));
    }

    private void renderSelectedImages() {
        linearSelectedImages.removeAllViews();
        boolean hasImages = !selectedImageUris.isEmpty();
        scrollSelectedImages.setVisibility(hasImages ? View.VISIBLE : View.GONE);
        btnPickImage.setImageResource(R.drawable.ic_add);

        for (int i = 0; i < selectedImageUris.size(); i++) {
            linearSelectedImages.addView(createSelectedImageView(i));
        }
    }

    private View createSelectedImageView(int index) {
        android.widget.FrameLayout frameLayout = new android.widget.FrameLayout(this);

        ImageView imageView = new ImageView(this);
        imageView.setImageURI(Uri.parse(selectedImageUris.get(index)));
        imageView.setBackgroundResource(R.drawable.bg_search_box);
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        frameLayout.addView(imageView, new android.widget.FrameLayout.LayoutParams(dp(72), dp(72)));

        ImageButton removeButton = new ImageButton(this);
        removeButton.setImageResource(R.drawable.ic_close);
        removeButton.setBackgroundResource(R.drawable.bg_action_pill_primary);
        removeButton.setContentDescription("Bỏ ảnh");
        removeButton.setPadding(dp(5), dp(5), dp(5), dp(5));
        removeButton.setOnClickListener(view -> {
            selectedImageUris.remove(index);
            if (index < selectedImageMimeTypes.size()) selectedImageMimeTypes.remove(index);
            renderSelectedImages();
        });
        android.widget.FrameLayout.LayoutParams removeParams =
                new android.widget.FrameLayout.LayoutParams(dp(26), dp(26), Gravity.RIGHT | Gravity.TOP);
        frameLayout.addView(removeButton, removeParams);

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(dp(72), dp(72));
        params.setMargins(0, 0, dp(6), 0);
        frameLayout.setLayoutParams(params);
        return frameLayout;
    }

    private void bindEvents() {
        btnSend.setOnClickListener(view -> {
            if (isWaitingResponse) {
                stopAgentResponse();
            } else {
                sendMessage();
            }
        });
        btnSelectModel.setOnClickListener(view -> showModelProfilePicker());
        btnPickImage.setOnClickListener(view -> openImagePicker());
    }

    private void bindAgentClient() {
        agentClient = getCurrentSettings().hasApiKey()
                ? new RemoteAgentClient()
                : new MockAgentClient();
    }

    private void bindSelectedModelLabel() {
        btnSelectModel.setText(shortModelName(getCurrentSettings()));
    }

    private AgentSettings getCurrentSettings() {
        if (currentSession == null) return AgentSettingsHandler.getSettings(this);

        AgentSettings settings = AgentSettingsHandler.getSettings(this, currentSession.getActiveProfileId());
        if (!settings.getId().equals(currentSession.getActiveProfileId())) {
            currentSession.setActiveProfileId(settings.getId());
            if (!messages.isEmpty()) AgentChatHistoryHandler.saveSession(this, currentSession);
        }
        return settings;
    }

    private void setCurrentSessionProfile(String profileId) {
        currentSession.setActiveProfileId(profileId);
        if (!messages.isEmpty()) AgentChatHistoryHandler.saveSession(this, currentSession);
        bindAgentClient();
        bindSelectedModelLabel();
    }

    private void bindCurrentSession() {
        String sessionId = getIntent().getStringExtra(EXTRA_SESSION_ID);
        currentSession = AgentChatHistoryHandler.getSession(this, sessionId);
        if (currentSession == null) {
            currentSession = AgentChatHistoryHandler.createDraftSession();
        }
        if (currentSession.getActiveProfileId().trim().isEmpty()) {
            currentSession.setActiveProfileId(AgentSettingsHandler.getSettings(this).getId());
        }

        messages = new ArrayList<>(currentSession.getMessages());
        renderMessages();
    }

    private void showModelProfilePicker() {
        List<AgentSettings> profiles = AgentSettingsHandler.getProfiles(this);
        if (profiles.isEmpty()) {
            Toast.makeText(this, "Bạn chưa lưu model nào. Vào Quản lý model để thêm API key và model.", Toast.LENGTH_LONG).show();
            openModelManageFromChat();
            return;
        }

        List<ModelPickerRow> rows = createModelPickerRows(profiles);
        ListView listView = new ListView(this);
        listView.setAdapter(new ModelPickerAdapter(rows));

        AlertDialog dialog = new AlertDialog.Builder(this)
                .setTitle("Chọn model")
                .setView(listView)
                .setPositiveButton("Quản lý model", (dialogInterface, which) ->
                        openModelManageFromChat())
                .create();

        listView.setOnItemClickListener((parent, view, position, id) -> {
            ModelPickerRow row = rows.get(position);
            if (row.profile == null) return;

            setCurrentSessionProfile(row.profile.getId());
            Toast.makeText(this, "Đã chọn model cho chat: " + row.profile.showName(), Toast.LENGTH_SHORT).show();
            dialog.dismiss();
        });
        dialog.show();
    }

    private void openModelManageFromChat() {
        Intent intent = new Intent(this, AgentModelManageActivity.class);
        intent.putExtra(AgentModelManageActivity.EXTRA_FROM_CHAT, true);
        intent.putExtra(AgentModelManageActivity.EXTRA_ACTIVE_PROFILE_ID, currentSession.getActiveProfileId());
        startActivityForResult(intent, REQUEST_MANAGE_MODEL);
    }

    private void openImagePicker() {
        Intent intent;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            intent = new Intent(MediaStore.ACTION_PICK_IMAGES);
            intent.setType("image/*");
            intent.putExtra(MediaStore.EXTRA_PICK_IMAGES_MAX, 20);
        } else {
            intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
            intent.addCategory(Intent.CATEGORY_OPENABLE);
            intent.setType("image/*");
            intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
            intent.addFlags(Intent.FLAG_GRANT_PERSISTABLE_URI_PERMISSION);
        }
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        startActivityForResult(intent, REQUEST_PICK_IMAGE);
    }

    private List<ModelPickerRow> createModelPickerRows(List<AgentSettings> profiles) {
        List<AgentSettings> sortedProfiles = new ArrayList<>(profiles);
        Collections.sort(sortedProfiles, new Comparator<AgentSettings>() {
            @Override
            public int compare(AgentSettings first, AgentSettings second) {
                int groupCompare = getModelGroupOrder(first) - getModelGroupOrder(second);
                if (groupCompare != 0) return groupCompare;
                return first.showName().compareToIgnoreCase(second.showName());
            }
        });

        List<ModelPickerRow> rows = new ArrayList<>();
        String currentGroup = "";
        for (AgentSettings profile : sortedProfiles) {
            String group = getModelGroup(profile);
            if (!group.equals(currentGroup)) {
                currentGroup = group;
                rows.add(new ModelPickerRow(group, null));
            }
            rows.add(new ModelPickerRow(profile.showName(), profile,
                    profile.getId().equals(currentSession.getActiveProfileId())));
        }
        return rows;
    }

    private void sendMessage() {
        if (isWaitingResponse) return;

        String message = edtMessage.getText().toString().trim();
        if (message.isEmpty() && selectedImageUris.isEmpty()) return;

        edtMessage.setText("");
        AgentMessage userMessage = new AgentMessage(AgentMessage.Role.USER, message, "",
                new ArrayList<>(selectedImageUris), new ArrayList<>(selectedImageMimeTypes));
        clearSelectedImages();
        addMessage(userMessage);
        requestAgentReply();
    }

    private void clearSelectedImages() {
        selectedImageUris.clear();
        selectedImageMimeTypes.clear();
        renderSelectedImages();
    }

    private void requestAgentReply() {
        AgentSettings requestSettings = getCurrentSettings();
        String requestModelName = requestSettings.showName();
        int requestId = ++activeRequestId;
        long startedAt = System.currentTimeMillis();
        setWaitingResponse(true);
        activeRequest = agentClient.send(this, requestSettings, new ArrayList<>(messages), messages.get(messages.size() - 1), new AgentCallback() {
            @Override
            public void onSuccess(AgentMessage message) {
                if (requestId != activeRequestId || !isWaitingResponse) return;
                activeRequest = null;
                addMessage(new AgentMessage(AgentMessage.Role.ASSISTANT,
                        message.getContent(), requestModelName, getElapsedTime(startedAt)));
                setWaitingResponse(false);
            }

            @Override
            public void onError(String errorMessage) {
                if (requestId != activeRequestId || !isWaitingResponse) return;
                activeRequest = null;
                addMessage(new AgentMessage(AgentMessage.Role.ASSISTANT,
                        errorMessage == null || errorMessage.isEmpty()
                                ? "Mình chưa trả lời được lúc này." : errorMessage,
                        requestModelName,
                        getElapsedTime(startedAt)));
                setWaitingResponse(false);
            }
        });
    }

    private long getElapsedTime(long startedAt) {
        return Math.max(0, System.currentTimeMillis() - startedAt);
    }

    private void stopAgentResponse() {
        activeRequestId++;
        if (activeRequest != null) {
            activeRequest.cancel();
            activeRequest = null;
        }
        setWaitingResponse(false);
        Toast.makeText(this, "Đã dừng lượt trả lời.", Toast.LENGTH_SHORT).show();
    }

    private void addMessage(AgentMessage message) {
        messages.add(message);
        updateCurrentSession(message);
        renderMessage(message);
    }

    private void renderMessages() {
        linearMessages.removeAllViews();
        for (AgentMessage message : messages) {
            renderMessage(message);
        }
        scrollMessages.post(() -> scrollMessages.fullScroll(ScrollView.FOCUS_DOWN));
    }

    private void renderMessage(AgentMessage message) {
        boolean isUser = message.isUser();
        LinearLayout messageGroup = new LinearLayout(this);
        messageGroup.setOrientation(LinearLayout.VERTICAL);

        TextView tvMessage = new TextView(this);
        tvMessage.setText(isUser ? getDisplayUserContent(message) : formatAssistantMessage(message.getContent()));
        tvMessage.setTextSize(15);
        tvMessage.setLineSpacing(3, 1);
        tvMessage.setTextColor(getResources().getColor(isUser ? R.color.colorThemeLight : R.color.colorText));
        tvMessage.setBackgroundResource(isUser ? R.drawable.bg_chat_user_message : R.drawable.bg_chat_bot_message);
        int padding = getResources().getDimensionPixelSize(R.dimen.padding_10dp);
        tvMessage.setPadding(padding, padding, padding, padding);
        messageGroup.addView(tvMessage, new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
        View.OnLongClickListener copyListener = view -> {
            showMessageMenu(tvMessage, message);
            return true;
        };
        messageGroup.setOnLongClickListener(copyListener);
        tvMessage.setOnLongClickListener(copyListener);

        if (message.hasImage()) {
            LinearLayout imageList = new LinearLayout(this);
            imageList.setOrientation(LinearLayout.VERTICAL);
            for (String imageUri : message.getImageUris()) {
                if (!canReadImage(imageUri)) {
                    LinearLayout.LayoutParams missingParams = new LinearLayout.LayoutParams(
                            LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                    missingParams.setMargins(0, 0, 0, dp(4));
                    imageList.addView(createMissingImageText(), missingParams);
                    continue;
                }

                ImageView imageView = new ImageView(this);
                imageView.setImageURI(Uri.parse(imageUri));
                imageView.setAdjustViewBounds(true);
                imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
                imageView.setOnClickListener(view -> showImageDialog(imageUri));
                LinearLayout.LayoutParams imageParams = new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT, dp(180));
                imageParams.setMargins(0, 0, 0, dp(4));
                imageList.addView(imageView, imageParams);
            }
            messageGroup.addView(imageList, 0);
        }

        String assistantMeta = buildAssistantMeta(message);
        if (!isUser && !assistantMeta.isEmpty()) {
            TextView tvModel = new TextView(this);
            tvModel.setText(assistantMeta);
            tvModel.setTextSize(11);
            tvModel.setTextColor(getResources().getColor(R.color.colorTextNavBar));
            tvModel.setPadding(padding, 1, padding, 0);
            messageGroup.addView(tvModel, new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
        }

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                (int) (getResources().getDisplayMetrics().widthPixels * 0.78f),
                LinearLayout.LayoutParams.WRAP_CONTENT);
        params.setMargins(0, 0, 0, getResources().getDimensionPixelSize(R.dimen.margin_5dp));
        params.gravity = isUser ? Gravity.RIGHT : Gravity.LEFT;

        linearMessages.addView(messageGroup, params);
        scrollMessages.post(() -> scrollMessages.fullScroll(ScrollView.FOCUS_DOWN));
    }

    private String buildAssistantMeta(AgentMessage message) {
        String modelName = message.getModelName() == null ? "" : message.getModelName().trim();
        String responseTime = formatResponseTime(message.getResponseTimeMs());
        if (modelName.isEmpty()) return responseTime;
        if (responseTime.isEmpty()) return modelName;
        return modelName + " - " + responseTime;
    }

    private String formatResponseTime(long responseTimeMs) {
        if (responseTimeMs <= 0) return "";
        if (responseTimeMs < 1000) return responseTimeMs + "ms";
        long totalSeconds = Math.round(responseTimeMs / 1000.0);
        if (totalSeconds >= 60) {
            return (totalSeconds / 60) + "m" + (totalSeconds % 60) + "s";
        }
        double seconds = responseTimeMs / 1000.0;
        if (seconds < 10) return String.format(java.util.Locale.US, "%.1fs", seconds);
        return totalSeconds + "s";
    }

    private void showImageDialog(String imageUri) {
        if (!canReadImage(imageUri)) {
            Toast.makeText(this, "Ảnh trong máy đã bị xóa hoặc không còn quyền truy cập.", Toast.LENGTH_SHORT).show();
            return;
        }

        Dialog dialog = new Dialog(this);
        dialog.getWindow();

        android.widget.FrameLayout container = new android.widget.FrameLayout(this);
        container.setBackgroundColor(Color.BLACK);
        container.setPadding(dp(8), dp(8), dp(8), dp(8));

        ImageView imageView = new ImageView(this);
        imageView.setImageURI(Uri.parse(imageUri));
        imageView.setAdjustViewBounds(true);
        imageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
        container.addView(imageView, new android.widget.FrameLayout.LayoutParams(
                android.widget.FrameLayout.LayoutParams.MATCH_PARENT,
                android.widget.FrameLayout.LayoutParams.MATCH_PARENT));

        ImageButton closeButton = new ImageButton(this);
        closeButton.setImageResource(R.drawable.ic_close);
        closeButton.setBackgroundResource(R.drawable.bg_action_pill_primary);
        closeButton.setContentDescription("Đóng");
        closeButton.setPadding(dp(7), dp(7), dp(7), dp(7));
        closeButton.setOnClickListener(view -> dialog.dismiss());
        android.widget.FrameLayout.LayoutParams closeParams =
                new android.widget.FrameLayout.LayoutParams(dp(36), dp(36), Gravity.RIGHT | Gravity.TOP);
        closeParams.setMargins(0, dp(8), dp(8), 0);
        container.addView(closeButton, closeParams);

        dialog.setContentView(container);
        if (dialog.getWindow() != null) {
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        }
        dialog.show();
        if (dialog.getWindow() != null) {
            dialog.getWindow().setLayout(
                    android.view.WindowManager.LayoutParams.MATCH_PARENT,
                    android.view.WindowManager.LayoutParams.MATCH_PARENT);
        }
    }

    private TextView createMissingImageText() {
        TextView textView = new TextView(this);
        textView.setText("Ảnh trong máy đã bị xóa hoặc không còn quyền truy cập.");
        textView.setTextSize(13);
        textView.setTextColor(getResources().getColor(R.color.colorTextNavBar));
        textView.setBackgroundResource(R.drawable.bg_search_box);
        int padding = dp(10);
        textView.setPadding(padding, padding, padding, padding);
        return textView;
    }

    private boolean canReadImage(String imageUri) {
        if (imageUri == null || imageUri.trim().isEmpty()) return false;

        try {
            InputStream inputStream = getContentResolver().openInputStream(Uri.parse(imageUri));
            if (inputStream == null) return false;
            inputStream.close();
            return true;
        } catch (Exception ignored) {
            return false;
        }
    }

    private String getDisplayUserContent(AgentMessage message) {
        if (!message.getContent().isEmpty()) return message.getContent();
        if (!message.hasImage()) return "";
        return message.getImageUris().size() > 1 ? message.getImageUris().size() + " ảnh" : "Ảnh";
    }

    private void showMessageMenu(TextView anchor, AgentMessage message) {
        PopupMenu popupMenu = new PopupMenu(this, anchor);
        popupMenu.getMenu().add("Sao chép");
        popupMenu.setOnMenuItemClickListener(menuItem -> {
            WidgetBase.copyToClipboard(this, "chat_message", message.getContent());
            Toast.makeText(this, "Đã sao chép tin nhắn.", Toast.LENGTH_SHORT).show();
            return true;
        });
        popupMenu.show();
    }

    private void updateCurrentSession(AgentMessage lastMessage) {
        if (lastMessage.isUser()
                && AgentChatHistoryHandler.NEW_CHAT_TITLE.equals(currentSession.getTitle())) {
            currentSession.setTitle(AgentChatHistoryHandler.createTitle(lastMessage.getContent()));
        }
        currentSession.setMessages(new ArrayList<>(messages));
        AgentChatHistoryHandler.saveSession(this, currentSession);
        AgentChatHistoryHandler.setActiveSessionId(this, currentSession.getId());
    }

    private CharSequence formatAssistantMessage(String content) {
        if (content == null || content.isEmpty()) return "";

        String normalizedContent = content.replaceAll("(?m)^\\s*\\*\\s+", "• ");
        SpannableStringBuilder builder = new SpannableStringBuilder();
        int currentIndex = 0;

        while (currentIndex < normalizedContent.length()) {
            int boldStart = normalizedContent.indexOf("**", currentIndex);
            if (boldStart < 0) {
                builder.append(normalizedContent.substring(currentIndex));
                break;
            }

            int boldEnd = normalizedContent.indexOf("**", boldStart + 2);
            if (boldEnd < 0) {
                builder.append(normalizedContent.substring(currentIndex));
                break;
            }

            builder.append(normalizedContent.substring(currentIndex, boldStart));
            int spanStart = builder.length();
            builder.append(normalizedContent.substring(boldStart + 2, boldEnd));
            builder.setSpan(new StyleSpan(Typeface.BOLD), spanStart, builder.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            currentIndex = boldEnd + 2;
        }

        return builder;
    }

    private String shortModelName(AgentSettings settings) {
        String provider = settings.getProvider() == null ? "" : settings.getProvider().trim();
        String model = settings.getModel() == null ? "" : settings.getModel().trim();
        if (model.isEmpty()) return trimModelLabel(provider);
        if (provider.isEmpty() || model.toLowerCase().contains(provider.toLowerCase())) {
            return trimModelLabel(model);
        }
        return trimModelLabel(provider + " " + model);
    }

    private String trimModelLabel(String modelName) {
        if (modelName.length() <= MODEL_LABEL_MAX_LENGTH) return modelName;
        return modelName.substring(0, MODEL_LABEL_MAX_LENGTH - 3) + "...";
    }

    private void setWaitingResponse(boolean waitingResponse) {
        isWaitingResponse = waitingResponse;
        edtMessage.setEnabled(!waitingResponse);
        btnPickImage.setEnabled(!waitingResponse);
        btnSend.setImageResource(waitingResponse ? R.drawable.ic_stop : R.drawable.ic_send);
        btnSend.setContentDescription(waitingResponse ? "Dừng" : "Gửi");
    }

    private String getModelGroup(AgentSettings profile) {
        String text = ((profile.getProvider() == null ? "" : profile.getProvider()) + " "
                + (profile.getModel() == null ? "" : profile.getModel())).toLowerCase();
        if (text.contains("gemini")) return "Gemini";
        if (text.contains("gpt") || text.contains("openai")) return "GPT / OpenAI";
        if (text.contains("grok") || text.contains("x.ai")) return "Grok";
        if (text.contains("openrouter")) return "OpenRouter";
        return "Khác";
    }

    private int getModelGroupOrder(AgentSettings profile) {
        String group = getModelGroup(profile);
        if ("Gemini".equals(group)) return 0;
        if ("GPT / OpenAI".equals(group)) return 1;
        if ("Grok".equals(group)) return 2;
        if ("OpenRouter".equals(group)) return 3;
        return 4;
    }

    private class ModelPickerRow {
        String title;
        AgentSettings profile;
        boolean active;

        ModelPickerRow(String title, AgentSettings profile) {
            this(title, profile, false);
        }

        ModelPickerRow(String title, AgentSettings profile, boolean active) {
            this.title = title;
            this.profile = profile;
            this.active = active;
        }
    }

    private class ModelPickerAdapter extends BaseAdapter {
        List<ModelPickerRow> rows;

        ModelPickerAdapter(List<ModelPickerRow> rows) {
            this.rows = rows;
        }

        @Override
        public int getCount() {
            return rows.size();
        }

        @Override
        public Object getItem(int position) {
            return rows.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public boolean isEnabled(int position) {
            return rows.get(position).profile != null;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ModelPickerRow row = rows.get(position);
            TextView textView = convertView instanceof TextView
                    ? (TextView) convertView
                    : new TextView(AgentChatActivity.this);
            textView.setText(row.title);
            textView.setSingleLine(true);
            textView.setPadding(dp(20), dp(row.profile == null ? 12 : 10), dp(20), dp(row.profile == null ? 6 : 10));
            textView.setTextSize(row.profile == null ? 13 : 16);
            textView.setTypeface(null, row.profile == null || row.active ? Typeface.BOLD : Typeface.NORMAL);
            textView.setTextColor(getResources().getColor(row.profile == null
                    ? R.color.colorPrimary
                    : row.active ? R.color.colorPrimary : R.color.colorText));
            return textView;
        }
    }

    private int dp(int value) {
        return (int) (value * getResources().getDisplayMetrics().density + 0.5f);
    }

    @Override
    public Context getContext() {
        return this;
    }
}
