package com.example.couple.Model.Origin;

import com.example.couple.Custom.Const.Const;
import com.example.couple.Model.Display.BCouple;
import com.example.couple.Model.Time.Cycle.Cycle;
import com.example.couple.Model.Time.DateBase;

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

    public Jackpot(String jackpot, DateBase dateBase) {
        this.jackpot = jackpot;
        this.dateBase = dateBase;
        this.dayCycle = Cycle.getEmpty();
    }

    public static Jackpot getEmpty(DateBase dateBase) {
        return new Jackpot(Const.EMPTY_JACKPOT, dateBase);
    }

    public static Jackpot getEmpty() {
        return new Jackpot(Const.EMPTY_JACKPOT, DateBase.getEmpty());
    }

    public boolean isEmpty() {
        return jackpot.equals(Const.EMPTY_JACKPOT);
    }

    public boolean isSameSequentlySign() {
        int count = 0;
        for (int i = 0; i < jackpot.length() - 2; i++) {
            int number = Integer.parseInt(jackpot.charAt(i) + "");
            int nextNumber = Integer.parseInt(jackpot.charAt(i + 1) + "");
            if (number == nextNumber) {
                count++;
                break;
            }
        }
        return count > 0;
    }

    public boolean isYearBranch(int currentYear) {
        if (this.isEmpty() || dayCycle.isEmpty()) return false;
        return dayCycle.getBranch().isYearBranch(getCoupleInt(), currentYear);
    }

    public int getThirdClaw() {
        return Integer.parseInt(jackpot.charAt(2) + "");
    }

    public Couple getCouple() {
        int tens = Integer.parseInt(jackpot.charAt(3) + "");
        int unit = Integer.parseInt(jackpot.charAt(4) + "");

        return new Couple(tens, unit, dateBase);
    }

    public int getCoupleInt() {
        int tens = Integer.parseInt(jackpot.charAt(3) + "");
        int unit = Integer.parseInt(jackpot.charAt(4) + "");
        return Integer.parseInt(tens + "" + unit);
    }

    public BCouple getBCouple() {
        int tens = Integer.parseInt(jackpot.charAt(3) + "");
        int unit = Integer.parseInt(jackpot.charAt(4) + "");
        return new BCouple(tens, unit);
    }
}
