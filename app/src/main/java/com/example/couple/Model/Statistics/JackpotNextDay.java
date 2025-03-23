package com.example.couple.Model.Statistics;

import com.example.couple.Model.Origin.Jackpot;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class JackpotNextDay {
    Jackpot jackpotFirst;
    Jackpot jackpotSecond;

    public boolean isError() {
        return jackpotFirst.isEmpty() || jackpotSecond.isEmpty();
    }
}
