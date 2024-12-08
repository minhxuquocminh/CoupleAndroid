package com.example.couple.Base.Handler;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.service.notification.StatusBarNotification;

import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationCompat;

import com.example.couple.Model.DateTime.Date.DateBase;
import com.example.couple.Model.DateTime.DateTimeBase;
import com.example.couple.Model.DateTime.Time.TimeBase;
import com.example.couple.Custom.Handler.Notification.NotificationInfo;
import com.example.couple.R;
import com.example.couple.View.Main.MainActivity;

import java.util.Date;

public class NotificationBase {
    public static final String channelID = "channelID";
    public static final String channelName = "Channel Name";

    private static NotificationManager mManager;

    private static void initNotification(Context context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            createChannel(context);
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private static void createChannel(Context context) {
        NotificationChannel channel = new NotificationChannel(channelID,
                channelName, NotificationManager.IMPORTANCE_HIGH);

        getManager(context).createNotificationChannel(channel);
    }

    private static NotificationManager getManager(Context context) {
        if (mManager == null) {
            mManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        }

        return mManager;
    }

    private static NotificationCompat.Builder getChannelNotification(Context context, String title, String content) {
        Intent notificationIntent = new Intent(context, MainActivity.class);
        notificationIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(context,
                0, notificationIntent, PendingIntent.FLAG_MUTABLE);
        return new NotificationCompat.Builder(context, channelID)
                .setContentTitle(title)
                .setContentText(content)
                .setSmallIcon(R.drawable.ic_launcher_background)
                .setContentIntent(pendingIntent);
    }

    public static void pushNotification(Context context, int notifyId, String title, String content) {
        initNotification(context);
        NotificationCompat.Builder nb = getChannelNotification(context, title, content);
        mManager.notify(notifyId, nb.build());
    }

    public static NotificationInfo getNotificationInfo(Context context, int notifyId) {
        NotificationManager mManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        StatusBarNotification[] activeNotifications = mManager.getActiveNotifications();

        for (StatusBarNotification notification : activeNotifications) {
            if (notification.getId() == notifyId) {
                long postTime = notification.getPostTime(); // Lấy thời gian thông báo được tạo
                Date date = new Date(postTime);
                DateTimeBase createDateTime = new DateTimeBase(DateBase.fromDate(date), TimeBase.from(postTime));
                Notification notif = notification.getNotification();
                String title = "";
                String content = "";
                if (notif != null && notif.extras != null) {
                    title = notif.extras.getString(Notification.EXTRA_TITLE);
                    content = notif.extras.getString(Notification.EXTRA_TEXT);
                }
                return new NotificationInfo(title, content, createDateTime);
            }
        }

        return NotificationInfo.getEmpty();
    }

}
