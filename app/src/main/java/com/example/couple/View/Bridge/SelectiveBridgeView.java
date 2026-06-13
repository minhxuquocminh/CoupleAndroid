package com.example.couple.View.Bridge;

import com.example.couple.Model.Bridge.Cycle.BranchInTwoDaysBridge;
import com.example.couple.Model.Bridge.AfterDouble.AfterDoubleSetMapping;
import com.example.couple.Model.Bridge.Cycle.BranchInDayBridge;
import com.example.couple.Model.Bridge.Double.SignOfDouble;
import com.example.couple.Model.Bridge.NumberSet.NumberSetHistory;
import com.example.couple.Model.Origin.Jackpot;
import com.example.couple.Model.Origin.Lottery;

import java.util.List;

public interface SelectiveBridgeView {
    void showMessage(String message);
    void showJackpotList(List<Jackpot> jackpotList);
    void showLotteryList(List<Lottery> lotteries);
    void showAfterDoubleSetMappings(List<AfterDoubleSetMapping> bridges);
    void showLongBeatBridge(List<NumberSetHistory> histories);
    void showBranchInTwoDaysBridge(BranchInTwoDaysBridge bridge);
    void showSignOfDouble(SignOfDouble sign);
    void showBranchInDayBridge(BranchInDayBridge bridge);
}
