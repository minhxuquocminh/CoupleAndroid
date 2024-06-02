package com.example.couple.ViewModel.Bridge;

import android.content.Context;

import com.example.couple.Custom.Const.Const;
import com.example.couple.Custom.Const.TimeInfo;
import com.example.couple.Custom.Handler.Bridge.ConnectedBridgeHandler;
import com.example.couple.Custom.Handler.Bridge.OtherBridgeHandler;
import com.example.couple.Custom.Handler.JackpotHandler;
import com.example.couple.Custom.Handler.LotteryHandler;
import com.example.couple.Model.Bridge.Couple.TriadBridge;
import com.example.couple.Model.Bridge.Single.ConnectedBridge;
import com.example.couple.Model.Display.BSingle;
import com.example.couple.Model.Display.Set;
import com.example.couple.Model.Origin.Jackpot;
import com.example.couple.Model.Origin.Lottery;
import com.example.couple.Model.Support.ClawSupport;
import com.example.couple.Model.Support.PairConnectedSupport;
import com.example.couple.Model.Support.TriadSets;
import com.example.couple.Model.Support.TriangleConnectedSupport;
import com.example.couple.View.Bridge.FindingBridgeView;

import java.util.ArrayList;
import java.util.List;

public class FindingBridgeViewModel {
    FindingBridgeView findingBridgeView;
    Context context;

    public FindingBridgeViewModel(FindingBridgeView findingBridgeView, Context context) {
        this.findingBridgeView = findingBridgeView;
        this.context = context;
    }

    public void getLotteryListAndJackpotList() {
        List<Lottery> lotteries =
                LotteryHandler.getLotteryListFromFile(context, Const.MAX_DAYS_TO_GET_LOTTERY);
        if (lotteries.isEmpty()) {
            findingBridgeView.showMessage("Lỗi không lấy được thông tin XSMB!");
        } else {
            findingBridgeView.showLotteryList(lotteries);
        }
        List<Jackpot> jackpotList =
                JackpotHandler.getReserveJackpotListFromFile(context, TimeInfo.DAY_OF_YEAR);
        if (jackpotList.isEmpty()) {
            findingBridgeView.showMessage("Lỗi không lấy được thông tin XS Đặc biệt!");
        } else {
            findingBridgeView.showJackpotList(jackpotList);
        }
    }

    public boolean getConnectedBridge(List<Lottery> lotteries, int findingDays, int dayNumberBefore) {
        ConnectedBridge connectedBridge = ConnectedBridgeHandler.getConnectedBridge(lotteries,
                dayNumberBefore, findingDays, Const.CONNECTED_BRIDGE_MAX_DISPLAY);
        if (dayNumberBefore < lotteries.size()) {
            String jackpotThatDay = "";
            if (dayNumberBefore - 1 < 0) {
                jackpotThatDay = "?????";
            } else {
                jackpotThatDay = lotteries.get(dayNumberBefore - 1).getJackpotString();
            }
            findingBridgeView.showConnectedBridge(connectedBridge, jackpotThatDay);
        }
        return !connectedBridge.getConnectedSupports().isEmpty();
    }

    public boolean getTriadBridge(List<Lottery> lotteries, int findingDays,
                                  int dayNumberBefore) {
        List<TriadBridge> triadBridges = ConnectedBridgeHandler.
                getTriadBridge(lotteries, dayNumberBefore, findingDays, Const.TRIAD_SET_BRIDGE_MAX_DISPLAY);
        findingBridgeView.showTriadBridge(triadBridges);
        return !triadBridges.isEmpty();
    }

