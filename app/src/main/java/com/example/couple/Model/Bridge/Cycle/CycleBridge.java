package com.example.couple.Model.Bridge.Cycle;

import com.example.couple.Model.Bridge.Bridge;
import com.example.couple.Model.Bridge.BridgeType;
import com.example.couple.Model.Bridge.JackpotHistory;
import com.example.couple.Model.DateTime.Date.Cycle.YearCycle;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import lombok.Getter;

@Getter
public class CycleBridge extends Bridge {
    BridgeType bridgeType;
    List<YearCycle> yearCycles;
    List<Integer> numbers;
    JackpotHistory jackpotHistory;

    public CycleBridge(BridgeType bridgeType, List<YearCycle> yearCycles, JackpotHistory jackpotHistory) {
        this.bridgeType = bridgeType;
        this.yearCycles = yearCycles;
        this.numbers = new ArrayList<>();
        for (YearCycle yearCycle : yearCycles) {
            int couple = yearCycle.getYear() % 100;
            if (!numbers.contains(couple)) {
                numbers.add(couple);
            }
        }
        this.jackpotHistory = jackpotHistory;
    }

    @Override
    public String showCompactNumbers() {
        return "";
    }

    @Override
    public String showCompactInfo() {
        return yearCycles.stream()
                .map(YearCycle::showByCouple)
                .collect(Collectors.joining(" "));
    }

    @Override
    public String showDetailInfo() {
        String show = showJackpotInfo();
        show += "\n\n" + (bridgeType == null ? "Cầu can chi" : bridgeType.name) + ":";
        show += "\n" + showCompactInfo();
        show += "\n\nDàn số:\n" + showNumbers();
        return show.trim();
    }

    @Override
    public BridgeType getType() {
        return bridgeType;
    }

    public static CycleBridge getEmpty() {
        return new CycleBridge(null, new ArrayList<>(), JackpotHistory.getEmpty());
    }

    public boolean isEmpty() {
        return bridgeType == null || yearCycles.isEmpty() || numbers.isEmpty();
    }

}
