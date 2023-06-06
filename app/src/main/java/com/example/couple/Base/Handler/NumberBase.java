package com.example.couple.Base.Handler;

import com.example.couple.Model.Display.Number;
import com.example.couple.Model.Support.JackpotHistory;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class NumberBase {

    public static double rounding(double number) {
        return Math.round(number * 100) / 100;
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

    public static int minusOne(int single) {
        if (single > 9 || single < 0) return -1;
        return single == 0 ? 9 : single - 1;
    }

    public static String showNumbers(List<Integer> numbers) {
        String show = "";
        for (int number : numbers) {
            show += showCouple(number) + " ";
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

    public static List<Integer> getCombineNumbersAndTouchs(List<Integer> numbers, List<Integer> touchs) {
        if (numbers.isEmpty() || touchs.isEmpty()) return new ArrayList<>();
        List<Integer> results = new ArrayList<>();
        for (int number : numbers) {
            for (int touch : touchs) {
                if (number / 10 == touch || number % 10 == touch) {
                    results.add(number);
                }
            }
        }
        Collections.sort(results);
        return filterDuplicatedNumbers(numbers);
    }

    public static List<Integer> getCombineNumbers(List<Integer> firstNumbers, List<Integer> secondNumbers) {
        if (firstNumbers.isEmpty() && secondNumbers.isEmpty()) return new ArrayList<>();
        if (firstNumbers.isEmpty()) return secondNumbers;
        if (secondNumbers.isEmpty()) return firstNumbers;
        List<Integer> numbers = new ArrayList<>();
        for (int first : firstNumbers) {
            if (secondNumbers.contains(first)) {
                numbers.add(first);
            }
        }
        Collections.sort(numbers);
        return filterDuplicatedNumbers(numbers);
    }

    public static List<Integer> filterDuplicatedNumbers(List<Integer> numbers) {
        if (numbers.size() == 0) return new ArrayList<>();
        List<Integer> filteredNumber = new ArrayList<>();
        for (int number : numbers) {
            if (!filteredNumber.contains(number)) {
                filteredNumber.add(number);
            }
        }
        return filteredNumber;
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

    // limitOfNumber = 1 : số nhỏ hơn 10, limitOfNumber = 2 số nhỏ hơn 100,....
    public static List<Integer> verifyNumberArray(String array, int limitOfNumber) {
        if (array.equals("")) return new ArrayList<>();
        List<Integer> numbers = new ArrayList<>();
        int count = 0;
        int LIMIT = (int) (Math.pow(10, limitOfNumber) - 1);
        String numberArr[] = array.split(" ");
        try {
            for (int i = 0; i < numberArr.length; i++) {
                if (numberArr[i].trim().length() == 0) continue;
                int number = Integer.parseInt(numberArr[i].trim());
                if (number < 0 || number > LIMIT) {
                    count++;
                } else {
                    if (numberArr[i].trim().length() > limitOfNumber) { // trường hợp đầu có số 0
                        count++;
                    } else {
                        numbers.add(number);
                    }
                }
            }
        } catch (Exception e) {

        }
        if (count > 0) return new ArrayList<>();
        return numbers;
    }

    // limitOfNumber = 1 : số nhỏ hơn 10, limitOfNumber = 2 số nhỏ hơn 100,....
    public static List<Number> verifyNumberArr(String array, int limitOfNumber) {
        if (array.equals("")) return new ArrayList<>();
        int LIMIT = (int) (Math.pow(10, limitOfNumber) - 1);
        List<Number> numbers = new ArrayList<>();
        String numberArr[] = array.split(" ");
        int count = 0;
        try {
            for (int i = 0; i < numberArr.length; i++) {
                int number = Integer.parseInt(numberArr[i].trim());
                if (number < 0 || number > LIMIT) {
                    count++;
                } else {
                    numbers.add(new Number(number, 1));
                }
            }
        } catch (Exception e) {

        }
        if (count > 0) return new ArrayList<>();
        return numbers;
    }

    public static String showCouple(int number) {
        if (number < 0 || number > 99) return "";
        return number < 10 ? 0 + "" + number : number + "";
    }

    public static String showNumberString(int number, int lengthStr) {
        if (number < 0 || number > Math.pow(10, lengthStr) - 1 || lengthStr == 1) return "";
        String numberStr = "";
        for (int i = lengthStr - 1; i >= 1; i--) {
            int minLimit = i == 1 ? 0 : (int) Math.pow(10, i - 1);
            int maxLimit = (int) Math.pow(10, i) - 1;
            if (number >= minLimit && number <= maxLimit) {
                for (int j = 0; j < lengthStr - i; j++) {
                    numberStr += "0";
                }
            }
        }
        return numberStr + "" + number;
    }

    public static String toString(List<Integer> numbers, String delimiter) {
        if (numbers.isEmpty()) return "";
        String data = "";
        for (int i = 0; i < numbers.size(); i++) {
            data += numbers.get(i);
            if (i != numbers.size() - 1) {
                data += delimiter;
            }
        }
        return data;
    }

    public static String toString(List<Integer> numbers, String delimiter, int limitOfNumber) {
        if (numbers.isEmpty()) return "";
        String data = "";
        for (int i = 0; i < numbers.size(); i++) {
            data += showNumberString(numbers.get(i), limitOfNumber);
            if (i != numbers.size() - 1) {
                data += delimiter;
            }
        }
        return data;
    }

    public static int randomInt(int limit) {
        Random rd = new Random();
        return rd.nextInt(limit);
    }

    public static List<Integer> parseString(String data, String split) {
        if (data.trim().equals("")) return new ArrayList<>();
        String arr[] = data.split(split);
        List<Integer> numbers = new ArrayList<>();
        try {
            for (String s : arr) {
                numbers.add(Integer.parseInt(s.trim()));
            }
        } catch (Exception e) {

        }
        return numbers;
    }
}
