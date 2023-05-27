package com.example.couple.ViewModel.Couple;

import android.content.Context;

import com.example.couple.Custom.Const.Const;
import com.example.couple.Custom.Handler.JackpotHandler;
import com.example.couple.Model.Origin.Jackpot;
import com.example.couple.View.Couple.CoupleByWeekView;

import java.util.List;

public class CoupleByWeekViewModel {
    Context context;
    CoupleByWeekView view;

    public CoupleByWeekViewModel(Context context, CoupleByWeekView view) {
        this.context = context;
        this.view = view;
    }

    public void GetJackpotByWeek(int weekNumber) {
        int dayNumber = weekNumber * Const.DAY_OF_WEEK;
        List<Jackpot> jackpotList = JackpotHandler.GetReserveJackpotListFromFile(context, dayNumber);
        if (jackpotList.isEmpty()) {
            view.ShowError("Không có dữ liệu XS Đặc biệt.");
        } else {
            view.ShowJackpotByWeek(jackpotList, weekNumber);
        }
    }
}
