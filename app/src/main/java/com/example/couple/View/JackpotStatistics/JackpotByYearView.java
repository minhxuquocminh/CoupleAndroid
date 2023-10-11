package com.example.couple.View.JackpotStatistics;

import java.util.List;

public interface JackpotByYearView {
    void ShowError(String message);
    void ShowYearList(List<Integer> yearList);
    void ShowTableOfJackpot(String[][] matrix, int year);
}
