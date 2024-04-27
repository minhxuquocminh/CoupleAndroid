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

    public void getJackpotDataFromFile() {
        List<Jackpot> jackpotList = JackpotHandler.getReserveJackpotListFromFile(context, TimeInfo.DAY_OF_YEAR);
        if (jackpotList.isEmpty()) balanceCoupleView.showMessage("Không lấy được dữ liệu !");
        balanceCoupleView.showJackpotData(jackpotList);
    }

    public void getTableOfBalanceCouple(List<Jackpot> jackpotList, int numberOfDays) {
        balanceCoupleView.showTableOfBalanceCouple(jackpotList, numberOfDays);
    }

    public void getPeriodHistory(List<Jackpot> jackpotList, String dayNumberBefore, String filterDays) {
        List<PeriodHistory> periodHistoryList = EstimatedBridgeHandler.getPeriodHistoryList(jackpotList,
                Integer.parseInt(dayNumberBefore), Integer.parseInt(filterDays), Const.AMPLITUDE_OF_PERIOD);
        balanceCoupleView.showPeriodHistory(periodHistoryList);
    }

    public void getCombinePeriod(List<Jackpot> jackpotList, String dayNumberBeforeStr, int bridgeType) {
        int dayNumberBefore = Integer.parseInt(dayNumberBeforeStr);
        List<Lottery> lotteries = LotteryHandler.getLotteryListFromFile(context, Const.MAX_DAYS_TO_GET_LOTTERY);
        EstimatedBridge estimatedBridge = EstimatedBridgeHandler.getEstimatedBridge(jackpotList, dayNumberBefore);
        List<Integer> touchs = ConnectedBridgeHandler.getTouchsByClawSupport(lotteries,
                Integer.parseInt(dayNumberBeforeStr), Const.CLAW_BRIDGE_FINDING_DAYS, 8, bridgeType);
        if (touchs.isEmpty()) {
            balanceCoupleView.showMessage("Không tìm thấy cầu.");
        } else {
            balanceCoupleView.showTest(touchs);
        }
    }

}
