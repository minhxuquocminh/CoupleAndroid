package com.example.couple.Custom.Handler.Notification;

import android.content.Context;

import com.example.couple.Base.Handler.NumberBase;
import com.example.couple.Base.Handler.NotificationBase;
import com.example.couple.Custom.Const.NotifyId;
import com.example.couple.Custom.Handler.Bridge.AfterDoubleBridgeHandler;
import com.example.couple.Custom.Handler.Bridge.CycleBridgeHandler;
import com.example.couple.Custom.Handler.Bridge.NumberSetBridgeHandler;
import com.example.couple.Custom.Handler.JackpotHandler;
import com.example.couple.Model.Bridge.AfterDouble.AfterDoubleCoupleBridge;
import com.example.couple.Model.Bridge.Cycle.BranchInTwoDaysBridge;
import com.example.couple.Model.Bridge.NumberSet.GeneralNumberSetBridge;
import com.example.couple.Model.Bridge.NumberSet.NumberSetHistory;
import com.example.couple.Model.DateTime.Date.DateBase;
import com.example.couple.Model.Notification.BridgeNotification;
import com.example.couple.Model.Notification.NotificationBridgeData;
import com.example.couple.Model.Origin.Jackpot;
import com.example.couple.View.Notification.NewBridgeActivity;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class NewBridge {
    private static final int MAX_NUMBER_SET_DISPLAY = 5;
    private static final int MAX_NUMBER_SET_PER_TYPE = 2;
    private static final int MAX_AFTER_DOUBLE_DISPLAY = 8;

    public static void notify(Context context, List<Jackpot> jackpotList) {
        if (!NotificationSettingsHandler.isBridgeNotificationEnabled(context)) return;

        DateBase nextDate = JackpotHandler.getLastDate(context).addDays(1);
        BridgeNotification bridgeNotification = getBridgeNotification(nextDate, jackpotList);
        if (bridgeNotification.isEmpty()) return;

        BridgeNotificationStorageHandler.saveIfAbsent(context, bridgeNotification);

        String title = "Có cầu mới cho ngày " + nextDate.showFullChars();
        String content = showNotificationContent(bridgeNotification);
        NotificationBase.pushExpandableNotification(context, NotifyId.SHOW_NEW_BRIDGE, title,
                content, NewBridgeActivity.class);
    }

    private static BridgeNotification getBridgeNotification(DateBase targetDate, List<Jackpot> jackpotList) {
        List<NotificationBridgeData> bridgeDataList = new ArrayList<>();

        for (NumberSetHistory history : getNumberSetHistories(jackpotList)) {
            String subBridgeName = history.getNumberSet().getName()
                    + " (" + history.getDayNumberBefore() + " ngày)";
            bridgeDataList.add(new NotificationBridgeData(
                    "Cầu gan",
                    subBridgeName,
                    "Cầu gan: " + subBridgeName,
                    history.getNumberSet().getNumbers()
            ));
        }

        AfterDoubleCoupleBridge afterDoubleBridge = AfterDoubleBridgeHandler.getAfterDoubleCoupleBridge(jackpotList, 0);
        if (!afterDoubleBridge.isEmpty()) {
            bridgeDataList.add(new NotificationBridgeData(
                    "Cầu sau khi ra kép",
                    "",
                    "Cầu sau khi ra kép: " + formatCouples(afterDoubleBridge.getNumbers(), MAX_AFTER_DOUBLE_DISPLAY),
                    afterDoubleBridge.getNumbers()
            ));
        }

        BranchInTwoDaysBridge cycleBridge = CycleBridgeHandler.getBranchInTwoDaysBridge(jackpotList, 0);
        if (cycleBridge.getRunningTimes() != 0) {
            String subBridgeName = "đã chạy " + cycleBridge.getRunningTimes() + " ngày liên tiếp";
            bridgeDataList.add(new NotificationBridgeData(
                    "Cầu can chi",
                    subBridgeName,
                    "Cầu can chi " + subBridgeName,
                    cycleBridge.getNumbers()
            ));
        }

        return new BridgeNotification(targetDate, bridgeDataList);
    }

    private static List<NumberSetHistory> getNumberSetHistories(List<Jackpot> jackpotList) {
        GeneralNumberSetBridge bridge = NumberSetBridgeHandler.getGeneralNumberSetBridges(jackpotList);
        List<NumberSetHistory> histories = new ArrayList<>();
        for (List<NumberSetHistory> typeHistories : bridge.getHistoriesByType().values()) {
            histories.addAll(typeHistories.stream()
                    .limit(MAX_NUMBER_SET_PER_TYPE)
                    .collect(Collectors.toList()));
        }
        return histories;
    }

    private static String formatCouples(List<Integer> numbers, int maxSize) {
        List<Integer> displayNumbers = numbers.stream()
                .limit(maxSize)
                .collect(Collectors.toList());
        String compact = NumberBase.showNumbers(displayNumbers, 2, ", ");
        int remaining = numbers.size() - maxSize;
        return remaining > 0 ? compact + "... (+" + remaining + " số)" : compact;
    }

    private static String showNotificationContent(BridgeNotification notification) {
        List<NotificationBridgeData> numberSetData = notification.getBridgeDataList().stream()
                .filter(data -> "Cầu gan".equals(data.getBridgeName()))
                .collect(Collectors.toList());

        StringBuilder content = new StringBuilder();
        if (!numberSetData.isEmpty()) {
            content.append("Cầu gan: ")
                    .append(numberSetData.stream()
                            .limit(MAX_NUMBER_SET_DISPLAY)
                            .map(NotificationBridgeData::getSubBridgeName)
                            .collect(Collectors.joining(", ")));
            int remaining = numberSetData.size() - MAX_NUMBER_SET_DISPLAY;
            if (remaining > 0) content.append("... (+").append(remaining).append(" cầu)");
            content.append("\n");
        }

        for (NotificationBridgeData data : notification.getBridgeDataList()) {
            if ("Cầu gan".equals(data.getBridgeName())) continue;
            content.append(data.showNotificationLine()).append("\n");
        }

        content.append("Gợi ý hôm nay: đang phát triển");
        return content.toString().trim();
    }
}
