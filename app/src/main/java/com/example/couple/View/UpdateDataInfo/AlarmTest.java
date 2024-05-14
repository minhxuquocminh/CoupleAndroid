package com.example.couple.View.UpdateDataInfo;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.example.couple.Base.Handler.IOFileBase;
import com.example.couple.Base.Handler.NotificationBase;
import com.example.couple.Custom.Const.Const;
import com.example.couple.Custom.Const.FileName;
import com.example.couple.Custom.Const.NotifyId;
import com.example.couple.Custom.Const.TimeInfo;
import com.example.couple.Custom.Handler.Api;
import com.example.couple.Custom.Handler.CheckUpdate;
import com.example.couple.Custom.Handler.CycleHandler;
import com.example.couple.Custom.Handler.JackpotHandler;
import com.example.couple.Custom.Handler.LotteryHandler;
import com.example.couple.Custom.Handler.Notification.NotifyNewBridge;
import com.example.couple.Model.Origin.Jackpot;
import com.example.couple.Model.Origin.Lottery;

import java.util.List;
import java.util.concurrent.ExecutionException;

public class AlarmTest extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        getData(context);
    }

    public void getData(Context context) {
        String title = "XSMB";
        String content = "";
        try {
            String jackpot = Api.getJackpotDataFromInternet(context, TimeInfo.CURRENT_YEAR);
            IOFileBase.saveDataToFile(context, "jackpot" +
                    TimeInfo.CURRENT_YEAR + ".txt", jackpot, Context.MODE_PRIVATE);
            if (!CheckUpdate.checkUpdateJackpot(context)) {
                List<Jackpot> jackpotList = JackpotHandler
                        .getReserveJackpotListFromFile(context, 18);
                if (jackpotList.isEmpty()) return;
                content = "Kết quả XS Đặc biệt Miền Bắc hôm nay là: " +
                        jackpotList.get(0).getJackpot() + ".";
                NotificationBase.pushNotification(context, NotifyId.UPDATE_DATA, title, content);
                NotifyNewBridge.notify(context, jackpotList);
                JackpotHandler.saveLastDate(context, jackpotList);
                getDataIfNeeded(context);
            }
        } catch (ExecutionException | InterruptedException ignored) {

        }
    }

    private void getDataIfNeeded(Context context) {
        boolean checkUpdateTime = CheckUpdate.checkUpdateTime(context);
        boolean checkUpdateLottery = CheckUpdate.checkUpdateLottery(context);
        boolean checkUpdateCycle = CheckUpdate.checkUpdateCycle(context);
        try {
            if (checkUpdateTime) {
                String time = Api.getTimeDataFromInternet(context);
                IOFileBase.saveDataToFile(context, FileName.TIME, time, Context.MODE_PRIVATE);
            }
            if (checkUpdateLottery) {
                String lottery = Api.getLotteryDataFromInternet(context, Const.MAX_DAYS_TO_GET_LOTTERY);
                IOFileBase.saveDataToFile(context, FileName.LOTTERY, lottery, Context.MODE_PRIVATE);
                List<Lottery> lotteries = LotteryHandler.getLotteryListFromFile(context, 1);
                LotteryHandler.saveLastDate(context, lotteries);
            }
            if (checkUpdateCycle) {
                CycleHandler.updateAllSexagenaryCycle(context);
            }
        } catch (ExecutionException | InterruptedException ignored) {

        }
    }


}
