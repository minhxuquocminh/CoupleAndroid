package com.example.couple.Custom.Handler.Bridge;

import com.example.couple.Custom.Const.Const;
import com.example.couple.Model.Bridge.Estimated.EstimatedBridge;
import com.example.couple.Model.Bridge.Estimated.DualStatus;
import com.example.couple.Model.Origin.Jackpot;
import com.example.couple.Model.Bridge.JackpotHistory;
import com.example.couple.Model.Bridge.Estimated.PeriodHistory;
import com.example.couple.Model.DateTime.Date.DateBase;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class EstimatedBridgeHandler {

    public static EstimatedBridge getEstimatedBridge(List<Jackpot> jackpotList, int dayNumberBefore) {
        List<PeriodHistory> periodHistories3 = getPeriodHistoryList(jackpotList,
                dayNumberBefore, 3, Const.AMPLITUDE_OF_PERIOD);
        List<PeriodHistory> periodHistories4 = getPeriodHistoryList(jackpotList,
                dayNumberBefore, 4, Const.AMPLITUDE_OF_PERIOD);
        Jackpot jackpot = dayNumberBefore == 0 ?
                Jackpot.getEmpty() : jackpotList.get(dayNumberBefore - 1);
        return new EstimatedBridge(periodHistories3, periodHistories4,
                new JackpotHistory(dayNumberBefore, jackpot));
    }

    public static List<PeriodHistory> getPeriodHistoryList(List<Jackpot> jackpotList,
                                                           int dayNumberBefore, int periodNumber, int range) {
        if (jackpotList.size() < dayNumberBefore + periodNumber) return new ArrayList<>();
        List<Integer> lastNumbers = new ArrayList<>();
        for (int i = dayNumberBefore; i < dayNumberBefore + periodNumber; i++) {
            lastNumbers.add(jackpotList.get(i).getCoupleInt());
        }

        List<PeriodHistory> periodHistoryList = new ArrayList<>();
        for (int i = dayNumberBefore + periodNumber; i < jackpotList.size() - periodNumber; i++) {
            int count = 0;
            List<Integer> numbers = new ArrayList<>();
            for (int j = 0; j < periodNumber; j++) {
                int coupleCheck = jackpotList.get(i + j).getCoupleInt();
                if (isInPeriod(lastNumbers.get(j), coupleCheck, range)) {
                    count++;
                    numbers.add(coupleCheck);
                }
            }
            if (count == periodNumber) {
                Collections.reverse(numbers);
                DateBase start = jackpotList.get(i + periodNumber).getDateBase();
                DateBase end = jackpotList.get(i).getDateBase();
                if (i - 1 < jackpotList.size() - 1) {
                    numbers.add(jackpotList.get(i - 1).getCoupleInt());
                    end = jackpotList.get(i - 1).getDateBase();
                }
                periodHistoryList.add(new PeriodHistory(start, end, numbers));
            }
        }

        Collections.reverse(periodHistoryList);

        return periodHistoryList;
    }

    public static List<PeriodHistory> getEstimatedHistoryList(List<Jackpot> jackpotList,
                                                              int dayNumberBefore, int periodNumber, int range) {
        if (jackpotList.size() < dayNumberBefore + periodNumber) return new ArrayList<>();
        List<Integer> lastNumbers = new ArrayList<>();
        for (int i = dayNumberBefore; i < dayNumberBefore + periodNumber; i++) {
            lastNumbers.add(jackpotList.get(i).getCoupleInt());
        }
        List<DualStatus> statusList = getStatusList(lastNumbers);

        List<PeriodHistory> periodHistoryList = new ArrayList<>();
        for (int i = dayNumberBefore + periodNumber; i < jackpotList.size() - periodNumber; i++) {
            int count = 0;
            List<Integer> numbers = new ArrayList<>();
            for (int j = 0; j < periodNumber; j++) {
                int coupleCheck = jackpotList.get(i + j).getCoupleInt();
                if (isInPeriod(lastNumbers.get(j), coupleCheck, range)) {
                    count++;
                    numbers.add(coupleCheck);
                }
            }
            if (count == periodNumber) {
                boolean checkStatus = compareStatusList(statusList, getStatusList(numbers));
                if (!checkStatus) continue;
                Collections.reverse(numbers);
                DateBase start = jackpotList.get(i + periodNumber).getDateBase();
                DateBase end = jackpotList.get(i).getDateBase();
                if (i - 1 < jackpotList.size() - 1) {
                    numbers.add(jackpotList.get(i - 1).getCoupleInt());
                    end = jackpotList.get(i - 1).getDateBase();
                }
                periodHistoryList.add(new PeriodHistory(start, end, numbers));
            }
        }

        Collections.reverse(periodHistoryList);

        return periodHistoryList;
    }

    private static boolean isInPeriod(int number, int numberCheck, int range) {
        if (number < 0 || number > 99) return false;
        if (number <= range) return numberCheck >= 0 && numberCheck <= number + range;
        if (number >= 99 - range) return numberCheck <= 99 && numberCheck >= number - range;
        return numberCheck >= number - range && numberCheck <= number + range;
    }

    private static List<DualStatus> getStatusList(List<Integer> coupleList) {
        List<DualStatus> statusList = new ArrayList<>();
        for (int i = 0; i < coupleList.size() - 1; i++) {
            int firstCouple = coupleList.get(i);
            int secondCouple = coupleList.get(i + 1);
            int headSub = firstCouple / 10 - secondCouple / 10;
            int tailSub = firstCouple % 10 - secondCouple % 10;
            int firstStatus = headSub == 0 ? 0 : headSub / Math.abs(headSub);
            int secondStatus = tailSub == 0 ? 0 : tailSub / Math.abs(tailSub);
            statusList.add(new DualStatus(firstStatus, secondStatus));
        }
        return statusList;
    }

    private static boolean compareStatusList(List<DualStatus> firstList, List<DualStatus> secondList) {
        if (firstList.size() != secondList.size()) return false;
        for (int i = 0; i < firstList.size(); i++) {
            if (!firstList.get(i).equals(secondList.get(i))) {
                return false;
            }
        }
        return true;
    }

}
