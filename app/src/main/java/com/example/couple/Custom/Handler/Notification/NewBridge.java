package com.example.couple.Custom.Handler.Notification;

import android.content.Context;

import com.example.couple.Base.Handler.NotificationBase;
import com.example.couple.Custom.Const.NotifyId;
import com.example.couple.Custom.Handler.Bridge.CycleBridgeHandler;
import com.example.couple.Custom.Handler.History.HistoryHandler;
import com.example.couple.Model.Bridge.Couple.BranchInTwoDaysBridge;
import com.example.couple.Model.History.NumberSetHistory;
import com.example.couple.Model.Origin.Jackpot;

import java.util.List;
import java.util.stream.Collectors;

public class NewBridge {
    public static void notify(Context context, List<Jackpot> jackpotList) {
        BranchInTwoDaysBridge bridge = CycleBridgeHandler.getBranchInTwoDaysBridge(jackpotList, 0);
        List<NumberSetHistory> numberSetHistories = HistoryHandler.getNumberSetsHistoryType2(jackpotList);
        if (bridge.getRunningTimes() != 0 || !numberSetHistories.isEmpty()) {
            String title = "Có cầu mới";
            String history = "Cầu gan: \n" + numberSetHistories.stream()
                    .map(NumberSetHistory::show).collect(Collectors.joining("\n"));
            String content = history +
                    "\nCầu can chi trong 2 ngày đã chạy được " + bridge.getRunningTimes() + " lần.";
            NotificationBase.pushNotification(context, NotifyId.SHOW_NEW_BRIDGE, title, content.trim());
        }
    }
}
