package com.example.couple.ViewModel.Bridge;

import android.content.Context;

import com.example.couple.Custom.Const.Const;
import com.example.couple.Custom.Const.TimeInfo;
import com.example.couple.Custom.Handler.Bridge.ConnectedBridgeHandler;
import com.example.couple.Custom.Handler.Bridge.OtherBridgeHandler;
import com.example.couple.Custom.Handler.JackpotHandler;
import com.example.couple.Custom.Handler.LotteryHandler;
import com.example.couple.Model.Bridge.Connected.PairConnectedSupport;
import com.example.couple.Model.Bridge.Connected.TriadBridge;
import com.example.couple.Model.Bridge.NumberSet.NumberSet;
import com.example.couple.Model.Bridge.Touch.ConnectedBridge;
import com.example.couple.Model.Origin.Jackpot;
import com.example.couple.Model.Origin.Lottery;
import com.example.couple.Model.Bridge.Connected.ClawSupport;
import com.example.couple.Model.Bridge.TriadClaw.Single;
import com.example.couple.Model.Bridge.Connected.TriadSets;
import com.example.couple.Model.Bridge.Connected.TriangleConnectedSupport;
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
                JackpotHandler.getReverseJackpotListByDays(context, TimeInfo.DAY_OF_YEAR);
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
            List<NumberSet> numberSetList = new ArrayList<>();
            for (int j = 0; j < triadBridgeList.size(); j++) {
                numberSetList.addAll(triadBridgeList.get(j).getSetList());
            }
            for (int j = 0; j < numberSetList.size(); j++) {
                for (int k = j + 1; k < numberSetList.size(); k++) {
                    if (numberSetList.get(j).equalsSet(numberSetList.get(k))) {
                        numberSetList.remove(k);
                        k--;
                    }
                }
            }

            int count = 0;
            boolean isInList = false;
            int coupleInt = lotteries.get(i - 1).getJackpotCouple().getInt();
            for (int j = numberSetList.size() - 1; j >= 0; j--) {
                count++;
                if (numberSetList.get(j).isItMatch(coupleInt)) {
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

        List<NumberSet> mainNumberSets = new ArrayList<>();
        List<NumberSet> longestNumberSets = new ArrayList<>();
        for (int i = 0; i < triadBridgeList.size(); i++) {
            if (triadBridgeList.get(i).getTriadStatusList().size() > 6) {
                longestNumberSets.addAll(triadBridgeList.get(i).getSetList());
            } else {
                mainNumberSets.addAll(triadBridgeList.get(i).getSetList());
            }
        }

        for (int i = 0; i < mainNumberSets.size(); i++) {
            for (int j = i + 1; j < mainNumberSets.size(); j++) {
                if (mainNumberSets.get(i).equalsSet(mainNumberSets.get(j))) {
                    mainNumberSets.remove(j);
                    j--;
                }
            }
        }

        for (int i = 0; i < longestNumberSets.size(); i++) {
            for (int j = i + 1; j < longestNumberSets.size(); j++) {
                if (longestNumberSets.get(i).equalsSet(longestNumberSets.get(j))) {
                    longestNumberSets.remove(j);
                    j--;
                }
            }
        }

        for (int i = 0; i < mainNumberSets.size(); i++) {
            for (int j = 0; j < longestNumberSets.size(); j++) {
                if (mainNumberSets.get(i).equalsSet(longestNumberSets.get(j))) {
                    mainNumberSets.remove(i);
                    i--;
                    break;
                }
            }
        }

        // xử lý cho cancelSets

        List<NumberSet> cancelNumberSets = new ArrayList<>();
        for (int i = 0; i < cancelBridgeList.size(); i++) {
            cancelNumberSets.addAll(cancelBridgeList.get(i).getSetList());
        }

        for (int i = 0; i < cancelNumberSets.size(); i++) {
            for (int j = i + 1; j < cancelNumberSets.size(); j++) {
                if (cancelNumberSets.get(i).equalsSet(cancelNumberSets.get(j))) {
                    cancelNumberSets.remove(j);
                    j--;
                }
            }
        }

        for (int i = 0; i < cancelNumberSets.size(); i++) {
            for (int j = 0; j < mainNumberSets.size(); j++) {
                if (cancelNumberSets.get(i).equalsSet(mainNumberSets.get(j))) {
                    cancelNumberSets.remove(i);
                    i--;
                    break;
                }
            }
        }

        for (int i = 0; i < cancelNumberSets.size(); i++) {
            for (int j = 0; j < longestNumberSets.size(); j++) {
                if (cancelNumberSets.get(i).equalsSet(longestNumberSets.get(j))) {
                    cancelNumberSets.remove(i);
                    i--;
                    break;
                }
            }
        }

        findingBridgeView.showTriadBridgeWithCondition(triadBridgeList,
                mainNumberSets, longestNumberSets, cancelNumberSets, enoughTouchs);
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
        List<Single> singles = OtherBridgeHandler.getTouchsByThirdClawBridge(jackpotList, dayNumberBefore);
        findingBridgeView.showJackpotThirdClawBridge(singles, jackpotList.size());
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
