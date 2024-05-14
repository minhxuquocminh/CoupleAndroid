package com.example.couple.Custom.Service;

import android.content.Context;

import com.example.couple.Base.Handler.InternetBase;
import com.example.couple.Base.Handler.MainThreadBase;
import com.example.couple.Custom.Const.Const;
import com.example.couple.Custom.Handler.CheckUpdate;
import com.example.couple.Custom.Handler.CycleHandler;
import com.example.couple.Custom.Handler.JackpotHandler;
import com.example.couple.Custom.Handler.LotteryHandler;
import com.example.couple.Custom.Handler.Notification.NotifyNewBridge;
import com.example.couple.Custom.Handler.TimeHandler;
import com.example.couple.Model.Origin.Jackpot;
import com.example.couple.Model.Origin.Lottery;

import java.util.List;

public class UpdateDataService {
    UpdateDataView updateDataView;
    Context context;

    public UpdateDataService(UpdateDataView updateDataView, Context context) {
        this.updateDataView = updateDataView;
        this.context = context;
    }

    public void updateAllDataIfNeeded(boolean isMainThread) {
        boolean isUpdateTime = CheckUpdate.checkUpdateTime(context);
        if (isUpdateTime) {
            updateAllData(isMainThread);
        }
    }

    public void updateAllData(boolean isMainThread) {
        if (!InternetBase.isInternetAvailable(context)) {
            showMessage("Bạn đang offline.", isMainThread);
            return;
        }
        showMessage("Đang cập nhật...", isMainThread);
        boolean checkUpdateTime = TimeHandler.updateTime(context);
        boolean checkUpdateJackpot = JackpotHandler.updateJackpot(context);
        boolean checkUpdateLottery =
                LotteryHandler.updateLottery(context, Const.MAX_DAYS_TO_GET_LOTTERY);
        boolean checkUpdateCycle = CycleHandler.updateAllSexagenaryCycle(context);

        String timeStatus = checkUpdateTime ? "(hoàn thành)" : "(thất bại)";
        if (checkUpdateTime) {
            getTimeData(isMainThread);
        }

        String jackpotStatus = checkUpdateJackpot ? "(hoàn thành)" : "(thất bại)";
        if (checkUpdateJackpot) {
            getJackpotData(isMainThread);
        }

        String lotteryStatus = checkUpdateLottery ? "(hoàn thành)" : "(thất bại)";
        if (checkUpdateLottery) {
            getLotteryData(Const.MAX_DAYS_TO_GET_LOTTERY, isMainThread);
        }

        String cycleStatus = checkUpdateCycle ? "(hoàn thành)" : "(thất bại)";
        showMessage("Cập nhật hoàn tất: thời gian " + timeStatus +
                ", XS Đặc Biệt " + jackpotStatus + ", XSMB " + lotteryStatus +
                ", thời gian can chi " + cycleStatus + ".", isMainThread);
    }

    public void getTimeData(boolean isMainThread) {
        String time = TimeHandler.getTimeData(context);
        if (time.isEmpty()) time = "Lỗi cập nhật thời gian!";
        String finalTime = time;
        new MainThreadBase(() -> {
            updateDataView.showTimeData(finalTime);
        }, isMainThread).post();
    }

    public void getJackpotData(boolean isMainThread) {
        List<Jackpot> jackpotList = JackpotHandler.getReserveJackpotListFromFile(context, 18);
        if (jackpotList.isEmpty()) return;
        NotifyNewBridge.notify(context, jackpotList);
        JackpotHandler.saveLastDate(context, jackpotList);
        new MainThreadBase(() -> {
            updateDataView.showJackpotData(jackpotList);
        }, isMainThread).post();
    }

    public void getLotteryData(int numberOfDays, boolean isMainThread) {
        List<Lottery> lotteries = LotteryHandler.getLotteryListFromFile(context, numberOfDays);
        if (lotteries.isEmpty()) return;
        LotteryHandler.saveLastDate(context, lotteries);
        new MainThreadBase(() -> {
            updateDataView.showLotteryData(lotteries);
        }, isMainThread).post();
    }

    public void updateJackpot(boolean isShowMessage, boolean isMainThread) {
        boolean checkUpdateJackpot = JackpotHandler.updateJackpot(context);
        String message = checkUpdateJackpot ?
                "Cập nhật XS Đặc Biệt thành công !" : "Đã xảy ra lỗi khi cập nhật XS Đặc Biệt !";
        if (isShowMessage) showMessage(message, isMainThread);
        if (checkUpdateJackpot) {
            getJackpotData(isMainThread);
        }
    }

    public void updateLottery(int numberOfDays, boolean isShowMessage, boolean isMainThread) {
        boolean checkUpdateLottery = LotteryHandler.updateLottery(context, numberOfDays);
        String message = checkUpdateLottery ?
                "Cập nhật XSMB thành công !" : "Đã xảy ra lỗi khi cập nhật XSMB !";
        if (isShowMessage) showMessage(message, isMainThread);
        if (checkUpdateLottery) {
            getLotteryData(Const.MAX_DAYS_TO_GET_LOTTERY, isMainThread);
        }
    }

    private void showMessage(String message, boolean isMainThread) {
        new MainThreadBase(() -> {
            updateDataView.showMessage(message);
        }, isMainThread).post();
    }
}
