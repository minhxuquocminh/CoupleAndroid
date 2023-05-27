package com.example.couple.Model.Origin;

import com.example.couple.Base.Handler.DateBase;
import com.example.couple.Model.Support.Position;

import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class Lottery {
    private String timeShow;
    private String characters;
    private List<String> lottery;
    private DateBase dateBase;

    public int getValueAtPosition(Position position) {
        return Integer.parseInt(lottery.get(position.getFirstLevel()).
                charAt(position.getSecondLevel()) + "");
    }

    public String getJackpotString() {
        return lottery.get(0);
    }

    public Jackpot getJackpot() {
        return new Jackpot(lottery.get(0), dateBase);
    }

    public int getThirdClaw() {
        return Integer.parseInt(lottery.get(0).charAt(2) + "");
    }

    public int getSecondClaw() {
        return Integer.parseInt(lottery.get(0).charAt(3) + "");
    }

    public int getFirstClaw() {
        return Integer.parseInt(lottery.get(0).charAt(4) + "");
    }

    public Couple getJackpotCouple() {
        String jackpots = lottery.get(0);
        int first = Integer.parseInt(jackpots.charAt(3) + "");
        int second = Integer.parseInt(jackpots.charAt(4) + "");
        return new Couple(first, second, dateBase);
    }

    public List<Couple> getCoupleList() {
        List<Couple> couples = new ArrayList<>();
        for (String numberString : lottery) {
            int length_number = numberString.length();
            int first = Integer.parseInt(numberString.charAt(length_number - 2) + "");
            int second = Integer.parseInt(numberString.charAt(length_number - 1) + "");
            couples.add(new Couple(first, second, dateBase));
        }
        return couples;
    }
}
