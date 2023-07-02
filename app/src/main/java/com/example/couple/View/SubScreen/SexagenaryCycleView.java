package com.example.couple.View.SubScreen;

import com.example.couple.Model.Origin.Jackpot;
import com.example.couple.Model.Support.TimeBase;

import java.util.List;

public interface SexagenaryCycleView {
    void ShowError(String message);
    void ShowUpdateSuccess();
    void ShowSexagenaryCycle(List<TimeBase> cycleList, List<Jackpot> jackpotList);
}
