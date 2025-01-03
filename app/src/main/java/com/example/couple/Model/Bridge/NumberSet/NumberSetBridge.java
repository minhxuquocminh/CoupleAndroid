package com.example.couple.Model.Bridge.NumberSet;

import com.example.couple.Model.Bridge.Bridge;
import com.example.couple.Model.Bridge.JackpotHistory;

import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class NumberSetBridge extends Bridge {
    String bridgeName;
    List<Integer> numbers;
    JackpotHistory jackpotHistory;

    @Override
    public String showCompactNumbers() {
        return "";
    }

    public static NumberSetBridge getEmpty() {
        return new NumberSetBridge("", new ArrayList<>(), JackpotHistory.getEmpty());
    }

    public boolean isEmpty() {
        return bridgeName.isEmpty() || numbers.isEmpty();
    }

}
