package com.example.couple.View.JackpotStatistics;

import com.example.couple.Model.Bridge.LongBeat.NearestTime;
import com.example.couple.Model.Origin.Jackpot;

import java.util.List;

public interface JackpotThisYearView {
    void showMessage(String message);
    void showReserveJackpotListThisYear(List<Jackpot> jackpotList);
    void showSameDoubleAndDayNumberTotal(int sameDoubleTotal, int numberOfDaysTotal);
    void showSameDoubleInNearestTime(List<NearestTime> nearestTimeList);
    void showHeadAndTailInNearestTime(List<NearestTime> nearestTimeList);


}
