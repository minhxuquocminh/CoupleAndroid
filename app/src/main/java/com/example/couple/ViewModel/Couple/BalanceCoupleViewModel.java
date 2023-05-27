package com.example.couple.ViewModel.Couple;

import android.content.Context;


import com.example.couple.Custom.Const.Const;
import com.example.couple.Custom.Handler.JackpotBridgeHandler;
import com.example.couple.Custom.Handler.JackpotHandler;
import com.example.couple.Custom.Handler.LotteryHandler;
import com.example.couple.Model.BridgeCouple.PeriodBridge;
import com.example.couple.Model.Support.History;
import com.example.couple.Model.Origin.Jackpot;
import com.example.couple.Model.Origin.Lottery;
import com.example.couple.View.Couple.BalanceCoupleView;

import java.util.Arrays;
import java.util.List;

public class BalanceCoupleViewModel {
    BalanceCoupleView balanceCoupleView;
    Context context;

    public BalanceCoupleViewModel(BalanceCoupleView balanceCoupleView, Context context) {
        this.balanceCoupleView = balanceCoupleView;
        this.context = context;
    }

    public void GetJackpotDataFromFile() {
        List<Jackpot> jackpotList = JackpotHandler.GetReserveJackpotListFromFile(context, Const.DAY_OF_YEAR);
        if (jackpotList.isEmpty()) balanceCoupleView.ShowError("Không lấy được dữ liệu !");
        balanceCoupleView.ShowJackpotData(jackpotList);
    }

    public void GetTableOfBalanceCouple(List<Jackpot> jackpotList, String numberOfDaysStr) {
        if (numberOfDaysStr.length() == 0) {
            balanceCoupleView.ShowError("Bạn chưa nhập số ngày để lấy dữ liệu!");
        } else if (Integer.parseInt(numberOfDaysStr) > 360 || Integer.parseInt(numberOfDaysStr) < 0) {
            balanceCoupleView.ShowError("Nằm ngoài phạm vi!");
        } else {
            balanceCoupleView.ShowTableOfBalanceCouple(jackpotList, Integer.parseInt(numberOfDaysStr));
        }
    }

    public void GetPeriodBridge(List<Jackpot> jackpotList, String dayNumberBefore, String filterDays) {
        List<History> historyList = JackpotBridgeHandler.GetPeriodHistoryList(jackpotList,
                Integer.parseInt(dayNumberBefore), Integer.parseInt(filterDays), Const.AMPLITUDE_OF_PERIOD_BRIDGE);
        balanceCoupleView.ShowPeriodBridge(historyList);
    }

    public void GetCombineBridge(List<Jackpot> jackpotList, String dayNumberBeforeStr, int bridgeType) {
        int dayNumberBefore = Integer.parseInt(dayNumberBeforeStr);
        List<Lottery> lotteries = LotteryHandler.getLotteryListFromFile(context, 30);
        PeriodBridge periodBridge = JackpotBridgeHandler.GetPeriodBridge(jackpotList, dayNumberBefore);
        List<Integer> searchDaysList = Arrays.asList(12, 14, 16, 18);
        List<Integer> touchs = JackpotBridgeHandler.GetTouchsByClawSupport(lotteries,
                searchDaysList, Integer.parseInt(dayNumberBeforeStr), 8, bridgeType);
        if (touchs.isEmpty()) {
            balanceCoupleView.ShowError("Không tìm thấy cầu.");
        } else {
            balanceCoupleView.ShowTest(touchs);
        }
    }

}
