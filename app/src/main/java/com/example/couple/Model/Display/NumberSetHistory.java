package com.example.couple.Model.Display;

import com.example.couple.Base.Handler.NumberBase;
import com.example.couple.Custom.Const.Const;

import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class NumberSetHistory {
    NumberSet numberSet;
    int appearanceTimes;
    int dayNumberBefore;
    List<Integer> beatList;

    public NumberSetHistory(String numberSetName, List<Integer> numbers, List<Integer> beatList) {
        this.numberSet = new NumberSet(numberSetName, numbers);
        this.appearanceTimes = beatList.size();
        this.dayNumberBefore = beatList.isEmpty() ?
                Const.MAX_DAY_NUMBER_BEFORE : beatList.get(beatList.size() - 1);
        this.beatList = beatList;
    }

    public String showCompact() {
        return "  + " + numberSet.getName() + " (" + dayNumberBefore + " ngày)";
    }

    public String show() {
        return "  + " + numberSet.getName() + ": " + NumberBase.showNumbers(beatList, ", ") + ".";
    }

}
