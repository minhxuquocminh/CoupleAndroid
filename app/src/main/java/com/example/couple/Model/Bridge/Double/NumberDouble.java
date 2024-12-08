package com.example.couple.Model.Bridge.Double;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class NumberDouble {
    int firstNumber;
    int secondNumber; // -1: ko tồn tại

    public String show() {
        String showFirst = firstNumber < 10 ? "0" + firstNumber : firstNumber + "";
        String showSecond = secondNumber == -1 ? "??" : secondNumber + "";
        return showFirst + " => " + showSecond;
    }
}
