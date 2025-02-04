package com.example.couple.ViewModel.BridgeHistory;

import android.content.Context;

import com.example.couple.Custom.Handler.DateHandler;
import com.example.couple.Custom.Handler.JackpotHandler;
import com.example.couple.Custom.Handler.Sync.CheckSync;
import com.example.couple.Model.DateTime.Date.DateData;
import com.example.couple.Model.Origin.Jackpot;
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
        if (!CheckSync.isSyncDateData(context)) {
            boolean checkUpdate = DateHandler.updateAllDateData(context);
            if (checkUpdate) view.updateSuccess();
        }
        List<DateData> cycleList = DateHandler.getAllDateData(context, numberOfDays);
        List<Jackpot> jackpotList = JackpotHandler
                .getAllReverseJackpotListByDays(context, numberOfDays);
        if (cycleList.isEmpty()) {
            view.showMessage("Lỗi không lấy đc thông tin.");
        } else {
            view.showSexagenaryCycle(cycleList, jackpotList);
        }

    }
}
