package com.example.couple.Model.DateTime.Date;

import com.example.couple.Custom.Const.Const;
import com.example.couple.Model.DateTime.Date.Cycle.Cycle;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class DateCycle {
    Cycle day;
    Cycle month;
    Cycle year;

    private DateCycle() {
        this.day = Cycle.getEmpty();
        this.month = Cycle.getEmpty();
        this.year = Cycle.getEmpty();
    }

    public static DateCycle getEmpty() {
        return new DateCycle();
    }

    public boolean isEmpty() {
        return day.isEmpty() || month.isEmpty() || year.isEmpty();
    }

    public DateCycle addDaysSameMonth(int numberOfDays) {
        if (this.isEmpty()) return DateCycle.getEmpty();
        return new DateCycle(day.addDays(numberOfDays), month, year);
    }

    public String show() {
        if (this.isEmpty()) return Const.EMPTY;
        return day.getName() + ", " + month.getName();
    }

}
