package com.example.couple.Custom.Enum;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum StorageType {

    // set
    SET_OF_BRANCHES("NUMBER_OF_BRANCHES"),
    SET_OF_BRIDGES("NUMBER_OF_BRIDGES"),

    // list
    LIST_OF_YEARS("NUMBER_OF_YEARS"),

    // string
    STRING_OF_NOTES("STRING_OF_NOTES");

    public final String name;

}
