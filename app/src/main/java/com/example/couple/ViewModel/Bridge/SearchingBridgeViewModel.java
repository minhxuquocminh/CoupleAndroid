package com.example.couple.ViewModel.Bridge;

import android.content.Context;

import com.example.couple.Custom.Const.Const;
import com.example.couple.Custom.Handler.Bridge.ConnectedBridgeHandler;
import com.example.couple.Custom.Handler.Bridge.OtherBridgeHandler;
import com.example.couple.Custom.Handler.JackpotHandler;
import com.example.couple.Custom.Handler.LotteryHandler;
import com.example.couple.Model.Bridge.Couple.TriadBridge;
import com.example.couple.Model.Bridge.Single.ConnectedBridge;
import com.example.couple.Model.Display.BSingle;
import com.example.couple.Model.Display.Set;
import com.example.couple.Model.Origin.Couple;
import com.example.couple.Model.Origin.Jackpot;
import com.example.couple.Model.Origin.Lottery;
import com.example.couple.Model.Support.ClawSupport;
import com.example.couple.Model.Support.PairConnectedSupport;
import com.example.couple.Model.Support.TriadSets;
import com.example.couple.Model.Support.TriangleConnectedSupport;
import com.example.couple.View.Bridge.SearchingBridgeView;

import java.util.ArrayList;
import java.util.List;

public class SearchingBridgeViewModel {
    SearchingBridgeView searchingBridgeView;
    Context context;

    public SearchingBridgeViewModel(SearchingBridgeView searchingBridgeView, Context context) {
        this.searchingBridgeView = searchingBridgeView;
        this.context = context;
    }

    public void GetLotteryListAndJackpotList() {
        List<Lottery> lotteries =
                LotteryHandler.getLotteryListFromFile(context, Const.MAX_DAYS_TO_GET_LOTTERY);
        if (lotteries.size() == 0) {
            searchingBridgeView.ShowError("Lỗi không lấy được thông tin XSMB!");
        } else {
            searchingBridgeView.ShowLotteryList(lotteries);
        }
        List<Jackpot> jackpotList =
                JackpotHandler.GetReserveJackpotListFromFile(context, Const.DAY_OF_YEAR);
        if (jackpotList.size() == 0) {
            searchingBridgeView.ShowError("Lỗi không lấy được thông tin XS Đặc biệt!");
        } else {
            searchingBridgeView.ShowJackpotList(jackpotList);
        }
    }

    public boolean GetConnectedBridge(List<Lottery> lotteries, int findingDays, int dayNumberBefore) {
        ConnectedBridge connectedBridge = ConnectedBridgeHandler.GetConnectedBridge(lotteries,
                dayNumberBefore, findingDays, Const.CONNECTED_BRIDGE_MAX_DISPLAY);
        if (dayNumberBefore < lotteries.size()) {
            String jackpotThatDay = "";
            if (dayNumberBefore - 1 < 0) {
                jackpotThatDay = "?????";
            } else {
                jackpotThatDay = lotteries.get(dayNumberBefore - 1).getJackpotString();
            }
            searchingBridgeView.ShowConnectedBridge(connectedBridge, jackpotThatDay);
        }
        return !connectedBridge.getConnectedSupports().isEmpty();
    }

    public boolean GetTriadBridge(List<Lottery> lotteries, int findingDays,
                                  int dayNumberBefore) {
        List<TriadBridge> triadBridges = ConnectedBridgeHandler.
                GetTriadBridge(lotteries, findingDays, dayNumberBefore, Const.TRIAD_SET_BRIDGE_MAX_DISPLAY);
        searchingBridgeView.ShowTriadBridge(triadBridges);
        return triadBridges.size() != 0;
    }

