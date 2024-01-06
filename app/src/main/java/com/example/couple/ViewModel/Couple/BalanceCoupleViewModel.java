package com.example.couple.ViewModel.Couple;

import android.content.Context;

import com.example.couple.Custom.Const.Const;
import com.example.couple.Custom.Const.TimeInfo;
import com.example.couple.Custom.Handler.Bridge.ConnectedBridgeHandler;
import com.example.couple.Custom.Handler.Bridge.EstimatedBridgeHandler;
import com.example.couple.Custom.Handler.JackpotHandler;
import com.example.couple.Custom.Handler.LotteryHandler;
import com.example.couple.Model.Bridge.Couple.EstimatedBridge;
import com.example.couple.Model.Origin.Jackpot;
import com.example.couple.Model.Origin.Lottery;
import com.example.couple.Model.Support.PeriodHistory;
import com.example.couple.View.Couple.BalanceCoupleView;

import java.util.List;

public class BalanceCoupleViewModel {
    BalanceCoupleView balanceCoupleView;
    Context context;

    public BalanceCoupleViewModel(BalanceCoupleView balanceCoupleView, Context context) {
        this.balanceCoupleView = balanceCoupleView;
        this.context = context;
    }

    public void GetJackpotDataFromFile() {
        List<Jackpot> jackpotList = JackpotHandler.GetReserveJackpotListFromFile(context, TimeInfo.DAY_OF_YEAR);
        if (jackpotList.isEmpty()) balanceCoupleView.ShowError("Không lấy được dữ liệu !");
        balanceCoupleView.ShowJackpotData(jackpotList);
    }

    public void GetTableOfBalanceCouple(List<Jackpot> jackpotList, int numberOfDays) {
        balanceCoupleView.ShowTableOfBalanceCouple(jackpotList, numberOfDays);
    }

    public void GetPeriodHistory(List<Jackpot> jackpotList, String dayNumberBefore, String filterDays) {
        List<PeriodHistory> periodHistoryList = EstimatedBridgeHandler.GetPeriodHistoryList(jackpotList,
                Integer.parseInt(dayNumberBefore), Integer.parseInt(filterDays), Const.AMPLITUDE_OF_PERIOD);
        balanceCoupleView.ShowPeriodHistory(periodHistoryList);
    }

    public void GetCombinePeriod(List<Jackpot> jackpotList, String dayNumberBeforeStr, int bridgeType) {
        int dayNumberBefore = Integer.parseInt(dayNumberBeforeStr);
        List<Lottery> lotteries = LotteryHandler.getLotteryListFromFile(context, Const.MAX_DAYS_TO_GET_LOTTERY);
        EstimatedBridge estimatedBridge = EstimatedBridgeHandler.GetEstimatedBridge(jackpotList, dayNumberBefore);
        List<Integer> touchs = ConnectedBridgeHandler.GetTouchsByClawSupport(lotteries,
                Integer.parseInt(dayNumberBeforeStr), Const.CLAW_BRIDGE_FINDING_DAYS, 8, bridgeType);
        if (touchs.isEmpty()) {
            balanceCoupleView.ShowError("Không tìm thấy cầu.");
        } else {
            balanceCoupleView.ShowTest(touchs);
        }
    }

}
