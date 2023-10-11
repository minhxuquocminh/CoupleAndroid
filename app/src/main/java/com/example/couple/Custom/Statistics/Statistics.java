package com.example.couple.Custom.Statistics;


import com.example.couple.Model.Origin.Couple;

import java.util.ArrayList;
import java.util.List;

public class Statistics {

    public static int[] ranges = {0, 33, 66, 100};

    public static List<Integer> getRangeOfResults(List<Couple> couples, int startPosition, int distance) {
        List<Integer> rangeOfResults = new ArrayList<>();
        for (int i = startPosition + 1; i < startPosition + distance + 1; i++) {
            if (i < couples.size()) {
                for (int j = 0; j < ranges.length - 1; j++) {
                    if (isInRange(couples.get(i), ranges[j], ranges[j + 1]))
                        rangeOfResults.add(j + 1);
                }
            }
        }
        return rangeOfResults;
    }

    public static boolean isInRange(Couple cp, int start, int end) {
        int number = Integer.parseInt(cp.getFirst() + "" + cp.getSecond());
        return number >= start && number < end;
    }

}
