package com.example.couple.Model.Bridge.Mapping;

import com.example.couple.Model.Bridge.Bridge;
import com.example.couple.Model.Bridge.BridgeType;
import com.example.couple.Model.Bridge.JackpotHistory;

import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class MappingBridge extends Bridge {
    BridgeType bridgeType;
    List<Integer> numbers;
    JackpotHistory jackpotHistory;

    public static MappingBridge getEmpty() {
        return new MappingBridge(null, new ArrayList<>(), JackpotHistory.getEmpty());
    }

    public boolean isEmpty() {
        return numbers.isEmpty();
    }

    @Override
    public String showCompactNumbers() {
        return "";
    }

    @Override
    public BridgeType getType() {
        return bridgeType;
    }

}
