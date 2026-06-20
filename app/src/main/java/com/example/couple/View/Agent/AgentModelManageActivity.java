package com.example.couple.View.Agent;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Gravity;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import com.example.couple.Base.View.ActivityBase;
import com.example.couple.Custom.Handler.Agent.AgentSettingsHandler;
import com.example.couple.Model.Agent.AgentSettings;
import com.example.couple.R;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class AgentModelManageActivity extends ActivityBase {
    public static final String EXTRA_FROM_CHAT = "EXTRA_FROM_CHAT";
    public static final String EXTRA_ACTIVE_PROFILE_ID = "EXTRA_ACTIVE_PROFILE_ID";
    public static final String EXTRA_SELECTED_PROFILE_ID = "EXTRA_SELECTED_PROFILE_ID";

    LinearLayout linearProfiles;
    Button btnAddModel;
    boolean openedFromChat = false;
    String activeProfileId = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agent_model_manage);
        openedFromChat = getIntent().getBooleanExtra(EXTRA_FROM_CHAT, false);
        activeProfileId = getIntent().getStringExtra(EXTRA_ACTIVE_PROFILE_ID);
        if (activeProfileId == null || activeProfileId.trim().isEmpty()) {
            activeProfileId = AgentSettingsHandler.getSettings(this).getId();
        }

        linearProfiles = findViewById(R.id.linearProfiles);
        btnAddModel = findViewById(R.id.btnAddModel);

        btnAddModel.setOnClickListener(view -> {
            Intent intent = new Intent(this, AgentSettingsActivity.class);
            intent.putExtra(AgentSettingsActivity.EXTRA_NEW_PROFILE, true);
            startActivity(intent);
        });
        renderProfiles();
    }

    @Override
    protected void onResume() {
        super.onResume();
        renderProfiles();
    }

    private void renderProfiles() {
        linearProfiles.removeAllViews();
        List<AgentSettings> profiles = AgentSettingsHandler.getProfiles(this);
        if (profiles.isEmpty()) {
            TextView tvEmpty = createText("Chưa có model nào.", 15, R.color.colorTextNavBar);
            tvEmpty.setGravity(Gravity.CENTER);
            linearProfiles.addView(tvEmpty, new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT, dp(80)));
            return;
        }

        String currentGroup = "";
        for (AgentSettings profile : groupedProfiles(profiles)) {
            String group = getModelGroup(profile);
            if (!group.equals(currentGroup)) {
                currentGroup = group;
                linearProfiles.addView(createGroupHeader(group));
            }
            linearProfiles.addView(createProfileView(profile, profile.getId().equals(activeProfileId)));
        }
    }

    private List<AgentSettings> groupedProfiles(List<AgentSettings> profiles) {
        List<AgentSettings> sortedProfiles = new ArrayList<>(profiles);
        Collections.sort(sortedProfiles, new Comparator<AgentSettings>() {
            @Override
            public int compare(AgentSettings first, AgentSettings second) {
                int groupCompare = getModelGroupOrder(first) - getModelGroupOrder(second);
                if (groupCompare != 0) return groupCompare;
                return first.showName().compareToIgnoreCase(second.showName());
            }
        });
        return sortedProfiles;
    }

    private TextView createGroupHeader(String group) {
        TextView header = createText(group, 14, R.color.colorPrimary);
        header.setTypeface(null, android.graphics.Typeface.BOLD);
        header.setPadding(dp(2), dp(8), dp(2), dp(6));
        return header;
    }

    private LinearLayout createProfileView(AgentSettings profile, boolean active) {
        LinearLayout item = new LinearLayout(this);
        item.setOrientation(LinearLayout.HORIZONTAL);
        item.setGravity(Gravity.CENTER_VERTICAL);
        item.setBackgroundResource(R.drawable.bg_bridge_combination_panel);
        item.setPadding(dp(12), dp(8), dp(6), dp(8));

        TextView tvName = createText(getModelName(profile), 16,
                active ? getActiveModelColor() : R.color.colorText);
        tvName.setTypeface(null, active ? android.graphics.Typeface.BOLD : android.graphics.Typeface.NORMAL);
        tvName.setSingleLine(true);
        tvName.setEllipsize(TextUtils.TruncateAt.END);
        item.addView(tvName, new LinearLayout.LayoutParams(0,
                LinearLayout.LayoutParams.WRAP_CONTENT, 1));

        ImageButton btnMore = new ImageButton(this);
        btnMore.setImageResource(R.drawable.ic_more_vert);
        btnMore.setBackgroundColor(android.graphics.Color.TRANSPARENT);
        btnMore.setContentDescription("Tùy chọn model");
        btnMore.setPadding(dp(10), dp(8), dp(10), dp(8));
        item.addView(btnMore, new LinearLayout.LayoutParams(dp(44), dp(40)));

        item.setOnLongClickListener(view -> {
            showProfileMenu(item, profile);
            return true;
        });
        btnMore.setOnClickListener(view -> showProfileMenu(btnMore, profile));

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        params.setMargins(0, 0, 0, dp(8));
        item.setLayoutParams(params);
        return item;
    }

    private TextView createText(String text, int textSize, int colorRes) {
        TextView textView = new TextView(this);
        textView.setText(text == null ? "" : text);
        textView.setTextSize(textSize);
        textView.setTextColor(getResources().getColor(colorRes));
        return textView;
    }

    private int getActiveModelColor() {
        return openedFromChat ? R.color.colorTextLink : R.color.colorPrimary;
    }

    private void confirmDelete(AgentSettings profile) {
        new AlertDialog.Builder(this)
                .setTitle("Xóa model?")
                .setMessage("Bạn có muốn xóa model " + profile.showName() + " không?")
                .setNegativeButton("Hủy", null)
                .setPositiveButton("Xóa", (dialog, which) -> {
                    if (AgentSettingsHandler.deleteProfile(this, profile.getId())) {
                        Toast.makeText(this, "Đã xóa model.", Toast.LENGTH_SHORT).show();
                        handleDeletedProfile(profile);
                        renderProfiles();
                    }
                })
                .show();
    }

    private void handleDeletedProfile(AgentSettings profile) {
        if (!profile.getId().equals(activeProfileId)) return;

        activeProfileId = AgentSettingsHandler.getSettings(this).getId();
        if (openedFromChat) {
            Intent data = new Intent();
            data.putExtra(EXTRA_SELECTED_PROFILE_ID, activeProfileId);
            setResult(RESULT_OK, data);
        }
    }

    private void showProfileMenu(android.view.View anchor, AgentSettings profile) {
        PopupMenu popupMenu = new PopupMenu(this, anchor);
        popupMenu.getMenu().add(openedFromChat ? "Chọn" : "Đặt làm mặc định");
        popupMenu.getMenu().add("Xóa");
        popupMenu.setOnMenuItemClickListener(menuItem -> {
            String title = menuItem.getTitle().toString();
            if ("Chọn".equals(title) || "Đặt làm mặc định".equals(title)) {
                selectProfile(profile);
                Toast.makeText(this, createSelectedMessage(profile), Toast.LENGTH_SHORT).show();
                return true;
            }
            if ("Xóa".equals(title)) {
                confirmDelete(profile);
                return true;
            }
            return false;
        });
        popupMenu.show();
    }

    private void selectProfile(AgentSettings profile) {
        activeProfileId = profile.getId();
        if (openedFromChat) {
            Intent data = new Intent();
            data.putExtra(EXTRA_SELECTED_PROFILE_ID, profile.getId());
            setResult(RESULT_OK, data);
            finish();
            return;
        }

        AgentSettingsHandler.setActiveProfileId(this, profile.getId());
        renderProfiles();
    }

    private String createSelectedMessage(AgentSettings profile) {
        if (openedFromChat) return "Đã chọn model cho chat: " + profile.showName();
        return "Đã đặt model mặc định: " + profile.showName();
    }

    private String getModelName(AgentSettings profile) {
        String model = profile.getModel() == null ? "" : profile.getModel().trim();
        if (!model.isEmpty()) return model;
        return profile.showName();
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

    private int dp(int value) {
        return (int) (value * getResources().getDisplayMetrics().density + 0.5f);
    }

    @Override
    public Context getContext() {
        return this;
    }
}
