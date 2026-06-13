package com.example.couple.Custom.Handler.Bridge;

import com.example.couple.Model.Statistics.BCouple;

import java.util.ArrayList;
import java.util.List;

public class BCoupleBridgeHandler {

    public static List<BCouple> getBalanceCouples(BCouple bcp1, BCouple bcp2) {
        List<BCouple> results = new ArrayList<>();
        results.add(bcp1.balanceOne(bcp2));
        results.add(bcp1.balanceTwo(bcp2));
        results.add(bcp1.balanceThree(bcp2));
        results.add(bcp1.balanceFour(bcp2));
        return results;
    }

}