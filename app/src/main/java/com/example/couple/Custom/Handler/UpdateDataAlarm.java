package com.example.couple.Custom.Handler;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.example.couple.Base.Handler.IOFileBase;
import com.example.couple.Base.Handler.InternetBase;
import com.example.couple.Base.Handler.NotificationBase;
import com.example.couple.Custom.Const.Const;
import com.example.couple.Custom.Const.TimeInfo;
import com.example.couple.Model.Origin.Jackpot;

import java.util.List;
import java.util.concurrent.ExecutionException;

// class alarm receiver
public class UpdateDataAlarm extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        if (InternetBase.isNetworkAvailable(context)) {
            getData(context);
        } else {
            String title = "XSMB";
            String content = "Lỗi lấy kết quả XS Đặc biệt Miền Bắc (Lỗi không có mạng).";
            NotificationBase.pushNotification(context, title, content);
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
                    NotificationBase.pushNotification(context, title, content);
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
        try {
            if (checkUpdateTime) {
                String time = Api.GetTimeDataFromInternet();
                IOFileBase.saveDataToFile(context, Const.TIME_FILE_NAME, time, 0);
            }
            if (checkUpdateLottery) {
                String lottery = Api.GetLotteryDataFromInternet(context, Const.MAX_DAYS_TO_GET_LOTTERY);
                IOFileBase.saveDataToFile(context, Const.LOTTERY_FILE_NAME, lottery, 0);
            }
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


}
