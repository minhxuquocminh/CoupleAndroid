package com.example.couple.Custom.Handler;

import com.example.couple.Model.Time.DateBase;
import com.example.couple.Base.Handler.NumberBase;
import com.example.couple.Custom.Const.Const;
import com.example.couple.Custom.Const.TimeInfo;
import com.example.couple.Model.Bridge.Couple.CycleBridge;
import com.example.couple.Model.Bridge.Couple.MappingBridge;
import com.example.couple.Model.Bridge.Couple.PeriodBridge;
import com.example.couple.Model.Bridge.Couple.ShadowMappingBridge;
import com.example.couple.Model.Bridge.Couple.TriadBridge;
import com.example.couple.Model.Bridge.Couple.TriadMappingBridge;
import com.example.couple.Model.Bridge.Single.ClawBridge;
import com.example.couple.Model.Bridge.Single.CombineTouchBridge;
import com.example.couple.Model.Bridge.Single.ConnectedBridge;
import com.example.couple.Model.Bridge.Single.LottoTouchBridge;
import com.example.couple.Model.Bridge.Single.ShadowTouchBridge;
import com.example.couple.Model.Time.Cycle.Cycle;
import com.example.couple.Model.Time.Cycle.YearCycle;
import com.example.couple.Model.Display.BSingle;
import com.example.couple.Model.Display.Set;
import com.example.couple.Model.Display.SpecialNumbersHistory;
import com.example.couple.Model.Display.Status;
import com.example.couple.Model.Origin.Couple;
import com.example.couple.Model.Origin.Jackpot;
import com.example.couple.Model.Origin.Lottery;
import com.example.couple.Model.Support.ClawSupport;
import com.example.couple.Model.Support.ConnectedSupport;
import com.example.couple.Model.Support.DayPositions;
import com.example.couple.Model.Support.Doublet;
import com.example.couple.Model.Support.History;
import com.example.couple.Model.Support.JackpotHistory;
import com.example.couple.Model.Support.Position;
import com.example.couple.Model.Support.ShadowSingle;
import com.example.couple.Model.Support.SupportTriad;
import com.example.couple.Model.Time.TimeBase;
import com.example.couple.Model.Support.TriadSets;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class JackpotBridgeHandler {

    /**
     * work with jackpot and others
     */

    public static CycleBridge GetCompatibleCycleBridge(List<Jackpot> jackpotList,
                                                       List<TimeBase> timeBases, int dayNumberBefore) {
        if (jackpotList.size() < dayNumberBefore || timeBases.size() < dayNumberBefore + 1)
            return CycleBridge.getEmpty();
        Cycle cycleDay = timeBases.get(dayNumberBefore).getDateCycle().getDay();
        List<YearCycle> yearCycles =
                cycleDay.getCompatibleYearCyclesByBranches(TimeInfo.CYCLE_START_YEAR, TimeInfo.CURRENT_YEAR);
        Jackpot jackpot = dayNumberBefore == 0 ? Jackpot.getEmpty() : jackpotList.get(dayNumberBefore - 1);
        return new CycleBridge(Const.COMPATIBLE_CYCLE_BRIDGE_NAME,
                yearCycles, new JackpotHistory(dayNumberBefore, jackpot));
    }

    public static CycleBridge GetIncompatibleCycleBridge(List<Jackpot> jackpotList,
                                                         List<TimeBase> timeBases, int dayNumberBefore) {
        if (jackpotList.size() < dayNumberBefore || timeBases.size() < dayNumberBefore + 1)
            return CycleBridge.getEmpty();
        Cycle cycleDay = timeBases.get(dayNumberBefore).getDateCycle().getDay();
        List<YearCycle> yearCycles =
                cycleDay.getIncompatibleYearCyclesByBranches(TimeInfo.CYCLE_START_YEAR, TimeInfo.CURRENT_YEAR);
        Jackpot jackpot = dayNumberBefore == 0 ? Jackpot.getEmpty() : jackpotList.get(dayNumberBefore - 1);
        return new CycleBridge(Const.INCOMPATIBLE_CYCLE_BRIDGE_NAME,
                yearCycles, new JackpotHistory(dayNumberBefore, jackpot));
    }

    /**
     * work with jackpot and lottery
     */

    public static CombineTouchBridge GetCombineTouchBridge(List<Jackpot> jackpotList,
                                                           List<Lottery> lotteries, int dayNumberBefore) {
        if (jackpotList.size() - Const.DAY_OF_WEEK < dayNumberBefore ||
                lotteries.size() - Const.CONNECTED_BRIDGE_FINDING_DAYS < dayNumberBefore)
            return CombineTouchBridge.getEmpty();
        ShadowTouchBridge shadowTouchBridge = GetShadowTouchBridge(jackpotList, dayNumberBefore);
        ConnectedBridge connectedBridge = GetConnectedBridge(lotteries, Const.CONNECTED_BRIDGE_FINDING_DAYS,
                dayNumberBefore, Const.CONNECTED_BRIDGE_MAX_DISPLAY);
        LottoTouchBridge lottoTouchBridge = GetLottoTouchBridge(lotteries, dayNumberBefore);
        Jackpot jackpot = dayNumberBefore == 0 ? Jackpot.getEmpty() : jackpotList.get(dayNumberBefore - 1);
        return new CombineTouchBridge(shadowTouchBridge, connectedBridge, lottoTouchBridge,
                new JackpotHistory(dayNumberBefore, jackpot));
    }

    /**
     * work with jackpot
     */

    public static TriadMappingBridge GetTriadMappingBridge(List<Jackpot> jackpotList, int dayNumberBefore) {
        if (jackpotList.size() < dayNumberBefore + 14)
            return TriadMappingBridge.getEmpty();
        Map<Couple, Couple> sequentCoupleMap = new HashMap<>();
        sequentCoupleMap.put(jackpotList.get(dayNumberBefore + 1).getCouple(),
                jackpotList.get(dayNumberBefore).getCouple());
        sequentCoupleMap.put(jackpotList.get(dayNumberBefore + 7).getCouple(),
                jackpotList.get(dayNumberBefore).getCouple());
//        List<Couple> couplesNextDay = getCouplesNextDay(jackpotList, dayNumberBefore);
//        if (couplesNextDay.size() >= 2) {
//            sequentCoupleMap.put(couplesNextDay.get(1), couplesNextDay.get(0));
//        }
        Jackpot jackpot = dayNumberBefore == 0 ? Jackpot.getEmpty() : jackpotList.get(dayNumberBefore - 1);
        return new TriadMappingBridge(sequentCoupleMap, new JackpotHistory(dayNumberBefore, jackpot));
    }

    public static SpecialNumbersHistory GetSpecialNumbersHistory(List<Jackpot> jackpotList,
                                                                 String numberType, int value) {
        if (jackpotList.isEmpty()) return new SpecialNumbersHistory();
        List<Integer> numbers = new ArrayList<>();
        switch (numberType) {
            case Const.HEAD:
                numbers = NumberArrayHandler.getHeads(value);
                break;
            case Const.TAIL:
                numbers = NumberArrayHandler.getTails(value);
                break;
            case Const.SUM:
                numbers = NumberArrayHandler.getSums(value);
                break;
            case Const.SET:
                Set set = new Set(value);
                numbers = set.getSetsDetail();
                break;
            case Const.DOUBLE:
                numbers = Const.DOUBLE_SET;
                break;
            case Const.DEVIATED_DOUBLE:
                numbers = Const.DEVIATED_DOUBLE_SET;
                break;
            case Const.NEAR_DOUBLE:
                numbers = Const.NEAR_DOUBLE_SET;
                break;
            default:
                break;
        }
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
        return new SpecialNumbersHistory(numberType, value, beatList);
    }

    public static ShadowMappingBridge GetShadowMappingBridge(List<Jackpot> reverseJackpotList, int dayNumberBefore) {
        if (reverseJackpotList.size() < dayNumberBefore + 2)
            return ShadowMappingBridge.getEmpty();
        ShadowSingle first = reverseJackpotList.get(dayNumberBefore + 1).getCouple().getShadowSingle();
        ShadowSingle second = reverseJackpotList.get(dayNumberBefore).getCouple().getShadowSingle();
        Jackpot jackpot = dayNumberBefore == 0 ? Jackpot.getEmpty() : reverseJackpotList.get(dayNumberBefore - 1);
        return new ShadowMappingBridge(first, second, new JackpotHistory(dayNumberBefore, jackpot));
    }

    public static MappingBridge GetMatchMappingBridge(List<Jackpot> jackpotList, int dayNumberBefore) {
        if (jackpotList.size() < dayNumberBefore + 2)
            return MappingBridge.getEmpty();
        List<Integer> firstList = jackpotList.get(dayNumberBefore + 1)
                .getCouple().getMappingNumbers(Const.MAPPING_ALL);
        List<Integer> secondList = jackpotList.get(dayNumberBefore)
                .getCouple().getMappingNumbers(Const.MAPPING_ALL);
        List<Integer> results = new ArrayList<>();
        for (int second : secondList) {
            if (firstList.contains(second)) {
                results.add(second);
            }
        }
        Collections.sort(firstList);
        Jackpot jackpot = dayNumberBefore == 0 ?
                Jackpot.getEmpty() : jackpotList.get(dayNumberBefore - 1);
        return new MappingBridge(Const.MATCH_MAPPING_BRIDGE_NAME,
                results, new JackpotHistory(dayNumberBefore, jackpot));
    }

    public static MappingBridge GetMappingBridge(List<Jackpot> reverseJackpotList,
                                                 int mappingType, int dayNumberBefore) {
        if (reverseJackpotList.size() < dayNumberBefore + 2)
            return MappingBridge.getEmpty();
        List<Integer> firstList = reverseJackpotList.get(dayNumberBefore + 1)
                .getCouple().getMappingNumbers(mappingType);
        List<Integer> secondList = reverseJackpotList.get(dayNumberBefore)
                .getCouple().getMappingNumbers(mappingType);
        for (int second : secondList) {
            if (!firstList.contains(second)) {
                firstList.add(second);
            }
        }
        Collections.sort(firstList);
        String bridgeName = "";
        switch (mappingType) {
            case 1:
                bridgeName = Const.MAPPING_BRIDGE_NAME_1;
                break;
            case Const.MAPPING_ALL:
                bridgeName = Const.MAPPING_BRIDGE_NAME;
                break;
        }
        Jackpot jackpot = dayNumberBefore == 0 ?
                Jackpot.getEmpty() : reverseJackpotList.get(dayNumberBefore - 1);
        return new MappingBridge(bridgeName, firstList, new JackpotHistory(dayNumberBefore, jackpot));
    }

    public static ShadowTouchBridge GetNegativeShadowTouchBridge(List<Jackpot> reverseJackpotList, int dayNumberBefore) {
        if (reverseJackpotList.size() < Const.DAY_OF_WEEK + dayNumberBefore)
            return ShadowTouchBridge.getEmpty();
        List<Integer> touchs = new ArrayList<>();
        Couple coupleLastWeek = reverseJackpotList.get(Const.DAY_OF_WEEK + dayNumberBefore - 1).getCouple();
        int first = coupleLastWeek.getFirst();
        int second = coupleLastWeek.getSecond();
        touchs.add(CoupleHandler.getNegativeShadow(first));
        touchs.add(CoupleHandler.getNegativeShadow(second));
        if (first == second) touchs.add(first);
        List<Integer> results = NumberBase.filterDuplicatedNumbers(touchs);
        Collections.sort(results, (x, y) -> x - y);
        Jackpot jackpot = dayNumberBefore == 0 ?
                Jackpot.getEmpty() : reverseJackpotList.get(dayNumberBefore - 1);
        return new ShadowTouchBridge(Const.NEGATIVE_SHADOW_BRIDGE_NAME, results,
                new JackpotHistory(dayNumberBefore, jackpot));
    }

    public static ShadowTouchBridge GetPositiveShadowTouchBridge(List<Jackpot> reverseJackpotList, int dayNumberBefore) {
        if (reverseJackpotList.size() < Const.DAY_OF_WEEK + dayNumberBefore)
            return ShadowTouchBridge.getEmpty();
        List<Integer> touchs = new ArrayList<>();
        Couple coupleLastWeek = reverseJackpotList.get(Const.DAY_OF_WEEK + dayNumberBefore - 1).getCouple();
        int first = coupleLastWeek.getFirst();
        int second = coupleLastWeek.getSecond();
        touchs.add(CoupleHandler.getShadow(first));
        touchs.add(CoupleHandler.getShadow(second));
        if (first == second) touchs.add(first);
        List<Integer> results = NumberBase.filterDuplicatedNumbers(touchs);
        Collections.sort(results, (x, y) -> x - y);
        Jackpot jackpot = dayNumberBefore == 0 ?
                Jackpot.getEmpty() : reverseJackpotList.get(dayNumberBefore - 1);
        return new ShadowTouchBridge(Const.POSITIVE_SHADOW_BRIDGE_NAME, results,
                new JackpotHistory(dayNumberBefore, jackpot));
    }

    public static ShadowTouchBridge GetShadowTouchBridge(List<Jackpot> jackpotList, int dayNumberBefore) {
        if (jackpotList.size() - Const.DAY_OF_WEEK < dayNumberBefore)
            return ShadowTouchBridge.getEmpty();
        List<Integer> touchs = new ArrayList<>();
        Couple coupleLastWeek = jackpotList.get(Const.DAY_OF_WEEK + dayNumberBefore - 1).getCouple();
        int first = coupleLastWeek.getFirst();
        int second = coupleLastWeek.getSecond();
        touchs.add(CoupleHandler.getNegativeShadow(first));
        touchs.add(CoupleHandler.getNegativeShadow(second));
        touchs.add(CoupleHandler.getShadow(first));
        touchs.add(CoupleHandler.getShadow(second));
        if (first == second) touchs.add(first);
        List<Integer> results = NumberBase.filterDuplicatedNumbers(touchs);
        Collections.sort(results, (x, y) -> x - y);
        Jackpot jackpot = dayNumberBefore == 0 ?
                Jackpot.getEmpty() : jackpotList.get(dayNumberBefore - 1);
        return new ShadowTouchBridge(Const.SHADOW_TOUCH_BRIDGE_NAME, results,
                new JackpotHistory(dayNumberBefore, jackpot));
    }

    public static List<BSingle> GetTouchsByThirdClawBridge(List<Jackpot> jackpotList, int dayNumberBefore) {
        int sizeTest = jackpotList.size() - dayNumberBefore < 5 ? jackpotList.size() - dayNumberBefore : 5;

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

    public static PeriodBridge GetPeriodBridge(List<Jackpot> jackpotList, int dayNumberBefore) {
        List<History> periodHistories3 = GetPeriodHistoryList(jackpotList,
                dayNumberBefore, 3, Const.AMPLITUDE_OF_PERIOD_BRIDGE);
        List<History> periodHistories4 = GetPeriodHistoryList(jackpotList,
                dayNumberBefore, 4, Const.AMPLITUDE_OF_PERIOD_BRIDGE);
        Jackpot jackpot = dayNumberBefore == 0 ?
                Jackpot.getEmpty() : jackpotList.get(dayNumberBefore - 1);
        return new PeriodBridge(periodHistories3, periodHistories4,
                new JackpotHistory(dayNumberBefore, jackpot));
    }

    public static List<History> GetPeriodHistoryList(List<Jackpot> jackpotList,
                                                     int dayNumberBefore, int periodNumber, int range) {
        if (jackpotList.size() < dayNumberBefore + periodNumber) return new ArrayList<>();
        List<Integer> lastNumbers = new ArrayList<>();
        for (int i = dayNumberBefore; i < dayNumberBefore + periodNumber; i++) {
            lastNumbers.add(jackpotList.get(i).getCoupleInt());
        }

        List<History> historyList = new ArrayList<>();
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
                historyList.add(new History(start, end, numbers));
            }
        }

        Collections.reverse(historyList);

        return historyList;
    }

    private static boolean isInPeriod(int number, int numberCheck, int range) {
        if (number < 0 || number > 99) return false;
        if (number <= range) return numberCheck >= 0 && numberCheck <= number + range;
        if (number >= 99 - range) return numberCheck <= 99 && numberCheck >= number - range;
        return numberCheck >= number - range && numberCheck <= number + range;
    }

    public static List<Couple> getCouplesNextDay(List<Jackpot> jackpotList, int dayNumberBefore) {
        if (jackpotList.isEmpty()) return new ArrayList<>();
        Couple couple = jackpotList.get(dayNumberBefore).getCouple();
        List<Couple> results = new ArrayList<>();
        for (int i = dayNumberBefore; i < jackpotList.size() - 1; i++) {
            if (jackpotList.get(i).getCouple().equals(couple)) {
                results.add(jackpotList.get(i + 1).getCouple());
            }
        }
        return results;
    }

    /**
     * work with lottery
     */

    public static LottoTouchBridge GetLottoTouchBridge(List<Lottery> lotteries, int dayNumberBefore) {
        if (lotteries.size() - dayNumberBefore < 1) return LottoTouchBridge.getEmpty();
        Jackpot jackpot = dayNumberBefore == 0 ?
                Jackpot.getEmpty() : lotteries.get(dayNumberBefore - 1).getJackpot();
        Lottery lottery = lotteries.get(dayNumberBefore);
        return new LottoTouchBridge(lottery.getHeadLotoList(), lottery.getTailLotoList(),
                new JackpotHistory(dayNumberBefore, jackpot));
    }

    public static ConnectedBridge GetConnectedBridge(List<Lottery> lotteries, int searchingDays,
                                                     int dayNumberBefore, int maxDisplay) {
        if (lotteries.isEmpty()) return ConnectedBridge.getEmpty();
        List<DayPositions> dayPositionsList = GetDayPositionsList(lotteries, searchingDays, dayNumberBefore);
        if (dayPositionsList.size() < 2) return ConnectedBridge.getEmpty();
        List<Position> startPositions = dayPositionsList.get(0).getPositions();
        List<DayPositions> searchDayPositionsList = dayPositionsList.subList(1, dayPositionsList.size());

        List<ConnectedSupport> connectedSupportList = new ArrayList<>();
        for (Position startPosition : startPositions) {
            List<Integer> typeList = new ArrayList<>();
            typeList.add(startPosition.getType());
            int times = 1;
            for (DayPositions dayPositions : searchDayPositionsList) {
                List<Position> positions = dayPositions.getPositions();
                int count = 0;
                for (Position position : positions) {
                    if (position.equalsOnlyPosition(startPosition)) {
                        count++;
                        times++;
                        if (count == 1) {
                            typeList.add(position.getType());
                        } else {
                            typeList.add(position.getType() * -1);
                        }
                    }
                }
                if (count == 0) {
                    break;
                }
            }
            if (times > 1) {
                int value = lotteries.get(dayNumberBefore).getValueAtPosition(startPosition);
                Jackpot jackpot = dayNumberBefore == 0 ?
                        Jackpot.getEmpty() : lotteries.get(dayNumberBefore - 1).getJackpot();
                ConnectedSupport connectedSupport = new ConnectedSupport(value,
                        startPosition, typeList, times, new JackpotHistory(dayNumberBefore, jackpot));
                connectedSupportList.add(connectedSupport);
            }
        }

        Collections.sort(connectedSupportList, (x, y) -> y.getNumberOfRuns() - x.getNumberOfRuns());

        int sizeOfShow = maxDisplay < connectedSupportList.size() ? maxDisplay : connectedSupportList.size();
        Jackpot jackpot = dayNumberBefore == 0 ?
                Jackpot.getEmpty() : lotteries.get(dayNumberBefore - 1).getJackpot();
        List<ConnectedSupport> connectedSupports = connectedSupportList.subList(0, sizeOfShow);
        return new ConnectedBridge(connectedSupports, new JackpotHistory(dayNumberBefore, jackpot));
    }

    private static List<DayPositions> GetDayPositionsList(List<Lottery> lotteries,
                                                          int searchingDays, int dayNumberBefore) {
        if (lotteries.size() < 2) return new ArrayList<>();
        int size = searchingDays < lotteries.size() - dayNumberBefore ?
                searchingDays - 1 : lotteries.size() - dayNumberBefore - 1;
        List<DayPositions> dayPositionsList = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            int secondClaw = lotteries.get(dayNumberBefore + i).getSecondClaw();
            int firstClaw = lotteries.get(dayNumberBefore + i).getFirstClaw();
            List<String> lotterySet = lotteries.get(dayNumberBefore + i + 1).getLottery();
            List<Position> positions = new ArrayList<>();
            for (int j = 0; j < lotterySet.size(); j++) {
                String numberStr = lotterySet.get(j);
                for (int k = 0; k < numberStr.length(); k++) {
                    int single = Integer.parseInt(numberStr.charAt(k) + "");
                    if (single == secondClaw || single == CoupleHandler.getShadow(secondClaw)) {
                        positions.add(new Position(j, k, 2));
                    }
                    if (single == firstClaw || single == CoupleHandler.getShadow(firstClaw)) {
                        positions.add(new Position(j, k, 1));
                    }
                }
            }
            DayPositions dayPositions =
                    new DayPositions(lotteries.get(dayNumberBefore + i).getDateBase(), positions);
            dayPositionsList.add(dayPositions);
        }
        return dayPositionsList;
    }

    public static List<TriadBridge> GetTriadBridge(List<Lottery> lotteries, int searchingDays,
                                                   int dayNumberBefore, int maxDisplay) {
        if (lotteries.size() == 0) return new ArrayList<>();
        List<SupportTriad> supportTriadList = GetSupportTriadList(lotteries,
                searchingDays, dayNumberBefore, 50);

        List<TriadBridge> triadBridgeList = new ArrayList<>();
        for (int i = 0; i < supportTriadList.size(); i++) {
            List<Integer> firstStatus = supportTriadList.get(i).getStatusList();
            for (int j = i + 1; j < supportTriadList.size(); j++) {
                List<Integer> secondStatus = supportTriadList.get(j).getStatusList();
                for (int k = j + 1; k < supportTriadList.size(); k++) {
                    List<Integer> thirdStatus = supportTriadList.get(k).getStatusList();
                    int min = minThreeNumber(firstStatus.size(), secondStatus.size(), thirdStatus.size());
                    List<Status> statusList = new ArrayList<>();
                    for (int t = 0; t < min; t++) {
                        int first = firstStatus.get(t);
                        int second = secondStatus.get(t);
                        int third = thirdStatus.get(t);
                        Couple couple = lotteries.get(dayNumberBefore + t).getJackpotCouple();
                        boolean isDouble = couple.isDouble();
                        if (checkThreeNumber(first, second, third, isDouble)) {
                            Status status = new Status(first, second, third);
                            statusList.add(status);
                        } else {
                            break;
                        }
                    }
                    if (statusList.size() > 2) {
                        Jackpot jackpot = dayNumberBefore == 0 ?
                                Jackpot.getEmpty() : lotteries.get(dayNumberBefore - 1).getJackpot();
                        TriadBridge triadBridge = new TriadBridge(supportTriadList.get(i), supportTriadList.get(j),
                                supportTriadList.get(k), statusList, new JackpotHistory(dayNumberBefore, jackpot));
                        triadBridgeList.add(triadBridge);
                    }
                }
            }
        }

        List<TriadBridge> results = new ArrayList<>();
        for (int i = 0; i < triadBridgeList.size(); i++) {
            if (!results.contains(triadBridgeList.get(i))) {
                results.add(triadBridgeList.get(i));
            }
        }

        Collections.sort(results, new Comparator<TriadBridge>() {
            @Override
            public int compare(TriadBridge o1, TriadBridge o2) {
                int size1 = o1.getStatusList().size();
                int size2 = o2.getStatusList().size();
                if (size1 < size2) return 1;
                else if (size1 == size2) return 0;
                else return -1;
            }
        });

        int sizeOfShow = maxDisplay < results.size() ? maxDisplay : results.size();
        return results.subList(0, sizeOfShow);
    }

    private static boolean checkThreeNumber(int first, int second, int third, boolean isDouble) {
        if (first == 0) return checkTwoNumber(second, third, isDouble);
        if (second == 0) return checkTwoNumber(first, third, isDouble);
        if (checkTwoNumber(first, second, isDouble)) return true;
        if (checkTwoNumber(second, third, isDouble)) return true;
        return checkTwoNumber(third, first, isDouble);
    }

    private static boolean checkTwoNumber(int first, int second, boolean isDouble) {
        int sum = isDouble ? 4 : 3;
        return first + second == sum;
    }

    public static List<SupportTriad> GetSupportTriadList(List<Lottery> lotteries, int searchingDays,
                                                         int dayNumberBefore, int maxDisplay) {
        if (lotteries.size() == 0) return new ArrayList<>();

        List<DayPositions> dayPositionsList = GetDayPositionsList(lotteries, searchingDays, dayNumberBefore);

        int[][] count = new int[Const.NUMBER_OF_PRIZES][Const.MAX_LENGTH_OF_PRIZE];
        for (DayPositions dayPositions : dayPositionsList) {
            for (Position position : dayPositions.getPositions()) {
                count[position.getFirstLevel()][position.getSecondLevel()]++;
            }
        }

        List<Position> positions = new ArrayList<>();
        for (int i = 0; i < Const.NUMBER_OF_PRIZES; i++) {
            for (int j = 0; j < Const.MAX_LENGTH_OF_PRIZE; j++) {
                if (count[i][j] > 2) {
                    positions.add(new Position(i, j, count[i][j]));
                }
            }
        }

        // sort
        Collections.sort(positions, new Comparator<Position>() {
            @Override
            public int compare(Position o1, Position o2) {
                if (o1.getType() < o2.getType()) {
                    return 1;
                } else if (o1.getType() == o2.getType()) {
                    return 0;
                } else {
                    return -1;
                }
            }
        });

        int sizeOfShowing = (positions.size() < maxDisplay) ? positions.size() : maxDisplay;
        List<SupportTriad> supportTriadList = new ArrayList<>();
        Lottery lotteryThatDay = lotteries.get(dayNumberBefore);
        for (int i = 0; i < sizeOfShowing; i++) {
            Position position = positions.get(i);
            int value = lotteryThatDay.getValueAtPosition(position);
            List<Integer> statusList = GetStatusList(lotteries, position, searchingDays, dayNumberBefore);
            SupportTriad supportTriad = new SupportTriad(value, position, statusList);
            supportTriadList.add(supportTriad);
        }

        return supportTriadList;
    }


    public static int minThreeNumber(int first, int second, int third) {
        int min = Math.min(first, second);
        return Math.min(min, third);
    }

    private static List<Integer> GetStatusList(List<Lottery> lotteries, Position position,
                                               int searchingDays, int dayNumberBefore) {
        if (lotteries.size() == 0) return new ArrayList<>();
        int size = searchingDays < lotteries.size() - dayNumberBefore ?
                searchingDays : lotteries.size() - dayNumberBefore;
        List<Integer> statusList = new ArrayList<>();
        for (int i = 0; i < size - 1; i++) {
            int secondClaw = lotteries.get(dayNumberBefore + i).getSecondClaw();
            int firstClaw = lotteries.get(dayNumberBefore + i).getFirstClaw();
            int number = lotteries.get(dayNumberBefore + i + 1).getValueAtPosition(position);
            if (number == secondClaw || number == CoupleHandler.getShadow(secondClaw)) {
                statusList.add(2);
            } else if (number == firstClaw || number == CoupleHandler.getShadow(firstClaw)) {
                statusList.add(1);
            } else {
                statusList.add(0);
            }
        }
        return statusList;
    }

    public static List<TriadSets> GetTriadSetsList(List<TriadBridge> triadBridgeList) {
        if (triadBridgeList.size() == 0) return new ArrayList<>();
        List<TriadSets> triadSetsList = new ArrayList<>();
        for (int i = 0; i < triadBridgeList.size(); i++) {
            int count = 0;
            List<TriadBridge> bridges = new ArrayList<>();
            bridges.add(triadBridgeList.get(i));
            for (int j = i + 1; j < triadBridgeList.size(); j++) {
                if (triadBridgeList.get(i).equalsSmallShadowSingles(triadBridgeList.get(j))) {
                    count++;
                    bridges.add(triadBridgeList.get(j));
                    triadBridgeList.remove(j);
                    j--;
                }
            }
            TriadSets triadSets = new TriadSets(count, bridges);
            triadSetsList.add(triadSets);
        }
        Collections.sort(triadSetsList, new Comparator<TriadSets>() {
            @Override
            public int compare(TriadSets o1, TriadSets o2) {
                if (o1.getSize() < o2.getSize()) {
                    return 1;
                } else if (o1.getSize() == o2.getSize()) {
                    return 0;
                } else {
                    return -1;
                }
            }
        });
        return triadSetsList;
    }

    public static ClawBridge GetClawBridge(List<Lottery> lotteries, List<Integer> searchingDaysList,
                                           int dayNumberBefore, int maxDisplay, int bridgeType) {
        if (lotteries.size() < 30) return ClawBridge.getEmpty();
        List<Integer> touchs0 = JackpotBridgeHandler.GetTouchsByClawSupport(lotteries,
                searchingDaysList, dayNumberBefore, maxDisplay, 0);
        List<Integer> touchs1 = JackpotBridgeHandler.GetTouchsByClawSupport(lotteries,
                searchingDaysList, dayNumberBefore, maxDisplay, 1);
        List<Integer> touchs = bridgeType == 0 ? touchs0 : NumberBase.getPrivateNumbers(touchs0, touchs1);
        Jackpot jackpot = dayNumberBefore == 0 ?
                Jackpot.getEmpty() : lotteries.get(dayNumberBefore - 1).getJackpot();
        return new ClawBridge(touchs, bridgeType, new JackpotHistory(dayNumberBefore, jackpot));
    }

    // lấy biên rồi so sánh các vị trí bên trong để quyết định biên nào được chọn
    public static List<Integer> GetTouchsByClawSupport(List<Lottery> lotteries, List<Integer> searchingDaysList,
                                                       int dayNumberBefore, int maxDisplay, int bridgeType) {
        List<Doublet> doublets = new ArrayList<>();
        for (int searchDays : searchingDaysList) {
            List<ClawSupport> firstList = JackpotBridgeHandler.GetClawSupport(lotteries,
                    searchDays, dayNumberBefore, 1, maxDisplay);
            List<ClawSupport> secondList = JackpotBridgeHandler.GetClawSupport(lotteries,
                    searchDays, dayNumberBefore, 2, maxDisplay);
            if (!firstList.isEmpty() && !secondList.isEmpty()) {
                if (bridgeType == 0)
                    doublets.add(new Doublet(firstList.get(0).getClaw(), secondList.get(0).getClaw()));
                if (bridgeType == 1 && secondList.size() >= 2)
                    doublets.add(new Doublet(secondList.get(0).getClaw(), secondList.get(1).getClaw()));
            }
        }
        if (doublets.size() < 2) return new ArrayList<>();
        int firstStart = 0;
        int secondStart = 0;
        for (int i = 1; i < doublets.size() - 1; i++) {
            int startFirst = doublets.get(0).getFirst();
            int startSecond = doublets.get(0).getSecond();
            int runFirst = doublets.get(i).getFirst();
            int runSecond = doublets.get(i).getSecond();
            if (CoupleHandler.getSmallShadow(startFirst) == CoupleHandler.getSmallShadow(runFirst))
                firstStart++;
            if (CoupleHandler.getSmallShadow(startSecond) == CoupleHandler.getSmallShadow(runSecond))
                secondStart++;
        }

        int firstEnd = 0;
        int secondEnd = 0;
        for (int i = doublets.size() - 2; i > 0; i--) {
            int endFirst = doublets.get(doublets.size() - 1).getFirst();
            int endSecond = doublets.get(doublets.size() - 1).getSecond();
            int runFirst = doublets.get(i).getFirst();
            int runSecond = doublets.get(i).getSecond();
            if (CoupleHandler.getSmallShadow(endFirst) == CoupleHandler.getSmallShadow(runFirst))
                firstEnd++;
            if (CoupleHandler.getSmallShadow(endSecond) == CoupleHandler.getSmallShadow(runSecond))
                secondEnd++;
        }

        int firstSub = firstStart - firstEnd;
        int secondSub = secondStart - secondEnd;

        List<Integer> results = new ArrayList<>();
        int resultIndex = firstSub + secondSub >= 0 ? 0 : doublets.size() - 1;
        int first = doublets.get(resultIndex).getFirst();
        int second = doublets.get(resultIndex).getSecond();
        results.add(first);
        results.add(second);
        results.add(CoupleHandler.getShadow(first));
        results.add(CoupleHandler.getShadow(second));
        Collections.sort(results, (x, y) -> x - y);
        return results;
    }

    // các loại càng của jackpot đc đánh số: 54321
    public static List<ClawSupport> GetClawSupport(List<Lottery> lotteries, int searchingDays,
                                                   int dayNumberBefore, int clawType, int maxDisplay) {
        if (lotteries.size() == 0) return new ArrayList<>();

        List<DayPositions> dayPositionsList = new ArrayList<>();
        int size = searchingDays < lotteries.size() - dayNumberBefore ?
                searchingDays : lotteries.size() - dayNumberBefore;
        for (int i = 0; i < size - 1; i++) {
            int claw;
            if (clawType == 1) {
                claw = lotteries.get(dayNumberBefore + i).getFirstClaw();
            } else if (clawType == 2) {
                claw = lotteries.get(dayNumberBefore + i).getSecondClaw();
            } else {
                claw = lotteries.get(dayNumberBefore + i).getThirdClaw();
            }
            List<String> lotterySet = lotteries.get(dayNumberBefore + i + 1).getLottery();
            List<Position> positions = new ArrayList<>();
            for (int j = 0; j < lotterySet.size(); j++) {
                String numberStr = lotterySet.get(j);
                for (int k = 0; k < numberStr.length(); k++) {
                    int single = Integer.parseInt(numberStr.charAt(k) + "");
                    if (single == claw || single == CoupleHandler.getShadow(claw)) {
                        positions.add(new Position(j, k, 0));
                    }
                }
            }
            DayPositions dayPositions =
                    new DayPositions(lotteries.get(dayNumberBefore + i + 1).getDateBase(), positions);
            dayPositionsList.add(dayPositions);
        }

        int[][] count = new int[Const.NUMBER_OF_PRIZES][Const.MAX_LENGTH_OF_PRIZE];
        for (DayPositions dayPositions : dayPositionsList) {
            for (Position position : dayPositions.getPositions()) {
                count[position.getFirstLevel()][position.getSecondLevel()]++;
            }
        }

        List<Position> positions = new ArrayList<>();
        for (int i = 0; i < Const.NUMBER_OF_PRIZES; i++) {
            for (int j = 0; j < Const.MAX_LENGTH_OF_PRIZE; j++) {
                if (count[i][j] > 2) {
                    positions.add(new Position(i, j, count[i][j]));
                }
            }
        }

        // sort
        Collections.sort(positions, new Comparator<Position>() {
            @Override
            public int compare(Position o1, Position o2) {
                if (o1.getType() < o2.getType()) {
                    return 1;
                } else if (o1.getType() == o2.getType()) {
                    return 0;
                } else {
                    return -1;
                }
            }
        });

        int sizeOfShowing = (positions.size() < maxDisplay) ? positions.size() : maxDisplay;
        List<ClawSupport> clawSupportList = new ArrayList<>();
        Lottery lotteryThatDay = lotteries.get(dayNumberBefore);
        for (int i = 0; i < sizeOfShowing; i++) {
            Position position = positions.get(i);
            int value = lotteryThatDay.getValueAtPosition(position);
            List<Integer> beatList = GetBeatListByClaw(lotteries, position,
                    searchingDays, dayNumberBefore, clawType);
            Jackpot jackpot = dayNumberBefore == 0 ?
                    Jackpot.getEmpty() : lotteries.get(dayNumberBefore - 1).getJackpot();
            ClawSupport clawSupport = new ClawSupport(value, position, beatList,
                    new JackpotHistory(dayNumberBefore, jackpot));
            clawSupportList.add(clawSupport);
        }

        return clawSupportList;
    }

    private static List<Integer> GetBeatListByClaw(List<Lottery> lotteries, Position position,
                                                   int searchingDays, int dayNumberBefore, int clawType) {
        if (lotteries.size() == 0) return new ArrayList<>();
        int size = searchingDays < lotteries.size() - dayNumberBefore ?
                searchingDays : lotteries.size() - dayNumberBefore;
        List<Integer> beatList = new ArrayList<>();
        int count = 0;
        for (int i = 0; i < size - 1; i++) {
            count++;
            int claw;
            if (clawType == 1) {
                claw = lotteries.get(dayNumberBefore + i).getFirstClaw();
            } else if (clawType == 2) {
                claw = lotteries.get(dayNumberBefore + i).getSecondClaw();
            } else {
                claw = lotteries.get(dayNumberBefore + i).getThirdClaw();
            }
            int number = lotteries.get(dayNumberBefore + i + 1).getValueAtPosition(position);
            if (number == claw || number == CoupleHandler.getShadow(claw)) {
                beatList.add(count);
                count = 0;
            }
        }
        return beatList;
    }

}
