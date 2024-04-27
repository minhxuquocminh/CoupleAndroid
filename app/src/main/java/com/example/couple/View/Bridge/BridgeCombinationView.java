package com.example.couple.View.Bridge;

import com.example.couple.Model.Bridge.CombineBridge;
import com.example.couple.Model.Origin.Jackpot;
import com.example.couple.Model.Origin.Lottery;
import com.example.couple.Model.Time.TimeBase;

import java.util.List;

public interface BridgeCombinationView {
    void showMessage(String message);
    void showAllData(List<Jackpot> allJackpotList, List<Jackpot> jackpotList,
                     List<Lottery> lotteryList, TimeBase timeBaseNextDay);
    void showAllBridgeToday(CombineBridge combineBridge);
    void showCombineBridgeList(List<CombineBridge> combineBridges);
}
