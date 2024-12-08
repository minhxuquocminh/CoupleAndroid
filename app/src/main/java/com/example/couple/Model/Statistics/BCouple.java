package com.example.couple.Model.Statistics;

import com.example.couple.Custom.Const.Const;
import com.example.couple.Model.Origin.Couple;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class BCouple {
    private int first;
    private int second;

    public String showDot() {
        return first + "." + second;
    }

    public static BCouple fromCouple(Couple cp) {
        return new BCouple(cp.getFirst(), cp.getSecond());
    }

    public static BCouple getEmpty() {
        return new BCouple(Const.EMPTY_VALUE, Const.EMPTY_VALUE);
    }

    public int getCoupleInt() {
        return Integer.parseInt(Math.abs(first) + "" + Math.abs(second));
    }

    public boolean isEmpty() {
        return first == Const.EMPTY_VALUE || second == Const.EMPTY_VALUE;
    }

    public int plus(BCouple cp) {
        int cp1 = Integer.parseInt(first + "" + second);
        int cp2 = Integer.parseInt(cp.first + "" + cp.second);
        return cp1 + cp2;
    }

    public int sub(BCouple cp) {
        int cp1 = Integer.parseInt(first + "" + second);
        int cp2 = Integer.parseInt(cp.first + "" + cp.second);
        return cp1 - cp2;
    }

    public BCouple balanceOne(BCouple BCouple) {
        int firstNumber = first - BCouple.first;
        int secondCache = second + BCouple.second;
        int secondNumber = secondCache < 10 ?
                secondCache : (20 - secondCache) % 10;
        return new BCouple(firstNumber, secondNumber);
    }

    public BCouple balanceTwo(BCouple BCouple) {
        int firstNumber = first - BCouple.second;
        int secondCache = second + BCouple.first;
        int secondNumber = secondCache < 10 ?
                secondCache : (20 - secondCache) % 10;
        return new BCouple(firstNumber, secondNumber);
    }

    public BCouple balanceThree(BCouple BCouple) {
        int firstNumber = second - BCouple.first;
        int secondCache = first + BCouple.second;
        int secondNumber = secondCache < 10 ?
                secondCache : (20 - secondCache) % 10;
        return new BCouple(firstNumber, secondNumber);
    }

    public BCouple balanceFour(BCouple BCouple) {
        int firstNumber = second - BCouple.second;
        int secondCache = first + BCouple.first;
        int secondNumber = secondCache < 10 ?
                secondCache : (20 - secondCache) % 10;
        return new BCouple(firstNumber, secondNumber);
    }

    public Couple toCouple() {
        return new Couple(Math.abs(first), Math.abs(second));
    }

}

