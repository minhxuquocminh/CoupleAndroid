package com.example.couple.Model.Display;

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
        int secondNumber = second + BCouple.second;
        return new BCouple(firstNumber, secondNumber);
    }

    public BCouple balanceTwo(BCouple BCouple) {
        int firstNumber = first - BCouple.second;
        int secondNumber = second + BCouple.first;
        return new BCouple(firstNumber, secondNumber);
    }

    public BCouple balanceThree(BCouple BCouple) {
        int firstNumber = second - BCouple.first;
        int secondNumber = first + BCouple.second;
        return new BCouple(firstNumber, secondNumber);
    }

    public BCouple balanceFour(BCouple BCouple) {
        int firstNumber = second - BCouple.second;
        int secondNumber = first + BCouple.first;
        return new BCouple(firstNumber, secondNumber);
    }
}
