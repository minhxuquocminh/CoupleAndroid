package com.example.couple.Custom.Handler.Display;

import com.example.couple.Base.Handler.CoupleBase;

import java.util.stream.IntStream;

public class MatrixUtil {

    public static String[][] getTenTenStringMatrix() {
        return IntStream.range(0, 10).mapToObj(i -> IntStream.range(0, 10)
                .mapToObj(j -> CoupleBase.showCouple(i * 10 + j))
                .toArray(String[]::new)).toArray(String[][]::new);
    }

    public static Integer[][] getTenTenIntMatrix(int startId) {
        return IntStream.range(0, 10).mapToObj(i -> IntStream.range(0, 10)
                .mapToObj(j -> startId + i * 10 + j)
                .toArray(Integer[]::new)).toArray(Integer[][]::new);
    }

    public static String[][] getTouchStringMatrix(int touch) {
        String[][] matrix = new String[2][10];
        for (int i = 0; i < 10; i++) {
            int first = touch * 10 + i, second = i * 10 + touch;
            matrix[0][i] = CoupleBase.showCouple(first);
            if (first == second) continue;
            matrix[1][i] = CoupleBase.showCouple(second);
        }
        return matrix;
    }

    public static Integer[][] getTouchIntMatrix(int startId, int touch) {
        Integer[][] matrix = new Integer[2][10];
        for (int i = 0; i < 10; i++) {
            int first = touch * 10 + i, second = i * 10 + touch;
            matrix[0][i] = startId + first;
            if (first == second) continue;
            matrix[1][i] = startId + second;
        }
        return matrix;
    }

}
