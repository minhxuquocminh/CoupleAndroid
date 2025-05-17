package com.example.couple.View.Main.CreateNumberArray;

import java.util.List;

public interface CreateNumberArrayView {
    void showMessage(String message);
    void showNumberArray(List<Integer> numbers, int typeOfNumber);
    void showNumberArrayCounter(int size);
    void verifyCoupleArraySuccess(List<Integer> numbers);
    void verifyTriadArraySuccess(List<Integer> numbers);
    void showVerifyStringSuccess(List<Integer> numbers, int typeOfNumber);
    void showTriadTable(List<Integer> normalNumbers, List<Integer> importantNumbers);
    void saveDataSuccess(String message);
    void showTriadList(List<Integer> numbers);
}
