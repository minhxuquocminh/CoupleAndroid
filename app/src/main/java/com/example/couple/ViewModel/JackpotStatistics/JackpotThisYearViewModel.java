package com.example.couple.ViewModel.JackpotStatistics;

import android.content.Context;

import com.example.couple.Custom.Old.Statistics.JackpotStatistics;
import com.example.couple.Custom.Const.TimeInfo;
import com.example.couple.Custom.Handler.JackpotHandler;
import com.example.couple.Model.Display.NearestTime;
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

    public void GetReserveJackpotListThisYear() {
        List<Jackpot> jackpotList = JackpotHandler.GetReserveJackpotListByYear(context, TimeInfo.CURRENT_YEAR);
        if (jackpotList.size() > 0) {
            view.ShowReserveJackpotListThisYear(jackpotList);
        }
    }

    public void GetSameDoubleAndDayNumberTotal(List<com.example.couple.Model.Origin.Jackpot> jackpotList) {
        int countSameDouble = 0;
        for (int i = 0; i < jackpotList.size(); i++) {
            if (jackpotList.get(i).getCouple().isSameDouble()) {
                countSameDouble++;
            }
        }
        view.ShowSameDoubleAndDayNumberTotal(countSameDouble, jackpotList.size());
    }

    public void GetSameDoubleInNearestTime(List<com.example.couple.Model.Origin.Jackpot> jackpotList) {
        List<NearestTime> nearestTimeList = JackpotStatistics.GetSameDoubleInNearestTime(jackpotList);
        view.ShowSameDoubleInNearestTime(nearestTimeList);
    }

    public void GetHeadAndTailInNearestTime(List<com.example.couple.Model.Origin.Jackpot> jackpotList) {
        List<NearestTime> nearestTimeList = JackpotStatistics.GetHeadAndTailInNearestTime(jackpotList);
        view.ShowHeadAndTailInNearestTime(nearestTimeList);
    }
}
