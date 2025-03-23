package com.example.couple.ViewModel.JackpotStatistics;

import android.content.Context;

import com.example.couple.Custom.Handler.History.HistoryHandler;
import com.example.couple.Custom.Handler.JackpotHandler;
import com.example.couple.Model.Bridge.NumberSet.NumberSetHistory;
import com.example.couple.Model.Bridge.NumberSet.NumberSetType;
import com.example.couple.Model.Origin.Jackpot;
import com.example.couple.View.BridgeHistory.NumberSetHistoryView;

import java.util.List;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class NumberSetHistoryViewModel {
    Context context;
    NumberSetHistoryView numberSetHistoryView;

    public void getJackpotList(int numberOfDays) {
        List<Jackpot> jackpotList = JackpotHandler.getJackpotListByDays(context, numberOfDays);
        numberSetHistoryView.showJackpotList(jackpotList);
    }

    public void getSpecialSetsHistory(List<Jackpot> jackpotList) {
        List<NumberSetHistory> historyList = HistoryHandler.getFullNumberSetsHistory(jackpotList,
                NumberSetType.getValuesWithCouple());
        if (!historyList.isEmpty()) {
            numberSetHistoryView.showSpecialSetsHistory(historyList);
        }
    }
}
