package com.example.couple.Custom.Handler.Bridge;

import com.example.couple.Base.Handler.SingleBase;
import com.example.couple.Custom.Const.Const;
import com.example.couple.Custom.Const.TimeInfo;
import com.example.couple.Model.Bridge.Couple.CycleBridge;
import com.example.couple.Model.Bridge.LongBeat.BranchInDayBridge;
import com.example.couple.Model.Display.SpecialSetHistory;
import com.example.couple.Model.Origin.Jackpot;
import com.example.couple.Model.Support.JackpotHistory;
import com.example.couple.Model.Time.Cycle.Branch;
import com.example.couple.Model.Time.Cycle.YearCycle;
import com.example.couple.Model.Time.TimeBase;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class CycleBridgeHandler {

    public static CycleBridge GetCompatibleCycleBridge(List<Jackpot> jackpotList,
                                                       TimeBase timeBaseNextDay, int dayNumberBefore) {
        if (jackpotList.size() < dayNumberBefore || timeBaseNextDay.isEmpty())
            return CycleBridge.getEmpty();
        int positionNextDay = timeBaseNextDay.getDateCycle().getDay().getBranch().getPosition();
        int currentPosition = (positionNextDay - dayNumberBefore) > 0 ?
                (positionNextDay - dayNumberBefore) : 12 + (positionNextDay - dayNumberBefore) % 12;
        Branch currentBranch = new Branch(currentPosition);
        List<YearCycle> yearCycles = currentBranch.getCompatibleYearCycles(TimeInfo.CURRENT_YEAR);
        Jackpot jackpot = dayNumberBefore == 0 ? Jackpot.getEmpty() : jackpotList.get(dayNumberBefore - 1);
        return new CycleBridge(Const.COMPATIBLE_CYCLE_BRIDGE_NAME,
                yearCycles, new JackpotHistory(dayNumberBefore, jackpot));
    }

    public static CycleBridge GetIncompatibleCycleBridge(List<Jackpot> jackpotList,
                                                         TimeBase timeBaseNextDay, int dayNumberBefore) {
        if (jackpotList.size() < dayNumberBefore || timeBaseNextDay.isEmpty())
            return CycleBridge.getEmpty();
        int positionNextDay = timeBaseNextDay.getDateCycle().getDay().getBranch().getPosition();
        int currentPosition = (positionNextDay - dayNumberBefore) > 0 ?
                (positionNextDay - dayNumberBefore) : 12 + (positionNextDay - dayNumberBefore) % 12;
        Branch currentBranch = new Branch(currentPosition);
        List<YearCycle> yearCycles = currentBranch.getIncompatibleYearCycles(TimeInfo.CURRENT_YEAR);
        Jackpot jackpot = dayNumberBefore == 0 ? Jackpot.getEmpty() : jackpotList.get(dayNumberBefore - 1);
        return new CycleBridge(Const.INCOMPATIBLE_CYCLE_BRIDGE_NAME,
                yearCycles, new JackpotHistory(dayNumberBefore, jackpot));
    }

    public static BranchInDayBridge GetBranchInDayBridges(List<Jackpot> jackpotList, Branch nextDayBranch) {
        if (jackpotList.isEmpty()) return BranchInDayBridge.getEmpty();
        List<Integer> beatList = new ArrayList<>();
        int beat = 0;
        int count = 0;
        for (Jackpot jackpot : jackpotList) {
            beat++;
            count++;
            Branch dayBranch = nextDayBranch.plusDays(-count);
            if (dayBranch.isYearBranch(jackpot.getCoupleInt(), TimeInfo.CURRENT_YEAR)) {
                beatList.add(beat);
                beat = 0;
            }
        }
        Collections.reverse(beatList);
        return new BranchInDayBridge(nextDayBranch, beatList);
    }

    public static SpecialSetHistory GetBranchDistanceHistory(List<Jackpot> jackpotList,
                                                             String specialSetName,
                                                             int distance, Branch nextDayBranch) {
        if (jackpotList.isEmpty()) return new SpecialSetHistory();
        List<Integer> beatList = new ArrayList<>();
        int beat = 0;
        int count = 0;
        for (Jackpot jackpot : jackpotList) {
            beat++;
            count++;
            Branch jackpotBranch = new Branch(jackpot.getCoupleInt() % 12);
            Branch dayBranch = nextDayBranch.plusDays(-count);
            if (dayBranch.getDistance(jackpotBranch) == distance) {
                beatList.add(beat);
                beat = 0;
            }
        }
        Collections.reverse(beatList);
        return new SpecialSetHistory(specialSetName, nextDayBranch.plusDays(distance).getIntYears(), beatList);
    }

    public static SpecialSetHistory GetBranchDistanceHistoryEach2Days(List<Jackpot> jackpotList,
                                                                      String specialSetName,
                                                                      int distance) {
        if (jackpotList.isEmpty()) return new SpecialSetHistory();
        List<Integer> beatList = new ArrayList<>();
        int beat = 0;
        for (int i = 0; i < jackpotList.size() - 1; i++) {
            beat++;
            int branchPosition = new Branch(jackpotList.get(i).getCoupleInt()).getPosition();
            int branchPosition1 = new Branch(jackpotList.get(i + 1).getCoupleInt()).getPosition();
            if (branchPosition - branchPosition1 == distance) {
                beatList.add(beat);
                beat = 0;
            }
        }
        Collections.reverse(beatList);
        int nextBranchPosition = new Branch(jackpotList.get(0).getCoupleInt()).getPosition() + distance;
        int validBranch = nextBranchPosition < 0 ? 12 + nextBranchPosition : nextBranchPosition;
        return new SpecialSetHistory(specialSetName, new Branch(validBranch).getIntYears(), beatList);
    }

    public static SpecialSetHistory GetPositive12BranchHistory(List<Jackpot> jackpotList,
                                                               String specialSetName) {
        if (jackpotList.isEmpty()) return new SpecialSetHistory();
        List<Integer> beatList = new ArrayList<>();
        int beat = 0;
        for (int i = 0; i < jackpotList.size() - 1; i++) {
            beat++;
            int branchPosition = new Branch(jackpotList.get(i).getCoupleInt()).getPosition();
            int branchPosition1 = new Branch(jackpotList.get(i + 1).getCoupleInt()).getPosition();
            if (SingleBase.getShadow(branchPosition % 10) == branchPosition1 % 10) {
                beatList.add(beat);
                beat = 0;
            }
        }
        Collections.reverse(beatList);
        int lastPosition = new Branch(jackpotList.get(0).getCoupleInt()).getPosition();
        int nextPosition = SingleBase.getShadow(lastPosition % 10);
        return new SpecialSetHistory(specialSetName, new Branch(nextPosition).getIntYears(), beatList);
    }

    public static SpecialSetHistory GetNegative12BranchHistory(List<Jackpot> jackpotList,
                                                               String specialSetName) {
        if (jackpotList.isEmpty()) return new SpecialSetHistory();
        List<Integer> beatList = new ArrayList<>();
        int beat = 0;
        for (int i = 0; i < jackpotList.size() - 1; i++) {
            beat++;
            int branchPosition = new Branch(jackpotList.get(i).getCoupleInt()).getPosition();
            int branchPosition1 = new Branch(jackpotList.get(i + 1).getCoupleInt()).getPosition();
            if (SingleBase.getNegativeShadow(branchPosition % 10) == branchPosition1 % 10) {
                beatList.add(beat);
                beat = 0;
            }
        }
        Collections.reverse(beatList);
        int lastPosition = new Branch(jackpotList.get(0).getCoupleInt()).getPosition();
        int nextPosition = SingleBase.getNegativeShadow(lastPosition % 10);
        return new SpecialSetHistory(specialSetName, new Branch(nextPosition).getIntYears(), beatList);
    }

    public static SpecialSetHistory GetPositive13BranchHistory(List<Jackpot> jackpotList, String specialSetName) {
        if (jackpotList.isEmpty()) return new SpecialSetHistory();
        List<Integer> beatList = new ArrayList<>();
        int beat = 0;
        for (int i = 0; i < jackpotList.size() - 2; i++) {
            beat++;
            int branchPosition = new Branch(jackpotList.get(i).getCoupleInt()).getPosition();
            int branchPosition1 = new Branch(jackpotList.get(i + 2).getCoupleInt()).getPosition();
            if (SingleBase.getShadow(branchPosition % 10) == branchPosition1 % 10) {
                beatList.add(beat);
                beat = 0;
            }
        }
        Collections.reverse(beatList);
        int lastPosition = new Branch(jackpotList.get(0).getCoupleInt()).getPosition();
        int nextPosition = SingleBase.getShadow(lastPosition % 10);
        return new SpecialSetHistory(specialSetName, new Branch(nextPosition).getIntYears(), beatList);
    }

    public static SpecialSetHistory GetNegative13BranchHistory(List<Jackpot> jackpotList, String specialSetName) {
        if (jackpotList.isEmpty()) return new SpecialSetHistory();
        List<Integer> beatList = new ArrayList<>();
        int beat = 0;
        for (int i = 0; i < jackpotList.size() - 2; i++) {
            beat++;
            int branchPosition = new Branch(jackpotList.get(i).getCoupleInt()).getPosition();
            int branchPosition1 = new Branch(jackpotList.get(i + 2).getCoupleInt()).getPosition();
            if (SingleBase.getNegativeShadow(branchPosition % 10) == branchPosition1 % 10) {
                beatList.add(beat);
                beat = 0;
            }
        }
        Collections.reverse(beatList);
        int lastPosition = new Branch(jackpotList.get(0).getCoupleInt()).getPosition();
        int nextPosition = SingleBase.getNegativeShadow(lastPosition % 10);
        return new SpecialSetHistory(specialSetName, new Branch(nextPosition).getIntYears(), beatList);
    }

}
