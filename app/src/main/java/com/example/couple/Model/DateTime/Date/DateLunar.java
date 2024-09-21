package com.example.couple.Model.DateTime.Date;

import com.example.couple.Base.Handler.CoupleBase;
import com.example.couple.Custom.Const.Const;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class DateLunar {
    int day;
    int month;
    int year;

    private DateLunar() {
        this.day = Const.EMPTY_VALUE;
        this.month = Const.EMPTY_VALUE;
        this.year = Const.EMPTY_VALUE;
    }

    public static DateLunar getEmpty() {
        return new DateLunar();
    }

    public boolean isEmpty() {
        return day == 0 || month == 0 || year == 0;
    }

    public DateLunar addDaysSameMonth(int numberOfDays) {
        if (this.isEmpty()) return DateLunar.getEmpty();
        if (numberOfDays + day < 1 || numberOfDays + day > 30) return DateLunar.getEmpty();
        return new DateLunar(day + numberOfDays, month, year);
    }

    public String showDDMM(String delimiter) {
        if (this.isEmpty()) return Const.EMPTY;
        return CoupleBase.showCouple(day) + delimiter + CoupleBase.showCouple(month);
    }

}
