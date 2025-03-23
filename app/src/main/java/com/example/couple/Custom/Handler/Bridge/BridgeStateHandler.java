package com.example.couple.Custom.Handler.Bridge;

import com.example.couple.Custom.Const.Const;
import com.example.couple.Model.Bridge.Bridge;
import com.example.couple.Model.Bridge.BridgeType;
import com.example.couple.Model.Bridge.CombineBridge;
import com.example.couple.Model.Bridge.JackpotHistory;
import com.example.couple.Model.Handler.Input;
import com.example.couple.Model.Origin.Jackpot;
import com.example.couple.Model.Origin.Lottery;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

public class BridgeStateHandler {

    public static List<CombineBridge> getCombineBridges(List<Jackpot> jackpotList, List<Lottery> lotteryList,
                                                        Set<BridgeType> bridgeTypes, int numberOfDay, List<Input> inputs) {
        List<CombineBridge> combineBridges = new ArrayList<>();
        numberOfDay = Math.min(numberOfDay, jackpotList.size() - 2);
        if (bridgeTypes.contains(BridgeType.COMBINE_TOUCH)) {
            numberOfDay = Math.min(numberOfDay, jackpotList.size() - 14);
        }

        if (bridgeTypes.contains(BridgeType.COMBINE_TOUCH) || bridgeTypes.contains(BridgeType.CONNECTED)) {
            numberOfDay = Math.min(numberOfDay, lotteryList.size() - Const.CONNECTED_BRIDGE_FINDING_DAYS);
        }

        for (int i = 0; i < numberOfDay; i++) {
            Jackpot jackpot = i - 1 >= 0 ? jackpotList.get(i - 1) : Jackpot.getEmpty();
            JackpotHistory jackpotHistory = new JackpotHistory(i, jackpot);
            int index = i;
            Map<BridgeType, Bridge> bridgeMap = bridgeTypes.stream()
                    .map(type -> type.getBridge(jackpotList, lotteryList, inputs, index)).filter(Objects::nonNull)
                    .collect(Collectors.toMap(bridge -> bridge != null ? bridge.getType() : null, Function.identity()));
            CombineBridge combineBridge = new CombineBridge(bridgeMap, jackpotHistory);
            combineBridges.add(combineBridge);
        }

        return combineBridges;
    }

}
