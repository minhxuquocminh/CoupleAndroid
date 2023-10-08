package com.example.couple.Model.Time.Cycle;

import com.example.couple.Base.Handler.CoupleBase;
import com.example.couple.Custom.Const.Const;

import lombok.Getter;

@Getter
public class YearCycle {
    int year;
    Cycle cycle;

    public String getCouple() {
        return CoupleBase.showCouple(year % 100);
    }

    public int getCoupleInt() {
        return year % 100;
    }

    public static YearCycle getEmpty() {
        return new YearCycle(Const.EMPTY_VALUE);
    }

    public boolean isEmpty() {
        return year == Const.EMPTY_VALUE;
    }

    public YearCycle(int year) {
        int stemsPos = year % 10;
        int branchesPos = (year - 4) > 0 ? (year - 4) % 12 : 12 + (year - 4) % 12;
        Stem stem = new Stem(stemsPos);
        Branch branch = new Branch(branchesPos);
        this.year = year;
        this.cycle = new Cycle(stem, branch);
    }

    public String showByCouple() {
        return cycle.getCycle() + " (" + getCouple() + ")";
    }
}
