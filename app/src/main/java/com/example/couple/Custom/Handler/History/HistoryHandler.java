package com.example.couple.Custom.Handler.History;

import com.example.couple.Base.Handler.CoupleBase;
import com.example.couple.Custom.Const.Const;
import com.example.couple.Custom.Const.TimeInfo;
import com.example.couple.Custom.Handler.Bridge.CycleBridgeHandler;
import com.example.couple.Custom.Handler.Bridge.OtherBridgeHandler;
import com.example.couple.Custom.Handler.NumberArrayHandler;
import com.example.couple.Model.Bridge.LongBeat.BranchInDayBridge;
import com.example.couple.Model.Display.NumberSetHistory;
import com.example.couple.Model.Display.Set;
import com.example.couple.Model.Origin.Jackpot;
import com.example.couple.Model.Set.SpecialSet;
import com.example.couple.Model.Time.Cycle.Branch;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class HistoryHandler {

    public static List<NumberSetHistory> getCompactNumberSetsHistory(List<Jackpot> jackpotList) {
        List<NumberSetHistory> tenNumbersList = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            NumberSetHistory head = OtherBridgeHandler.getNumberSetHistory(jackpotList,
                    Const.HEAD + " " + i, NumberArrayHandler.getHeads(i));
            tenNumbersList.add(head);
            NumberSetHistory tail = OtherBridgeHandler.getNumberSetHistory(jackpotList,
                    Const.TAIL + " " + i, NumberArrayHandler.getTails(i));
            tenNumbersList.add(tail);
            NumberSetHistory sum = OtherBridgeHandler.getNumberSetHistory(jackpotList,
                    Const.SUM + " " + i, NumberArrayHandler.getSums(i));
            tenNumbersList.add(sum);
        }

        NumberSetHistory doubleHistory = OtherBridgeHandler
                .getNumberSetHistory(jackpotList, SpecialSet.DOUBLE.name, SpecialSet.DOUBLE.values);
        tenNumbersList.add(doubleHistory);
        NumberSetHistory deviatedHistory = OtherBridgeHandler.getNumberSetHistory(jackpotList,
                SpecialSet.POSITIVE_DOUBLE.name, SpecialSet.POSITIVE_DOUBLE.values);
        tenNumbersList.add(deviatedHistory);
        NumberSetHistory nearIncreaseHistory = OtherBridgeHandler.getNumberSetHistory(jackpotList,
                SpecialSet.NEAR_DOUBLE_INCREASE.name, SpecialSet.NEAR_DOUBLE_INCREASE.values);
        tenNumbersList.add(nearIncreaseHistory);
        NumberSetHistory nearDecreaseHistory = OtherBridgeHandler.getNumberSetHistory(jackpotList,
                SpecialSet.NEAR_DOUBLE_DECREASE.name, SpecialSet.NEAR_DOUBLE_DECREASE.values);
        tenNumbersList.add(nearDecreaseHistory);
        Collections.sort(tenNumbersList, (x, y) -> y.getDayNumberBefore() - x.getDayNumberBefore());

        List<NumberSetHistory> eightNumbersList = new ArrayList<>();
        NumberSetHistory cycleSet = CycleBridgeHandler
                .getCycleInDayHistory(jackpotList, "Bộ can chi");
        eightNumbersList.add(cycleSet);

        for (int i = 0; i < TimeInfo.EARTHLY_BRANCHES.size(); i++) {
            NumberSetHistory branch = OtherBridgeHandler.getNumberSetHistory(jackpotList,
                    TimeInfo.EARTHLY_BRANCHES.get(i) + " " + CoupleBase.showCouple(i),
                    (new Branch(i)).getTailsOfYear());
            eightNumbersList.add(branch);
        }

        for (int i : Const.SMALL_SETS_NOT_DOUBLE) {
            NumberSetHistory set = OtherBridgeHandler.getNumberSetHistory(jackpotList,
                    Const.SET + " " + CoupleBase.showCouple(i),
                    Set.getFrom(i).getSetsDetail());
            eightNumbersList.add(set);
        }

        Collections.sort(eightNumbersList, (x, y) -> y.getDayNumberBefore() - x.getDayNumberBefore());

        List<NumberSetHistory> histories = new ArrayList<>();
        for (NumberSetHistory history : tenNumbersList) {
            if (history.getDayNumberBefore() > 35) {
                histories.add(history);
            }
        }

        for (NumberSetHistory history : eightNumbersList) {
            if (history.getDayNumberBefore() > 45) {
                histories.add(history);
            }
        }

        return histories;
    }

    public static List<NumberSetHistory> getNumberSetsHistory(List<Jackpot> jackpotList) {
        List<NumberSetHistory> historyList = new ArrayList<>();
        // for cycle
        BranchInDayBridge branchBridge = CycleBridgeHandler
                .getBranchInDayBridge(jackpotList);
        historyList.add(branchBridge.toSpecialSetHistory());

        NumberSetHistory cycleSet = CycleBridgeHandler
                .getCycleInDayHistory(jackpotList, "Bộ can chi");
        historyList.add(cycleSet);

        List<NumberSetHistory> branches1 = new ArrayList<>();
        for (int i = 0; i < TimeInfo.EARTHLY_BRANCHES.size(); i++) {
            NumberSetHistory branch = OtherBridgeHandler.getNumberSetHistory(jackpotList,
                    TimeInfo.EARTHLY_BRANCHES.get(i) + " " + i, (new Branch(i)).getTailsOfYear());
            branches1.add(branch);
        }
        Collections.sort(branches1, (x, y) -> y.getDayNumberBefore() - x.getDayNumberBefore());
        historyList.addAll(branches1);

        List<NumberSetHistory> branches2 = new ArrayList<>();
        for (int i = -6; i < 6; i++) {
            String specialSetName = i < 0 ? "KC" + i : "KC+" + i;
            NumberSetHistory branch = CycleBridgeHandler
                    .getBranchDistanceHistory(jackpotList, specialSetName, i);
            branches2.add(branch);
        }
        Collections.sort(branches2, (x, y) -> y.getDayNumberBefore() - x.getDayNumberBefore());
        historyList.addAll(branches2);

        List<NumberSetHistory> branches3 = new ArrayList<>();
        for (int i = -6; i < 6; i++) {
            String specialSetName = i < 0 ? "TS" + i : "TS+" + i;
            NumberSetHistory branch = CycleBridgeHandler
                    .getBranchDistanceHistoryEach2Days(jackpotList, specialSetName, i);
            branches3.add(branch);
        }
        Collections.sort(branches3, (x, y) -> y.getDayNumberBefore() - x.getDayNumberBefore());
        historyList.addAll(branches3);

        NumberSetHistory branch4 = CycleBridgeHandler
                .getPositive12BranchHistory(jackpotList, "Chi 12 dương");
        historyList.add(branch4);

        NumberSetHistory branch5 = CycleBridgeHandler
                .getNegative12BranchHistory(jackpotList, "Chi 12 âm");
        historyList.add(branch5);

        NumberSetHistory branch6 = CycleBridgeHandler
                .getPositive13BranchHistory(jackpotList, "Chi 13 dương");
        historyList.add(branch6);

        NumberSetHistory branch7 = CycleBridgeHandler
                .getNegative13BranchHistory(jackpotList, "Chi 13 âm");
        historyList.add(branch7);

        // others
        List<NumberSetHistory> headtails = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            NumberSetHistory head = OtherBridgeHandler.getNumberSetHistory(jackpotList,
                    Const.HEAD + " " + i, NumberArrayHandler.getHeads(i));
            headtails.add(head);
            NumberSetHistory tail = OtherBridgeHandler.getNumberSetHistory(jackpotList,
                    Const.TAIL + " " + i, NumberArrayHandler.getTails(i));
            headtails.add(tail);
        }
        Collections.sort(headtails, (x, y) -> y.getDayNumberBefore() - x.getDayNumberBefore());
        historyList.addAll(headtails);

        List<NumberSetHistory> sums = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            NumberSetHistory sum = OtherBridgeHandler.getNumberSetHistory(jackpotList,
                    Const.SUM + " " + i, NumberArrayHandler.getSums(i));
            sums.add(sum);
        }
        Collections.sort(sums, (x, y) -> y.getDayNumberBefore() - x.getDayNumberBefore());
        historyList.addAll(sums);

        List<NumberSetHistory> sets = new ArrayList<>();
        for (int i : Const.SMALL_SETS_NOT_DOUBLE) {
            NumberSetHistory set = OtherBridgeHandler.getNumberSetHistory(jackpotList,
                    Const.SET + " " + i, Set.getFrom(i).getSetsDetail());
            sets.add(set);
        }
        Collections.sort(sets, (x, y) -> y.getDayNumberBefore() - x.getDayNumberBefore());
        historyList.addAll(sets);

        List<NumberSetHistory> others = new ArrayList<>();
        NumberSetHistory doubleHistory = OtherBridgeHandler
                .getNumberSetHistory(jackpotList, SpecialSet.DOUBLE.name, SpecialSet.DOUBLE.values);
        others.add(doubleHistory);
        NumberSetHistory deviatedHistory = OtherBridgeHandler.getNumberSetHistory(jackpotList,
                SpecialSet.POSITIVE_DOUBLE.name, SpecialSet.POSITIVE_DOUBLE.values);
        others.add(deviatedHistory);
        NumberSetHistory nearIncreaseHistory = OtherBridgeHandler.getNumberSetHistory(jackpotList,
                SpecialSet.NEAR_DOUBLE_INCREASE.name, SpecialSet.NEAR_DOUBLE_INCREASE.values);
        others.add(nearIncreaseHistory);
        NumberSetHistory nearDecreaseHistory = OtherBridgeHandler.getNumberSetHistory(jackpotList,
                SpecialSet.NEAR_DOUBLE_DECREASE.name, SpecialSet.NEAR_DOUBLE_DECREASE.values);
        others.add(nearDecreaseHistory);
        Collections.sort(others, (x, y) -> y.getDayNumberBefore() - x.getDayNumberBefore());
        historyList.addAll(others);

        return historyList;
    }
}
