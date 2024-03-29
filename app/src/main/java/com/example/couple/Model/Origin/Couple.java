package com.example.couple.Model.Origin;

import com.example.couple.Base.Handler.NumberBase;
import com.example.couple.Custom.Const.Const;
import com.example.couple.Custom.Handler.CoupleHandler;
import com.example.couple.Model.Display.BCouple;
import com.example.couple.Model.Support.ShadowSingle;
import com.example.couple.Model.Support.Single;
import com.example.couple.Model.Time.DateBase;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class Couple {
    private int first;
    private int second;
    private DateBase dateBase;

    public List<Integer> getShadowExchange() {
        List<Integer> firstList = new ArrayList<>();
        firstList.add(CoupleHandler.getNegativeShadow(first));
        firstList.add(CoupleHandler.getShadow(first));
        List<Integer> secondList = new ArrayList<>();
        secondList.add(CoupleHandler.getNegativeShadow(second));
        secondList.add(CoupleHandler.getShadow(second));
        List<Integer> results = new ArrayList<>();
        for (int fi : firstList) {
            for (int se : secondList) {
                results.add(fi * 10 + se);
                results.add(CoupleHandler.reverse(fi * 10 + se));
            }
        }
        return NumberBase.filterDuplicatedNumbers(results);
    }

    public ShadowSingle getShadowSingle() {
        List<Single> firstsTens = new ArrayList<>();
        List<Single> secondsTens = new ArrayList<>();
        List<Single> firstsUnits = new ArrayList<>();
        List<Single> secondsUnits = new ArrayList<>();

        firstsTens.addAll(Couple.getFirstList(first));
        secondsTens.addAll(Couple.getSecondList(second));
        firstsUnits.addAll(Couple.getFirstList(second));
        secondsUnits.addAll(Couple.getSecondList(first));

        return new ShadowSingle(firstsTens, secondsTens, firstsUnits, secondsUnits);
    }

    private static List<Single> getFirstList(int first) {
        List<Single> firstList = new ArrayList<>();
        firstList.add(new Single(first, 1, 1));
        firstList.add(new Single(CoupleHandler.getShadow(first), 1, 1));
        firstList.add(new Single(CoupleHandler.getNegativeShadow(first), 1, 2));
        return firstList;
    }

    private static List<Single> getSecondList(int second) {
        List<Single> secondList = new ArrayList<>();
        int shadowSecond = CoupleHandler.getShadow(second);
        int negativeShadowOfSecond = CoupleHandler.getNegativeShadow(second);
        int negativeShadowOfShadowSecond = CoupleHandler.getNegativeShadow(shadowSecond);
        secondList.add(new Single(second, 2, 1));
        secondList.add(new Single(shadowSecond, 2, 1));
        secondList.add(new Single(CoupleHandler.minusOne(second), 2, 4));
        secondList.add(new Single(CoupleHandler.minusOne(shadowSecond), 2, 4));

        secondList.add(new Single(negativeShadowOfSecond, 2, 2));
        secondList.add(new Single(CoupleHandler.getShadow(negativeShadowOfSecond), 2, 5));
        secondList.add(new Single(CoupleHandler.minusOne(negativeShadowOfSecond), 2, 2));
        secondList.add(new Single(CoupleHandler.minusOne(CoupleHandler
                .getShadow(negativeShadowOfSecond)), 2, 5));

        secondList.add(new Single(negativeShadowOfShadowSecond, 2, 3));
        secondList.add(new Single(CoupleHandler.getShadow(negativeShadowOfShadowSecond), 2, 3));
        secondList.add(new Single(CoupleHandler.minusOne(negativeShadowOfShadowSecond), 2, 6));
        secondList.add(new Single(CoupleHandler.minusOne(CoupleHandler
                .getShadow(negativeShadowOfShadowSecond)), 2, 6));
        return secondList;
    }

    public List<Integer> getMappingNumbers(int mappingType) {
        if (mappingType == 0) return CoupleHandler.getMappingNumbers(getCoupleInt());
        List<Integer> numbers = mappingType == Const.MAPPING_ALL ?
                CoupleHandler.getMappingNumbers(getCoupleInt()) : new ArrayList<>();
        int start = mappingType == Const.MAPPING_ALL ? 1 : mappingType;
        int end = mappingType == Const.MAPPING_ALL ? 2 : mappingType;
        for (int i = start; i <= end; i++) {
            if (first - i >= 0) {
                int top = (first - i) * 10 + second;
                List<Integer> topList = CoupleHandler.getMappingNumbers(top);
                numbers.addAll(topList);
            }

            if (first + i <= 9) {
                int bottom = (first + i) * 10 + second;
                List<Integer> bottomList = CoupleHandler.getMappingNumbers(bottom);
                numbers.addAll(bottomList);
            }

            if (second - i >= 0) {
                int left = first * 10 + second - i;
                List<Integer> leftList = CoupleHandler.getMappingNumbers(left);
                numbers.addAll(leftList);
            }

            if (second + i <= 9) {
                int right = first * 10 + second + i;
                List<Integer> rightList = CoupleHandler.getMappingNumbers(right);
                numbers.addAll(rightList);
            }
        }

        List<Integer> results = NumberBase.filterDuplicatedNumbers(numbers);
        Collections.sort(results);

        return results;
    }

    public List<Integer> getRightMappingNumbers() {
        List<Integer> numbers = CoupleHandler.getMappingNumbers(getCoupleInt());
        for (int i = 1; i <= 2; i++) {
            // top
            if (first - i >= 0) {
                int top = (first - i) * 10 + second;
                numbers.addAll(CoupleHandler.getMappingLeftBottomNumbers(top));
            } else {
                if (second + i <= 9) {
                    int right = first * 10 + second + i;
                    numbers.addAll(CoupleHandler.getMappingLeftBottomNumbers(right));
                }
            }
            // bottom
            if (first + i <= 9) {
                int bottom = (first + i) * 10 + second;
                numbers.addAll(CoupleHandler.getMappingRightTopNumbers(bottom));
            } else {
                if (second + i <= 9) {
                    int right = first * 10 + second + i;
                    numbers.addAll(CoupleHandler.getMappingRightTopNumbers(right));
                }
            }
        }
        List<Integer> results = NumberBase.filterDuplicatedNumbers(numbers);
        Collections.sort(results);
        return results;
    }

    public List<Integer> getRight1MappingNumbers() {
        List<Integer> numbers = new ArrayList<>();
        if (first - 1 >= 0) {
            int top = (first - 1) * 10 + second;
            numbers.addAll(CoupleHandler.getMappingLeftBottomNumbers(top));
        } else {
            if (second + 1 <= 9) {
                int right = first * 10 + second + 1;
                numbers.addAll(CoupleHandler.getMappingLeftBottomNumbers(right));
            }
        }
        // bottom
        if (first + 1 <= 9) {
            int bottom = (first + 1) * 10 + second;
            numbers.addAll(CoupleHandler.getMappingRightTopNumbers(bottom));
        } else {
            if (second + 1 <= 9) {
                int right = first * 10 + second + 1;
                numbers.addAll(CoupleHandler.getMappingRightTopNumbers(right));
            }
        }
        List<Integer> results = NumberBase.filterDuplicatedNumbers(numbers);
        Collections.sort(results);
        return results;
    }


    public int getCoupleInt() {
        return Integer.parseInt(first + "" + second);
    }

    public int plus(Couple cp) {
        int cp1 = Integer.parseInt(first + "" + second);
        int cp2 = Integer.parseInt(cp.show());
        return cp1 + cp2;
    }

    public boolean equals(Couple couple) {
        return first == couple.getFirst() && second == couple.getSecond();
    }

    public int sub(Couple cp) {
        int cp1 = Integer.parseInt(first + "" + second);
        int cp2 = Integer.parseInt(cp.show());
        return cp1 - cp2;
    }

    public String show() {
        return first + "" + second;
    }

    public String showDot() {
        return first + "." + second;
    }

    public boolean isSameDouble() {
        return first == second;
    }

    public boolean isDouble() {
        // kép = hoặc lệch
        return first == second || first == CoupleHandler.getShadow(second);
    }

    public boolean isDeviatedDouble() {
        return Math.abs(first - second) == 5;
    }

    public String getCL() {
        String firstStt = "";
        String secondStt = "";
        if (first % 2 == 0) {
            firstStt = "C";
        } else {
            firstStt = "L";
        }
        if (second % 2 == 0) {
            secondStt = "C";
        } else {
            secondStt = "L";
        }
        return firstStt + secondStt;
    }

    public BCouple toBCouple() {
        return new BCouple(first, second);
    }

    public static Couple getEmpty() {
        return new Couple(Const.EMPTY_VALUE, Const.EMPTY_VALUE, DateBase.getEmpty());
    }

    public boolean isEmpty() {
        return first == Const.EMPTY_VALUE || second == Const.EMPTY_VALUE;
    }

}
