package com.example.couple.View.Lottery;

import com.example.couple.Model.Origin.Lottery;

import java.util.List;

public interface LotteryView {
    void ShowError(String message);
    void ShowLotteryList(List<Lottery> lotteries);
    void ShowRequestToUpdateLottery(int maxDayNumber, String dayNumber);
    void UpdateLotterySuccess(String message,int numberOfDays);
}
