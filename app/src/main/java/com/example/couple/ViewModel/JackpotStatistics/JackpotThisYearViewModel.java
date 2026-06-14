package com.example.couple.ViewModel.JackpotStatistics;

import android.content.Context;

import com.example.couple.Custom.Const.TimeInfo;
import com.example.couple.Custom.Handler.History.NumberSetHistoryHandler;
import com.example.couple.Custom.Handler.JackpotHandler;
import com.example.couple.Custom.Handler.Statistics.JackpotStatistics;
import com.example.couple.Model.Bridge.NumberSet.NumberSetHistory;
import com.example.couple.Model.Bridge.NumberSet.NumberSetType;
import com.example.couple.Model.Origin.Jackpot;
import com.example.couple.Model.Statistics.EventFrequency;
import com.example.couple.Model.Statistics.EventFrequencyType;
import com.example.couple.View.JackpotStatistics.JackpotThisYearView;

import java.util.Arrays;
import java.util.Collections;
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
        if (jackpotList.isEmpty()) return;
        view.showReserveJackpotListThisYear(jackpotList);
    }

    public void getEventFrequency(List<Jackpot> jackpotList) {
        Map<EventFrequencyType, EventFrequency> eventFrequencyMap = JackpotStatistics.getEventFrequencyMap(jackpotList,
                Arrays.asList(EventFrequencyType.KEEP_SAME, EventFrequencyType.REVERSE));
        view.showEventFrequency(eventFrequencyMap);
    }

    public void getSameDoubleInNearestTime(List<Jackpot> jackpotList) {
        Map<NumberSetType, List<NumberSetHistory>> historiesByType = NumberSetHistoryHandler.getFullNumberSetsHistory(jackpotList,
                Collections.singletonList(NumberSetType.DOUBLE));
        if (historiesByType.isEmpty()) return;
        // double
        List<NumberSetHistory> doubleHistories = historiesByType.get(NumberSetType.DOUBLE);
        if (doubleHistories != null) {
            view.showSameDoubleInNearestTime(doubleHistories, jackpotList.size());
        }
    }

    public void getHeadAndTailInNearestTime(List<Jackpot> jackpotList) {
        Map<NumberSetType, List<NumberSetHistory>> historiesByType = NumberSetHistoryHandler.getFullNumberSetsHistory(jackpotList,
                Arrays.asList(NumberSetType.HEAD, NumberSetType.TAIL));
        if (historiesByType.isEmpty()) return;
        // head tail
        List<NumberSetHistory> headHistories = historiesByType.get(NumberSetType.HEAD);
        List<NumberSetHistory> tailHistories = historiesByType.get(NumberSetType.TAIL);
        if (headHistories != null && tailHistories != null) {
            view.showHeadAndTailInNearestTime(Stream.concat(headHistories.stream(), tailHistories.stream()).collect(Collectors.toList()));
        }
    }
}
