package com.example.couple.Model.Bridge.NumberSet;

import java.util.Arrays;
import java.util.List;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum SpecialSet {

    BIG_DOUBLE("Bộ kép to", Arrays.asList
            (
                    03, 30,
                    05, 50,
                    07, 70,
                    14, 41,
                    16, 61,
                    27, 72,
                    29, 92,
                    35, 53,
                    36, 63,
                    37, 73,
                    38, 83,
                    46, 64,
                    49, 94,
                    57, 75,
                    58, 85,
                    79, 97
            )),

    DOUBLE("Bộ kép bằng", Arrays.asList
            (
                    00,
                    11,
                    22,
                    33,
                    44,
                    55,
                    66,
                    77,
                    88,
                    99
            )),

    POSITIVE_DOUBLE("Bộ kép dương", Arrays.asList
            (
                    05, 50,
                    16, 61,
                    27, 72,
                    38, 83,
                    49, 94
            )),

    NEAR_DOUBLE_INCREASE("Sát kép tăng", Arrays.asList
            (
                    01,
                    12,
                    23,
                    34,
                    45,
                    56,
                    67,
                    78,
                    89
            )),

    NEAR_DOUBLE_DECREASE("Sát kép giảm", Arrays.asList
            (
                    10,
                    21,
                    32,
                    43,
                    54,
                    65,
                    76,
                    87,
                    98
            ));

    public final String name;
    public final List<Integer> values;

}
