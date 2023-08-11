package com.example.couple.Model.Display;

import com.example.couple.Custom.Handler.CoupleHandler;
import com.example.couple.Base.Handler.NumberBase;
import com.example.couple.Custom.Const.Const;
import com.example.couple.Custom.Handler.NumberArrayHandler;

import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class SpecialSetHistory {
    SpecialSet specialSet;
    int appearanceTimes;
    int dayNumberBefore;
    List<Integer> beatList;

    public SpecialSetHistory(String specialSetName, List<Integer> numbers, List<Integer> beatList) {
        this.specialSet = new SpecialSet(specialSetName, numbers);
        this.appearanceTimes = beatList.size();
        this.dayNumberBefore = beatList.isEmpty() ?
                Const.MAX_DAY_NUMBER_BEFORE : beatList.get(beatList.size() - 1);
        this.beatList = beatList;
    }

    public String showHistory() {
        return "  - " + specialSet.getName() + ": " + NumberBase.showNumbers(beatList, ", ") + ".";
    }

}
