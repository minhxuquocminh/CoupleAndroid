package com.example.couple.View.JackpotStatistics;

public interface CoupleByYearView {
    void showMessage(String message);
    void showCoupleCountingTable(int[][] matrix, int m, int n, int startYear);
    void showRequestLoadMoreData(int startYear_file, int endYear_file);
}
