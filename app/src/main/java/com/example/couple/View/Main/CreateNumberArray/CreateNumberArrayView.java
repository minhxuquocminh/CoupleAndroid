package com.example.couple.View.Main.CreateNumberArray;

import com.example.couple.Model.Display.Number;
import com.example.couple.Model.Display.SpecialSetHistory;
import com.example.couple.Model.Origin.Jackpot;
import com.example.couple.Model.Origin.Lottery;
import com.example.couple.Model.Support.PeriodHistory;

import java.util.List;

public interface CreateNumberArrayView {
    void ShowError(String message);
    void ShowLotteryAndJackpotList(List<Jackpot> jackpotList, List<Lottery> lotteryList);
    void ShowPeriodHistory(List<PeriodHistory> periodHistoryList);
    void ShowNumberArray(List<Integer> numbers, int typeOfNumber);
    void ShowNumberArrayCounter(int size);
    void VerifyCoupleArraySuccess(String numbersArr);
    void VerifyTriadArraySuccess(List<Number> numbers);
    void ShowVerifyStringSuccess(List<Integer> numbers, int typeOfNumber);
    void ShowSubJackpotList(List<Jackpot> jackpotList);
    void ShowTriadTable(List<Number> numbers);
    void SaveDataSuccess(String message);
    void ShowTriadList(List<Number> numbers);
}
