package com.example.couple.Custom.Handler;

import android.content.Context;

import com.example.couple.Base.Handler.IOFileBase;
import com.example.couple.Custom.Const.FileName;
import com.example.couple.Custom.Const.TimeInfo;
import com.example.couple.Model.Origin.Jackpot;
import com.example.couple.Model.Origin.Lottery;
import com.example.couple.Model.Time.TimeBase;

import java.util.List;

public class CheckUpdate {
    public static boolean checkUpdateTime(Context context) {
        String data = IOFileBase.readDataFromFile(context, FileName.TIME);
        if (data.equals("")) return true;
        String[] sub = data.split("===");
        int calendarDay = Integer.parseInt(sub[1]);
        String[] monthData = sub[2].split(" ");
        int calendarMonth = Integer.parseInt(monthData[1]);
        int calendarYear = Integer.parseInt(monthData[4]);
        return !(TimeInfo.CURRENT_DAY == calendarDay && TimeInfo.CURRENT_MONTH == calendarMonth
                && TimeInfo.CURRENT_YEAR == calendarYear);

    }

    public static boolean checkUpdateCycle(Context context) {
        List<TimeBase> timeBaseList = TimeHandler.getAllSexagenaryCycle(context, 1);
        if (timeBaseList.isEmpty()) return true;
        List<Jackpot> jackpotList = JackpotHandler.GetReserveJackpotListFromFile(context, 1);
        if (jackpotList.isEmpty()) return true;
        return !jackpotList.get(0).getDateBase().plusDays(1).equals(timeBaseList.get(0).getDateBase());
    }

    public static boolean checkUpdateJackpot(Context context) {
        List<Jackpot> jackpotList = JackpotHandler.GetReserveJackpotListFromFile(context, 1);
        if (jackpotList.isEmpty()) return true;
        return !jackpotList.get(0).getDateBase().isToday();
    }

    public static boolean checkUpdateLottery(Context context) {
        List<Lottery> lotteries = LotteryHandler.getLotteryListFromFile(context, 1);
        if (lotteries.isEmpty()) return true;
        return !lotteries.get(0).getDateBase().isToday();
    }

    public static boolean checkDataSync(Context context) {
        List<Jackpot> jackpotList = JackpotHandler.GetReserveJackpotListFromFile(context, 1);
        List<Lottery> lotteries = LotteryHandler.getLotteryListFromFile(context, 1);
        if (jackpotList.isEmpty() || lotteries.isEmpty()) return false;
        return jackpotList.get(0).getDateBase().equals(lotteries.get(0).getDateBase());
    }

}
