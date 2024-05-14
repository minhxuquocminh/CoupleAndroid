package com.example.couple.ViewModel.BridgeHistory;

import android.content.Context;

import com.example.couple.Custom.Handler.CheckUpdate;
import com.example.couple.Custom.Handler.CycleHandler;
import com.example.couple.Custom.Handler.JackpotHandler;
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
        if (CheckUpdate.checkUpdateCycle(context)) {
            boolean checkUpdate = CycleHandler.updateAllSexagenaryCycle(context);
            if (checkUpdate) view.updateSuccess();
        }
        List<TimeBase> cycleList = CycleHandler.getAllSexagenaryCycle(context, numberOfDays);
        List<Jackpot> jackpotList = JackpotHandler
                .getReserveJackpotListFromFile(context, numberOfDays);
        if (cycleList.isEmpty()) {
            view.showMessage("Lỗi không lấy đc thông tin.");
        } else {
            view.showSexagenaryCycle(cycleList, jackpotList);
        }

    }
}
