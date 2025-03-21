package com.example.couple.ViewModel.JackpotStatistics;

import android.content.Context;

import com.example.couple.Custom.Handler.History.HistoryHandler;
import com.example.couple.Custom.Handler.JackpotHandler;
import com.example.couple.Model.Bridge.NumberSet.NumericSetHistory;
import com.example.couple.Model.Origin.Jackpot;
import com.example.couple.View.BridgeHistory.SpecialSetsHistoryView;

import java.util.List;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class SpecialSetsHistoryViewModel {
    Context context;
    SpecialSetsHistoryView specialSetsHistoryView;

    public void getJackpotList(int numberOfDays) {
        List<Jackpot> jackpotList = JackpotHandler.getReverseJackpotListByDays(context, numberOfDays);
        specialSetsHistoryView.showJackpotList(jackpotList);
    }

    public void getSpecialSetsHistory(List<Jackpot> jackpotList) {
        List<NumericSetHistory> historyList =
                HistoryHandler.getNumberSetsHistory(jackpotList);
        if (!historyList.isEmpty()) {
            specialSetsHistoryView.showSpecialSetsHistory(historyList);
        }
    }
}
