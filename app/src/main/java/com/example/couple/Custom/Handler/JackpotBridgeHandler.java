package com.example.couple.Custom.Handler;

import com.example.couple.Base.Handler.DateBase;
import com.example.couple.Base.Handler.NumberBase;
import com.example.couple.Custom.Const.Const;
import com.example.couple.Model.BridgeCouple.ShadowMappingBridge;
import com.example.couple.Model.BridgeSingle.ShadowTouchBridge;
import com.example.couple.Model.BridgeSingle.ClawBridge;
import com.example.couple.Model.Support.ClawSupport;
import com.example.couple.Model.BridgeSingle.ConnectedBridge;
import com.example.couple.Model.Support.ConnectedSupport;
import com.example.couple.Model.BridgeCouple.MappingBridge;
import com.example.couple.Model.BridgeCouple.PeriodBridge;
import com.example.couple.Model.BridgeSingle.CombineTouchBridge;
import com.example.couple.Model.Support.DayPositions;
import com.example.couple.Model.Support.Doublet;
import com.example.couple.Model.Support.JackpotHistory;
import com.example.couple.Model.Support.History;
import com.example.couple.Model.Support.Position;
import com.example.couple.Model.Support.ShadowSingle;
import com.example.couple.Model.Support.SupportTriad;
import com.example.couple.Model.BridgeCouple.TriadBridge;
import com.example.couple.Model.Support.TriadSets;
import com.example.couple.Model.Display.BSingle;
import com.example.couple.Model.Display.Status;
import com.example.couple.Model.Origin.Couple;
import com.example.couple.Model.Origin.Jackpot;
import com.example.couple.Model.Origin.Lottery;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class JackpotBridgeHandler {

    /**
     * work with jackpot and lottery
     */

    public static CombineTouchBridge GetTouchBridge(List<Jackpot> reverseJackpotList,
                                                    List<Lottery> lotteries, int dayNumberBefore) {
        if (reverseJackpotList.size() < dayNumberBefore + 2 || lotteries.size() < 30)
            return new CombineTouchBridge();
        List<Integer> firstList = GetShadowTouchBridge(reverseJackpotList, dayNumberBefore).getTouchs();
        List<Integer> secondList = GetConnectedBridge(lotteries, Const.CONNECTED_BRIDGE_FINDING_DAYS,
                dayNumberBefore, Const.CONNECTED_BRIDGE_MAX_DISPLAY).getTouchs();
        Jackpot jackpot = dayNumberBefore == 0 ?
                Jackpot.getEmptyJackpot() : reverseJackpotList.get(dayNumberBefore - 1);
        return new CombineTouchBridge("Cầu chạm bóng", firstList,
                "Cầu liên thông", secondList, "",
                new ArrayList<>(), new JackpotHistory(dayNumberBefore, jackpot));
    }

    /**
     * work with jackpot
     */

    public static ShadowMappingBridge GetShadowMappingBridge(List<Jackpot> reverseJackpotList, int dayNumberBefore) {
        if (reverseJackpotList.size() < dayNumberBefore + 2)
            return new ShadowMappingBridge();
        ShadowSingle first = reverseJackpotList.get(dayNumberBefore + 1).getCouple().getShadowSingle();
        ShadowSingle second = reverseJackpotList.get(dayNumberBefore).getCouple().getShadowSingle();
        Jackpot jackpot = dayNumberBefore == 0 ?
                Jackpot.getEmptyJackpot() : reverseJackpotList.get(dayNumberBefore - 1);
        return new ShadowMappingBridge(first, second, new JackpotHistory(dayNumberBefore, jackpot));
    }

    public static MappingBridge GetMappingBridge(List<Jackpot> reverseJackpotList, int dayNumberBefore) {
        if (reverseJackpotList.size() < dayNumberBefore + 2)
            return new MappingBridge(new ArrayList<>(), new JackpotHistory());
        List<Integer> firstList = reverseJackpotList.get(dayNumberBefore + 1).getCouple().getMappingNumbers();
        List<Integer> secondList = reverseJackpotList.get(dayNumberBefore).getCouple().getMappingNumbers();
        for (int second : secondList) {
            if (!firstList.contains(second)) {
                firstList.add(second);
            }
        }
        Jackpot jackpot = dayNumberBefore == 0 ?
                Jackpot.getEmptyJackpot() : reverseJackpotList.get(dayNumberBefore - 1);
        Collections.sort(firstList);
        return new MappingBridge(firstList, new JackpotHistory(dayNumberBefore, jackpot));
    }

    public static ShadowTouchBridge GetNegativeShadowTouchBridge(List<Jackpot> reverseJackpotList, int dayNumberBefore) {
        if (reverseJackpotList.size() < Const.DAY_OF_WEEK + dayNumberBefore)
            return ShadowTouchBridge.getEmpty();
        List<Integer> touchs = new ArrayList<>();
        String coupleLastWeek = reverseJackpotList.get(Const.DAY_OF_WEEK + dayNumberBefore - 1).getCouple().toString();
        int first = Integer.parseInt(coupleLastWeek.charAt(0) + "");
        int second = Integer.parseInt(coupleLastWeek.charAt(1) + "");
        touchs.add(NumberBase.getNegativeShadow(first));
        touchs.add(NumberBase.getNegativeShadow(second));
        if (first == second) touchs.add(first);
        List<Integer> results = new ArrayList<>();
        for (int touch : touchs) {
            if (!results.contains(touch)) {
                results.add(touch);
            }
        }
        Collections.sort(results, (x, y) -> x - y);
        Jackpot jackpot = dayNumberBefore == 0 ?
                Jackpot.getEmptyJackpot() : reverseJackpotList.get(dayNumberBefore - 1);
        return new ShadowTouchBridge(Const.NEGATIVE_SHADOW_BRIDGE_NAME, results,
                new JackpotHistory(dayNumberBefore, jackpot));
    }

    public static ShadowTouchBridge GetPositiveShadowTouchBridge(List<Jackpot> reverseJackpotList, int dayNumberBefore) {
        if (reverseJackpotList.size() < Const.DAY_OF_WEEK + dayNumberBefore)
            return ShadowTouchBridge.getEmpty();
        List<Integer> touchs = new ArrayList<>();
        String coupleLastWeek = reverseJackpotList.get(Const.DAY_OF_WEEK + dayNumberBefore - 1).getCouple().toString();
        int first = Integer.parseInt(coupleLastWeek.charAt(0) + "");
        int second = Integer.parseInt(coupleLastWeek.charAt(1) + "");
        touchs.add(NumberBase.getShadow(first));
        touchs.add(NumberBase.getShadow(second));
        if (first == second) touchs.add(first);
        List<Integer> results = new ArrayList<>();
        for (int touch : touchs) {
            if (!results.contains(touch)) {
                results.add(touch);
            }
        }
        Collections.sort(results, (x, y) -> x - y);
        Jackpot jackpot = dayNumberBefore == 0 ?
                Jackpot.getEmptyJackpot() : reverseJackpotList.get(dayNumberBefore - 1);
        return new ShadowTouchBridge(Const.POSITIVE_SHADOW_BRIDGE_NAME, results,
                new JackpotHistory(dayNumberBefore, jackpot));
    }

    public static ShadowTouchBridge GetShadowTouchBridge(List<Jackpot> reverseJackpotList, int dayNumberBefore) {
        if (reverseJackpotList.size() < Const.DAY_OF_WEEK + dayNumberBefore)
            return ShadowTouchBridge.getEmpty();
        List<Integer> touchs = new ArrayList<>();
        String coupleLastWeek = reverseJackpotList.get(Const.DAY_OF_WEEK + dayNumberBefore - 1).getCouple().toString();
        int first = Integer.parseInt(coupleLastWeek.charAt(0) + "");
        int second = Integer.parseInt(coupleLastWeek.charAt(1) + "");
        touchs.add(NumberBase.getNegativeShadow(first));
        touchs.add(NumberBase.getNegativeShadow(second));
        touchs.add(NumberBase.getShadow(first));
        touchs.add(NumberBase.getShadow(second));
        if (first == second) touchs.add(first);
        List<Integer> results = new ArrayList<>();
        for (int touch : touchs) {
            if (!results.contains(touch)) {
                results.add(touch);
            }
        }
        Collections.sort(results, (x, y) -> x - y);
        Jackpot jackpot = dayNumberBefore == 0 ?
                Jackpot.getEmptyJackpot() : reverseJackpotList.get(dayNumberBefore - 1);
        return new ShadowTouchBridge("Cầu chạm bóng", results,
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
                Jackpot.getEmptyJackpot() : jackpotList.get(dayNumberBefore - 1);
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

    /**
     * work with lottery
     */

    public static ConnectedBridge GetConnectedBridge(List<Lottery> lotteries, int searchingDays,
                                                     int dayNumberBefore, int maxDisplay) {
        if (lotteries.size() == 0) return ConnectedBridge.getEmpty();
        List<DayPositions> dayPositionsList = GetDayPositionsList(lotteries, searchingDays, dayNumberBefore);
        if (dayPositionsList.size() < 2) return ConnectedBridge.getEmpty();
        List<Position> startPositions = dayPositionsList.get(0).getPositions();

        List<ConnectedSupport> connectedSupportList = new ArrayList<>();
        for (int i = 0; i < startPositions.size(); i++) {
            List<Integer> typeList = new ArrayList<>();
            typeList.add(startPositions.get(i).getType());
            int times = 1;
            for (int j = 1; j < dayPositionsList.size(); j++) {
                List<Position> positions = dayPositionsList.get(j).getPositions();
                int count = 0;
                for (int k = 0; k < positions.size(); k++) {
                    if (positions.get(k).equalsOnlyPosition(startPositions.get(i))) {
                        count++;
                        times++;
                        if (count == 1) {
                            typeList.add(positions.get(k).getType());
                        } else {
                            typeList.add(positions.get(k).getType() * -1);
                        }
                    }
                }
                if (count == 0) {
                    break;
                }
            }
            if (times > 1) {
                int value = lotteries.get(dayNumberBefore).getValueAtPosition(startPositions.get(i));
                Jackpot jackpot = dayNumberBefore == 0 ?
                        Jackpot.getEmptyJackpot() : lotteries.get(dayNumberBefore - 1).getJackpot();
                ConnectedSupport connectedSupport = new ConnectedSupport(value,
                        startPositions.get(i), typeList, times, new JackpotHistory(dayNumberBefore, jackpot));
                connectedSupportList.add(connectedSupport);
            }
        }
        // Sort
        Collections.sort(connectedSupportList, new Comparator<ConnectedSupport>() {
            @Override
            public int compare(ConnectedSupport o1, ConnectedSupport o2) {
                if (o1.getNumberOfRuns() < o2.getNumberOfRuns()) {
                    return 1;
                } else if (o1.getNumberOfRuns() == o2.getNumberOfRuns()) {
                    return 0;
                } else {
                    return -1;
                }
            }
        });

        int sizeOfShow = maxDisplay < connectedSupportList.size() ? maxDisplay : connectedSupportList.size();
        Jackpot jackpot = dayNumberBefore == 0 ?
                Jackpot.getEmptyJackpot() : lotteries.get(dayNumberBefore - 1).getJackpot();
        List<ConnectedSupport> connectedSupports = connectedSupportList.subList(0, sizeOfShow);
        return new ConnectedBridge(connectedSupports, new JackpotHistory(dayNumberBefore, jackpot));
    }

    private static List<DayPositions> GetDayPositionsList(List<Lottery> lotteries,
                                                          int searchingDays, int dayNumberBefore) {
        if (lotteries.size() == 0) return new ArrayList<>();
        int size = searchingDays < lotteries.size() - dayNumberBefore ?
                searchingDays : lotteries.size() - dayNumberBefore;
        List<DayPositions> dayPositionsList = new ArrayList<>();
        for (int i = 0; i < size - 1; i++) {
            int secondClaw = lotteries.get(dayNumberBefore + i).getSecondClaw();
            int firstClaw = lotteries.get(dayNumberBefore + i).getFirstClaw();
            List<String> lotterySet = lotteries.get(dayNumberBefore + i + 1).getLottery();
            List<Position> positions = new ArrayList<>();
            for (int j = 0; j < lotterySet.size(); j++) {
                String numberStr = lotterySet.get(j);
                for (int k = 0; k < numberStr.length(); k++) {
                    int single = Integer.parseInt(numberStr.charAt(k) + "");
                    if (single == secondClaw || single == NumberBase.getShadow(secondClaw)) {
                        positions.add(new Position(j, k, 2));
                    }
                    if (single == firstClaw || single == NumberBase.getShadow(firstClaw)) {
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
                                Jackpot.getEmptyJackpot() : lotteries.get(dayNumberBefore - 1).getJackpot();
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
            if (number == secondClaw || number == NumberBase.getShadow(secondClaw)) {
                statusList.add(2);
            } else if (number == firstClaw || number == NumberBase.getShadow(firstClaw)) {
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
        if (lotteries.size() < 30) return new ClawBridge();
        List<Integer> touchs0 = JackpotBridgeHandler.GetTouchsByClawSupport(lotteries,
                searchingDaysList, dayNumberBefore, maxDisplay, 0);
        List<Integer> touchs1 = JackpotBridgeHandler.GetTouchsByClawSupport(lotteries,
                searchingDaysList, dayNumberBefore, maxDisplay, 1);
        List<Integer> touchs = bridgeType == 0 ? touchs0 : NumberBase.getPrivateNumbers(touchs0, touchs1);
        Jackpot jackpot = dayNumberBefore == 0 ?
                Jackpot.getEmptyJackpot() : lotteries.get(dayNumberBefore - 1).getJackpot();
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
            if (NumberBase.getSmallShadow(startFirst) == NumberBase.getSmallShadow(runFirst))
                firstStart++;
            if (NumberBase.getSmallShadow(startSecond) == NumberBase.getSmallShadow(runSecond))
                secondStart++;
        }

        int firstEnd = 0;
        int secondEnd = 0;
        for (int i = doublets.size() - 2; i > 0; i--) {
            int endFirst = doublets.get(doublets.size() - 1).getFirst();
            int endSecond = doublets.get(doublets.size() - 1).getSecond();
            int runFirst = doublets.get(i).getFirst();
            int runSecond = doublets.get(i).getSecond();
            if (NumberBase.getSmallShadow(endFirst) == NumberBase.getSmallShadow(runFirst))
                firstEnd++;
            if (NumberBase.getSmallShadow(endSecond) == NumberBase.getSmallShadow(runSecond))
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
        results.add(NumberBase.getShadow(first));
        results.add(NumberBase.getShadow(second));
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
                    if (single == claw || single == NumberBase.getShadow(claw)) {
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
                    Jackpot.getEmptyJackpot() : lotteries.get(dayNumberBefore - 1).getJackpot();
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
            if (number == claw || number == NumberBase.getShadow(claw)) {
                beatList.add(count);
                count = 0;
            }
        }
        return beatList;
    }

}
