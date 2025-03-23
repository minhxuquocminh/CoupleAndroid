package com.example.couple.ViewModel.JackpotStatistics;

import android.content.Context;

import com.example.couple.Custom.Const.TimeInfo;
import com.example.couple.Custom.Handler.JackpotHandler;
import com.example.couple.Custom.Statistics.JackpotStatistics;
import com.example.couple.Model.Bridge.LongBeat.NearestTime;
import com.example.couple.Model.Origin.Jackpot;
import com.example.couple.View.JackpotStatistics.JackpotThisYearView;

import java.util.List;

public class JackpotThisYearViewModel {
    JackpotThisYearView view;
    Context context;

    public JackpotThisYearViewModel(JackpotThisYearView view, Context context) {
        this.view = view;
        this.context = context;
    }

    public void getReserveJackpotListThisYear() {
        List<Jackpot> jackpotList = JackpotHandler.getJackpotListByYear(context, TimeInfo.CURRENT_YEAR);
        if (!jackpotList.isEmpty()) {
            view.showReserveJackpotListThisYear(jackpotList);
        }
    }

    public void getSameDoubleAndDayNumberTotal(List<Jackpot> jackpotList) {
        int countSameDouble = 0;
        for (int i = 0; i < jackpotList.size(); i++) {
            if (jackpotList.get(i).getCouple().isSameDouble()) {
                countSameDouble++;
            }
        }
        view.showSameDoubleAndDayNumberTotal(countSameDouble, jackpotList.size());
    }

    public void getSameDoubleInNearestTime(List<Jackpot> jackpotList) {
        List<NearestTime> nearestTimeList = JackpotStatistics.getSameDoubleInNearestTime(jackpotList);
        view.showSameDoubleInNearestTime(nearestTimeList);
    }

    public void getHeadAndTailInNearestTime(List<Jackpot> jackpotList) {
        List<NearestTime> nearestTimeList = JackpotStatistics.getHeadAndTailInNearestTime(jackpotList);
        view.showHeadAndTailInNearestTime(nearestTimeList);
    }
}
