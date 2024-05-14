package com.example.couple.View.BridgeHistory;

import com.example.couple.Model.Origin.Jackpot;
import com.example.couple.Model.Time.TimeBase;

import java.util.List;

public interface SexagenaryCycleView {
    void showMessage(String message);
    void updateSuccess();
    void showSexagenaryCycle(List<TimeBase> cycleList, List<Jackpot> jackpotList);
}
