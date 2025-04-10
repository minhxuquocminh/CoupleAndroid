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
import com.example.couple.Model.Bridge.Connected.ConnectedSetBridge;
import com.example.couple.Model.Bridge.Connected.TriadBridge;
import com.example.couple.Model.Bridge.Cycle.BranchInDayBridge;
import com.example.couple.Model.Bridge.Cycle.BranchInTwoDaysBridge;
import com.example.couple.Model.Bridge.Double.AfterDoubleBridge;
import com.example.couple.Model.Bridge.Double.AfterDoubleExtendBridge;
import com.example.couple.Model.Bridge.Double.SignOfDouble;
import com.example.couple.Model.Bridge.NumberSet.NumberSetHistory;
import com.example.couple.Model.Bridge.NumberSet.NumberSetType;
import com.example.couple.Model.Bridge.Touch.ConnectedBridge;
import com.example.couple.Model.Bridge.Touch.ShadowTouchBridge;
import com.example.couple.Model.Origin.Jackpot;
import com.example.couple.Model.Origin.Lottery;
import com.example.couple.View.Bridge.SelectiveBridgeView;

import java.util.Arrays;
import java.util.List;

public class SelectiveBridgeViewModel {
    SelectiveBridgeView view;
    Context context;

    public SelectiveBridgeViewModel(SelectiveBridgeView view, Context context) {
        this.view = view;
        this.context = context;
    }

    public void getAllData() {
        List<Jackpot> jackpotList = JackpotHandler.getJackpotListByDays(context, TimeInfo.DAY_OF_YEAR);
        if (jackpotList.isEmpty()) {
            view.showMessage("Lỗi không lấy được thông tin XS Đặc biệt!");
        } else {
            view.showJackpotList(jackpotList);
        }

        List<Lottery> lotteries = LotteryHandler.getLotteryListFromFile(context, Const.MAX_DAYS_TO_GET_LOTTERY);
        if (lotteries.isEmpty()) {
            view.showMessage("Lỗi không lấy được thông tin XSMB!");
        } else {
            view.showLotteryList(lotteries);
        }
    }

    public void getAfterDoubleExtendBridge(List<Jackpot> jackpotList) {
        List<AfterDoubleExtendBridge> bridges = BCoupleBridgeHandler.getAfterAllDoubleBridges(jackpotList);
        view.showAfterDoubleExtendBridge(bridges);
    }

    public void getAfterDoubleBridge(List<Jackpot> jackpotList) {
        List<AfterDoubleBridge> bridges = BCoupleBridgeHandler.getAfterDoubleBridges(jackpotList);
        view.showAfterDoubleBridge(bridges);
    }

    public void getLongBeatBridge(List<Jackpot> jackpotList) {
        List<NumberSetHistory> histories = HistoryHandler.getCompactNumberSetsHistory(jackpotList,
                Arrays.asList(NumberSetType.values()), 40, 30, 79);
        view.showLongBeatBridge(histories);
    }

    public void getBranchInTwoDaysBridge(List<Jackpot> jackpotList) {
        BranchInTwoDaysBridge bridge = CycleBridgeHandler.getBranchInTwoDaysBridge(jackpotList,
                0);
        view.showBranchInTwoDaysBridge(bridge);
    }

    public void getConnectedTouchs(List<Lottery> lotteries) {
        ConnectedBridge bridge = ConnectedBridgeHandler.getConnectedBridge(lotteries,
                0, Const.CONNECTED_BRIDGE_FINDING_DAYS, Const.CONNECTED_BRIDGE_MAX_DISPLAY);
        view.showConnectedTouchs(bridge.getTouches());
    }

    public void getShadowTouchs(List<Jackpot> jackpotList) {
        ShadowTouchBridge bridge = TouchBridgeHandler.getShadowTouchBridge(jackpotList, 0);
        view.showShadowTouchs(bridge.getTouches());
    }

    public void getSignOfDouble(List<Jackpot> jackpotList) {
        SignOfDouble sign = OtherBridgeHandler.getSignOfDouble(jackpotList, 0);
        view.showSignOfDouble(sign);
    }

    public void getBranchInDayBridge(List<Jackpot> jackpotList) {
        BranchInDayBridge bridge = CycleBridgeHandler.getBranchInDayBridge(jackpotList);
        view.showBranchInDayBridge(bridge);
    }

    public void getConnectedSetBridge(List<Lottery> lotteries) {
        ConnectedSetBridge bridge = ConnectedBridgeHandler.getConnectedSetBridge(lotteries,
                0, Const.CONNECTED_BRIDGE_FINDING_DAYS, Const.CONNECTED_BRIDGE_MAX_DISPLAY);
        view.showConnectedSetBridge(bridge);
    }

    public void getTriadSetBridge(List<Lottery> lotteries) {
        List<TriadBridge> bridges = ConnectedBridgeHandler.getTriadBridge(lotteries,
                0, Const.CONNECTED_BRIDGE_FINDING_DAYS, Const.CONNECTED_BRIDGE_MAX_DISPLAY);
        view.showTriadSetBridge(bridges);

    }

}
