package com.example.couple.Model.Origin;

import com.example.couple.Base.Handler.DateBase;
import com.example.couple.Base.Handler.NumberBase;
import com.example.couple.Model.Display.BCouple;
import com.example.couple.Model.Support.ShadowSingle;
import com.example.couple.Model.Support.Single;

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

    public boolean isDouble() {
        // kép = hoặc lệch
        return first == second || first == NumberBase.getShadow(second);
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
        firstList.add(new Single(NumberBase.getShadow(first), 1, 1));
        firstList.add(new Single(NumberBase.getNegativeShadow(first), 1, 2));
        return firstList;
    }

    private static List<Single> getSecondList(int second) {
        List<Single> secondList = new ArrayList<>();
        int shadowSecond = NumberBase.getShadow(second);
        int negativeShadowOfSecond = NumberBase.getNegativeShadow(second);
        int negativeShadowOfShadowSecond = NumberBase.getNegativeShadow(shadowSecond);
        secondList.add(new Single(second, 2, 1));
        secondList.add(new Single(shadowSecond, 2, 1));
        secondList.add(new Single(NumberBase.minusOne(second), 2, 4));
        secondList.add(new Single(NumberBase.minusOne(shadowSecond), 2, 4));

        secondList.add(new Single(negativeShadowOfSecond, 2, 2));
        secondList.add(new Single(NumberBase.getShadow(negativeShadowOfSecond), 2, 5));
        secondList.add(new Single(NumberBase.minusOne(negativeShadowOfSecond), 2, 2));
        secondList.add(new Single(NumberBase.minusOne(NumberBase
                .getShadow(negativeShadowOfSecond)), 2, 5));

        secondList.add(new Single(negativeShadowOfShadowSecond, 2, 3));
        secondList.add(new Single(NumberBase.getShadow(negativeShadowOfShadowSecond), 2, 3));
        secondList.add(new Single(NumberBase.minusOne(negativeShadowOfShadowSecond), 2, 6));
        secondList.add(new Single(NumberBase.minusOne(NumberBase
                .getShadow(negativeShadowOfShadowSecond)), 2, 6));
        return secondList;
    }

    public List<Integer> getMappingNumbers() {
        List<Integer> numbers = NumberBase.getMappingNumbers(getCoupleInt());
        for (int i = 1; i <= 2; i++) {
            if (first - i >= 0) {
                int top = (first - i) * 10 + second;
                List<Integer> topList = NumberBase.getMappingNumbers(top);
                numbers.addAll(topList);
            }

            if (first + i <= 9) {
                int bottom = (first + i) * 10 + second;
                List<Integer> bottomList = NumberBase.getMappingNumbers(bottom);
                numbers.addAll(bottomList);
            }

            if (second - i >= 0) {
                int left = first * 10 + second - i;
                List<Integer> leftList = NumberBase.getMappingNumbers(left);
                numbers.addAll(leftList);
            }

            if (second + i <= 9) {
                int right = first * 10 + second + i;
                List<Integer> rightList = NumberBase.getMappingNumbers(right);
                numbers.addAll(rightList);
            }
        }

        List<Integer> results = new ArrayList<>();
        for (int num : numbers) {
            if (!results.contains(num)) {
                results.add(num);
            }
        }

        Collections.sort(results);
        return results;
    }

    public int getCoupleInt() {
        return Integer.parseInt(first + "" + second);
    }

    public int plus(Couple cp) {
        int cp1 = Integer.parseInt(first + "" + second);
        int cp2 = Integer.parseInt(cp.toString());
        return cp1 + cp2;
    }

    public int sub(Couple cp) {
        int cp1 = Integer.parseInt(first + "" + second);
        int cp2 = Integer.parseInt(cp.toString());
        return cp1 - cp2;
    }

    public String toString() {
        return first + "" + second;
    }

    public String showDot() {
        return first + "." + second;
    }

    public boolean isSameDouble() {
        return first == second;
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

    public BCouple getCouple() {
        return new BCouple(first, second);
    }
}
