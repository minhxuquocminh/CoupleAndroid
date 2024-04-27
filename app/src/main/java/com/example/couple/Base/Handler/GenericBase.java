package com.example.couple.Base.Handler;

import java.util.List;

public class GenericBase {
    public static <T> String getDelimiterString(List<T> datas, String delimiter) {
        StringBuilder results = new StringBuilder();
        for (int i = 0; i < datas.size(); i++) {
            results.append(datas.get(i).toString());
            if (i != datas.size() - 1) {
                results.append(delimiter);
            }
        }
        return results.toString();
    }
}
