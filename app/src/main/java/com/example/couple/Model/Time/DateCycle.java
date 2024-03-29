package com.example.couple.Model.Time;

import com.example.couple.Model.Time.Cycle.Cycle;

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

    public DateCycle plusDaysSameMonth(int numberOfDays) {
        int index_day = day.getPosition();
        int new_index = numberOfDays + index_day < 0 ?
                60 + numberOfDays + index_day : (index_day + numberOfDays) % 60;
        return new DateCycle(Cycle.getCycle(new_index), month, year);
    }

    public String show() {
        return day.getCycle() + ", " + month.getCycle();
    }

}
