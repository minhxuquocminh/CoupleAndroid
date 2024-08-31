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
        this.syncDateState = CheckSync.isSyncDate(context) ? SyncState.OK : SyncState.NOT_YET;
        this.syncJackpotState = CheckSync.isSyncJackpot(context) ? SyncState.OK : SyncState.NOT_YET;
        this.syncLotteryState = CheckSync.isSyncLottery(context) ? SyncState.OK : SyncState.NOT_YET;
        this.syncDateDataState = CheckSync.isSyncDateData(context) ? SyncState.OK : SyncState.NOT_YET;
    }

    public boolean needToSync() {
        if (syncDateState != SyncState.OK) return true;
        boolean synced = syncJackpotState == SyncState.OK &&
                syncLotteryState == SyncState.OK && syncDateDataState == SyncState.OK;
        return !synced && TimeBase.CURRENT().isAfter(new TimeBase(18, 30, 0));
    }
}
