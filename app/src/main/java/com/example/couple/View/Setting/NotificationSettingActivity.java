package com.example.couple.View.Setting;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.TimePicker;

import androidx.core.app.ActivityCompat;

import com.example.couple.Base.View.ActivityBase;
import com.example.couple.Custom.Handler.JackpotHandler;
import com.example.couple.Custom.Handler.LotteryHandler;
import com.example.couple.Custom.Handler.Notification.NotificationSettingsHandler;
import com.example.couple.Model.DateTime.Date.DateBase;
import com.example.couple.Model.DateTime.Time.TimeBase;
import com.example.couple.R;

public class NotificationSettingActivity extends ActivityBase {
    private static final int REQUEST_POST_NOTIFICATION = 2233;

    Switch switchAppNotifications;
    Switch switchBridgeNotification;
    TimePicker timePickerBridgeNotification;
    TextView tvDataStatus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification_setting);

        switchAppNotifications = findViewById(R.id.switchAppNotifications);
        switchBridgeNotification = findViewById(R.id.switchBridgeNotification);
        timePickerBridgeNotification = findViewById(R.id.timePickerBridgeNotification);
        tvDataStatus = findViewById(R.id.tvDataStatus);

        bindData();
        bindEvents();
    }

    private void bindData() {
        switchAppNotifications.setChecked(NotificationSettingsHandler.isAppNotificationEnabled(this));
        switchBridgeNotification.setChecked(NotificationSettingsHandler.isBridgeNotificationEnabled(this));

        timePickerBridgeNotification.setIs24HourView(true);
        TimeBase timeBase = NotificationSettingsHandler.getBridgeNotificationTime(this);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            timePickerBridgeNotification.setHour(timeBase.getHour());
            timePickerBridgeNotification.setMinute(timeBase.getMinute());
        } else {
            timePickerBridgeNotification.setCurrentHour(timeBase.getHour());
            timePickerBridgeNotification.setCurrentMinute(timeBase.getMinute());
        }

        updateBridgeControls();
        showDataStatus();
    }

    private void bindEvents() {
        switchAppNotifications.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean checked) {
                if (checked) requestPostNotificationPermissionIfNeeded();
                NotificationSettingsHandler.setAppNotificationEnabled(NotificationSettingActivity.this, checked);
                updateBridgeControls();
            }
        });

        switchBridgeNotification.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean checked) {
                NotificationSettingsHandler.setBridgeNotificationEnabled(NotificationSettingActivity.this, checked);
                updateBridgeControls();
            }
        });

        timePickerBridgeNotification.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener() {
            @Override
            public void onTimeChanged(TimePicker timePicker, int hour, int minute) {
                NotificationSettingsHandler.setBridgeNotificationTime(NotificationSettingActivity.this, hour, minute);
            }
        });
    }

    private void requestPostNotificationPermissionIfNeeded() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.TIRAMISU) return;
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS)
                == PackageManager.PERMISSION_GRANTED) {
            return;
        }

        ActivityCompat.requestPermissions(this,
                new String[]{Manifest.permission.POST_NOTIFICATIONS},
                REQUEST_POST_NOTIFICATION);
    }

    private void updateBridgeControls() {
        boolean isAppNotificationEnabled = switchAppNotifications.isChecked();
        switchBridgeNotification.setEnabled(isAppNotificationEnabled);
        timePickerBridgeNotification.setEnabled(isAppNotificationEnabled && switchBridgeNotification.isChecked());
    }

    private void showDataStatus() {
        DateBase yesterday = DateBase.today().addDays(-1);
        DateBase lastJackpotDate = JackpotHandler.getLastDate(this);
        DateBase lastLotteryDate = LotteryHandler.getLastDate(this);

        String status = "Điều kiện báo cầu: cần dữ liệu ngày " + yesterday.showFullChars() + ".\n";
        status += "ĐB gần nhất: " + (lastJackpotDate.isEmpty() ? "chưa có" : lastJackpotDate.showFullChars()) + ".\n";
        status += "XSMB gần nhất: " + (lastLotteryDate.isEmpty() ? "chưa có" : lastLotteryDate.showFullChars()) + ".";
        tvDataStatus.setText(status);
    }

    @Override
    public Context getContext() {
        return this;
    }
}