    public void GetThreeSetBridgeStatus(List<Lottery> lotteries, int findingDays, int dayNumberBefore) {
        List<Integer> statusList = new ArrayList<>();
        for (int i = 1; i <= dayNumberBefore; i++) {
            List<TriadBridge> triadBridgeList = ConnectedBridgeHandler.
                    GetTriadBridge(lotteries, findingDays, i, Const.TRIAD_SET_BRIDGE_MAX_DISPLAY);
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
            Couple couple = lotteries.get(i - 1).getJackpotCouple();
            for (int j = setList.size() - 1; j >= 0; j--) {
                count++;
                if (setList.get(j).isItMatch(couple)) {
                    statusList.add(count);
                    isInList = true;
                    break;
                }
            }
            if (!isInList) {
                statusList.add(0);
            }
        }
        searchingBridgeView.ShowThreeSetBridgeStatus(statusList);
    }

    public void GetTriadBridgeWithCondition(List<TriadBridge> allTriadBridges,
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
            List<TriadSets> triadSetsList = ConnectedBridgeHandler.GetTriadSetsList(cacheTriadList);
            for (int i = 0; i < triadSetsList.size(); i++) {
                triadBridgeList.addAll(triadSetsList.get(i).getTriadBridgeList());
            }
        }

        // xử lý cho mainSets và longestSets

        List<Set> mainSets = new ArrayList<>();
        List<Set> longestSets = new ArrayList<>();
        for (int i = 0; i < triadBridgeList.size(); i++) {
            if (triadBridgeList.get(i).getStatusList().size() > 6) {
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

        searchingBridgeView.ShowTriadBridgeWithCondition(triadBridgeList,
                mainSets, longestSets, cancelSets, enoughTouchs);
    }

    public boolean FindingFirstClawBridge(List<Lottery> lotteries, int findingDays, int dayNumberBefore) {
        List<ClawSupport> clawSupportList = ConnectedBridgeHandler.GetClawSupport(lotteries,
                findingDays, dayNumberBefore, 1, Const.CLAW_BRIDGE_MAX_DISPLAY);
        searchingBridgeView.ShowFirstClawBridge(clawSupportList);
        return clawSupportList.size() != 0;
    }

    public boolean FindingSecondClawBridge(List<Lottery> lotteries, int findingDays, int dayNumberBefore) {
        List<ClawSupport> clawSupportList = ConnectedBridgeHandler.GetClawSupport(lotteries,
                findingDays, dayNumberBefore, 2, Const.CLAW_BRIDGE_MAX_DISPLAY);
        searchingBridgeView.ShowSecondClawBridge(clawSupportList);
        return clawSupportList.size() != 0;
    }

    public boolean FindingThirdClawBridge(List<Lottery> lotteries, int findingDays, int dayNumberBefore) {
        List<ClawSupport> clawSupportList = ConnectedBridgeHandler.GetClawSupport(lotteries,
                findingDays, dayNumberBefore, 3, Const.CLAW_BRIDGE_MAX_DISPLAY);
        searchingBridgeView.ShowThirdClawBridge(clawSupportList);
        return clawSupportList.size() != 0;
    }

    public void FindingJackpotThirdClawBridge(List<Jackpot> jackpotList, int dayNumberBefore) {
        List<BSingle> BSingleList = OtherBridgeHandler.GetTouchsByThirdClawBridge(jackpotList, dayNumberBefore);
        searchingBridgeView.ShowJackpotThirdClawBridge(BSingleList, jackpotList.size());
    }

    public void Test(List<Lottery> lotteries, int findingDays, int dayNumberBefore) {
        List<TriangleConnectedSupport> connectedBridge = ConnectedBridgeHandler.GetTriangleConnectedSupports(lotteries,
                dayNumberBefore, findingDays, Const.CONNECTED_BRIDGE_MAX_DISPLAY,true);
       if(!connectedBridge.isEmpty())
            searchingBridgeView.ShowTest(connectedBridge);
    }

    public void Test2(List<Lottery> lotteries, int findingDays, int dayNumberBefore) {
        List<PairConnectedSupport> supports = ConnectedBridgeHandler.GetPairConnectedSupports(lotteries,
                dayNumberBefore, findingDays, Const.CONNECTED_BRIDGE_MAX_DISPLAY,true);
        if(!supports.isEmpty())
            searchingBridgeView.ShowTest2(supports);
    }
}
