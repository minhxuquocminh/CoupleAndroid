package com.example.couple.Custom.Handler;

import com.example.couple.Model.DateTime.Date.Cycle.Branch;
import com.example.couple.Model.DateTime.Date.Cycle.Cycle;
import com.example.couple.Model.DateTime.Date.Cycle.Stem;
import com.example.couple.Model.DateTime.Date.DateBase;
import com.example.couple.Model.DateTime.Date.DateCycle;
import com.example.couple.Model.DateTime.Date.DateData;
import com.example.couple.Model.DateTime.Date.DateLunar;

public class LunarCalendarHandler {
    private static final double TIME_ZONE = 7.0;

    public static DateData getDateData(DateBase dateBase) {
        if (dateBase == null || dateBase.isEmpty()) return DateData.getEmpty();
        int[] lunar = convertSolarToLunar(dateBase.getDay(), dateBase.getMonth(), dateBase.getYear());
        DateLunar dateLunar = new DateLunar(lunar[0], lunar[1], lunar[2]);
        DateCycle dateCycle = new DateCycle(
                getDayCycle(jdFromDate(dateBase.getDay(), dateBase.getMonth(), dateBase.getYear())),
                getMonthCycle(lunar[1], lunar[2]),
                getYearCycle(lunar[2])
        );
        return new DateData(dateBase, dateLunar, dateCycle);
    }

    private static int jdFromDate(int day, int month, int year) {
        int a = (14 - month) / 12;
        int y = year + 4800 - a;
        int m = month + 12 * a - 3;
        int jd = day + (153 * m + 2) / 5 + 365 * y + y / 4 - y / 100 + y / 400 - 32045;
        if (jd < 2299161) {
            jd = day + (153 * m + 2) / 5 + 365 * y + y / 4 - 32083;
        }
        return jd;
    }

    private static int getNewMoonDay(int k) {
        double t = k / 1236.85;
        double t2 = t * t;
        double t3 = t2 * t;
        double dr = Math.PI / 180;
        double jd1 = 2415020.75933 + 29.53058868 * k + 0.0001178 * t2
                - 0.000000155 * t3;
        jd1 += 0.00033 * Math.sin((166.56 + 132.87 * t - 0.009173 * t2) * dr);
        double m = 359.2242 + 29.10535608 * k - 0.0000333 * t2 - 0.00000347 * t3;
        double mpr = 306.0253 + 385.81691806 * k + 0.0107306 * t2 + 0.00001236 * t3;
        double f = 21.2964 + 390.67050646 * k - 0.0016528 * t2 - 0.00000239 * t3;
        double c1 = (0.1734 - 0.000393 * t) * Math.sin(m * dr)
                + 0.0021 * Math.sin(2 * dr * m);
        c1 -= 0.4068 * Math.sin(mpr * dr) + 0.0161 * Math.sin(2 * dr * mpr);
        c1 -= 0.0004 * Math.sin(3 * dr * mpr);
        c1 += 0.0104 * Math.sin(2 * dr * f) - 0.0051 * Math.sin((m + mpr) * dr);
        c1 -= 0.0074 * Math.sin((m - mpr) * dr) + 0.0004 * Math.sin((2 * f + m) * dr);
        c1 -= 0.0004 * Math.sin((2 * f - m) * dr) - 0.0006 * Math.sin((2 * f + mpr) * dr);
        c1 += 0.0010 * Math.sin((2 * f - mpr) * dr) + 0.0005 * Math.sin((2 * mpr + m) * dr);
        double deltaT;
        if (t < -11) {
            deltaT = 0.001 + 0.000839 * t + 0.0002261 * t2
                    - 0.00000845 * t3 - 0.000000081 * t * t3;
        } else {
            deltaT = -0.000278 + 0.000265 * t + 0.000262 * t2;
        }
        return (int) Math.floor(jd1 + c1 - deltaT + 0.5 + TIME_ZONE / 24);
    }

