package com.example.couple.Custom.Handler.Sync;

import android.content.Context;

import com.example.couple.Custom.Handler.JackpotHandler;
import com.example.couple.Custom.Handler.LotteryHandler;
import com.example.couple.Model.DateTime.Date.DateBase;
import com.example.couple.Model.DateTime.Time.TimeBase;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class SyncDataState {
    private final Context context;
    SyncState syncDateState;
    SyncState syncJackpotState;
    SyncState syncLotteryState;
    SyncState syncDateDataState;

    public SyncDataState(Context context) {
        this.context = context;
        this.syncDateState = SyncState.DONE;
        this.syncJackpotState = CheckSync.isSyncJackpot(context) ? SyncState.DONE : SyncState.NOT_YET;
        this.syncLotteryState = CheckSync.isSyncLottery(context) ? SyncState.DONE : SyncState.NOT_YET;
        this.syncDateDataState = SyncState.DONE;
    }

    public boolean needToSync() {
        boolean synced = syncJackpotState == SyncState.DONE &&
                syncLotteryState == SyncState.DONE;
        if (synced) return false;
        if (isMissingBeforeYesterday(JackpotHandler.getLastDate(context))) return true;
        if (isMissingBeforeYesterday(LotteryHandler.getLastDate(context))) return true;
        return TimeBase.current().isAfter(new TimeBase(18, 30, 0));
    }

    private boolean isMissingBeforeYesterday(DateBase lastDate) {
        if (lastDate.isEmpty()) return true;
        DateBase yesterday = DateBase.today().addDays(-1);
        return lastDate.isBefore(yesterday);
    }
}
