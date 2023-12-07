package com.example.couple.Model.Bridge.Couple;

import com.example.couple.Model.Bridge.Bridge;
import com.example.couple.Model.Support.PairConnectedSupport;
import com.example.couple.Model.Support.JackpotHistory;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;

@Getter
public class PairConnectedBridge extends Bridge {
    String bridgeName;
    List<PairConnectedSupport> pairConnectedSupports;
    JackpotHistory jackpotHistory;
    List<Integer> numbers;

    public PairConnectedBridge(String bridgeName,
                               List<PairConnectedSupport> pairConnectedSupports,
                               JackpotHistory jackpotHistory) {
        this.bridgeName = bridgeName;
        this.pairConnectedSupports = pairConnectedSupports;
        this.jackpotHistory = jackpotHistory;
        this.numbers = new ArrayList<>();
    }

    @Override
    public String showCompactNumbers() {
        return null;
    }

    public static PairConnectedBridge getEmpty() {
        return new PairConnectedBridge("", new ArrayList<>(), JackpotHistory.getEmpty());
    }

    public boolean isEmpty() {
        return pairConnectedSupports.isEmpty() || numbers.isEmpty();
    }
}
