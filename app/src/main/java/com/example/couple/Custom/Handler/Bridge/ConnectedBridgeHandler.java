package com.example.couple.Custom.Handler.Bridge;

import com.example.couple.Base.Handler.CoupleBase;
import com.example.couple.Base.Handler.SingleBase;
import com.example.couple.Custom.Const.Const;
import com.example.couple.Model.Bridge.Connected.ClawSupport;
import com.example.couple.Model.Bridge.Connected.ConnectedSetBridge;
import com.example.couple.Model.Bridge.Connected.ConnectedSupport;
import com.example.couple.Model.Bridge.Connected.DayPositions;
import com.example.couple.Model.Bridge.Connected.Pair;
import com.example.couple.Model.Bridge.Connected.PairConnectedBridge;
import com.example.couple.Model.Bridge.Connected.PairConnectedSupport;
import com.example.couple.Model.Bridge.Connected.PairPosition;
import com.example.couple.Model.Bridge.Connected.SupportTriad;
import com.example.couple.Model.Bridge.Connected.TriadBridge;
import com.example.couple.Model.Bridge.Connected.TriadSets;
import com.example.couple.Model.Bridge.Connected.TriadStatus;
import com.example.couple.Model.Bridge.Connected.Triangle;
import com.example.couple.Model.Bridge.Connected.TriangleConnectedSupport;
import com.example.couple.Model.Bridge.Connected.TrianglePosition;
import com.example.couple.Model.Bridge.JackpotHistory;
import com.example.couple.Model.Bridge.NumberSet.NumberSet;
import com.example.couple.Model.Bridge.Position;
import com.example.couple.Model.Bridge.Touch.ConnectedBridge;
import com.example.couple.Model.Origin.Couple;
import com.example.couple.Model.Origin.Jackpot;
import com.example.couple.Model.Origin.Lottery;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class ConnectedBridgeHandler {


    public static PairConnectedBridge getConnectedVer2Bridge(List<Lottery> lotteries, int dayNumberBefore,
                                                             int findingDays, int maxDisplay) {
        if (lotteries.isEmpty()) return PairConnectedBridge.getEmpty();
        List<PairConnectedSupport> connectedSupports =
                getPairConnectedSupports(lotteries, dayNumberBefore, findingDays, maxDisplay, false);
        Jackpot jackpot = dayNumberBefore == 0 ?
                Jackpot.getEmpty() : lotteries.get(dayNumberBefore - 1).getJackpot();
        return new PairConnectedBridge("2 cửa", connectedSupports, new JackpotHistory(dayNumberBefore, jackpot));
    }

    public static List<PairConnectedSupport> getPairConnectedSupports(List<Lottery> lotteries, int dayNumberBefore,
                                                                      int findingDays, int maxDisplay,
                                                                      boolean useShadow) {
        if (lotteries.isEmpty()) return new ArrayList<>();
        List<ConnectedSupport> connectedSupports = getConnectedSupportList(lotteries,
                dayNumberBefore, findingDays, maxDisplay, useShadow, false);
        if (connectedSupports.size() < 2) return new ArrayList<>();

        List<PairConnectedSupport> supports = new ArrayList<>();
        for (int i = 0; i < connectedSupports.size(); i++) {
            for (int j = i + 1; j < connectedSupports.size(); j++) {
                List<Integer> firstTypes = connectedSupports.get(i).getTypeList();
                List<Integer> secondTypes = connectedSupports.get(j).getTypeList();
                int size = Math.min(firstTypes.size(), secondTypes.size());
                int time = 0;
                List<Pair> pairTypes = new ArrayList<>();
                for (int k = 0; k < size; k++) {
                    if (firstTypes.get(k) + secondTypes.get(k) != 3) {
                        break;
                    }
                    time++;
                    pairTypes.add(new Pair(firstTypes.get(k), secondTypes.get(k)));
                }
                if (time > 1) {
                    Position firstPosition = connectedSupports.get(i).getPosition();
                    Position secondPosition = connectedSupports.get(j).getPosition();
                    int first = lotteries.get(dayNumberBefore).getValueAtPosition(firstPosition);
                    int second = lotteries.get(dayNumberBefore).getValueAtPosition(secondPosition);
                    PairPosition pairPosition = new PairPosition(firstPosition, secondPosition);
                    supports.add(new PairConnectedSupport(new Pair(first, second), pairPosition, pairTypes, time));
                }
            }
        }

        supports.sort((x, y) -> y.getNumberOfRuns() - x.getNumberOfRuns());
        int sizeOfShow = Math.min(maxDisplay, supports.size());

        return supports.subList(0, sizeOfShow);
    }

    public static List<TriangleConnectedSupport> getTriangleConnectedSupports(List<Lottery> lotteries, int dayNumberBefore,
                                                                              int findingDays, int maxDisplay,
                                                                              boolean useShadow) {
        if (lotteries.isEmpty()) return new ArrayList<>();
        List<ConnectedSupport> connectedSupports = getConnectedSupportList(lotteries,
                dayNumberBefore, findingDays, maxDisplay, useShadow, true);
        if (connectedSupports.size() < 2) return new ArrayList<>();

        List<TriangleConnectedSupport> supports = new ArrayList<>();
        for (int i = 0; i < connectedSupports.size(); i++) {
            for (int j = i + 1; j < connectedSupports.size(); j++) {
                for (int k = j + 1; k < connectedSupports.size(); k++) {
                    List<Integer> firstTypes = connectedSupports.get(i).getTypeList();
                    List<Integer> secondTypes = connectedSupports.get(j).getTypeList();
                    List<Integer> thirdTypes = connectedSupports.get(k).getTypeList();
                    int size = Math.min(firstTypes.size(), secondTypes.size());
                    size = Math.min(size, thirdTypes.size());
                    int time = 0;
                    List<Triangle> types = new ArrayList<>();
                    for (int t = 0; t < size; t++) {
                        if (firstTypes.get(t) + secondTypes.get(t) + thirdTypes.get(t) != 6) {
                            break;
                        }
                        if (firstTypes.get(t) == 2 && secondTypes.get(t) == 2 && thirdTypes.get(t) == 2) {
                            break;
                        }
                        time++;
                        types.add(new Triangle(firstTypes.get(t), secondTypes.get(t), thirdTypes.get(t)));
                    }
                    if (time > 1) {
                        Position firstPosition = connectedSupports.get(i).getPosition();
                        Position secondPosition = connectedSupports.get(j).getPosition();
                        Position thirdPosition = connectedSupports.get(k).getPosition();
                        int first = lotteries.get(dayNumberBefore).getValueAtPosition(firstPosition);
                        int second = lotteries.get(dayNumberBefore).getValueAtPosition(secondPosition);
                        int third = lotteries.get(dayNumberBefore).getValueAtPosition(thirdPosition);
                        TrianglePosition trianglePosition =
                                new TrianglePosition(firstPosition, secondPosition, thirdPosition);
                        supports.add(new TriangleConnectedSupport(new Triangle(first, second, third),
                                trianglePosition, types, time));
                    }
                }
            }
        }

        supports.sort((x, y) -> y.getNumberOfRuns() - x.getNumberOfRuns());
        int sizeOfShow = Math.min(maxDisplay, supports.size());

        return supports.subList(0, sizeOfShow);
    }

    public static List<ConnectedSupport> getConnectedSupportList(List<Lottery> lotteries, int dayNumberBefore,
                                                                 int findingDays, int maxDisplay,
                                                                 boolean useShadow, boolean addThirdClaw) {
        if (lotteries.isEmpty()) return new ArrayList<>();
        List<DayPositions> dayPositionsList =
                getDayPositionsList(lotteries, dayNumberBefore, findingDays, useShadow, addThirdClaw);
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
                ConnectedSupport connectedSupport = new ConnectedSupport(value, startPosition, typeList, times);
                connectedSupportList.add(connectedSupport);
            }
        }

        connectedSupportList.sort((x, y) -> y.getNumberOfRuns() - x.getNumberOfRuns());
        int sizeOfShow = Math.min(maxDisplay, connectedSupportList.size());

        return connectedSupportList.subList(0, sizeOfShow);
    }

    public static ConnectedSetBridge getConnectedSetBridge(List<Lottery> lotteries, int dayNumberBefore,
                                                           int findingDays, int maxDisplay) {
        if (lotteries.isEmpty()) return ConnectedSetBridge.getEmpty();
        List<ConnectedSupport> connectedSupports =
                getConnectedSupportList(lotteries, dayNumberBefore, findingDays, maxDisplay);
        Jackpot jackpot = dayNumberBefore == 0 ?
                Jackpot.getEmpty() : lotteries.get(dayNumberBefore - 1).getJackpot();
        return new ConnectedSetBridge(connectedSupports, new JackpotHistory(dayNumberBefore, jackpot));
    }

    // rất hiếm khi có các vị trí chạy thông quá dài nên giá trị của searchingDays thông thường = 10,
    // => giá trị an toàn của dayNumberBefore sẽ là lotteries.size() - searchingDays
    public static ConnectedBridge getConnectedBridge(List<Lottery> lotteries, int dayNumberBefore,
                                                     int searchingDays, int maxDisplay) {
        if (lotteries.isEmpty()) return ConnectedBridge.getEmpty();
        List<ConnectedSupport> connectedSupports =
                getConnectedSupportList(lotteries, dayNumberBefore, searchingDays, maxDisplay);
        Jackpot jackpot = dayNumberBefore == 0 ?
                Jackpot.getEmpty() : lotteries.get(dayNumberBefore - 1).getJackpot();
        return new ConnectedBridge(connectedSupports, new JackpotHistory(dayNumberBefore, jackpot));
    }

    public static List<ConnectedSupport> getConnectedSupportList(List<Lottery> lotteries, int dayNumberBefore,
                                                                 int findingDays, int maxDisplay) {
        if (lotteries.isEmpty()) return new ArrayList<>();
        List<DayPositions> dayPositionsList = getDayPositionsList(lotteries, dayNumberBefore, findingDays);
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
                ConnectedSupport connectedSupport = new ConnectedSupport(value, startPosition, typeList, times);
                connectedSupportList.add(connectedSupport);
            }
        }

        connectedSupportList.sort((x, y) -> y.getNumberOfRuns() - x.getNumberOfRuns());
        int sizeOfShow = Math.min(maxDisplay, connectedSupportList.size());

        return connectedSupportList.subList(0, sizeOfShow);
    }

    public static List<NumberSet> getConnectedSets(List<ConnectedSupport> connectedSupports) {
        if (connectedSupports.isEmpty()) return new ArrayList<>();
        List<Integer> combines = new ArrayList<>();
        List<Integer> touchs = getConnectedTouchs(connectedSupports);
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
        List<NumberSet> results = new ArrayList<>();
        for (int combine : combines) {
            for (int touch : touchs) {
                int small = CoupleBase.getSmallShadow(combine * 10 + touch);
                if (!smallSets.contains(small)) {
                    smallSets.add(small);
                    results.add(new NumberSet(combine, touch));
                }
            }
        }

        return results;
    }

    public static List<Integer> getConnectedTouchs(List<ConnectedSupport> connectedSupports) {
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

    public static List<TriadBridge> getTriadBridge(List<Lottery> lotteries, int dayNumberBefore,
                                                   int findingDays, int maxDisplay) {
        if (lotteries.isEmpty()) return new ArrayList<>();
        List<SupportTriad> supportTriadList = getSupportTriadList(lotteries,
                dayNumberBefore, findingDays, 50);

        List<TriadBridge> triadBridgeList = new ArrayList<>();
        for (int i = 0; i < supportTriadList.size(); i++) {
            List<Integer> firstStatus = supportTriadList.get(i).getStatusList();
            for (int j = i + 1; j < supportTriadList.size(); j++) {
                List<Integer> secondStatus = supportTriadList.get(j).getStatusList();
                for (int k = j + 1; k < supportTriadList.size(); k++) {
                    List<Integer> thirdStatus = supportTriadList.get(k).getStatusList();
                    int min = minThreeNumber(firstStatus.size(), secondStatus.size(), thirdStatus.size());
                    List<TriadStatus> triadStatusList = new ArrayList<>();
                    for (int t = 0; t < min; t++) {
                        int first = firstStatus.get(t);
                        int second = secondStatus.get(t);
                        int third = thirdStatus.get(t);
                        Couple couple = lotteries.get(dayNumberBefore + t).getJackpotCouple();
                        boolean isDouble = couple.isDoubleOrShadow();
                        if (checkThreeNumber(first, second, third, isDouble)) {
                            TriadStatus triadStatus = new TriadStatus(first, second, third);
                            triadStatusList.add(triadStatus);
                        } else {
                            break;
                        }
                    }
                    if (triadStatusList.size() > 2) {
                        Jackpot jackpot = dayNumberBefore == 0 ?
                                Jackpot.getEmpty() : lotteries.get(dayNumberBefore - 1).getJackpot();
                        TriadBridge triadBridge = new TriadBridge(supportTriadList.get(i), supportTriadList.get(j),
                                supportTriadList.get(k), triadStatusList, new JackpotHistory(dayNumberBefore, jackpot));
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

        results.sort(new Comparator<TriadBridge>() {
            @Override
            public int compare(TriadBridge o1, TriadBridge o2) {
                int size1 = o1.getTriadStatusList().size();
                int size2 = o2.getTriadStatusList().size();
                return Integer.compare(size2, size1);
            }
        });

        int sizeOfShow = Math.min(maxDisplay, results.size());
        return results.subList(0, sizeOfShow);
    }

    private static int minThreeNumber(int first, int second, int third) {
        int min = Math.min(first, second);
        return Math.min(min, third);
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

    public static List<SupportTriad> getSupportTriadList(List<Lottery> lotteries, int dayNumberBefore,
                                                         int findingDays, int maxDisplay) {
        if (lotteries.isEmpty()) return new ArrayList<>();
        List<DayPositions> dayPositionsList = getDayPositionsList(lotteries, dayNumberBefore, findingDays);

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
        positions.sort(new Comparator<Position>() {
            @Override
            public int compare(Position o1, Position o2) {
                return Integer.compare(o2.getType(), o1.getType());
            }
        });

        int sizeOfShowing = Math.min(positions.size(), maxDisplay);
        List<SupportTriad> supportTriadList = new ArrayList<>();
        Lottery lotteryThatDay = lotteries.get(dayNumberBefore);
        for (int i = 0; i < sizeOfShowing; i++) {
            Position position = positions.get(i);
            int value = lotteryThatDay.getValueAtPosition(position);
            List<Integer> statusList = getStatusList(lotteries, dayNumberBefore, findingDays, position);
            SupportTriad supportTriad = new SupportTriad(value, position, statusList);
            supportTriadList.add(supportTriad);
        }

        return supportTriadList;
    }

    public static List<TriadSets> getTriadSetsList(List<TriadBridge> triadBridgeList) {
        if (triadBridgeList.isEmpty()) return new ArrayList<>();
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
        triadSetsList.sort(new Comparator<TriadSets>() {
            @Override
            public int compare(TriadSets o1, TriadSets o2) {
                return Integer.compare(o2.getSize(), o1.getSize());
            }
        });
        return triadSetsList;
    }

    // các loại càng của jackpot đc đánh số: 54321
    public static List<ClawSupport> getClawSupport(List<Lottery> lotteries, int dayNumberBefore,
                                                   int findingDays, int maxDisplay, int clawType) {
        if (lotteries.isEmpty()) return new ArrayList<>();

        List<DayPositions> dayPositionsList = new ArrayList<>();
        int size = Math.min(findingDays, lotteries.size() - dayNumberBefore);
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
        positions.sort(new Comparator<Position>() {
            @Override
            public int compare(Position o1, Position o2) {
                return Integer.compare(o2.getType(), o1.getType());
            }
        });

        int sizeOfShowing = Math.min(positions.size(), maxDisplay);
        List<ClawSupport> clawSupportList = new ArrayList<>();
        Lottery lotteryThatDay = lotteries.get(dayNumberBefore);
        for (int i = 0; i < sizeOfShowing; i++) {
            Position position = positions.get(i);
            int value = lotteryThatDay.getValueAtPosition(position);
            List<Integer> beatList = getBeatListByClaw(lotteries, dayNumberBefore,
                    findingDays, position, clawType);
            Jackpot jackpot = dayNumberBefore == 0 ?
                    Jackpot.getEmpty() : lotteries.get(dayNumberBefore - 1).getJackpot();
            ClawSupport clawSupport = new ClawSupport(value, position, beatList,
                    new JackpotHistory(dayNumberBefore, jackpot));
            clawSupportList.add(clawSupport);
        }

        return clawSupportList;
    }

    private static List<DayPositions> getDayPositionsList(List<Lottery> lotteries, int dayNumberBefore,
                                                          int findingDays, boolean useShadow,
                                                          boolean addThirdClaw) {
        if (lotteries.size() < 2) return new ArrayList<>();
        int size = Math.min(findingDays, lotteries.size() - dayNumberBefore);
        List<DayPositions> dayPositionsList = new ArrayList<>();
        for (int i = 0; i < size - 1; i++) {
            int firstClaw = lotteries.get(dayNumberBefore + i).getClaw(1);
            int secondClaw = lotteries.get(dayNumberBefore + i).getClaw(2);
            int thirdClaw = lotteries.get(dayNumberBefore + i).getClaw(3);
            List<String> lotterySet = lotteries.get(dayNumberBefore + i + 1).getLottery();
            List<Position> positions = new ArrayList<>();
            for (int j = 0; j < lotterySet.size(); j++) {
                String numberStr = lotterySet.get(j);
                for (int k = 0; k < numberStr.length(); k++) {
                    int single = Integer.parseInt(numberStr.charAt(k) + "");
                    if (equalsWithShadow(single, firstClaw, useShadow)) {
                        positions.add(new Position(j, k, 1));
                    }
                    if (equalsWithShadow(single, secondClaw, useShadow)) {
                        positions.add(new Position(j, k, 2));
                    }
                    if (addThirdClaw && equalsWithShadow(single, thirdClaw, useShadow)) {
                        positions.add(new Position(j, k, 3));
                    }
                }
            }
            DayPositions dayPositions =
                    new DayPositions(lotteries.get(dayNumberBefore + i).getDateBase(), positions);
            dayPositionsList.add(dayPositions);
        }
        return dayPositionsList;
    }

    private static boolean equalsWithShadow(int first, int second, boolean useShadow) {
        if (!useShadow) return first == second;
        return first == second || first == SingleBase.getShadow(second);
    }

    private static List<DayPositions> getDayPositionsList(List<Lottery> lotteries,
                                                          int dayNumberBefore, int findingDays) {
        if (lotteries.size() < 2) return new ArrayList<>();
        int size = Math.min(findingDays, lotteries.size() - dayNumberBefore);
        List<DayPositions> dayPositionsList = new ArrayList<>();
        for (int i = 0; i < size - 1; i++) {
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

    private static List<Integer> getStatusList(List<Lottery> lotteries, int dayNumberBefore,
                                               int findingDays, Position position) {
        if (lotteries.isEmpty()) return new ArrayList<>();
        int size = Math.min(findingDays, lotteries.size() - dayNumberBefore);
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

    private static List<Integer> getBeatListByClaw(List<Lottery> lotteries, int dayNumberBefore,
                                                   int findingDays, Position position, int clawType) {
        if (lotteries.isEmpty()) return new ArrayList<>();
        int size = Math.min(findingDays, lotteries.size() - dayNumberBefore);
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
