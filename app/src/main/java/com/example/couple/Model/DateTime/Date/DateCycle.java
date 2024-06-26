package com.example.couple.Model.DateTime.Date;

import com.example.couple.Model.DateTime.Date.Cycle.Cycle;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class DateCycle {
    Cycle day;
    Cycle month;
    Cycle year;

    public static DateCycle getEmpty() {
        return new DateCycle(Cycle.getEmpty(), Cycle.getEmpty(), Cycle.getEmpty());
    }

    public boolean isEmpty() {
        return day.isEmpty() || month.isEmpty() || year.isEmpty();
    }

    public DateCycle addDaysSameMonth(int numberOfDays) {
        return new DateCycle(day.addDays(numberOfDays), month, year);
    }

    public String show() {
        return day.getName() + ", " + month.getName();
    }

}
