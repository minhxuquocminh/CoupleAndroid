package com.example.couple.Model.DateTime.Time;

import com.example.couple.Base.Handler.CoupleBase;
import com.example.couple.Custom.Const.Const;

import java.util.Calendar;

import lombok.Getter;

@Getter
public class TimeBase {
    int hour;
    int minute;
    int second;

    public TimeBase(int hour, int minute, int second) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, hour);
        calendar.set(Calendar.MINUTE, minute);
        calendar.set(Calendar.SECOND, second);
        this.hour = calendar.get(Calendar.HOUR_OF_DAY);
        this.minute = calendar.get(Calendar.MINUTE);
        this.second = calendar.get(Calendar.SECOND);
    }

    public Calendar toCalendar() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, hour);
        calendar.set(Calendar.MINUTE, minute);
        calendar.set(Calendar.SECOND, second);
        return calendar;
    }

    public TimeBase addSeconds(int second) {
        return new TimeBase(this.hour, this.minute, this.second + second);
    }

    public static TimeBase from(long milliseconds) {
        int seconds = (int) (milliseconds / 1000) % 60;
        int minutes = (int) (milliseconds / (1000 * 60)) % 60;
        int hours = (int) (milliseconds / (1000 * 60 * 60)) % 24;
        return new TimeBase(hours, minutes, seconds);
    }

    public static TimeBase CURRENT() {
        Calendar calendar = Calendar.getInstance();
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);
        int second = calendar.get(Calendar.SECOND);
        return new TimeBase(hour, minute, second);
    }

    public boolean isAfter(TimeBase timeBase) {
        if (this.hour < timeBase.getHour()) return false;
        if (this.hour > timeBase.getHour()) return true;
        if (this.minute < timeBase.getMinute()) return false;
        if (this.minute > timeBase.getMinute()) return true;
        return this.second > timeBase.getSecond();
    }

    public boolean isBefore(TimeBase timeBase) {
        if (this.hour > timeBase.getHour()) return false;
        if (this.hour < timeBase.getHour()) return true;
        if (this.minute > timeBase.getMinute()) return false;
        if (this.minute < timeBase.getMinute()) return true;
        return this.second < timeBase.getSecond();
    }

    public static TimeBase getEmpty() {
        return new TimeBase(Const.EMPTY_VALUE, Const.EMPTY_VALUE, Const.EMPTY_VALUE);
    }

    public boolean isEmpty() {
        return hour == Const.EMPTY_VALUE || minute == Const.EMPTY_VALUE || second == Const.EMPTY_VALUE;
    }

    public String showHHMM() {
        return CoupleBase.showCouple(hour) + ":" + CoupleBase.showCouple(minute);
    }

    public String showHHMMSS() {
        return CoupleBase.showCouple(hour) + ":" + CoupleBase.showCouple(minute) + ":" + CoupleBase.showCouple(second);
    }
}
