package com.example.couple.View.Couple;

import com.example.couple.Model.Origin.Jackpot;
import com.example.couple.Model.Support.PeriodHistory;

import java.util.List;

public interface BalanceCoupleView {
    void showMessage(String message);
    void showJackpotData(List<Jackpot> jackpotList);
    void showTableOfBalanceCouple(List<Jackpot> jackpotList, int numberOfDays);
    void showPeriodHistory(List<PeriodHistory> periodHistoryList);
    void showTest(List<Integer> touchs);
}
