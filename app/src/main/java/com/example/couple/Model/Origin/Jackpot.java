package com.example.couple.Model.Origin;

import com.example.couple.Model.Display.BCouple;
import com.example.couple.Base.Handler.DateBase;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class Jackpot {
    String jackpot;
    DateBase dateBase;

    public static Jackpot getEmptyJackpot() {
        return new Jackpot("-99999", new DateBase());
    }

    public boolean isEmptyJackpot() {
        return jackpot != null && jackpot.equals("-99999");
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
