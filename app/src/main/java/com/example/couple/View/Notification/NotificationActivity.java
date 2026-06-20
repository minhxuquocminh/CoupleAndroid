package com.example.couple.View.Notification;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.couple.Base.Handler.NotificationBase;
import com.example.couple.Base.View.ActivityBase;
import com.example.couple.Custom.Const.NotifyId;
import com.example.couple.Custom.Handler.Notification.BridgeNotificationStorageHandler;
import com.example.couple.Custom.Handler.Notification.NotificationInfo;
import com.example.couple.Model.Notification.BridgeNotification;
import com.example.couple.R;
import com.example.couple.View.Setting.NotificationSettingActivity;

import java.util.List;

public class NotificationActivity extends ActivityBase {
    LinearLayout linearNotificationList;
    TextView tvEmptyNotification;
    Button tvNotificationSetting;

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
        addBridgeNotifications();
        tvEmptyNotification.setVisibility(linearNotificationList.getChildCount() == 0 ? View.VISIBLE : View.GONE);
    }

    private void addBridgeNotifications() {
        List<BridgeNotification> notifications = BridgeNotificationStorageHandler.getNotifications(this);
        for (BridgeNotification notification : notifications) {
            addBridgeNotification(notification);
        }
    }

    private void addBridgeNotification(BridgeNotification notification) {
        TextView tvNotification = addTextNotification(
                BridgeNotificationStorageHandler.show(notification),
                NewBridgeActivity.class
        );
        tvNotification.setOnClickListener(view -> {
            Intent intent = new Intent(NotificationActivity.this, NewBridgeActivity.class);
            intent.putExtra(NewBridgeActivity.EXTRA_TARGET_DATE, notification.getDateBase().toString("-"));
            startActivity(intent);
        });
    }

    private void addNotification(String label, NotificationInfo notificationInfo) {
        if (notificationInfo.isEmpty()) return;

        String time = notificationInfo.getCreatedDateTime().getDateBase().showFullChars()
                + " " + notificationInfo.getCreatedDateTime().getTimeBase().showHHMM();
        String show = label + "\n" + notificationInfo.getTitle() + "\n"
                + notificationInfo.getContent() + "\n" + time;
        addTextNotification(show, null);
    }

    private TextView addTextNotification(String text, Class<?> targetActivity) {
        TextView tvNotification = new TextView(this);
        tvNotification.setText(text);
        tvNotification.setTextColor(getResources().getColor(R.color.colorText));
        tvNotification.setTextSize(15);
        tvNotification.setLineSpacing(4, 1);
        tvNotification.setBackgroundResource(R.drawable.bg_bridge_combination_panel);
        int padding = getResources().getDimensionPixelSize(R.dimen.padding_10dp);
        tvNotification.setPadding(padding, padding, padding, padding);
        if (targetActivity != null) {
            tvNotification.setOnClickListener(view ->
                    startActivity(new Intent(NotificationActivity.this, targetActivity)));
        }

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        int marginBottom = getResources().getDimensionPixelSize(R.dimen.margin_5dp);
        params.setMargins(0, 0, 0, marginBottom);
        linearNotificationList.addView(tvNotification, params);
        return tvNotification;
    }

    @Override
    public Context getContext() {
        return this;
    }
}
