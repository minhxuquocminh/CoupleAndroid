package com.example.couple.Custom.Handler.Bridge;

import com.example.couple.Model.Bridge.LongBeat.AfterDoubleBridge;
import com.example.couple.Model.Display.BCouple;
import com.example.couple.Model.Display.BSingle;
import com.example.couple.Model.Origin.Couple;
import com.example.couple.Model.Origin.Jackpot;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class BCoupleBridgeHandler {

    public static List<AfterDoubleBridge> GetAfterDoubleBridges(List<Jackpot> jackpotList) {
        List<AfterDoubleBridge> bridges = new ArrayList<>();
        List<AfterDoubleBridge> checkList = new ArrayList<>();
        int maxSize = jackpotList.size() < 30 ? jackpotList.size() : 30;
        for (int i = 0; i < maxSize - 2; i++) {
            if (jackpotList.get(i).getCouple().isDoubleAndShadow()) {
                List<Couple> lastDoubleRange = new ArrayList<>();
                lastDoubleRange.add(jackpotList.get(i + 2).getCouple());
                lastDoubleRange.add(jackpotList.get(i + 1).getCouple());
                lastDoubleRange.add(jackpotList.get(i).getCouple());
                if (i - 1 >= 0) {
                    lastDoubleRange.add(jackpotList.get(i - 1).getCouple());
                }
                checkList.add(new AfterDoubleBridge(lastDoubleRange, i));
            }
        }
        for (AfterDoubleBridge bridge : checkList) {
            for (int i = bridge.getDayNumberBefore(); i >= 0; i--) {
                for (int j = 1; j <= 4; j++) {
                    if (bridge.getSetMap().containsKey(j) &&
                            bridge.getSetMap().get(j).isItMatch(jackpotList.get(i).getCoupleInt())) {
                        bridge.getSetMap().remove(j);
                    }
                }
            }
            if (!bridge.getSetMap().isEmpty()) {
                bridges.add(bridge);
            }
        }
        return bridges;
    }

    public static List<BSingle> GetTouchBridge(List<Jackpot> jackpotList) {
        if (jackpotList.size() < 2) return new ArrayList<>();
        List<BSingle> touchList = new ArrayList<>();
        BCouple cp1 = jackpotList.get(1).getBCouple();
        BCouple cp0 = jackpotList.get(0).getBCouple();
        List<BCouple> BCouples = GetBalanceCouples(cp1, cp0);
        for (int i = 0; i < BCouples.size(); i++) {
            int first = BCouples.get(i).getFirst();
            int second = BCouples.get(i).getSecond();
            BSingle BSingle = new BSingle(second, 1);
            touchList.add(BSingle);
            int number;
            int level;
            if (first >= 0) {
                number = first;
                level = 2;
            } else {
                number = Math.abs(first);
                level = 3;
            }
            BSingle BSingle2 = new BSingle(number, level);
            touchList.add(BSingle2);
        }

        Collections.sort(touchList, new Comparator<BSingle>() {
            @Override
            public int compare(BSingle o1, BSingle o2) {
                if (o1.getNumber() > o2.getNumber()) {
                    return 1;
                } else if (o1.getNumber() == o2.getNumber()) {
                    return 0;
                } else {
                    return -1;
                }
            }
        });

        for (int i = 0; i < touchList.size() - 1; i++) {
            BSingle BSingle1 = touchList.get(i);
            BSingle BSingle2 = touchList.get(i + 1);
            if (BSingle1.getNumber() == BSingle2.getNumber()) {
                if (BSingle1.getLevel() < BSingle2.getLevel()) {
                    touchList.remove(i + 1);
                    i--;
                } else {
                    touchList.remove(i);
                    i--;
                }
            }
        }

        return touchList;
    }

    public static List<Integer> GetSpecialTouchBridge(List<Jackpot> jackpotList) {
        if (jackpotList.size() < 4) return new ArrayList<>();
        BCouple cp3 = jackpotList.get(3).getBCouple();
        BCouple cp2 = jackpotList.get(2).getBCouple();
        BCouple cp1 = jackpotList.get(1).getBCouple();
        BCouple cp0 = jackpotList.get(0).getBCouple();
        List<BCouple> couples1 = BCoupleBridgeHandler.GetBalanceCouples(cp3, cp2);
        List<BCouple> couples2 = BCoupleBridgeHandler.GetBalanceCouples(cp1, cp0);
        List<Integer> balance1 = new ArrayList<>();
        List<Integer> balance2 = new ArrayList<>();
        for (int i = 0; i < couples1.size(); i++) {
            balance1.add(Math.abs(couples1.get(i).getFirst()));
            balance1.add(couples1.get(i).getSecond());
            balance2.add(Math.abs(couples2.get(i).getFirst()));
            balance2.add(couples2.get(i).getSecond());
        }

        int[] arr = new int[10];
        for (int i = 0; i < balance1.size(); i++) {
            for (int j = 0; j < balance2.size(); j++) {
                if (balance1.get(i) == balance2.get(j)) {
                    arr[balance1.get(i)]++;
                }
            }
        }

        List<Integer> touchList = new ArrayList<>();
        for (int i = 0; i < arr.length; i++) {
            if (arr[i] > 0) {
                touchList.add(i);
            }
        }
        Collections.sort(touchList);
        return touchList;
    }

    public static List<BCouple> GetBalanceCouples(BCouple bcp1, BCouple bcp2) {
        List<BCouple> results = new ArrayList<>();
        results.add(bcp1.balanceOne(bcp2));
        results.add(bcp1.balanceTwo(bcp2));
        results.add(bcp1.balanceThree(bcp2));
        results.add(bcp1.balanceFour(bcp2));
        return results;
    }
}