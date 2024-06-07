package com.example.couple.Base.Handler;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

public class NumberBase {

    public static List<Integer> getMatchNumbers(List<Integer> firstList, List<Integer> secondList) {
        if (firstList.isEmpty() && secondList.isEmpty()) return new ArrayList<>();
        if (firstList.isEmpty()) return secondList;
        if (secondList.isEmpty()) return firstList;
        return firstList.stream().filter(secondList::contains).collect(Collectors.toList());
    }

    // numberLength = 1 : số nhỏ hơn 10, numberLength = 2 số nhỏ hơn 100,....
    public static List<Integer> verifyNumberArray(String numberArray, int numberLength) {
        if (numberArray.isEmpty()) return new ArrayList<>();
        List<Integer> numbers = new ArrayList<>();
        int LIMIT = (int) (Math.pow(10, numberLength) - 1);
        String[] numberArr = numberArray.split(" ");
        try {
            for (String numberStr : numberArr) {
                if (numberStr.trim().isEmpty()) continue;
                int number = Integer.parseInt(numberStr.trim());
                if (number < 0 || number > LIMIT) {
                    return new ArrayList<>();
                }
                numbers.add(number);
            }
        } catch (Exception ignored) {

        }
        return numbers;
    }

    public static String showNumbers(List<Integer> numbers, String delimiter) {
        if (numbers.isEmpty()) return "";
        StringBuilder data = new StringBuilder();
        for (int i = 0; i < numbers.size(); i++) {
            data.append(numbers.get(i)).append(i == numbers.size() - 1 ? "" : delimiter);
        }
        return data.toString();
    }

    public static String showNumbers(List<Integer> numbers, int numberLength, String delimiter) {
        if (numbers.isEmpty()) return "";
        StringBuilder data = new StringBuilder();
        for (int i = 0; i < numbers.size(); i++) {
            data.append(showNumberString(numbers.get(i), numberLength)).append(i == numbers.size() - 1 ? "" : delimiter);
        }
        return data.toString();
    }

    public static String showNumberString(int number, int numberLength) {
        if (number < 0 || number > Math.pow(10, numberLength) - 1) return "";
        if (numberLength == 1) return number + "";
        int lengthNum = (number + "").length();
        StringBuilder numberStr = new StringBuilder();
        for (int i = lengthNum; i < numberLength; i++) {
            numberStr.append("0");
        }
        return numberStr + "" + number;
    }

    public static int randomInt(int limit) {
        Random rd = new Random();
        return rd.nextInt(limit);
    }
}
