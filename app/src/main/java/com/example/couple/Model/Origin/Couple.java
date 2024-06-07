package com.example.couple.Model.Origin;

import com.example.couple.Base.Handler.SingleBase;
import com.example.couple.Custom.Const.Const;
import com.example.couple.Custom.Handler.CoupleHandler;
import com.example.couple.Model.Display.BCouple;
import com.example.couple.Model.Display.Set;
import com.example.couple.Model.Time.Cycle.Cycle;
import com.example.couple.Model.Time.DateBase;

import java.util.List;
import java.util.stream.Collectors;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class Couple extends Jackpot {
    int first;
    int second;

    public Couple(String jackpot, DateBase dateBase, Cycle dayCycle) {
        super(jackpot, dateBase, dayCycle);
        this.first = Integer.parseInt(jackpot.charAt(3) + "");
        this.second = Integer.parseInt(jackpot.charAt(4) + "");
    }

    public List<Integer> getMappingNumbers() {
        List<Integer> numbers = CoupleHandler.getMappingNumbers(getInt());
        for (int i = 1; i <= 2; i++) {
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
        return numbers.stream().distinct().sorted().collect(Collectors.toList());
    }

    public List<Integer> getRightMappingNumbers() {
        List<Integer> numbers = CoupleHandler.getMappingNumbers(getInt());
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
        return numbers.stream().distinct().sorted().collect(Collectors.toList());
    }

    public List<Integer> getSetDetails() {
        return Set.getFrom(getCoupleInt()).getSetsDetail();
    }

    public int getInt() {
        return first * 10 + second;
    }

    public int plus(Couple cp) {
        return this.getInt() + cp.getInt();
    }

    public boolean equals(Couple couple) {
        return first == couple.getFirst() && second == couple.getSecond();
    }

    public int sub(Couple cp) {
        return this.getInt() - cp.getInt();
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

    public boolean isDoubleAndShadow() {
        return first == second || first == SingleBase.getShadow(second);
    }

    public boolean isDouble() {
        return first == second;
    }

    public boolean isDeviatedDouble() {
        return Math.abs(first - second) == 5;
    }

    public String getCL() {
        String firstStt = first % 2 == 0 ? "C" : "L";
        String secondStt = second % 2 == 0 ? "C" : "L";
        return firstStt + secondStt;
    }

    public BCouple toBCouple() {
        return new BCouple(first, second);
    }

    public static Couple getEmpty() {
        return new Couple(Const.EMPTY_VALUE, Const.EMPTY_VALUE);
    }

    public boolean isEmpty() {
        return first == Const.EMPTY_VALUE || second == Const.EMPTY_VALUE;
    }

}
