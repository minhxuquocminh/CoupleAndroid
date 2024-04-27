package com.example.couple.View.JackpotStatistics;

public interface JackpotAllYearView {
    void showMessage(String message);
    void showSameDoubleCountingManyYears(int[][] matrixSDB, int m, int n, int startYear, int[] dayNumberArr);
    void showRequestLoadMoreData(int startYear_file, int endYear_file);
}
