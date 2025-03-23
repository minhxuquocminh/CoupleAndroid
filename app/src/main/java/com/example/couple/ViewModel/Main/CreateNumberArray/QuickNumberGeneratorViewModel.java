package com.example.couple.ViewModel.Main.CreateNumberArray;

import android.content.Context;

import com.example.couple.Custom.Handler.Bridge.BridgeStateHandler;
import com.example.couple.Custom.Handler.History.HistoryHandler;
import com.example.couple.Model.Bridge.BridgeType;
import com.example.couple.Model.Bridge.CombineBridge;
import com.example.couple.Model.Bridge.NumberSet.NumberSetHistory;
import com.example.couple.Model.Bridge.NumberSet.NumberSetType;
import com.example.couple.Model.Origin.Jackpot;
import com.example.couple.Model.Origin.Lottery;
import com.example.couple.View.Main.CreateNumberArray.QuickNumberGeneratorView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class QuickNumberGeneratorViewModel {
    QuickNumberGeneratorView view;
    Context context;

    public QuickNumberGeneratorViewModel(QuickNumberGeneratorView view, Context context) {
        this.view = view;
        this.context = context;
    }

    public void getCombineBridgesToday(List<Jackpot> jackpotList, List<Lottery> lotteries, Set<BridgeType> bridgeTypes) {
        List<CombineBridge> combineBridges = BridgeStateHandler.getCombineBridges(jackpotList,
                lotteries, bridgeTypes, 1, new ArrayList<>());
        if (combineBridges.isEmpty()) {
            view.showMessage("Không tìm thấy số nào.");
        } else {
            view.showCombineBridgesToday(combineBridges.get(0));
        }
    }

    public void getLongBeatNumbers(List<Jackpot> jackpotList) {
        List<NumberSetHistory> histories =HistoryHandler.getCompactNumberSetsHistory(jackpotList,
                Arrays.asList(NumberSetType.values()),40,30,79);
        view.showLongBeatNumbers(histories);
    }

    public void getMappingAndTouchState(List<Jackpot> jackpotList, List<Lottery> lotteries) {
        List<CombineBridge> combineBridges = BridgeStateHandler.getCombineBridges(jackpotList, lotteries,
                new HashSet<>(Arrays.asList(BridgeType.COMBINE_TOUCH, BridgeType.CONNECTED, BridgeType.MAPPING)), 16, new ArrayList<>());
        view.showMappingAndTouchState(combineBridges);
    }

    public void getSetsState(List<Jackpot> jackpotList) {
        List<CombineBridge> combineBridges = BridgeStateHandler.getCombineBridges(jackpotList, new ArrayList<>(),
                new HashSet<>(Arrays.asList(BridgeType.BIG_DOUBLE, BridgeType.SAME_DOUBLE)), 60, new ArrayList<>());
        view.showSetsState(combineBridges);
    }

}
