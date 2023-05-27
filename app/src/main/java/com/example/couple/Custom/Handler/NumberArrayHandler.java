package com.example.couple.Custom.Handler;

import com.example.couple.Base.Handler.NumberBase;
import com.example.couple.Model.Display.Set;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class NumberArrayHandler {

    // các hàm dưới đây áp dụng cho các số >= 0

    public static List<Integer> getHeads(List<Integer> numbers) {
        List<Integer> data = new ArrayList<>();
        for (int i = 0; i < numbers.size(); i++) {
            for (int j = 0; j < 10; j++) {
                int number = Integer.parseInt(numbers.get(i) + "" + j);
                data.add(number);
            }
        }
        return data;
    }

    public static List<Integer> getTails(List<Integer> numbers) {
        List<Integer> data = new ArrayList<>();
        for (int i = 0; i < numbers.size(); i++) {
            for (int j = 0; j < 10; j++) {
                int number = Integer.parseInt(j + "" + numbers.get(i));
                data.add(number);
            }
        }
        return data;
    }

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

    public static List<Integer> getThreeClaws(List<Integer> coupleList, List<Integer> thirdClawList) {
        if (coupleList.size() == 0 || thirdClawList.size() == 0) return new ArrayList<>();

        List<Integer> compactNumbers = new ArrayList<>();
        for (int i : coupleList) {
            if (!compactNumbers.contains(i)) {
                compactNumbers.add(i);
            }
        }

        List<Integer> compactClaw = new ArrayList<>();
        for (int i : thirdClawList) {
            if (!compactClaw.contains(i)) {
                compactClaw.add(i);
            }
        }

        List<Integer> results = new ArrayList<>();
        for (int i = 0; i < compactNumbers.size(); i++) {
            for (int j = 0; j < compactClaw.size(); j++) {
                int number = compactClaw.get(j) * 100 + compactNumbers.get(i);
                results.add(number);
            }
        }

        return results;
    }

    public static List<Integer> getSums(List<Integer> singleList) {
        if (singleList.size() == 0) return new ArrayList<>();

        List<Integer> compactNumbers = new ArrayList<>();
        for (int i : singleList) {
            if (!compactNumbers.contains(i)) {
                compactNumbers.add(i);
            }
        }

        List<Integer> results = new ArrayList<>();
        for (int i = 0; i < compactNumbers.size(); i++) {
            List<Integer> sumList = getSums(compactNumbers.get(i));
            results.addAll(sumList);
        }

        return results;
    }

    public static List<Integer> getSums(int single) {
        List<Integer> numbers = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            int first = i;
            int second = single - i < 0 ? 10 + single - i : single - i;
            int number1 = Integer.parseInt(first + "" + second);
            int number2 = Integer.parseInt(second + "" + first);

            if (!numbers.contains(number1)) {
                numbers.add(number1);
            }
            if (!numbers.contains(number2) && number1 != number2) {
                numbers.add(number2);
            }
        }
        return numbers;
    }

    public static List<Integer> getTouchs(List<Integer> singleList) {
        if (singleList.size() == 0) return new ArrayList<>();

        List<Integer> compactNumbers = new ArrayList<>();
        for (int i : singleList) {
            if (!compactNumbers.contains(i)) {
                compactNumbers.add(i);
            }
        }

        List<Integer> touchList = new ArrayList<>();
        for (int i = 0; i < compactNumbers.size(); i++) {
            List<Integer> touchDetails = getTouchs(compactNumbers.get(i));
            touchList.addAll(touchDetails);
        }

        List<Integer> results = new ArrayList<>();
        for (int touch : touchList) {
            if (!results.contains(touch)) {
                results.add(touch);
            }
        }

        return results;
    }

    public static List<Integer> getTouchs(int single) {
        List<Integer> numbers = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            int number1 = Integer.parseInt(i + "" + single);
            int number2 = Integer.parseInt(single + "" + i);
            numbers.add(number1);
            if (number1 != number2) {
                numbers.add(number2);
            }
        }
        return numbers;
    }

    public static List<Set> getSets(List<Integer> singleList) {
        List<Integer> convertToSmallShadow = new ArrayList<>();
        for (int i = 0; i < singleList.size(); i++) {
            convertToSmallShadow.add(NumberBase.getSmallShadow(singleList.get(i)));
        }

        List<Integer> doubleArray = new ArrayList<>();
        List<Integer> compactNumbers = new ArrayList<>();
        for (int i : convertToSmallShadow) {
            if (!compactNumbers.contains(i)) {
                compactNumbers.add(i);
            } else {
                doubleArray.add(i);
            }
        }

        List<Integer> doubleNumbers = new ArrayList<>();
        for (int i : doubleArray) {
            if (!doubleNumbers.contains(i)) {
                doubleNumbers.add(i);
            }
        }

        if (compactNumbers.size() < 2 && doubleNumbers.size() == 0) return new ArrayList<>();

        List<Set> setList = new ArrayList<>();
        for (int i = 0; i < compactNumbers.size(); i++) {
            for (int j = i + 1; j < compactNumbers.size(); j++) {
                int first = compactNumbers.get(i);
                int second = compactNumbers.get(j);
                setList.add(new Set(first, second));
            }
        }

        for (int i = 0; i < doubleNumbers.size(); i++) {
            int doubleNumber = doubleNumbers.get(i);
            setList.add(new Set(doubleNumber, doubleNumber));
        }

        return setList;
    }

    public static List<Integer> getSetsDetail(List<Integer> sets) {
        if (sets.size() == 0) return new ArrayList<>();
        List<Integer> convertToSmallShadow = new ArrayList<>();
        for (int i = 0; i < sets.size(); i++) {
            convertToSmallShadow.add(NumberBase.getSmallShadow(sets.get(i)));
        }

        List<Integer> compactNumbers = new ArrayList<>();

        for (int i : convertToSmallShadow) {
            if (!compactNumbers.contains(i)) {
                compactNumbers.add(i);
            }
        }

        List<Integer> results = new ArrayList<>();
        for (int i = 0; i < compactNumbers.size(); i++) {
            Set set = new Set(compactNumbers.get(i));
            results.addAll(set.getSetsDetail());
        }

        return results;
    }

    public static List<Integer> getSetsDetailFromSetList(List<Set> setList) {
        if (setList.size() == 0) return new ArrayList<>();
        List<Integer> convertToSmallShadow = new ArrayList<>();
        for (int i = 0; i < setList.size(); i++) {
            convertToSmallShadow.add(NumberBase.getSmallShadow(setList.get(i).getSetInt()));
        }

        List<Integer> compactNumbers = new ArrayList<>();

        for (int i : convertToSmallShadow) {
            if (!compactNumbers.contains(i)) {
                compactNumbers.add(i);
            }
        }

        List<Integer> results = new ArrayList<>();
        for (int i = 0; i < compactNumbers.size(); i++) {
            Set set = new Set(compactNumbers.get(i));
            results.addAll(set.getSetsDetail());
        }

        return results;
    }

    public static List<Integer> getSetsDetailFromSingles(List<Integer> singleList) {
        List<Integer> convertToSmallShadow = new ArrayList<>();
        for (int i = 0; i < singleList.size(); i++) {
            convertToSmallShadow.add(NumberBase.getSmallShadow(singleList.get(i)));
        }

        List<Integer> doubleArray = new ArrayList<>();
        List<Integer> compactNumbers = new ArrayList<>();
        for (int i : convertToSmallShadow) {
            if (!compactNumbers.contains(i)) {
                compactNumbers.add(i);
            } else {
                doubleArray.add(i);
            }
        }

        List<Integer> doubleNumbers = new ArrayList<>();
        for (int i : doubleArray) {
            if (!doubleNumbers.contains(i)) {
                doubleNumbers.add(i);
            }
        }

        if (compactNumbers.size() < 2 && doubleNumbers.size() == 0) return new ArrayList<>();

        Collections.sort(compactNumbers);
        Collections.sort(doubleNumbers);

        List<Set> setList = new ArrayList<>();
        for (int i = 0; i < compactNumbers.size(); i++) {
            for (int j = i + 1; j < compactNumbers.size(); j++) {
                int first = compactNumbers.get(i);
                int second = compactNumbers.get(j);
                setList.add(new Set(first, second));
            }
        }

        List<Integer> results = new ArrayList<>();
        for (int i = 0; i < setList.size(); i++) {
            results.addAll(setList.get(i).getSetsDetail());
        }

        for (int i = 0; i < doubleNumbers.size(); i++) {
            int doubleNumber = doubleNumbers.get(i);
            Set set = new Set(doubleNumber, doubleNumber);
            results.addAll(set.getSetsDetail());
        }

        return results;
    }

}
