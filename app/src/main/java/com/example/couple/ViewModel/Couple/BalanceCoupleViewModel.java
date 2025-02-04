package com.example.couple.ViewModel.Couple;

import android.content.Context;

import com.example.couple.Custom.Const.Const;
import com.example.couple.Custom.Const.TimeInfo;
import com.example.couple.Custom.Handler.Bridge.EstimatedBridgeHandler;
import com.example.couple.Custom.Handler.JackpotHandler;
import com.example.couple.Model.Origin.Jackpot;
import com.example.couple.Model.Bridge.Estimated.PeriodHistory;
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
        List<Jackpot> jackpotList = JackpotHandler.getReverseJackpotListByDays(context, TimeInfo.DAY_OF_YEAR);
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

}
