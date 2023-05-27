package com.example.couple.Model.Display;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class BSingle {
    int number;
    int level;

    public String showTouchBalanceCouple() {
        String show = "";
        if (level > 2) {
            show = "{{" + number + "}}";
        } else if (level > 1) {
            show = "{" + number + "}";
        } else {
            show = number + "";
        }
        return show;
    }

    public String show() {
        return number + " (" + level + ")";
    }
}
