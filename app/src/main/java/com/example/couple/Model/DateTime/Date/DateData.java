package com.example.couple.Model.DateTime.Date;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class DateData {
    DateBase dateBase;
    DateLunar dateLunar;
    DateCycle dateCycle;

    public DateData() {
        this.dateBase = DateBase.getEmpty();
        this.dateLunar = DateLunar.getEmpty();
        this.dateCycle = DateCycle.getEmpty();
    }

    public static DateData getEmpty() {
        return new DateData();
    }

    public boolean isEmpty() {
        return dateBase.isEmpty() || dateLunar.isEmpty() || dateCycle.isEmpty();
    }
}
