package com.example.couple.View.Notification;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.couple.Base.Handler.NotificationBase;
import com.example.couple.Base.View.ActivityBase;
import com.example.couple.Custom.Const.NotifyId;
import com.example.couple.Custom.Handler.Notification.NotificationInfo;
import com.example.couple.R;
import com.example.couple.View.Setting.NotificationSettingActivity;

public class NotificationActivity extends ActivityBase {
    LinearLayout linearNotificationList;
    TextView tvEmptyNotification;
    TextView tvNotificationSetting;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);

        linearNotificationList = findViewById(R.id.linearNotificationList);
        tvEmptyNotification = findViewById(R.id.tvEmptyNotification);
        tvNotificationSetting = findViewById(R.id.tvNotificationSetting);

        showNotifications();
        tvNotificationSetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(NotificationActivity.this, NotificationSettingActivity.class));
            }
        });
    }

    private void showNotifications() {
        linearNotificationList.removeAllViews();
        addNotification("Cập nhật dữ liệu", NotificationBase.getNotificationInfo(this, NotifyId.UPDATE_DATA));
        addNotification("Cầu hôm nay", NotificationBase.getNotificationInfo(this, NotifyId.SHOW_NEW_BRIDGE));
        tvEmptyNotification.setVisibility(linearNotificationList.getChildCount() == 0 ? View.VISIBLE : View.GONE);
    }

    private void addNotification(String label, NotificationInfo notificationInfo) {
        if (notificationInfo.isEmpty()) return;

        TextView tvNotification = new TextView(this);
        String time = notificationInfo.getCreatedDateTime().getDateBase().showFullChars()
                + " " + notificationInfo.getCreatedDateTime().getTimeBase().showHHMM();
        String show = label + "\n" + notificationInfo.getTitle() + "\n"
                + notificationInfo.getContent() + "\n" + time;
        tvNotification.setText(show);
        tvNotification.setTextColor(getResources().getColor(R.color.colorText));
        tvNotification.setTextSize(15);
        tvNotification.setLineSpacing(4, 1);
        tvNotification.setBackgroundResource(R.drawable.bg_bridge_combination_panel);
        tvNotification.setPadding(12, 12, 12, 12);

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        params.setMargins(0, 0, 0, 10);
        linearNotificationList.addView(tvNotification, params);
    }

    @Override
    public Context getContext() {
        return this;
    }
}
