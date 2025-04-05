package com.example.couple.Custom.Enum;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum StorageType {

    // set
    SET_OF_BRIDGES("NUMBER_OF_BRIDGES"),

    // list
    LIST_OF_TOUCHES("SET_OF_TOUCHES"),
    LIST_OF_BRANCHES("NUMBER_OF_BRANCHES"),
    LIST_OF_YEARS("NUMBER_OF_YEARS"),

    // strings
    STRING_OF_NOTES("STRING_OF_NOTES"),

    // number
    NUMBER_OF_PICKER("NUMBER_OF_PICKER");

    public final String name;

}
