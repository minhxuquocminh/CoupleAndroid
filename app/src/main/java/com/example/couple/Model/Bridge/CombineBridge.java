package com.example.couple.Model.Bridge;

import com.example.couple.Base.Handler.NumberBase;
import com.example.couple.Custom.Handler.CoupleHandler;
import com.example.couple.Model.Support.JackpotHistory;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;

@Getter
public class CombineBridge {
    List<Bridge> bridgeList;
    JackpotHistory jackpotHistory;
    List<Integer> numbers;

    public CombineBridge(List<Bridge> bridgeList, JackpotHistory jackpotHistory) {
        this.bridgeList = bridgeList;
        this.jackpotHistory = jackpotHistory;
        this.numbers = new ArrayList<>();
        for (Bridge bridge : bridgeList) {
            numbers = NumberBase.getMatchNumbers(numbers, bridge.getNumbers());
        }
    }

    public String showBridge() {
        String show = " * " + jackpotHistory.show() + ":\n";
        for (Bridge bridge : bridgeList) {
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

}
