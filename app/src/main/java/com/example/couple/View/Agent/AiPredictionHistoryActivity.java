package com.example.couple.View.Agent;

import android.app.AlertDialog;
import android.content.Context;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.Gravity;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.TextView;

import com.example.couple.Base.View.ActivityBase;
import com.example.couple.Custom.Handler.Agent.AiPredictionHistoryHandler;
import com.example.couple.Model.Agent.AiPrediction;
import com.example.couple.R;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class AiPredictionHistoryActivity extends ActivityBase {
    LinearLayout linearPredictionHistory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ai_prediction_history);

        linearPredictionHistory = findViewById(R.id.linearPredictionHistory);
    }

    @Override
    protected void onResume() {
        super.onResume();
        renderHistory();
    }

    private void renderHistory() {
        linearPredictionHistory.removeAllViews();
        List<AiPrediction> predictions = AiPredictionHistoryHandler.getPredictions(this);

        if (predictions.isEmpty()) {
            TextView emptyView = createTextView("Chưa có lịch sử dự đoán AI.", R.color.colorTextNavBar, 15, false);
            emptyView.setGravity(Gravity.CENTER);
            emptyView.setPadding(0, getResources().getDimensionPixelSize(R.dimen.padding_15dp), 0, 0);
            linearPredictionHistory.addView(emptyView, new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
            return;
        }

        for (AiPrediction prediction : predictions) {
            linearPredictionHistory.addView(createPredictionView(prediction), createItemParams());
        }
    }

    private LinearLayout createPredictionView(AiPrediction prediction) {
        LinearLayout item = new LinearLayout(this);
        item.setOrientation(LinearLayout.HORIZONTAL);
        item.setGravity(Gravity.CENTER_VERTICAL);
        item.setBackgroundResource(R.drawable.bg_bridge_combination_panel);
        int padding = getResources().getDimensionPixelSize(R.dimen.padding_10dp);
        item.setPadding(padding, padding, padding, padding);
        item.setOnClickListener(view -> showPredictionDetail(prediction));
        item.setOnLongClickListener(view -> {
            confirmDeletePrediction(prediction);
            return true;
        });

        LinearLayout content = new LinearLayout(this);
        content.setOrientation(LinearLayout.VERTICAL);

        TextView title = createTextView(prediction.showTitle(), R.color.colorPrimary, 17, true);
        TextView description = createTextView(formatPredictionInfo(prediction), R.color.colorTextNavBar, 13, false);
        TextView preview = createTextView(createPreview(prediction.getContent()), R.color.colorText, 14, false);
        preview.setMaxLines(2);

        content.addView(title);
        content.addView(description);
        content.addView(preview);
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
        btnMore.setOnClickListener(view -> showPredictionMenu(btnMore, prediction));
        LinearLayout.LayoutParams moreParams = new LinearLayout.LayoutParams(dp(42), dp(42));
        moreParams.setMargins(getResources().getDimensionPixelSize(R.dimen.margin_10dp), 0, 0, 0);
        item.addView(btnMore, moreParams);
        return item;
    }

    private void showPredictionMenu(ImageButton anchor, AiPrediction prediction) {
        PopupMenu popupMenu = new PopupMenu(this, anchor);
        popupMenu.getMenu().add("Xem chi tiết");
        popupMenu.getMenu().add("Xóa lịch sử");
        popupMenu.setOnMenuItemClickListener(menuItem -> {
            String title = menuItem.getTitle().toString();
            if ("Xem chi tiết".equals(title)) {
                showPredictionDetail(prediction);
            } else {
                confirmDeletePrediction(prediction);
            }
            return true;
        });
        popupMenu.show();
    }

    private void showPredictionDetail(AiPrediction prediction) {
        String message = prediction.getContent();
        if (!prediction.showDataRange().trim().isEmpty()) {
            message = prediction.showDataRange() + "\n\n" + message;
        }
        if (!prediction.getLocalStats().trim().isEmpty()) {
            message += "\n\n--- Thống kê nền ---\n" + prediction.getLocalStats();
        }
        new AlertDialog.Builder(this)
                .setTitle(prediction.showTitle())
                .setMessage(message)
                .setPositiveButton("Đóng", (dialog, which) -> dialog.dismiss())
                .show();
    }

    private void confirmDeletePrediction(AiPrediction prediction) {
        new AlertDialog.Builder(this)
                .setTitle("Xóa lịch sử AI?")
                .setMessage("Bạn có chắc muốn xóa \"" + prediction.showTitle() + "\" không?")
                .setPositiveButton("Xóa", (dialog, which) -> {
                    AiPredictionHistoryHandler.deletePrediction(this, prediction.getId());
                    renderHistory();
                })
                .setNegativeButton("Hủy", (dialog, which) -> dialog.dismiss())
                .show();
    }

    private String formatPredictionInfo(AiPrediction prediction) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM HH:mm", Locale.US);
        String modelName = prediction.getModelName() == null ? "" : prediction.getModelName().trim();
        String time = dateFormat.format(new Date(prediction.getCreatedAt()));
        String dataRange = prediction.showDataRange();
        String prefix = modelName.isEmpty() ? time : modelName + " - " + time;
        return dataRange.trim().isEmpty() ? prefix : prefix + " - " + dataRange;
    }

    private String createPreview(String content) {
        String preview = content == null ? "" : content.trim().replaceAll("\\s+", " ");
        if (preview.length() <= 110) return preview;
        return preview.substring(0, 110) + "...";
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

    private int dp(int value) {
        return (int) (value * getResources().getDisplayMetrics().density + 0.5f);
    }

    @Override
    public Context getContext() {
        return this;
    }
}
