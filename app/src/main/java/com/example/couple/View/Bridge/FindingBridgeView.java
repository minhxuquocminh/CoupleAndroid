package com.example.couple.View.Bridge;


import com.example.couple.Model.Support.ClawSupport;
import com.example.couple.Model.Bridge.Single.ConnectedBridge;
import com.example.couple.Model.Bridge.Couple.TriadBridge;
import com.example.couple.Model.Display.Set;
import com.example.couple.Model.Display.BSingle;
import com.example.couple.Model.Origin.Jackpot;
import com.example.couple.Model.Origin.Lottery;
import com.example.couple.Model.Support.PairConnectedSupport;
import com.example.couple.Model.Support.TriangleConnectedSupport;

import java.util.List;

public interface FindingBridgeView {
    void ShowError(String message);
    void ShowLotteryList(List<Lottery> lotteries);
    void ShowJackpotList(List<Jackpot> jackpotList);
    void ShowConnectedBridge(ConnectedBridge connectedBridge, String jackpotThatDay);
    void ShowTriadBridge(List<TriadBridge> triadBridgeList);
    void ShowTriadBridgeWithCondition(List<TriadBridge> triadBridgeList, List<Set> mainSets,
                                      List<Set> longestSets, List<Set> cancelSets, int enoughTouchs);
    void ShowFirstClawBridge(List<ClawSupport> clawSupportList);
    void ShowSecondClawBridge(List<ClawSupport> clawSupportList);
    void ShowThirdClawBridge(List<ClawSupport> clawSupportList);
    void ShowJackpotThirdClawBridge(List<BSingle> BSingleList, int frame);
    void ShowThreeSetBridgeStatus(List<Integer> statusList);

    void ShowTest(List<TriangleConnectedSupport> connectedBridge);

    void ShowTest2(List<PairConnectedSupport> supports);
}
