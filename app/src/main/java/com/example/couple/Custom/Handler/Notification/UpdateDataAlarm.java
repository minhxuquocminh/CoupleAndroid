package com.example.couple.Custom.Handler.Notification;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.example.couple.Base.Handler.InternetBase;
import com.example.couple.Base.Handler.NotificationBase;
import com.example.couple.Custom.Const.NotifyId;
import com.example.couple.Custom.Handler.JackpotHandler;
import com.example.couple.Custom.Handler.Sync.CheckSync;
import com.example.couple.Custom.Handler.Sync.SyncDataHandler;
import com.example.couple.Custom.Handler.Sync.SyncDataState;
import com.example.couple.Custom.Handler.Sync.SyncState;
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
            String title = "XSMB";
            String content = "Lỗi lấy kết quả XS Đặc Biệt Miền Bắc (Lỗi không có mạng).";
            NotificationBase.pushNotification(context, NotifyId.UPDATE_DATA, title, content);
        } else {
            getData(context);
        }
    }

    public void getData(Context context) {
        SyncDataState syncDataState = SyncDataHandler.execute(context);
        SyncState jackpotSyncState = syncDataState.getSyncJackpotState();
        if (jackpotSyncState == SyncState.OK) {
            List<Jackpot> jackpotList = JackpotHandler.getReserveJackpotListFromFile(context, 99);
            if (!jackpotList.isEmpty()) {
                String content = "Kết quả XS Đặc biệt Miền Bắc hôm nay là: " +
                        jackpotList.get(0).getJackpot() + ".";
                NotificationBase.pushNotification(context, NotifyId.UPDATE_DATA, "XSĐB", content);
                NewBridge.notify(context, jackpotList);
            }
        } else {
            String content = "Trạng thái cập nhật XSĐB ("
                    + jackpotSyncState.name + " vào lúc " + TimeBase.CURRENT().showHHMM() + ")";
            NotificationBase.pushNotification(context, NotifyId.UPDATE_DATA, "XSĐB", content);
        }
    }

}
