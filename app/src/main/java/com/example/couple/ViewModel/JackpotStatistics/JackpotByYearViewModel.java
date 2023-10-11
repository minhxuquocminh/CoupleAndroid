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

    public void GetYearList() {
        int[] startAndEndYearFile = JackpotStatistics.GetStartAndEndYearFile(context);
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
        jackpotByYearView.ShowYearList(yearList);
    }

    public void GetTableOfJackpot(int year) {
        String[][] matrix = JackpotHandler.GetJackpotMaxtrixByYear(context, year);
        if (matrix == null) {
            jackpotByYearView.ShowError("Lỗi không lấy được thông tin bảng XS Đặc biệt năm " + year + ".");
        } else {
            jackpotByYearView.ShowTableOfJackpot(matrix, year);
        }
    }

}
