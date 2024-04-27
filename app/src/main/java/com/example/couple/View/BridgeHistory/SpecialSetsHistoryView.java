package com.example.couple.View.BridgeHistory;

import com.example.couple.Model.Display.SpecialSetHistory;
import com.example.couple.Model.Origin.Jackpot;
import com.example.couple.Model.Time.TimeBase;

import java.util.List;

public interface SpecialSetsHistoryView {
    void showMessage(String message);
    void showJackpotListAndTimeBaseData(List<Jackpot> allJackpotList,
                                        List<Jackpot> jackpotList, TimeBase timeBaseNextDay);
    void showSpecialSetsHistory(List<SpecialSetHistory> historyList);
}
