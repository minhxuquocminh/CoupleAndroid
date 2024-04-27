package com.example.couple.View.Main.CreateNumberArray;

import com.example.couple.Model.Display.Number;
import com.example.couple.Model.Origin.Jackpot;
import com.example.couple.Model.Origin.Lottery;
import com.example.couple.Model.Support.PeriodHistory;

import java.util.List;

public interface CreateNumberArrayView {
    void showMessage(String message);
    void showLotteryAndJackpotList(List<Jackpot> jackpotList, List<Lottery> lotteryList);
    void showPeriodHistory(List<PeriodHistory> periodHistoryList);
    void showNumberArray(List<Integer> numbers, int typeOfNumber);
    void showNumberArrayCounter(int size);
    void verifyCoupleArraySuccess(String numbersArr);
    void verifyTriadArraySuccess(List<Number> numbers);
    void showVerifyStringSuccess(List<Integer> numbers, int typeOfNumber);
    void showSubJackpotList(List<Jackpot> jackpotList);
    void showTriadTable(List<Number> numbers);
    void saveDataSuccess(String message);
    void showTriadList(List<Number> numbers);
}
