package com.example.couple.View.Jackpot;

import com.example.couple.Model.Statistics.JackpotNextDay;

import java.util.List;

public interface JackpotNextDayView {
    void showMessage(String message);
    void showNumberOfYears(int numberOfYears);
    void showJackpotNextDay(List<JackpotNextDay> jackpotNextDayList);
    void showRequestLoadMoreData(int startYear_file, int endYear_file);

}
