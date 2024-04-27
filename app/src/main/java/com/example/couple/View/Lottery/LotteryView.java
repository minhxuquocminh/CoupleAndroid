package com.example.couple.View.Lottery;

import com.example.couple.Model.Origin.Lottery;

import java.util.List;

public interface LotteryView {
    void showMessage(String message);
    void showLotteryList(List<Lottery> lotteries);
    void showRequestToUpdateLottery(int maxDayNumber, int dayNumber);
    void updateLotterySuccess(String message, int numberOfDays);
}
