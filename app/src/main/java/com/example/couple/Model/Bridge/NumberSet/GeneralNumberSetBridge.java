package com.example.couple.Model.Bridge.NumberSet;

import com.example.couple.Model.Bridge.Bridge;
import com.example.couple.Model.Bridge.BridgeType;
import com.example.couple.Model.Bridge.JackpotHistory;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class GeneralNumberSetBridge extends Bridge {
    Map<NumberSetType, List<NumberSetHistory>> historiesByType;

    @Override
    public String showCompactNumbers() {
        return getNumbers().size() + " số.";
    }

    @Override
    public BridgeType getType() {
        return BridgeType.SYNTHETIC;
    }

    @Override
    public List<Integer> getNumbers() {
        List<Integer> numbers = new ArrayList<>();
        for (List<NumberSetHistory> histories : historiesByType.values()) {
            for (NumberSetHistory history : histories) {
                for (Integer number : history.getNumberSet().getNumbers()) {
                    if (!numbers.contains(number)) numbers.add(number);
                }
            }
        }
        return numbers;
    }

    @Override
    public JackpotHistory getJackpotHistory() {
        return JackpotHistory.getEmpty();
    }

}
