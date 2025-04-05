package com.example.couple.Base.View;

import com.example.couple.View.Bridge.BridgeCombinationActivity;
import com.example.couple.View.Bridge.FindingBridgeActivity;
import com.example.couple.View.Bridge.SelectiveBridgeActivity;
import com.example.couple.View.BridgeHistory.NumberSetHistoryActivity;
import com.example.couple.View.BridgeHistory.SexagenaryCycleActivity;
import com.example.couple.View.Couple.BalanceCoupleActivity;
import com.example.couple.View.JackpotStatistics.JackpotByYearActivity;
import com.example.couple.View.JackpotStatistics.JackpotNextDayActivity;
import com.example.couple.View.JackpotStatistics.JackpotThisYearActivity;
import com.example.couple.View.Lottery.LotteryActivity;
import com.example.couple.View.SubScreen.CycleByYearActivity;
import com.example.couple.View.UpdateDataInfo.AddJackpotManyYearsActivity;
import com.example.couple.View.UpdateDataInfo.UrlAndParamsActivity;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum ActivityType {

    JACKPOT_BY_YEAR(1001, "đặc biệt", JackpotByYearActivity.class),
    LOTTERY(1002, "lô tô", LotteryActivity.class),
    JACKPOT_NEXT_DAY(1003, "đặc biệt ngày mai", JackpotNextDayActivity.class),
    CYCLE(1004, "can chi", SexagenaryCycleActivity.class),

    SELECTIVE_BRIDGE(2001, "tham khảo", SelectiveBridgeActivity.class),
    BALANCE_COUPLE(2002, "bộ số cân bằng", BalanceCoupleActivity.class),
    FINDING_BRIDGE(2003, "soi cầu", FindingBridgeActivity.class),

    SPECIAL_SETS_HISTORY(3001, "nhịp chạy", NumberSetHistoryActivity.class),
    JACKPOT_THIS_YEAR(3002, "đầu đuôi", JackpotThisYearActivity.class),
    CYCLE_BY_YEAR(3003, "đặc biệt theo năm", CycleByYearActivity.class),
    BRIDGE_COMBINATION(3004, "tổ hợp cầu", BridgeCombinationActivity.class),

    UPDATE_MANY_YEAR(4001, "cập nhật", AddJackpotManyYearsActivity.class),
    UPDATE_URL_AND_PARAM(4002, "đường dẫn", UrlAndParamsActivity.class);

    public final int value;
    public final String key;
    public final Class<?> targetClass;

}
