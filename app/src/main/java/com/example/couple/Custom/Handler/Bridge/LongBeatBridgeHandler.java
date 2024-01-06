package com.example.couple.Custom.Handler.Bridge;

import com.example.couple.Custom.Const.TimeInfo;
import com.example.couple.Model.Bridge.LongBeat.AfterDoubleBridge;
import com.example.couple.Model.Bridge.LongBeat.BranchInDayBridge;
import com.example.couple.Model.Origin.Couple;
import com.example.couple.Model.Origin.Jackpot;
import com.example.couple.Model.Time.TimeBase;

import java.util.ArrayList;
import java.util.List;

public class LongBeatBridgeHandler {
    public static List<AfterDoubleBridge> GetAfterDoubleBridges(List<Jackpot> jackpotList) {
        List<AfterDoubleBridge> bridges = new ArrayList<>();
        List<AfterDoubleBridge> checkList = new ArrayList<>();
        int maxSize = jackpotList.size() < 20 ? jackpotList.size() : 20;
        for (int i = 0; i < maxSize - 1; i++) {
            if (jackpotList.get(i).getCouple().isDouble()) {
                List<Couple> lastDoubleRange = new ArrayList<>();
                lastDoubleRange.add(jackpotList.get(i + 1).getCouple());
                lastDoubleRange.add(jackpotList.get(i).getCouple());
                if (i - 1 >= 0) {
                    lastDoubleRange.add(jackpotList.get(i - 1).getCouple());
                }
                checkList.add(new AfterDoubleBridge(lastDoubleRange, i));
            }
        }
        for (AfterDoubleBridge bridge : checkList) {
            int count = 0;
            for (int i = bridge.getDayNumberBefore(); i >= 0; i--) {
                if (!bridge.getFirstSet().isEmpty() &&
                        bridge.getFirstSet().isItMatch(jackpotList.get(i).getCouple())) {
                    count++;
                }
                if (!bridge.getSecondSet().isEmpty() &&
                        bridge.getSecondSet().isItMatch(jackpotList.get(i).getCouple())) {
                    count++;
                }

            }
            if (count <= 1) {
                bridges.add(bridge);
            }
        }
        return bridges;
    }

    public static BranchInDayBridge GetBranchInDayBridges(List<Jackpot> jackpotList, List<TimeBase> timeBaseList) {
        if (jackpotList.isEmpty() || timeBaseList.size() <= 1) return BranchInDayBridge.getEmpty();
        if (!jackpotList.get(0).getDateBase().equals(timeBaseList.get(1).getDateBase()))
            return BranchInDayBridge.getEmpty();
        List<TimeBase> timeBases = new ArrayList<>();
        int maxSize = timeBaseList.size() < 30 ? timeBaseList.size() : 30;
        for (int i = 1; i < maxSize; i++) {
            if (timeBaseList.get(i).getDateCycle().getDay().getBranch()
                    .isYearBranch(jackpotList.get(i - 1).getCoupleInt(), TimeInfo.CURRENT_YEAR)) {
                timeBases.add(timeBaseList.get(i));
            }
        }
        return new BranchInDayBridge(timeBaseList.get(0), timeBases);
    }

}
