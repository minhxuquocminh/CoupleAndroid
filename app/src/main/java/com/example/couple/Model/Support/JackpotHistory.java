package com.example.couple.Model.Support;

import com.example.couple.Model.Origin.Jackpot;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Getter
public class JackpotHistory {
    int dayNumberBefore;
    Jackpot jackpot;

    public JackpotHistory() {
        this.dayNumberBefore = -1;
        jackpot = Jackpot.getEmptyJackpot();
    }

    public boolean isEmpty() {
        return dayNumberBefore == -1;
    }

    public String getJackpotString() {
        return jackpot.getJackpot();
    }

    public String show() {
        return "KQ: " + jackpot.getJackpot() + " (" + dayNumberBefore + " ngày trước)";
    }

}
