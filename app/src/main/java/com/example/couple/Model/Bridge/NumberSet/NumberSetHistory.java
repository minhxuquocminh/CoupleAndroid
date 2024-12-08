package com.example.couple.Model.Bridge.NumberSet;

import com.example.couple.Base.Handler.NumberBase;
import com.example.couple.Custom.Const.Const;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import lombok.Getter;

@Getter
public class NumberSetHistory {
    NumberSet numberSet;
    int appearanceTimes;
    int dayNumberBefore;
    List<Integer> beatList;

    public static NumberSetHistory getEmpty() {
        return new NumberSetHistory("", new ArrayList<>(), new ArrayList<>());
    }

    public boolean isEmpty() {
        return numberSet.getName().isEmpty() || beatList.isEmpty();
    }

    public NumberSetHistory(String numberSetName, List<Integer> numbers, List<Integer> beatList) {
        this.numberSet = new NumberSet(numberSetName, numbers);
        this.appearanceTimes = beatList.size();
        this.dayNumberBefore = beatList.isEmpty() ?
                Const.MAX_DAY_NUMBER_BEFORE : beatList.get(beatList.size() - 1);
        this.beatList = beatList;
    }

    public String showCompact() {
        return numberSet.getName() + " (" + dayNumberBefore + " ngày)";
    }

    public String show() {
        return "  + " + numberSet.getName() + " (" + dayNumberBefore + " ngày)";
    }

    public String showWithBeats() {
        return "  + " + numberSet.getName() + ": " + NumberBase.showNumbers(beatList, ", ") + ".";
    }

    public int getBeatMax() {
        return Collections.max(beatList);
    }

}
