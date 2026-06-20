package com.example.couple.Custom.Handler.Notification;

import static android.content.Context.MODE_PRIVATE;

import android.content.Context;

import com.example.couple.Base.Handler.AlarmBase;
import com.example.couple.Custom.Const.RequestCode;
import com.example.couple.Model.DateTime.Time.TimeBase;

public class NotificationSettingsHandler {
    private static final String PREF_NAME = "NOTIFICATION_SETTINGS";
    private static final String KEY_APP_NOTIFICATIONS_ENABLED = "APP_NOTIFICATIONS_ENABLED";
    private static final String KEY_BRIDGE_NOTIFICATIONS_ENABLED = "BRIDGE_NOTIFICATIONS_ENABLED";
    private static final String KEY_BRIDGE_NOTIFICATION_HOUR = "BRIDGE_NOTIFICATION_HOUR";
    private static final String KEY_BRIDGE_NOTIFICATION_MINUTE = "BRIDGE_NOTIFICATION_MINUTE";

    private static final int DEFAULT_BRIDGE_NOTIFICATION_HOUR = 10;
    private static final int DEFAULT_BRIDGE_NOTIFICATION_MINUTE = 0;

    public static boolean isAppNotificationEnabled(Context context) {
        return context.getSharedPreferences(PREF_NAME, MODE_PRIVATE)
                .getBoolean(KEY_APP_NOTIFICATIONS_ENABLED, true);
    }

    public static void setAppNotificationEnabled(Context context, boolean enabled) {
        context.getSharedPreferences(PREF_NAME, MODE_PRIVATE).edit()
                .putBoolean(KEY_APP_NOTIFICATIONS_ENABLED, enabled)
                .apply();
        applyBridgeNotificationAlarm(context);
    }

    public static boolean isBridgeNotificationEnabled(Context context) {
        return context.getSharedPreferences(PREF_NAME, MODE_PRIVATE)
                .getBoolean(KEY_BRIDGE_NOTIFICATIONS_ENABLED, true);
    }

    public static void setBridgeNotificationEnabled(Context context, boolean enabled) {
        context.getSharedPreferences(PREF_NAME, MODE_PRIVATE).edit()
                .putBoolean(KEY_BRIDGE_NOTIFICATIONS_ENABLED, enabled)
                .apply();
        applyBridgeNotificationAlarm(context);
    }

    public static TimeBase getBridgeNotificationTime(Context context) {
        int hour = context.getSharedPreferences(PREF_NAME, MODE_PRIVATE)
                .getInt(KEY_BRIDGE_NOTIFICATION_HOUR, DEFAULT_BRIDGE_NOTIFICATION_HOUR);
        int minute = context.getSharedPreferences(PREF_NAME, MODE_PRIVATE)
                .getInt(KEY_BRIDGE_NOTIFICATION_MINUTE, DEFAULT_BRIDGE_NOTIFICATION_MINUTE);
        return new TimeBase(hour, minute, 0);
    }

    public static void setBridgeNotificationTime(Context context, int hour, int minute) {
        context.getSharedPreferences(PREF_NAME, MODE_PRIVATE).edit()
                .putInt(KEY_BRIDGE_NOTIFICATION_HOUR, hour)
                .putInt(KEY_BRIDGE_NOTIFICATION_MINUTE, minute)
                .apply();
        applyBridgeNotificationAlarm(context);
    }

    public static void applyBridgeNotificationAlarm(Context context) {
        if (isAppNotificationEnabled(context) && isBridgeNotificationEnabled(context)) {
            AlarmBase.registerAlarmEveryDay(context, NextDayBridgeAlarm.class, RequestCode.ALARM_TODAY_BRIDGE,
                    getBridgeNotificationTime(context));
        } else {
            AlarmBase.cancelAlarm(context, NextDayBridgeAlarm.class, RequestCode.ALARM_TODAY_BRIDGE);
        }
    }

}
