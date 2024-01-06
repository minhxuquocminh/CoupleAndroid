package com.example.couple.View.Bridge;

import com.example.couple.Model.Bridge.Couple.ConnectedSetBridge;
import com.example.couple.Model.Bridge.Couple.TriadBridge;
import com.example.couple.Model.Bridge.LongBeat.AfterDoubleBridge;
import com.example.couple.Model.Bridge.LongBeat.BranchInDayBridge;
import com.example.couple.Model.Display.SpecialSetHistory;
import com.example.couple.Model.Origin.Jackpot;
import com.example.couple.Model.Origin.Lottery;
import com.example.couple.Model.Time.TimeBase;

import java.util.List;

public interface SelectiveBridgeView {
    void ShowError(String message);
    void ShowJackpotAndTimeBaseList(List<Jackpot> jackpotList, List<TimeBase> timeBaseList);
    void ShowLotteryList(List<Lottery> lotteries);
    void ShowAfterDoubleBridge(List<AfterDoubleBridge> bridges);
    void ShowLongBeatBridge(List<SpecialSetHistory> histories);
    void ShowShadowTouchs(List<Integer> touchs);
    void ShowBranchInDayBridge(BranchInDayBridge bridge);
    void ShowConnectedSetBridge(ConnectedSetBridge bridge);
    void ShowTriadSetBridge(List<TriadBridge> bridges);
    void ShowConnectedTouchs(List<Integer> touchs);
}
