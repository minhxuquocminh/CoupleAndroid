package com.example.couple.View.Bridge;

import com.example.couple.Model.Bridge.Cycle.BranchInTwoDaysBridge;
import com.example.couple.Model.Bridge.Connected.ConnectedSetBridge;
import com.example.couple.Model.Bridge.Connected.TriadBridge;
import com.example.couple.Model.Bridge.Double.AfterDoubleBridge;
import com.example.couple.Model.Bridge.Double.AfterDoubleExtendBridge;
import com.example.couple.Model.Bridge.Cycle.BranchInDayBridge;
import com.example.couple.Model.Bridge.Double.SignOfDouble;
import com.example.couple.Model.Bridge.NumberSet.NumericSetHistory;
import com.example.couple.Model.Origin.Jackpot;
import com.example.couple.Model.Origin.Lottery;

import java.util.List;

public interface SelectiveBridgeView {
    void showMessage(String message);
    void showJackpotList(List<Jackpot> jackpotList);
    void showLotteryList(List<Lottery> lotteries);
    void showAfterDoubleExtendBridge(List<AfterDoubleExtendBridge> bridges);
    void showAfterDoubleBridge(List<AfterDoubleBridge> bridges);
    void showLongBeatBridge(List<NumericSetHistory> histories);
    void showConnectedTouchs(List<Integer> touchs);
    void showBranchInTwoDaysBridge(BranchInTwoDaysBridge bridge);
    void showShadowTouchs(List<Integer> touchs);
    void showSignOfDouble(SignOfDouble sign);
    void showBranchInDayBridge(BranchInDayBridge bridge);
    void showConnectedSetBridge(ConnectedSetBridge bridge);
    void showTriadSetBridge(List<TriadBridge> bridges);
}