    private static int getSunLongitude(int jdn) {
        double t = (jdn - 2451545.5 - TIME_ZONE / 24) / 36525;
        double t2 = t * t;
        double dr = Math.PI / 180;
        double m = 357.52910 + 35999.05030 * t - 0.0001559 * t2 - 0.00000048 * t * t2;
        double l0 = 280.46645 + 36000.76983 * t + 0.0003032 * t2;
        double dl = (1.914600 - 0.004817 * t - 0.000014 * t2) * Math.sin(dr * m);
        dl += (0.019993 - 0.000101 * t) * Math.sin(2 * dr * m)
                + 0.000290 * Math.sin(3 * dr * m);
        double l = l0 + dl;
        l = l * dr;
        l = l - Math.PI * 2 * Math.floor(l / (Math.PI * 2));
        return (int) Math.floor(l / Math.PI * 6);
    }

    private static int getLunarMonth11(int year) {
        int off = jdFromDate(31, 12, year) - 2415021;
        int k = (int) Math.floor(off / 29.530588853);
        int nm = getNewMoonDay(k);
        int sunLong = getSunLongitude(nm);
        if (sunLong >= 9) {
            nm = getNewMoonDay(k - 1);
        }
        return nm;
    }

    private static int getLeapMonthOffset(int a11) {
        int k = (int) Math.floor((a11 - 2415021.076998695) / 29.530588853 + 0.5);
        int last = 0;
        int i = 1;
        int arc = getSunLongitude(getNewMoonDay(k + i));
        do {
            last = arc;
            i++;
            arc = getSunLongitude(getNewMoonDay(k + i));
        } while (arc != last && i < 14);
        return i - 1;
    }

    private static int[] convertSolarToLunar(int day, int month, int year) {
        int dayNumber = jdFromDate(day, month, year);
        int k = (int) Math.floor((dayNumber - 2415021.076998695) / 29.530588853);
        int monthStart = getNewMoonDay(k + 1);
        if (monthStart > dayNumber) {
            monthStart = getNewMoonDay(k);
        }
        int a11 = getLunarMonth11(year);
        int b11 = a11;
        int lunarYear;
        if (a11 >= monthStart) {
            lunarYear = year;
            a11 = getLunarMonth11(year - 1);
        } else {
            lunarYear = year + 1;
            b11 = getLunarMonth11(year + 1);
        }
        int lunarDay = dayNumber - monthStart + 1;
        int diff = (int) Math.floor((monthStart - a11) / 29.0);
        int lunarLeap = 0;
        int lunarMonth = diff + 11;
        if (b11 - a11 > 365) {
            int leapMonthDiff = getLeapMonthOffset(a11);
            if (diff >= leapMonthDiff) {
                lunarMonth = diff + 10;
                if (diff == leapMonthDiff) {
                    lunarLeap = 1;
                }
            }
        }
        if (lunarMonth > 12) {
            lunarMonth -= 12;
        }
        if (lunarMonth >= 11 && diff < 4) {
            lunarYear -= 1;
        }
        return new int[]{lunarDay, lunarMonth, lunarYear, lunarLeap};
    }

    private static Cycle getDayCycle(int jd) {
        int stemPosition = positiveMod(jd + 3, 10);
        int branchPosition = positiveMod(jd + 1, 12);
        return Cycle.getByStemAndBranch(new Stem(stemPosition), new Branch(branchPosition));
    }

    private static Cycle getMonthCycle(int lunarMonth, int lunarYear) {
        int stemPosition = positiveMod(lunarYear * 12 + lunarMonth + 7, 10);
        int branchPosition = positiveMod(lunarMonth + 1, 12);
        return Cycle.getByStemAndBranch(new Stem(stemPosition), new Branch(branchPosition));
    }

    private static Cycle getYearCycle(int lunarYear) {
        return new Cycle(positiveMod(lunarYear - 1900, 60));
    }

    private static int positiveMod(int value, int mod) {
        int result = value % mod;
        return result < 0 ? result + mod : result;
    }
}
