package com.example.couple.Custom.Handler;

import android.content.Context;

import com.example.couple.Base.Handler.IOFileBase;
import com.example.couple.Custom.Const.FileName;
import com.example.couple.Model.Time.Cycle.Cycle;
import com.example.couple.Model.Time.DateBase;
import com.example.couple.Model.Time.DateCycle;
import com.example.couple.Model.Time.DateLunar;
import com.example.couple.Model.Time.TimeBase;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class CycleHandler {

    public static boolean updateAllSexagenaryCycle(Context context) {
        boolean checkNextDay = updateSexagenaryCycle(context,
                DateBase.TO_DAY().addDays(1), FileName.CYCLE_NEXT_DAY);
        if (!checkNextDay) return false;
        boolean checkToday = updateSexagenaryCycle(context, DateBase.TO_DAY(), FileName.CYCLE_TODAY);
        if (!checkToday) return false;
        TimeBase today = getSexagenaryCycle(context, FileName.CYCLE_TODAY);
        DateBase previousMonth = today.getDateBase().addDays(-today.getDateLunar().getDay());
        boolean checkPreviousMonth =
                updateSexagenaryCycle(context, previousMonth, FileName.CYCLE_PREVIOUS_1);
        if (!checkPreviousMonth) return false;
        TimeBase timeBase1 = getSexagenaryCycle(context, FileName.CYCLE_PREVIOUS_1);
        DateBase previousPreviousMonth = timeBase1.getDateBase().addDays(-timeBase1.getDateLunar().getDay());

        return updateSexagenaryCycle(context, previousPreviousMonth, FileName.CYCLE_PREVIOUS_2);
    }

    private static boolean updateSexagenaryCycle(Context context, DateBase dateBase, String fileName) {
        try {
            String data = Api.getSexagenaryCycleByDay(context, dateBase);
            if (data.isEmpty()) return false;
            IOFileBase.saveDataToFile(context, fileName, data, 0);
            return true;
        } catch (ExecutionException | InterruptedException e) {
            return false;
        }
    }

    public static List<TimeBase> getAllSexagenaryCycle(Context context, int numberOfDays) {
        List<TimeBase> timeBaseList = new ArrayList<>();
        TimeBase nextDay = getSexagenaryCycle(context, FileName.CYCLE_NEXT_DAY);
        timeBaseList.add(nextDay);
        numberOfDays--;

        TimeBase today = getSexagenaryCycle(context, FileName.CYCLE_TODAY);
        int daysOfThisMonth = today.getDateLunar().getDay();
        int size = Math.min(numberOfDays, daysOfThisMonth);
        for (int i = 0; i < size; i++) {
            DateBase dateBase = today.getDateBase().addDays(-i);
            DateLunar dateLunar = today.getDateLunar().addDaysSameMonth(-i);
            DateCycle dateCycle = today.getDateCycle().addDaysSameMonth(-i);
            timeBaseList.add(new TimeBase(dateBase, dateLunar, dateCycle));
        }
        if (numberOfDays <= daysOfThisMonth) {
            return timeBaseList;
        }

        TimeBase previous = getSexagenaryCycle(context, FileName.CYCLE_PREVIOUS_1);
        int daysOfPreviousMonth = previous.getDateLunar().getDay();
        size = Math.min((numberOfDays - daysOfThisMonth), daysOfPreviousMonth);
        for (int i = 0; i < size; i++) {
            DateBase dateBase = previous.getDateBase().addDays(-i);
            DateLunar dateLunar = previous.getDateLunar().addDaysSameMonth(-i);
            DateCycle dateCycle = previous.getDateCycle().addDaysSameMonth(-i);
            timeBaseList.add(new TimeBase(dateBase, dateLunar, dateCycle));
        }
        if (numberOfDays <= timeBaseList.size()) {
            return timeBaseList;
        }

        TimeBase previous2 = getSexagenaryCycle(context, FileName.CYCLE_PREVIOUS_2);
        int daysOfPreviousMonth2 = previous2.getDateLunar().getDay();
        size = Math.min((numberOfDays - daysOfThisMonth - daysOfPreviousMonth), daysOfPreviousMonth2);
        for (int i = 0; i < size; i++) {
            DateBase dateBase = previous2.getDateBase().addDays(-i);
            DateLunar dateLunar = previous2.getDateLunar().addDaysSameMonth(-i);
            DateCycle dateCycle = previous2.getDateCycle().addDaysSameMonth(-i);
            timeBaseList.add(new TimeBase(dateBase, dateLunar, dateCycle));
        }
        if (numberOfDays <= timeBaseList.size()) {
            return timeBaseList;
        }

        // khởi tạo time base chỉ có dateBase và cycleDay, ko có cycleMonth, cycleYear
        DateBase lastDateBase = timeBaseList.get(timeBaseList.size() - 1).getDateBase();
        Cycle lastCycle = timeBaseList.get(timeBaseList.size() - 1).getDateCycle().getDay();
        for (int i = 1; i <= numberOfDays - timeBaseList.size(); i++) {
            DateBase dateBase = lastDateBase.addDays(-i);
            Cycle dayCycle = lastCycle.addDays(-i);
            DateCycle dateCycle = new DateCycle(dayCycle, Cycle.getEmpty(), Cycle.getEmpty());
            timeBaseList.add(new TimeBase(dateBase, DateLunar.getEmpty(), dateCycle));
        }

        return timeBaseList;
    }

    private static TimeBase getSexagenaryCycle(Context context, String fileName) {
        String data = IOFileBase.readDataFromFile(context, fileName);
        try {
            String[] arr = data.split("Âm lịch:");
            String[] calendarArr = arr[0].trim().split(" ");
            String[] calendarArr1 = calendarArr[calendarArr.length - 1].trim().split("/");
            int day = Integer.parseInt(calendarArr1[0].trim());
            int month = Integer.parseInt(calendarArr1[1].trim());
            int year = Integer.parseInt(calendarArr1[2].trim());
            DateBase dateBase = new DateBase(day, month, year);

            String[] lunnarCalendarArr = arr[1].trim().split(" ")[1].trim().split("/");
            int lunnarDay = Integer.parseInt(lunnarCalendarArr[0].trim());
            int lunnarMonth = Integer.parseInt(lunnarCalendarArr[1].trim());
            int lunnarYear = Integer.parseInt(lunnarCalendarArr[2].trim());
            DateLunar dateLunar = new DateLunar(lunnarDay, lunnarMonth, lunnarYear);

            String[] cycleArr = arr[1].trim().split("Hành")[0].trim().split(",");
            String cycleDay = cycleArr[0].trim().split("Tức ngày")[1].trim();
            String cycleMonth = cycleArr[1].trim().split("tháng")[1].trim();
            String cycleYear = cycleArr[2].trim().split("năm")[1].trim();
            DateCycle dateCycle = new DateCycle(Cycle.getByName(cycleDay),
                    Cycle.getByName(cycleMonth), Cycle.getByName(cycleYear));

            return new TimeBase(dateBase, dateLunar, dateCycle);
        } catch (Exception ignored) {

        }
        return TimeBase.getEmpty();
    }

    public static TimeBase getTimeBaseNextDay(Context context) {
        return getSexagenaryCycle(context, FileName.CYCLE_NEXT_DAY);
    }

    public static TimeBase getTimeBaseToday(Context context) {
        return getSexagenaryCycle(context, FileName.CYCLE_TODAY);
    }

}
