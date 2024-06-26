package com.example.couple.View.BridgeHistory;

import com.example.couple.Model.Origin.Jackpot;
import com.example.couple.Model.DateTime.Date.DateData;

import java.util.List;

public interface SexagenaryCycleView {
    void showMessage(String message);
    void updateSuccess();
    void showSexagenaryCycle(List<DateData> cycleList, List<Jackpot> jackpotList);
}
