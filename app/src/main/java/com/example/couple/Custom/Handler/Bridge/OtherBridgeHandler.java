package com.example.couple.Custom.Handler.Bridge;

import com.example.couple.Model.Bridge.Double.UnappearedBigDoubleBridge;
import com.example.couple.Model.Bridge.Estimated.EstimatedBridge;
import com.example.couple.Model.Bridge.Mapping.MappingBridge;
import com.example.couple.Model.Bridge.Double.DayDoubleSign;
import com.example.couple.Model.Bridge.Double.SignOfDouble;
import com.example.couple.Model.Bridge.SyntheticBridge;
import com.example.couple.Model.Bridge.Touch.CombineTouchBridge;
import com.example.couple.Model.DateTime.Date.DateBase;
import com.example.couple.Model.Origin.Jackpot;
import com.example.couple.Model.Origin.Lottery;
import com.example.couple.Model.Bridge.NumberSet.SpecialSet;
import com.example.couple.Model.Bridge.JackpotHistory;
import com.example.couple.Model.Bridge.TriadClaw.Single;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class OtherBridgeHandler {

    public static SyntheticBridge getSyntheticBridge(List<Jackpot> jackpotList, List<Lottery> lotteries,
                                                     int dayNumberBefore) {
        if (jackpotList.size() < dayNumberBefore + 2) return SyntheticBridge.getEmpty();
        Jackpot jackpot = dayNumberBefore == 0 ? Jackpot.getEmpty() : jackpotList.get(dayNumberBefore - 1);
        List<Jackpot> lastTwoJackpot = new ArrayList<>();
        lastTwoJackpot.add(jackpotList.get(dayNumberBefore));
        lastTwoJackpot.add(jackpotList.get(dayNumberBefore + 1));
        CombineTouchBridge combineTouchBridge = TouchBridgeHandler.getCombineTouchBridge(jackpotList, lotteries, dayNumberBefore);
        MappingBridge mappingBridge = MappingBridgeHandler.getMappingBridge(jackpotList, dayNumberBefore);
        EstimatedBridge estimatedBridge = EstimatedBridgeHandler.getEstimatedBridge(jackpotList, dayNumberBefore);
        return new SyntheticBridge(lastTwoJackpot, combineTouchBridge, mappingBridge, estimatedBridge,
                new JackpotHistory(dayNumberBefore, jackpot));
    }

    public static UnappearedBigDoubleBridge getUnappearedBigDoubleBridge(List<Jackpot> jackpotList,
                                                                         int dayNumberBefore) {
        if (jackpotList.size() < dayNumberBefore) return UnappearedBigDoubleBridge.getEmpty();
        List<Integer> bigDoubleSet = new ArrayList<>(SpecialSet.BIG_DOUBLE.values);
        for (int i = dayNumberBefore; i < jackpotList.size(); i++) {
            if (jackpotList.get(i).getDateBase().isLastYear()) {
                break;
            }

            Integer couple = jackpotList.get(i).getCoupleInt();
            bigDoubleSet.remove(couple);
        }
        Jackpot jackpot = dayNumberBefore == 0 ? Jackpot.getEmpty() : jackpotList.get(dayNumberBefore - 1);
        return new UnappearedBigDoubleBridge(bigDoubleSet, new JackpotHistory(dayNumberBefore, jackpot));
    }

    public static SignOfDouble getSignOfDouble(List<Jackpot> jackpotList, int dayNumberBefore) {
        if (jackpotList.size() < dayNumberBefore) return SignOfDouble.getEmpty();
        DateBase dateBase = dayNumberBefore == 0 ?
                jackpotList.get(0).getDateBase().addDays(1) :
                jackpotList.get(dayNumberBefore - 1).getDateBase();
        List<Integer> upMonthList = new ArrayList<>();
        List<Integer> monthList = new ArrayList<>();
        List<Integer> downMonthList = new ArrayList<>();
        List<Integer> weekList = new ArrayList<>();
        List<DayDoubleSign> dayList = new ArrayList<>();
        DateBase upMonth2 = DateBase.getEmpty();
        DateBase month2 = DateBase.getEmpty();
        DateBase downMonth2 = DateBase.getEmpty();
        DateBase week2 = DateBase.getEmpty();
        int count = 0;
        boolean hasDouble = false;
        for (Jackpot jackpot : jackpotList) {
            DateBase dateCheck = jackpot.getDateBase();

            if (dateCheck.isUpLastMonthOf(dateBase) && jackpot.getCouple().isDoubleOrShadow()) {
                upMonthList.add(jackpot.getCoupleInt());
                upMonth2 = dateCheck.getUpLastMonth();
            }
            if (!upMonth2.isEmpty() && dateCheck.equals(upMonth2)) {
                upMonthList.add(jackpot.getCoupleInt());
            }

            if (dateCheck.isLastMonthOf(dateBase) && jackpot.getCouple().isDoubleOrShadow()) {
                monthList.add(jackpot.getCoupleInt());
                month2 = dateCheck.getLastMonth();
            }
            if (!month2.isEmpty() && dateCheck.equals(month2)) {
                monthList.add(jackpot.getCoupleInt());
            }

            if (dateCheck.isDownLastMonthOf(dateBase) && jackpot.getCouple().isDoubleOrShadow()) {
                downMonthList.add(jackpot.getCoupleInt());
                downMonth2 = dateCheck.getDownLastMonth();
            }
            if (!downMonth2.isEmpty() && dateCheck.equals(downMonth2)) {
                downMonthList.add(jackpot.getCoupleInt());
            }

            if (dateCheck.isLastWeekOf(dateBase) && jackpot.getCouple().isDoubleOrShadow()) {
                weekList.add(jackpot.getCoupleInt());
                week2 = dateCheck.getLastWeek();
            }
            if (!week2.isEmpty() && dateCheck.equals(week2)) {
                weekList.add(jackpot.getCoupleInt());
            }

            // check double
            if (jackpot.getCouple().isDouble()) {
                hasDouble = true;
            }
            count++;
            if (!hasDouble && jackpot.isSameSequentlySign()) {
                dayList.add(new DayDoubleSign(jackpot, count));
            }

            if (dateBase.addDays(-70).equals(dateCheck)) break;
        }

        Collections.reverse(upMonthList);
        Collections.reverse(downMonthList);
        Collections.reverse(weekList);
        Collections.reverse(dayList);
        return new SignOfDouble(upMonthList, monthList, downMonthList, weekList, dayList);
    }

    // cầu này để tìm càng thứ 3 dựa trên lịch sử càng giống càng chạy gần đây
    public static List<Single> getTouchsByThirdClawBridge(List<Jackpot> jackpotList, int dayNumberBefore) {
        if (jackpotList.size() < dayNumberBefore) return new ArrayList<>();
        int sizeTest = Math.min(jackpotList.size() - dayNumberBefore, 5);

        List<Integer> nearestThirdClaw = new ArrayList<>();
        for (int i = 0; i < sizeTest; i++) {
            nearestThirdClaw.add(jackpotList.get(dayNumberBefore + i).getThirdClaw());
        }

        int size = jackpotList.size() - sizeTest - dayNumberBefore;

        List<Single> singles = new ArrayList<>();
        for (int i = 1; i < size; i++) {
            int count = 0;
            for (int j = 0; j < sizeTest; j++) {
                if (nearestThirdClaw.get(j) == jackpotList.get(dayNumberBefore + i + j).getThirdClaw()) {
                    count++;
                }
            }
            if (count == sizeTest) {
                singles.add(new Single(jackpotList.get(dayNumberBefore + i - 1).getThirdClaw(), count));
            }
            if (i == size - 1) {
                sizeTest--;
                if (sizeTest == 1) {
                    break;
                }
                i = 1;
            }
        }

        for (int i = 0; i < singles.size(); i++) {
            for (int j = i + 1; j < singles.size(); j++) {
                if (singles.get(j).getNumber() == singles.get(i).getNumber()) {
                    singles.remove(j);
                }
            }
        }
        return singles;
    }

}
