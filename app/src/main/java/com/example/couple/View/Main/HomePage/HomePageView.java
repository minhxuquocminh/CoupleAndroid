package com.example.couple.View.Main.HomePage;

import com.example.couple.Model.Display.BSingle;
import com.example.couple.Model.Origin.Jackpot;
import com.example.couple.Model.Origin.Lottery;

import java.util.List;

public interface HomePageView {
    void ShowError(String message);
    void ShowAllDataStatus(String message);
    void UpdateTimeSuccess(String message);
    void UpdateJackpotSuccess(String message);
    void UpdateLotterySuccess(String message);
    // show data
    void ShowTimeDataFromFile(String time);
    void ShowJackpotDataFromFile(List<Jackpot> jackpotList);
    void ShowLotteryList(List<Lottery> lotteries);
    // show bridge
    void ShowTouchsByClawBridge(List<Integer> touchs);
    void ShowTouchBridge(List<BSingle> touchList);
    void ShowSpecialTouchBridge(List<Integer> touchList);
    void ShowNote(String note);


}
