package com.example.couple.Model.BridgeCouple;

import com.example.couple.Custom.Handler.CoupleHandler;
import com.example.couple.Model.Cycle.YearCycle;
import com.example.couple.Model.Support.JackpotHistory;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;

@Getter
public class CycleBridge implements CombineInterface {
    String bridgeName;
    List<YearCycle> yearCycles;
    List<Integer> numbers;
    JackpotHistory jackpotHistory;

    public CycleBridge(String bridgeName, List<YearCycle> yearCycles, JackpotHistory jackpotHistory) {
        this.bridgeName = bridgeName;
        this.yearCycles = yearCycles;
        this.numbers = new ArrayList<>();
        for (YearCycle yearCycle : yearCycles) {
            int couple = yearCycle.getYear() % 100;
            if (!numbers.contains(couple)) {
                numbers.add(couple);
            }
        }
        this.jackpotHistory = jackpotHistory;
    }

    public String showBridge() {
        String show = "";
        String win = jackpotHistory.isEmpty() ? "" : (isWin() ? "trúng" : "trượt");
        show += " * " + jackpotHistory.show() + " - " + win + "\n";
        show += "    - " + bridgeName + ": " + showNumbers() + " (" + numbers.size() + " số).";
        return show;
    }

    public String showCompactBridge() {
        String show = "";
        String win = jackpotHistory.isEmpty() ? "" : (isWin() ? " (trúng)" : " (trượt)");
        show += "    - " + bridgeName + win + ": " + numbers.size() + " số.";
        return show;
    }

    public boolean isWin() {
        return CoupleHandler.isWin(jackpotHistory, numbers);
    }

    public String showNumbers() {
        return CoupleHandler.showCoupleNumbers(numbers);
    }

    public static CycleBridge getEmpty() {
        return new CycleBridge("", new ArrayList<>(), JackpotHistory.getEmpty());
    }

    public boolean isEmpty() {
        return bridgeName.equals("") || yearCycles.isEmpty() || numbers.isEmpty();
    }

}