    public void getThreeSetBridgeStatus(List<Lottery> lotteries, int findingDays, int dayNumberBefore) {
        List<Integer> statusList = new ArrayList<>();
        for (int i = 1; i <= dayNumberBefore; i++) {
            List<TriadBridge> triadBridgeList = ConnectedBridgeHandler.
                    getTriadBridge(lotteries, i, findingDays, Const.TRIAD_SET_BRIDGE_MAX_DISPLAY);
            List<Set> setList = new ArrayList<>();
            for (int j = 0; j < triadBridgeList.size(); j++) {
                setList.addAll(triadBridgeList.get(j).getSetList());
            }
            for (int j = 0; j < setList.size(); j++) {
                for (int k = j + 1; k < setList.size(); k++) {
                    if (setList.get(j).equalsSet(setList.get(k))) {
                        setList.remove(k);
                        k--;
                    }
                }
            }

            int count = 0;
            boolean isInList = false;
            int coupleInt = lotteries.get(i - 1).getJackpotCouple().getInt();
            for (int j = setList.size() - 1; j >= 0; j--) {
                count++;
                if (setList.get(j).isItMatch(coupleInt)) {
                    statusList.add(count);
                    isInList = true;
                    break;
                }
            }
            if (!isInList) {
                statusList.add(0);
            }
        }
        findingBridgeView.showThreeSetBridgeStatus(statusList);
    }

    public void getTriadBridgeWithCondition(List<TriadBridge> allTriadBridges,
                                            int enoughTouchs, boolean sortBySet) {
        List<TriadBridge> triadBridgeList = new ArrayList<>();
        List<TriadBridge> cancelBridgeList = new ArrayList<>();
        if (!sortBySet) {
            for (int i = 0; i < allTriadBridges.size(); i++) {
                if (allTriadBridges.get(i).getEnoughTouchs() >= enoughTouchs) {
                    triadBridgeList.add(allTriadBridges.get(i));
                } else {
                    if (enoughTouchs - 1 >= 0 && allTriadBridges.get(i).getEnoughTouchs() == enoughTouchs - 1) {
                        cancelBridgeList.add(allTriadBridges.get(i));
                    }
                }
            }
        } else {
            List<TriadBridge> cacheTriadList = new ArrayList<>();
            for (int i = 0; i < allTriadBridges.size(); i++) {
                if (allTriadBridges.get(i).getEnoughTouchs() >= enoughTouchs) {
                    cacheTriadList.add(allTriadBridges.get(i));
                } else {
                    if (enoughTouchs - 1 >= 0 && allTriadBridges.get(i).getEnoughTouchs() == enoughTouchs - 1) {
                        cancelBridgeList.add(allTriadBridges.get(i));
                    }
                }
            }
            List<TriadSets> triadSetsList = ConnectedBridgeHandler.getTriadSetsList(cacheTriadList);
            for (int i = 0; i < triadSetsList.size(); i++) {
                triadBridgeList.addAll(triadSetsList.get(i).getTriadBridgeList());
            }
        }

        // xử lý cho mainSets và longestSets

        List<Set> mainSets = new ArrayList<>();
        List<Set> longestSets = new ArrayList<>();
        for (int i = 0; i < triadBridgeList.size(); i++) {
            if (triadBridgeList.get(i).getTriadStatusList().size() > 6) {
                longestSets.addAll(triadBridgeList.get(i).getSetList());
            } else {
                mainSets.addAll(triadBridgeList.get(i).getSetList());
            }
        }

        for (int i = 0; i < mainSets.size(); i++) {
            for (int j = i + 1; j < mainSets.size(); j++) {
                if (mainSets.get(i).equalsSet(mainSets.get(j))) {
                    mainSets.remove(j);
                    j--;
                }
            }
        }

        for (int i = 0; i < longestSets.size(); i++) {
            for (int j = i + 1; j < longestSets.size(); j++) {
                if (longestSets.get(i).equalsSet(longestSets.get(j))) {
                    longestSets.remove(j);
                    j--;
                }
            }
        }

        for (int i = 0; i < mainSets.size(); i++) {
            for (int j = 0; j < longestSets.size(); j++) {
                if (mainSets.get(i).equalsSet(longestSets.get(j))) {
                    mainSets.remove(i);
                    i--;
                    break;
                }
            }
        }

        // xử lý cho cancelSets

        List<Set> cancelSets = new ArrayList<>();
        for (int i = 0; i < cancelBridgeList.size(); i++) {
            cancelSets.addAll(cancelBridgeList.get(i).getSetList());
        }

        for (int i = 0; i < cancelSets.size(); i++) {
            for (int j = i + 1; j < cancelSets.size(); j++) {
                if (cancelSets.get(i).equalsSet(cancelSets.get(j))) {
                    cancelSets.remove(j);
                    j--;
                }
            }
        }

        for (int i = 0; i < cancelSets.size(); i++) {
            for (int j = 0; j < mainSets.size(); j++) {
                if (cancelSets.get(i).equalsSet(mainSets.get(j))) {
                    cancelSets.remove(i);
                    i--;
                    break;
                }
            }
        }

        for (int i = 0; i < cancelSets.size(); i++) {
            for (int j = 0; j < longestSets.size(); j++) {
                if (cancelSets.get(i).equalsSet(longestSets.get(j))) {
                    cancelSets.remove(i);
                    i--;
                    break;
                }
            }
        }

        findingBridgeView.showTriadBridgeWithCondition(triadBridgeList,
                mainSets, longestSets, cancelSets, enoughTouchs);
    }

