package com.example.couple.Model.Time.Cycle;

import com.example.couple.Custom.Const.Const;
import com.example.couple.Custom.Handler.CoupleHandler;

import lombok.Getter;

@Getter
public class YearCycle {
    int year;
    Cycle cycle;

    public String getCouple() {
        return CoupleHandler.showCouple(year % 100);
    }

    public static YearCycle getEmpty() {
        return new YearCycle(Const.EMPTY_VALUE);
    }

    public boolean isEmpty() {
        return year == Const.EMPTY_VALUE;
    }

    public YearCycle(int year) {
        int stemsPos = year % 10;
        int branchesPos = (year - 4) % 12;
        Stems stems = new Stems(stemsPos);
        Branches branches = new Branches(branchesPos);
        this.year = year;
        this.cycle = new Cycle(stems, branches);
    }

    public String showByCouple() {
        return cycle.getCycle() + " (" + getCouple() + ")";
    }
}
