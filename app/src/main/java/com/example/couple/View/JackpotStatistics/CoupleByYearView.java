package com.example.couple.View.JackpotStatistics;

public interface CoupleByYearView {
    void ShowError(String message);
    void ShowCoupleCountingTable(int[][] matrix, int m, int n, int startYear);
    void ShowRequestLoadMoreData(int startYear_file, int endYear_file);
}
