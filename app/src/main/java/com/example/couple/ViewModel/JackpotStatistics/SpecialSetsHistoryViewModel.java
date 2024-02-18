package com.example.couple.ViewModel.JackpotStatistics;

import android.content.Context;

import com.example.couple.Custom.Handler.History.HistoryHandler;
import com.example.couple.Custom.Handler.JackpotHandler;
import com.example.couple.Custom.Handler.TimeHandler;
import com.example.couple.Model.Display.SpecialSetHistory;
import com.example.couple.Model.Origin.Jackpot;
import com.example.couple.Model.Time.TimeBase;
import com.example.couple.View.BridgeHistory.SpecialSetsHistoryView;

import java.util.List;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class SpecialSetsHistoryViewModel {
    Context context;
    SpecialSetsHistoryView specialSetsHistoryView;

    public void GetJackpotListAndTimeBaseData(int numberOfDays) {
        List<Jackpot> allJackpotList = JackpotHandler.GetAllReserveJackpotListFromFile(context, numberOfDays);
        List<Jackpot> jackpotList = JackpotHandler.GetReserveJackpotListFromFile(context, numberOfDays);
        TimeBase timeBaseNextDay = TimeHandler.getTimeBaseNextDay(context);
        specialSetsHistoryView.ShowJackpotListAndTimeBaseData(allJackpotList, jackpotList, timeBaseNextDay);
    }

    public void GetSpecialSetsHistory(List<Jackpot> allJackpotList,
                                      List<Jackpot> jackpotList, TimeBase timeBaseNextDay) {
        List<SpecialSetHistory> historyList =
                HistoryHandler.GetSpecialSetsHistory(allJackpotList, jackpotList, timeBaseNextDay);
        if (!historyList.isEmpty()) {
            specialSetsHistoryView.ShowSpecialSetsHistory(historyList);
        }
    }
}
