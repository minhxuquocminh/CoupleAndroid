package com.example.couple.View.JackpotStatistics;

public interface JackpotAllYearView {
    void ShowError(String message);
    void ShowSameDoubleCountingManyYears(int[][] matrixSDB, int m, int n, int startYear, int[] dayNumberArr);
    void ShowRequestLoadMoreData(int startYear_file, int endYear_file);
}
