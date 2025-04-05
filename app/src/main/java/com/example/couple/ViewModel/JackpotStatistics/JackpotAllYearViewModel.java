package com.example.couple.ViewModel.JackpotStatistics;

import android.content.Context;

import com.example.couple.Custom.Handler.JackpotHandler;
import com.example.couple.Custom.Handler.Statistics.JackpotStatistics;
import com.example.couple.Model.Bridge.NumberSet.SpecialSet;
import com.example.couple.View.JackpotStatistics.JackpotAllYearView;

import java.util.Map;

public class JackpotAllYearViewModel {
    JackpotAllYearView view;
    Context context;

    public JackpotAllYearViewModel(JackpotAllYearView view, Context context) {
        this.view = view;
        this.context = context;
    }

    public void getAllStatistics(int balanceYears, int doubleYears) {
        doubleYears = Math.min(doubleYears, 7);
        Map<Integer, String[][]> matrixByYears = JackpotHandler.getJackpotMatrixByYears(context, doubleYears);
        Map<Integer, int[]> counters = JackpotStatistics.getCounterByYears(matrixByYears);
        String[][] matrix = JackpotStatistics.getCounterMatrixByYears(counters, SpecialSet.DOUBLE.values);
        view.showSameDoubleCountingManyYears(matrix, SpecialSet.DOUBLE.values.size() + 1, counters.size() + 2);
    }

}
