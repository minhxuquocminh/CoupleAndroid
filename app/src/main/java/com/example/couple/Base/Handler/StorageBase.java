package com.example.couple.Base.Handler;

import static android.content.Context.MODE_PRIVATE;

import android.content.Context;

import com.example.couple.Custom.Enum.Flag;
import com.example.couple.Custom.Enum.StorageType;

import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class StorageBase {

    private static final String FLAG = "FLAG";

    public static void setFlag(Context context, Flag flag, boolean value) {
        context.getSharedPreferences(FLAG, MODE_PRIVATE).edit().putBoolean(flag.name, value).apply();
    }

    public static boolean getFlag(Context context, Flag flag) {
        return context.getSharedPreferences(FLAG, MODE_PRIVATE).getBoolean(flag.name, false);
    }

    private static final String NUMBER_SET = "NUMBER_SET";

    public static void setNumberSet(Context context, StorageType type, Set<Integer> values) {
        Set<String> dataSet = values.stream().map(x -> x + "").collect(Collectors.toSet());
        context.getSharedPreferences(NUMBER_SET, MODE_PRIVATE).edit().putStringSet(type.name, dataSet).apply();
    }

    public static Set<Integer> getNumberSet(Context context, StorageType type) {
        Set<String> dataSet = context.getSharedPreferences(NUMBER_SET, MODE_PRIVATE).getStringSet(type.name, new HashSet<>());
        return dataSet.stream().map(Integer::parseInt).collect(Collectors.toSet());
    }

    private static final String NUMBER_LIST = "NUMBER_LIST";

    public static void setNumberList(Context context, StorageType type, List<Integer> values) {
        Set<String> dataSet = values.stream().map(x -> x + "").collect(Collectors.toCollection(LinkedHashSet::new));
        context.getSharedPreferences(NUMBER_LIST, MODE_PRIVATE).edit().putStringSet(type.name, dataSet).apply();
    }

    public static List<Integer> getNumberList(Context context, StorageType type) {
        Set<String> dataSet = context.getSharedPreferences(NUMBER_LIST, MODE_PRIVATE).getStringSet(type.name, new LinkedHashSet<>());
        return dataSet.stream().map(Integer::parseInt).collect(Collectors.toList());
    }

    private static final String STRING_SET = "STRING_SET";

    public static void setStringSet(Context context, StorageType type, Set<String> values) {
        context.getSharedPreferences(STRING_SET, MODE_PRIVATE).edit().putStringSet(type.name, values).apply();
    }

    public static Set<String> getStringSet(Context context, StorageType type) {
        return context.getSharedPreferences(STRING_SET, MODE_PRIVATE).getStringSet(type.name, new HashSet<>());
    }

}
