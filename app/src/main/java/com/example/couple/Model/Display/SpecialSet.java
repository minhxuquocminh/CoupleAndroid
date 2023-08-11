package com.example.couple.Model.Display;

import com.example.couple.Custom.Handler.CoupleHandler;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class SpecialSet {
    String name;
    List<Integer> numbers;

    public String showNumbers() {
        return CoupleHandler.showCoupleNumbers(numbers);
    }

}
