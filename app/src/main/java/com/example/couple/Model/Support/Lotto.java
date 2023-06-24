package com.example.couple.Model.Support;

import com.example.couple.Model.Origin.Couple;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
public class Lotto {
    String type;
    int value;
    int coupleNumber;
    List<Integer> couples;

    public Lotto(String type, int value, List<Integer> couples) {
        this.type = type;
        this.value = value;
        this.coupleNumber = couples.size();
        this.couples = couples;
    }
}
