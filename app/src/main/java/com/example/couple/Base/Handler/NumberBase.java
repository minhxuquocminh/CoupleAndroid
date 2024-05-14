package com.example.couple.Base.Handler;

import com.example.couple.Model.Display.Picker;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class NumberBase {

    public static List<Integer> getMatchNumbers(List<Integer> firstList,
                                                List<Integer> sencondList, List<Integer> thirdList) {
        if (firstList.isEmpty()) return getMatchNumbers(sencondList, thirdList);
        if (sencondList.isEmpty()) return getMatchNumbers(firstList, thirdList);
        if (thirdList.isEmpty()) return getMatchNumbers(firstList, sencondList);
        List<Integer> list = getMatchNumbers(firstList, sencondList);
        return getMatchNumbers(list, thirdList);
    }

    public static List<Integer> getMatchNumbers(List<Integer> firstList, List<Integer> secondList) {
        if (firstList.isEmpty() && secondList.isEmpty()) return new ArrayList<>();
        if (firstList.isEmpty()) return secondList;
        if (secondList.isEmpty()) return firstList;
        List<Integer> numbers = new ArrayList<>();
        for (int first : firstList) {
            if (secondList.contains(first)) {
                numbers.add(first);
            }
        }
        return numbers;
    }

    public static List<Integer> getPrivateNumbers(List<Integer> firstNumbers, List<Integer> secondNumbers) {
        if (firstNumbers.isEmpty() || secondNumbers.isEmpty()) return new ArrayList<>();
        if (firstNumbers.size() > secondNumbers.size()) return firstNumbers;
        if (secondNumbers.size() > firstNumbers.size()) return secondNumbers;
        List<Integer> numbers = new ArrayList<>();
        for (int first : firstNumbers) {
            if (!secondNumbers.contains(first)) {
                numbers.add(first);
            }
        }
        for (int second : secondNumbers) {
            if (!firstNumbers.contains(second)) {
                numbers.add(second);
            }
        }
        Collections.sort(numbers);
        return filterDuplicatedNumbers(numbers);
    }

    public static List<Integer> filterDuplicatedNumbers(List<Integer> numbers) {
        if (numbers.isEmpty()) return new ArrayList<>();
        List<Integer> filteredNumber = new ArrayList<>();
        for (int number : numbers) {
            if (!filteredNumber.contains(number)) {
                filteredNumber.add(number);
            }
        }
        return filteredNumber;
    }

    public static List<Integer> getDuplicatedNumbers(List<Integer> numbers) {
        if (numbers.isEmpty()) return new ArrayList<>();
        List<Integer> results = new ArrayList<>();
        List<Integer> initNumbers = new ArrayList<>();
        for (int number : numbers) {
            if (!initNumbers.contains(number)) {
                initNumbers.add(number);
            } else {
                results.add(number);
            }
        }
        return results;
    }

    public static List<Integer> getReverseList(List<Integer> numbers) {
        if (numbers.isEmpty()) return new ArrayList<>();
        List<Integer> result = new ArrayList<>();
        for (int i = numbers.size() - 1; i >= 0; i--) {
            result.add(numbers.get(i));
        }
        return result;
    }

    // numberLength = 1 : số nhỏ hơn 10, numberLength = 2 số nhỏ hơn 100,....
    public static List<Picker> verifyNumberArr(String numberArray, int numberLength) {
        if (numberArray.isEmpty()) return new ArrayList<>();
        int LIMIT = (int) (Math.pow(10, numberLength) - 1);
        List<Picker> pickers = new ArrayList<>();
        String[] numberArr = numberArray.split(" ");
        try {
            for (String numberStr : numberArr) {
                int number = Integer.parseInt(numberStr.trim());
                // check giá trị và trường hợp đầu có số 0
                if (number < 0 || number > LIMIT || numberStr.trim().length() > numberLength) {
                    return new ArrayList<>();
                }
                pickers.add(new Picker(number, 1));
            }
        } catch (Exception ignored) {
        }
        return pickers;
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
                // check giá trị và trường hợp đầu có số 0
                if (number < 0 || number > LIMIT || numberStr.trim().length() > numberLength) {
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

    public static List<Integer> parseString(String data, String split) {
        if (data.trim().isEmpty()) return new ArrayList<>();
        String[] arr = data.split(split);
        List<Integer> numbers = new ArrayList<>();
        try {
            for (String s : arr) {
                numbers.add(Integer.parseInt(s.trim()));
            }
        } catch (Exception ignored) {
        }
        return numbers;
    }
}
