package com.example.couple.ViewModel.Bridge;

import android.content.Context;

import com.example.couple.Custom.Const.Const;
import com.example.couple.Custom.Const.TimeInfo;
import com.example.couple.Custom.Handler.Bridge.BCoupleBridgeHandler;
import com.example.couple.Custom.Handler.Bridge.ConnectedBridgeHandler;
import com.example.couple.Custom.Handler.Bridge.CycleBridgeHandler;
import com.example.couple.Custom.Handler.Bridge.OtherBridgeHandler;
import com.example.couple.Custom.Handler.Bridge.TouchBridgeHandler;
import com.example.couple.Custom.Handler.History.HistoryHandler;
import com.example.couple.Custom.Handler.JackpotHandler;
import com.example.couple.Custom.Handler.LotteryHandler;
import com.example.couple.Custom.Handler.TimeHandler;
import com.example.couple.Model.Bridge.Couple.BranchInTwoDaysBridge;
import com.example.couple.Model.Bridge.Couple.ConnectedSetBridge;
import com.example.couple.Model.Bridge.Couple.TriadBridge;
import com.example.couple.Model.Bridge.LongBeat.AfterDoubleBridge;
import com.example.couple.Model.Bridge.LongBeat.BranchInDayBridge;
import com.example.couple.Model.Bridge.Sign.SignOfDouble;
import com.example.couple.Model.Bridge.Single.ConnectedBridge;
import com.example.couple.Model.Bridge.Single.ShadowTouchBridge;
import com.example.couple.Model.Display.SpecialSetHistory;
import com.example.couple.Model.Origin.Jackpot;
import com.example.couple.Model.Origin.Lottery;
import com.example.couple.Model.Time.Cycle.Branch;
import com.example.couple.Model.Time.TimeBase;
import com.example.couple.View.Bridge.SelectiveBridgeView;

import java.util.List;

public class SelectiveBridgeViewModel {
    SelectiveBridgeView view;
    Context context;

    public SelectiveBridgeViewModel(SelectiveBridgeView view, Context context) {
        this.view = view;
        this.context = context;
    }

    public void GetAllData() {
        TimeBase nextDay = TimeHandler.getTimeBaseNextDay(context);
        List<Jackpot> allJackpotList = JackpotHandler.GetAllReserveJackpotListFromFile(context, TimeInfo.DAY_OF_YEAR);
        List<Jackpot> jackpotList = JackpotHandler.GetReserveJackpotListFromFile(context, TimeInfo.DAY_OF_YEAR);
        if (jackpotList.size() == 0) {
            view.ShowError("Lỗi không lấy được thông tin XS Đặc biệt!");
        } else {
            view.ShowNextDayTimeAndJackpotList(nextDay, allJackpotList, jackpotList);
        }

        List<Lottery> lotteries = LotteryHandler.getLotteryListFromFile(context, Const.MAX_DAYS_TO_GET_LOTTERY);
        if (lotteries.size() == 0) {
            view.ShowError("Lỗi không lấy được thông tin XSMB!");
        } else {
            view.ShowLotteryList(lotteries);
        }
    }

    public void GetAfterDoubleBridge(List<Jackpot> jackpotList) {
        List<AfterDoubleBridge> bridges = BCoupleBridgeHandler.GetAfterDoubleBridges(jackpotList);
        view.ShowAfterDoubleBridge(bridges);
    }

    public void GetLongBeatBridge(List<Jackpot> jackpotList) {
        List<SpecialSetHistory> histories = HistoryHandler.GetCompactSpecialSetsHistory(jackpotList);
        view.ShowLongBeatBridge(histories);
    }

    public void GetBranchInTwoDaysBridge(List<Jackpot> jackpotList) {
        BranchInTwoDaysBridge bridge = CycleBridgeHandler.GetBranchInTwoDaysBridge(jackpotList,
                0);
        view.ShowBranchInTwoDaysBridge(bridge);
    }

    public void GetConnectedTouchs(List<Lottery> lotteries) {
        ConnectedBridge bridge = ConnectedBridgeHandler.GetConnectedBridge(lotteries,
                0, Const.CONNECTED_BRIDGE_FINDING_DAYS, Const.CONNECTED_BRIDGE_MAX_DISPLAY);
        view.ShowConnectedTouchs(bridge.getTouchs());
    }

    public void GetShadowTouchs(List<Jackpot> jackpotList) {
        ShadowTouchBridge bridge = TouchBridgeHandler.GetShadowTouchBridge(jackpotList, 0);
        view.ShowShadowTouchs(bridge.getTouchs());
    }

    public void GetSignOfDouble(List<Jackpot> jackpotList) {
        SignOfDouble sign = OtherBridgeHandler.GetSignOfDouble(jackpotList, 0);
        view.ShowSignOfDouble(sign);
    }

    public void GetBranchInDayBridge(List<Jackpot> allJackpotList, Branch nextDayBranch) {
        BranchInDayBridge bridge = CycleBridgeHandler.GetBranchInDayBridges(allJackpotList, nextDayBranch);
        view.ShowBranchInDayBridge(bridge);
    }

    public void GetConnectedSetBridge(List<Lottery> lotteries) {
        ConnectedSetBridge bridge = ConnectedBridgeHandler.GetConnectedSetBridge(lotteries,
                0, Const.CONNECTED_BRIDGE_FINDING_DAYS, Const.CONNECTED_BRIDGE_MAX_DISPLAY);
        view.ShowConnectedSetBridge(bridge);
    }

    public void GetTriadSetBridge(List<Lottery> lotteries) {
        List<TriadBridge> bridges = ConnectedBridgeHandler.GetTriadBridge(lotteries,
                0, Const.CONNECTED_BRIDGE_FINDING_DAYS, Const.CONNECTED_BRIDGE_MAX_DISPLAY);
        view.ShowTriadSetBridge(bridges);

    }

}
