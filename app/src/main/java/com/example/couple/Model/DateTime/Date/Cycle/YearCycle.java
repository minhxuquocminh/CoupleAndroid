package com.example.couple.Model.DateTime.Date.Cycle;

import com.example.couple.Custom.Const.Const;

import lombok.Getter;

@Getter
public class YearCycle {
    int year;
    Cycle cycle;

    public static YearCycle getEmpty() {
        return new YearCycle(Const.EMPTY_VALUE);
    }

    public boolean isEmpty() {
        return year == Const.EMPTY_VALUE;
    }

    public YearCycle(int year) {
        Stem stem = new Stem(year % 10);
        Branch branch = new Branch((year - 4) > 0 ? (year - 4) % 12 : 12 + (year - 4) % 12);
        this.year = year;
        this.cycle = Cycle.getByStemAndBranch(stem, branch);
    }

    public int getCoupleInt() {
        return year % 100;
    }

    public String showByCouple() {
        return cycle.getName() + " (" + getCoupleInt() + ")";
    }
}
