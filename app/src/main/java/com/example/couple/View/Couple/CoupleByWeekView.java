package com.example.couple.View.Couple;

import com.example.couple.Model.Origin.Jackpot;

import java.util.List;

public interface CoupleByWeekView {
    void ShowError(String message);
    void ShowJackpotByWeek(List<Jackpot> jackpotList, int weekNumber);
}
