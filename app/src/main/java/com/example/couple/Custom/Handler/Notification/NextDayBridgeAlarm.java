package com.example.couple.Custom.Handler.Notification;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.example.couple.Custom.Const.Const;
import com.example.couple.Custom.Handler.JackpotHandler;
import com.example.couple.Model.DateTime.Date.DateBase;
import com.example.couple.Model.Origin.Jackpot;

import java.util.List;

public class NextDayBridgeAlarm extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        NotificationSettingsHandler.applyBridgeNotificationAlarm(context);
        if (!NotificationSettingsHandler.isAppNotificationEnabled(context)
                || !NotificationSettingsHandler.isBridgeNotificationEnabled(context)) {
            return;
        }

        DateBase lastJackpotDate = JackpotHandler.getLastDate(context);
        if (!canNotifyFromLastJackpotDate(lastJackpotDate)) return;

        List<Jackpot> jackpotList = JackpotHandler.getJackpotListByDays(context, Const.DAY_NUMBER_TO_GET_JACKPOT);
        if (jackpotList.isEmpty()) return;

        NewBridge.notify(context, jackpotList);
    }

    private boolean canNotifyFromLastJackpotDate(DateBase lastJackpotDate) {
        if (lastJackpotDate.isEmpty()) return false;

        DateBase today = DateBase.today();
        return lastJackpotDate.equals(today) || lastJackpotDate.equals(today.addDays(-1));
    }
}
