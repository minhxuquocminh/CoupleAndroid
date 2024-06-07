package com.example.couple.Model.Display;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class Picker {
    int number;
    int level;

    public String showCouple() {
        return (number < 10) ? "0" + number : number + "";
    }
}
