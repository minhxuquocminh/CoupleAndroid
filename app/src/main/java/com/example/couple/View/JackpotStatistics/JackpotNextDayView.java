package com.example.couple.View.JackpotStatistics;

import com.example.couple.Model.Display.JackpotNextDay;

import java.util.List;

public interface JackpotNextDayView {
    void showMessage(String message);
    void showNumberOfYears(int numberOfYears);
    void showJackpotNextDay(List<JackpotNextDay> jackpotNextDayList);
    void showRequestLoadMoreData(int startYear_file, int endYear_file);

}
