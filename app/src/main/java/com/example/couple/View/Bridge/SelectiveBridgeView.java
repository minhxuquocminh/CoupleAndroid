package com.example.couple.View.Bridge;

import com.example.couple.Model.Bridge.Couple.BranchInTwoDaysBridge;
import com.example.couple.Model.Bridge.Couple.ConnectedSetBridge;
import com.example.couple.Model.Bridge.Couple.TriadBridge;
import com.example.couple.Model.Bridge.LongBeat.AfterDoubleBridge;
import com.example.couple.Model.Bridge.LongBeat.BranchInDayBridge;
import com.example.couple.Model.Bridge.Sign.SignOfDouble;
import com.example.couple.Model.Display.SpecialSetHistory;
import com.example.couple.Model.Origin.Jackpot;
import com.example.couple.Model.Origin.Lottery;
import com.example.couple.Model.Time.TimeBase;

import java.util.List;

public interface SelectiveBridgeView {
    void ShowError(String message);
    void ShowNextDayTimeAndJackpotList(TimeBase nextDay,
                                       List<Jackpot> allJackpotList, List<Jackpot> jackpotList);
    void ShowLotteryList(List<Lottery> lotteries);
    void ShowAfterDoubleBridge(List<AfterDoubleBridge> bridges);
    void ShowLongBeatBridge(List<SpecialSetHistory> histories);
    void ShowConnectedTouchs(List<Integer> touchs);
    void ShowBranchInTwoDaysBridge(BranchInTwoDaysBridge bridge);
    void ShowShadowTouchs(List<Integer> touchs);
    void ShowSignOfDouble(SignOfDouble sign);
    void ShowBranchInDayBridge(BranchInDayBridge bridge);
    void ShowConnectedSetBridge(ConnectedSetBridge bridge);
    void ShowTriadSetBridge(List<TriadBridge> bridges);
}
