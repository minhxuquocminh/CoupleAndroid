package com.example.couple.Custom.Handler.Bridge;

import com.example.couple.Model.Bridge.LongBeat.AfterDoubleBridge;
import com.example.couple.Model.Display.BCouple;
import com.example.couple.Model.Origin.Couple;
import com.example.couple.Model.Origin.Jackpot;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class BCoupleBridgeHandler {

    public static List<AfterDoubleBridge> getAfterDoubleBridges(List<Jackpot> jackpotList) {
        List<AfterDoubleBridge> bridges = new ArrayList<>();
        List<AfterDoubleBridge> checkList = new ArrayList<>();
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
                checkList.add(new AfterDoubleBridge(lastDoubleRange, i));
            }
        }
        for (AfterDoubleBridge bridge : checkList) {
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

    public static List<BCouple> getBalanceCouples(BCouple bcp1, BCouple bcp2) {
        List<BCouple> results = new ArrayList<>();
        results.add(bcp1.balanceOne(bcp2));
        results.add(bcp1.balanceTwo(bcp2));
        results.add(bcp1.balanceThree(bcp2));
        results.add(bcp1.balanceFour(bcp2));
        return results;
    }
}