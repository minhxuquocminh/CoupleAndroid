package com.example.couple.Custom.Handler.Notification;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.example.couple.Base.Handler.InternetBase;
import com.example.couple.Base.Handler.NotificationBase;
import com.example.couple.Base.Handler.TextToSpeechBase;
import com.example.couple.Custom.Const.NotifyId;
import com.example.couple.Custom.Handler.JackpotHandler;
import com.example.couple.Custom.Handler.Sync.CheckSync;
import com.example.couple.Custom.Handler.Sync.SyncDataHandler;
import com.example.couple.Custom.Handler.Sync.SyncDataState;
import com.example.couple.Custom.Handler.Sync.SyncState;
import com.example.couple.Model.DateTime.Date.DateBase;
import com.example.couple.Model.DateTime.Time.TimeBase;
import com.example.couple.Model.Origin.Jackpot;

import java.util.List;

// class alarm receiver
public class UpdateDataAlarm extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        boolean isSyncJackpot = CheckSync.isSyncJackpot(context);
        if (isSyncJackpot) {
            return;
        }

        boolean isInternetAvailable = InternetBase.isInternetAvailable(context);
        if (!isInternetAvailable) {
            String title = "Cập nhật XSĐB";
            String content = "Lỗi không có mạng.";
            NotificationBase.pushNotification(context, NotifyId.UPDATE_DATA, title, content);
        } else {
            getData(context);
        }
    }

    public void getData(Context context) {
        SyncDataState syncDataState = SyncDataHandler.execute(context);
        SyncState jackpotSyncState = syncDataState.getSyncJackpotState();
        if (jackpotSyncState == SyncState.DONE) {
            List<Jackpot> jackpotList = JackpotHandler.getReserveJackpotListFromFile(context, 99);
            if (!jackpotList.isEmpty()) {
                String title = "XSĐB ngày " + jackpotList.get(0).getDateBase().showFullChars();
                String content = "Kết quả: " + jackpotList.get(0).getJackpot() + ".";
                NotificationBase.pushNotification(context, NotifyId.UPDATE_DATA, title, content);
                new TextToSpeechBase(context, "Kết quả: " + jackpotList.get(0).getCouple().show());
            }
        } else {
            String title = "XSĐB ngày " + DateBase.TO_DAY().showFullChars();
            String content = jackpotSyncState.name + " (" + TimeBase.CURRENT().showHHMM() + ").";
            NotificationBase.pushNotification(context, NotifyId.UPDATE_DATA, title, content);
        }
    }

}
