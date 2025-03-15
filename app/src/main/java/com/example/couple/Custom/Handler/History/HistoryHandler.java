package com.example.couple.Custom.Handler.History;

import com.example.couple.Base.Handler.CoupleBase;
import com.example.couple.Custom.Const.Const;
import com.example.couple.Custom.Const.TimeInfo;
import com.example.couple.Custom.Handler.Bridge.CycleBridgeHandler;
import com.example.couple.Custom.Handler.NumberArrayHandler;
import com.example.couple.Model.Bridge.Cycle.BranchInDayBridge;
import com.example.couple.Model.Bridge.NumberSet.NumberSet;
import com.example.couple.Model.DateTime.Date.Cycle.Branch;
import com.example.couple.Model.Bridge.NumberSet.NumericSetHistory;
import com.example.couple.Model.Origin.Jackpot;
import com.example.couple.Model.Bridge.NumberSet.SpecialSet;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class HistoryHandler {

    public static List<NumericSetHistory> getCompactNumberSetsHistory(List<Jackpot> jackpotList) {
        if (jackpotList.isEmpty()) return new ArrayList<>();
        List<NumericSetHistory> tenNumbersList = new ArrayList<>();
        List<NumericSetHistory> tenNumbersList2 = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            // for đề đầu
            NumericSetHistory head1 = getNumberSetHistoryWithHeadCouple(jackpotList,
                    Const.HEAD + " " + CoupleBase.showCouple(i) + " (***) ", NumberArrayHandler.getHeads(i));
            tenNumbersList2.add(head1);
            NumericSetHistory tail1 = getNumberSetHistoryWithHeadCouple(jackpotList,
                    Const.TAIL + " " + CoupleBase.showCouple(i) + " (***) ", NumberArrayHandler.getTails(i));
            tenNumbersList2.add(tail1);
            NumericSetHistory sum1 = getNumberSetHistoryWithHeadCouple(jackpotList,
                    Const.SUM + " " + CoupleBase.showCouple(i) + " (***) ", NumberArrayHandler.getSums(i));
            tenNumbersList2.add(sum1);
            // for đề đuôi
            NumericSetHistory head = getNumberSetHistory(jackpotList,
                    Const.HEAD + " " + CoupleBase.showCouple(i), NumberArrayHandler.getHeads(i));
            tenNumbersList.add(head);
            NumericSetHistory tail = getNumberSetHistory(jackpotList,
                    Const.TAIL + " " + CoupleBase.showCouple(i), NumberArrayHandler.getTails(i));
            tenNumbersList.add(tail);
            NumericSetHistory sum = getNumberSetHistory(jackpotList,
                    Const.SUM + " " + CoupleBase.showCouple(i), NumberArrayHandler.getSums(i));
            tenNumbersList.add(sum);
        }
        tenNumbersList2.sort((x, y) -> y.getDayNumberBefore() - x.getDayNumberBefore());

        NumericSetHistory doubleHistory = getNumberSetHistory(jackpotList,
                SpecialSet.DOUBLE.name, SpecialSet.DOUBLE.values);
        tenNumbersList.add(doubleHistory);
        NumericSetHistory deviatedHistory = getNumberSetHistory(jackpotList,
                SpecialSet.POSITIVE_DOUBLE.name, SpecialSet.POSITIVE_DOUBLE.values);
        tenNumbersList.add(deviatedHistory);
        NumericSetHistory nearIncreaseHistory = getNumberSetHistory(jackpotList,
                SpecialSet.NEAR_DOUBLE_INCREASE.name, SpecialSet.NEAR_DOUBLE_INCREASE.values);
        tenNumbersList.add(nearIncreaseHistory);
        NumericSetHistory nearDecreaseHistory = getNumberSetHistory(jackpotList,
                SpecialSet.NEAR_DOUBLE_DECREASE.name, SpecialSet.NEAR_DOUBLE_DECREASE.values);
        tenNumbersList.add(nearDecreaseHistory);
        tenNumbersList.sort((x, y) -> y.getDayNumberBefore() - x.getDayNumberBefore());

        List<NumericSetHistory> eightNumbersList = new ArrayList<>();
        NumericSetHistory cycleSet = CycleBridgeHandler
                .getCycleInDayHistory(jackpotList, "Bộ can chi");
        eightNumbersList.add(cycleSet);

        for (int i = 0; i < TimeInfo.EARTHLY_BRANCHES.size(); i++) {
            NumericSetHistory branch = getNumberSetHistory(jackpotList,
                    TimeInfo.EARTHLY_BRANCHES.get(i) + " " + CoupleBase.showCouple(i),
                    (new Branch(i)).getTailsOfYear());
            eightNumbersList.add(branch);
        }

        for (int i : Const.SMALL_SETS_NOT_DOUBLE) {
            NumericSetHistory set = getNumberSetHistory(jackpotList,
                    Const.SET + " " + CoupleBase.showCouple(i),
                    NumberSet.getFrom(i).getSetsDetail());
            eightNumbersList.add(set);
        }

        eightNumbersList.sort((x, y) -> y.getDayNumberBefore() - x.getDayNumberBefore());

        List<NumericSetHistory> histories = new ArrayList<>();
        for (NumericSetHistory history : tenNumbersList2) {
            if (history.getDayNumberBefore() > 79) {
                histories.add(history);
            }
        }

        for (NumericSetHistory history : tenNumbersList) {
            if (history.getDayNumberBefore() > 30) {
                histories.add(history);
            }
        }

        for (NumericSetHistory history : eightNumbersList) {
            if (history.getDayNumberBefore() > 40) {
                histories.add(history);
            }
        }

        return histories;
    }

    public static List<NumericSetHistory> getNumberSetsHistoryType2(List<Jackpot> jackpotList) {
        if (jackpotList.isEmpty()) return new ArrayList<>();
        List<NumericSetHistory> tenNumbersList = new ArrayList<>();
        List<NumericSetHistory> tenNumbersList2 = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            // for đề đầu
            NumericSetHistory head1 = getNumberSetHistoryWithHeadCouple(jackpotList,
                    Const.HEAD + " " + CoupleBase.showCouple(i) + " (***) ", NumberArrayHandler.getHeads(i));
            tenNumbersList2.add(head1);
            NumericSetHistory tail1 = getNumberSetHistoryWithHeadCouple(jackpotList,
                    Const.TAIL + " " + CoupleBase.showCouple(i) + " (***) ", NumberArrayHandler.getTails(i));
            tenNumbersList2.add(tail1);
            NumericSetHistory sum1 = getNumberSetHistoryWithHeadCouple(jackpotList,
                    Const.SUM + " " + CoupleBase.showCouple(i) + " (***) ", NumberArrayHandler.getSums(i));
            tenNumbersList2.add(sum1);
            // for đề đuôi
            NumericSetHistory head = getNumberSetHistory(jackpotList,
                    Const.HEAD + " " + CoupleBase.showCouple(i), NumberArrayHandler.getHeads(i));
            tenNumbersList.add(head);
            NumericSetHistory tail = getNumberSetHistory(jackpotList,
                    Const.TAIL + " " + CoupleBase.showCouple(i), NumberArrayHandler.getTails(i));
            tenNumbersList.add(tail);
            NumericSetHistory sum = getNumberSetHistory(jackpotList,
                    Const.SUM + " " + CoupleBase.showCouple(i), NumberArrayHandler.getSums(i));
            tenNumbersList.add(sum);
        }
        tenNumbersList2.sort((x, y) -> y.getDayNumberBefore() - x.getDayNumberBefore());

        NumericSetHistory doubleHistory = getNumberSetHistory(jackpotList,
                SpecialSet.DOUBLE.name, SpecialSet.DOUBLE.values);
        tenNumbersList.add(doubleHistory);
        NumericSetHistory deviatedHistory = getNumberSetHistory(jackpotList,
                SpecialSet.POSITIVE_DOUBLE.name, SpecialSet.POSITIVE_DOUBLE.values);
        tenNumbersList.add(deviatedHistory);
        NumericSetHistory nearIncreaseHistory = getNumberSetHistory(jackpotList,
                SpecialSet.NEAR_DOUBLE_INCREASE.name, SpecialSet.NEAR_DOUBLE_INCREASE.values);
        tenNumbersList.add(nearIncreaseHistory);
        NumericSetHistory nearDecreaseHistory = getNumberSetHistory(jackpotList,
                SpecialSet.NEAR_DOUBLE_DECREASE.name, SpecialSet.NEAR_DOUBLE_DECREASE.values);
        tenNumbersList.add(nearDecreaseHistory);
        tenNumbersList.sort((x, y) -> y.getDayNumberBefore() - x.getDayNumberBefore());

        List<NumericSetHistory> eightNumbersList = new ArrayList<>();
        NumericSetHistory cycleSet = CycleBridgeHandler
                .getCycleInDayHistory(jackpotList, "Bộ can chi");
        eightNumbersList.add(cycleSet);

        for (int i = 0; i < TimeInfo.EARTHLY_BRANCHES.size(); i++) {
            NumericSetHistory branch = getNumberSetHistory(jackpotList,
                    TimeInfo.EARTHLY_BRANCHES.get(i) + " " + CoupleBase.showCouple(i),
                    (new Branch(i)).getTailsOfYear());
            eightNumbersList.add(branch);
        }

        for (int i : Const.SMALL_SETS_NOT_DOUBLE) {
            NumericSetHistory set = getNumberSetHistory(jackpotList,
                    Const.SET + " " + CoupleBase.showCouple(i),
                    NumberSet.getFrom(i).getSetsDetail());
            eightNumbersList.add(set);
        }

        eightNumbersList.sort((x, y) -> y.getDayNumberBefore() - x.getDayNumberBefore());

        List<NumericSetHistory> histories = new ArrayList<>();
        for (NumericSetHistory history : tenNumbersList2) {
            if (history.getDayNumberBefore() > 79) {
                histories.add(history);
            }
        }

        for (NumericSetHistory history : tenNumbersList) {
            if (history.getDayNumberBefore() > 50) {
                histories.add(history);
            }
        }

        for (NumericSetHistory history : eightNumbersList) {
            if (history.getDayNumberBefore() > 50) {
                histories.add(history);
            }
        }

        return histories;
    }

    public static List<NumericSetHistory> getNumberSetsHistory(List<Jackpot> jackpotList) {
        if (jackpotList.isEmpty()) return new ArrayList<>();
        List<NumericSetHistory> historyList = new ArrayList<>();
        // for cycle
        BranchInDayBridge branchBridge = CycleBridgeHandler
                .getBranchInDayBridge(jackpotList);
        historyList.add(branchBridge.toNumberSetHistory());

        NumericSetHistory cycleSet = CycleBridgeHandler
                .getCycleInDayHistory(jackpotList, "Bộ can chi");
        historyList.add(cycleSet);

        List<NumericSetHistory> branches1 = new ArrayList<>();
        for (int i = 0; i < TimeInfo.EARTHLY_BRANCHES.size(); i++) {
            NumericSetHistory branch = getNumberSetHistory(jackpotList,
                    TimeInfo.EARTHLY_BRANCHES.get(i) + " " + CoupleBase.showCouple(i),
                    (new Branch(i)).getTailsOfYear());
            branches1.add(branch);
        }
        branches1.sort((x, y) -> y.getDayNumberBefore() - x.getDayNumberBefore());
        historyList.addAll(branches1);

        List<NumericSetHistory> branches2 = new ArrayList<>();
        for (int i = -6; i < 6; i++) {
            String specialSetName = i < 0 ? "KC" + i : "KC+" + i;
            NumericSetHistory branch = CycleBridgeHandler
                    .getBranchDistanceHistory(jackpotList, specialSetName, i);
            branches2.add(branch);
        }
        branches2.sort((x, y) -> y.getDayNumberBefore() - x.getDayNumberBefore());
        historyList.addAll(branches2);

        List<NumericSetHistory> branches3 = new ArrayList<>();
        for (int i = -6; i < 6; i++) {
            String specialSetName = i < 0 ? "TS" + i : "TS+" + i;
            NumericSetHistory branch = CycleBridgeHandler
                    .getBranchDistanceHistoryEach2Days(jackpotList, specialSetName, i);
            branches3.add(branch);
        }
        branches3.sort((x, y) -> y.getDayNumberBefore() - x.getDayNumberBefore());
        historyList.addAll(branches3);

        NumericSetHistory branch4 = CycleBridgeHandler
                .getPositive12BranchHistory(jackpotList, "Chi 12 dương");
        historyList.add(branch4);

        NumericSetHistory branch5 = CycleBridgeHandler
                .getNegative12BranchHistory(jackpotList, "Chi 12 âm");
        historyList.add(branch5);

        NumericSetHistory branch6 = CycleBridgeHandler
                .getPositive13BranchHistory(jackpotList, "Chi 13 dương");
        historyList.add(branch6);

        NumericSetHistory branch7 = CycleBridgeHandler
                .getNegative13BranchHistory(jackpotList, "Chi 13 âm");
        historyList.add(branch7);

        // others
        List<NumericSetHistory> headtails = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            NumericSetHistory head = getNumberSetHistory(jackpotList,
                    Const.HEAD + " " + i, NumberArrayHandler.getHeads(i));
            headtails.add(head);
            NumericSetHistory tail = getNumberSetHistory(jackpotList,
                    Const.TAIL + " " + i, NumberArrayHandler.getTails(i));
            headtails.add(tail);
        }
        headtails.sort((x, y) -> y.getDayNumberBefore() - x.getDayNumberBefore());
        historyList.addAll(headtails);

        List<NumericSetHistory> sums = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            NumericSetHistory sum = getNumberSetHistory(jackpotList,
                    Const.SUM + " " + i, NumberArrayHandler.getSums(i));
            sums.add(sum);
        }
        sums.sort((x, y) -> y.getDayNumberBefore() - x.getDayNumberBefore());
        historyList.addAll(sums);

        List<NumericSetHistory> sets = new ArrayList<>();
        for (int i : Const.SMALL_SETS_NOT_DOUBLE) {
            NumericSetHistory set = getNumberSetHistory(jackpotList,
                    Const.SET + " " + CoupleBase.showCouple(i), NumberSet.getFrom(i).getSetsDetail());
            sets.add(set);
        }
        sets.sort((x, y) -> y.getDayNumberBefore() - x.getDayNumberBefore());
        historyList.addAll(sets);

        List<NumericSetHistory> others = new ArrayList<>();
        NumericSetHistory doubleHistory = getNumberSetHistory(jackpotList,
                SpecialSet.DOUBLE.name, SpecialSet.DOUBLE.values);
        others.add(doubleHistory);
        NumericSetHistory deviatedHistory = getNumberSetHistory(jackpotList,
                SpecialSet.POSITIVE_DOUBLE.name, SpecialSet.POSITIVE_DOUBLE.values);
        others.add(deviatedHistory);
        NumericSetHistory nearIncreaseHistory = getNumberSetHistory(jackpotList,
                SpecialSet.NEAR_DOUBLE_INCREASE.name, SpecialSet.NEAR_DOUBLE_INCREASE.values);
        others.add(nearIncreaseHistory);
        NumericSetHistory nearDecreaseHistory = getNumberSetHistory(jackpotList,
                SpecialSet.NEAR_DOUBLE_DECREASE.name, SpecialSet.NEAR_DOUBLE_DECREASE.values);
        others.add(nearDecreaseHistory);
        others.sort((x, y) -> y.getDayNumberBefore() - x.getDayNumberBefore());
        historyList.addAll(others);

        return historyList;
    }

    public static List<NumericSetHistory> getCustomNumberSetsHistory(List<Jackpot> jackpotList) {
        if (jackpotList.isEmpty()) return new ArrayList<>();

        List<NumericSetHistory> sets = new ArrayList<>();
        for (int i : Const.SMALL_SETS_NOT_DOUBLE) {
            NumericSetHistory set = getNumberSetHistory(jackpotList,
                    Const.SET + " " + CoupleBase.showCouple(i), NumberSet.getFrom(i).getSetsDetail());
            sets.add(set);
        }
        sets.sort((x, y) -> y.getDayNumberBefore() - x.getDayNumberBefore());

        List<NumericSetHistory> headtails = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            NumericSetHistory head = getNumberSetHistory(jackpotList,
                    Const.HEAD + " " + i, NumberArrayHandler.getHeads(i));
            headtails.add(head);
            NumericSetHistory tail = getNumberSetHistory(jackpotList,
                    Const.TAIL + " " + i, NumberArrayHandler.getTails(i));
            headtails.add(tail);
        }
        headtails.sort((x, y) -> y.getDayNumberBefore() - x.getDayNumberBefore());
        sets.addAll(headtails);

        return sets;
    }

    public static List<NumericSetHistory> getFixedNumberSetsHistory(List<Jackpot> jackpotList) {
        if (jackpotList.isEmpty()) return new ArrayList<>();
        List<NumericSetHistory> historyList = new ArrayList<>();
        // for cycle
        for (int i = 0; i < TimeInfo.EARTHLY_BRANCHES.size(); i++) {
            NumericSetHistory branch = getNumberSetHistory(jackpotList,
                    TimeInfo.EARTHLY_BRANCHES.get(i) + " " + CoupleBase.showCouple(i),
                    (new Branch(i)).getTailsOfYear());
            historyList.add(branch);
        }
        historyList.sort((x, y) -> y.getDayNumberBefore() - x.getDayNumberBefore());

        // others
        List<NumericSetHistory> headtails = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            NumericSetHistory head = getNumberSetHistory(jackpotList,
                    Const.HEAD + " " + i, NumberArrayHandler.getHeads(i));
            headtails.add(head);
            NumericSetHistory tail = getNumberSetHistory(jackpotList,
                    Const.TAIL + " " + i, NumberArrayHandler.getTails(i));
            headtails.add(tail);
        }
        headtails.sort((x, y) -> y.getDayNumberBefore() - x.getDayNumberBefore());
        historyList.addAll(headtails);

        List<NumericSetHistory> sums = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            NumericSetHistory sum = getNumberSetHistory(jackpotList,
                    Const.SUM + " " + i, NumberArrayHandler.getSums(i));
            sums.add(sum);
        }
        sums.sort((x, y) -> y.getDayNumberBefore() - x.getDayNumberBefore());
        historyList.addAll(sums);

        List<NumericSetHistory> headtails1 = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            NumericSetHistory head = getNumberSetHistoryWithHeadCouple(jackpotList,
                    Const.HEAD + " " + i + " (***) ", NumberArrayHandler.getHeads(i));
            headtails1.add(head);
            NumericSetHistory tail = getNumberSetHistoryWithHeadCouple(jackpotList,
                    Const.TAIL + " " + i + " (***) ", NumberArrayHandler.getTails(i));
            headtails1.add(tail);
        }
        headtails1.sort((x, y) -> y.getDayNumberBefore() - x.getDayNumberBefore());
        historyList.addAll(headtails1);

        List<NumericSetHistory> sums1 = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            NumericSetHistory sum = getNumberSetHistoryWithHeadCouple(jackpotList,
                    Const.SUM + " " + i + " (***) ", NumberArrayHandler.getSums(i));
            sums1.add(sum);
        }
        sums1.sort((x, y) -> y.getDayNumberBefore() - x.getDayNumberBefore());
        historyList.addAll(sums1);

        List<NumericSetHistory> sets = new ArrayList<>();
        for (int i : Const.SMALL_SETS_NOT_DOUBLE) {
            NumericSetHistory set = getNumberSetHistory(jackpotList,
                    Const.SET + " " + CoupleBase.showCouple(i), NumberSet.getFrom(i).getSetsDetail());
            sets.add(set);
        }
        sets.sort((x, y) -> y.getDayNumberBefore() - x.getDayNumberBefore());
        historyList.addAll(sets);

        List<NumericSetHistory> others = new ArrayList<>();
        NumericSetHistory doubleHistory = getNumberSetHistory(jackpotList,
                SpecialSet.DOUBLE.name, SpecialSet.DOUBLE.values);
        others.add(doubleHistory);
        NumericSetHistory deviatedHistory = getNumberSetHistory(jackpotList,
                SpecialSet.POSITIVE_DOUBLE.name, SpecialSet.POSITIVE_DOUBLE.values);
        others.add(deviatedHistory);
        NumericSetHistory nearIncreaseHistory = getNumberSetHistory(jackpotList,
                SpecialSet.NEAR_DOUBLE_INCREASE.name, SpecialSet.NEAR_DOUBLE_INCREASE.values);
        others.add(nearIncreaseHistory);
        NumericSetHistory nearDecreaseHistory = getNumberSetHistory(jackpotList,
                SpecialSet.NEAR_DOUBLE_DECREASE.name, SpecialSet.NEAR_DOUBLE_DECREASE.values);
        others.add(nearDecreaseHistory);
        others.sort((x, y) -> y.getDayNumberBefore() - x.getDayNumberBefore());
        historyList.addAll(others);

        return historyList;
    }

    public static NumericSetHistory getNumberSetHistory(List<Jackpot> jackpotList,
                                                        String numberSetName, List<Integer> numbers) {
        if (jackpotList.isEmpty()) return NumericSetHistory.getEmpty();
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
        return new NumericSetHistory(numberSetName, numbers, beatList);
    }

    public static NumericSetHistory getNumberSetHistoryWithHeadCouple(List<Jackpot> jackpotList,
                                                                      String numberSetName, List<Integer> numbers) {
        if (jackpotList.isEmpty()) return NumericSetHistory.getEmpty();
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
        return new NumericSetHistory(numberSetName, numbers, beatList);
    }

}
