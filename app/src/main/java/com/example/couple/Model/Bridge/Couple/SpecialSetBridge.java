package com.example.couple.Model.Bridge.Couple;

import com.example.couple.Model.Bridge.Bridge;
import com.example.couple.Model.Support.JackpotHistory;

import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class SpecialSetBridge extends Bridge {
    String bridgeName;
    List<Integer> numbers;
    JackpotHistory jackpotHistory;

    @Override
    public String showCompactNumbers() {
        return "";
    }

    public static SpecialSetBridge getEmpty() {
        return new SpecialSetBridge("", new ArrayList<>(), JackpotHistory.getEmpty());
    }

    public boolean isEmpty() {
        return bridgeName.isEmpty() || numbers.isEmpty();
    }

}