    public boolean findingFirstClawBridge(List<Lottery> lotteries, int findingDays, int dayNumberBefore) {
        List<ClawSupport> clawSupportList = ConnectedBridgeHandler.getClawSupport(lotteries,
                dayNumberBefore, findingDays, Const.CLAW_BRIDGE_MAX_DISPLAY, 1);
        findingBridgeView.showFirstClawBridge(clawSupportList);
        return !clawSupportList.isEmpty();
    }

    public boolean findingSecondClawBridge(List<Lottery> lotteries, int findingDays, int dayNumberBefore) {
        List<ClawSupport> clawSupportList = ConnectedBridgeHandler.getClawSupport(lotteries,
                dayNumberBefore, findingDays, Const.CLAW_BRIDGE_MAX_DISPLAY, 2);
        findingBridgeView.showSecondClawBridge(clawSupportList);
        return !clawSupportList.isEmpty();
    }

    public boolean findingThirdClawBridge(List<Lottery> lotteries, int findingDays, int dayNumberBefore) {
        List<ClawSupport> clawSupportList = ConnectedBridgeHandler.getClawSupport(lotteries,
                dayNumberBefore, findingDays, Const.CLAW_BRIDGE_MAX_DISPLAY, 3);
        findingBridgeView.showThirdClawBridge(clawSupportList);
        return !clawSupportList.isEmpty();
    }

    public void findingJackpotThirdClawBridge(List<Jackpot> jackpotList, int dayNumberBefore) {
        List<BSingle> BSingleList = OtherBridgeHandler.getTouchsByThirdClawBridge(jackpotList, dayNumberBefore);
        findingBridgeView.showJackpotThirdClawBridge(BSingleList, jackpotList.size());
    }

    public void test(List<Lottery> lotteries, int findingDays, int dayNumberBefore) {
        List<TriangleConnectedSupport> connectedBridge = ConnectedBridgeHandler.getTriangleConnectedSupports(lotteries,
                dayNumberBefore, findingDays, Const.CONNECTED_BRIDGE_MAX_DISPLAY, true);
        if (!connectedBridge.isEmpty())
            findingBridgeView.showTest(connectedBridge);
    }

    public void test2(List<Lottery> lotteries, int findingDays, int dayNumberBefore) {
        List<PairConnectedSupport> supports = ConnectedBridgeHandler.getPairConnectedSupports(lotteries,
                dayNumberBefore, findingDays, Const.CONNECTED_BRIDGE_MAX_DISPLAY, true);
        if (!supports.isEmpty())
            findingBridgeView.showTest2(supports);
    }
}
