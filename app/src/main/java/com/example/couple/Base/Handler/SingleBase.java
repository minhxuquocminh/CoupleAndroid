package com.example.couple.Base.Handler;

import java.util.List;
import java.util.stream.Collectors;

public class SingleBase {

    public static int minusOne(int single) {
        if (single > 9 || single < 0) return -1;
        return single == 0 ? 9 : single - 1;
    }

    public static int getNegativeShadow(int single) {
        if (single > 9 || single < 0) return -1;
        int[] arr = {7, 4, 9, 6, 1, 8, 3, 0, 5, 2};
        return arr[single];
    }

    public static int getSmallShadow(int single) {
        if (single > 9 || single < 0) return -1;
        if (single < 5) return single;
        return single - 5;
    }

    public static int getShadow(int single) {
        if (single > 9 || single < 0) return -1;
        if (single - 5 >= 0) return single - 5;
        return single + 5;
    }

    public static String showTouches(List<Integer> touches) {
        return touches.stream().map(x -> x + "").collect(Collectors.joining(" "));
    }

    public static String showTouches(List<Integer> touches, String delimiter) {
        return touches.stream().map(x -> x + "").collect(Collectors.joining(delimiter));
    }

}
