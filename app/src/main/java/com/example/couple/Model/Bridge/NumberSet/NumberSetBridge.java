package com.example.couple.Model.Bridge.NumberSet;

import com.example.couple.Model.Bridge.Bridge;
import com.example.couple.Model.Bridge.BridgeType;
import com.example.couple.Model.Bridge.JackpotHistory;
import com.example.couple.Model.Handler.Input;

import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class NumberSetBridge extends Bridge {
    BridgeType bridgeType;
    List<Integer> numbers;
    JackpotHistory jackpotHistory;

    @Override
    public String showCompactNumbers() {
        return "";
    }

    @Override
    public BridgeType getType() {
        return bridgeType;
    }

    public static NumberSetBridge getEmpty() {
        return new NumberSetBridge(null, new ArrayList<>(), JackpotHistory.getEmpty());
    }

    public boolean isEmpty() {
        return bridgeType == null || numbers.isEmpty();
    }

}
