package com.example.couple.Model.DateTime.Date.Cycle;

import com.example.couple.Custom.Const.Const;

import lombok.Getter;

@Getter
public class YearCycle {
    int year;
    Cycle cycle;

    public YearCycle() {
        this.year = Const.EMPTY_VALUE;
        this.cycle = Cycle.getEmpty();
    }

    public YearCycle(int year) {
        Stem stem = new Stem(year % 10);
        Branch branch = new Branch((year - 4) > 0 ? (year - 4) % 12 : 12 + (year - 4) % 12);
        this.year = year;
        this.cycle = Cycle.getByStemAndBranch(stem, branch);
    }

    public static YearCycle getEmpty() {
        return new YearCycle();
    }

    public boolean isEmpty() {
        return year == Const.EMPTY_VALUE;
    }

    public int getCoupleInt() {
        if (this.isEmpty()) return Const.EMPTY_VALUE;
        return year % 100;
    }

    public String showByCouple() {
        if (this.isEmpty()) return Const.EMPTY;
        return cycle.getName() + " (" + getCoupleInt() + ")";
    }
}
