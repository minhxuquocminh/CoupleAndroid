package com.example.couple.View.Main.HomePage;

import com.example.couple.Model.Display.BSingle;
import com.example.couple.Model.Display.NearestTime;
import com.example.couple.Model.Origin.Jackpot;
import com.example.couple.Model.Origin.Lottery;

import java.util.List;

public interface HomePageView {
    void showError(String message);
    void showMessage(String message);
    void showAllDataStatus(String message);
    void updateJackpotSuccess(String message);
    void updateLotterySuccess(String message);
    // show data
    void showTimeDataFromFile(String time);
    void showJackpotDataFromFile(List<Jackpot> jackpotList);
    void showLotteryList(List<Lottery> lotteries);
    // show bridge
    void showHeadAndTailInLongestTime(List<NearestTime> nearestTimeList);
    void showTouchBridge(List<BSingle> touchList);
    void showSpecialTouchBridge(List<Integer> touchList);
    void showNote(String note);
}
