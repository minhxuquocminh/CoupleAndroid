package com.example.couple.ViewModel.Bridge;

import android.content.Context;

import com.example.couple.Base.Handler.NumberBase;
import com.example.couple.Custom.Const.Const;
import com.example.couple.Custom.Handler.JackpotBridgeHandler;
import com.example.couple.Custom.Handler.JackpotHandler;
import com.example.couple.Custom.Handler.LotteryHandler;
import com.example.couple.Custom.Handler.NumberArrayHandler;
import com.example.couple.Custom.Handler.TimeHandler;
import com.example.couple.Model.Bridge.Bridge;
import com.example.couple.Model.Bridge.CombineBridge;
import com.example.couple.Model.Bridge.Couple.CycleBridge;
import com.example.couple.Model.Bridge.Couple.EstimatedBridge;
import com.example.couple.Model.Bridge.Couple.MappingBridge;
import com.example.couple.Model.Bridge.Couple.ShadowExchangeBridge;
import com.example.couple.Model.Bridge.Couple.ShadowMappingBridge;
import com.example.couple.Model.Bridge.Couple.SpecialSetBridge;
import com.example.couple.Model.Bridge.Couple.TriadMappingBridge;
import com.example.couple.Model.Bridge.Single.CombineTouchBridge;
import com.example.couple.Model.Bridge.Single.ConnectedBridge;
import com.example.couple.Model.Bridge.Single.LottoTouchBridge;
import com.example.couple.Model.Bridge.Single.ShadowTouchBridge;
import com.example.couple.Model.Origin.Jackpot;
import com.example.couple.Model.Origin.Lottery;
import com.example.couple.Model.Support.JackpotHistory;
import com.example.couple.Model.Time.TimeBase;
import com.example.couple.View.Bridge.BridgeCombinationView;

import java.util.ArrayList;
import java.util.List;

public class BridgeCombinationViewModel {
    BridgeCombinationView view;
    Context context;

    public BridgeCombinationViewModel(BridgeCombinationView view, Context context) {
        this.view = view;
        this.context = context;
    }

    public void GetTimeBaseNextDayAndLotteryAndJackpotList() {
        List<Jackpot> jackpotList = JackpotHandler.GetReserveJackpotListFromFile(context, Const.DAY_OF_YEAR);
        List<Lottery> lotteryList = LotteryHandler.getLotteryListFromFile(context, Const.MAX_DAYS_TO_GET_LOTTERY);
        TimeBase timeBaseNextDay = TimeHandler.getTimeBaseNextDay(context);
        view.ShowLotteryAndJackpotAndTimeBaseList(jackpotList, lotteryList, timeBaseNextDay);
    }

    public void GetAllBridgeToday(List<Jackpot> jackpotList, List<Lottery> lotteryList) {
        if (!jackpotList.isEmpty()) {
            List<Bridge> bridgeList = new ArrayList<>();
            // touch
            CombineTouchBridge combineTouchBridge =
                    JackpotBridgeHandler.GetCombineTouchBridge(jackpotList, lotteryList, 0);
            bridgeList.add(combineTouchBridge);
            ConnectedBridge connectedBridge = JackpotBridgeHandler.GetConnectedBridge(lotteryList,
                    Const.CONNECTED_BRIDGE_FINDING_DAYS, 0, Const.CONNECTED_BRIDGE_MAX_DISPLAY);
            bridgeList.add(connectedBridge);
            ShadowTouchBridge shadowTouchBridge = JackpotBridgeHandler
                    .GetShadowTouchBridge(jackpotList, 0);
            bridgeList.add(shadowTouchBridge);
            LottoTouchBridge lottoTouchBridge =
                    JackpotBridgeHandler.GetLottoTouchBridge(lotteryList, 0);
            bridgeList.add(lottoTouchBridge);
            ShadowTouchBridge negativeShadowBridge =
                    JackpotBridgeHandler.GetNegativeShadowTouchBridge(jackpotList, 0);
            bridgeList.add(negativeShadowBridge);
            ShadowTouchBridge positiveShadowBridge =
                    JackpotBridgeHandler.GetPositiveShadowTouchBridge(jackpotList, 0);
            bridgeList.add(positiveShadowBridge);
            // mapping, estimated
            MappingBridge mappingBridge =
                    JackpotBridgeHandler.GetMappingBridge(jackpotList, Const.MAPPING_ALL, 0);
            bridgeList.add(mappingBridge);
            ShadowMappingBridge shadowMappingBridge =
                    JackpotBridgeHandler.GetShadowMappingBridge(jackpotList, 0);
            bridgeList.add(shadowMappingBridge);
            EstimatedBridge estimatedBridge = JackpotBridgeHandler.GetEstimatedBridge(jackpotList, 0);
            bridgeList.add(estimatedBridge);
            CombineBridge combineBridge = new CombineBridge(bridgeList,
                    new JackpotHistory(0, Jackpot.getEmpty()));
            view.ShowAllBridgeToday(combineBridge);
        }
    }

