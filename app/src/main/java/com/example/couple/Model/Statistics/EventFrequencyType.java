package com.example.couple.Model.Statistics;

import com.example.couple.Model.Origin.Couple;

import java.util.function.BiPredicate;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum EventFrequencyType {
    KEEP_SAME(1, "Đề ko quay", Couple::isSameValue),
    REVERSE(2, "Đề lộn", Couple::isReverseValue);

    public final int value;
    public final String name;
    public final BiPredicate<Couple, Couple> condition;
}
