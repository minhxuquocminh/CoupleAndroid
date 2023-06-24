package com.example.couple.Model.BridgeCouple;

import com.example.couple.Custom.Handler.CoupleHandler;
import com.example.couple.Base.Handler.NumberBase;
import com.example.couple.Custom.Const.Const;
import com.example.couple.Model.BridgeSingle.CombineTouchBridge;
import com.example.couple.Model.BridgeSingle.ConnectedBridge;
import com.example.couple.Model.BridgeSingle.LottoTouchBridge;
import com.example.couple.Model.BridgeSingle.ShadowTouchBridge;
import com.example.couple.Model.Support.JackpotHistory;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;

@Getter
public class CombineBridge {
    ShadowTouchBridge shadowTouchBridge;
    ConnectedBridge connectedBridge;
    LottoTouchBridge lottoTouchBridge;
    MappingBridge mappingBridge;
    ShadowMappingBridge shadowMappingBridge;
    PeriodBridge periodBridge;
    ShadowTouchBridge negativeShadowBridge;
    ShadowTouchBridge positiveShadowBridge;
    CombineTouchBridge combineTouchBridge;
    SpecialSet bigDoubleSet;

    JackpotHistory jackpotHistory;
    List<String> bridgeNames;
    List<Integer> numbers;

    public CombineBridge(ShadowTouchBridge shadowTouchBridge, ConnectedBridge connectedBridge,
                         LottoTouchBridge lottoTouchBridge, ShadowTouchBridge negativeShadowBridge,
                         ShadowTouchBridge positiveShadowBridge, MappingBridge mappingBridge,
                         ShadowMappingBridge shadowMappingBridge, PeriodBridge periodBridge,
                         CombineTouchBridge combineTouchBridge, SpecialSet bigDoubleSet,
                         JackpotHistory jackpotHistory) {
        this.shadowTouchBridge = shadowTouchBridge;
        this.connectedBridge = connectedBridge;
        this.lottoTouchBridge = lottoTouchBridge;
        this.positiveShadowBridge = positiveShadowBridge;
        this.negativeShadowBridge = negativeShadowBridge;
        this.mappingBridge = mappingBridge;
        this.shadowMappingBridge = shadowMappingBridge;
        this.periodBridge = periodBridge;
        this.combineTouchBridge = combineTouchBridge;
        this.bigDoubleSet = bigDoubleSet;
        this.jackpotHistory = jackpotHistory;
        this.bridgeNames = new ArrayList<>();
        if (!shadowTouchBridge.isEmpty()) {
            this.bridgeNames.add(Const.SHADOW_TOUCH_BRIDGE_NAME);
        }
        if (!connectedBridge.isEmpty()) {
            this.bridgeNames.add(Const.CONNECTED_BRIDGE_NAME);
        }
        if (!lottoTouchBridge.isEmpty()) {
            this.bridgeNames.add(Const.LOTTO_TOUCH_BRIDGE_NAME);
        }
        if (!positiveShadowBridge.isEmpty()) {
            this.bridgeNames.add(Const.POSITIVE_SHADOW_BRIDGE_NAME);
        }
        if (!negativeShadowBridge.isEmpty()) {
            this.bridgeNames.add(Const.NEGATIVE_SHADOW_BRIDGE_NAME);
        }
        if (!mappingBridge.isEmpty()) {
            this.bridgeNames.add(Const.MAPPING_BRIDGE_NAME);
        }
        if (!shadowMappingBridge.isEmpty()) {
            this.bridgeNames.add(Const.SHADOW_MAPPING_BRIDGE_NAME);
        }
        if (!periodBridge.isEmpty()) {
            this.bridgeNames.add(Const.PERIOD_BRIDGE_NAME);
        }
        if (!combineTouchBridge.isEmpty()) {
            this.bridgeNames.add(Const.COMBINE_TOUCH_BRIDGE_NAME);
        }
        if (!bigDoubleSet.isEmpty()) {
            this.bridgeNames.add(Const.BIG_DOUBLE_SET_NAME);
        }

        this.numbers = new ArrayList<>();
        for (String bridgeName : bridgeNames) {
            CombineInterface bridge = getBridge(bridgeName);
            numbers = NumberBase.getMatchNumbers(numbers, bridge.getNumbers());
        }
    }

    public String showBridge() {
        String show = " * " + jackpotHistory.show() + ":\n";
        for (String bridgeName : bridgeNames) {
            CombineInterface bridge = getBridge(bridgeName);
            show += bridge.showCompactBridge() + "\n";
        }
        String win = jackpotHistory.isEmpty() ? "" : (isWin() ? " (trúng)" : "(trượt)");
        show += "    => KQ tổ hợp" + win + ": " + showNumbers() + " (" + numbers.size() + " số).";
        return show.trim();
    }

    public boolean isWin() {
        return CoupleHandler.isWin(jackpotHistory, numbers);
    }

    public String showNumbers() {
        return CoupleHandler.showCoupleNumbers(numbers);
    }

    private CombineInterface getBridge(String bridgeName) {
        switch (bridgeName) {
            case Const.SHADOW_TOUCH_BRIDGE_NAME:
                return this.shadowTouchBridge;
            case Const.CONNECTED_BRIDGE_NAME:
                return this.connectedBridge;
            case Const.LOTTO_TOUCH_BRIDGE_NAME:
                return this.lottoTouchBridge;
            case Const.MAPPING_BRIDGE_NAME:
                return this.mappingBridge;
            case Const.SHADOW_MAPPING_BRIDGE_NAME:
                return this.shadowMappingBridge;
            case Const.PERIOD_BRIDGE_NAME:
                return this.periodBridge;
            case Const.POSITIVE_SHADOW_BRIDGE_NAME:
                return this.positiveShadowBridge;
            case Const.NEGATIVE_SHADOW_BRIDGE_NAME:
                return this.negativeShadowBridge;
            case Const.COMBINE_TOUCH_BRIDGE_NAME:
                return this.combineTouchBridge;
            case Const.BIG_DOUBLE_SET_NAME:
                return this.bigDoubleSet;
            default:
                return null;
        }
    }

}
