package com.example.couple.Model.Bridge.Couple;

import com.example.couple.Model.Bridge.Bridge;
import com.example.couple.Model.Time.Cycle.YearCycle;
import com.example.couple.Model.Support.JackpotHistory;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;

@Getter
public class CycleBridge extends Bridge {
    String bridgeName;
    List<YearCycle> yearCycles;
    List<Integer> numbers;
    JackpotHistory jackpotHistory;

    public CycleBridge(String bridgeName, List<YearCycle> yearCycles, JackpotHistory jackpotHistory) {
        this.bridgeName = bridgeName;
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
    public List<Integer> getTouchs() {
        return new ArrayList<>();
    }

    public static CycleBridge getEmpty() {
        return new CycleBridge("", new ArrayList<>(), JackpotHistory.getEmpty());
    }

    public boolean isEmpty() {
        return bridgeName.equals("") || yearCycles.isEmpty() || numbers.isEmpty();
    }

}
