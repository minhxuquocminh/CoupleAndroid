package com.example.couple.View.JackpotStatistics;

import com.example.couple.Model.Bridge.NumberSet.NumberSetHistory;
import com.example.couple.Model.Bridge.NumberSet.NumberSetType;

import java.util.List;
import java.util.Map;

public interface JackpotThisYearView {
    void showMessage(String message);
    void showSameDoubleInNearestTime(List<NumberSetHistory> doubleHistories, int jackpotSize);
    void showHeadAndTailInNearestTime(List<NumberSetHistory> headTailHistories);

}
