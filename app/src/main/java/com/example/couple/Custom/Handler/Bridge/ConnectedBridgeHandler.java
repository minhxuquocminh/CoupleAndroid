package com.example.couple.Custom.Handler.Bridge;

import com.example.couple.Base.Handler.CoupleBase;
import com.example.couple.Base.Handler.NumberBase;
import com.example.couple.Base.Handler.SingleBase;
import com.example.couple.Custom.Const.Const;
import com.example.couple.Model.Bridge.Couple.ConnectedSetBridge;
import com.example.couple.Model.Bridge.Couple.TriadBridge;
import com.example.couple.Model.Bridge.Single.ClawBridge;
import com.example.couple.Model.Bridge.Single.ConnectedBridge;
import com.example.couple.Model.Display.Set;
import com.example.couple.Model.Display.Status;
import com.example.couple.Model.Origin.Couple;
import com.example.couple.Model.Origin.Jackpot;
import com.example.couple.Model.Origin.Lottery;
import com.example.couple.Model.Support.ClawSupport;
import com.example.couple.Model.Support.ConnectedSupport;
import com.example.couple.Model.Support.DayPositions;
import com.example.couple.Model.Support.Doublet;
import com.example.couple.Model.Support.JackpotHistory;
import com.example.couple.Model.Support.Position;
import com.example.couple.Model.Support.SupportTriad;
import com.example.couple.Model.Support.TriadSets;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class ConnectedBridgeHandler {

    public static ConnectedSetBridge GetConnectedSetBridge(List<Lottery> lotteries,
                                                           int dayNumberBefore,
                                                           int searchingDays,
                                                           int maxDisplay) {
        if (lotteries.isEmpty()) return ConnectedSetBridge.getEmpty();
        List<ConnectedSupport> connectedSupports =
                GetConnectedSupportList(lotteries, dayNumberBefore, searchingDays, maxDisplay);
        Jackpot jackpot = dayNumberBefore == 0 ?
                Jackpot.getEmpty() : lotteries.get(dayNumberBefore - 1).getJackpot();
        return new ConnectedSetBridge(connectedSupports, new JackpotHistory(dayNumberBefore, jackpot));
    }

    public static ConnectedBridge GetConnectedBridge(List<Lottery> lotteries, int dayNumberBefore,
                                                     int searchingDays, int maxDisplay) {
        if (lotteries.isEmpty()) return ConnectedBridge.getEmpty();
        List<ConnectedSupport> connectedSupports =
                GetConnectedSupportList(lotteries, dayNumberBefore, searchingDays, maxDisplay);
        Jackpot jackpot = dayNumberBefore == 0 ?
                Jackpot.getEmpty() : lotteries.get(dayNumberBefore - 1).getJackpot();
        return new ConnectedBridge(connectedSupports, new JackpotHistory(dayNumberBefore, jackpot));
    }

    public static List<ConnectedSupport> GetConnectedSupportList(List<Lottery> lotteries,
                                                                 int dayNumberBefore,
                                                                 int searchingDays,
                                                                 int maxDisplay) {
        if (lotteries.isEmpty()) return new ArrayList<>();
        List<DayPositions> dayPositionsList = GetDayPositionsList(lotteries, searchingDays, dayNumberBefore);
        if (dayPositionsList.size() < 2) return new ArrayList<>();
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

        return connectedSupportList.subList(0, sizeOfShow);
    }

    public static List<Integer> GetConnectedTouchs(List<ConnectedSupport> connectedSupports) {
        if (connectedSupports.isEmpty()) return new ArrayList<>();
        List<Integer> inits = new ArrayList<>();
        List<Integer> inits1 = new ArrayList<>();
        List<Integer> inits2 = new ArrayList<>();
        List<Integer> inits3 = new ArrayList<>();
        List<Integer> originInits3 = new ArrayList<>();
        for (int i = 0; i < connectedSupports.size(); i++) {
            int sizeTypeList = connectedSupports.get(i).getTypeList().size();
            int smallShadow = CoupleBase.getSmallShadow(connectedSupports.get(i).getValue());
            if (sizeTypeList > 4) {
                if (!inits1.contains(smallShadow)) {
                    inits1.add(smallShadow);
                }
            }
            if (sizeTypeList == 4) {
                if (!inits2.contains(smallShadow)) {
                    inits2.add(smallShadow);
                }
            }
            if (sizeTypeList < 4) {
                if (!inits3.contains(smallShadow)) {
                    inits3.add(smallShadow);
                }
                originInits3.add(smallShadow);
            }
        }

        Collections.reverse(originInits3);
        if (inits2.isEmpty() && inits1.isEmpty()) {
            for (int small : originInits3) {
                if (inits.size() < 2 && !inits.contains(small)) {
                    inits.add(small);
                }
            }
        } else {
            for (int small : inits2) {
                if (inits.size() < 2 && !inits.contains(small)) {
                    inits.add(small);
                }
            }
            for (int small : inits3) {
                if (inits.size() < 2 && !inits.contains(small)) {
                    inits.add(small);
                }
            }
        }

        if (inits.size() < 2) {
            for (int small : inits1) {
                if (inits.size() < 2 && !inits.contains(small)) {
                    inits.add(small);
                }
            }
        }

        List<Integer> results = new ArrayList<>();
        for (int small : inits) {
            results.add(small);
            results.add(SingleBase.getShadow(small));
        }

        return results;
    }

    public static List<Set> GetConnectedSets(List<ConnectedSupport> connectedSupports) {
        if (connectedSupports.isEmpty()) return new ArrayList<>();
        List<Integer> combines = new ArrayList<>();
        List<Integer> touchs = GetConnectedTouchs(connectedSupports);
        for (int i = 0; i < connectedSupports.size(); i++) {
            int sizeTypeList = connectedSupports.get(i).getTypeList().size();
            int smallShadow = CoupleBase.getSmallShadow(connectedSupports.get(i).getValue());
            if (sizeTypeList > 4) {
                if (!combines.contains(smallShadow)) {
                    combines.add(smallShadow);
                }
            }
            if (sizeTypeList == 4) {
                if (!combines.contains(smallShadow)) {
                    combines.add(smallShadow);
                }
            }
        }

        List<Integer> smallSets = new ArrayList<>();
        List<Set> results = new ArrayList<>();
        for (int combine : combines) {
            for (int touch : touchs) {
                int small = CoupleBase.getSmallShadow(combine * 10 + touch);
                if (!smallSets.contains(small)) {
                    smallSets.add(small);
                    results.add(new Set(combine, touch));
                }
            }
        }

        return results;
    }

    private static List<DayPositions> GetDayPositionsListByClawType(List<Lottery> lotteries,
                                                                    int dayNumberBefore,
                                                                    int searchingDays,
                                                                    int clawType) {
        if (lotteries.size() < 2) return new ArrayList<>();
        int size = searchingDays < lotteries.size() - dayNumberBefore ?
                searchingDays - 1 : lotteries.size() - dayNumberBefore - 1;
        List<DayPositions> dayPositionsList = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            int claw = lotteries.get(dayNumberBefore + i).getClaw(clawType);
            List<String> lotterySet = lotteries.get(dayNumberBefore + i + 1).getLottery();
            List<Position> positions = new ArrayList<>();
            for (int j = 0; j < lotterySet.size(); j++) {
                String numberStr = lotterySet.get(j);
                for (int k = 0; k < numberStr.length(); k++) {
                    int single = Integer.parseInt(numberStr.charAt(k) + "");
                    if (single == claw) {
                        positions.add(new Position(j, k, claw));
                    }
                }
            }
            DayPositions dayPositions =
                    new DayPositions(lotteries.get(dayNumberBefore + i).getDateBase(), positions);
            dayPositionsList.add(dayPositions);
        }
        return dayPositionsList;
    }

    private static List<DayPositions> GetDayPositionsList(List<Lottery> lotteries,
                                                          int searchingDays, int dayNumberBefore) {
        if (lotteries.size() < 2) return new ArrayList<>();
        int size = searchingDays < lotteries.size() - dayNumberBefore ?
                searchingDays - 1 : lotteries.size() - dayNumberBefore - 1;
        List<DayPositions> dayPositionsList = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            int secondClaw = lotteries.get(dayNumberBefore + i).getClaw(2);
            int firstClaw = lotteries.get(dayNumberBefore + i).getClaw(1);
            List<String> lotterySet = lotteries.get(dayNumberBefore + i + 1).getLottery();
            List<Position> positions = new ArrayList<>();
            for (int j = 0; j < lotterySet.size(); j++) {
                String numberStr = lotterySet.get(j);
                for (int k = 0; k < numberStr.length(); k++) {
                    int single = Integer.parseInt(numberStr.charAt(k) + "");
                    if (single == secondClaw || single == SingleBase.getShadow(secondClaw)) {
                        positions.add(new Position(j, k, 2));
                    }
                    if (single == firstClaw || single == SingleBase.getShadow(firstClaw)) {
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
            int secondClaw = lotteries.get(dayNumberBefore + i).getClaw(2);
            int firstClaw = lotteries.get(dayNumberBefore + i).getClaw(1);
            int number = lotteries.get(dayNumberBefore + i + 1).getValueAtPosition(position);
            if (number == secondClaw || number == SingleBase.getShadow(secondClaw)) {
                statusList.add(2);
            } else if (number == firstClaw || number == SingleBase.getShadow(firstClaw)) {
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
        List<Integer> touchs0 = GetTouchsByClawSupport(lotteries,
                searchingDaysList, dayNumberBefore, maxDisplay, 0);
        List<Integer> touchs1 = GetTouchsByClawSupport(lotteries,
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
            List<ClawSupport> firstList = GetClawSupport(lotteries,
                    searchDays, dayNumberBefore, 1, maxDisplay);
            List<ClawSupport> secondList = GetClawSupport(lotteries,
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
            if (CoupleBase.getSmallShadow(startFirst) == CoupleBase.getSmallShadow(runFirst))
                firstStart++;
            if (CoupleBase.getSmallShadow(startSecond) == CoupleBase.getSmallShadow(runSecond))
                secondStart++;
        }

        int firstEnd = 0;
        int secondEnd = 0;
        for (int i = doublets.size() - 2; i > 0; i--) {
            int endFirst = doublets.get(doublets.size() - 1).getFirst();
            int endSecond = doublets.get(doublets.size() - 1).getSecond();
            int runFirst = doublets.get(i).getFirst();
            int runSecond = doublets.get(i).getSecond();
            if (CoupleBase.getSmallShadow(endFirst) == CoupleBase.getSmallShadow(runFirst))
                firstEnd++;
            if (CoupleBase.getSmallShadow(endSecond) == CoupleBase.getSmallShadow(runSecond))
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
        results.add(SingleBase.getShadow(first));
        results.add(SingleBase.getShadow(second));
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
                claw = lotteries.get(dayNumberBefore + i).getClaw(1);
            } else if (clawType == 2) {
                claw = lotteries.get(dayNumberBefore + i).getClaw(2);
            } else {
                claw = lotteries.get(dayNumberBefore + i).getClaw(3);
            }
            List<String> lotterySet = lotteries.get(dayNumberBefore + i + 1).getLottery();
            List<Position> positions = new ArrayList<>();
            for (int j = 0; j < lotterySet.size(); j++) {
                String numberStr = lotterySet.get(j);
                for (int k = 0; k < numberStr.length(); k++) {
                    int single = Integer.parseInt(numberStr.charAt(k) + "");
                    if (single == claw || single == SingleBase.getShadow(claw)) {
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
                claw = lotteries.get(dayNumberBefore + i).getClaw(1);
            } else if (clawType == 2) {
                claw = lotteries.get(dayNumberBefore + i).getClaw(2);
            } else {
                claw = lotteries.get(dayNumberBefore + i).getClaw(3);
            }
            int number = lotteries.get(dayNumberBefore + i + 1).getValueAtPosition(position);
            if (number == claw || number == SingleBase.getShadow(claw)) {
                beatList.add(count);
                count = 0;
            }
        }
        return beatList;
    }

}
