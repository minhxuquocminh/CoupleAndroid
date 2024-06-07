package com.example.couple.Custom.Handler;

import com.example.couple.Base.Handler.CoupleBase;
import com.example.couple.Model.Display.Set;
import com.example.couple.Model.Time.Cycle.Branch;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class NumberArrayHandler {

    public static List<Integer> getBranches(List<Integer> branchList) {
        List<Integer> data = new ArrayList<>();
        for (int branch : branchList) {
            data.addAll(new Branch(branch).getTailsOfYear());
        }
        return data;
    }

    public static List<Integer> getHeads(List<Integer> headList) {
        List<Integer> data = new ArrayList<>();
        for (int i = 0; i < headList.size(); i++) {
            for (int j = 0; j < 10; j++) {
                int number = Integer.parseInt(headList.get(i) + "" + j);
                data.add(number);
            }
        }
        return data;
    }

    public static List<Integer> getTails(List<Integer> tailList) {
        if (tailList.isEmpty()) return new ArrayList<>();
        List<Integer> data = new ArrayList<>();
        for (int i = 0; i < tailList.size(); i++) {
            for (int j = 0; j < 10; j++) {
                int number = Integer.parseInt(j + "" + tailList.get(i));
                data.add(number);
            }
        }
        return data;
    }

    public static List<Integer> getThreeClaws(List<Integer> coupleList, List<Integer> thirdClawList) {
        if (coupleList.isEmpty() || thirdClawList.isEmpty()) return new ArrayList<>();
        List<Integer> compactCouples = coupleList.stream().distinct().collect(Collectors.toList());
        List<Integer> compactClaw = thirdClawList.stream().distinct().collect(Collectors.toList());

        List<Integer> results = new ArrayList<>();
        for (int couple : compactCouples) {
            for (int claw : compactClaw) {
                int number = claw * 100 + couple;
                results.add(number);
            }
        }

        return results;
    }

    public static List<Integer> getSums(List<Integer> singleSumList) {
        if (singleSumList.isEmpty()) return new ArrayList<>();
        List<Integer> compactNumbers = singleSumList.stream().distinct().collect(Collectors.toList());

        List<Integer> results = new ArrayList<>();
        for (int sum : compactNumbers) {
            List<Integer> sumList = getSums(sum);
            results.addAll(sumList);
        }

        return results;
    }

    public static List<Integer> getTouchs(List<Integer> singleTouchList) {
        if (singleTouchList.isEmpty()) return new ArrayList<>();
        List<Integer> compactNumbers = singleTouchList.stream().distinct().collect(Collectors.toList());

        List<Integer> touchList = new ArrayList<>();
        for (int touch : compactNumbers) {
            List<Integer> touchs = getTouchs(touch);
            touchList.addAll(touchs);
        }

        return touchList.stream().distinct().collect(Collectors.toList());
    }

    public static List<Integer> getSetsBySingles(List<Integer> singleSetList) {
        List<Integer> smallShadowList = new ArrayList<>();
        for (int i = 0; i < singleSetList.size(); i++) {
            smallShadowList.add(CoupleBase.getSmallShadow(singleSetList.get(i)));
        }

        List<Integer> compactNumbers = smallShadowList.stream().distinct().collect(Collectors.toList());
        if (compactNumbers.size() < 2) return new ArrayList<>();
        Collections.sort(compactNumbers);

        List<Integer> results = new ArrayList<>();
        for (int i = 0; i < compactNumbers.size(); i++) {
            for (int j = i + 1; j < compactNumbers.size(); j++) {
                int first = compactNumbers.get(i);
                int second = compactNumbers.get(j);
                Set set = new Set(first, second);
                results.addAll(set.getSetsDetail());
            }
        }

        for (int single : compactNumbers) {
            Set set = new Set(single, single);
            results.addAll(set.getSetsDetail());
        }

        return results;
    }

    public static List<Integer> getSetsByCouples(List<Integer> coupleSetList) {
        if (coupleSetList.isEmpty()) return new ArrayList<>();
        List<Integer> smallShadowList = new ArrayList<>();
        for (int i = 0; i < coupleSetList.size(); i++) {
            smallShadowList.add(CoupleBase.getSmallShadow(coupleSetList.get(i)));
        }

        List<Integer> compactNumbers = smallShadowList.stream().distinct().collect(Collectors.toList());

        List<Integer> results = new ArrayList<>();
        for (int couple : compactNumbers) {
            results.addAll(Set.getFrom(couple).getSetsDetail());
        }

        return results;
    }

    public static List<Set> getSetListBySingles(List<Integer> singleSetList) {
        List<Integer> smallShadowList = new ArrayList<>();
        for (int i = 0; i < singleSetList.size(); i++) {
            smallShadowList.add(CoupleBase.getSmallShadow(singleSetList.get(i)));
        }

        List<Integer> compactNumbers = smallShadowList.stream().distinct().collect(Collectors.toList());
        if (compactNumbers.size() < 2) return new ArrayList<>();

        List<Set> setList = new ArrayList<>();
        for (int i = 0; i < compactNumbers.size(); i++) {
            for (int j = i + 1; j < compactNumbers.size(); j++) {
                int first = compactNumbers.get(i);
                int second = compactNumbers.get(j);
                setList.add(new Set(first, second));
            }
        }

        for (int single : compactNumbers) {
            setList.add(new Set(single, single));
        }

        return setList;
    }

    public static List<Integer> getHeads(int head) {
        if (head < 0 || head > 9) return new ArrayList<>();
        List<Integer> numbers = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            int number = Integer.parseInt(head + "" + i);
            numbers.add(number);
        }
        return numbers;
    }

    public static List<Integer> getTails(int tail) {
        if (tail < 0 || tail > 9) return new ArrayList<>();
        List<Integer> numbers = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            int number = Integer.parseInt(i + "" + tail);
            numbers.add(number);
        }
        return numbers;
    }

    public static List<Integer> getTouchs(int touch) {
        List<Integer> numbers = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            int number1 = Integer.parseInt(i + "" + touch);
            int number2 = Integer.parseInt(touch + "" + i);
            numbers.add(number1);
            if (number1 != number2) {
                numbers.add(number2);
            }
        }
        return numbers;
    }

    public static List<Integer> getSums(int sum) {
        if (sum > 9 || sum < 0) return new ArrayList<>();
        List<Integer> numbers = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            int unit = sum - i < 0 ? 10 + sum - i : sum - i;
            numbers.add(i * 10 + unit);
        }
        return numbers;
    }

}
