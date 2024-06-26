package com.example.couple.Custom.Handler.Notification;

import android.content.Context;

import com.example.couple.Base.Handler.NotificationBase;
import com.example.couple.Custom.Const.NotifyId;
import com.example.couple.Custom.Handler.Bridge.CycleBridgeHandler;
import com.example.couple.Model.Bridge.Couple.BranchInTwoDaysBridge;
import com.example.couple.Model.Origin.Jackpot;

import java.util.List;

public class NewBridge {
    public static void notify(Context context, List<Jackpot> jackpotList) {
        BranchInTwoDaysBridge bridge = CycleBridgeHandler.getBranchInTwoDaysBridge(jackpotList, 0);
        if (bridge.getRunningTimes() != 0) {
            String title = "Có cầu mới";
            String content = "Cầu can chi trong 2 ngày đã chạy được " + bridge.getRunningTimes() + " lần.";
            NotificationBase.pushNotification(context, NotifyId.SHOW_NEW_BRIDGE, title, content);
        }
    }
}
