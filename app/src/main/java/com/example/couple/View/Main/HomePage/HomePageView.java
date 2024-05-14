package com.example.couple.View.Main.HomePage;

import com.example.couple.Model.Display.BSingle;
import com.example.couple.Model.Display.NearestTime;
import com.example.couple.Model.Origin.Jackpot;
import com.example.couple.Model.Origin.Lottery;

import java.util.List;

public interface HomePageView {
    void showMessage(String message);
    void showHeadAndTailInLongestTime(List<NearestTime> nearestTimeList);
    void showTouchBridge(List<BSingle> touchList);
    void showSpecialTouchBridge(List<Integer> touchList);
    void showNote(String note);
}
