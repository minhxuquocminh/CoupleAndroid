package com.example.couple.ViewModel.JackpotStatistics;

import android.content.Context;

import com.example.couple.Custom.Const.TimeInfo;
import com.example.couple.Custom.Handler.JackpotHandler;
import com.example.couple.View.JackpotStatistics.JackpotByYearView;

import java.util.List;

public class JackpotByYearViewModel {
    JackpotByYearView jackpotByYearView;
    Context context;

    public JackpotByYearViewModel(JackpotByYearView jackpotByYearView, Context context) {
        this.jackpotByYearView = jackpotByYearView;
        this.context = context;
    }

    public void getYearList() {
        List<Integer> years = JackpotHandler.getUpdatedYears(context);
        if (years.isEmpty()) {
            years.add(TimeInfo.CURRENT_YEAR);
        }
        jackpotByYearView.showYearList(years);
    }

    public void getTableOfJackpot(int year) {
        String[][] matrix = JackpotHandler.getJackpotMatrixByYear(context, year);
        if (matrix == null) {
            jackpotByYearView.showMessage("Lỗi không lấy được thông tin bảng XS Đặc biệt năm " + year + ".");
        } else {
            jackpotByYearView.showTableOfJackpot(matrix, year);
        }
    }

}
