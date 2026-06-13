package com.example.couple.ViewModel.Couple;

import android.content.Context;

import com.example.couple.Custom.Const.TimeInfo;
import com.example.couple.Custom.Handler.JackpotHandler;
import com.example.couple.Model.Origin.Jackpot;
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
        List<Jackpot> jackpotList = JackpotHandler.getJackpotListByDays(context, TimeInfo.DAY_OF_YEAR);
        if (jackpotList.isEmpty()) balanceCoupleView.showMessage("Không lấy được dữ liệu !");
        balanceCoupleView.showJackpotData(jackpotList);
    }

    public void getTableOfBalanceCouple(List<Jackpot> jackpotList, int numberOfDays) {
        balanceCoupleView.showTableOfBalanceCouple(jackpotList, numberOfDays);
    }

}
