package com.example.couple.Custom.Handler.History;

import com.example.couple.Custom.Const.Const;
import com.example.couple.Custom.Const.TimeInfo;
import com.example.couple.Custom.Handler.Bridge.CycleBridgeHandler;
import com.example.couple.Custom.Handler.Bridge.OtherBridgeHandler;
import com.example.couple.Custom.Handler.NumberArrayHandler;
import com.example.couple.Model.Bridge.LongBeat.BranchInDayBridge;
import com.example.couple.Model.Display.Set;
import com.example.couple.Model.Display.SpecialSetHistory;
import com.example.couple.Model.Origin.Jackpot;
import com.example.couple.Model.Time.Cycle.Branch;
import com.example.couple.Model.Time.TimeBase;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class HistoryHandler {

    public static List<SpecialSetHistory> getCompactSpecialSetsHistory(List<Jackpot> jackpotList) {
        List<SpecialSetHistory> tenNumbersList = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            SpecialSetHistory head = OtherBridgeHandler.getSpecialSetHistory(jackpotList,
                    Const.HEAD + " " + i, NumberArrayHandler.getHeads(i));
            tenNumbersList.add(head);
            SpecialSetHistory tail = OtherBridgeHandler.getSpecialSetHistory(jackpotList,
                    Const.TAIL + " " + i, NumberArrayHandler.getTails(i));
            tenNumbersList.add(tail);
            SpecialSetHistory sum = OtherBridgeHandler.getSpecialSetHistory(jackpotList,
                    Const.SUM + " " + i, NumberArrayHandler.getSums(i));
            tenNumbersList.add(sum);
        }

        SpecialSetHistory doubleHistory = OtherBridgeHandler
                .getSpecialSetHistory(jackpotList, Const.DOUBLE, Const.DOUBLE_SET);
        tenNumbersList.add(doubleHistory);
        SpecialSetHistory deviatedHistory = OtherBridgeHandler
                .getSpecialSetHistory(jackpotList, Const.POSITIVE_DOUBLE_SET_NAME, Const.POSITIVE_DOUBLE_SET);
        tenNumbersList.add(deviatedHistory);
        SpecialSetHistory nearIncreaseHistory = OtherBridgeHandler
                .getSpecialSetHistory(jackpotList, Const.NEAR_DOUBLE_INCREASE, Const.NEAR_DOUBLE_INCREASE_SET);
        tenNumbersList.add(nearIncreaseHistory);
        SpecialSetHistory nearDecreaseHistory = OtherBridgeHandler
                .getSpecialSetHistory(jackpotList, Const.NEAR_DOUBLE_DECREASE, Const.NEAR_DOUBLE_DECREASE_SET);
        tenNumbersList.add(nearDecreaseHistory);
        Collections.sort(tenNumbersList, (x, y) -> y.getDayNumberBefore() - x.getDayNumberBefore());

        List<SpecialSetHistory> eightNumbersList = new ArrayList<>();
        for (int i = 0; i < TimeInfo.EARTHLY_BRANCHES.size(); i++) {
            SpecialSetHistory branch = OtherBridgeHandler.getSpecialSetHistory(jackpotList,
                    TimeInfo.EARTHLY_BRANCHES.get(i) + " " + i, (new Branch(i)).getTailsOfYear());
            eightNumbersList.add(branch);
        }

        for (int i : Const.SMALL_SETS_NOT_DOUBLE) {
            SpecialSetHistory set = OtherBridgeHandler.getSpecialSetHistory(jackpotList,
                    Const.SET + " " + i, (new Set(i)).getSetsDetail());
            eightNumbersList.add(set);
        }

        Collections.sort(eightNumbersList, (x, y) -> y.getDayNumberBefore() - x.getDayNumberBefore());

        List<SpecialSetHistory> histories = new ArrayList<>();
        for (SpecialSetHistory history : tenNumbersList) {
            if (history.getDayNumberBefore() > 30) {
                histories.add(history);
            }
        }

        for (SpecialSetHistory history : eightNumbersList) {
            if (history.getDayNumberBefore() > 40) {
                histories.add(history);
            }
        }

        return histories;
    }

    public static List<SpecialSetHistory> getSpecialSetsHistory(List<Jackpot> allJackpotList,
                                                                List<Jackpot> jackpotList,
                                                                TimeBase timeBaseNextDay) {
        List<SpecialSetHistory> historyList = new ArrayList<>();
        // for cycle
        BranchInDayBridge branchBridge = CycleBridgeHandler
                .getBranchInDayBridges(allJackpotList, timeBaseNextDay.getDateCycle().getDay().getBranch());
        historyList.add(branchBridge.toSpecialSetHistory());

        List<SpecialSetHistory> branches1 = new ArrayList<>();
        for (int i = 0; i < TimeInfo.EARTHLY_BRANCHES.size(); i++) {
            SpecialSetHistory branch = OtherBridgeHandler.getSpecialSetHistory(jackpotList,
                    TimeInfo.EARTHLY_BRANCHES.get(i) + " " + i, (new Branch(i)).getTailsOfYear());
            branches1.add(branch);
        }
        Collections.sort(branches1, (x, y) -> y.getDayNumberBefore() - x.getDayNumberBefore());
        historyList.addAll(branches1);

        List<SpecialSetHistory> branches2 = new ArrayList<>();
        Branch nextDayBranch = timeBaseNextDay.getDateCycle().getDay().getBranch();
        for (int i = -6; i < 6; i++) {
            String specialSetName = i < 0 ? "KC" + i : "KC+" + i;
            SpecialSetHistory branch = CycleBridgeHandler
                    .getBranchDistanceHistory(jackpotList, specialSetName, i, nextDayBranch);
            branches2.add(branch);
        }
        Collections.sort(branches2, (x, y) -> y.getDayNumberBefore() - x.getDayNumberBefore());
        historyList.addAll(branches2);

        List<SpecialSetHistory> branches3 = new ArrayList<>();
        for (int i = -6; i < 6; i++) {
            String specialSetName = i < 0 ? "TS" + i : "TS+" + i;
            SpecialSetHistory branch = CycleBridgeHandler
                    .getBranchDistanceHistoryEach2Days(jackpotList, specialSetName, i);
            branches3.add(branch);
        }
        Collections.sort(branches3, (x, y) -> y.getDayNumberBefore() - x.getDayNumberBefore());
        historyList.addAll(branches3);

        SpecialSetHistory branch4 = CycleBridgeHandler
                .getPositive12BranchHistory(jackpotList, "Chi 12 dương");
        historyList.add(branch4);

        SpecialSetHistory branch5 = CycleBridgeHandler
                .getNegative12BranchHistory(jackpotList, "Chi 12 âm");
        historyList.add(branch5);

        SpecialSetHistory branch6 = CycleBridgeHandler
                .getPositive13BranchHistory(jackpotList, "Chi 13 dương");
        historyList.add(branch6);

        SpecialSetHistory branch7 = CycleBridgeHandler
                .getNegative13BranchHistory(jackpotList, "Chi 13 âm");
        historyList.add(branch7);

        // others
        List<SpecialSetHistory> headtails = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            SpecialSetHistory head = OtherBridgeHandler.getSpecialSetHistory(jackpotList,
                    Const.HEAD + " " + i, NumberArrayHandler.getHeads(i));
            headtails.add(head);
            SpecialSetHistory tail = OtherBridgeHandler.getSpecialSetHistory(jackpotList,
                    Const.TAIL + " " + i, NumberArrayHandler.getTails(i));
            headtails.add(tail);
        }
        Collections.sort(headtails, (x, y) -> y.getDayNumberBefore() - x.getDayNumberBefore());
        historyList.addAll(headtails);

        List<SpecialSetHistory> sums = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            SpecialSetHistory sum = OtherBridgeHandler.getSpecialSetHistory(jackpotList,
                    Const.SUM + " " + i, NumberArrayHandler.getSums(i));
            sums.add(sum);
        }
        Collections.sort(sums, (x, y) -> y.getDayNumberBefore() - x.getDayNumberBefore());
        historyList.addAll(sums);

        List<SpecialSetHistory> sets = new ArrayList<>();
        for (int i : Const.SMALL_SETS_NOT_DOUBLE) {
            SpecialSetHistory set = OtherBridgeHandler.getSpecialSetHistory(jackpotList,
                    Const.SET + " " + i, (new Set(i)).getSetsDetail());
            sets.add(set);
        }
        Collections.sort(sets, (x, y) -> y.getDayNumberBefore() - x.getDayNumberBefore());
        historyList.addAll(sets);

        List<SpecialSetHistory> others = new ArrayList<>();
        SpecialSetHistory doubleHistory = OtherBridgeHandler
                .getSpecialSetHistory(jackpotList, Const.DOUBLE, Const.DOUBLE_SET);
        others.add(doubleHistory);
        SpecialSetHistory deviatedHistory = OtherBridgeHandler
                .getSpecialSetHistory(jackpotList, Const.POSITIVE_DOUBLE_SET_NAME, Const.POSITIVE_DOUBLE_SET);
        others.add(deviatedHistory);
        SpecialSetHistory nearIncreaseHistory = OtherBridgeHandler
                .getSpecialSetHistory(jackpotList, Const.NEAR_DOUBLE_INCREASE, Const.NEAR_DOUBLE_INCREASE_SET);
        others.add(nearIncreaseHistory);
        SpecialSetHistory nearDecreaseHistory = OtherBridgeHandler
                .getSpecialSetHistory(jackpotList, Const.NEAR_DOUBLE_DECREASE, Const.NEAR_DOUBLE_DECREASE_SET);
        others.add(nearDecreaseHistory);
        Collections.sort(others, (x, y) -> y.getDayNumberBefore() - x.getDayNumberBefore());
        historyList.addAll(others);

        return historyList;
    }
}
