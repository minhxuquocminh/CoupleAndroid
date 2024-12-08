package com.example.couple.Model.Bridge.Connected;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class Triangle {
    int first;
    int second;
    int third;

    public String show(String split) {
        return first + split + second + split + third;
    }

}
