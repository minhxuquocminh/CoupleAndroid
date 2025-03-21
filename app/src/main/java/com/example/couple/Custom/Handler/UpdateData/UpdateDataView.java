package com.example.couple.Custom.Handler.UpdateData;

import com.example.couple.Model.Origin.Jackpot;
import com.example.couple.Model.Origin.Lottery;

import java.util.List;

public interface UpdateDataView {
    void showMessage(String message);
    void showLongMessage(String message);
    void showTimeData(String time);
    void showJackpotData(List<Jackpot> jackpotList);
    void showLotteryData(List<Lottery> lotteries);
}
