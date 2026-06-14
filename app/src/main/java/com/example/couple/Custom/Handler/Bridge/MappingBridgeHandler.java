package com.example.couple.Custom.Handler.Bridge;

import com.example.couple.Model.Bridge.BridgeType;
import com.example.couple.Model.Bridge.JackpotHistory;
import com.example.couple.Model.Bridge.Mapping.MappingBridge;
import com.example.couple.Model.Bridge.Mapping.MappingCouplePair;
import com.example.couple.Model.DateTime.Date.DateBase;
import com.example.couple.Model.Origin.Couple;
import com.example.couple.Model.Origin.Jackpot;

import java.util.ArrayList;
import java.util.List;

public class MappingBridgeHandler {

    public static MappingBridge getCombinedMappingBridge(List<Jackpot> jackpotList, int dayNumberBefore) {
        if (jackpotList.size() < dayNumberBefore + 14)
            return MappingBridge.getEmpty();
        List<MappingCouplePair> mappingCouplePairs = getMonthMappingCouplePairs(jackpotList, dayNumberBefore);
        mappingCouplePairs.addAll(getWeekMappingCouplePairs(jackpotList, dayNumberBefore));
        mappingCouplePairs.addAll(getDayMappingCouplePairs(jackpotList, dayNumberBefore));
        Jackpot jackpot = dayNumberBefore == 0 ? Jackpot.getEmpty() : jackpotList.get(dayNumberBefore - 1);
        return new MappingBridge(BridgeType.COMBINED_MAPPING, mappingCouplePairs,
                new JackpotHistory(dayNumberBefore, jackpot));
    }

    public static MappingBridge getDayMappingBridge(List<Jackpot> reverseJackpotList, int dayNumberBefore) {
        if (reverseJackpotList.size() < dayNumberBefore + 2) return MappingBridge.getEmpty();
        List<MappingCouplePair> mappingCouplePairs = getDayMappingCouplePairs(reverseJackpotList, dayNumberBefore);
        Jackpot jackpot = dayNumberBefore == 0 ? Jackpot.getEmpty() : reverseJackpotList.get(dayNumberBefore - 1);
        return new MappingBridge(BridgeType.DAY_MAPPING, mappingCouplePairs,
                new JackpotHistory(dayNumberBefore, jackpot));
    }

    public static MappingBridge getDayRightMappingBridge(List<Jackpot> jackpotList, int dayNumberBefore) {
        if (jackpotList.size() < dayNumberBefore + 2) return MappingBridge.getEmpty();
        List<MappingCouplePair> mappingCouplePairs = getDayMappingCouplePairs(jackpotList, dayNumberBefore);
        Jackpot jackpot = dayNumberBefore == 0 ? Jackpot.getEmpty() : jackpotList.get(dayNumberBefore - 1);
        return MappingBridge.getRightMappingBridge(BridgeType.DAY_RIGHT_MAPPING, mappingCouplePairs,
                new JackpotHistory(dayNumberBefore, jackpot));
    }

    public static MappingBridge getWeekMappingBridge(List<Jackpot> jackpotList, int dayNumberBefore) {
        if (jackpotList.size() < dayNumberBefore + 17) return MappingBridge.getEmpty();
        List<MappingCouplePair> mappingCouplePairs = getWeekMappingCouplePairs(jackpotList, dayNumberBefore);
        Jackpot jackpot = dayNumberBefore == 0 ? Jackpot.getEmpty() : jackpotList.get(dayNumberBefore - 1);
        return new MappingBridge(BridgeType.WEEK_MAPPING, mappingCouplePairs,
                new JackpotHistory(dayNumberBefore, jackpot));
    }

    public static MappingBridge getMonthMappingBridge(List<Jackpot> jackpotList, int dayNumberBefore) {
        if (jackpotList.size() < dayNumberBefore + 14) return MappingBridge.getEmpty();
        List<MappingCouplePair> mappingCouplePairs = getMonthMappingCouplePairs(jackpotList, dayNumberBefore);
        Jackpot jackpot = dayNumberBefore == 0 ? Jackpot.getEmpty() : jackpotList.get(dayNumberBefore - 1);
        return new MappingBridge(BridgeType.MONTH_MAPPING, mappingCouplePairs,
                new JackpotHistory(dayNumberBefore, jackpot));
    }

    public static List<MappingCouplePair> getDayMappingCouplePairs(List<Jackpot> jackpotList, int dayNumberBefore) {
        if (jackpotList.size() < dayNumberBefore + 2) return new ArrayList<>();
        List<MappingCouplePair> mappingCouplePairs = new ArrayList<>();
        Couple firstCouple = jackpotList.get(dayNumberBefore + 1).getCouple();
        Couple secondCouple = jackpotList.get(dayNumberBefore).getCouple();
        if (!firstCouple.isDayOff() && !secondCouple.isDayOff()) {
            mappingCouplePairs.add(new MappingCouplePair(firstCouple, secondCouple));
        }
        return mappingCouplePairs;
    }

