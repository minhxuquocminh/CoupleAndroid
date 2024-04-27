package com.example.couple.ViewModel.JackpotStatistics;

import android.content.Context;

import com.example.couple.Custom.Const.TimeInfo;
import com.example.couple.Custom.Handler.JackpotHandler;
import com.example.couple.Custom.Statistics.JackpotStatistics;
import com.example.couple.View.JackpotStatistics.JackpotByYearView;

import java.util.ArrayList;
import java.util.List;

public class JackpotByYearViewModel {
    JackpotByYearView jackpotByYearView;
    Context context;

    public JackpotByYearViewModel(JackpotByYearView jackpotByYearView, Context context) {
        this.jackpotByYearView = jackpotByYearView;
        this.context = context;
    }

    public void getYearList() {
        int[] startAndEndYearFile = JackpotStatistics.getStartAndEndYearFile(context);
        List<Integer> yearList = new ArrayList<>();
        if (startAndEndYearFile == null) {
            yearList.add(TimeInfo.CURRENT_YEAR);
        } else {
            int startYear = startAndEndYearFile[0];
            int endYear = startAndEndYearFile[1];
            for (int year = endYear; year >= startYear; year--) {
                yearList.add(year);
            }
        }
        jackpotByYearView.showYearList(yearList);
    }

    public void getTableOfJackpot(int year) {
        String[][] matrix = JackpotHandler.getJackpotMaxtrixByYear(context, year);
        if (matrix == null) {
            jackpotByYearView.showMessage("Lỗi không lấy được thông tin bảng XS Đặc biệt năm " + year + ".");
        } else {
            jackpotByYearView.showTableOfJackpot(matrix, year);
        }
    }

}
