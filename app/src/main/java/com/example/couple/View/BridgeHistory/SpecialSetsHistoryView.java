package com.example.couple.View.BridgeHistory;

import com.example.couple.Model.Display.NumberSetHistory;
import com.example.couple.Model.Origin.Jackpot;

import java.util.List;

public interface SpecialSetsHistoryView {
    void showMessage(String message);
    void showJackpotList(List<Jackpot> jackpotList);
    void showSpecialSetsHistory(List<NumberSetHistory> historyList);
}
