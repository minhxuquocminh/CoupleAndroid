package com.example.couple.View.UpdateDataInfo.TestNotif;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.example.couple.Base.Handler.InternetBase;
import com.example.couple.Base.Handler.NotificationBase;
import com.example.couple.Custom.Const.NotifyId;
import com.example.couple.Custom.Handler.Notification.UpdateDataAlarm;

public class UpdateDataTest extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        boolean isInternetAvailable = InternetBase.isInternetAvailable(context);
        if (!isInternetAvailable) {
            String title = "XSMB";
            String content = "Lỗi lấy kết quả XS Đặc Biệt Miền Bắc (Lỗi không có mạng).";
            NotificationBase.pushNotification(context, NotifyId.UPDATE_DATA, title, content);
            return;
        }
        UpdateDataAlarm updateDataAlarm = new UpdateDataAlarm();
        updateDataAlarm.getData(context);
    }
}