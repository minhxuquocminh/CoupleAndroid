package com.example.couple.View.Couple;

import com.example.couple.Model.Origin.Jackpot;
import com.example.couple.Model.Support.PeriodHistory;

import java.util.List;

public interface BalanceCoupleView {
    void ShowError(String message);
    void ShowJackpotData(List<Jackpot> jackpotList);
    void ShowTableOfBalanceCouple(List<Jackpot> jackpotList, int numberOfDays);
    void ShowPeriodHistory(List<PeriodHistory> periodHistoryList);
    void ShowTest(List<Integer> touchs);
}
