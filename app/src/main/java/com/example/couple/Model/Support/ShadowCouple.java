package com.example.couple.Model.Support;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class ShadowCouple {
    Integer number;
    Integer type;

    public String show() {
        return number + "." + type + " ";
    }

}
