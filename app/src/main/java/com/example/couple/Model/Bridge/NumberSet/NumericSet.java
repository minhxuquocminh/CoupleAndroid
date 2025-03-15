package com.example.couple.Model.Bridge.NumberSet;

import com.example.couple.Base.Handler.CoupleBase;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class NumericSet {
    String name;
    List<Integer> numbers;

    public String showNumbers() {
        return CoupleBase.showCoupleNumbers(numbers);
    }

}
