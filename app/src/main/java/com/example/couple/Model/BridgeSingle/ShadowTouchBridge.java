package com.example.couple.Model.BridgeSingle;

import com.example.couple.Base.Handler.NumberBase;
import com.example.couple.Custom.Handler.NumberArrayHandler;
import com.example.couple.Model.BridgeCouple.CombineInterface;
import com.example.couple.Model.Support.JackpotHistory;

import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
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
        return NumberBase.isTouch(jackpotHistory, touchs);
    }

    public String showNumbers() {
        return NumberBase.showNumbers(numbers);
    }

    public String showTouchs() {
        return NumberBase.showTouchs(touchs);
    }

    public String showBridge() {
        String show = "";
        String win = isWin() ? "trúng" : "trượt";
        show += " * " + jackpotHistory.show() + " - " + win + "\n";
        show += " - " + bridgeName + ": " + showTouchs() + ".";
        return show;
    }

    public String showCompactBridge() {
        return showBridge();
    }

    public static ShadowTouchBridge getEmpty() {
        return new ShadowTouchBridge("", new ArrayList<>(), new JackpotHistory());
    }

    public boolean isEmpty() {
        return jackpotHistory.isEmpty();
    }

}