    public void GetCombineBridgeList(List<Jackpot> jackpotList, List<Lottery> lotteryList,
                                     TimeBase timeBaseNextDay, int numberOfDay,
                                     boolean combineTouch, boolean connected, boolean shadowTouch,
                                     boolean lottoTouch, boolean negativeShadow, boolean positiveShadow,
                                     boolean mapping, boolean shadowMapping, boolean estimated, boolean rightMapping,
                                     boolean compatible, boolean incompatible, boolean compactRightMapping,
                                     boolean triadMapping, boolean shadowExchange,
                                     boolean bigDouble, boolean sameDouble, boolean nearDouble,
                                     String setData, String touchData, String sumData, String branchData,
                                     String headData, String tailData, String combineData) {
        List<CombineBridge> combineBridges = new ArrayList<>();
        if (connected && numberOfDay > lotteryList.size() - Const.CONNECTED_BRIDGE_FINDING_DAYS) {
            view.ShowError("Đặt lại giới hạn số ngày cho cầu liên thông là " +
                    (lotteryList.size() - Const.CONNECTED_BRIDGE_FINDING_DAYS) + " ngày");
        }
        int newDayNumber = connected && numberOfDay >
                lotteryList.size() - Const.CONNECTED_BRIDGE_FINDING_DAYS ?
                lotteryList.size() - Const.CONNECTED_BRIDGE_FINDING_DAYS : numberOfDay;
        for (int i = 0; i < newDayNumber; i++) {
            List<Bridge> bridgeList = new ArrayList<>();
            // touch
            if (combineTouch) {
                CombineTouchBridge combineTouchBridge = JackpotBridgeHandler
                        .GetCombineTouchBridge(jackpotList, lotteryList, i);
                bridgeList.add(combineTouchBridge);
            }
            if (connected) {
                ConnectedBridge connectedBridge = JackpotBridgeHandler
                        .GetConnectedBridge(lotteryList, Const.CONNECTED_BRIDGE_FINDING_DAYS, i,
                                Const.CONNECTED_BRIDGE_MAX_DISPLAY);
                bridgeList.add(connectedBridge);
            }
            if (shadowTouch) {
                ShadowTouchBridge shadowTouchBridge = JackpotBridgeHandler
                        .GetShadowTouchBridge(jackpotList, i);
                bridgeList.add(shadowTouchBridge);
            }
            if (lottoTouch) {
                LottoTouchBridge lottoTouchBridge = JackpotBridgeHandler
                        .GetLottoTouchBridge(lotteryList, i);
                bridgeList.add(lottoTouchBridge);
            }
            if (negativeShadow) {
                ShadowTouchBridge negativeShadowBridge = JackpotBridgeHandler
                        .GetNegativeShadowTouchBridge(jackpotList, i);
                bridgeList.add(negativeShadowBridge);
            }
            if (positiveShadow) {
                ShadowTouchBridge positiveShadowBridge = JackpotBridgeHandler
                        .GetPositiveShadowTouchBridge(jackpotList, i);
                bridgeList.add(positiveShadowBridge);
            }
            // mapping, estimated
            if (mapping) {
                MappingBridge mappingBridge = JackpotBridgeHandler
                        .GetMappingBridge(jackpotList, Const.MAPPING_ALL, i);
                bridgeList.add(mappingBridge);
            }
            if (shadowMapping) {
                ShadowMappingBridge shadowMappingBridge = JackpotBridgeHandler
                        .GetShadowMappingBridge(jackpotList, i);
                bridgeList.add(shadowMappingBridge);
            }
            if (estimated) {
                EstimatedBridge estimatedBridge = JackpotBridgeHandler
                        .GetEstimatedBridge(jackpotList, i);
                bridgeList.add(estimatedBridge);
            }
            if (rightMapping) {
                MappingBridge rightMappingBridge = JackpotBridgeHandler
                        .GetRightMappingBridge(jackpotList, i);
                bridgeList.add(rightMappingBridge);
            }
            if (compatible) {
                CycleBridge compatibleBridge = JackpotBridgeHandler
                        .GetCompatibleCycleBridge(jackpotList, timeBaseNextDay, i);
                bridgeList.add(compatibleBridge);
            }
            if (incompatible) {
                CycleBridge incompatibleBridge = JackpotBridgeHandler
                        .GetIncompatibleCycleBridge(jackpotList, timeBaseNextDay, i);
                bridgeList.add(incompatibleBridge);
            }
            if (compactRightMapping) {
                MappingBridge mappingBridge1 = JackpotBridgeHandler
                        .GetCompactRightMappingBridge(jackpotList, i);
                bridgeList.add(mappingBridge1);
            }

            if (triadMapping) {
                TriadMappingBridge triadMappingBridge = JackpotBridgeHandler
                        .GetTriadMappingBridge(jackpotList, i);
                bridgeList.add(triadMappingBridge);
            }
            if (shadowExchange) {
                ShadowExchangeBridge shadowExchangeBridge = JackpotBridgeHandler
                        .GetShadowExchangeBridge(jackpotList, i);
                bridgeList.add(shadowExchangeBridge);
            }
            // jackpot
            Jackpot jackpot = i - 1 >= 0 ? jackpotList.get(i - 1) : Jackpot.getEmpty();
            // special set
            if (bigDouble) {
                SpecialSetBridge bigDoubleSet = new SpecialSetBridge(Const.BIG_DOUBLE_SET_NAME,
                        Const.BIG_DOUBLE_SET, new JackpotHistory(i, jackpot));
                bridgeList.add(bigDoubleSet);
            }
            if (sameDouble) {
                SpecialSetBridge sameDoubleSet = new SpecialSetBridge(Const.DOUBLE_SET_NAME,
                        Const.DOUBLE_SET, new JackpotHistory(i, jackpot));
                bridgeList.add(sameDoubleSet);
            }
            if (nearDouble) {
                SpecialSetBridge nearDoubleSet = new SpecialSetBridge(Const.NEAR_DOUBLE_SET_NAME,
                        Const.NEAR_DOUBLE_SET, new JackpotHistory(i, jackpot));
                bridgeList.add(nearDoubleSet);
            }
            // other set
            String notifMessage = "";
            List<Integer> setList1 = NumberBase.verifyNumberArray(setData, 1);
            List<Integer> setList2 = NumberBase.verifyNumberArray(setData, 2);
            if (!setData.equals("") && setList1.isEmpty() && setList2.isEmpty())
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
            if (!touchData.equals("") && touchList.isEmpty()) notifMessage += "chạm;";
            if (!touchList.isEmpty()) {
                SpecialSetBridge set = new SpecialSetBridge("Chạm",
                        NumberArrayHandler.getTouchs(touchList), new JackpotHistory(i, jackpot));
                bridgeList.add(set);
            }
            List<Integer> sumList = NumberBase.verifyNumberArray(sumData, 1);
            if (!sumData.equals("") && sumList.isEmpty()) notifMessage += "tổng;";
            if (!sumList.isEmpty()) {
                SpecialSetBridge set = new SpecialSetBridge("Tổng",
                        NumberArrayHandler.getSums(sumList), new JackpotHistory(i, jackpot));
                bridgeList.add(set);
            }
            List<Integer> branchList = NumberBase.verifyNumberArray(branchData, 2);
            if (!branchData.equals("") && branchList.isEmpty()) notifMessage += "chi;";
            if (!branchList.isEmpty()) {
                SpecialSetBridge set = new SpecialSetBridge("Chi",
                        NumberArrayHandler.getBranches(branchList), new JackpotHistory(i, jackpot));
                bridgeList.add(set);
            }
            List<Integer> headList = NumberBase.verifyNumberArray(headData, 1);
            if (!headData.equals("") && headList.isEmpty()) notifMessage += "đầu;";
            if (!headList.isEmpty()) {
                SpecialSetBridge set = new SpecialSetBridge("Đầu",
                        NumberArrayHandler.getHeads(headList), new JackpotHistory(i, jackpot));
                bridgeList.add(set);
            }
            List<Integer> tailList = NumberBase.verifyNumberArray(tailData, 1);
            if (!tailData.equals("") && tailList.isEmpty()) notifMessage += "đuôi;";
            if (!tailList.isEmpty()) {
                SpecialSetBridge set = new SpecialSetBridge("Đuôi",
                        NumberArrayHandler.getTails(tailList), new JackpotHistory(i, jackpot));
                bridgeList.add(set);
            }
            List<Integer> combineList = NumberBase.verifyNumberArray(combineData, 2);
            if (!combineData.equals("") && combineList.isEmpty()) notifMessage += "kết hợp;";
            if (!combineList.isEmpty()) {
                SpecialSetBridge set = new SpecialSetBridge("Kết hợp",
                        combineList, new JackpotHistory(i, jackpot));
                bridgeList.add(set);
            }

            if (!notifMessage.equals("")) {
                view.ShowError("Có lỗi nhập tại: " + notifMessage);
            }

            CombineBridge combineBridge = new CombineBridge(bridgeList, new JackpotHistory(i, jackpot));
            combineBridges.add(combineBridge);
        }
        if (combineBridges.isEmpty()) {
            view.ShowError("Không tìm thấy cầu.");
        } else {
            view.ShowCombineBridgeList(combineBridges);
        }
    }
}
