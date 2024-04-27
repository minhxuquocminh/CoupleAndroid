package com.example.couple.Custom.Handler.Bridge;

import com.example.couple.Base.Handler.CoupleBase;
import com.example.couple.Base.Handler.SingleBase;
import com.example.couple.Custom.Const.Const;
import com.example.couple.Custom.Const.TimeInfo;
import com.example.couple.Model.Bridge.Couple.BranchInTwoDaysBridge;
import com.example.couple.Model.Bridge.Couple.CycleBridge;
import com.example.couple.Model.Bridge.LongBeat.BranchInDayBridge;
import com.example.couple.Model.Display.SpecialSetHistory;
import com.example.couple.Model.Origin.Jackpot;
import com.example.couple.Model.Support.JackpotHistory;
import com.example.couple.Model.Time.Cycle.Branch;
import com.example.couple.Model.Time.Cycle.YearCycle;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class CycleBridgeHandler {

    public static BranchInTwoDaysBridge getBranchInTwoDaysBridge(List<Jackpot> jackpotList, int dayNumberBefore) {
        if (jackpotList.size() < dayNumberBefore) return BranchInTwoDaysBridge.getEmpty();
        List<Boolean> xx = new ArrayList<>();
        List<Boolean> xn = new ArrayList<>();
        List<Boolean> nx = new ArrayList<>();
        List<Boolean> nn = new ArrayList<>();
        boolean xxBigFlag = true;
        boolean xnBigFlag = true;
        boolean nxBigFlag = true;
        boolean nnBigFlag = true;
        for (int i = dayNumberBefore; i < jackpotList.size() - 3; i += 2) {
            int couple2 = jackpotList.get(i + 1).getCoupleInt();
            int couple1 = jackpotList.get(i + 2).getCoupleInt();
            int reCouple2 = CoupleBase.reverse(couple2);
            int reCouple1 = CoupleBase.reverse(couple1);
            boolean xxFlag = Branch.isSameBranch(couple1, couple2, TimeInfo.CURRENT_YEAR);
            boolean xnFlag = Branch.isSameBranch(couple1, reCouple2, TimeInfo.CURRENT_YEAR);
            boolean nxFlag = Branch.isSameBranch(reCouple1, couple2, TimeInfo.CURRENT_YEAR);
            boolean nnFlag = Branch.isSameBranch(reCouple1, reCouple2, TimeInfo.CURRENT_YEAR);
            if (!xxFlag) xxBigFlag = false;
            if (!xnFlag) xnBigFlag = false;
            if (!nxFlag) nxBigFlag = false;
            if (!nnFlag) nnBigFlag = false;
            if (xxBigFlag) xx.add(xxFlag);
            if (xnBigFlag) xn.add(xnFlag);
            if (nxBigFlag) nx.add(nxFlag);
            if (nnBigFlag) nn.add(nnFlag);
            if (i == dayNumberBefore + 10) break;
        }
        // get max
        int max = xx.size();
        if (max < xn.size()) max = xn.size();
        if (max < nx.size()) max = nx.size();
        if (max < nn.size()) max = nn.size();
        if (max < 2) return BranchInTwoDaysBridge.getEmpty();

        boolean isReverseBefore = false;
        boolean isReverseAfter = false;

        if (max == xx.size()) {
            isReverseBefore = false;
            isReverseAfter = false;
        }

        if (max == xn.size()) {
            isReverseBefore = false;
            isReverseAfter = true;
        }

        if (max == nx.size()) {
            isReverseBefore = true;
            isReverseAfter = false;
        }

        if (max == nn.size()) {
            isReverseBefore = true;
            isReverseAfter = true;
        }

        Jackpot jackpot = dayNumberBefore == 0 ? Jackpot.getEmpty() : jackpotList.get(dayNumberBefore - 1);
        return new BranchInTwoDaysBridge(jackpotList.get(dayNumberBefore).getCouple(),
                isReverseBefore, isReverseAfter, max, new JackpotHistory(dayNumberBefore, jackpot));
    }

    public static CycleBridge getCompatibleCycleBridge(List<Jackpot> allJackpotList,
                                                       Branch branchNextDay, int dayNumberBefore) {
        if (allJackpotList.size() < dayNumberBefore) return CycleBridge.getEmpty();
        int positionNextDay = branchNextDay.getPosition();
        int currentPosition = (positionNextDay - dayNumberBefore) > 0 ?
                (positionNextDay - dayNumberBefore) : 12 + (positionNextDay - dayNumberBefore) % 12;
        Branch currentBranch = new Branch(currentPosition);
        List<YearCycle> yearCycles = currentBranch.getCompatibleYearCycles(TimeInfo.CURRENT_YEAR);
        Jackpot jackpot = dayNumberBefore == 0 ? Jackpot.getEmpty() : allJackpotList.get(dayNumberBefore - 1);
        return new CycleBridge(Const.COMPATIBLE_CYCLE_BRIDGE_NAME,
                yearCycles, new JackpotHistory(dayNumberBefore, jackpot));
    }

    public static CycleBridge getIncompatibleCycleBridge(List<Jackpot> allJackpotList,
                                                         Branch branchNextDay, int dayNumberBefore) {
        if (allJackpotList.size() < dayNumberBefore) return CycleBridge.getEmpty();
        int positionNextDay = branchNextDay.getPosition();
        int currentPosition = (positionNextDay - dayNumberBefore) > 0 ?
                (positionNextDay - dayNumberBefore) : 12 + (positionNextDay - dayNumberBefore) % 12;
        Branch currentBranch = new Branch(currentPosition);
        List<YearCycle> yearCycles = currentBranch.getIncompatibleYearCycles(TimeInfo.CURRENT_YEAR);
        Jackpot jackpot = dayNumberBefore == 0 ? Jackpot.getEmpty() : allJackpotList.get(dayNumberBefore - 1);
        return new CycleBridge(Const.INCOMPATIBLE_CYCLE_BRIDGE_NAME,
                yearCycles, new JackpotHistory(dayNumberBefore, jackpot));
    }

    public static BranchInDayBridge getBranchInDayBridges(List<Jackpot> allJackpotList, Branch nextDayBranch) {
        if (allJackpotList.isEmpty()) return BranchInDayBridge.getEmpty();
        List<Integer> beatList = new ArrayList<>();
        int beat = 0;
        int count = 0;
        for (Jackpot jackpot : allJackpotList) {
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

    public static SpecialSetHistory getBranchDistanceHistory(List<Jackpot> jackpotList,
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
        return new SpecialSetHistory(specialSetName, nextDayBranch.plusDays(distance).getTailsOfYear(), beatList);
    }

    public static SpecialSetHistory getBranchDistanceHistoryEach2Days(List<Jackpot> jackpotList,
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
        return new SpecialSetHistory(specialSetName, new Branch(validBranch).getTailsOfYear(), beatList);
    }

    public static SpecialSetHistory getPositive12BranchHistory(List<Jackpot> jackpotList, String specialSetName) {
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
        return new SpecialSetHistory(specialSetName, new Branch(nextPosition).getTailsOfYear(), beatList);
    }

    public static SpecialSetHistory getNegative12BranchHistory(List<Jackpot> jackpotList, String specialSetName) {
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
        return new SpecialSetHistory(specialSetName, new Branch(nextPosition).getTailsOfYear(), beatList);
    }

    public static SpecialSetHistory getPositive13BranchHistory(List<Jackpot> jackpotList, String specialSetName) {
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
        return new SpecialSetHistory(specialSetName, new Branch(nextPosition).getTailsOfYear(), beatList);
    }

    public static SpecialSetHistory getNegative13BranchHistory(List<Jackpot> jackpotList, String specialSetName) {
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
        return new SpecialSetHistory(specialSetName, new Branch(nextPosition).getTailsOfYear(), beatList);
    }

}
