package com.example.couple.Model.Bridge.Mapping;

import com.example.couple.Base.Handler.NumberBase;
import com.example.couple.Model.Bridge.Bridge;
import com.example.couple.Model.Bridge.BridgeType;
import com.example.couple.Model.Bridge.JackpotHistory;
import com.example.couple.Model.Origin.Couple;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import lombok.Getter;

@Getter
public class MappingBridge extends Bridge {
    BridgeType bridgeType;
    List<MappingCouplePair> mappingCouplePairs;
    List<Integer> numbers;
    JackpotHistory jackpotHistory;

    private MappingBridge(BridgeType bridgeType, List<MappingCouplePair> mappingCouplePairs, List<Integer> numbers, JackpotHistory jackpotHistory) {
        this.bridgeType = bridgeType;
        this.mappingCouplePairs = mappingCouplePairs;
        this.numbers = numbers;
        this.jackpotHistory = jackpotHistory;
    }

    public MappingBridge(BridgeType bridgeType, List<MappingCouplePair> mappingCouplePairs, JackpotHistory jackpotHistory) {
        this.bridgeType = bridgeType;
        this.mappingCouplePairs = mappingCouplePairs;
        this.jackpotHistory = jackpotHistory;
        List<Integer> numbers = new ArrayList<>();
        for (MappingCouplePair mappingCouplePair : mappingCouplePairs) {
            Couple first = mappingCouplePair.getFirstCouple();
            Couple second = mappingCouplePair.getSecondCouple();
            List<Integer> currentNumbers = new ArrayList<>();
            currentNumbers.addAll(first.getMappingNumbers());
            currentNumbers.addAll(second.getMappingNumbers());
            numbers = NumberBase.getMatchNumbers(numbers,
                    currentNumbers.stream().distinct().collect(Collectors.toList()));
        }
        this.numbers = numbers;
    }

    public static MappingBridge getRightMappingBridge(BridgeType bridgeType, List<MappingCouplePair> mappingCouplePairs, JackpotHistory jackpotHistory) {
        List<Integer> numbers = new ArrayList<>();
        for (MappingCouplePair mappingCouplePair : mappingCouplePairs) {
            Couple first = mappingCouplePair.getFirstCouple();
            Couple second = mappingCouplePair.getSecondCouple();
            List<Integer> currentNumbers = new ArrayList<>();
            currentNumbers.addAll(first.getRightMappingNumbers());
            currentNumbers.addAll(second.getRightMappingNumbers());
            numbers = NumberBase.getMatchNumbers(numbers,
                    currentNumbers.stream().distinct().collect(Collectors.toList()));
        }
        return new MappingBridge(bridgeType, mappingCouplePairs, numbers, jackpotHistory);
    }

    public static MappingBridge getEmpty() {
        return new MappingBridge(null, new ArrayList<>(), JackpotHistory.getEmpty());
    }

    public boolean isEmpty() {
        return mappingCouplePairs.isEmpty() || numbers.isEmpty();
    }

    @Override
    public String showCompactNumbers() {
        return "";
    }

    @Override
    public String showCompactInfo() {
        if (!mappingCouplePairs.isEmpty()) {
            return mappingCouplePairs.stream()
                    .map(MappingCouplePair::show)
                    .collect(Collectors.joining(" "));
        }
        return bridgeType == null ? "" : bridgeType.name;
    }

    @Override
    public String showDetailInfo() {
        String show = showJackpotInfo();
        if (!mappingCouplePairs.isEmpty()) {
            show += "\n\nCầu 2 ánh xạ:";
            show += "\n" + showCompactInfo();
        } else {
            show += "\n\n" + showCompactInfo();
        }
        show += "\n\nDàn số:\n" + showNumbers();
        return show.trim();
    }

    @Override
    public BridgeType getType() {
        return bridgeType;
    }

}
