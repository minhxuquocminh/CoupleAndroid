package com.example.couple.Custom.Handler.Sync;

import android.content.Context;

import com.example.couple.Base.Handler.IOFileBase;
import com.example.couple.Custom.Const.FileName;
import com.example.couple.Custom.Handler.DateHandler;
import com.example.couple.Model.DateTime.Date.DateBase;

public class CheckSync {

    public static boolean isSyncDate(Context context) {
        DateBase dateBase = DateHandler.getDateBase(context);
        if (dateBase.isEmpty()) return true;
        return dateBase.isToday();
    }

    public static boolean isSyncJackpot(Context context) {
        String lastDateStr = IOFileBase.readDataFromFile(context, FileName.JACKPOT_LAST_DATE);
        DateBase lastDate = DateBase.fromString(lastDateStr, "-");
        if (lastDate.isEmpty()) return true;
        return lastDate.isToday();
    }

    public static boolean isSyncLottery(Context context) {
        String lastDateStr = IOFileBase.readDataFromFile(context, FileName.LOTTERY_LAST_DATE);
        DateBase lastDate = DateBase.fromString(lastDateStr, "-");
        if (lastDate.isEmpty()) return true;
        return lastDate.isToday();
    }

    public static boolean isSyncDateData(Context context) {
        DateBase dateBase = DateHandler.getDateDataToday(context).getDateBase();
        if (dateBase.isEmpty()) return true;
        return dateBase.isToday();
    }

}
