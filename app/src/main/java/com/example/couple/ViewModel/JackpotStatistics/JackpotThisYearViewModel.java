package com.example.couple.ViewModel.JackpotStatistics;

import android.content.Context;

import com.example.couple.Custom.Const.TimeInfo;
import com.example.couple.Custom.Handler.History.HistoryHandler;
import com.example.couple.Custom.Handler.JackpotHandler;
import com.example.couple.Model.Bridge.NumberSet.NumberSetHistory;
import com.example.couple.Model.Bridge.NumberSet.NumberSetType;
import com.example.couple.Model.Origin.Jackpot;
import com.example.couple.View.JackpotStatistics.JackpotThisYearView;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class JackpotThisYearViewModel {
    JackpotThisYearView view;
    Context context;

    public JackpotThisYearViewModel(JackpotThisYearView view, Context context) {
        this.view = view;
        this.context = context;
    }

    public void getReserveJackpotListThisYear() {
        List<Jackpot> jackpotList = JackpotHandler.getJackpotListByYear(context, TimeInfo.CURRENT_YEAR);
        Map<NumberSetType, List<NumberSetHistory>> historiesByType = HistoryHandler.getFullNumberSetsHistory(jackpotList,
                Arrays.asList(NumberSetType.DOUBLE, NumberSetType.HEAD, NumberSetType.TAIL));
        if (historiesByType.isEmpty()) return;
        // double
        List<NumberSetHistory> doubleHistories = historiesByType.get(NumberSetType.DOUBLE);
        if (doubleHistories != null) {
            view.showSameDoubleInNearestTime(doubleHistories, jackpotList.size());
        }
        // head tail
        List<NumberSetHistory> headHistories = historiesByType.get(NumberSetType.HEAD);
        List<NumberSetHistory> tailHistories = historiesByType.get(NumberSetType.TAIL);
        if (headHistories != null && tailHistories != null) {
            view.showHeadAndTailInNearestTime(Stream.concat(headHistories.stream(), tailHistories.stream()).collect(Collectors.toList()));
        }
    }

}
