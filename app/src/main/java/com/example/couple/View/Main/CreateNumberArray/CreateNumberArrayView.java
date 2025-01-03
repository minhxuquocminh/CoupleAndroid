package com.example.couple.View.Main.CreateNumberArray;

import com.example.couple.Model.Handler.Picker;
import com.example.couple.Model.Bridge.Estimated.PeriodHistory;

import java.util.List;

public interface CreateNumberArrayView {
    void showMessage(String message);
    void showPeriodHistory(List<PeriodHistory> periodHistoryList);
    void showNumberArray(List<Integer> numbers, int typeOfNumber);
    void showNumberArrayCounter(int size);
    void verifyCoupleArraySuccess(List<Integer> pickers);
    void verifyTriadArraySuccess(List<Integer> pickers);
    void showVerifyStringSuccess(List<Integer> numbers, int typeOfNumber);
    void showTriadTable(List<Picker> pickers);
    void saveDataSuccess(String message);
    void showTriadList(List<Picker> pickers);
}
