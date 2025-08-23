package com.example.couple.View.JackpotStatistics;

import com.example.couple.Model.Bridge.NumberSet.NumberSetHistory;
import com.example.couple.Model.Origin.Jackpot;
import com.example.couple.Model.Statistics.EventFrequency;
import com.example.couple.Model.Statistics.EventFrequencyType;

import java.util.List;
import java.util.Map;

public interface JackpotThisYearView {
    void showMessage(String message);
    void showReserveJackpotListThisYear(List<Jackpot> jackpotList);
    void showEventFrequency(Map<EventFrequencyType, EventFrequency> eventFrequencyMap);
    void showSameDoubleInNearestTime(List<NumberSetHistory> doubleHistories, int jackpotSize);
    void showHeadAndTailInNearestTime(List<NumberSetHistory> headTailHistories);
}
