package com.example.couple.Custom.Handler;

import com.example.couple.Model.Bridge.JackpotHistory;

import java.util.ArrayList;
import java.util.List;

public class CoupleHandler {

    public static boolean isWin(JackpotHistory jackpotHistory, List<Integer> couples) {
        return couples.stream().anyMatch(x -> x == jackpotHistory.getJackpot().getCoupleInt());
    }

    public static List<Integer> getPeriodNumbers(int weight, int ampliude) {
        List<Integer> numbers = new ArrayList<>();
        int start = Math.max(weight - ampliude, 0);
        int end = Math.min(weight + ampliude, 99);
        for (int i = start; i <= end; i++) {
            numbers.add(i);
        }
        return numbers;
    }

    public static List<Integer> getMappingNumbers(int couple) {
        if (couple < 0 || couple > 99) return new ArrayList<>();
        int first = couple / 10;
        int second = couple % 10;

        List<Integer> numbers = new ArrayList<>();
        for (int i = 1; i <= first; i++) {
            int topLeft = (first - i) * 10 + second - i;
            if (second - i >= 0 && topLeft >= 0) {
                numbers.add(topLeft);
            }
            if (second + i <= 9) {
                numbers.add((first - i) * 10 + second + i);
            }
        }
        for (int i = 1; first + i <= 9; i++) {
            if (second - i >= 0) {
                numbers.add((first + i) * 10 + second - i);
            }

            int bottomRight = (first + i) * 10 + second + i;
            if (second + i <= 9 && bottomRight <= 99) {
                numbers.add(bottomRight);
            }
        }
        numbers.add(couple);
        return numbers;
    }

    public static List<Integer> getMappingLeftBottomNumbers(int couple) {
        if (couple < 0 || couple > 99) return new ArrayList<>();
        int first = couple / 10;
        int second = couple % 10;

        List<Integer> numbers = new ArrayList<>();
        for (int i = 1; i <= first; i++) {
            int topLeft = (first - i) * 10 + second - i;
            if (second - i >= 0 && topLeft >= 0) {
                numbers.add(topLeft);
            }
        }
        for (int i = 1; first + i <= 9; i++) {
            int bottomRight = (first + i) * 10 + second + i;
            if (second + i <= 9 && bottomRight <= 99) {
                numbers.add(bottomRight);
            }
        }
        numbers.add(couple);
        return numbers;
    }

    public static List<Integer> getMappingRightTopNumbers(int couple) {
        if (couple < 0 || couple > 99) return new ArrayList<>();
        int first = couple / 10;
        int second = couple % 10;

        List<Integer> numbers = new ArrayList<>();
        for (int i = 1; i <= first; i++) {
            if (second + i <= 9) {
                numbers.add((first - i) * 10 + second + i);
            }
        }
        for (int i = 1; first + i <= 9; i++) {
            if (second - i >= 0) {
                numbers.add((first + i) * 10 + second - i);
            }
        }
        numbers.add(couple);
        return numbers;
    }

}
