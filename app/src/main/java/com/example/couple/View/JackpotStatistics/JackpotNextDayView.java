package com.example.couple.View.JackpotStatistics;

import com.example.couple.Model.Display.JackpotNextDay;

import java.util.List;

public interface JackpotNextDayView {
    void ShowError(String message);
    void ShowNumberOfYears(int numberOfYears);
    void ShowJackpotNextDay(List<JackpotNextDay> jackpotNextDayList);
    void ShowRequestLoadMoreData(int startYear_file, int endYear_file);

}
