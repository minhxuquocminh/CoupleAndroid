package com.example.couple.Custom.Handler;

import com.example.couple.Model.BridgeCouple.CombineBridge;
import com.example.couple.Model.BridgeCouple.MappingBridge;
import com.example.couple.Model.BridgeCouple.PeriodBridge;
import com.example.couple.Model.BridgeCouple.ShadowMappingBridge;
import com.example.couple.Model.BridgeCouple.SpecialSet;
import com.example.couple.Model.BridgeSingle.ConnectedBridge;
import com.example.couple.Model.BridgeSingle.ShadowTouchBridge;
import com.example.couple.Model.Origin.Jackpot;
import com.example.couple.Model.Support.JackpotHistory;

import java.util.List;

public class CombineBridgeHandler {

    public static CombineBridge GetCombineBridgeLevel1(List<Jackpot> jackpotList, int dayNumberBefore) {
        ShadowTouchBridge shadowTouchBridge = JackpotBridgeHandler
                .GetShadowTouchBridge(jackpotList, dayNumberBefore);
        MappingBridge mappingBridge = JackpotBridgeHandler.GetMappingBridge(jackpotList, dayNumberBefore);
        Jackpot jackpot = dayNumberBefore == 0 ?
                Jackpot.getEmptyJackpot() : jackpotList.get(dayNumberBefore - 1);
        return new CombineBridge(shadowTouchBridge, mappingBridge, new ShadowMappingBridge(),
                PeriodBridge.getEmpty(), ConnectedBridge.getEmpty(), ShadowTouchBridge.getEmpty(),
                ShadowTouchBridge.getEmpty(), SpecialSet.getEmpty(), new JackpotHistory(dayNumberBefore, jackpot));
    }

    public static CombineBridge GetCombineBridgeLevel2(List<Jackpot> jackpotList, int dayNumberBefore) {
        ShadowTouchBridge shadowTouchBridge = JackpotBridgeHandler
                .GetShadowTouchBridge(jackpotList, dayNumberBefore);
        MappingBridge mappingBridge = JackpotBridgeHandler.GetMappingBridge(jackpotList, dayNumberBefore);
        ShadowMappingBridge shadowMappingBridge =
                JackpotBridgeHandler.GetShadowMappingBridge(jackpotList, dayNumberBefore);
        Jackpot jackpot = dayNumberBefore == 0 ?
                Jackpot.getEmptyJackpot() : jackpotList.get(dayNumberBefore - 1);
        return new CombineBridge(shadowTouchBridge, mappingBridge, shadowMappingBridge,
                PeriodBridge.getEmpty(), ConnectedBridge.getEmpty(), ShadowTouchBridge.getEmpty(),
                ShadowTouchBridge.getEmpty(), SpecialSet.getEmpty(), new JackpotHistory(dayNumberBefore, jackpot));
    }

    public static CombineBridge GetCombineBridgeLevel3(List<Jackpot> jackpotList, int dayNumberBefore) {
        ShadowTouchBridge shadowTouchBridge = JackpotBridgeHandler
                .GetShadowTouchBridge(jackpotList, dayNumberBefore);
        MappingBridge mappingBridge = JackpotBridgeHandler.GetMappingBridge(jackpotList, dayNumberBefore);
        ShadowMappingBridge shadowMappingBridge =
                JackpotBridgeHandler.GetShadowMappingBridge(jackpotList, dayNumberBefore);
        PeriodBridge periodBridge = JackpotBridgeHandler.GetPeriodBridge(jackpotList, dayNumberBefore);
        Jackpot jackpot = dayNumberBefore == 0 ?
                Jackpot.getEmptyJackpot() : jackpotList.get(dayNumberBefore - 1);
        return new CombineBridge(shadowTouchBridge, mappingBridge, shadowMappingBridge,
                periodBridge, ConnectedBridge.getEmpty(), ShadowTouchBridge.getEmpty(),
                ShadowTouchBridge.getEmpty(), SpecialSet.getEmpty(), new JackpotHistory(dayNumberBefore, jackpot));
    }

}
