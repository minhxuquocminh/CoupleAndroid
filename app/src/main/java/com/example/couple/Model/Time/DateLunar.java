package com.example.couple.Model.Time;

import com.example.couple.Base.Handler.CoupleBase;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class DateLunar {
    int day;
    int month;
    int year;

    public static DateLunar getEmpty() {
        return new DateLunar(0, 0, 0);
    }

    public boolean isEmpty() {
        return day == 0 || month == 0 || year == 0;
    }

    public DateLunar plusDaysSameMonth(int numberOfDays) {
        if (numberOfDays + day < 1 || numberOfDays + day > 30) return DateLunar.getEmpty();
        return new DateLunar(day + numberOfDays, month, year);
    }

    public String showDDMM(String delimiter) {
        return CoupleBase.showCouple(day) + delimiter + CoupleBase.showCouple(month);
    }

}
