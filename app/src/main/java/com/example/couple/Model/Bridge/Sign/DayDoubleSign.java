package com.example.couple.Model.Bridge.Sign;

import com.example.couple.Model.Origin.Jackpot;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class DayDoubleSign {
    Jackpot jackpot;
    int dayNumberBefore;
}
