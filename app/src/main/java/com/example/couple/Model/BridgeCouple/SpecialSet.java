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

    public static SpecialSet getEmpty() {
        return new SpecialSet("", new ArrayList<>(), new JackpotHistory());
    }

    @Override
    public String showBridge() {
        String show = "";
        String win = isWin() ? "trúng" : "trượt";
        show += " * " + jackpotHistory.show() + " - " + win + "\n";
        show += " - " + bridgeName + ": " + showNumbers() + " (" + numbers.size() + " số).";
        return show;
    }

    @Override
    public String showCompactBridge() {
        return showBridge();
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

    @Override
    public boolean isEmpty() {
        return jackpotHistory.isEmpty();
    }
}
