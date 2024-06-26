package com.example.couple.Model.DateTime.Date;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class DateData {
    DateBase dateBase;
    DateLunar dateLunar;
    DateCycle dateCycle;

    public static DateData getEmpty() {
        return new DateData(DateBase.getEmpty(), DateLunar.getEmpty(), DateCycle.getEmpty());
    }

    public boolean isEmpty() {
        return dateBase.isEmpty() || dateLunar.isEmpty() || dateCycle.isEmpty();
    }
}
