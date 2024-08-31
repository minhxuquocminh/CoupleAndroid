package com.example.couple.Model.DateTime.Date;

import android.annotation.SuppressLint;

import com.example.couple.Base.Handler.CoupleBase;
import com.example.couple.Custom.Const.Const;
import com.example.couple.Custom.Const.TimeInfo;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@EqualsAndHashCode
public class DateBase implements Serializable {
    int day;
    int month;
    int year;

    public static DateBase getEmpty() {
        return new DateBase(Const.EMPTY_VALUE, Const.EMPTY_VALUE, Const.EMPTY_VALUE);
    }

    public boolean isEmpty() {
        return day == Const.EMPTY_VALUE || month == Const.EMPTY_VALUE || year == Const.EMPTY_VALUE;
    }

    public static DateBase TO_DAY() {
        return new DateBase(TimeInfo.CURRENT_DAY, TimeInfo.CURRENT_MONTH, TimeInfo.CURRENT_YEAR);
    }

    public static DateBase getYearLastDate(int year) {
        return new DateBase(TimeInfo.DAY_OF_MONTH, TimeInfo.MONTH_OF_YEAR, year);
    }

    public static DateBase getYearStartDate(int year) {
        return new DateBase(1, 1, year);
    }

    public boolean isDateLastYear() {
        return this.year == TimeInfo.CURRENT_YEAR - 1;
    }

    public boolean isAfter(DateBase dateBase) {
        if (this.year < dateBase.getYear()) return false;
        if (this.year > dateBase.getYear()) return true;
        if (this.month < dateBase.getMonth()) return false;
        if (this.month > dateBase.getMonth()) return true;
        return this.day > dateBase.getDay();
    }

    public boolean isBefore(DateBase dateBase) {
        if (this.year > dateBase.getYear()) return false;
        if (this.year < dateBase.getYear()) return true;
        if (this.month > dateBase.getMonth()) return false;
        if (this.month < dateBase.getMonth()) return true;
        return this.day < dateBase.getDay();
    }

    public DateBase addDays(int numberOfDays) {
        Date date = toDate();
        if (date == null) return null;
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DATE, numberOfDays);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        int month = calendar.get(Calendar.MONTH) + 1;
        int year = calendar.get(Calendar.YEAR);
        return new DateBase(day, month, year);
    }

    public long countUp() {
        Date today = Calendar.getInstance().getTime();
        Date thatday = this.toDate();
        long diff = today.getTime() - thatday.getTime();
        return TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS);
    }

    public boolean isLastMonthOf(DateBase dateBase) {
        if (!this.isValid() || !dateBase.isValid()) return false;
        if (dateBase.getMonth() == 1) {
            return day == dateBase.getDay() && month == 12 && year == dateBase.getYear() - 1;
        }

        return day == dateBase.getDay() && month == dateBase.getMonth() - 1 && year == dateBase.getYear();
    }

    public DateBase getLastMonth() {
        DateBase newDate = DateBase.getEmpty();
        if (month == 1) {
            newDate = new DateBase(day, 12, year - 1);
        } else {
            newDate = new DateBase(day, month - 1, year);
        }
        return newDate.isValid() ? newDate : DateBase.getEmpty();
    }

    public boolean isUpLastMonthOf(DateBase dateBase) {
        if (!this.isValid() || !dateBase.isValid()) return false;
        if (dateBase.getDay() == 1) return false;
        if (dateBase.getMonth() == 1) {
            return day == dateBase.getDay() - 1 && month == 12 && year == dateBase.getYear() - 1;
        }

        return day == dateBase.getDay() - 1 && month == dateBase.getMonth() - 1 && year == dateBase.getYear();
    }

    public DateBase getUpLastMonth() {
        DateBase newDate = DateBase.getEmpty();
        if (month == 1) {
            newDate = new DateBase(day - 1, 12, year - 1);
        } else {
            newDate = new DateBase(day - 1, month - 1, year);
        }
        return newDate.isValid() ? newDate : DateBase.getEmpty();
    }

    public boolean isDownLastMonthOf(DateBase dateBase) {
        if (!this.isValid() || !dateBase.isValid()) return false;
        DateBase lastMonth = dateBase.getLastMonth();
        DateBase addDay = lastMonth.addDays(1);
        if (addDay.getMonth() != lastMonth.getMonth()) return false;
        if (dateBase.getMonth() == 1) {
            return day == dateBase.getDay() + 1 && month == 12 && year == dateBase.getYear() - 1;
        }

        return day == dateBase.getDay() + 1 && month == dateBase.getMonth() - 1 && year == dateBase.getYear();
    }

    public DateBase getDownLastMonth() {
        DateBase newDate = DateBase.getEmpty();
        if (month == 1) {
            newDate = new DateBase(day + 1, 12, year - 1);
        } else {
            newDate = new DateBase(day + 1, month - 1, year);
        }
        return newDate.isValid() ? newDate : DateBase.getEmpty();
    }

    public boolean isLastWeekOf(DateBase dateBase) {
        return this.addDays(7).equals(dateBase);
    }

    public DateBase getLastWeek() {
        return this.addDays(-7);
    }

    public boolean isToday() {
        return day == TimeInfo.CURRENT_DAY && month == TimeInfo.CURRENT_MONTH && year == TimeInfo.CURRENT_YEAR;
    }

    public boolean isItOnSunday() {
        Date date = toDate();
        if (date == null) return false;
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY;
    }

    public boolean isItOnSaturday() {
        Date date = toDate();
        if (date == null) return false;
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY;
    }

    public int getDayOfWeek() {
        Date date = toDate();
        if (date == null) return 0;
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(Calendar.DAY_OF_WEEK);
    }

    public boolean isValid() {
        if (month > 12 || month < 1) {
            return false;
        }
        if (month == 4 || month == 6 || month == 9 || month == 11) {
            return day <= 30 && day >= 1;
        }
        if (month == 2) {
            if (year % 4 == 0 && year % 100 != 0) {
                return day <= 29 && day >= 1;
            } else {
                return day <= 28 && day >= 1;
            }
        }
        return day <= 31 && day >= 1;
    }

    @SuppressLint("SimpleDateFormat")
    public Date toDate() {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
            sdf.setLenient(false); // chi lay ngay dung
            return sdf.parse(day + "-" + month + "-" + year);
        } catch (ParseException e) {
            return null;
        }
    }

    public long distance(DateBase dateBase) {
        return (long) ((dateBase.toDate().getTime() - this.toDate().getTime()) / (24 * 60 * 60 * 1000));
    }

    public static DateBase getCurrentDate() {
        return new DateBase(TimeInfo.CURRENT_DAY, TimeInfo.CURRENT_MONTH, TimeInfo.CURRENT_YEAR);
    }

    public String toString(String delimiter) {
        return day + delimiter + month + delimiter + year;
    }

    public static DateBase fromString(String data, String splitRegex) {
        String[] sub = data.split(splitRegex);
        if (sub.length < 3) return DateBase.getEmpty();
        return new DateBase(Integer.parseInt(sub[0]), Integer.parseInt(sub[1]), Integer.parseInt(sub[2]));
    }

    public String showFullChars() {
        String dayStr = day < 10 ? "0" + day : "" + day;
        String monthStr = month < 10 ? "0" + month : "" + month;
        return dayStr + "-" + monthStr + "-" + year;
    }

    public String showDDMM(String delimiter) {
        return CoupleBase.showCouple(day) + delimiter + CoupleBase.showCouple(month);
    }

}
