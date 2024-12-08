package com.example.couple.Model.Bridge.Mapping;

import com.example.couple.Base.Handler.NumberBase;
import com.example.couple.Model.Bridge.Bridge;
import com.example.couple.Model.Bridge.BridgeType;
import com.example.couple.Model.Origin.Couple;
import com.example.couple.Model.Bridge.JackpotHistory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class TriadMappingBridge extends Bridge {
    Map<Couple, Couple> sequentCoupleMap;
    List<Integer> numbers;
    JackpotHistory jackpotHistory;

    public TriadMappingBridge(Map<Couple, Couple> sequentCoupleMap, JackpotHistory jackpotHistory) {
        this.sequentCoupleMap = sequentCoupleMap;
        this.numbers = new ArrayList<>();
        for (Map.Entry<Couple, Couple> entry : sequentCoupleMap.entrySet()) {
            Couple first = entry.getKey();
            Couple second = entry.getValue();
            List<Integer> currentNumbers = new ArrayList<>();
            currentNumbers.addAll(first.getMappingNumbers());
            currentNumbers.addAll(second.getMappingNumbers());
            numbers = NumberBase.getMatchNumbers(numbers,
                    currentNumbers.stream().distinct().collect(Collectors.toList()));
        }
        this.jackpotHistory = jackpotHistory;
    }

    @Override
    public String showCompactNumbers() {
        return "";
    }

    @Override
    public String getBridgeName() {
        return BridgeType.TRIAD_MAPPING.name;
    }

    public static TriadMappingBridge getEmpty() {
        return new TriadMappingBridge(new HashMap<>(), new ArrayList<>(), JackpotHistory.getEmpty());
    }

    public boolean isEmpty() {
        return numbers.isEmpty();
    }
}
