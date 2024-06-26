package com.example.couple.Base.Handler;

import java.util.List;

public class StringBase {
    public static String getDelimiterString(List<Integer> numbers, int numberLength, String delimiter) {
        StringBuilder results = new StringBuilder();
        for (int i = 0; i < numbers.size(); i++) {
            results.append(NumberBase.showNumberString(numbers.get(i), numberLength))
                    .append(i != numbers.size() - 1 ? delimiter : "");
        }
        return results.toString();
    }

    public static boolean isNullOrEmpty(String data) {
        return data == null || data.isEmpty();
    }

}
