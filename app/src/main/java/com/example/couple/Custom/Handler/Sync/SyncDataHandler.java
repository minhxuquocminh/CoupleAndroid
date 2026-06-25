package com.example.couple.Custom.Handler.Sync;

import android.content.Context;

import com.example.couple.Custom.Const.Const;
import com.example.couple.Custom.Handler.JackpotHandler;
import com.example.couple.Custom.Handler.LotteryHandler;

public class SyncDataHandler {

    public static SyncDataState execute(Context context) {
        SyncDataState syncDataState = new SyncDataState(context);
        if (syncDataState.getSyncJackpotState() != SyncState.DONE) {
            boolean updateState = JackpotHandler.updateJackpot(context);
            boolean syncState = CheckSync.isSyncJackpot(context);
            syncDataState.setSyncJackpotState(getSyncState(updateState, syncState));
        }
        if (syncDataState.getSyncLotteryState() != SyncState.DONE) {
            boolean updateState = LotteryHandler.updateLottery(context, Const.MAX_DAYS_TO_GET_LOTTERY);
            boolean syncState = CheckSync.isSyncLottery(context);
            syncDataState.setSyncLotteryState(getSyncState(updateState, syncState));
        }
        return syncDataState;
    }

    private static SyncState getSyncState(boolean updateState, boolean syncState) {
        if (!updateState) return SyncState.NETWORK_ERROR;
        if (syncState) return SyncState.DONE;
        return SyncState.NOT_YET;
    }
}
