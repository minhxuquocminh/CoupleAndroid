package com.example.couple.Model.Bridge.NumberSet;

import com.example.couple.Base.Handler.CoupleBase;
import com.example.couple.Custom.Const.Const;
import com.example.couple.Custom.Const.TimeInfo;
import com.example.couple.Custom.Handler.Bridge.CycleBridgeHandler;
import com.example.couple.Custom.Handler.History.HistoryHandler;
import com.example.couple.Custom.Handler.NumberArrayHandler;
import com.example.couple.Model.Bridge.Cycle.BranchInDayBridge;
import com.example.couple.Model.DateTime.Date.Cycle.Branch;
import com.example.couple.Model.Origin.Jackpot;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum NumberSetType {
    HEAD("Đầu", 10),
    TAIL("Đuôi", 10),
    SUM("Tổng", 10),
    SET("Bộ", 8),
    DOUBLE("Kép", 10),
    BRANCH("Chi", 8),
    BRANCH_IN_DAY("CTN", 10),

    HEAD_BEGIN("Đầu bắt đầu", 6),
    TAIL_BEGIN("Đuôi bắt đầu", 6),
    SUM_BEGIN("Tổng bắt đầu", 6);

    public final String name;

    public final int size;

    public static List<NumberSetType> getValuesWithCouple() {
        return Arrays.asList(HEAD, TAIL, SUM, SET, DOUBLE, BRANCH, BRANCH_IN_DAY);
    }

    public List<NumberSetHistory> getNumberSetHistory(List<Jackpot> jackpotList) {
        switch (this) {
            case HEAD:
                return IntStream.range(0, 10)
                        .mapToObj(i -> HistoryHandler.getNumberSetHistory(jackpotList,
                                name + " " + i, NumberArrayHandler.getHeads(i)))
                        .sorted(Comparator.comparingInt(NumberSetHistory::getDayNumberBefore).reversed())
                        .collect(Collectors.toList());
            case TAIL:
                return IntStream.range(0, 10)
                        .mapToObj(i -> HistoryHandler.getNumberSetHistory(jackpotList,
                                name + " " + i, NumberArrayHandler.getTails(i)))
                        .sorted(Comparator.comparingInt(NumberSetHistory::getDayNumberBefore).reversed())
                        .collect(Collectors.toList());
            case SUM:
                return IntStream.range(0, 10)
                        .mapToObj(i -> HistoryHandler.getNumberSetHistory(jackpotList,
                                name + " " + i, NumberArrayHandler.getSums(i)))
                        .sorted(Comparator.comparingInt(NumberSetHistory::getDayNumberBefore).reversed())
                        .collect(Collectors.toList());
            case SET:
                return Const.SMALL_SETS_NOT_DOUBLE.stream()
                        .map(i -> HistoryHandler.getNumberSetHistory(jackpotList,
                                Const.SET + " " + CoupleBase.showCouple(i),
                                SetBase.getFrom(i).getSetsDetail()))
                        .sorted(Comparator.comparingInt(NumberSetHistory::getDayNumberBefore).reversed())
                        .collect(Collectors.toList());
            case BRANCH_IN_DAY:
                if (jackpotList.isEmpty() || jackpotList.get(0).getDayCycle().isEmpty())
                    return new ArrayList<>();
                BranchInDayBridge branchInDayBridge = CycleBridgeHandler
                        .getBranchInDayBridge(jackpotList);
                return Collections.singletonList(branchInDayBridge.toNumberSetHistory());
            case BRANCH:
                return IntStream.range(0, TimeInfo.EARTHLY_BRANCHES.size())
                        .mapToObj(i -> HistoryHandler.getNumberSetHistory(jackpotList,
                                TimeInfo.EARTHLY_BRANCHES.get(i) + " " + CoupleBase.showCouple(i),
                                new Branch(i).getTailsOfYear()))
                        .sorted(Comparator.comparingInt(NumberSetHistory::getDayNumberBefore).reversed())
                        .collect(Collectors.toList());
            case DOUBLE:
                List<NumberSetHistory> doubles = new ArrayList<>();
                NumberSetHistory doubleHistory = HistoryHandler.getNumberSetHistory(jackpotList,
                        SpecialSet.DOUBLE.name, SpecialSet.DOUBLE.values);
                doubles.add(doubleHistory);
                NumberSetHistory deviatedHistory = HistoryHandler.getNumberSetHistory(jackpotList,
                        SpecialSet.POSITIVE_DOUBLE.name, SpecialSet.POSITIVE_DOUBLE.values);
                doubles.add(deviatedHistory);
                NumberSetHistory nearIncreaseHistory = HistoryHandler.getNumberSetHistory(jackpotList,
                        SpecialSet.NEAR_DOUBLE_INCREASE.name, SpecialSet.NEAR_DOUBLE_INCREASE.values);
                doubles.add(nearIncreaseHistory);
                NumberSetHistory nearDecreaseHistory = HistoryHandler.getNumberSetHistory(jackpotList,
                        SpecialSet.NEAR_DOUBLE_DECREASE.name, SpecialSet.NEAR_DOUBLE_DECREASE.values);
                doubles.add(nearDecreaseHistory);
                doubles.sort((x, y) -> y.getDayNumberBefore() - x.getDayNumberBefore());
                return doubles;
            case HEAD_BEGIN:
                return IntStream.range(0, 10)
                        .mapToObj(i -> HistoryHandler.getNumberSetHistoryWithHeadCouple(jackpotList,
                                name + " " + i + " (***) ", NumberArrayHandler.getHeads(i)))
                        .sorted(Comparator.comparingInt(NumberSetHistory::getDayNumberBefore).reversed())
                        .collect(Collectors.toList());
            case TAIL_BEGIN:
                return IntStream.range(0, 10)
                        .mapToObj(i -> HistoryHandler.getNumberSetHistoryWithHeadCouple(jackpotList,
                                name + " " + i + " (***) ", NumberArrayHandler.getTails(i)))
                        .sorted(Comparator.comparingInt(NumberSetHistory::getDayNumberBefore).reversed())
                        .collect(Collectors.toList());
            case SUM_BEGIN:
                return IntStream.range(0, 10)
                        .mapToObj(i -> HistoryHandler.getNumberSetHistoryWithHeadCouple(jackpotList,
                                name + " " + i + " (***) ", NumberArrayHandler.getSums(i)))
                        .sorted(Comparator.comparingInt(NumberSetHistory::getDayNumberBefore).reversed())
                        .collect(Collectors.toList());
            default:
                return new ArrayList<>();
        }
    }
}
