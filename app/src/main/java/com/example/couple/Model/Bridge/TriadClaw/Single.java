package com.example.couple.Model.Bridge.TriadClaw;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class Single {
    int number;
    int level;

    public String show() {
        return number + " (" + level + ")";
    }
}
