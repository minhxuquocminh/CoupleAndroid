package com.example.couple.Custom.Handler.Sync;

import android.content.Context;

import com.example.couple.Custom.Handler.DateHandler;
import com.example.couple.Custom.Handler.JackpotHandler;
import com.example.couple.Custom.Handler.LotteryHandler;
import com.example.couple.Model.DateTime.Date.DateBase;

public class CheckSync {

    public static boolean isSyncDate(Context context) {
        DateBase dateBase = DateHandler.getDateBase(context);
        if (dateBase.isEmpty()) return false;
        return dateBase.isToday();
    }

    public static boolean isSyncJackpot(Context context) {
        DateBase lastDate = JackpotHandler.getLastDate(context);
        if (lastDate.isEmpty()) return false;
        return lastDate.isToday();
    }

    public static boolean isSyncLottery(Context context) {
        DateBase lastDate = LotteryHandler.getLastDate(context);
        if (lastDate.isEmpty()) return false;
        return lastDate.isToday();
    }

    public static boolean isSyncDateData(Context context) {
        DateBase dateBase = DateHandler.getDateDataToday(context).getDateBase();
        if (dateBase.isEmpty()) return false;
        return dateBase.isToday();
    }

}
