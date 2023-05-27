package com.example.couple.View.JackpotStatistics;

import com.example.couple.Model.Display.NearestTime;
import com.example.couple.Model.Origin.Jackpot;

import java.util.List;

public interface JackpotThisYearView {
    void ShowError(String message);
    void ShowReserveJackpotListThisYear(List<Jackpot> jackpotList);
    void ShowSameDoubleAndDayNumberTotal(int sameDoubleTotal, int numberOfDaysTotal);
    void ShowSameDoubleInNearestTime(List<NearestTime> nearestTimeList);
    void ShowHeadAndTailInNearestTime(List<NearestTime> nearestTimeList);


}
