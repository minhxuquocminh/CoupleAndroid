package com.example.couple.Model.Bridge.Couple;

import com.example.couple.Base.Handler.CoupleBase;
import com.example.couple.Custom.Const.Const;
import com.example.couple.Model.Bridge.Bridge;
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
public class ShadowMappingBridge extends Bridge {
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
                if (couple1.getNumber().equals(couple2.getNumber())) {
                    int type = couple1.getType() > couple2.getType() ? couple1.getType() : couple2.getType();
                    results.add(new ShadowCouple(couple1.getNumber(), type));
                }
            }
        }
        Collections.sort(results, (x, y) -> x.getType() - y.getType());
        return results;
    }

    @Override
    public String showCompactNumbers() {
        return "";
    }

    @Override
    public String getBridgeName() {
        return Const.SHADOW_MAPPING_BRIDGE_NAME;
    }

    public String showCopyCombineShadowCouples() {
        return CoupleBase.showCoupleNumbers(getNumbers());
    }

    public static ShadowMappingBridge getEmpty() {
        return new ShadowMappingBridge(ShadowSingle.getEmpty(),
                ShadowSingle.getEmpty(), JackpotHistory.getEmpty());
    }

    public boolean isEmpty() {
        return shadowSingle1.isEmpty() && shadowSingle2.isEmpty();
    }
}
