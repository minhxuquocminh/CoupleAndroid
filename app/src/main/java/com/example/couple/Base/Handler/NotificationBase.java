package com.example.couple.Base.Handler;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;

import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationCompat;

import com.example.couple.R;
import com.example.couple.View.Main.MainActivity;

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
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, notificationIntent, 0);
        return new NotificationCompat.Builder(context, channelID)
                .setContentTitle(title)
                .setContentText(content)
                .setSmallIcon(R.drawable.ic_launcher_background)
                .setContentIntent(pendingIntent);
    }

    public static void pushNotification(Context context, String title, String content) {
        initNotification(context);
        NotificationCompat.Builder nb = getChannelNotification(context, title, content);
        mManager.notify(1, nb.build());
    }


}
