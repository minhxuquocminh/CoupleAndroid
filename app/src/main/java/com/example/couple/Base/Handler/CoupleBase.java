package com.example.couple.Base.Handler;

import java.util.List;

public class CoupleBase {

    public static int reverse(int couple) {
        if (couple < 0 || couple > 99) return -1;
        return (couple % 10) * 10 + (couple / 10);
    }

    public static int getSmallShadow(int couple) {
        if (couple < 0 || couple > 99) return -1;
        if (couple < 5) return couple;
        if (couple < 10) return couple - 5;
        int shadow1 = getSmallShadow(couple / 10);
        int shadow2 = getSmallShadow(couple % 10);
        return shadow1 < shadow2 ? shadow1 * 10 + shadow2 : shadow2 * 10 + shadow1;
    }

    public static String showCoupleNumbers(List<Integer> couples) {
        StringBuilder show = new StringBuilder();
        for (int couple : couples) {
            show.append(showCouple(couple)).append(" ");
        }
        return show.toString().trim();
    }

    public static String showCoupleNumbers(List<Integer> couples, String delimiter) {
        StringBuilder show = new StringBuilder();
        for (int i = 0; i < couples.size(); i++) {
            show.append(showCouple(couples.get(i))).append(i == couples.size() - 1 ? "" : delimiter);
        }
        return show.toString().trim();
    }

    public static String showCouple(int couple) {
        if (couple < 0 || couple > 99) return "";
        return couple < 10 ? 0 + "" + couple : couple + "";
    }

}
