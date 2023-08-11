package com.example.couple.View.BridgeHistory;

import com.example.couple.Model.Display.SpecialSetHistory;
import com.example.couple.Model.Origin.Jackpot;
import com.example.couple.Model.Origin.Lottery;
import com.example.couple.Model.Time.TimeBase;

import java.util.List;

public interface SpecialSetsHistoryView {
    void ShowError(String message);
    void ShowJackpotListAndTimeBaseData(List<Jackpot> jackpotList, TimeBase timeBaseNextDay);
    void ShowSpecialSetsHistory(List<SpecialSetHistory> historyList);
}
