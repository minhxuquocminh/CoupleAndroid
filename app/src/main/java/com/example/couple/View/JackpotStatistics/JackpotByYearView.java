package com.example.couple.View.JackpotStatistics;

import java.util.List;

public interface JackpotByYearView {
    void showMessage(String message);
    void showYearList(List<Integer> yearList);
    void showTableOfJackpot(String[][] matrix, int year);
}
