package com.example.couple.Custom.Handler.Notification;

import android.content.Context;
import android.util.Base64;

import com.example.couple.Base.Handler.IOFileBase;
import com.example.couple.Base.Handler.NotificationBase;
import com.example.couple.Custom.Const.FileName;
import com.example.couple.Custom.Const.NotifyId;
import com.example.couple.Model.DateTime.Date.DateBase;
import com.example.couple.Model.Notification.BridgeNotification;
import com.example.couple.Model.Notification.NotificationBridgeData;
import com.example.couple.Model.Origin.Jackpot;
import com.example.couple.View.Notification.NewBridgeActivity;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class BridgeNotificationStorageHandler {
    private static final int MAX_STORED_NOTIFICATIONS = 20;
    private static final String FIELD_SEPARATOR = "\t";
    private static final String NUMBER_SEPARATOR = ",";

    public static boolean hasNotification(Context context, DateBase dateBase) {
        for (BridgeNotification notification : getNotifications(context)) {
            if (notification.getDateBase().equals(dateBase)) return true;
        }
        return false;
    }

    public static void saveIfAbsent(Context context, BridgeNotification notification) {
        if (notification == null || notification.isEmpty()
                || hasNotification(context, notification.getDateBase())) {
            return;
        }

        List<BridgeNotification> notifications = getNotifications(context);
        notifications.add(0, notification);
        saveNotifications(context, notifications);
    }

    public static List<BridgeNotification> getNotifications(Context context) {
        String data = IOFileBase.readDataFromFile(context, FileName.BRIDGE_NOTIFICATION);
        if (data.isEmpty()) return new ArrayList<>();

        List<BridgeNotification> notifications = new ArrayList<>();
        String[] lines = data.split("\\n");
        for (String line : lines) {
            BridgeNotification notification = parse(line);
            if (notification != null && !notification.isEmpty()) notifications.add(notification);
        }
        return notifications;
    }

    public static String show(BridgeNotification notification) {
        StringBuilder builder = new StringBuilder();
        builder.append("Có cầu mới cho ngày ")
                .append(notification.getDateBase().showFullChars());
        for (NotificationBridgeData data : notification.getBridgeDataList()) {
            builder.append("\n").append(data.showNotificationLine());
        }
        return builder.toString();
    }

    public static void notifyWinningBridges(Context context, Jackpot jackpot) {
        if (!NotificationSettingsHandler.isBridgeNotificationEnabled(context)) return;
        if (jackpot == null || jackpot.isEmptyOrInvalid()) return;

        List<BridgeNotification> notifications = getNotifications(context);
        boolean changed = false;
        List<String> winLines = new ArrayList<>();
        int couple = jackpot.getCoupleInt();

        for (BridgeNotification notification : notifications) {
            if (!notification.getDateBase().equals(jackpot.getDateBase())) continue;

            for (NotificationBridgeData data : notification.getBridgeDataList()) {
                if (!data.isWinNotified() && data.isWin(couple)) {
                    data.setWinNotified(true);
                    changed = true;
                    winLines.add("Đã ra " + data.showName() + ".");
                }
            }
        }

        if (changed) {
            saveNotifications(context, notifications);
        }

        if (!winLines.isEmpty()) {
            String title = "Cầu đã về ngày " + jackpot.getDateBase().showFullChars();
            String content = winLines.stream().distinct().collect(Collectors.joining("\n"));
            NotificationBase.pushExpandableNotification(context, NotifyId.SHOW_BRIDGE_WIN,
                    title, content, NewBridgeActivity.class);
        }
    }

    private static void saveNotifications(Context context, List<BridgeNotification> notifications) {
        String data = notifications.stream()
                .limit(MAX_STORED_NOTIFICATIONS)
                .map(BridgeNotificationStorageHandler::serialize)
                .filter(line -> !line.isEmpty())
                .collect(Collectors.joining("\n"));
        IOFileBase.saveDataToFile(context, FileName.BRIDGE_NOTIFICATION, data, 0);
    }

    private static String serialize(BridgeNotification notification) {
        return notification.getDateBase().toString("-") + FIELD_SEPARATOR
                + notification.getBridgeDataList().stream()
                .map(BridgeNotificationStorageHandler::serialize)
                .collect(Collectors.joining(";"));
    }

    private static String serialize(NotificationBridgeData data) {
        return encode(data.getBridgeName()) + "|"
                + encode(data.getSubBridgeName()) + "|"
                + encode(data.getInfo()) + "|"
                + serializeNumbers(data.getNumbers()) + "|"
                + (data.isWinNotified() ? "1" : "0");
    }

    private static BridgeNotification parse(String line) {
        String[] fields = line.split(FIELD_SEPARATOR, 2);
        if (fields.length != 2) return null;

        DateBase dateBase = DateBase.fromString(fields[0], "-");
        List<NotificationBridgeData> bridgeDataList = new ArrayList<>();
        for (String item : fields[1].split(";")) {
            NotificationBridgeData data = parseBridgeData(item);
            if (data != null) bridgeDataList.add(data);
        }
        return new BridgeNotification(dateBase, bridgeDataList);
    }

    private static NotificationBridgeData parseBridgeData(String item) {
        String[] fields = item.split("\\|", 5);
        if (fields.length != 5) return null;

        return new NotificationBridgeData(
                decode(fields[0]),
                decode(fields[1]),
                decode(fields[2]),
                parseNumbers(fields[3]),
                "1".equals(fields[4])
        );
    }

    private static String serializeNumbers(List<Integer> numbers) {
        return numbers.stream()
                .distinct()
                .map(String::valueOf)
                .collect(Collectors.joining(NUMBER_SEPARATOR));
    }

    private static List<Integer> parseNumbers(String data) {
        List<Integer> numbers = new ArrayList<>();
        if (data.isEmpty()) return numbers;

        for (String number : data.split(NUMBER_SEPARATOR)) {
            try {
                numbers.add(Integer.parseInt(number));
            } catch (NumberFormatException ignored) {
            }
        }
        return numbers;
    }

    private static String encode(String value) {
        String safeValue = value == null ? "" : value;
        return Base64.encodeToString(safeValue.getBytes(StandardCharsets.UTF_8), Base64.NO_WRAP);
    }

    private static String decode(String value) {
        return new String(Base64.decode(value, Base64.NO_WRAP), StandardCharsets.UTF_8);
    }
}
