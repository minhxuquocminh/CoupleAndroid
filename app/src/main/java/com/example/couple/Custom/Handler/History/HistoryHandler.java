package com.example.couple.Custom.Handler.History;

import com.example.couple.Model.Bridge.NumberSet.NumberSetHistory;
import com.example.couple.Model.Bridge.NumberSet.NumberSetType;
import com.example.couple.Model.Origin.Jackpot;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class HistoryHandler {

    public static List<NumberSetHistory> getCompactNumberSetsHistory(List<Jackpot> jackpotList, List<NumberSetType> numberSetTypes,
                                                                     int min8size, int min10size, int minHead) {
        if (jackpotList.isEmpty()) return new ArrayList<>();
        int size = Math.min(jackpotList.size(), 150);
        List<Jackpot> jackpots = jackpotList.subList(0, size);
        List<NumberSetHistory> histories = numberSetTypes.stream().filter(type -> type.size == 8)
                .flatMap(type -> type.getNumberSetHistory(jackpots)
                        .stream().filter(history -> history.getDayNumberBefore() > min8size))
                .collect(Collectors.toList());
        List<NumberSetHistory> tenNumbersList = numberSetTypes.stream().filter(type -> type.size == 10)
                .flatMap(type -> type.getNumberSetHistory(jackpots)
                        .stream().filter(history -> history.getDayNumberBefore() > min10size))
                .collect(Collectors.toList());
        histories.addAll(tenNumbersList);
        List<NumberSetHistory> heads = numberSetTypes.stream().filter(type -> type.size == 6)
                .flatMap(type -> type.getNumberSetHistory(jackpots)
                        .stream().filter(history -> history.getDayNumberBefore() > minHead))
                .collect(Collectors.toList());
        histories.addAll(heads);
        return histories;
    }

    public static Map<NumberSetType, List<NumberSetHistory>> getFullNumberSetsHistory(List<Jackpot> jackpotList, List<NumberSetType> numberSetTypes) {
        if (jackpotList.isEmpty()) return new HashMap<>();
        return numberSetTypes.stream().collect(Collectors.toMap(type -> type, type -> type.getNumberSetHistory(jackpotList)));
    }

    public static NumberSetHistory getNumberSetHistory(List<Jackpot> jackpotList,
                                                       String numberSetName, List<Integer> numbers) {
        if (jackpotList.isEmpty()) return NumberSetHistory.getEmpty();
        List<Integer> beatList = new ArrayList<>();
        int count = 0;
        for (Jackpot jackpot : jackpotList) {
            count++;
            if (numbers.contains(jackpot.getCoupleInt())) {
                beatList.add(count);
                count = 0;
            }
        }
        Collections.reverse(beatList);
        return new NumberSetHistory(numberSetName, numbers, beatList);
    }

    public static NumberSetHistory getNumberSetHistoryWithHeadCouple(List<Jackpot> jackpotList,
                                                                     String numberSetName, List<Integer> numbers) {
        if (jackpotList.isEmpty()) return NumberSetHistory.getEmpty();
        List<Integer> beatList = new ArrayList<>();
        int count = 0;
        for (Jackpot jackpot : jackpotList) {
            count++;
            if (numbers.contains(jackpot.getCoupleIntAtBegin())) {
                beatList.add(count);
                count = 0;
            }
        }
        Collections.reverse(beatList);
        return new NumberSetHistory(numberSetName, numbers, beatList);
    }

}
