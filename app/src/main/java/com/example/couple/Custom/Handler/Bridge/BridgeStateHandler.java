package com.example.couple.Custom.Handler.Bridge;

import com.example.couple.Custom.Const.Const;
import com.example.couple.Model.Bridge.Bridge;
import com.example.couple.Model.Bridge.BridgeType;
import com.example.couple.Model.Bridge.CombineBridge;
import com.example.couple.Model.Bridge.Connected.ConnectedSetBridge;
import com.example.couple.Model.Bridge.Cycle.BranchInTwoDaysBridge;
import com.example.couple.Model.Bridge.Cycle.CycleBridge;
import com.example.couple.Model.Bridge.Double.UnappearedBigDoubleBridge;
import com.example.couple.Model.Bridge.Estimated.EstimatedBridge;
import com.example.couple.Model.Bridge.JackpotHistory;
import com.example.couple.Model.Bridge.Mapping.MappingBridge;
import com.example.couple.Model.Bridge.Mapping.TriadMappingBridge;
import com.example.couple.Model.Bridge.NumberSet.NumericSetBridge;
import com.example.couple.Model.Bridge.NumberSet.SpecialSet;
import com.example.couple.Model.Bridge.SyntheticBridge;
import com.example.couple.Model.Bridge.Touch.CombineTouchBridge;
import com.example.couple.Model.Bridge.Touch.ConnectedBridge;
import com.example.couple.Model.Bridge.Touch.LottoTouchBridge;
import com.example.couple.Model.Bridge.Touch.ShadowTouchBridge;
import com.example.couple.Model.Handler.Input;
import com.example.couple.Model.Origin.Jackpot;
import com.example.couple.Model.Origin.Lottery;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BridgeStateHandler {

    public static List<CombineBridge> getCombineBridges(List<Jackpot> jackpotList, List<Lottery> lotteryList,
                                                        List<BridgeType> bridgeTypes, int numberOfDay, List<Input> inputs) {
        List<CombineBridge> combineBridges = new ArrayList<>();
        numberOfDay = Math.min(numberOfDay, jackpotList.size() - 2);
        if (bridgeTypes.contains(BridgeType.COMBINE_TOUCH)) {
            numberOfDay = Math.min(numberOfDay, jackpotList.size() - 14);
        }

        if (bridgeTypes.contains(BridgeType.COMBINE_TOUCH) || bridgeTypes.contains(BridgeType.CONNECTED)) {
            numberOfDay = Math.min(numberOfDay, lotteryList.size() - Const.CONNECTED_BRIDGE_FINDING_DAYS);
        }

        for (int i = 0; i < numberOfDay; i++) {
            Map<BridgeType, Bridge> bridgeList = new HashMap<>();
            // touch
            if (bridgeTypes.contains(BridgeType.COMBINE_TOUCH)) {
                CombineTouchBridge combineTouchBridge = TouchBridgeHandler
                        .getCombineTouchBridge(jackpotList, lotteryList, i);
                bridgeList.put(BridgeType.COMBINE_TOUCH, combineTouchBridge);
            }
            if (bridgeTypes.contains(BridgeType.CONNECTED)) {
                ConnectedBridge connectedBridge = ConnectedBridgeHandler
                        .getConnectedBridge(lotteryList, i, Const.CONNECTED_BRIDGE_FINDING_DAYS,
                                Const.CONNECTED_BRIDGE_MAX_DISPLAY);
                bridgeList.put(BridgeType.CONNECTED, connectedBridge);
            }
            if (bridgeTypes.contains(BridgeType.SYNTHETIC)) {
                SyntheticBridge syntheticBridge = OtherBridgeHandler
                        .getSyntheticBridge(jackpotList, lotteryList, i);
                bridgeList.put(BridgeType.SYNTHETIC, syntheticBridge);
            }
            if (bridgeTypes.contains(BridgeType.LOTTO_TOUCH)) {
                LottoTouchBridge lottoTouchBridge = TouchBridgeHandler
                        .getLottoTouchBridge(lotteryList, i);
                bridgeList.put(BridgeType.LOTTO_TOUCH, lottoTouchBridge);
            }
            if (bridgeTypes.contains(BridgeType.LAST_DAY_SHADOW)) {
                ShadowTouchBridge negativeShadowBridge = TouchBridgeHandler
                        .getLastDayShadowTouchBridge(jackpotList, i);
                bridgeList.put(BridgeType.LAST_DAY_SHADOW, negativeShadowBridge);
            }
            if (bridgeTypes.contains(BridgeType.LAST_WEEK_SHADOW)) {
                ShadowTouchBridge positiveShadowBridge = TouchBridgeHandler
                        .getLastWeekShadowTouchBridge(jackpotList, i);
                bridgeList.put(BridgeType.LAST_WEEK_SHADOW, positiveShadowBridge);
            }
            // mapping, estimated
            if (bridgeTypes.contains(BridgeType.MAPPING)) {
                MappingBridge mappingBridge = MappingBridgeHandler
                        .getMappingBridge(jackpotList, i);
                bridgeList.put(BridgeType.MAPPING, mappingBridge);
            }
            if (bridgeTypes.contains(BridgeType.CONNECTED_SET)) {
                ConnectedSetBridge connectedSetBridge = ConnectedBridgeHandler
                        .getConnectedSetBridge(lotteryList, i,
                                Const.CONNECTED_BRIDGE_FINDING_DAYS, Const.CONNECTED_BRIDGE_MAX_DISPLAY);
                bridgeList.put(BridgeType.CONNECTED_SET, connectedSetBridge);
            }
            if (bridgeTypes.contains(BridgeType.ESTIMATED)) {
                EstimatedBridge estimatedBridge = EstimatedBridgeHandler
                        .getEstimatedBridge(jackpotList, i);
                bridgeList.put(BridgeType.ESTIMATED, estimatedBridge);
            }
            if (bridgeTypes.contains(BridgeType.RIGHT_MAPPING)) {
                MappingBridge rightMappingBridge = MappingBridgeHandler
                        .getRightMappingBridge(jackpotList, i);
                bridgeList.put(BridgeType.RIGHT_MAPPING, rightMappingBridge);
            }
            if (bridgeTypes.contains(BridgeType.COMPATIBLE_CYCLE)) {
                CycleBridge compatibleBridge = CycleBridgeHandler
                        .getCompatibleCycleBridge(jackpotList, i);
                bridgeList.put(BridgeType.COMPATIBLE_CYCLE, compatibleBridge);
            }
            if (bridgeTypes.contains(BridgeType.INCOMPATIBLE_CYCLE)) {
                CycleBridge incompatibleBridge = CycleBridgeHandler
                        .getIncompatibleCycleBridge(jackpotList, i);
                bridgeList.put(BridgeType.INCOMPATIBLE_CYCLE, incompatibleBridge);
            }
            if (bridgeTypes.contains(BridgeType.UNAPPEARED_BIG_DOUBLE)) {
                UnappearedBigDoubleBridge unappearedBigDoubleBridge =
                        OtherBridgeHandler.getUnappearedBigDoubleBridge(jackpotList, i);
                bridgeList.put(BridgeType.UNAPPEARED_BIG_DOUBLE, unappearedBigDoubleBridge);
            }

            if (bridgeTypes.contains(BridgeType.TRIAD_MAPPING)) {
                TriadMappingBridge triadMappingBridge = MappingBridgeHandler
                        .getAnyMappingBridge(jackpotList, i);
                bridgeList.put(BridgeType.TRIAD_MAPPING, triadMappingBridge);
            }
            if (bridgeTypes.contains(BridgeType.BRANCH_IN_TWO_DAYS_BRIDGE)) {
                BranchInTwoDaysBridge branchInTwoDaysBridge = CycleBridgeHandler
                        .getBranchInTwoDaysBridge(jackpotList, i);
                bridgeList.put(BridgeType.BRANCH_IN_TWO_DAYS_BRIDGE, branchInTwoDaysBridge);
            }
            // jackpot
            Jackpot jackpot = i - 1 >= 0 ? jackpotList.get(i - 1) : Jackpot.getEmpty();
            // special set
            if (bridgeTypes.contains(BridgeType.BIG_DOUBLE)) {
                NumericSetBridge bigDoubleSet = new NumericSetBridge(SpecialSet.BIG_DOUBLE.name,
                        SpecialSet.BIG_DOUBLE.values, new JackpotHistory(i, jackpot));
                bridgeList.put(BridgeType.BIG_DOUBLE, bigDoubleSet);
            }
            if (bridgeTypes.contains(BridgeType.SAME_DOUBLE)) {
                NumericSetBridge sameDoubleSet = new NumericSetBridge(SpecialSet.DOUBLE.name,
                        SpecialSet.DOUBLE.values, new JackpotHistory(i, jackpot));
                bridgeList.put(BridgeType.SAME_DOUBLE, sameDoubleSet);
            }
            if (bridgeTypes.contains(BridgeType.POSITIVE_DOUBLE)) {
                NumericSetBridge nearDoubleSet = new NumericSetBridge(SpecialSet.POSITIVE_DOUBLE.name,
                        SpecialSet.POSITIVE_DOUBLE.values, new JackpotHistory(i, jackpot));
                bridgeList.put(BridgeType.POSITIVE_DOUBLE, nearDoubleSet);
            }

            for (Input input : inputs) {
                if (!input.isError() && !input.isEmpty()) {
                    NumericSetBridge set = new NumericSetBridge(input.getInputType().name,
                            input.getNumbers(), new JackpotHistory(i, jackpot));
                    bridgeList.put(BridgeType.INPUT, set);
                }
            }

            CombineBridge combineBridge = new CombineBridge(bridgeList, new JackpotHistory(i, jackpot));
            combineBridges.add(combineBridge);
        }

        return combineBridges;
    }

}
