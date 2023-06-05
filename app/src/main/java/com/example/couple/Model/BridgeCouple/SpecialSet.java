package com.example.couple.Model.BridgeCouple;

import com.example.couple.Base.Handler.NumberBase;
import com.example.couple.Model.Support.JackpotHistory;

import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class SpecialSet implements CombineInterface {
    String bridgeName;
    List<Integer> numbers;
    JackpotHistory jackpotHistory;

    @Override
    public String showBridge() {
        String show = "";
        String win = jackpotHistory.isEmpty() ? "" : (isWin() ? "trúng" : "trượt");
        show += " * " + jackpotHistory.show() + " - " + win + "\n";
        show += "    - " + bridgeName + ": " + showNumbers() + " (" + numbers.size() + " số).";
        return show;
    }

    @Override
    public String showCompactBridge() {
        String show = "";
        String win = jackpotHistory.isEmpty() ? "" : (isWin() ? " (trúng)" : " (trượt)");
        show += "    - " + bridgeName + "" + win + ".";
        return show;
    }

    @Override
    public boolean isWin() {
        return NumberBase.isWin(jackpotHistory, numbers);
    }

    @Override
    public List<Integer> getNumbers() {
        return numbers;
    }

    @Override
    public String showNumbers() {
        return NumberBase.showNumbers(numbers);
    }

    public static SpecialSet getEmpty() {
        return new SpecialSet("", new ArrayList<>(), JackpotHistory.getEmpty());
    }

    @Override
    public boolean isEmpty() {
        return bridgeName.equals("") || numbers.isEmpty();
    }
}
