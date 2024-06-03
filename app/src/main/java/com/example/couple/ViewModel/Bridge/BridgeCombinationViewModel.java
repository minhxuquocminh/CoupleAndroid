package com.example.couple.ViewModel.Bridge;

import android.content.Context;

import com.example.couple.Base.Handler.NumberBase;
import com.example.couple.Custom.Const.Const;
import com.example.couple.Custom.Const.TimeInfo;
import com.example.couple.Custom.Handler.Bridge.ConnectedBridgeHandler;
import com.example.couple.Custom.Handler.Bridge.CycleBridgeHandler;
import com.example.couple.Custom.Handler.Bridge.EstimatedBridgeHandler;
import com.example.couple.Custom.Handler.Bridge.MappingBridgeHandler;
import com.example.couple.Custom.Handler.Bridge.OtherBridgeHandler;
import com.example.couple.Custom.Handler.Bridge.TouchBridgeHandler;
import com.example.couple.Custom.Handler.JackpotHandler;
import com.example.couple.Custom.Handler.LotteryHandler;
import com.example.couple.Custom.Handler.NumberArrayHandler;
import com.example.couple.Model.Bridge.Bridge;
import com.example.couple.Model.Bridge.BridgeType;
import com.example.couple.Model.Bridge.CombineBridge;
import com.example.couple.Model.Bridge.Couple.BranchInTwoDaysBridge;
import com.example.couple.Model.Bridge.Couple.ConnectedSetBridge;
import com.example.couple.Model.Bridge.Couple.CycleBridge;
import com.example.couple.Model.Bridge.Couple.EstimatedBridge;
import com.example.couple.Model.Bridge.Couple.MappingBridge;
import com.example.couple.Model.Bridge.Couple.SpecialSetBridge;
import com.example.couple.Model.Bridge.Couple.TriadMappingBridge;
import com.example.couple.Model.Bridge.Couple.UnappearedBigDoubleBridge;
import com.example.couple.Model.Bridge.Single.CombineTouchBridge;
import com.example.couple.Model.Bridge.Single.ConnectedBridge;
import com.example.couple.Model.Bridge.Single.LottoTouchBridge;
import com.example.couple.Model.Bridge.Single.ShadowTouchBridge;
import com.example.couple.Model.Origin.Jackpot;
import com.example.couple.Model.Origin.Lottery;
import com.example.couple.Model.Set.SpecialSet;
import com.example.couple.Model.Support.JackpotHistory;
import com.example.couple.View.Bridge.BridgeCombinationView;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class BridgeCombinationViewModel {
    BridgeCombinationView view;
    Context context;

    public BridgeCombinationViewModel(BridgeCombinationView view, Context context) {
        this.view = view;
        this.context = context;
    }

    public void getAllData() {
        List<Jackpot> jackpotList = JackpotHandler.getReserveJackpotListFromFile(context, TimeInfo.DAY_OF_YEAR);
        List<Lottery> lotteryList = LotteryHandler.getLotteryListFromFile(context, Const.MAX_DAYS_TO_GET_LOTTERY);
        if (jackpotList.isEmpty() || lotteryList.isEmpty()) {
            view.showMessage("Lỗi không láy được dữ liệu Xổ số.");
            return;
        }
        view.showAllData(jackpotList, lotteryList);
    }

    public void getAllBridgeToday(List<Jackpot> jackpotList, List<Lottery> lotteryList) {
        if (!jackpotList.isEmpty()) {
            List<Bridge> bridgeList = new ArrayList<>();
            // touch
            CombineTouchBridge combineTouchBridge =
                    TouchBridgeHandler.getCombineTouchBridge(jackpotList, lotteryList, 0);
            bridgeList.add(combineTouchBridge);
            ConnectedBridge connectedBridge = ConnectedBridgeHandler.getConnectedBridge(lotteryList,
                    0, Const.CONNECTED_BRIDGE_FINDING_DAYS, Const.CONNECTED_BRIDGE_MAX_DISPLAY);
            bridgeList.add(connectedBridge);
            ShadowTouchBridge shadowTouchBridge = TouchBridgeHandler
                    .getShadowTouchBridge(jackpotList, 0);
            bridgeList.add(shadowTouchBridge);
            LottoTouchBridge lottoTouchBridge =
                    TouchBridgeHandler.getLottoTouchBridge(lotteryList, 0);
            bridgeList.add(lottoTouchBridge);
            ShadowTouchBridge negativeShadowBridge =
                    TouchBridgeHandler.getNegativeShadowTouchBridge(jackpotList, 0);
            bridgeList.add(negativeShadowBridge);
            ShadowTouchBridge positiveShadowBridge =
                    TouchBridgeHandler.getPositiveShadowTouchBridge(jackpotList, 0);
            bridgeList.add(positiveShadowBridge);
            // mapping, estimated
            MappingBridge mappingBridge =
                    MappingBridgeHandler.getMappingBridge(jackpotList, 0);
            bridgeList.add(mappingBridge);
            ConnectedSetBridge connectedSetBridge =
                    ConnectedBridgeHandler.getConnectedSetBridge(lotteryList, 0,
                            Const.CONNECTED_BRIDGE_FINDING_DAYS, Const.CONNECTED_BRIDGE_MAX_DISPLAY);
            bridgeList.add(connectedSetBridge);
            EstimatedBridge estimatedBridge = EstimatedBridgeHandler.getEstimatedBridge(jackpotList, 0);
            bridgeList.add(estimatedBridge);
            CombineBridge combineBridge = new CombineBridge(bridgeList,
                    new JackpotHistory(0, Jackpot.getEmpty()));
            view.showAllBridgeToday(combineBridge);
        }
    }

    public void getCombineBridgeList(List<Jackpot> jackpotList, List<Lottery> lotteryList, int numberOfDay,
                                     Map<BridgeType, Boolean> bridgeTypeFlag, String setData, String touchData,
                                     String sumData, String branchData, String headData, String tailData, String combineData) {
        List<CombineBridge> combineBridges = new ArrayList<>();
        if (Boolean.TRUE.equals(bridgeTypeFlag.get(BridgeType.CONNECTED))
                && numberOfDay > lotteryList.size() - Const.CONNECTED_BRIDGE_FINDING_DAYS) {
            view.showMessage("Đặt lại giới hạn số ngày cho cầu liên thông là " +
                    (lotteryList.size() - Const.CONNECTED_BRIDGE_FINDING_DAYS) + " ngày");
        }
        int newDayNumber = Boolean.TRUE.equals(bridgeTypeFlag.get(BridgeType.CONNECTED)) &&
                numberOfDay > lotteryList.size() - Const.CONNECTED_BRIDGE_FINDING_DAYS ?
                lotteryList.size() - Const.CONNECTED_BRIDGE_FINDING_DAYS : numberOfDay;
        for (int i = 0; i < newDayNumber; i++) {
            List<Bridge> bridgeList = new ArrayList<>();
            // touch
            if (Boolean.TRUE.equals(bridgeTypeFlag.get(BridgeType.COMBINE_TOUCH))) {
                CombineTouchBridge combineTouchBridge = TouchBridgeHandler
                        .getCombineTouchBridge(jackpotList, lotteryList, i);
                bridgeList.add(combineTouchBridge);
            }
            if (Boolean.TRUE.equals(bridgeTypeFlag.get(BridgeType.CONNECTED))) {
                ConnectedBridge connectedBridge = ConnectedBridgeHandler
                        .getConnectedBridge(lotteryList, i, Const.CONNECTED_BRIDGE_FINDING_DAYS,
                                Const.CONNECTED_BRIDGE_MAX_DISPLAY);
                bridgeList.add(connectedBridge);
            }
            if (Boolean.TRUE.equals(bridgeTypeFlag.get(BridgeType.SHADOW_TOUCH))) {
                ShadowTouchBridge shadowTouchBridge = TouchBridgeHandler
                        .getShadowTouchBridge(jackpotList, i);
                bridgeList.add(shadowTouchBridge);
            }
            if (Boolean.TRUE.equals(bridgeTypeFlag.get(BridgeType.LOTTO_TOUCH))) {
                LottoTouchBridge lottoTouchBridge = TouchBridgeHandler
                        .getLottoTouchBridge(lotteryList, i);
                bridgeList.add(lottoTouchBridge);
            }
            if (Boolean.TRUE.equals(bridgeTypeFlag.get(BridgeType.NEGATIVE_SHADOW))) {
                ShadowTouchBridge negativeShadowBridge = TouchBridgeHandler
                        .getNegativeShadowTouchBridge(jackpotList, i);
                bridgeList.add(negativeShadowBridge);
            }
            if (Boolean.TRUE.equals(bridgeTypeFlag.get(BridgeType.POSITIVE_SHADOW))) {
                ShadowTouchBridge positiveShadowBridge = TouchBridgeHandler
                        .getPositiveShadowTouchBridge(jackpotList, i);
                bridgeList.add(positiveShadowBridge);
            }
            // mapping, estimated
            if (Boolean.TRUE.equals(bridgeTypeFlag.get(BridgeType.MAPPING))) {
                MappingBridge mappingBridge = MappingBridgeHandler
                        .getMappingBridge(jackpotList, i);
                bridgeList.add(mappingBridge);
            }
            if (Boolean.TRUE.equals(bridgeTypeFlag.get(BridgeType.CONNECTED_SET))) {
                ConnectedSetBridge connectedSetBridge = ConnectedBridgeHandler
                        .getConnectedSetBridge(lotteryList, i,
                                Const.CONNECTED_BRIDGE_FINDING_DAYS, Const.CONNECTED_BRIDGE_MAX_DISPLAY);
                bridgeList.add(connectedSetBridge);
            }
            if (Boolean.TRUE.equals(bridgeTypeFlag.get(BridgeType.ESTIMATED))) {
                EstimatedBridge estimatedBridge = EstimatedBridgeHandler
                        .getEstimatedBridge(jackpotList, i);
                bridgeList.add(estimatedBridge);
            }
            if (Boolean.TRUE.equals(bridgeTypeFlag.get(BridgeType.RIGHT_MAPPING))) {
                MappingBridge rightMappingBridge = MappingBridgeHandler
                        .getRightMappingBridge(jackpotList, i);
                bridgeList.add(rightMappingBridge);
            }
            if (Boolean.TRUE.equals(bridgeTypeFlag.get(BridgeType.COMPATIBLE_CYCLE))) {
                CycleBridge compatibleBridge = CycleBridgeHandler
                        .getCompatibleCycleBridge(jackpotList, i);
                bridgeList.add(compatibleBridge);
            }
            if (Boolean.TRUE.equals(bridgeTypeFlag.get(BridgeType.INCOMPATIBLE_CYCLE))) {
                CycleBridge incompatibleBridge = CycleBridgeHandler
                        .getIncompatibleCycleBridge(jackpotList, i);
                bridgeList.add(incompatibleBridge);
            }
            if (Boolean.TRUE.equals(bridgeTypeFlag.get(BridgeType.UNAPPEARED_BIG_DOUBLE))) {
                UnappearedBigDoubleBridge unappearedBigDoubleBridge =
                        OtherBridgeHandler.getUnappearedBigDoubleBridge(jackpotList, i);
                bridgeList.add(unappearedBigDoubleBridge);
            }

            if (Boolean.TRUE.equals(bridgeTypeFlag.get(BridgeType.TRIAD_MAPPING))) {
                TriadMappingBridge triadMappingBridge = MappingBridgeHandler
                        .getAnyMappingBridge(jackpotList, i);
                bridgeList.add(triadMappingBridge);
            }
            if (Boolean.TRUE.equals(bridgeTypeFlag.get(BridgeType.BRANCH_IN_TWO_DAYS_BRIDGE))) {
                BranchInTwoDaysBridge branchInTwoDaysBridge = CycleBridgeHandler
                        .getBranchInTwoDaysBridge(jackpotList, i);
                bridgeList.add(branchInTwoDaysBridge);
            }
            // jackpot
            Jackpot jackpot = i - 1 >= 0 ? jackpotList.get(i - 1) : Jackpot.getEmpty();
            // special set
            if (Boolean.TRUE.equals(bridgeTypeFlag.get(BridgeType.BIG_DOUBLE))) {
                SpecialSetBridge bigDoubleSet = new SpecialSetBridge(SpecialSet.BIG_DOUBLE.name,
                        SpecialSet.BIG_DOUBLE.values, new JackpotHistory(i, jackpot));
                bridgeList.add(bigDoubleSet);
            }
            if (Boolean.TRUE.equals(bridgeTypeFlag.get(BridgeType.SAME_DOUBLE))) {
                SpecialSetBridge sameDoubleSet = new SpecialSetBridge(SpecialSet.DOUBLE.name,
                        SpecialSet.DOUBLE.values, new JackpotHistory(i, jackpot));
                bridgeList.add(sameDoubleSet);
            }
            if (Boolean.TRUE.equals(bridgeTypeFlag.get(BridgeType.POSITIVE_DOUBLE))) {
                SpecialSetBridge nearDoubleSet = new SpecialSetBridge(SpecialSet.POSITIVE_DOUBLE.name,
                        SpecialSet.POSITIVE_DOUBLE.values, new JackpotHistory(i, jackpot));
                bridgeList.add(nearDoubleSet);
            }
            // other set
            String notifMessage = "";
            List<Integer> setList1 = NumberBase.verifyNumberArray(setData, 1);
            List<Integer> setList2 = NumberBase.verifyNumberArray(setData, 2);
            if (!setData.isEmpty() && setList1.isEmpty() && setList2.isEmpty())
                notifMessage += "bộ;";
            if (!setList1.isEmpty()) {
                SpecialSetBridge set = new SpecialSetBridge("Bộ số",
                        NumberArrayHandler.getSetsBySingles(setList1), new JackpotHistory(i, jackpot));
                bridgeList.add(set);
            } else {
                if (!setList2.isEmpty()) {
                    SpecialSetBridge set = new SpecialSetBridge("Bộ số",
                            NumberArrayHandler.getSetsByCouples(setList2), new JackpotHistory(i, jackpot));
                    bridgeList.add(set);
                }
            }

            List<Integer> touchList = NumberBase.verifyNumberArray(touchData, 1);
            if (!touchData.isEmpty() && touchList.isEmpty()) notifMessage += "chạm;";
            if (!touchList.isEmpty()) {
                SpecialSetBridge set = new SpecialSetBridge("Chạm",
                        NumberArrayHandler.getTouchs(touchList), new JackpotHistory(i, jackpot));
                bridgeList.add(set);
            }
            List<Integer> sumList = NumberBase.verifyNumberArray(sumData, 1);
            if (!sumData.isEmpty() && sumList.isEmpty()) notifMessage += "tổng;";
            if (!sumList.isEmpty()) {
                SpecialSetBridge set = new SpecialSetBridge("Tổng",
                        NumberArrayHandler.getSums(sumList), new JackpotHistory(i, jackpot));
                bridgeList.add(set);
            }
            List<Integer> branchList = NumberBase.verifyNumberArray(branchData, 2);
            if (!branchData.isEmpty() && branchList.isEmpty()) notifMessage += "chi;";
            if (!branchList.isEmpty()) {
                SpecialSetBridge set = new SpecialSetBridge("Chi",
                        NumberArrayHandler.getBranches(branchList), new JackpotHistory(i, jackpot));
                bridgeList.add(set);
            }
            List<Integer> headList = NumberBase.verifyNumberArray(headData, 1);
            if (!headData.isEmpty() && headList.isEmpty()) notifMessage += "đầu;";
            if (!headList.isEmpty()) {
                SpecialSetBridge set = new SpecialSetBridge("Đầu",
                        NumberArrayHandler.getHeads(headList), new JackpotHistory(i, jackpot));
                bridgeList.add(set);
            }
            List<Integer> tailList = NumberBase.verifyNumberArray(tailData, 1);
            if (!tailData.isEmpty() && tailList.isEmpty()) notifMessage += "đuôi;";
            if (!tailList.isEmpty()) {
                SpecialSetBridge set = new SpecialSetBridge("Đuôi",
                        NumberArrayHandler.getTails(tailList), new JackpotHistory(i, jackpot));
                bridgeList.add(set);
            }
            List<Integer> combineList = NumberBase.verifyNumberArray(combineData, 2);
            if (!combineData.isEmpty() && combineList.isEmpty()) notifMessage += "kết hợp;";
            if (!combineList.isEmpty()) {
                SpecialSetBridge set = new SpecialSetBridge("Kết hợp",
                        combineList, new JackpotHistory(i, jackpot));
                bridgeList.add(set);
            }

            if (!notifMessage.isEmpty()) {
                view.showMessage("Có lỗi nhập tại: " + notifMessage);
            }

            CombineBridge combineBridge = new CombineBridge(bridgeList, new JackpotHistory(i, jackpot));
            combineBridges.add(combineBridge);
        }
        if (combineBridges.isEmpty()) {
            view.showMessage("Không tìm thấy cầu.");
        } else {
            view.showCombineBridgeList(combineBridges);
        }
    }
}
