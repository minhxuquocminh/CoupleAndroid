package com.example.couple.Base.View;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class Spacing {
    int left;
    int top;
    int right;
    int bottom;

    public static Spacing by(int left, int top, int right, int bottom) {
        return new Spacing(left, top, right, bottom);
    }
}
