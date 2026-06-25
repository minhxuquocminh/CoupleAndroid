package com.example.couple.Custom.Handler.UpdateData;

import android.content.Context;

import com.example.couple.Base.Handler.InternetBase;
import com.example.couple.Base.Handler.MainThreadBase;
import com.example.couple.Custom.Const.Const;
import com.example.couple.Custom.Handler.JackpotHandler;
import com.example.couple.Custom.Handler.LotteryHandler;
import com.example.couple.Custom.Handler.Notification.BridgeNotificationStorageHandler;
import com.example.couple.Custom.Handler.Notification.NewBridge;
import com.example.couple.Custom.Handler.Sync.CheckSync;
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

    public boolean updateAllData(boolean showMessageCheck, boolean isMainThread) {
        SyncDataState syncDataState = new SyncDataState(context);
        if (!syncDataState.needToSync()) {
            if (showMessageCheck) {
                showMessage("Kh\u00f4ng c\u00f3 d\u1eef li\u1ec7u n\u00e0o c\u1ea7n c\u1eadp nh\u1eadt.", isMainThread);
            }
            return true;
        }

        if (!InternetBase.isInternetAvailable(context)) {
            showMessage("B\u1ea1n \u0111ang offline.", isMainThread);
            return false;
        }

        showMessage("\u0110ang c\u1eadp nh\u1eadt...", isMainThread);
        boolean shouldNotifyNewBridge = syncDataState.getSyncJackpotState() != SyncState.DONE;
        SyncDataState resultSyncState = SyncDataHandler.execute(context);
        if (resultSyncState.getSyncJackpotState() != SyncState.NETWORK_ERROR) {
            getJackpotData(isMainThread);
            handleBridgeNotificationAfterJackpotUpdate(shouldNotifyNewBridge
                    && resultSyncState.getSyncJackpotState() == SyncState.DONE);
        }
        if (resultSyncState.getSyncLotteryState() != SyncState.NETWORK_ERROR) {
            getLotteryData(Const.MAX_DAYS_TO_GET_LOTTERY, isMainThread);
        }

        showLongMessage("XS\u0110B (" + resultSyncState.getSyncJackpotState().sign + "), " +
                "XSMB (" + resultSyncState.getSyncLotteryState().sign + ").", isMainThread);
        return resultSyncState.getSyncJackpotState() != SyncState.NETWORK_ERROR
                && resultSyncState.getSyncLotteryState() != SyncState.NETWORK_ERROR;
    }

    public void getAllIfDataIsOld(String screenTimeData, List<Jackpot> screenJackpotList,
                                  List<Lottery> screenLotteries) {
        if (screenTimeData == null || screenJackpotList == null || screenLotteries == null) return;

        if (!screenJackpotList.isEmpty()
                && !JackpotHandler.getLastDate(context).equals(screenJackpotList.get(0).getDateBase())) {
            getJackpotData(true);
        }

        if (!screenLotteries.isEmpty()
                && !LotteryHandler.getLastDate(context).equals(screenLotteries.get(0).getDateBase())) {
            getLotteryData(Const.MAX_DAYS_TO_GET_LOTTERY, true);
        }
    }

    public void getTimeData(boolean isMainThread) {
        new MainThreadBase(() -> updateDataView.showTimeData(""), isMainThread).post();
    }

    public void getJackpotData(boolean isMainThread) {
        List<Jackpot> jackpotList =
                JackpotHandler.getJackpotListByDays(context, Const.DAY_NUMBER_TO_GET_JACKPOT);
        if (jackpotList.isEmpty()) return;
        new MainThreadBase(() -> updateDataView.showJackpotData(jackpotList), isMainThread).post();
    }

    public void getLotteryData(int numberOfDays, boolean isMainThread) {
        List<Lottery> lotteries = LotteryHandler.getLotteryListFromFile(context, numberOfDays);
        if (lotteries.isEmpty()) return;
        new MainThreadBase(() -> updateDataView.showLotteryData(lotteries), isMainThread).post();
    }

    public void updateJackpot(boolean isShowMessage, boolean isMainThread) {
        boolean shouldNotifyNewBridge = !CheckSync.isSyncJackpot(context);
        boolean checkUpdateJackpot = JackpotHandler.updateJackpot(context);
        String message = checkUpdateJackpot
                ? "C\u1eadp nh\u1eadt XS \u0110\u1eb7c Bi\u1ec7t th\u00e0nh c\u00f4ng !"
                : "\u0110\u00e3 x\u1ea3y ra l\u1ed7i khi c\u1eadp nh\u1eadt XS \u0110\u1eb7c Bi\u1ec7t !";
        if (isShowMessage) showMessage(message, isMainThread);
        if (checkUpdateJackpot) {
            getJackpotData(isMainThread);
            handleBridgeNotificationAfterJackpotUpdate(shouldNotifyNewBridge
                    && CheckSync.isSyncJackpot(context));
        }
    }

    public void updateLottery(int numberOfDays, boolean isShowMessage, boolean isMainThread) {
        boolean checkUpdateLottery = LotteryHandler.updateLottery(context, numberOfDays);
        String message = checkUpdateLottery
                ? "C\u1eadp nh\u1eadt XSMB th\u00e0nh c\u00f4ng !"
                : "\u0110\u00e3 x\u1ea3y ra l\u1ed7i khi c\u1eadp nh\u1eadt XSMB !";
        if (isShowMessage) showMessage(message, isMainThread);
        if (checkUpdateLottery) {
            getLotteryData(Const.MAX_DAYS_TO_GET_LOTTERY, isMainThread);
        }
    }

    private void showMessage(String message, boolean isMainThread) {
        new MainThreadBase(() -> updateDataView.showMessage(message), isMainThread).post();
    }

    private void showLongMessage(String message, boolean isMainThread) {
        new MainThreadBase(() -> updateDataView.showLongMessage(message), isMainThread).post();
    }

    private void handleBridgeNotificationAfterJackpotUpdate(boolean shouldNotify) {
        if (!shouldNotify) return;

        List<Jackpot> jackpotList =
                JackpotHandler.getJackpotListByDays(context, Const.DAY_NUMBER_TO_GET_JACKPOT);
        if (jackpotList.isEmpty()) return;
        BridgeNotificationStorageHandler.notifyWinningBridges(context, jackpotList.get(0));
        NewBridge.notify(context, jackpotList);
    }
}
