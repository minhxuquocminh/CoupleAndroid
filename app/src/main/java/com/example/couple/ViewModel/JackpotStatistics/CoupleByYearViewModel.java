package com.example.couple.ViewModel.JackpotStatistics;

import android.content.Context;

import com.example.couple.Custom.Const.Const;
import com.example.couple.Custom.Const.TimeInfo;
import com.example.couple.Custom.Handler.JackpotHandler;
import com.example.couple.Custom.Statistics.JackpotStatistics;
import com.example.couple.Model.Origin.Jackpot;
import com.example.couple.View.JackpotStatistics.CoupleByYearView;

import java.util.List;

public class CoupleByYearViewModel {
    CoupleByYearView view;
    Context context;

    public static final int START_NUMBER_OF_YEARS = 3;
    int startYear;

    public CoupleByYearViewModel(CoupleByYearView view, Context context) {
        this.view = view;
        this.context = context;
    }

    public void getCoupleCountingTable(String yearNumber, String tens, String unit, int status) {
        int numberOfYears = yearNumber.isEmpty() || yearNumber.equals("0") ?
                JackpotStatistics.getMaxStartNumberOfYears(context, START_NUMBER_OF_YEARS) :
                Integer.parseInt(yearNumber);
        int[][] matrix = getMatrixCountCouple(numberOfYears);
        if (matrix != null) {
            if (tens.isEmpty() && unit.isEmpty()) {
                int[][] matrix_sort = JackpotStatistics.getSortMatrix(matrix,
                        Const.MAX_ROW_COUNT_TABLE, numberOfYears + 1, status);
                view.showCoupleCountingTable(matrix_sort,
                        Const.MAX_ROW_COUNT_TABLE, numberOfYears + 1, startYear);
            } else if (!tens.isEmpty() && !unit.isEmpty()) {
                int rowNumber = 1;
                int[][] new_matrix = new int[rowNumber][numberOfYears + 1];
                new_matrix[0][0] = Integer.parseInt(tens + unit);
                for (int j = 1; j < numberOfYears + 1; j++) {
                    new_matrix[0][j] = matrix[Integer.parseInt(tens + unit)][j];
                }
                int[][] matrix_sort = JackpotStatistics.getSortMatrix(new_matrix,
                        rowNumber, numberOfYears + 1, status);
                view.showCoupleCountingTable(matrix_sort, rowNumber, numberOfYears + 1, startYear);
            } else {
                int rowNumber = 10;
                int[][] new_matrix = new int[rowNumber][numberOfYears + 1];
                for (int i = 0; i < rowNumber; i++) {
                    int digit2d = unit.isEmpty() ?
                            Integer.parseInt(tens + i) : Integer.parseInt(i + unit);
                    new_matrix[i][0] = digit2d;
                    for (int j = 1; j < numberOfYears + 1; j++) {
                        if (unit.isEmpty()) {
                            new_matrix[i][j] = matrix[Integer.parseInt(tens + i)][j];
                        } else {
                            new_matrix[i][j] = matrix[Integer.parseInt(i + unit)][j];
                        }
                    }
                }
                int[][] matrix_sort = JackpotStatistics.getSortMatrix(new_matrix,
                        rowNumber, numberOfYears + 1, status);
                view.showCoupleCountingTable(matrix_sort, rowNumber, numberOfYears + 1, startYear);
            }
        }
    }

    private int[][] getMatrixCountCouple(int numberOfYears) {
        int[] startAndEndYearFile = JackpotStatistics.getStartAndEndYearFile(context);
        if (startAndEndYearFile == null) {
            view.showMessage("Bạn cần nạp dữ liệu XS Đặc biệt các năm mới có thể xem được bảng" +
                    " số lần xuất hiện của các số theo năm!");
        } else {
            int startYear_file = startAndEndYearFile[0];
            int endYear_file = startAndEndYearFile[1];
            int numberOfYears_file = endYear_file - startYear_file + 1;
            if (endYear_file < TimeInfo.CURRENT_YEAR || numberOfYears_file < numberOfYears) {
                view.showRequestLoadMoreData(startYear_file, endYear_file);
            } else {
                startYear = endYear_file - numberOfYears + 1;
                List<Jackpot> jackpotList = JackpotHandler.getJackpotListManyYears(context, numberOfYears);
                // numberOfYears + 1 vì 1 là cột của số đề
                return JackpotStatistics.getCountCoupleMatrix(jackpotList,
                        Const.MAX_ROW_COUNT_TABLE, numberOfYears + 1, startYear);
            }
        }
        return null;
    }

}
