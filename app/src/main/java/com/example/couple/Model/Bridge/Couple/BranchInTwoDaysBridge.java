package com.example.couple.Model.Bridge.Couple;

import com.example.couple.Base.Handler.CoupleBase;
import com.example.couple.Custom.Const.TimeInfo;
import com.example.couple.Model.Bridge.Bridge;
import com.example.couple.Model.Bridge.BridgeType;
import com.example.couple.Model.Origin.Couple;
import com.example.couple.Model.Support.JackpotHistory;
import com.example.couple.Model.Time.Cycle.Branch;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import lombok.Getter;

@Getter
public class BranchInTwoDaysBridge extends Bridge {
    Couple lastCouple;
    boolean isReverseBefore;
    boolean isReverseAfter;
    int runningTimes;
    List<Integer> numbers;
    JackpotHistory jackpotHistory;

    public BranchInTwoDaysBridge(Couple lastCouple, boolean isReverseBefore, boolean isReverseAfter,
                                 int runningTimes, JackpotHistory jackpotHistory) {
        this.lastCouple = lastCouple;
        this.isReverseBefore = isReverseBefore;
        this.isReverseAfter = isReverseAfter;
        this.runningTimes = runningTimes;
        this.numbers = new ArrayList<>();
        this.jackpotHistory = jackpotHistory;
        if (lastCouple.isEmpty()) return;
        int couple = isReverseBefore ? CoupleBase.reverse(lastCouple.getInt()) : lastCouple.getInt();
        List<Integer> tailsOfYear = new ArrayList<>();
        if (isReverseAfter) {
            tailsOfYear.addAll(new Branch(couple).getReverseTailsOfYear(TimeInfo.CURRENT_YEAR));
            if (couple <= TimeInfo.CURRENT_YEAR % 100) {
                tailsOfYear.addAll(new Branch(couple + 4).getReverseTailsOfYear(TimeInfo.CURRENT_YEAR));
            }
        } else {
            tailsOfYear.addAll(new Branch(couple).getTailsOfYear(TimeInfo.CURRENT_YEAR));
            if (couple <= TimeInfo.CURRENT_YEAR % 100) {
                tailsOfYear.addAll(new Branch(couple + 4).getTailsOfYear(TimeInfo.CURRENT_YEAR));
            }
        }
        this.numbers = tailsOfYear.stream().distinct().collect(Collectors.toList());
    }

    @Override
    public String showCompactNumbers() {
        return "";
    }

    @Override
    public String getBridgeName() {
        return BridgeType.BRANCH_IN_TWO_DAYS_BRIDGE.name + " (" + runningTimes + ")";
    }

    public static BranchInTwoDaysBridge getEmpty() {
        return new BranchInTwoDaysBridge(Couple.getEmpty(),
                false, false, 0, JackpotHistory.getEmpty());
    }

    public boolean isEmpty() {
        return lastCouple.isEmpty() || numbers.isEmpty();
    }
}
