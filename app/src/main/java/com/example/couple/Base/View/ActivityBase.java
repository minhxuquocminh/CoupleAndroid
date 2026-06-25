package com.example.couple.Base.View;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;

import com.example.couple.R;
import com.example.couple.View.Main.MainActivity;

public abstract class ActivityBase extends SpeechToTextActivityBase {
    public abstract Context getContext();

    private boolean isEventBound = false;

    @Override
    protected void onStart() {
        super.onStart();
        bindBackButton();

        TextView tvToolbar = findViewById(R.id.tvToolbar);
        if (tvToolbar == null) return;
        if (!isEventBound) {
            tvToolbar.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    ActivityBase.super.startListening();
                    return false;
                }
            });
            isEventBound = true;
        }
    }

    private void bindBackButton() {
        if (this instanceof MainActivity) return;

        Toolbar toolbar = findViewById(R.id.toolbar);
        if (toolbar == null) return;

        toolbar.setContentInsetStartWithNavigation(0);
        toolbar.setContentInsetsRelative(0, 0);
        toolbar.setPadding(0, 0, 0, 0);
        toolbar.setNavigationIcon(null);

        TextView tvToolbar = findViewById(R.id.tvToolbar);
        if (tvToolbar == null) return;

        Toolbar.LayoutParams params = new Toolbar.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT);
        params.gravity = Gravity.START | Gravity.CENTER_VERTICAL;
        tvToolbar.setLayoutParams(params);
        tvToolbar.setGravity(Gravity.CENTER_VERTICAL);
        tvToolbar.setPadding(dp(8), 0, dp(10), 0);
        tvToolbar.setSingleLine(true);
        tvToolbar.setEllipsize(TextUtils.TruncateAt.END);
        tvToolbar.setTextSize(TypedValue.COMPLEX_UNIT_SP, 17);
        tvToolbar.setText(getCompactToolbarTitle(tvToolbar.getText().toString()));
        setToolbarBackIcon(tvToolbar);
        tvToolbar.setCompoundDrawablePadding(dp(2));
        tvToolbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void setToolbarBackIcon(TextView tvToolbar) {
        Drawable backIcon = ContextCompat.getDrawable(this, R.drawable.ic_back);
        if (backIcon == null) return;
        int offsetTop = dp(0.5f);
        int size = dp(24);
        backIcon.setBounds(0, offsetTop, size, size + offsetTop);
        tvToolbar.setCompoundDrawables(backIcon, null, null, null);
    }

    private String getCompactToolbarTitle(String title) {
        if (title == null) return "";
        title = title.trim();
        if (title.split("\\s+").length <= 6) return title;

        switch (title) {
            case "Cài đặt thông báo":
                return "Cài thông báo";
            case "Tổ hợp cầu":
                return "Tổ hợp";
            case "Cầu sau khi ra kép":
                return "Cầu sau kép";
            case "Đặc Biệt năm nay":
                return "ĐB năm nay";
            case "Nhịp chạy ĐB":
                return "Nhịp ĐB";
            case "Đường dẫn và tham số":
                return "URL và tham số";
            case "Thay đổi mật khẩu":
                return "Đổi mật khẩu";
            case "Thêm dữ liệu vào CSDL":
                return "Thêm dữ liệu";
            case "Tìm kiếm":
                return "Tìm kiếm";
            default:
                return title;
        }
    }

    private int dp(int value) {
        return (int) (value * getResources().getDisplayMetrics().density + 0.5f);
    }

    private int dp(float value) {
        return (int) (value * getResources().getDisplayMetrics().density + 0.5f);
    }

    @Override
    public void post(String resultText) {
        changeActivity(getContext(), resultText);
    }

    public void changeActivity(Context context, String actionText) {
        if (!(context instanceof Activity)) return;
        Activity activity = (Activity) context;
        if (actionText.contains("quay lại")) {
            activity.onBackPressed();
            return;
        }

        for (ActivityType activityType : ActivityType.values()) {
            if (!activity.getClass().equals(activityType.targetClass) && actionText.contains(activityType.key)) {
                Intent intent = new Intent(context, activityType.targetClass);
                context.startActivity(intent);
            }
        }
    }

}
