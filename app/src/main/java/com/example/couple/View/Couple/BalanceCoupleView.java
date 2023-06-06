package com.example.couple.View.Couple;

import com.example.couple.Model.Origin.Jackpot;
import com.example.couple.Model.Support.History;

import java.util.List;

public interface BalanceCoupleView {
    void ShowError(String message);
    void ShowJackpotData(List<Jackpot> jackpotList);
    void ShowTableOfBalanceCouple(List<Jackpot> jackpotList, int numberOfDays);
    void ShowPeriodHistory(List<History> historyList);
    void ShowTest(List<Integer> touchs);
}
