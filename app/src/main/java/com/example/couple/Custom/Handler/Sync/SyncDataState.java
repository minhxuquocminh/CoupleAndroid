package com.example.couple.Custom.Handler.Sync;

import android.content.Context;

import com.example.couple.Model.DateTime.Time.TimeBase;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class SyncDataState {
    SyncState syncDateState;
    SyncState syncJackpotState;
    SyncState syncLotteryState;
    SyncState syncDateDataState;

    public SyncDataState(Context context) {
        this.syncDateState = CheckSync.isSyncDate(context) ? SyncState.DONE : SyncState.NOT_YET;
        this.syncJackpotState = CheckSync.isSyncJackpot(context) ? SyncState.DONE : SyncState.NOT_YET;
        this.syncLotteryState = CheckSync.isSyncLottery(context) ? SyncState.DONE : SyncState.NOT_YET;
        this.syncDateDataState = CheckSync.isSyncDateData(context) ? SyncState.DONE : SyncState.NOT_YET;
    }

    public boolean needToSync() {
        if (syncDateState != SyncState.DONE) return true;
        boolean synced = syncJackpotState == SyncState.DONE &&
                syncLotteryState == SyncState.DONE && syncDateDataState == SyncState.DONE;
        return !synced && TimeBase.CURRENT().isAfter(new TimeBase(18, 30, 0));
    }
}
