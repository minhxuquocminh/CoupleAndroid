package com.example.couple.View.Bridge;

import com.example.couple.Model.Bridge.CombineBridge;
import com.example.couple.Model.Bridge.Couple.MappingBridge;
import com.example.couple.Model.Bridge.Couple.PeriodBridge;
import com.example.couple.Model.Bridge.Couple.ShadowMappingBridge;
import com.example.couple.Model.Bridge.Couple.SpecialSet;
import com.example.couple.Model.Bridge.Single.CombineTouchBridge;
import com.example.couple.Model.Bridge.Single.ShadowTouchBridge;
import com.example.couple.Model.Origin.Jackpot;
import com.example.couple.Model.Origin.Lottery;
import com.example.couple.Model.Time.TimeBase;

import java.util.List;

public interface BridgeCombinationView {
    void ShowError(String message);
    void ShowLotteryAndJackpotAndTimeBaseList(List<Jackpot> jackpotList,
                                              List<Lottery> lotteryList, List<TimeBase> timeBaseList);
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
