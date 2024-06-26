package com.example.couple.Custom.Handler.Bridge;

import com.example.couple.Model.Bridge.BridgeType;
import com.example.couple.Model.Bridge.Couple.MappingBridge;
import com.example.couple.Model.Bridge.Couple.TriadMappingBridge;
import com.example.couple.Model.Origin.Couple;
import com.example.couple.Model.Origin.Jackpot;
import com.example.couple.Model.Support.JackpotHistory;
import com.example.couple.Model.DateTime.Date.DateBase;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MappingBridgeHandler {

    public static TriadMappingBridge getAnyMappingBridge(List<Jackpot> jackpotList, int dayNumberBefore) {
        if (jackpotList.size() < dayNumberBefore + 14)
            return TriadMappingBridge.getEmpty();
        Map<Couple, Couple> sequentCoupleMap = getMappingDayCouples(jackpotList, dayNumberBefore);
//        sequentCoupleMap.put(jackpotList.get(dayNumberBefore + 1).getCouple(),
//                jackpotList.get(dayNumberBefore).getCouple());
//        sequentCoupleMap.put(jackpotList.get(dayNumberBefore + 7).getCouple(),
//                jackpotList.get(dayNumberBefore).getCouple());
        Jackpot jackpot = dayNumberBefore == 0 ? Jackpot.getEmpty() : jackpotList.get(dayNumberBefore - 1);
        return new TriadMappingBridge(sequentCoupleMap, new JackpotHistory(dayNumberBefore, jackpot));
    }

    public static Map<Couple, Couple> getMappingDayCouples(List<Jackpot> jackpotList, int dayNumberBefore) {
        if (jackpotList.size() < dayNumberBefore + 14) return new HashMap<>();
        Map<Couple, Couple> sequentCoupleMap = new HashMap<>();
        DateBase dateBase = dayNumberBefore - 1 < 0 ?
                jackpotList.get(dayNumberBefore).getDateBase().addDays(1) :
                jackpotList.get(dayNumberBefore - 1).getDateBase();
        int day = dateBase.getDay();
        int month = dateBase.getMonth();
        int year = dateBase.getYear();
        DateBase upLeft = new DateBase();
        DateBase upupLeftLeft = new DateBase();
        if (day > 2) {
            if (month > 2) {
                upLeft = new DateBase(day - 1, month - 1, year);
                upupLeftLeft = new DateBase(day - 2, month - 2, year);
            } else {
                upLeft = new DateBase(day - 1,
                        month - 1 > 0 ? month - 1 : 12 + month - 1,
                        month - 1 > 0 ? year : year - 1);
                upupLeftLeft = new DateBase(day - 2,
                        month - 2 > 0 ? month - 2 : 12 + month - 2,
                        month - 2 > 0 ? year : year - 1);
            }
        }

        DateBase left = new DateBase(day,
                month - 1 > 0 ? month - 1 : 12 + month - 1,
                month - 1 > 0 ? year : year - 1);
        DateBase leftleft = new DateBase(day,
                month - 2 > 0 ? month - 2 : 12 + month - 2,
                month - 2 > 0 ? year : year - 1);

        DateBase downLeft = new DateBase();
        DateBase downdownLeftLeft = new DateBase();
        if (day < 30) {
            if (month > 2) {
                downLeft = new DateBase(day + 1, month - 1, year);
                downdownLeftLeft = new DateBase(day + 2, month - 2, year);
            } else {
                downLeft = new DateBase(day + 1,
                        month - 1 > 0 ? month - 1 : 12 + month - 1,
                        month - 1 > 0 ? year : year - 1);
                downdownLeftLeft = new DateBase(day + 2,
                        month - 2 > 0 ? month - 2 : 12 + month - 2,
                        month - 2 > 0 ? year : year - 1);
            }
        }

        Couple uullCouple = Couple.getEmpty();
        Couple ulCouple = Couple.getEmpty();
        Couple lCouple = Couple.getEmpty();
        Couple llCouple = Couple.getEmpty();
        Couple dlCouple = Couple.getEmpty();
        Couple ddllCouple = Couple.getEmpty();
        for (Jackpot jackpot : jackpotList) {
            if (jackpot.getDateBase().equals(upupLeftLeft)) {
                uullCouple = jackpot.getCouple();
            }
            if (jackpot.getDateBase().equals(upLeft)) {
                ulCouple = jackpot.getCouple();
            }
            if (jackpot.getDateBase().equals(left)) {
                lCouple = jackpot.getCouple();
            }
            if (jackpot.getDateBase().equals(leftleft)) {
                llCouple = jackpot.getCouple();
            }
            if (jackpot.getDateBase().equals(downLeft)) {
                dlCouple = jackpot.getCouple();
            }
            if (jackpot.getDateBase().equals(downdownLeftLeft)) {
                ddllCouple = jackpot.getCouple();
            }
        }

        if (!uullCouple.isEmpty() && !ulCouple.isEmpty()) {
            sequentCoupleMap.put(uullCouple, ulCouple);
        }

        if (!llCouple.isEmpty() && !lCouple.isEmpty()) {
            sequentCoupleMap.put(llCouple, lCouple);
        }

        if (!ddllCouple.isEmpty() && !dlCouple.isEmpty()) {
            sequentCoupleMap.put(ddllCouple, dlCouple);
        }

        return sequentCoupleMap;
    }

    public static MappingBridge getRightMappingBridge(List<Jackpot> jackpotList, int dayNumberBefore) {
        if (jackpotList.size() < dayNumberBefore + 2)
            return MappingBridge.getEmpty();
        List<Integer> firstList = jackpotList.get(dayNumberBefore + 1)
                .getCouple().getRightMappingNumbers();
        List<Integer> secondList = jackpotList.get(dayNumberBefore)
                .getCouple().getRightMappingNumbers();
        for (int second : secondList) {
            if (!firstList.contains(second)) {
                firstList.add(second);
            }
        }

        Collections.sort(firstList);
        Jackpot jackpot = dayNumberBefore == 0 ?
                Jackpot.getEmpty() : jackpotList.get(dayNumberBefore - 1);
        return new MappingBridge(BridgeType.RIGHT_MAPPING.name, firstList,
                new JackpotHistory(dayNumberBefore, jackpot));
    }

    public static MappingBridge getMappingBridge(List<Jackpot> reverseJackpotList, int dayNumberBefore) {
        if (reverseJackpotList.size() < dayNumberBefore + 2)
            return MappingBridge.getEmpty();
        List<Integer> firstList = reverseJackpotList.get(dayNumberBefore + 1).getCouple().getMappingNumbers();
        List<Integer> secondList = reverseJackpotList.get(dayNumberBefore).getCouple().getMappingNumbers();
        for (int second : secondList) {
            if (!firstList.contains(second)) {
                firstList.add(second);
            }
        }

        Collections.sort(firstList);
        Jackpot jackpot = dayNumberBefore == 0 ?
                Jackpot.getEmpty() : reverseJackpotList.get(dayNumberBefore - 1);
        return new MappingBridge(BridgeType.MAPPING.name, firstList, new JackpotHistory(dayNumberBefore, jackpot));
    }

}
