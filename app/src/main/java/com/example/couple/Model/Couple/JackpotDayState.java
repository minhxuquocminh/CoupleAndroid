package com.example.couple.Model.Couple;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum JackpotDayState {
    INVALID_DATE(-1, "INVALID_DATE"),
    DAY_OFF(0, "DAY_OFF"),
    FUTURE_DAY(1, "FUTURE_DAY");

    public final int value;
    public final String name;
}
