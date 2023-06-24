package com.example.couple.Model.BridgeSingle;

import com.example.couple.Custom.Handler.CoupleHandler;
import com.example.couple.Custom.Handler.NumberArrayHandler;
import com.example.couple.Model.BridgeCouple.CombineInterface;
import com.example.couple.Model.Support.JackpotHistory;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;

@Getter
public class ShadowTouchBridge implements CombineInterface {
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

    public boolean isWin() {
        return CoupleHandler.isTouch(jackpotHistory, touchs);
    }

    public String showNumbers() {
        return CoupleHandler.showCoupleNumbers(numbers);
    }

    public String showTouchs() {
        return CoupleHandler.showTouchs(touchs);
    }

    public String showBridge() {
        String show = "";
        String win = jackpotHistory.isEmpty() ? "" : (isWin() ? "trúng" : "trượt");
        show += " * " + jackpotHistory.show() + " - " + win + "\n";
        show += "    - " + bridgeName + ": " + showTouchs() + ".";
        return show;
    }

    public String showCompactBridge() {
        String show = "";
        String win = jackpotHistory.isEmpty() ? "" : (isWin() ? " (trúng)" : " (trượt)");
        show += "    - " + bridgeName + "" + win + ": " + showTouchs() + ".";
        return show;
    }

    public static ShadowTouchBridge getEmpty() {
        return new ShadowTouchBridge("", new ArrayList<>(), JackpotHistory.getEmpty());
    }

    public boolean isEmpty() {
        return bridgeName.equals("") || touchs.isEmpty();
    }

}
