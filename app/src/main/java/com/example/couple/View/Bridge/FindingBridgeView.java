package com.example.couple.View.Bridge;


import com.example.couple.Model.Bridge.Connected.PairConnectedSupport;
import com.example.couple.Model.Bridge.Connected.TriadBridge;
import com.example.couple.Model.Bridge.NumberSet.SetBase;
import com.example.couple.Model.Bridge.Touch.ConnectedBridge;
import com.example.couple.Model.Origin.Jackpot;
import com.example.couple.Model.Origin.Lottery;
import com.example.couple.Model.Bridge.Connected.ClawSupport;
import com.example.couple.Model.Bridge.TriadClaw.Single;
import com.example.couple.Model.Bridge.Connected.TriangleConnectedSupport;

import java.util.List;

public interface FindingBridgeView {
    void showMessage(String message);
    void showLotteryList(List<Lottery> lotteries);
    void showJackpotList(List<Jackpot> jackpotList);
    void showConnectedBridge(ConnectedBridge connectedBridge, String jackpotThatDay);
    void showTriadBridge(List<TriadBridge> triadBridgeList);
    void showTriadBridgeWithCondition(List<TriadBridge> triadBridgeList, List<SetBase> mainSetBases,
                                      List<SetBase> longestSetBases, List<SetBase> cancelSetBases, int enoughTouchs);
    void showFirstClawBridge(List<ClawSupport> clawSupportList);
    void showSecondClawBridge(List<ClawSupport> clawSupportList);
    void showThirdClawBridge(List<ClawSupport> clawSupportList);
    void showJackpotThirdClawBridge(List<Single> singles, int frame);
    void showThreeSetBridgeStatus(List<Integer> statusList);

    void showTest(List<TriangleConnectedSupport> connectedBridge);

    void showTest2(List<PairConnectedSupport> supports);
}
