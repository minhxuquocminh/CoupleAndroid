package com.example.couple.ViewModel.JackpotStatistics;

import android.content.Context;

import com.example.couple.Custom.Handler.JackpotHandler;
import com.example.couple.Custom.Handler.NumberArrayHandler;
import com.example.couple.Custom.Statistics.JackpotStatistics;
import com.example.couple.View.JackpotStatistics.CoupleByYearView;

import java.util.Map;

public class CoupleByYearViewModel {
    CoupleByYearView view;
    Context context;

    public CoupleByYearViewModel(CoupleByYearView view, Context context) {
        this.view = view;
        this.context = context;
    }

    // nếu status = 0 thì ko sắp xếp, status = 1 thì sx năm gần nhất, status = 2 thì sx tổng.
    public void getCoupleCountingTable(int years, String tens, String unit, int status) {
        years = Math.min(years, 8);
        Map<Integer, String[][]> matrixByYears = JackpotHandler.getJackpotMatrixByYears(context, years);
        Map<Integer, int[]> counterByYears = JackpotStatistics.getCounterByYears(matrixByYears);
        String[][] matrix;
        int row;
        if (!tens.isEmpty()) {
            matrix = JackpotStatistics.getCounterMatrixByYears(counterByYears, NumberArrayHandler.getHeads(Integer.parseInt(tens)));
            row = 11;
        } else if (!unit.isEmpty()) {
            matrix = JackpotStatistics.getCounterMatrixByYears(counterByYears, NumberArrayHandler.getTails(Integer.parseInt(unit)));
            row = 11;
        } else {
            matrix = JackpotStatistics.getCounterMatrixByYears(counterByYears);
            row = 101;
        }

        int col = counterByYears.size() + 2;
        if (status == 1) {
            matrix = JackpotStatistics.sortMatrixByColumn(matrix, col - 2);
        }
        if (status == 2) {
            matrix = JackpotStatistics.sortMatrixByColumn(matrix, col - 1);
        }
        view.showCoupleCountingTable(matrix, row, col);
    }

}
