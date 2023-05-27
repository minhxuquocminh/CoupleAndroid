package com.example.couple.Custom.Handler;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import androidx.core.app.NotificationCompat;

import com.example.couple.Custom.Const.TimeInfo;
import com.example.couple.Base.Handler.IOFileBase;
import com.example.couple.Base.Handler.InternetBase;
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
            pushNotification(context, title, content);
        }
    }

    public void getData(Context context) {
        boolean isUpdateAll = CheckUpdate.checkUpdateAll(context);
        if (isUpdateAll) {
            getDataIfNeeded(context);
            String title = "XSMB";
            String content = "";
            if(!CheckUpdate.checkUpdateData(context)){ // sub: update = false => data.length > 0 nên ko cần check empty list nữa
                List<Jackpot> jackpotList = JackpotHandler.GetReserveJackpotListFromFile(context, 7);
                content = "Kết quả XS Đặc biệt Miền Bắc hôm nay là: "
                        + jackpotList.get(0).getJackpot();
            } else {
                content = "Hiện tại, dữ liệu XS Đặc biệt Miền Bắc trong ngày hôm nay chưa được cập nhật.";
            }
            pushNotification(context, title, content);
        }
    }

    private void getDataIfNeeded(Context context) {
        boolean isUpdateTime = CheckUpdate.checkUpdateTime(context);
        boolean isUpdateData = CheckUpdate.checkUpdateData(context);
        try {
            if (isUpdateTime) {
                String time = Api.GetTimeDataFromInternet();
                IOFileBase.saveDataToFile(context, "time.txt", time, 0);
            }
            if (isUpdateData) {
                String jackpot = Api.GetJackpotDataFromInternet(context, TimeInfo.CURRENT_YEAR);
                String lottery = Api.GetLotteryDataFromInternet(context, 30);
                IOFileBase.saveDataToFile(context, "jackpot" + TimeInfo.CURRENT_YEAR + ".txt",
                        jackpot, 0);
                IOFileBase.saveDataToFile(context, "lottery.txt", lottery, 0);
            }
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void pushNotification(Context context, String title, String content) {
        UpdateDataNotification notification = new UpdateDataNotification(context, title, content);
        NotificationCompat.Builder nb = notification.getChannelNotification();
        notification.getManager().notify(1, nb.build());
    }


}
