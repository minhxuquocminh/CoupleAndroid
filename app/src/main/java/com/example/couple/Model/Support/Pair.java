package com.example.couple.Model.Support;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class Pair {
    int first;
    int second;

    public String show(String split) {
        return first + split + second;
    }
}
