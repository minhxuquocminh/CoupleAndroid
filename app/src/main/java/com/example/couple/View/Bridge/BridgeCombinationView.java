package com.example.couple.View.Bridge;

import com.example.couple.Model.BridgeCouple.CombineBridge;
import com.example.couple.Model.BridgeCouple.MappingBridge;
import com.example.couple.Model.BridgeCouple.PeriodBridge;
import com.example.couple.Model.BridgeCouple.ShadowMappingBridge;
import com.example.couple.Model.BridgeCouple.SpecialSet;
import com.example.couple.Model.BridgeSingle.CombineTouchBridge;
import com.example.couple.Model.BridgeSingle.ShadowTouchBridge;
import com.example.couple.Model.Origin.Jackpot;
import com.example.couple.Model.Origin.Lottery;

import java.util.List;

public interface BridgeCombinationView {
    void ShowError(String message);
    void ShowLotteryAndJackpotList(List<Jackpot> jackpotList, List<Lottery> lotteryList);
    void ShowAllBridgeToday(CombineBridge combineBridge);
    void ShowCombineBridgeList(List<CombineBridge> combineBridges);
    void ShowTouchBridgeList(List<CombineTouchBridge> combineTouchBridges);
    void ShowShadowTouchBridgeList(List<ShadowTouchBridge> touchBridges);
    void ShowPeriodBridgeList(List<PeriodBridge> periodBridges);
    void ShowMappingBridgeList(List<MappingBridge> mappingBridgeList);
    void ShowShadowMappingBridgeList(List<ShadowMappingBridge> shadowMappingBridgeList);
    void ShowSet(List<Integer> bigDoubleSet);
    void ShowBigDoubleSet(List<SpecialSet> bigDoubleSets);
    void ShowDoubleSet(List<SpecialSet> doubleSets);
    void ShowNearDoubleSet(List<SpecialSet> nearDoubleSets);
}
