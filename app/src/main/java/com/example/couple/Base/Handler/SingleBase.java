package com.example.couple.Base.Handler;

import java.util.List;

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

    public static int getShadow(int single) {
        if (single > 9 || single < 0) return -1;
        if (single - 5 >= 0) return single - 5;
        return single + 5;
    }

    public static String showTouchs(List<Integer> touchs) {
        String show = "";
        for (int touch : touchs) {
            show += touch + " ";
        }
        return show.trim();
    }

    public static String showTouchs(List<Integer> touchs, String delimiter) {
        String show = "";
        for (int i = 0; i < touchs.size(); i++) {
            show += touchs.get(i) + (i == touchs.size() - 1 ? "" : delimiter);
        }
        return show.trim();
    }

}
