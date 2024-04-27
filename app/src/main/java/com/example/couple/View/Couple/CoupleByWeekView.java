package com.example.couple.View.Couple;

import com.example.couple.Model.Origin.Jackpot;

import java.util.List;

public interface CoupleByWeekView {
    void showMessage(String message);
    void showJackpotByWeek(List<Jackpot> jackpotList, int weekNumber);
}
