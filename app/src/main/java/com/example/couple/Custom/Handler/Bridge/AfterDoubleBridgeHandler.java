package com.example.couple.Custom.Handler.Bridge;

import com.example.couple.Model.Bridge.AfterDouble.AfterDoubleCoupleBridge;
import com.example.couple.Model.Bridge.AfterDouble.AfterDoubleCoupleMapping;
import com.example.couple.Model.Bridge.AfterDouble.AfterDoubleCoupleSupport;
import com.example.couple.Model.Bridge.AfterDouble.AfterDoubleSetMapping;
import com.example.couple.Model.Bridge.AfterDouble.CoupleBeat;
import com.example.couple.Model.Bridge.JackpotHistory;
import com.example.couple.Model.Origin.Couple;
import com.example.couple.Model.Origin.Jackpot;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class AfterDoubleBridgeHandler {

    public static AfterDoubleCoupleBridge getAfterDoubleCoupleBridge(List<Jackpot> jackpotList, int dayNumberBefore) {
        Jackpot jackpot = dayNumberBefore - 1 >= 0 ? jackpotList.get(dayNumberBefore - 1) : Jackpot.getEmpty();
        JackpotHistory jackpotHistory = new JackpotHistory(dayNumberBefore, jackpot);
        int newIndex = Math.min(Math.max(dayNumberBefore, 0), jackpotList.size());
        List<Jackpot> checkList = dayNumberBefore <= 0 ? jackpotList : jackpotList.subList(newIndex, jackpotList.size());
        List<AfterDoubleCoupleMapping> mappings = getAfterDoubleCoupleMappings(checkList);
        return new AfterDoubleCoupleBridge(getAfterDoubleCoupleSupports(mappings), jackpotHistory);
    }

    public static List<AfterDoubleCoupleSupport> getAfterDoubleCoupleSupports(List<AfterDoubleCoupleMapping> mappings) {
        Map<Couple, AfterDoubleCoupleSupport> resultsByCouple = new LinkedHashMap<>();

        for (AfterDoubleCoupleMapping mapping : mappings) {

            if (mapping.getDayNumberBefore() > 5 && mapping.getDayNumberBefore() < 30) {
                continue;
            }

            if (mapping.getDayNumberBefore() > 5 && mapping.getDoubleCouple().isNegativeDouble()) {
                continue;
            }

            for (Map.Entry<Couple, List<CoupleBeat>> entry : mapping.getBeatsByCouple().entrySet()) {
                Couple predictedCouple = entry.getKey();
                List<CoupleBeat> beats = entry.getValue();

                if (predictedCouple.isDouble() || predictedCouple.isShadowDouble()) {
                    continue;
                }

                if (beats.stream().anyMatch(x -> x.getCouple().isSameOrReverse(predictedCouple))) {
                    continue;
                }

                AfterDoubleCoupleSupport prediction = resultsByCouple.computeIfAbsent(
                        predictedCouple,
                        AfterDoubleCoupleSupport::new
                );
                prediction.addSource(mapping.getDoubleCouple(), mapping.getDayNumberBefore(), beats.size());
            }
        }

        List<AfterDoubleCoupleSupport> results = new ArrayList<>(resultsByCouple.values());
        results.sort(Comparator.comparingInt(AfterDoubleCoupleSupport::getPriority)
                .thenComparingInt(AfterDoubleCoupleSupport::getDayNumberBefore));

        return results;
    }

    public static List<AfterDoubleCoupleMapping> getAfterDoubleCoupleMappings(List<Jackpot> jackpotList) {
        if (jackpotList.size() < 2) return new ArrayList<>();
        int CHECK_RANGE = 42;
        int minSize = Math.min(jackpotList.size(), CHECK_RANGE);
        List<AfterDoubleCoupleMapping> bridges = new ArrayList<>();

        for (int i = 0; i < minSize - 2; i++) {
            if (jackpotList.get(i).getCouple().isDoubleOrShadow()) {
                List<Couple> lastDoubleRange = new ArrayList<>();
                lastDoubleRange.add(jackpotList.get(i + 2).getCouple());
                lastDoubleRange.add(jackpotList.get(i + 1).getCouple());
                lastDoubleRange.add(jackpotList.get(i).getCouple());
                if (i - 1 >= 0) {
                    lastDoubleRange.add(jackpotList.get(i - 1).getCouple());
                }
                bridges.add(new AfterDoubleCoupleMapping(lastDoubleRange, i + 1));
            }
        }


        for (int i = 0; i < minSize - 2; i++) {
            for (AfterDoubleCoupleMapping bridge : bridges) {
                if (i >= bridge.getDayNumberBefore() - 1) continue;
                for (Couple couple : bridge.getBeatsByCouple().keySet()) {
                    Couple checkCouple = jackpotList.get(i).getCouple();
                    if (checkCouple.isSameSetOf(couple)) {
                        bridge.getBeatsByCouple()
                                .computeIfAbsent(couple, k -> new ArrayList<>())
                                .add(new CoupleBeat(checkCouple, i + 1));
                    }
                }
            }
        }
        return bridges;
    }

    public static List<AfterDoubleSetMapping> getAfterDoubleSetMappings(List<Jackpot> jackpotList) {
        List<AfterDoubleSetMapping> bridges = new ArrayList<>();
        List<AfterDoubleSetMapping> checkList = new ArrayList<>();
        int minSize = Math.min(jackpotList.size(), 30);
        for (int i = 0; i < minSize - 2; i++) {
            if (jackpotList.get(i).getCouple().isDoubleOrShadow()) {
                List<Couple> lastDoubleRange = new ArrayList<>();
                lastDoubleRange.add(jackpotList.get(i + 2).getCouple());
                lastDoubleRange.add(jackpotList.get(i + 1).getCouple());
                lastDoubleRange.add(jackpotList.get(i).getCouple());
                if (i - 1 >= 0) {
                    lastDoubleRange.add(jackpotList.get(i - 1).getCouple());
                }
                checkList.add(new AfterDoubleSetMapping(lastDoubleRange, i + 1));
            }
        }
        for (AfterDoubleSetMapping bridge : checkList) {
            for (int i = bridge.getDayNumberBefore() - 1; i >= 0; i--) {
                for (int j = 1; j <= 4; j++) {
                    if (bridge.getSetMap().containsKey(j) &&
                            Objects.requireNonNull(bridge.getSetMap().get(j))
                                    .isItMatch(jackpotList.get(i).getCoupleInt())) {
                        bridge.getSetMap().remove(j);
                    }
                }
            }
            if (!bridge.getSetMap().isEmpty()) {
                bridges.add(bridge);
            }
        }
        return bridges;
    }

}
