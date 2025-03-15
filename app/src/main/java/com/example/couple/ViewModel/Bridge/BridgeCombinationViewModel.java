package com.example.couple.ViewModel.Bridge;

import android.content.Context;

import com.example.couple.Custom.Const.Const;
import com.example.couple.Custom.Const.TimeInfo;
import com.example.couple.Custom.Handler.Bridge.BridgeStateHandler;
import com.example.couple.Custom.Handler.JackpotHandler;
import com.example.couple.Custom.Handler.LotteryHandler;
import com.example.couple.Model.Bridge.BridgeType;
import com.example.couple.Model.Bridge.CombineBridge;
import com.example.couple.Model.Handler.Input;
import com.example.couple.Model.Origin.Jackpot;
import com.example.couple.Model.Origin.Lottery;
import com.example.couple.View.Bridge.BridgeCombinationView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class BridgeCombinationViewModel {
    BridgeCombinationView view;
    Context context;

    public BridgeCombinationViewModel(BridgeCombinationView view, Context context) {
        this.view = view;
        this.context = context;
    }

    public void getAllData() {
        List<Jackpot> jackpotList = JackpotHandler.getReverseJackpotListByDays(context, TimeInfo.DAY_OF_YEAR);
        List<Lottery> lotteryList = LotteryHandler.getLotteryListFromFile(context, Const.MAX_DAYS_TO_GET_LOTTERY);
        if (jackpotList.isEmpty() || lotteryList.isEmpty()) {
            view.showMessage("Lỗi không láy được dữ liệu Xổ số.");
            return;
        }
        view.showAllData(jackpotList, lotteryList);
    }

    public void getAllBridgeToday(List<Jackpot> jackpotList, List<Lottery> lotteryList) {
        if (jackpotList.isEmpty() || lotteryList.isEmpty()) return;
        List<BridgeType> bridgeTypes = Arrays.asList(BridgeType.COMBINE_TOUCH, BridgeType.CONNECTED,
                BridgeType.SYNTHETIC, BridgeType.LOTTO_TOUCH, BridgeType.LAST_DAY_SHADOW, BridgeType.LAST_WEEK_SHADOW,
                BridgeType.MAPPING, BridgeType.CONNECTED_SET, BridgeType.ESTIMATED);
        List<CombineBridge> combineBridges = BridgeStateHandler.getCombineBridges(jackpotList,
                lotteryList, bridgeTypes, 1, new ArrayList<>());
        if (!bridgeTypes.isEmpty()) {
            view.showAllBridgeToday(combineBridges.get(0).getBridgeMap());
        }
    }

    public void getCombineBridgeList(List<Jackpot> jackpotList, List<Lottery> lotteryList, int numberOfDay,
                                     Map<BridgeType, Boolean> bridgeTypeFlag, List<Input> inputs) {
        List<BridgeType> bridgeTypes = bridgeTypeFlag.entrySet().stream()
                .filter(Map.Entry::getValue).map(Map.Entry::getKey).collect(Collectors.toList());
        if (bridgeTypes.contains(BridgeType.CONNECTED) && numberOfDay > lotteryList.size() - Const.CONNECTED_BRIDGE_FINDING_DAYS) {
            view.showMessage("Giới hạn số ngày cho cầu liên thông là " +
                    (lotteryList.size() - Const.CONNECTED_BRIDGE_FINDING_DAYS) + " ngày");
        }

        String notifMessage = "";
        for (Input input : inputs) {
            if (input.isError()) {
                notifMessage += " " + input.getInputType().name + ";";
            }
        }
        if (!notifMessage.isEmpty()) {
            view.showMessage("Có lỗi nhập tại:" + notifMessage);
        }

        List<CombineBridge> combineBridges = BridgeStateHandler.getCombineBridges(jackpotList,
                lotteryList, bridgeTypes, numberOfDay, inputs);
        if (combineBridges.isEmpty()) {
            view.showMessage("Không tìm thấy cầu.");
        } else {
            view.showCombineBridgeList(combineBridges);
        }
    }
}
