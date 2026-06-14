package com.example.couple.Custom.Handler.Notification;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.example.couple.Custom.Handler.JackpotHandler;
import com.example.couple.Custom.Handler.LotteryHandler;
import com.example.couple.Model.DateTime.Date.DateBase;
import com.example.couple.Model.Origin.Jackpot;

import java.util.List;

public class TodayBridgeAlarm extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        NotificationSettingsHandler.applyBridgeNotificationAlarm(context);
        if (!NotificationSettingsHandler.isAppNotificationEnabled(context)
                || !NotificationSettingsHandler.isBridgeNotificationEnabled(context)) {
            return;
        }

        DateBase yesterday = DateBase.today().addDays(-1);
        DateBase lastJackpotDate = JackpotHandler.getLastDate(context);
        if (lastJackpotDate.isEmpty() || !lastJackpotDate.equals(yesterday)) return;

        DateBase lastLotteryDate = LotteryHandler.getLastDate(context);
        if (!lastLotteryDate.isEmpty() && !lastLotteryDate.equals(yesterday)) return;

        List<Jackpot> jackpotList = JackpotHandler.getJackpotListByDays(context, 99);
        if (jackpotList.isEmpty()) return;

        NewBridge.notify(context, jackpotList);
    }
}
