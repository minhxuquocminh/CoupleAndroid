package com.example.couple.Model.Time;

import com.example.couple.Custom.Const.Const;
import com.example.couple.Custom.Const.TimeInfo;
import com.example.couple.Custom.Handler.CoupleHandler;

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
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@ToString
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

    public DateBase plusDays(int numberOfDays) {
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

    public boolean isToday() {
        return day == TimeInfo.CURRENT_DAY && month == TimeInfo.CURRENT_MONTH && year == TimeInfo.CURRENT_YEAR;
    }

    public boolean isItOnSunday() {
        Date date = toDate();
        if (date == null) return false;
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(Calendar.DAY_OF_WEEK) == 1;
    }

    public boolean isItOnSaturday() {
        Date date = toDate();
        if (date == null) return false;
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(Calendar.DAY_OF_WEEK) == 7;
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

    public Date toDate() {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
            sdf.setLenient(false); // chi lay ngay dung
            return sdf.parse(day + "-" + month + "-" + year);
        } catch (ParseException e) {
            return null;
        }
    }

    public static DateBase getCurrentDate() {
        return new DateBase(TimeInfo.CURRENT_DAY, TimeInfo.CURRENT_MONTH, TimeInfo.CURRENT_YEAR);
    }

    public static DateBase fromString(String s, String splitRegex) {
        String sub[] = s.split(splitRegex);
        if (sub.length < 3) return null;
        return new DateBase(Integer.parseInt(sub[0]), Integer.parseInt(sub[1]), Integer.parseInt(sub[2]));
    }

    public String showDot() {
        return day + "." + month + "." + year;
    }

    public String showFullChars() {
        String dayStr = day < 10 ? "0" + day : "" + day;
        String monthStr = month < 10 ? "0" + month : "" + month;
        return dayStr + "-" + monthStr + "-" + year;
    }

    public String showDDMM(String delimiter) {
        return CoupleHandler.showCouple(day) + delimiter + CoupleHandler.showCouple(month);
    }

}
