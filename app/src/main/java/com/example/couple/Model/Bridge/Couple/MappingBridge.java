package com.example.couple.Model.Bridge.Couple;

import com.example.couple.Model.Bridge.Bridge;
import com.example.couple.Model.Support.JackpotHistory;

import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class MappingBridge extends Bridge {
    String bridgeName;
    List<Integer> numbers;
    JackpotHistory jackpotHistory;

    public static MappingBridge getEmpty() {
        return new MappingBridge("", new ArrayList<>(), JackpotHistory.getEmpty());
    }

    public boolean isEmpty() {
        return numbers.isEmpty();
    }

    @Override
    public List<Integer> getTouchs() {
        return new ArrayList<>();
    }

}
