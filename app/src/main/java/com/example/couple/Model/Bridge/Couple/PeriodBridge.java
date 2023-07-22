package com.example.couple.Model.Bridge.Couple;

import com.example.couple.Custom.Const.Const;
import com.example.couple.Custom.Handler.CoupleHandler;
import com.example.couple.Model.Bridge.Bridge;
import com.example.couple.Model.Support.History;
import com.example.couple.Model.Support.JackpotHistory;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import lombok.Getter;

@Getter
public class PeriodBridge extends Bridge {
    List<History> periodHistories3;
    List<History> periodHistories4;
    JackpotHistory jackpotHistory;
    List<History> periodHistories;
    List<Integer> numbers;

    public PeriodBridge(List<History> periodHistories3,
                        List<History> periodHistories4, JackpotHistory jackpotHistory) {
        this.periodHistories3 = periodHistories3;
        this.periodHistories4 = periodHistories4;
        this.jackpotHistory = jackpotHistory;
        this.periodHistories = new ArrayList<>();
        this.numbers = new ArrayList<>();

        for (History history3 : periodHistories3) {
            int count = 0;
            for (History history4 : periodHistories4) {
                if (history3.getEndDate().equals(history4.getEndDate())) count++;
            }
            if (count == 0) periodHistories.add(history3);
        }

        List<Integer> inits = new ArrayList<>();
        for (History history : periodHistories) {
            inits.addAll(CoupleHandler
                    .getPeriodNumbers(history.getLastNumber(), Const.AMPLITUDE_OF_PERIOD_BRIDGE));
        }

        for (int number : inits) {
            if (!numbers.contains(number)) {
                numbers.add(number);
            }
        }

        Collections.sort(numbers);

    }

    public static PeriodBridge getEmpty() {
        return new PeriodBridge(new ArrayList<>(), new ArrayList<>(), JackpotHistory.getEmpty());
    }

    public boolean isEmpty() {
        return periodHistories.isEmpty();
    }

    @Override
    public List<Integer> getTouchs() {
        return new ArrayList<>();
    }

    @Override
    public String getBridgeName() {
        return Const.PERIOD_BRIDGE_NAME;
    }

}
