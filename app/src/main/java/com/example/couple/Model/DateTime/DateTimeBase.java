package com.example.couple.Model.DateTime;

import com.example.couple.Model.DateTime.Date.DateBase;
import com.example.couple.Model.DateTime.Time.TimeBase;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class DateTimeBase {
    DateBase dateBase;
    TimeBase timeBase;

    public static DateTimeBase getEmpty() {
        return new DateTimeBase(DateBase.getEmpty(), TimeBase.getEmpty());
    }

    public boolean isEmpty() {
        return dateBase.isEmpty();
    }

    public boolean isAfter(DateTimeBase dateTimeBase) {
        if (dateBase.isBefore(dateTimeBase.getDateBase())) return false;
        if (dateBase.isAfter(dateTimeBase.getDateBase())) return true;
        return timeBase.isAfter(dateTimeBase.getTimeBase());
    }

    public boolean isBefore(DateTimeBase dateTimeBase) {
        if (dateBase.isAfter(dateTimeBase.getDateBase())) return false;
        if (dateBase.isBefore(dateTimeBase.getDateBase())) return true;
        return timeBase.isBefore(dateTimeBase.getTimeBase());
    }

}
