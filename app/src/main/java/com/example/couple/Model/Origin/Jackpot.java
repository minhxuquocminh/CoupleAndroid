package com.example.couple.Model.Origin;

import com.example.couple.Custom.Const.Const;
import com.example.couple.Model.Bridge.NumberSet.NumberSet;
import com.example.couple.Model.DateTime.Date.Cycle.Cycle;
import com.example.couple.Model.DateTime.Date.DateBase;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
public class Jackpot {
    String jackpot;
    DateBase dateBase;
    @Setter
    Cycle dayCycle;

    // for Couple
    protected Jackpot() {
        this.jackpot = Const.EMPTY_JACKPOT;
        this.dateBase = DateBase.getEmpty();
        this.dayCycle = Cycle.getEmpty();
    }

    public Jackpot(String jackpot, DateBase dateBase) {
        this.jackpot = jackpot;
        this.dateBase = dateBase;
        this.dayCycle = Cycle.getEmpty();
    }

    public static Jackpot getEmpty() {
        return new Jackpot(Const.EMPTY_JACKPOT, DateBase.getEmpty(), Cycle.getEmpty());
    }

    public boolean isEmpty() {
        return jackpot.equals(Const.EMPTY_JACKPOT) || dateBase.isEmpty();
    }

    public boolean isDayOff() {
        return jackpot.equals(Const.EMPTY_JACKPOT);
    }

    public boolean isSameSequentlySign() {
        for (int i = 0; i < jackpot.length() - 2; i++) {
            int number = Integer.parseInt(jackpot.charAt(i) + "");
            int nextNumber = Integer.parseInt(jackpot.charAt(i + 1) + "");
            if (number == nextNumber) {
                return true;
            }
        }
        return false;
    }

    public boolean isYearBranch(int currentYear) {
        if (this.isEmpty() || dayCycle.isEmpty()) return false;
        return dayCycle.getBranch().isYearBranch(getCoupleInt(), currentYear);
    }

    public boolean isDayCycleSet() {
        if (this.isEmpty() || dayCycle.isEmpty()) return false;
        return NumberSet.getFrom(dayCycle.getCoupleInt()).isItMatch(getCoupleInt());
    }

    public int getThirdClaw() {
        return Integer.parseInt(jackpot.charAt(2) + "");
    }

    public Couple getCouple() {
        return new Couple(jackpot, dateBase, dayCycle);
    }

    public int getCoupleInt() {
        return this.getCouple().getInt();
    }

    public Couple getCoupleAtBegin() {
        if (jackpot == null || jackpot.equals(Const.EMPTY_JACKPOT)) return Couple.getEmpty();
        int first = Integer.parseInt(jackpot.charAt(0) + "");
        int second = Integer.parseInt(jackpot.charAt(1) + "");
        return new Couple(first, second);
    }

    public int getCoupleIntAtBegin() {
        return this.getCoupleAtBegin().getInt();
    }
}
