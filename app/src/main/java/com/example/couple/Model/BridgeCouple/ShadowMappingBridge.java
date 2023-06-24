package com.example.couple.Model.BridgeCouple;

import com.example.couple.Custom.Handler.CoupleHandler;
import com.example.couple.Custom.Const.Const;
import com.example.couple.Model.Support.JackpotHistory;
import com.example.couple.Model.Support.ShadowCouple;
import com.example.couple.Model.Support.ShadowSingle;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class ShadowMappingBridge implements CombineInterface {
    ShadowSingle shadowSingle1;
    ShadowSingle shadowSingle2;
    JackpotHistory jackpotHistory;
    List<Integer> numbers;

    public ShadowMappingBridge(ShadowSingle shadowSingle1, ShadowSingle shadowSingle2, JackpotHistory jackpotHistory) {
        this.shadowSingle1 = shadowSingle1;
        this.shadowSingle2 = shadowSingle2;
        this.jackpotHistory = jackpotHistory;
        this.numbers = new ArrayList<>();
        List<ShadowCouple> shadowCouples = getCombineShadowCouples();
        for (ShadowCouple couple : shadowCouples) {
            this.numbers.add(couple.getNumber());
        }
    }

    public List<ShadowCouple> getCombineShadowCouples() {
        List<ShadowCouple> results = new ArrayList<>();
        List<ShadowCouple> shadowCouples1 = shadowSingle1.getAllShadowCouples();
        List<ShadowCouple> shadowCouples2 = shadowSingle2.getAllShadowCouples();
        for (ShadowCouple couple1 : shadowCouples1) {
            for (ShadowCouple couple2 : shadowCouples2) {
                if (couple1.getNumber() == couple2.getNumber()) {
                    int type = couple1.getType() > couple2.getType() ? couple1.getType() : couple2.getType();
                    results.add(new ShadowCouple(couple1.getNumber(), type));
                }
            }
        }
        Collections.sort(results, (x, y) -> x.getType() - y.getType());
        return results;
    }

    public String showBridge() {
        String show = "";
        String win = jackpotHistory.isEmpty() ? "" : (isWin() ? "trúng" : "trượt");
        show += " * " + jackpotHistory.show() + " - " + win + "\n";
        show += "    - " + Const.SHADOW_MAPPING_BRIDGE_NAME + ": " + showCombineShadowCouples() +
                " (" + getCombineShadowCouples().size() + " số).";
        return show;
    }

    public String showCompactBridge() {
        String show = "";
        String win = jackpotHistory.isEmpty() ? "" : (isWin() ? " (trúng)" : " (trượt)");
        show += "    - " + Const.SHADOW_MAPPING_BRIDGE_NAME + win + ": " +
                getCombineShadowCouples().size() + " số.";
        return show;
    }

    public boolean isWin() {
        return CoupleHandler.isWin(jackpotHistory, getNumbers());
    }

    public String showNumbers() {
        return CoupleHandler.showCoupleNumbers(numbers);
    }

    public String showCombineShadowCouples() {
        String show = "";
        List<ShadowCouple> shadowCouples = getCombineShadowCouples();
        for (ShadowCouple couple : shadowCouples) {
            show += couple.show();
        }
        return show;
    }

    public String showCopyCombineShadowCouples() {
        return CoupleHandler.showCoupleNumbers(getNumbers());
    }

    public static ShadowMappingBridge getEmpty() {
        return new ShadowMappingBridge(ShadowSingle.getEmpty(),
                ShadowSingle.getEmpty(), JackpotHistory.getEmpty());
    }

    public boolean isEmpty() {
        return shadowSingle1.isEmpty() && shadowSingle2.isEmpty();
    }
}
