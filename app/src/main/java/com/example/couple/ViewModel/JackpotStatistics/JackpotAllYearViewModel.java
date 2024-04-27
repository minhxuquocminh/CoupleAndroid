package com.example.couple.ViewModel.JackpotStatistics;

import android.content.Context;

import com.example.couple.Custom.Const.Const;
import com.example.couple.Custom.Const.TimeInfo;
import com.example.couple.Custom.Handler.JackpotHandler;
import com.example.couple.Custom.Statistics.JackpotStatistics;
import com.example.couple.Model.Origin.Jackpot;
import com.example.couple.View.JackpotStatistics.JackpotAllYearView;

import java.util.List;

public class JackpotAllYearViewModel {
    JackpotAllYearView view;
    Context context;

    public static final int START_YEAR_NUMBER_BCP = 3;
    int numberOfYearsBCP;
    int startYearBCP;
    public static final int START_YEAR_NUMBER_SDB = 5;
    int numberOfYearsSDB;
    int startYearSDB;

    public JackpotAllYearViewModel(JackpotAllYearView view, Context context) {
        this.view = view;
        this.context = context;
    }

    public void getAllStatistics(String yearNumberBCPString, String yearNumberSDBString) {
        int numberOfYears_BCP = yearNumberBCPString.isEmpty() || yearNumberBCPString.equals("0") ?
                JackpotStatistics.getMaxStartNumberOfYears(context, START_YEAR_NUMBER_SDB) :
                Integer.parseInt(yearNumberBCPString);
        int numberOfYears_SDB = yearNumberSDBString.isEmpty() || yearNumberSDBString.equals("0") ?
                JackpotStatistics.getMaxStartNumberOfYears(context, START_YEAR_NUMBER_SDB) :
                Integer.parseInt(yearNumberSDBString);
        if (numberOfYears_BCP != numberOfYearsBCP) {

        }
        if (numberOfYears_SDB != numberOfYearsSDB) {
            numberOfYearsSDB = numberOfYears_SDB;
            int[][] matrixSDB = getCoupleCountingMatrix(numberOfYearsSDB);
            if (matrixSDB != null) {
                int rowNumber = 10;
                int[][] new_matrix = new int[rowNumber][numberOfYearsSDB + 1];
                for (int i = 0; i < rowNumber; i++) {
                    for (int j = 0; j < numberOfYearsSDB + 1; j++) {
                        new_matrix[i][j] = matrixSDB[i * 11][j];
                    }
                }
                int[] dayNumberArr = JackpotStatistics.getDayNumberByYear(matrixSDB,
                        Const.MAX_ROW_COUNT_TABLE, numberOfYearsSDB + 1);
                view.showSameDoubleCountingManyYears(new_matrix, rowNumber, numberOfYearsSDB + 1,
                        startYearSDB, dayNumberArr);
            }
        }
    }

    private int[][] getCoupleCountingMatrix(int yearNumber) {
        int[] startAndEndYearFile = JackpotStatistics.getStartAndEndYearFile(context);
        if (startAndEndYearFile == null) {
            view.showMessage("Bạn cần nạp dữ liệu XS Đặc biệt các năm mới có thể xem được bảng" +
                    " số lần xuất hiện của Kép bằng theo năm!");
        } else {
            int startYear_file = startAndEndYearFile[0];
            int endYear_file = startAndEndYearFile[1];
            int numberOfYears_file = endYear_file - startYear_file + 1;
            if (endYear_file < TimeInfo.CURRENT_YEAR || numberOfYears_file < yearNumber) {
                view.showRequestLoadMoreData(startYear_file, endYear_file);
            } else {
                startYearSDB = endYear_file - numberOfYearsSDB + 1;
                List<Jackpot> jackpotList = JackpotHandler.getJackpotListManyYears(context, numberOfYearsSDB);
                return JackpotStatistics.getCountCoupleMatrix(jackpotList,
                        Const.MAX_ROW_COUNT_TABLE, numberOfYearsSDB + 1, startYearSDB);
            }
        }
        return null;
    }

}
