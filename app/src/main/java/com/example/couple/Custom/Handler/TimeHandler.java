package com.example.couple.Custom.Handler;

import android.content.Context;

import com.example.couple.Base.Handler.DateBase;
import com.example.couple.Base.Handler.IOFileBase;
import com.example.couple.Custom.Const.Const;
import com.example.couple.Model.Cycle.Cycle;
import com.example.couple.Model.Support.DateCycle;
import com.example.couple.Model.Support.DateLunar;
import com.example.couple.Model.Support.TimeBase;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class TimeHandler {

    public static boolean hasSyncedCycleToday(Context context) {
        TimeBase today = getSexagenaryCycle(context, Const.CYCLE_TODAY_FILE_NAME);
        return !today.isEmpty() && today.getDateBase().equals(DateBase.TO_DAY());
    }

    public static List<TimeBase> getAllSexagenaryCycle(Context context, int numberOfDays) {
        List<TimeBase> timeBaseList = new ArrayList<>();
        if (!CheckUpdate.checkUpdateJackpot(context)) {
            TimeBase tomorrow = getSexagenaryCycle(context, Const.CYCLE_TOMORROW_FILE_NAME);
            timeBaseList.add(tomorrow);
        }

        TimeBase today = getSexagenaryCycle(context, Const.CYCLE_TODAY_FILE_NAME);
        int daysOfThisMonth = today.getDateLunar().getDay();
        int size = numberOfDays < daysOfThisMonth ? numberOfDays : daysOfThisMonth;
        for (int i = 0; i < size; i++) {
            DateBase dateBase = today.getDateBase().plusDays(-i);
            DateLunar dateLunar = today.getDateLunar().plusDaysSameMonth(-i);
            DateCycle dateCycle = today.getDateCycle().plusDaysSameMonth(-i);
            timeBaseList.add(new TimeBase(dateBase, dateLunar, dateCycle));
        }
        if (numberOfDays < daysOfThisMonth) {
            return timeBaseList;
        }

        TimeBase previous = getSexagenaryCycle(context, Const.CYCLE_1_FILE_NAME);
        int daysOfPreviousMonth = previous.getDateLunar().getDay();
        size = (numberOfDays - daysOfThisMonth) < daysOfPreviousMonth ?
                (numberOfDays - daysOfThisMonth) : daysOfPreviousMonth;
        for (int i = 0; i < size; i++) {
            DateBase dateBase = previous.getDateBase().plusDays(-i);
            DateLunar dateLunar = previous.getDateLunar().plusDaysSameMonth(-i);
            DateCycle dateCycle = previous.getDateCycle().plusDaysSameMonth(-i);
            timeBaseList.add(new TimeBase(dateBase, dateLunar, dateCycle));
        }

        TimeBase previous2 = getSexagenaryCycle(context, Const.CYCLE_2_FILE_NAME);
        int daysOfPreviousMonth2 = previous2.getDateLunar().getDay();
        if (numberOfDays - daysOfThisMonth - daysOfPreviousMonth < daysOfPreviousMonth2) {
            return timeBaseList;
        }

        size = (numberOfDays - daysOfThisMonth - daysOfPreviousMonth) < daysOfPreviousMonth2 ?
                (numberOfDays - daysOfThisMonth - daysOfPreviousMonth) : daysOfPreviousMonth2;
        for (int i = 0; i < size; i++) {
            DateBase dateBase = previous2.getDateBase().plusDays(-i);
            DateLunar dateLunar = previous2.getDateLunar().plusDaysSameMonth(-i);
            DateCycle dateCycle = previous2.getDateCycle().plusDaysSameMonth(-i);
            timeBaseList.add(new TimeBase(dateBase, dateLunar, dateCycle));
        }

        return timeBaseList;
    }

    public static boolean updateAllSexagenaryCycle(Context context) {
        boolean checkTomorrow = updateSexagenaryCycle(context,
                DateBase.TO_DAY().plusDays(1), Const.CYCLE_TOMORROW_FILE_NAME);
        if (!checkTomorrow) return false;

        boolean checkToday = updateSexagenaryCycle(context, DateBase.TO_DAY(), Const.CYCLE_TODAY_FILE_NAME);
        if (!checkToday) return false;
        TimeBase today = getSexagenaryCycle(context, Const.CYCLE_TODAY_FILE_NAME);
        DateBase previousMonth = today.getDateBase().plusDays(-today.getDateLunar().getDay());

        boolean check1 = updateSexagenaryCycle(context, previousMonth, Const.CYCLE_1_FILE_NAME);
        if (!check1) return false;
        TimeBase timeBase1 = getSexagenaryCycle(context, Const.CYCLE_1_FILE_NAME);
        DateBase previousPreviousMonth = timeBase1.getDateBase().plusDays(-timeBase1.getDateLunar().getDay());

        boolean check2 = updateSexagenaryCycle(context, previousPreviousMonth, Const.CYCLE_2_FILE_NAME);
        if (!check2) return false;

        return true;
    }

    private static boolean updateSexagenaryCycle(Context context, DateBase dateBase, String fileName) {
        try {
            String data = Api.GetSexagenaryCycleByDay(context,
                    dateBase.getDay(), dateBase.getMonth(), dateBase.getYear());
            if (data.equals("")) return false;
            IOFileBase.saveDataToFile(context, fileName, data, 0);
            return true;
        } catch (ExecutionException e) {
            return false;
        } catch (InterruptedException e) {
            return false;
        }
    }

    private static TimeBase getSexagenaryCycle(Context context, String fileName) {
        String data = IOFileBase.readDataFromFile(context, fileName);
        try {
            String arr[] = data.split("Âm lịch:");
            String calendarArr[] = arr[0].trim().split(" ");
            String calendarArr1[] = calendarArr[calendarArr.length - 1].trim().split("/");
            int day = Integer.parseInt(calendarArr1[0].trim());
            int month = Integer.parseInt(calendarArr1[1].trim());
            int year = Integer.parseInt(calendarArr1[2].trim());
            DateBase dateBase = new DateBase(day, month, year);

            String lunnarCalendarArr[] = arr[1].trim().split(" ")[1].trim().split("/");
            int lunnarDay = Integer.parseInt(lunnarCalendarArr[0].trim());
            int lunnarMonth = Integer.parseInt(lunnarCalendarArr[1].trim());
            int lunnarYear = Integer.parseInt(lunnarCalendarArr[2].trim());
            DateLunar dateLunar = new DateLunar(lunnarDay, lunnarMonth, lunnarYear);

            String cycleArr[] = arr[1].trim().split("Hành")[0].trim().split(",");
            String cycleDay = cycleArr[0].trim().split("Tức ngày")[1].trim();
            String cycleMonth = cycleArr[1].trim().split("tháng")[1].trim();
            String cycleYear = cycleArr[2].trim().split("năm")[1].trim();
            DateCycle dateCycle = new DateCycle(Cycle.getCycle(cycleDay),
                    Cycle.getCycle(cycleMonth), Cycle.getCycle(cycleYear));

            return new TimeBase(dateBase, dateLunar, dateCycle);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return TimeBase.getEmpty();
    }

}
