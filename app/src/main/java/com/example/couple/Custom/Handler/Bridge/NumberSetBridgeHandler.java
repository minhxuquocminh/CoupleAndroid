package com.example.couple.Custom.Handler.Bridge;

import com.example.couple.Custom.Handler.History.NumberSetHistoryHandler;
import com.example.couple.Model.Bridge.NumberSet.GeneralNumberSetBridge;
import com.example.couple.Model.Bridge.NumberSet.NumberSetHistory;
import com.example.couple.Model.Bridge.NumberSet.NumberSetType;
import com.example.couple.Model.Origin.Jackpot;

import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class NumberSetBridgeHandler {

    public static GeneralNumberSetBridge getGeneralNumberSetBridges(List<Jackpot> jackpotList) {
        Map<NumberSetType, List<NumberSetHistory>> historiesByType = NumberSetHistoryHandler.getFullNumberSetsHistory(jackpotList,
                Arrays.asList(NumberSetType.HEAD, NumberSetType.TAIL, NumberSetType.SUM, NumberSetType.SET));

        Map<NumberSetType, List<NumberSetHistory>> generalHistoriesByType = new LinkedHashMap<>();
        generalHistoriesByType.put(NumberSetType.HEAD,
                getValidHistories(historiesByType, NumberSetType.HEAD, 20));
        generalHistoriesByType.put(NumberSetType.TAIL,
                getValidHistories(historiesByType, NumberSetType.TAIL, 20));
        generalHistoriesByType.put(NumberSetType.SUM,
                getValidHistories(historiesByType, NumberSetType.SUM, 30));
        generalHistoriesByType.put(NumberSetType.SET,
                getValidHistories(historiesByType, NumberSetType.SET, 40));

        return new GeneralNumberSetBridge(generalHistoriesByType);
    }

    private static List<NumberSetHistory> getValidHistories(Map<NumberSetType, List<NumberSetHistory>> historiesByType,
                                                            NumberSetType type, int minDayNumberBefore) {
        List<NumberSetHistory> histories = historiesByType.get(type);
        if (histories == null) return Collections.emptyList();

        return histories.stream()
                .filter(history -> history.getDayNumberBefore() >= minDayNumberBefore)
                .sorted(getHistoryComparator(minDayNumberBefore))
                .collect(Collectors.toList());
    }

    private static Comparator<NumberSetHistory> getHistoryComparator(int minDayNumberBefore) {
        return Comparator.comparingInt((NumberSetHistory history) ->
                        getDayNumberBeforeLevel(history, minDayNumberBefore)).reversed()
                .thenComparing(Comparator.comparingInt(NumberSetHistory::getDayNumberBefore).reversed())
                .thenComparing(Comparator.comparingInt(NumberSetHistory::getAppearanceTimes).reversed());
    }

    private static int getDayNumberBeforeLevel(NumberSetHistory history, int minDayNumberBefore) {
        return history.getDayNumberBefore() >= minDayNumberBefore + 10 ? 1 : 0;
    }

}
