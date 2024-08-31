package com.example.couple.View.Bridge;

import com.example.couple.Model.Bridge.Couple.BranchInTwoDaysBridge;
import com.example.couple.Model.Bridge.Couple.ConnectedSetBridge;
import com.example.couple.Model.Bridge.Couple.TriadBridge;
import com.example.couple.Model.Bridge.LongBeat.AfterDoubleBridge;
import com.example.couple.Model.Bridge.LongBeat.BranchInDayBridge;
import com.example.couple.Model.Bridge.Sign.SignOfDouble;
import com.example.couple.Model.History.NumberSetHistory;
import com.example.couple.Model.Origin.Jackpot;
import com.example.couple.Model.Origin.Lottery;

import java.util.List;

public interface SelectiveBridgeView {
    void showMessage(String message);
    void showJackpotList(List<Jackpot> jackpotList);
    void showLotteryList(List<Lottery> lotteries);
    void showAfterDoubleBridge(List<AfterDoubleBridge> bridges);
    void showLongBeatBridge(List<NumberSetHistory> histories);
    void showConnectedTouchs(List<Integer> touchs);
    void showBranchInTwoDaysBridge(BranchInTwoDaysBridge bridge);
    void showShadowTouchs(List<Integer> touchs);
    void showSignOfDouble(SignOfDouble sign);
    void showBranchInDayBridge(BranchInDayBridge bridge);
    void showConnectedSetBridge(ConnectedSetBridge bridge);
    void showTriadSetBridge(List<TriadBridge> bridges);
}
