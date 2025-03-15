package com.example.couple.Model.Bridge.NumberSet;

import com.example.couple.Base.Handler.NumberBase;
import com.example.couple.Custom.Const.Const;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import lombok.Getter;

@Getter
public class NumericSetHistory {
    NumericSet numericSet;
    int appearanceTimes;
    int dayNumberBefore;
    List<Integer> beatList;

    public static NumericSetHistory getEmpty() {
        return new NumericSetHistory("", new ArrayList<>(), new ArrayList<>());
    }

    public boolean isEmpty() {
        return numericSet.getName().isEmpty() || beatList.isEmpty();
    }

    public NumericSetHistory(String numberSetName, List<Integer> numbers, List<Integer> beatList) {
        this.numericSet = new NumericSet(numberSetName, numbers);
        this.appearanceTimes = beatList.size();
        this.dayNumberBefore = beatList.isEmpty() ?
                Const.MAX_DAY_NUMBER_BEFORE : beatList.get(beatList.size() - 1);
        this.beatList = beatList;
    }

    public String showCompact() {
        return numericSet.getName() + " (" + dayNumberBefore + " ngày)";
    }

    public String show() {
        return "  + " + numericSet.getName() + " (" + dayNumberBefore + " ngày)";
    }

    public String showWithBeats() {
        return "  + " + numericSet.getName() + ": " + NumberBase.showNumbers(beatList, ", ") + ".";
    }

    public int getBeatMax() {
        return Collections.max(beatList);
    }

}
