package com.example.couple.ViewModel.Main;

import android.content.Context;

import com.example.couple.Base.Handler.AlarmBase;
import com.example.couple.Base.Handler.IOFileBase;
import com.example.couple.Custom.Const.Const;
import com.example.couple.Custom.Const.FileName;
import com.example.couple.Custom.Const.RequestCode;
import com.example.couple.Custom.Handler.Notification.UpdateDataAlarm;
import com.example.couple.Model.DateTime.Time.TimeBase;
import com.example.couple.View.Main.MainView;

public class MainViewModel {
    MainView mainView;
    Context context;

    public MainViewModel(MainView mainView, Context context) {
        this.mainView = mainView;
        this.context = context;
    }

    /**
     * init url data if needed
     */
    public void setUrlAndParamsIfNoData() {
        String data1 = IOFileBase.readDataFromFile(context, FileName.JACKPOT_URL);
        String data2 = IOFileBase.readDataFromFile(context, FileName.LOTTERY_URL);
        if (data1.isEmpty() || data2.isEmpty()) {
            IOFileBase.saveDataToFile(context,
                    FileName.JACKPOT_URL, Const.JACKPOT_URL_AND_PARAMS, 0);
            IOFileBase.saveDataToFile(context,
                    FileName.LOTTERY_URL, Const.LOTTERY_URL_AND_PARAMS, 0);
        }
    }

    public void registerBackgoundRuntime() {
        AlarmBase.registerAlarmEveryDay(context, UpdateDataAlarm.class, RequestCode.ALARM_1830,
                new TimeBase(18, 30, 0));
        AlarmBase.registerAlarmEveryDay(context, UpdateDataAlarm.class, RequestCode.ALARM_1831,
                new TimeBase(18, 31, 0));
        AlarmBase.registerAlarmEveryDay(context, UpdateDataAlarm.class, RequestCode.ALARM_1832,
                new TimeBase(18, 32, 0));
        AlarmBase.registerAlarmEveryDay(context, UpdateDataAlarm.class, RequestCode.ALARM_1833,
                new TimeBase(18, 33, 0));
    }


}
