package com.example.couple.View.JackpotStatistics;

public interface JackpotAllYearView {
    void showMessage(String message);
    void showSameDoubleCountingManyYears(String[][] matrix, int row, int col);
    void showRequestLoadMoreData(int startYear_file, int endYear_file);
}
