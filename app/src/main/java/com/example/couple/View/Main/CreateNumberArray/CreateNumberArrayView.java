package com.example.couple.View.Main.CreateNumberArray;

import com.example.couple.Model.Display.Picker;
import com.example.couple.Model.Support.PeriodHistory;

import java.util.List;

public interface CreateNumberArrayView {
    void showMessage(String message);
    void showPeriodHistory(List<PeriodHistory> periodHistoryList);
    void showNumberArray(List<Integer> numbers, int typeOfNumber);
    void showNumberArrayCounter(int size);
    void verifyCoupleArraySuccess(List<Picker> pickers);
    void verifyTriadArraySuccess(List<Picker> pickers);
    void showVerifyStringSuccess(List<Integer> numbers, int typeOfNumber);
    void showTriadTable(List<Picker> pickers);
    void saveDataSuccess(String message);
    void showTriadList(List<Picker> pickers);
}
