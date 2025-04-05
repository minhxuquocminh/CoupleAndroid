package com.example.couple.Model.Bridge.Touch;

import com.example.couple.Base.Handler.SingleBase;
import com.example.couple.Custom.Handler.NumberArrayHandler;
import com.example.couple.Model.Bridge.BridgeType;
import com.example.couple.Model.Bridge.JackpotHistory;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;

@Getter
public class ShadowTouchBridge extends TouchBridge {
    BridgeType bridgeType;
    List<Integer> touches;
    JackpotHistory jackpotHistory;
    List<Integer> numbers;

    public ShadowTouchBridge(BridgeType bridgeType, List<Integer> touches, JackpotHistory jackpotHistory) {
        this.bridgeType = bridgeType;
        this.touches = touches;
        this.jackpotHistory = jackpotHistory;
        this.numbers = NumberArrayHandler.getTouchs(touches);
    }

    public static ShadowTouchBridge getEmpty() {
        return new ShadowTouchBridge(null, new ArrayList<>(), JackpotHistory.getEmpty());
    }

    public boolean isEmpty() {
        return bridgeType == null || touches.isEmpty();
    }

    @Override
    public String showCompactNumbers() {
        return SingleBase.showTouches(touches);
    }

    @Override
    public BridgeType getType() {
        return bridgeType;
    }
}
