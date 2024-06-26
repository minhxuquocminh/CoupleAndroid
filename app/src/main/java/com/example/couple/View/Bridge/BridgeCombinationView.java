package com.example.couple.View.Bridge;

import com.example.couple.Model.Bridge.Bridge;
import com.example.couple.Model.Bridge.BridgeType;
import com.example.couple.Model.Bridge.CombineBridge;
import com.example.couple.Model.Origin.Jackpot;
import com.example.couple.Model.Origin.Lottery;

import java.util.List;
import java.util.Map;

public interface BridgeCombinationView {
    void showMessage(String message);
    void showAllData(List<Jackpot> jackpotList, List<Lottery> lotteryList);
    void showAllBridgeToday(Map<BridgeType, Bridge> bridgeMap);
    void showCombineBridgeList(List<CombineBridge> combineBridges);
}
