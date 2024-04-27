package com.example.couple.Model.Bridge.Single;

import com.example.couple.Base.Handler.SingleBase;
import com.example.couple.Custom.Handler.NumberArrayHandler;
import com.example.couple.Model.Bridge.Bridge;
import com.example.couple.Model.Support.JackpotHistory;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;

@Getter
public class ShadowTouchBridge extends Bridge {
    String bridgeName;
    List<Integer> touchs;
    JackpotHistory jackpotHistory;
    List<Integer> numbers;

    public ShadowTouchBridge(String bridgeName, List<Integer> touchs, JackpotHistory jackpotHistory) {
        this.bridgeName = bridgeName;
        this.touchs = touchs;
        this.jackpotHistory = jackpotHistory;
        this.numbers = NumberArrayHandler.getTouchs(touchs);
    }

    public static ShadowTouchBridge getEmpty() {
        return new ShadowTouchBridge("", new ArrayList<>(), JackpotHistory.getEmpty());
    }

    public boolean isEmpty() {
        return bridgeName.isEmpty() || touchs.isEmpty();
    }

    @Override
    public String showCompactNumbers() {
        return SingleBase.showTouchs(touchs);
    }
}
