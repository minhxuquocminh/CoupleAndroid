package com.example.couple.View.UpdateDataInfo;

import java.util.List;

public interface AddJackpotManyYearsView {
    void showMessage(String message);
    void showStartYear(int year);
    void updateJackpotDataInManyYearsError(List<Integer> years);
}
