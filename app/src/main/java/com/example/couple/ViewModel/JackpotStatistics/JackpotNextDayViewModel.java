package com.example.couple.ViewModel.JackpotStatistics;

import android.content.Context;

import com.example.couple.Custom.Handler.JackpotHandler;
import com.example.couple.Custom.Handler.Statistics.JackpotStatistics;
import com.example.couple.Model.Origin.Jackpot;
import com.example.couple.Model.Statistics.JackpotNextDay;
import com.example.couple.View.JackpotStatistics.JackpotNextDayView;

import java.util.List;
import java.util.Map;

public class JackpotNextDayViewModel {
    JackpotNextDayView view;
    Context context;

    public JackpotNextDayViewModel(JackpotNextDayView view, Context context) {
        this.view = view;
        this.context = context;
    }

    public void getJackpotNextDay(int years, int dayNumberBefore) {
        years = Math.min(years, 8);
        List<Jackpot> jackpotList = JackpotHandler.getJackpotListByDays(context, 10);
        int couple = jackpotList.get(dayNumberBefore).getCoupleInt();
        Map<Integer, String[][]> matrixByYears = JackpotHandler.getJackpotMatrixByYears(context, years);
        List<JackpotNextDay> jackpotNextDayList =
                JackpotStatistics.getJackpotNextDayList(matrixByYears, couple);
        view.showJackpotNextDay(jackpotNextDayList);
    }

}
