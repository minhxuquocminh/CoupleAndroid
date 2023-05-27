package com.example.couple.Model.Display;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class Number {
    int number;
    int level;

    public String show() {
        return (number < 10) ? "0" + number : number + "";
    }
}
