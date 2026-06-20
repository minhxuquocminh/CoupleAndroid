package com.example.couple.View.Agent;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.Gravity;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.TextView;

import com.example.couple.Base.View.ActivityBase;
import com.example.couple.Custom.Handler.Agent.AgentChatHistoryHandler;
import com.example.couple.Model.Agent.AgentChatSession;
import com.example.couple.R;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class AgentChatHistoryActivity extends ActivityBase {
    Button btnNewChat;
    LinearLayout linearChatHistory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agent_chat_history);

        btnNewChat = findViewById(R.id.btnNewChat);
        linearChatHistory = findViewById(R.id.linearChatHistory);

        btnNewChat.setOnClickListener(view -> openNewChat());
    }

    @Override
    protected void onResume() {
        super.onResume();
        renderChatHistory();
    }

    private void renderChatHistory() {
        linearChatHistory.removeAllViews();
        List<AgentChatSession> sessions = AgentChatHistoryHandler.getSessions(this);

        if (sessions.isEmpty()) {
            TextView emptyView = createTextView("Chưa có lịch sử chat.", R.color.colorTextNavBar, 15, false);
            emptyView.setGravity(Gravity.CENTER);
            emptyView.setPadding(0, getResources().getDimensionPixelSize(R.dimen.padding_15dp), 0, 0);
            linearChatHistory.addView(emptyView, new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
            return;
        }

        for (AgentChatSession session : sessions) {
            linearChatHistory.addView(createSessionView(session), createItemParams());
        }
    }

    private LinearLayout createSessionView(AgentChatSession session) {
        LinearLayout item = new LinearLayout(this);
        item.setOrientation(LinearLayout.HORIZONTAL);
        item.setGravity(Gravity.CENTER_VERTICAL);
        item.setBackgroundResource(R.drawable.bg_bridge_combination_panel);
        int padding = getResources().getDimensionPixelSize(R.dimen.padding_10dp);
        item.setPadding(padding, padding, padding, padding);
        item.setOnClickListener(view -> openSession(session));
        item.setOnLongClickListener(view -> {
            confirmDeleteSession(session);
            return true;
        });

        LinearLayout content = new LinearLayout(this);
        content.setOrientation(LinearLayout.VERTICAL);

        TextView title = createTextView(session.getTitle(), R.color.colorPrimary, 17, true);
        TextView description = createTextView(formatSessionInfo(session), R.color.colorTextNavBar, 13, false);

        content.addView(title);
        content.addView(description);
        item.addView(content, new LinearLayout.LayoutParams(
                0, LinearLayout.LayoutParams.WRAP_CONTENT, 1));

        ImageButton btnMore = new ImageButton(this);
        btnMore.setImageResource(R.drawable.ic_more_vert);
        btnMore.setBackgroundColor(getResources().getColor(android.R.color.transparent));
        btnMore.setContentDescription("Tùy chọn");
        btnMore.setPadding(getResources().getDimensionPixelSize(R.dimen.padding_5dp),
                getResources().getDimensionPixelSize(R.dimen.padding_5dp),
                getResources().getDimensionPixelSize(R.dimen.padding_5dp),
                getResources().getDimensionPixelSize(R.dimen.padding_5dp));
        btnMore.setOnClickListener(view -> showSessionMenu(btnMore, session));
        LinearLayout.LayoutParams moreParams = new LinearLayout.LayoutParams(dp(42), dp(42));
        moreParams.setMargins(getResources().getDimensionPixelSize(R.dimen.margin_10dp), 0, 0, 0);
        item.addView(btnMore, moreParams);
        return item;
    }

    private void showSessionMenu(ImageButton anchor, AgentChatSession session) {
        PopupMenu popupMenu = new PopupMenu(this, anchor);
        popupMenu.getMenu().add("Xóa lịch sử");
        popupMenu.setOnMenuItemClickListener(menuItem -> {
            confirmDeleteSession(session);
            return true;
        });
        popupMenu.show();
    }

    private void confirmDeleteSession(AgentChatSession session) {
        new AlertDialog.Builder(this)
                .setTitle("Xóa lịch sử chat?")
                .setMessage("Bạn có chắc muốn xóa \"" + session.getTitle() + "\" không?")
                .setPositiveButton("Xóa", (dialog, which) -> {
                    AgentChatHistoryHandler.deleteSession(this, session.getId());
                    renderChatHistory();
                })
                .setNegativeButton("Hủy", (dialog, which) -> dialog.dismiss())
                .show();
    }

    private String formatSessionInfo(AgentChatSession session) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM HH:mm", Locale.US);
        return session.getMessages().size() + " tin - " + dateFormat.format(new Date(session.getUpdatedAt()));
    }

    private TextView createTextView(String text, int colorId, int textSize, boolean bold) {
        TextView textView = new TextView(this);
        textView.setText(text);
        textView.setTextSize(textSize);
        textView.setTextColor(getResources().getColor(colorId));
        if (bold) textView.setTypeface(null, Typeface.BOLD);
        return textView;
    }

    private LinearLayout.LayoutParams createItemParams() {
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        params.setMargins(0, 0, 0, getResources().getDimensionPixelSize(R.dimen.margin_5dp));
        return params;
    }

    private void openNewChat() {
        startActivity(new Intent(this, AgentChatActivity.class));
    }

    private void openSession(AgentChatSession session) {
        Intent intent = new Intent(this, AgentChatActivity.class);
        intent.putExtra(AgentChatActivity.EXTRA_SESSION_ID, session.getId());
        startActivity(intent);
    }

    private int dp(int value) {
        return (int) (value * getResources().getDisplayMetrics().density + 0.5f);
    }

    @Override
    public Context getContext() {
        return this;
    }
}
