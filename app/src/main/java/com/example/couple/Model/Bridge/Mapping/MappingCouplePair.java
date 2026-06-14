package com.example.couple.Model.Bridge.Mapping;

import com.example.couple.Model.Origin.Couple;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class MappingCouplePair {
    Couple firstCouple;
    Couple secondCouple;

    public String show() {
        return firstCouple.show() + "-" + secondCouple.show();
    }

}
