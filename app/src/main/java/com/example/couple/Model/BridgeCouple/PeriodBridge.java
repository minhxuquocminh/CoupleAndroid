package com.example.couple.Model.BridgeCouple;

import com.example.couple.Base.Handler.NumberBase;
import com.example.couple.Custom.Const.Const;
import com.example.couple.Model.Support.JackpotHistory;
import com.example.couple.Model.Support.History;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import lombok.Getter;

@Getter
public class PeriodBridge implements CombineInterface {
    List<History> periodHistories3;
    List<History> periodHistories4;
    JackpotHistory jackpotHistory;
    List<History> periodHistories;
    List<Integer> numbers;

    public PeriodBridge(List<History> periodHistories3,
                        List<History> periodHistories4, JackpotHistory jackpotHistory) {
        this.periodHistories3 = periodHistories3;
        this.periodHistories4 = periodHistories4;
        this.jackpotHistory = jackpotHistory;
        this.periodHistories = new ArrayList<>();
        this.numbers = new ArrayList<>();

        for (History history3 : periodHistories3) {
            int count = 0;
            for (History history4 : periodHistories4) {
                if (history3.getEndDate().equals(history4.getEndDate())) count++;
            }
            if (count == 0) periodHistories.add(history3);
        }

        List<Integer> inits = new ArrayList<>();
        for (History history : periodHistories) {
            inits.addAll(NumberBase
                    .getPeriodNumbers(history.getLastNumber(), Const.AMPLITUDE_OF_PERIOD_BRIDGE));
        }

        for (int number : inits) {
            if (!numbers.contains(number)) {
                numbers.add(number);
            }
        }

        Collections.sort(numbers);

    }

    public String showBridge() {
        String show = "";
        String win = isWin() ? "trúng" : "trượt";
        show += " * " + jackpotHistory.show() + " - " + win + "\n";
        show += " - Cầu khoảng: " + showNumbers() + " (" + numbers.size() + " số).";
        return show;
    }

    public String showCompactBridge() {
        String show = "";
        String win = isWin() ? "trúng" : "trượt";
        show += " * " + jackpotHistory.show() + " - " + win + "\n";
        show += " - Cầu khoảng: " + numbers.size() + " số.";
        return show;
    }

    public boolean isWin() {
        return NumberBase.isWin(jackpotHistory, numbers);
    }

    public String showNumbers() {
        return NumberBase.showNumbers(numbers);
    }

    public static PeriodBridge getEmpty() {
        return new PeriodBridge(new ArrayList<>(), new ArrayList<>(), new JackpotHistory());
    }

    public boolean isEmpty() {
        return jackpotHistory.isEmpty();
    }

}
