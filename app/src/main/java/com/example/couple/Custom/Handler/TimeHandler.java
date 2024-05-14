package com.example.couple.Custom.Handler;

import android.content.Context;

import com.example.couple.Base.Handler.IOFileBase;
import com.example.couple.Custom.Const.FileName;
import com.example.couple.Model.Time.DateBase;

import java.util.concurrent.ExecutionException;

public class TimeHandler {

    public static boolean updateTime(Context context) {
        try {
            String timeData = Api.getTimeDataFromInternet(context);
            if (timeData.isEmpty()) {
                return false;
            }
            IOFileBase.saveDataToFile(context, FileName.TIME, timeData, 0);
            return true;
        } catch (ExecutionException | InterruptedException e) {
            return false;
        }
    }

    public static String getTimeData(Context context) {
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

}
