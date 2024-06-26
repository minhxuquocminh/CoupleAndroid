package com.example.couple.Custom.Handler;

import android.content.Context;

import com.example.couple.Base.Handler.IOFileBase;
import com.example.couple.Custom.Const.FileName;
import com.example.couple.Model.DateTime.Date.DateBase;

public class CheckUpdate {

    public static boolean checkUpdateTime(Context context) {
        DateBase dateBase = DateHandler.getDateBase(context);
        if (dateBase.isEmpty()) return true;
        return !dateBase.isToday();
    }

    public static boolean checkUpdateJackpot(Context context) {
        String lastDateStr = IOFileBase.readDataFromFile(context, FileName.JACKPOT_LAST_DATE);
        DateBase lastDate = DateBase.fromString(lastDateStr, "-");
        if (lastDate.isEmpty()) return true;
        return !lastDate.isToday();
    }

    public static boolean checkUpdateLottery(Context context) {
        String lastDateStr = IOFileBase.readDataFromFile(context, FileName.LOTTERY_LAST_DATE);
        DateBase lastDate = DateBase.fromString(lastDateStr, "-");
        if (lastDate.isEmpty()) return true;
        return !lastDate.isToday();
    }

    public static boolean checkUpdateCycle(Context context) {
        DateBase dateBase = DateHandler.getDateDataToday(context).getDateBase();
        if (dateBase.isEmpty()) return true;
        return !dateBase.isToday();
    }

//    public static boolean checkDataSync(Context context) {
//        Jackpot lastJackpot = JackpotHandler.getLastJackpot(context);
//        Lottery lastLottery = LotteryHandler.getLastLottery(context);
//        if (lastJackpot.isEmpty() || lastLottery.isEmpty()) return false;
//        return lastJackpot.getDateBase().equals(lastLottery.getDateBase());
//    }
}
