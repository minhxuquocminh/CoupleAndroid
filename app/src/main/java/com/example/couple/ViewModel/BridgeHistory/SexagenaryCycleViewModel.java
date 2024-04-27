package com.example.couple.ViewModel.BridgeHistory;

import android.content.Context;

import com.example.couple.Custom.Handler.JackpotHandler;
import com.example.couple.Custom.Handler.TimeHandler;
import com.example.couple.Model.Origin.Jackpot;
import com.example.couple.Model.Time.TimeBase;
import com.example.couple.View.BridgeHistory.SexagenaryCycleView;

import java.util.List;

public class SexagenaryCycleViewModel {
    Context context;
    SexagenaryCycleView view;

    public SexagenaryCycleViewModel(Context context, SexagenaryCycleView view) {
        this.context = context;
        this.view = view;
    }

    public void getSexagenaryCycle(int numberOfDays) {
        boolean sync = TimeHandler.hasSyncedCycleToday(context);
        if (!sync) {
            TimeHandler.updateAllSexagenaryCycle(context);
            view.updateSuccess();
        }
        List<TimeBase> cycleList = TimeHandler.getAllSexagenaryCycle(context, numberOfDays);
        List<Jackpot> allJackpotList = JackpotHandler
                .getAllReserveJackpotListFromFile(context, numberOfDays);
        if (cycleList.isEmpty()) {
            view.showMessage("Lỗi không lấy đc thông tin.");
        } else {
            view.showSexagenaryCycle(cycleList, allJackpotList);
        }

    }
}
