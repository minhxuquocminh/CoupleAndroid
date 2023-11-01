package com.example.couple.Model.Bridge.Couple;

import com.example.couple.Base.Handler.NumberBase;
import com.example.couple.Custom.Const.Const;
import com.example.couple.Model.Bridge.Bridge;
import com.example.couple.Model.Origin.Couple;
import com.example.couple.Model.Support.JackpotHistory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
            currentNumbers.addAll(first.getMappingNumbers(Const.MAPPING_ALL));
            currentNumbers.addAll(second.getMappingNumbers(Const.MAPPING_ALL));
            numbers = NumberBase.getMatchNumbers(numbers, NumberBase.filterDuplicatedNumbers(currentNumbers));
        }
        this.jackpotHistory = jackpotHistory;
    }

    @Override
    public String showCompactNumbers() {
        return "";
    }

    @Override
    public String getBridgeName() {
        return Const.TRIAD_MAPPING_BRIDGE_NAME;
    }

    public static TriadMappingBridge getEmpty() {
        return new TriadMappingBridge(new HashMap<>(), new ArrayList<>(), JackpotHistory.getEmpty());
    }

    public boolean isEmpty() {
        return numbers.isEmpty();
    }
}
