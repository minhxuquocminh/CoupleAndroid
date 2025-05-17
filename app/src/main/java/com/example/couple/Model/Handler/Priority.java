package com.example.couple.Model.Handler;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum Priority {

    NONE(0),
    NORMAL(1),
    IMPORTANT(2);

    private final int level;

}
