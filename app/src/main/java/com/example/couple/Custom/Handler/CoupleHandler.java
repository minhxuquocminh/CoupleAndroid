package com.example.couple.Custom.Handler;

import com.example.couple.Model.Support.JackpotHistory;

import java.util.ArrayList;
import java.util.List;

public class CoupleHandler {

    public static int minusOne(int single) {
        if (single > 9 || single < 0) return -1;
        return single == 0 ? 9 : single - 1;
    }

    public static int reverse(int couple) {
        if (couple < 0 || couple > 99) return -1;
        return (couple % 10) * 10 + (couple / 10);
    }

    public static int getNegativeShadow(int single) {
        if (single > 9 || single < 0) return -1;
        int arr[] = {7, 4, 9, 6, 1, 8, 3, 0, 5, 2};
        return arr[single];
    }

    public static int getShadow(int number) {
        if (number > 9 || number < 0) return -1;
        if (number - 5 >= 0) return number - 5;
        return number + 5;
    }

    public static int getSmallShadow(int number) {
        if (number < 0 || number > 99) return -1;
        if (number < 10) {
            if (number >= 5) return number - 5;
            else return number;
        }
        String numberStr = number + "";
        int first = Integer.parseInt(numberStr.charAt(0) + "");
        int second = Integer.parseInt(numberStr.charAt(1) + "");
        int shadow1 = getSmallShadow(first);
        int shadow2 = getSmallShadow(second);
        return shadow1 < shadow2 ? Integer.parseInt(shadow1 + "" + shadow2) :
                Integer.parseInt(shadow2 + "" + shadow1);
    }

    public static boolean isWin(JackpotHistory jackpotHistory, List<Integer> numbers) {
        if (jackpotHistory.isEmpty() || numbers.isEmpty()) return false;
        int couple = jackpotHistory.getJackpot().getCoupleInt();
        for (int number : numbers) {
            if (number == couple) return true;
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

    public static String showCoupleNumbers(List<Integer> numbers) {
        String show = "";
        for (int number : numbers) {
            show += showCouple(number) + " ";
        }
        return show.trim();
    }

    public static String showCoupleNumbers(List<Integer> numbers, String delimiter) {
        String show = "";
        for (int i = 0; i < numbers.size(); i++) {
            show += showCouple(numbers.get(i)) + (i == numbers.size() - 1 ? "" : delimiter);
        }
        return show.trim();
    }

    public static String showTouchs(List<Integer> touchs) {
        String show = "";
        for (int touch : touchs) {
            show += touch + " ";
        }
        return show.trim();
    }

    public static String showTouchs(List<Integer> touchs, String delimiter) {
        String show = "";
        for (int i = 0; i < touchs.size(); i++) {
            show += touchs.get(i) + (i == touchs.size() - 1 ? "" : delimiter);
        }
        return show.trim();
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

    public static List<Integer> getMappingNumbers(int number) {
        if (number < 0 || number > 99) return new ArrayList<>();
        int first = number / 10;
        int second = number % 10;

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
        numbers.add(number);
        return numbers;
    }

    public static String showCouple(int number) {
        if (number < 0 || number > 99) return "";
        return number < 10 ? 0 + "" + number : number + "";
    }

}
