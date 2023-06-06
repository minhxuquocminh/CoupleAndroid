package com.example.couple.Model.Support;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class Period {
    int weight;
    int start;
    int end;
    List<Integer> numbers;

    public boolean isNull() {
        return start == -1 || end == -1;
    }

}
