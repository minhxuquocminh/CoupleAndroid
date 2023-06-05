package com.example.couple.Model.Support;

import com.example.couple.Model.Origin.Jackpot;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class JackpotHistory {
    int dayNumberBefore;
    Jackpot jackpot;

    public static JackpotHistory getEmpty() {
        return new JackpotHistory(0, Jackpot.getEmpty());
    }

    public boolean isEmpty() {
        return dayNumberBefore == 0 || jackpot.isEmpty();
    }

    public String show() {
        return "KQ: " + jackpot.getJackpot() + " (" + dayNumberBefore + " ngày trước)";
    }

}
