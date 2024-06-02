package com.example.couple.Custom.Handler.Bridge;

import com.example.couple.Base.Handler.NumberBase;
import com.example.couple.Model.Bridge.Couple.UnappearedBigDoubleBridge;
import com.example.couple.Model.Set.SpecialSet;
import com.example.couple.Model.Bridge.Sign.DayDoubleSign;
import com.example.couple.Model.Bridge.Sign.SignOfDouble;
import com.example.couple.Model.Display.BSingle;
import com.example.couple.Model.Display.NumberSetHistory;
import com.example.couple.Model.Origin.Jackpot;
import com.example.couple.Model.Support.JackpotHistory;
import com.example.couple.Model.Time.DateBase;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class OtherBridgeHandler {

    public static UnappearedBigDoubleBridge getUnappearedBigDoubleBridge(List<Jackpot> jackpotList,
                                                                         int dayNumberBefore) {
        if (jackpotList.size() < dayNumberBefore) return UnappearedBigDoubleBridge.getEmpty();
        List<Integer> bigDoubleSet = new ArrayList<>(SpecialSet.BIG_DOUBLE.values);
        for (int i = dayNumberBefore; i < jackpotList.size(); i++) {
            if (jackpotList.get(i).getDateBase().isDateLastYear()) {
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
                jackpotList.get(0).getDateBase().addDays(1) : jackpotList.get(dayNumberBefore - 1).getDateBase();
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

            if (dateCheck.isUpLastMonthOf(dateBase) && jackpot.getCouple().isDoubleAndShadow()) {
                upMonthList.add(jackpot.getCoupleInt());
                upMonth2 = dateCheck.getUpLastMonth();
            }
            if (!upMonth2.isEmpty() && dateCheck.equals(upMonth2)) {
                upMonthList.add(jackpot.getCoupleInt());
            }

            if (dateCheck.isLastMonthOf(dateBase) && jackpot.getCouple().isDoubleAndShadow()) {
                monthList.add(jackpot.getCoupleInt());
                month2 = dateCheck.getLastMonth();
            }
            if (!month2.isEmpty() && dateCheck.equals(month2)) {
                monthList.add(jackpot.getCoupleInt());
            }

            if (dateCheck.isDownLastMonthOf(dateBase) && jackpot.getCouple().isDoubleAndShadow()) {
                downMonthList.add(jackpot.getCoupleInt());
                downMonth2 = dateCheck.getDownLastMonth();
            }
            if (!downMonth2.isEmpty() && dateCheck.equals(downMonth2)) {
                downMonthList.add(jackpot.getCoupleInt());
            }

            if (dateCheck.isLastWeekOf(dateBase) && jackpot.getCouple().isDoubleAndShadow()) {
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

        List<DayDoubleSign> reverseDayList = new ArrayList<>();
        for (int i = dayList.size() - 1; i >= 0; i--) {
            reverseDayList.add(dayList.get(i));
        }

        return new SignOfDouble(NumberBase.getReverseList(upMonthList), NumberBase.getReverseList(monthList),
                NumberBase.getReverseList(downMonthList), NumberBase.getReverseList(weekList), reverseDayList);
    }

    public static NumberSetHistory getNumberSetHistory(List<Jackpot> jackpotList,
                                                       String numberSetName, List<Integer> numbers) {
        if (jackpotList.isEmpty()) return NumberSetHistory.getEmpty();
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
        return new NumberSetHistory(numberSetName, numbers, beatList);
    }

    // cầu này để tìm càng thứ 3 dựa trên lịch sử càng giống càng chạy gần đây
    public static List<BSingle> getTouchsByThirdClawBridge(List<Jackpot> jackpotList, int dayNumberBefore) {
        int sizeTest = Math.min(jackpotList.size() - dayNumberBefore, 5);

        List<Integer> nearestThirdClaw = new ArrayList<>();
        for (int i = 0; i < sizeTest; i++) {
            nearestThirdClaw.add(jackpotList.get(dayNumberBefore + i).getThirdClaw());
        }

        int size = jackpotList.size() - sizeTest - dayNumberBefore;

        List<BSingle> BSingleList = new ArrayList<>();
        for (int i = 1; i < size; i++) {
            int count = 0;
            for (int j = 0; j < sizeTest; j++) {
                if (nearestThirdClaw.get(j) == jackpotList.get(dayNumberBefore + i + j).getThirdClaw()) {
                    count++;
                }
            }
            if (count == sizeTest) {
                BSingleList.add(new BSingle(jackpotList.get(dayNumberBefore + i - 1).getThirdClaw(), count));
            }
            if (i == size - 1) {
                sizeTest--;
                if (sizeTest == 1) {
                    break;
                }
                i = 1;
            }
        }

        for (int i = 0; i < BSingleList.size(); i++) {
            for (int j = i + 1; j < BSingleList.size(); j++) {
                if (BSingleList.get(j).getNumber() == BSingleList.get(i).getNumber()) {
                    BSingleList.remove(j);
                }
            }
        }
        return BSingleList;
    }

}
