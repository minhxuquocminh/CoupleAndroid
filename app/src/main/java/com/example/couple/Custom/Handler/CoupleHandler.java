package com.example.couple.Custom.Handler;

import com.example.couple.Model.Support.JackpotHistory;

import java.util.ArrayList;
import java.util.List;

public class CoupleHandler {

    public static boolean isWin(JackpotHistory jackpotHistory, List<Integer> couples) {
        if (jackpotHistory.isEmpty() || couples.isEmpty()) return false;
        int jackpotCouple = jackpotHistory.getJackpot().getCoupleInt();
        for (int couple : couples) {
            if (couple == jackpotCouple) return true;
        }
        return false;
    }

    public static boolean isTouch(JackpotHistory jackpotHistory, List<Integer> touchs) {
        if (jackpotHistory.isEmpty() || touchs.isEmpty()) return false;
        int couple = jackpotHistory.getJackpot().getCoupleInt();
        for (int touch : touchs) {
            if (touch == couple / 10 || touch == couple % 10) return true;
        }
        return false;
    }

    public static List<Integer> getPeriodNumbers(int weight, int ampliude) {
        List<Integer> numbers = new ArrayList<>();
        int start = weight - ampliude < 0 ? 0 : weight - ampliude;
        int end = weight + ampliude > 99 ? 99 : weight + ampliude;
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
