package com.example.couple.Model.Support;

import com.example.couple.Custom.Handler.LotteryHandler;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class PairConnectedSupport {
    Pair pair;
    PairPosition pairPosition;
    List<Pair> typeList;
    int numberOfRuns;

    public String show() {
        String show = "Các số " + pair.show(", ") + " - " +
                LotteryHandler.showPrize(pairPosition.getFirstPosition()) + ", " +
                LotteryHandler.showPrize(pairPosition.getSecondPosition()) + "\n TT: ";
        for (int i = 0; i < typeList.size(); i++) {
            show += i != typeList.size() - 1 ? typeList.get(i).show("-") + ", " : typeList.get(i).show("-");
        }
        return show;
    }

    public String showShort() {
        return pair.getFirst() + "-" + pair.getSecond() + " (" + typeList.size() + ")";
    }
}
