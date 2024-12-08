package com.example.couple.View.Main.HomePage;

import com.example.couple.Model.Bridge.LongBeat.NearestTime;

import java.util.List;

public interface HomePageView {
    void showMessage(String message);
    void showHeadAndTailInLongestTime(List<NearestTime> nearestTimeList);
    void showNote(String note);
}
