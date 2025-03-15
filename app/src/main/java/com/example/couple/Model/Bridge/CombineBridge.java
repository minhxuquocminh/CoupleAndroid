package com.example.couple.Model.Bridge;

import com.example.couple.Base.Handler.CoupleBase;
import com.example.couple.Base.Handler.NumberBase;
import com.example.couple.Custom.Handler.CoupleHandler;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import lombok.Getter;

@Getter
public class CombineBridge {
    Map<BridgeType, Bridge> bridgeMap;
    JackpotHistory jackpotHistory;
    List<Integer> numbers;

    public CombineBridge(Map<BridgeType, Bridge> bridgeMap, JackpotHistory jackpotHistory) {
        this.bridgeMap = bridgeMap;
        this.jackpotHistory = jackpotHistory;
        this.numbers = new ArrayList<>();
        for (Bridge bridge : bridgeMap.values()) {
            numbers = NumberBase.getMatchNumbers(numbers, bridge.getNumbers());
        }
    }

    public String showBridge() {
        String show = " * " + jackpotHistory.show() + ":\n";
        for (Bridge bridge : bridgeMap.values()) {
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
        return CoupleBase.showCoupleNumbers(numbers);
    }

}
