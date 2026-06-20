package com.example.couple.View.Setting;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.TimePicker;

import androidx.core.app.ActivityCompat;

import com.example.couple.Base.View.ActivityBase;
import com.example.couple.Custom.Handler.Notification.NotificationSettingsHandler;
import com.example.couple.Model.DateTime.Time.TimeBase;
import com.example.couple.R;

public class NotificationSettingActivity extends ActivityBase {
    private static final int REQUEST_POST_NOTIFICATION = 2233;

    Switch switchAppNotifications;
    Switch switchBridgeNotification;
    TextView tvBridgeNotificationTime;
    Button btnEditBridgeNotificationTime;
    Button btnSaveBridgeNotificationTime;
    Button btnCancelBridgeNotificationTime;
    LinearLayout linearBridgeTimeEditor;
    TimePicker timePickerBridgeNotification;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification_setting);

        switchAppNotifications = findViewById(R.id.switchAppNotifications);
        switchBridgeNotification = findViewById(R.id.switchBridgeNotification);
        tvBridgeNotificationTime = findViewById(R.id.tvBridgeNotificationTime);
        btnEditBridgeNotificationTime = findViewById(R.id.btnEditBridgeNotificationTime);
        btnSaveBridgeNotificationTime = findViewById(R.id.btnSaveBridgeNotificationTime);
        btnCancelBridgeNotificationTime = findViewById(R.id.btnCancelBridgeNotificationTime);
        linearBridgeTimeEditor = findViewById(R.id.linearBridgeTimeEditor);
        timePickerBridgeNotification = findViewById(R.id.timePickerBridgeNotification);

        bindData();
        bindEvents();
    }

    private void bindData() {
        switchAppNotifications.setChecked(NotificationSettingsHandler.isAppNotificationEnabled(this));
        switchBridgeNotification.setChecked(NotificationSettingsHandler.isBridgeNotificationEnabled(this));

        timePickerBridgeNotification.setIs24HourView(true);
        bindBridgeNotificationTime();

        updateBridgeControls();
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

        btnEditBridgeNotificationTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bindBridgeNotificationTime();
                linearBridgeTimeEditor.setVisibility(View.VISIBLE);
                btnEditBridgeNotificationTime.setVisibility(View.GONE);
                btnCancelBridgeNotificationTime.setVisibility(View.VISIBLE);
            }
        });

        btnCancelBridgeNotificationTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bindBridgeNotificationTime();
                linearBridgeTimeEditor.setVisibility(View.GONE);
                btnEditBridgeNotificationTime.setVisibility(View.VISIBLE);
                btnCancelBridgeNotificationTime.setVisibility(View.GONE);
            }
        });

        btnSaveBridgeNotificationTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveSelectedBridgeNotificationTime();
                bindBridgeNotificationTime();
                linearBridgeTimeEditor.setVisibility(View.GONE);
                btnEditBridgeNotificationTime.setVisibility(View.VISIBLE);
                btnCancelBridgeNotificationTime.setVisibility(View.GONE);
            }
        });
    }

    private void bindBridgeNotificationTime() {
        TimeBase timeBase = NotificationSettingsHandler.getBridgeNotificationTime(this);
        bindBridgeNotificationTimeLabel(timeBase);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            timePickerBridgeNotification.setHour(timeBase.getHour());
            timePickerBridgeNotification.setMinute(timeBase.getMinute());
        } else {
            timePickerBridgeNotification.setCurrentHour(timeBase.getHour());
            timePickerBridgeNotification.setCurrentMinute(timeBase.getMinute());
        }
    }

    private void bindBridgeNotificationTimeLabel(TimeBase timeBase) {
        tvBridgeNotificationTime.setText("Giờ báo: " + timeBase.showHHMM());
    }

    private void saveSelectedBridgeNotificationTime() {
        int hour;
        int minute;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            hour = timePickerBridgeNotification.getHour();
            minute = timePickerBridgeNotification.getMinute();
        } else {
            hour = timePickerBridgeNotification.getCurrentHour();
            minute = timePickerBridgeNotification.getCurrentMinute();
        }
        NotificationSettingsHandler.setBridgeNotificationTime(this, hour, minute);
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
        boolean isBridgeNotificationEnabled = switchBridgeNotification.isChecked();
        switchBridgeNotification.setEnabled(isAppNotificationEnabled);
        btnEditBridgeNotificationTime.setEnabled(isAppNotificationEnabled && isBridgeNotificationEnabled);
        btnSaveBridgeNotificationTime.setEnabled(isAppNotificationEnabled && isBridgeNotificationEnabled);
        btnCancelBridgeNotificationTime.setEnabled(isAppNotificationEnabled && isBridgeNotificationEnabled);
        timePickerBridgeNotification.setEnabled(isAppNotificationEnabled && isBridgeNotificationEnabled);
        if (!isAppNotificationEnabled || !isBridgeNotificationEnabled) {
            linearBridgeTimeEditor.setVisibility(View.GONE);
            btnEditBridgeNotificationTime.setVisibility(View.VISIBLE);
            btnCancelBridgeNotificationTime.setVisibility(View.GONE);
        }
    }

    @Override
    public Context getContext() {
        return this;
    }
}