    public static List<MappingCouplePair> getWeekMappingCouplePairs(List<Jackpot> jackpotList, int dayNumberBefore) {
        if (jackpotList.size() < dayNumberBefore + 17) return new ArrayList<>();
        List<MappingCouplePair> mappingCouplePairs = new ArrayList<>();
        DateBase dateBase = dayNumberBefore - 1 < 0 ?
                jackpotList.get(dayNumberBefore).getDateBase().addDays(1) :
                jackpotList.get(dayNumberBefore - 1).getDateBase();

        DateBase upLastWeek = dateBase.addDays(-8);
        DateBase upTwoWeeksBefore = dateBase.addDays(-16);
        DateBase lastWeek = dateBase.addDays(-7);
        DateBase twoWeeksBefore = dateBase.addDays(-14);
        DateBase downLastWeek = dateBase.addDays(-6);
        DateBase downTwoWeeksBefore = dateBase.addDays(-12);

        Couple upLastWeekCouple = Couple.getEmpty();
        Couple upTwoWeeksBeforeCouple = Couple.getEmpty();
        Couple lastWeekCouple = Couple.getEmpty();
        Couple twoWeeksBeforeCouple = Couple.getEmpty();
        Couple downLastWeekCouple = Couple.getEmpty();
        Couple downTwoWeeksBeforeCouple = Couple.getEmpty();
        for (Jackpot jackpot : jackpotList) {
            if (jackpot.getDateBase().equals(upLastWeek)) {
                upLastWeekCouple = jackpot.getCouple();
            }
            if (jackpot.getDateBase().equals(upTwoWeeksBefore)) {
                upTwoWeeksBeforeCouple = jackpot.getCouple();
            }
            if (jackpot.getDateBase().equals(lastWeek)) {
                lastWeekCouple = jackpot.getCouple();
            }
            if (jackpot.getDateBase().equals(twoWeeksBefore)) {
                twoWeeksBeforeCouple = jackpot.getCouple();
            }
            if (jackpot.getDateBase().equals(downLastWeek)) {
                downLastWeekCouple = jackpot.getCouple();
            }
            if (jackpot.getDateBase().equals(downTwoWeeksBefore)) {
                downTwoWeeksBeforeCouple = jackpot.getCouple();
            }
        }

        if (!upTwoWeeksBeforeCouple.isDayOff() && !upLastWeekCouple.isDayOff()) {
            mappingCouplePairs.add(new MappingCouplePair(upTwoWeeksBeforeCouple, upLastWeekCouple));
        }

        if (!twoWeeksBeforeCouple.isDayOff() && !lastWeekCouple.isDayOff()) {
            mappingCouplePairs.add(new MappingCouplePair(twoWeeksBeforeCouple, lastWeekCouple));
        }

        if (!downTwoWeeksBeforeCouple.isDayOff() && !downLastWeekCouple.isDayOff()) {
            mappingCouplePairs.add(new MappingCouplePair(downTwoWeeksBeforeCouple, downLastWeekCouple));
        }

        return mappingCouplePairs;
    }

    public static List<MappingCouplePair> getMonthMappingCouplePairs(List<Jackpot> jackpotList, int dayNumberBefore) {
        if (jackpotList.size() < dayNumberBefore + 14) return new ArrayList<>();
        List<MappingCouplePair> mappingCouplePairs = new ArrayList<>();
        DateBase dateBase = dayNumberBefore - 1 < 0 ?
                jackpotList.get(dayNumberBefore).getDateBase().addDays(1) :
                jackpotList.get(dayNumberBefore - 1).getDateBase();
        int day = dateBase.getDay();
        int month = dateBase.getMonth();
        int year = dateBase.getYear();
        DateBase upLeft = DateBase.getEmpty();
        DateBase upupLeftLeft = DateBase.getEmpty();
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

        DateBase downLeft = DateBase.getEmpty();
        DateBase downdownLeftLeft = DateBase.getEmpty();
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

        if (!uullCouple.isDayOff() && !ulCouple.isDayOff()) {
            mappingCouplePairs.add(new MappingCouplePair(uullCouple, ulCouple));
        }

        if (!llCouple.isDayOff() && !lCouple.isDayOff()) {
            mappingCouplePairs.add(new MappingCouplePair(llCouple, lCouple));
        }

        if (!ddllCouple.isDayOff() && !dlCouple.isDayOff()) {
            mappingCouplePairs.add(new MappingCouplePair(ddllCouple, dlCouple));
        }

        return mappingCouplePairs;
    }

}
