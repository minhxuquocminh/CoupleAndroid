package com.example.couple.Model.Bridge.Couple;

import com.example.couple.Model.Bridge.Bridge;
import com.example.couple.Model.Support.JackpotHistory;

import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class SpecialSet extends Bridge {
    String bridgeName;
    List<Integer> numbers;
    JackpotHistory jackpotHistory;

    @Override
    public List<Integer> getTouchs() {
        return new ArrayList<>();
    }

    public static SpecialSet getEmpty() {
        return new SpecialSet("", new ArrayList<>(), JackpotHistory.getEmpty());
    }

    public boolean isEmpty() {
        return bridgeName.equals("") || numbers.isEmpty();
    }

}
