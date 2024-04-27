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
    void showMessage(String message);
    void showLotteryList(List<Lottery> lotteries);
    void showJackpotList(List<Jackpot> jackpotList);
    void showConnectedBridge(ConnectedBridge connectedBridge, String jackpotThatDay);
    void showTriadBridge(List<TriadBridge> triadBridgeList);
    void showTriadBridgeWithCondition(List<TriadBridge> triadBridgeList, List<Set> mainSets,
                                      List<Set> longestSets, List<Set> cancelSets, int enoughTouchs);
    void showFirstClawBridge(List<ClawSupport> clawSupportList);
    void showSecondClawBridge(List<ClawSupport> clawSupportList);
    void showThirdClawBridge(List<ClawSupport> clawSupportList);
    void showJackpotThirdClawBridge(List<BSingle> BSingleList, int frame);
    void showThreeSetBridgeStatus(List<Integer> statusList);

    void showTest(List<TriangleConnectedSupport> connectedBridge);

    void showTest2(List<PairConnectedSupport> supports);
}
