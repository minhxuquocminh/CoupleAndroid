package com.example.couple.View.BridgeHistory;

import com.example.couple.Model.Bridge.NumberSet.NumberSetHistory;
import com.example.couple.Model.Bridge.NumberSet.NumberSetType;
import com.example.couple.Model.Origin.Jackpot;

import java.util.List;
import java.util.Map;

public interface NumberSetHistoryView {
    void showMessage(String message);
    void showJackpotList(List<Jackpot> jackpotList);
    void showSpecialSetsHistory(Map<NumberSetType, List<NumberSetHistory>> historiesByType);
}
