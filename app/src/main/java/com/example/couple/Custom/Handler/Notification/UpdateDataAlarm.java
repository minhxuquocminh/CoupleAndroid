package com.example.couple.Custom.Handler.Notification;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.example.couple.Base.Handler.IOFileBase;
import com.example.couple.Base.Handler.InternetBase;
import com.example.couple.Base.Handler.NotificationBase;
import com.example.couple.Custom.Const.Const;
import com.example.couple.Custom.Const.FileName;
import com.example.couple.Custom.Const.NotifyId;
import com.example.couple.Custom.Const.TimeInfo;
import com.example.couple.Custom.Handler.Api;
import com.example.couple.Custom.Handler.CheckUpdate;
import com.example.couple.Custom.Handler.JackpotHandler;
import com.example.couple.Custom.Handler.TimeHandler;
import com.example.couple.Model.Origin.Jackpot;

import java.util.List;
import java.util.concurrent.ExecutionException;

// class alarm receiver
public class UpdateDataAlarm extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        if (InternetBase.isInternetAvailable(context)) {
            getData(context);
        } else {
            String title = "XSMB";
            String content = "Lỗi lấy kết quả XS Đặc biệt Miền Bắc (Lỗi không có mạng).";
            NotificationBase.pushNotification(context, NotifyId.UPDATE_DATA, title, content);
        }
    }

    public void getData(Context context) {
        String title = "XSMB";
        String content = "";
        if (CheckUpdate.checkUpdateJackpot(context)) {
            try {
                String jackpot = Api.GetJackpotDataFromInternet(context, TimeInfo.CURRENT_YEAR);
                IOFileBase.saveDataToFile(context, "jackpot" +
                        TimeInfo.CURRENT_YEAR + ".txt", jackpot, 0);
                if (!CheckUpdate.checkUpdateJackpot(context)) {
                    List<Jackpot> jackpotList = JackpotHandler
                            .GetReserveJackpotListFromFile(context, 1);
                    content = "Kết quả XS Đặc biệt Miền Bắc hôm nay là: " +
                            jackpotList.get(0).getJackpot() + ".";
                    NotificationBase.pushNotification(context, NotifyId.UPDATE_DATA, title, content);
                    NotifyNewBridge.notify(context, jackpotList);
                    getDataIfNeeded(context);
                }
            } catch (ExecutionException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private void getDataIfNeeded(Context context) {
        boolean checkUpdateTime = CheckUpdate.checkUpdateTime(context);
        boolean checkUpdateLottery = CheckUpdate.checkUpdateLottery(context);
        boolean checkUpdateCycle = CheckUpdate.checkUpdateCycle(context);
        try {
            if (checkUpdateTime) {
                String time = Api.GetTimeDataFromInternet(context);
                IOFileBase.saveDataToFile(context, FileName.TIME, time, 0);
            }
            if (checkUpdateLottery) {
                String lottery = Api.GetLotteryDataFromInternet(context, Const.MAX_DAYS_TO_GET_LOTTERY);
                IOFileBase.saveDataToFile(context, FileName.LOTTERY, lottery, 0);
            }
            if (checkUpdateCycle) {
                TimeHandler.updateAllSexagenaryCycle(context);
            }
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


}
