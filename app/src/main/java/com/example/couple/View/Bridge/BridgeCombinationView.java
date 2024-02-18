package com.example.couple.View.Bridge;

import com.example.couple.Model.Bridge.CombineBridge;
import com.example.couple.Model.Origin.Jackpot;
import com.example.couple.Model.Origin.Lottery;
import com.example.couple.Model.Time.TimeBase;

import java.util.List;

public interface BridgeCombinationView {
    void ShowError(String message);
    void ShowAllData(List<Jackpot> allJackpotList, List<Jackpot> jackpotList,
                     List<Lottery> lotteryList, TimeBase timeBaseNextDay);
    void ShowAllBridgeToday(CombineBridge combineBridge);
    void ShowCombineBridgeList(List<CombineBridge> combineBridges);
}
