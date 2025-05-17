package com.example.couple.Custom.Enum;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum StorageType {

    // set
    SET_OF_BRIDGES("SET_OF_BRIDGES"),

    // list
    LIST_OF_TOUCHES("LIST_OF_TOUCHES"),
    LIST_OF_BRANCHES("LIST_OF_BRANCHES"),
    LIST_OF_YEARS("LIST_OF_YEARS"),
    LIST_OF_PICKER_A("LIST_OF_PICKER_A"),
    LIST_OF_PICKER_B("LIST_OF_PICKER_B"),
    LIST_OF_IMP_PICKER_A("LIST_OF_IMP_PICKER_A"),
    LIST_OF_IMP_PICKER_B("LIST_OF_IMP_PICKER_B"),
    LIST_OF_TRIAD("LIST_OF_TRIAD"),
    LIST_OF_IMP_TRIAD("LIST_OF_IMP_TRIAD"),

    // strings
    STRING_OF_NOTE("STRING_OF_NOTE"),

    // number
    NUMBER_OF_PICKER("NUMBER_OF_PICKER");

    public final String name;

}
