package com.example.couple.Custom.Handler.Display;

import com.example.couple.Base.Handler.CoupleBase;

import java.util.stream.IntStream;

public class ArrayUtil {

    public static String[] getTenStringArray() {
        return IntStream.range(0, 10).mapToObj(String::valueOf).toArray(String[]::new);
    }

    public static Integer[] getTenIntArray(int startId) {
        return IntStream.range(0, 10).mapToObj(i -> startId + i).toArray(Integer[]::new);
    }

    public static String[] getZeroStringArray() {
        return IntStream.range(0, 10).mapToObj(i -> "0").toArray(String[]::new);
    }
}
