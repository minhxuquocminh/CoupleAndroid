package com.example.couple.Model.BridgeCouple;

import com.example.couple.Base.Handler.NumberBase;
import com.example.couple.Custom.Const.Const;
import com.example.couple.Custom.Handler.CoupleHandler;
import com.example.couple.Model.Origin.Couple;
import com.example.couple.Model.Support.JackpotHistory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class TriadMappingBridge implements CombineInterface {
    Map<Couple, Couple> sequentCoupleMap;
    List<Integer> numbers;
    JackpotHistory jackpotHistory;

    public TriadMappingBridge(Map<Couple, Couple> sequentCoupleMap, JackpotHistory jackpotHistory) {
        this.sequentCoupleMap = sequentCoupleMap;
        this.numbers = new ArrayList<>();
        for (Map.Entry<Couple, Couple> entry : sequentCoupleMap.entrySet()) {
            Couple first = entry.getKey();
            Couple second = entry.getValue();
            List<Integer> currentNumbers = new ArrayList<>();
            currentNumbers.addAll(first.getMappingNumbers(Const.MAPPING_ALL));
            currentNumbers.addAll(second.getMappingNumbers(Const.MAPPING_ALL));
            numbers = NumberBase.getMatchNumbers(numbers, NumberBase.filterDuplicatedNumbers(currentNumbers));
        }
        this.jackpotHistory = jackpotHistory;
    }

    public String showBridge() {
        String show = "";
        String win = jackpotHistory.isEmpty() ? "" : (isWin() ? "trúng" : "trượt");
        show += " * " + jackpotHistory.show() + " - " + win + "\n";
        show += "    - " + Const.TRIAD_MAPPING_BRIDGE_NAME + ": " + showNumbers() + " (" + numbers.size() + " số).";
        return show;
    }

    public String showCompactBridge() {
        String show = "";
        String win = jackpotHistory.isEmpty() ? "" : (isWin() ? " (trúng)" : " (trượt)");
        show += "    - " + Const.TRIAD_MAPPING_BRIDGE_NAME + win + ": " + numbers.size() + " số.";
        return show;
    }

    public boolean isWin() {
        return CoupleHandler.isWin(jackpotHistory, numbers);
    }

    public String showNumbers() {
        return CoupleHandler.showCoupleNumbers(numbers);
    }

    public static TriadMappingBridge getEmpty() {
        return new TriadMappingBridge(new HashMap<>(), new ArrayList<>(), JackpotHistory.getEmpty());
    }

    public boolean isEmpty() {
        return numbers.isEmpty();
    }
}
