package com.example.couple.Custom.Handler;

import android.content.Context;

import com.example.couple.Base.Handler.IOFileBase;
import com.example.couple.Custom.Const.FileName;
import com.example.couple.Model.DateTime.Date.Cycle.Cycle;
import com.example.couple.Model.DateTime.Date.DateBase;
import com.example.couple.Model.DateTime.Date.DateCycle;
import com.example.couple.Model.DateTime.Date.DateData;
import com.example.couple.Model.DateTime.Date.DateLunar;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class DateHandler {

    /**
     * date no cycle
     */

    public static boolean updateDate(Context context) {
        try {
            String dateData = Api.getDateFromInternet(context);
            if (dateData.isEmpty()) {
                return false;
            }
            IOFileBase.saveDataToFile(context, FileName.TIME, dateData, 0);
            return true;
        } catch (ExecutionException | InterruptedException e) {
            return false;
        }
    }

    public static String getDate(Context context) {
        String data = IOFileBase.readDataFromFile(context, FileName.TIME);
        String time = "Lỗi cập nhật thời gian!";
        try {
            String[] sub = data.split("===");
            String calendarWeek = sub[0];

            String calendarDay = sub[1];
            String calendarMonth = sub[2];
            String lunarDay = sub[3];

            String[] elements = lunarDay.split(",");
            String day = elements[1].trim().split(" ")[1].trim();
            String month = elements[2].trim().split(" ")[1].trim();

            time = calendarWeek + ", Ngày " + calendarDay + " - " + calendarMonth
                    + "\n (Ngày " + day + "/" + month + " - Âm lịch)";
        } catch (Exception ignored) {

        }
        return time;
    }

    public static DateBase getDateBase(Context context) {
        String timeData = IOFileBase.readDataFromFile(context, FileName.TIME);
        if (timeData.isEmpty()) return DateBase.getEmpty();
        String[] sub = timeData.split("===");
        int calendarDay = Integer.parseInt(sub[1]);
        String[] monthData = sub[2].split(" ");
        int calendarMonth = Integer.parseInt(monthData[1]);
        int calendarYear = Integer.parseInt(monthData[4]);
        return new DateBase(calendarDay, calendarMonth, calendarYear);
    }

    /**
     * date with cycle
     */

    public static boolean updateAllDateData(Context context) {
        boolean checkNextDay = updateDateData(context,
                DateBase.TO_DAY().addDays(1), FileName.CYCLE_NEXT_DAY);
        if (!checkNextDay) return false;
        boolean checkToday = updateDateData(context, DateBase.TO_DAY(), FileName.CYCLE_TODAY);
        if (!checkToday) return false;
        DateData today = getDateData(context, FileName.CYCLE_TODAY);
        DateBase previousMonth = today.getDateBase().addDays(-today.getDateLunar().getDay());
        boolean checkPreviousMonth =
                updateDateData(context, previousMonth, FileName.CYCLE_PREVIOUS_1);
        if (!checkPreviousMonth) return false;
        DateData dateData1 = getDateData(context, FileName.CYCLE_PREVIOUS_1);
        DateBase previousPreviousMonth = dateData1.getDateBase().addDays(-dateData1.getDateLunar().getDay());

        return updateDateData(context, previousPreviousMonth, FileName.CYCLE_PREVIOUS_2);
    }

    private static boolean updateDateData(Context context, DateBase dateBase, String fileName) {
        try {
            String data = Api.getDateDataFromInternet(context, dateBase);
            if (data.isEmpty()) return false;
            IOFileBase.saveDataToFile(context, fileName, data, 0);
            return true;
        } catch (ExecutionException | InterruptedException e) {
            return false;
        }
    }

    public static List<DateData> getAllDateData(Context context, int numberOfDays) {
        List<DateData> dateDataList = new ArrayList<>();
        DateData nextDay = getDateData(context, FileName.CYCLE_NEXT_DAY);
        dateDataList.add(nextDay);
        numberOfDays--;

        DateData today = getDateData(context, FileName.CYCLE_TODAY);
        int daysOfThisMonth = today.getDateLunar().getDay();
        int size = Math.min(numberOfDays, daysOfThisMonth);
        for (int i = 0; i < size; i++) {
            DateBase dateBase = today.getDateBase().addDays(-i);
            DateLunar dateLunar = today.getDateLunar().addDaysSameMonth(-i);
            DateCycle dateCycle = today.getDateCycle().addDaysSameMonth(-i);
            dateDataList.add(new DateData(dateBase, dateLunar, dateCycle));
        }
        if (numberOfDays <= daysOfThisMonth) {
            return dateDataList;
        }

        DateData previous = getDateData(context, FileName.CYCLE_PREVIOUS_1);
        int daysOfPreviousMonth = previous.getDateLunar().getDay();
        size = Math.min((numberOfDays - daysOfThisMonth), daysOfPreviousMonth);
        for (int i = 0; i < size; i++) {
            DateBase dateBase = previous.getDateBase().addDays(-i);
            DateLunar dateLunar = previous.getDateLunar().addDaysSameMonth(-i);
            DateCycle dateCycle = previous.getDateCycle().addDaysSameMonth(-i);
            dateDataList.add(new DateData(dateBase, dateLunar, dateCycle));
        }
        if (numberOfDays <= dateDataList.size()) {
            return dateDataList;
        }

        DateData previous2 = getDateData(context, FileName.CYCLE_PREVIOUS_2);
        int daysOfPreviousMonth2 = previous2.getDateLunar().getDay();
        size = Math.min((numberOfDays - daysOfThisMonth - daysOfPreviousMonth), daysOfPreviousMonth2);
        for (int i = 0; i < size; i++) {
            DateBase dateBase = previous2.getDateBase().addDays(-i);
            DateLunar dateLunar = previous2.getDateLunar().addDaysSameMonth(-i);
            DateCycle dateCycle = previous2.getDateCycle().addDaysSameMonth(-i);
            dateDataList.add(new DateData(dateBase, dateLunar, dateCycle));
        }
        if (numberOfDays <= dateDataList.size()) {
            return dateDataList;
        }

        // khởi tạo time base chỉ có dateBase và cycleDay, ko có cycleMonth, cycleYear
        DateBase lastDateBase = dateDataList.get(dateDataList.size() - 1).getDateBase();
        Cycle lastCycle = dateDataList.get(dateDataList.size() - 1).getDateCycle().getDay();
        for (int i = 1; i <= numberOfDays - dateDataList.size(); i++) {
            DateBase dateBase = lastDateBase.addDays(-i);
            Cycle dayCycle = lastCycle.addDays(-i);
            DateCycle dateCycle = new DateCycle(dayCycle, Cycle.getEmpty(), Cycle.getEmpty());
            dateDataList.add(new DateData(dateBase, DateLunar.getEmpty(), dateCycle));
        }

        return dateDataList;
    }

    private static DateData getDateData(Context context, String fileName) {
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

            return new DateData(dateBase, dateLunar, dateCycle);
        } catch (Exception ignored) {

        }
        return DateData.getEmpty();
    }

    public static DateData getDateDataNextDay(Context context) {
        return getDateData(context, FileName.CYCLE_NEXT_DAY);
    }

    public static DateData getDateDataToday(Context context) {
        return getDateData(context, FileName.CYCLE_TODAY);
    }

}
