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
import com.example.couple.Custom.Handler.DateHandler;
import com.example.couple.Custom.Handler.JackpotHandler;
import com.example.couple.Custom.Handler.LotteryHandler;
import com.example.couple.Model.DateTime.Time.TimeBase;
import com.example.couple.Model.Origin.Jackpot;
import com.example.couple.Model.Origin.Lottery;

import java.util.List;
import java.util.concurrent.ExecutionException;

// class alarm receiver
public class UpdateDataAlarm extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        boolean checkUpdateJackpot = CheckUpdate.checkUpdateJackpot(context);
        if (!checkUpdateJackpot) {
            return;
        }

        boolean isInternetAvailable = InternetBase.isInternetAvailable(context);
        if (!isInternetAvailable) {
            String title = "XSMB";
            String content = "Lỗi lấy kết quả XS Đặc Biệt Miền Bắc (Lỗi không có mạng).";
            NotificationBase.pushNotification(context, NotifyId.UPDATE_DATA, title, content);
        } else {
            getData(context);
        }
    }

    public void getData(Context context) {
        try {
            String title = "XSMB";
            String content = "Lỗi cập nhật dữ liệu !";
            String jackpot = Api.getJackpotDataFromInternet(context, TimeInfo.CURRENT_YEAR);
            IOFileBase.saveDataToFile(context, "jackpot" +
                    TimeInfo.CURRENT_YEAR + ".txt", jackpot, Context.MODE_PRIVATE);
            List<Jackpot> jackpotList = JackpotHandler.getReserveJackpotListFromFile(context, 18);
            if (jackpotList.isEmpty()) {
                NotificationBase.pushNotification(context, NotifyId.UPDATE_DATA, title, content);
                return;
            }

            JackpotHandler.saveLastDate(context, jackpotList);
            boolean isUpdate = CheckUpdate.checkUpdateJackpot(context);
            if (isUpdate) {
                content = "Chưa có dữ liệu XSĐB ngày hôm nay (Cập nhật lúc " + TimeBase.CURRENT().showHHMM() + ")";
                NotificationBase.pushNotification(context, NotifyId.UPDATE_DATA, title, content);
            } else {
                content = "Kết quả XS Đặc biệt Miền Bắc hôm nay là: " +
                        jackpotList.get(0).getJackpot() + ".";
                NotificationBase.pushNotification(context, NotifyId.UPDATE_DATA, title, content);
                NewBridge.notify(context, jackpotList);
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
                String time = Api.getDateFromInternet(context);
                IOFileBase.saveDataToFile(context, FileName.DATE_TIME, time, Context.MODE_PRIVATE);
            }
            if (checkUpdateLottery) {
                String lottery = Api.getLotteryDataFromInternet(context, Const.MAX_DAYS_TO_GET_LOTTERY);
                IOFileBase.saveDataToFile(context, FileName.LOTTERY, lottery, Context.MODE_PRIVATE);
                List<Lottery> lotteries = LotteryHandler.getLotteryListFromFile(context, 1);
                LotteryHandler.saveLastDate(context, lotteries);
            }
            if (checkUpdateCycle) {
                DateHandler.updateAllDateData(context);
            }
        } catch (ExecutionException | InterruptedException ignored) {

        }
    }

}
