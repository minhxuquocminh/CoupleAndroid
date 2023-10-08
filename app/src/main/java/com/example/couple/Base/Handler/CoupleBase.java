package com.example.couple.Base.Handler;

import java.util.List;

public class CoupleBase {

    public static int reverse(int couple) {
        if (couple < 0 || couple > 99) return -1;
        return (couple % 10) * 10 + (couple / 10);
    }


    public static int getSmallShadow(int couple) {
        if (couple < 0 || couple > 99) return -1;
        if (couple < 10) {
            if (couple >= 5) return couple - 5;
            else return couple;
        }
        String numberStr = couple + "";
        int first = Integer.parseInt(numberStr.charAt(0) + "");
        int second = Integer.parseInt(numberStr.charAt(1) + "");
        int shadow1 = getSmallShadow(first);
        int shadow2 = getSmallShadow(second);
        return shadow1 < shadow2 ? Integer.parseInt(shadow1 + "" + shadow2) :
                Integer.parseInt(shadow2 + "" + shadow1);
    }

    public static String showCoupleNumbers(List<Integer> couples) {
        String show = "";
        for (int couple : couples) {
            show += showCouple(couple) + " ";
        }
        return show.trim();
    }

    public static String showCoupleNumbers(List<Integer> couples, String delimiter) {
        String show = "";
        for (int i = 0; i < couples.size(); i++) {
            show += showCouple(couples.get(i)) + (i == couples.size() - 1 ? "" : delimiter);
        }
        return show.trim();
    }

    public static String showCouple(int couple) {
        if (couple < 0 || couple > 99) return "";
        return couple < 10 ? 0 + "" + couple : couple + "";
    }

}
