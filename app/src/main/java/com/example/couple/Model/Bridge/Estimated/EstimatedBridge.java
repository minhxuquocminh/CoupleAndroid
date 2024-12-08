package com.example.couple.Model.Bridge.Estimated;

import com.example.couple.Custom.Const.Const;
import com.example.couple.Custom.Handler.CoupleHandler;
import com.example.couple.Model.Bridge.Bridge;
import com.example.couple.Model.Bridge.BridgeType;
import com.example.couple.Model.Bridge.JackpotHistory;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import lombok.Getter;

@Getter
public class EstimatedBridge extends Bridge {
    List<PeriodHistory> periodHistories3;
    List<PeriodHistory> periodHistories4;
    JackpotHistory jackpotHistory;
    List<PeriodHistory> periodHistories;
    List<Integer> numbers;

    public EstimatedBridge(List<PeriodHistory> periodHistories3,
                           List<PeriodHistory> periodHistories4, JackpotHistory jackpotHistory) {
        this.periodHistories3 = periodHistories3;
        this.periodHistories4 = periodHistories4;
        this.jackpotHistory = jackpotHistory;
        this.periodHistories = new ArrayList<>();
        this.numbers = new ArrayList<>();

        for (PeriodHistory periodHistory3 : periodHistories3) {
            int count = 0;
            for (PeriodHistory periodHistory4 : periodHistories4) {
                if (periodHistory3.getEndDate().equals(periodHistory4.getEndDate())) count++;
            }
            if (count == 0) periodHistories.add(periodHistory3);
        }

        List<Integer> inits = new ArrayList<>();
        for (PeriodHistory periodHistory : periodHistories) {
            inits.addAll(CoupleHandler
                    .getPeriodNumbers(periodHistory.getLastNumber(), Const.AMPLITUDE_OF_PERIOD));
        }

        for (int number : inits) {
            if (!numbers.contains(number)) {
                numbers.add(number);
            }
        }

        Collections.sort(numbers);

    }

    public static EstimatedBridge getEmpty() {
        return new EstimatedBridge(new ArrayList<>(), new ArrayList<>(), JackpotHistory.getEmpty());
    }

    public boolean isEmpty() {
        return periodHistories.isEmpty();
    }

    @Override
    public String showCompactNumbers() {
        return "";
    }

    @Override
    public String getBridgeName() {
        return BridgeType.ESTIMATED.name;
    }

}
