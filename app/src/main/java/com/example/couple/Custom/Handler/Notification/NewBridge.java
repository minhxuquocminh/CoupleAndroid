package com.example.couple.Custom.Handler.Notification;

import android.content.Context;

import com.example.couple.Base.Handler.NotificationBase;
import com.example.couple.Custom.Const.NotifyId;
import com.example.couple.Custom.Handler.Bridge.CycleBridgeHandler;
import com.example.couple.Custom.Handler.History.HistoryHandler;
import com.example.couple.Custom.Handler.JackpotHandler;
import com.example.couple.Model.Bridge.Cycle.BranchInTwoDaysBridge;
import com.example.couple.Model.DateTime.Date.DateBase;
import com.example.couple.Model.Bridge.NumberSet.NumberSetHistory;
import com.example.couple.Model.Origin.Jackpot;

import java.util.List;
import java.util.stream.Collectors;

public class NewBridge {
    public static void notify(Context context, List<Jackpot> jackpotList) {
        DateBase nextDate = JackpotHandler.getLastDate(context).addDays(1);
        NotificationInfo notificationInfo = NotificationBase.getNotificationInfo(context, NotifyId.SHOW_NEW_BRIDGE);
        if (!notificationInfo.isEmpty()) {
            String dateStr = notificationInfo.getTitle().split("ngày")[1].trim();
            if (DateBase.fromString(dateStr, "-").equals(nextDate)) return;
        }
        BranchInTwoDaysBridge bridge = CycleBridgeHandler.getBranchInTwoDaysBridge(jackpotList, 0);
        List<NumberSetHistory> numberSetHistories = HistoryHandler.getNumberSetsHistoryType2(jackpotList);
        String title = "Có cầu mới cho ngày " + nextDate.showFullChars();
        String content = "";
        if (!numberSetHistories.isEmpty()) {
            content += "Cầu gan: " + numberSetHistories.stream()
                    .map(NumberSetHistory::showCompact).collect(Collectors.joining(", ")) + "; ";
        }

        if (bridge.getRunningTimes() != 0) {
            content += "Cầu can chi trong 2 ngày đã chạy được " + bridge.getRunningTimes() + " lần.";
        }

        if (!content.isEmpty()) {
            NotificationBase.pushNotification(context, NotifyId.SHOW_NEW_BRIDGE, title, content.trim());
        }
    }
}
