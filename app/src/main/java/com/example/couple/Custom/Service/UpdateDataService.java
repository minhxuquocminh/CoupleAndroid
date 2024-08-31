package com.example.couple.Custom.Service;

import android.content.Context;

import com.example.couple.Base.Handler.InternetBase;
import com.example.couple.Base.Handler.MainThreadBase;
import com.example.couple.Custom.Const.Const;
import com.example.couple.Custom.Handler.DateHandler;
import com.example.couple.Custom.Handler.JackpotHandler;
import com.example.couple.Custom.Handler.LotteryHandler;
import com.example.couple.Custom.Handler.Notification.NewBridge;
import com.example.couple.Custom.Handler.Sync.SyncDataHandler;
import com.example.couple.Custom.Handler.Sync.SyncDataState;
import com.example.couple.Custom.Handler.Sync.SyncState;
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

    public void updateAllData(boolean showMessageCheck, boolean isMainThread) {
        SyncDataState syncDataState = new SyncDataState(context);
        if (!syncDataState.needToSync()) {
            if (showMessageCheck) {
                showMessage("Hiện tại, không có dữ liệu nào cần cập nhật !", isMainThread);
            }
            return;
        }

        if (!InternetBase.isInternetAvailable(context)) {
            showMessage("Bạn đang offline.", isMainThread);
            return;
        }

        showMessage("Đang cập nhật...", isMainThread);
        SyncDataState resultSyncState = SyncDataHandler.execute(context);
        if (resultSyncState.getSyncDateState() == SyncState.OK) {
            getTimeData(isMainThread);
        }
        if (resultSyncState.getSyncJackpotState() == SyncState.OK) {
            getJackpotData(isMainThread);
        }
        if (resultSyncState.getSyncLotteryState() == SyncState.OK) {
            getLotteryData(Const.MAX_DAYS_TO_GET_LOTTERY, isMainThread);
        }

        showMessage("Cập nhật hoàn tất: \n" +
                " + Thời gian (" + resultSyncState.getSyncDateState().name + "),\n" +
                " + XS Đặc Biệt (" + resultSyncState.getSyncJackpotState().name + "),\n" +
                " + XSMB (" + resultSyncState.getSyncLotteryState().name + "),\n" +
                " + Thời gian can chi (" + resultSyncState.getSyncDateDataState().name + ").", isMainThread);
    }

    public void getTimeData(boolean isMainThread) {
        String time = DateHandler.getDate(context);
        if (time.isEmpty()) time = "Lỗi cập nhật thời gian!";
        String finalTime = time;
        new MainThreadBase(() -> {
            updateDataView.showTimeData(finalTime);
        }, isMainThread).post();
    }

    public void getJackpotData(boolean isMainThread) {
        List<Jackpot> jackpotList = JackpotHandler.getReserveJackpotListFromFile(context, 99);
        if (jackpotList.isEmpty()) return;
        NewBridge.notify(context, jackpotList);
        new MainThreadBase(() -> {
            updateDataView.showJackpotData(jackpotList);
        }, isMainThread).post();
    }

    public void getLotteryData(int numberOfDays, boolean isMainThread) {
        List<Lottery> lotteries = LotteryHandler.getLotteryListFromFile(context, numberOfDays);
        if (lotteries.isEmpty()) return;
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
