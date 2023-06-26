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
    // touch
    CombineTouchBridge combineTouchBridge;
    ConnectedBridge connectedBridge;
    ShadowTouchBridge shadowTouchBridge;
    LottoTouchBridge lottoTouchBridge;
    ShadowTouchBridge negativeShadowBridge;
    ShadowTouchBridge positiveShadowBridge;
    // mapping, period
    MappingBridge mappingBridge;
    ShadowMappingBridge shadowMappingBridge;
    PeriodBridge periodBridge;
    MappingBridge mappingBridge0;
    MappingBridge mappingBridge1;
    MappingBridge mappingBridge2;
    // special set
    SpecialSet bigDoubleSet;
    SpecialSet sameDoubleSet;
    SpecialSet nearDoubleSet;

    JackpotHistory jackpotHistory;
    List<String> bridgeNames;
    List<Integer> numbers;

    public CombineBridge(CombineTouchBridge combineTouchBridge, ConnectedBridge connectedBridge,
                         ShadowTouchBridge shadowTouchBridge, LottoTouchBridge lottoTouchBridge,
                         ShadowTouchBridge negativeShadowBridge, ShadowTouchBridge positiveShadowBridge,
                         MappingBridge mappingBridge, ShadowMappingBridge shadowMappingBridge,
                         PeriodBridge periodBridge, MappingBridge mappingBridge0,
                         MappingBridge mappingBridge1, MappingBridge mappingBridge2,
                         SpecialSet bigDoubleSet, SpecialSet sameDoubleSet, SpecialSet nearDoubleSet,
                         JackpotHistory jackpotHistory) {
        // touch
        this.combineTouchBridge = combineTouchBridge;
        this.connectedBridge = connectedBridge;
        this.shadowTouchBridge = shadowTouchBridge;
        this.lottoTouchBridge = lottoTouchBridge;
        this.positiveShadowBridge = positiveShadowBridge;
        this.negativeShadowBridge = negativeShadowBridge;
        // mapping, period
        this.mappingBridge = mappingBridge;
        this.shadowMappingBridge = shadowMappingBridge;
        this.periodBridge = periodBridge;
        this.mappingBridge0 = mappingBridge0;
        this.mappingBridge1 = mappingBridge1;
        this.mappingBridge2 = mappingBridge2;
        // special set
        this.bigDoubleSet = bigDoubleSet;
        this.sameDoubleSet = sameDoubleSet;
        this.nearDoubleSet = nearDoubleSet;
        // jackpot
        this.jackpotHistory = jackpotHistory;
        this.bridgeNames = new ArrayList<>();
        // touch
        if (!combineTouchBridge.isEmpty()) {
            this.bridgeNames.add(Const.COMBINE_TOUCH_BRIDGE_NAME);
        }
        if (!connectedBridge.isEmpty()) {
            this.bridgeNames.add(Const.CONNECTED_BRIDGE_NAME);
        }
        if (!shadowTouchBridge.isEmpty()) {
            this.bridgeNames.add(Const.SHADOW_TOUCH_BRIDGE_NAME);
        }
        if (!lottoTouchBridge.isEmpty()) {
            this.bridgeNames.add(Const.LOTTO_TOUCH_BRIDGE_NAME);
        }
        if (!negativeShadowBridge.isEmpty()) {
            this.bridgeNames.add(Const.NEGATIVE_SHADOW_BRIDGE_NAME);
        }
        if (!positiveShadowBridge.isEmpty()) {
            this.bridgeNames.add(Const.POSITIVE_SHADOW_BRIDGE_NAME);
        }
        // mapping, period
        if (!mappingBridge.isEmpty()) {
            this.bridgeNames.add(Const.MAPPING_BRIDGE_NAME);
        }
        if (!shadowMappingBridge.isEmpty()) {
            this.bridgeNames.add(Const.SHADOW_MAPPING_BRIDGE_NAME);
        }
        if (!periodBridge.isEmpty()) {
            this.bridgeNames.add(Const.PERIOD_BRIDGE_NAME);
        }
        if (!mappingBridge0.isEmpty()) {
            this.bridgeNames.add(Const.MAPPING_BRIDGE_NAME_0);
        }
        if (!mappingBridge1.isEmpty()) {
            this.bridgeNames.add(Const.MAPPING_BRIDGE_NAME_1);
        }
        if (!mappingBridge2.isEmpty()) {
            this.bridgeNames.add(Const.MAPPING_BRIDGE_NAME_2);
        }
        // special set
        if (!bigDoubleSet.isEmpty()) {
            this.bridgeNames.add(Const.BIG_DOUBLE_SET_NAME);
        }
        if (!sameDoubleSet.isEmpty()) {
            this.bridgeNames.add(Const.DOUBLE_SET_NAME);
        }
        if (!nearDoubleSet.isEmpty()) {
            this.bridgeNames.add(Const.NEAR_DOUBLE_SET_NAME);
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
            // touch
            case Const.COMBINE_TOUCH_BRIDGE_NAME:
                return this.combineTouchBridge;
            case Const.CONNECTED_BRIDGE_NAME:
                return this.connectedBridge;
            case Const.SHADOW_TOUCH_BRIDGE_NAME:
                return this.shadowTouchBridge;
            case Const.LOTTO_TOUCH_BRIDGE_NAME:
                return this.lottoTouchBridge;
            case Const.NEGATIVE_SHADOW_BRIDGE_NAME:
                return this.negativeShadowBridge;
            case Const.POSITIVE_SHADOW_BRIDGE_NAME:
                return this.positiveShadowBridge;
            // mapping, period
            case Const.MAPPING_BRIDGE_NAME:
                return this.mappingBridge;
            case Const.SHADOW_MAPPING_BRIDGE_NAME:
                return this.shadowMappingBridge;
            case Const.PERIOD_BRIDGE_NAME:
                return this.periodBridge;
            case Const.MAPPING_BRIDGE_NAME_0:
                return this.mappingBridge0;
            case Const.MAPPING_BRIDGE_NAME_1:
                return this.mappingBridge1;
            case Const.MAPPING_BRIDGE_NAME_2:
                return this.mappingBridge2;
            // special set
            case Const.BIG_DOUBLE_SET_NAME:
                return this.bigDoubleSet;
            case Const.DOUBLE_SET_NAME:
                return this.sameDoubleSet;
            case Const.NEAR_DOUBLE_SET_NAME:
                return this.nearDoubleSet;
            default:
                return null;
        }
    }

}
