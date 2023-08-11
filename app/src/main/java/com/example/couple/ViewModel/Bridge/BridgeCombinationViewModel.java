package com.example.couple.ViewModel.Bridge;

import android.content.Context;

import com.example.couple.Custom.Const.Const;
import com.example.couple.Custom.Handler.JackpotBridgeHandler;
import com.example.couple.Custom.Handler.JackpotHandler;
import com.example.couple.Custom.Handler.LotteryHandler;
import com.example.couple.Custom.Handler.TimeHandler;
import com.example.couple.Model.Bridge.Bridge;
import com.example.couple.Model.Bridge.CombineBridge;
import com.example.couple.Model.Bridge.Couple.CycleBridge;
import com.example.couple.Model.Bridge.Couple.MappingBridge;
import com.example.couple.Model.Bridge.Couple.PeriodBridge;
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
            // mapping, period
            MappingBridge mappingBridge =
                    JackpotBridgeHandler.GetMappingBridge(jackpotList, Const.MAPPING_ALL, 0);
            bridgeList.add(mappingBridge);
            ShadowMappingBridge shadowMappingBridge =
                    JackpotBridgeHandler.GetShadowMappingBridge(jackpotList, 0);
            bridgeList.add(shadowMappingBridge);
            PeriodBridge periodBridge = JackpotBridgeHandler.GetPeriodBridge(jackpotList, 0);
            bridgeList.add(periodBridge);
            CombineBridge combineBridge = new CombineBridge(bridgeList,
                    new JackpotHistory(0, Jackpot.getEmpty()));
            view.ShowAllBridgeToday(combineBridge);
        }
    }

    public void GetCombineBridgeList(List<Jackpot> jackpotList, List<Lottery> lotteryList,
                                     TimeBase timeBaseNextDay, int numberOfDay,
                                     boolean combineTouch, boolean connected, boolean shadowTouch,
                                     boolean lottoTouch, boolean negativeShadow, boolean positiveShadow,
                                     boolean mapping, boolean shadowMapping, boolean period, boolean mapping1,
                                     boolean compatible, boolean incompatible, boolean matchMapping,
                                     boolean triadMapping, boolean shadowExchange,
                                     boolean bigDouble, boolean sameDouble, boolean nearDouble) {
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
            // mapping, period
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
            if (period) {
                PeriodBridge periodBridge = JackpotBridgeHandler
                        .GetPeriodBridge(jackpotList, i);
                bridgeList.add(periodBridge);
            }
            if (mapping1) {
                MappingBridge mappingBridge1 = JackpotBridgeHandler
                        .GetMappingBridge(jackpotList, 1, i);
                bridgeList.add(mappingBridge1);
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
            if (matchMapping) {
                MappingBridge matchMappingBridge = JackpotBridgeHandler
                        .GetMatchMappingBridge(jackpotList, i);
                bridgeList.add(matchMappingBridge);
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
            CombineBridge combineBridge = new CombineBridge(bridgeList, new JackpotHistory(i, jackpot));
            combineBridges.add(combineBridge);
        }
        if (combineBridges.isEmpty()) {
            view.ShowError("Không tìm thấy cầu.");
        } else {
            view.ShowCombineBridgeList(combineBridges);
        }
    }

    public void GetTouchBridgeList(List<Jackpot> jackpotList, List<Lottery> lotteryList, int numberOfDay) {
        List<CombineTouchBridge> combineTouchBridges = new ArrayList<>();
        for (int i = 0; i < numberOfDay; i++) {
            CombineTouchBridge combineTouchBridge = JackpotBridgeHandler.GetCombineTouchBridge(jackpotList, lotteryList, i);
            combineTouchBridges.add(combineTouchBridge);
        }
        if (combineTouchBridges.isEmpty()) {
            view.ShowError("Không tìm thấy cầu.");
        } else {
            view.ShowTouchBridgeList(combineTouchBridges);
        }
    }

    public void GetShadowTouchBridgeList(List<Jackpot> jackpotList, int numberOfDay) {
        List<ShadowTouchBridge> touchBridges = new ArrayList<>();
        for (int i = 0; i < numberOfDay; i++) {
            ShadowTouchBridge bridge = JackpotBridgeHandler.GetShadowTouchBridge(jackpotList, i);
            touchBridges.add(bridge);
        }
        if (touchBridges.isEmpty()) {
            view.ShowError("Không tìm thấy cầu.");
        } else {
            view.ShowShadowTouchBridgeList(touchBridges);
        }
    }

    public void GetPeriodBridgeList(List<Jackpot> jackpotList, int numberOfDay) {
        List<PeriodBridge> periodBridges = new ArrayList<>();
        for (int i = 0; i < numberOfDay; i++) {
            PeriodBridge bridge = JackpotBridgeHandler.GetPeriodBridge(jackpotList, i);
            periodBridges.add(bridge);
        }
        if (periodBridges.isEmpty()) {
            view.ShowError("Không tìm thấy cầu.");
        } else {
            view.ShowPeriodBridgeList(periodBridges);
        }
    }

    public void GetMappingBridgeList(List<Jackpot> jackpotList, int numberOfDay) {
        List<MappingBridge> mappingBridgeList = new ArrayList<>();
        for (int i = 0; i < numberOfDay; i++) {
            MappingBridge mappingBridge = JackpotBridgeHandler.GetMappingBridge(jackpotList, Const.MAPPING_ALL, i);
            mappingBridgeList.add(mappingBridge);
        }
        if (mappingBridgeList.isEmpty()) {
            view.ShowError("Không tìm thấy cầu.");
        } else {
            view.ShowMappingBridgeList(mappingBridgeList);
        }
    }

    public void GetShadowBridgeList(List<Jackpot> jackpotList, int numberOfDay) {
        List<ShadowMappingBridge> shadowMappingBridgeList = new ArrayList<>();
        for (int i = 0; i < numberOfDay; i++) {
            ShadowMappingBridge shadowMappingBridge = JackpotBridgeHandler.GetShadowMappingBridge(jackpotList, i);
            shadowMappingBridgeList.add(shadowMappingBridge);
        }
        if (shadowMappingBridgeList.isEmpty()) {
            view.ShowError("Không tìm thấy cầu.");
        } else {
            view.ShowShadowMappingBridgeList(shadowMappingBridgeList);
        }
    }

    public void GetBigDoubleSet(List<Jackpot> jackpotList, int numberOfDay) {
        List<SpecialSetBridge> bigDoubleSets = new ArrayList<>();
        for (int i = 0; i < numberOfDay; i++) {
            Jackpot jackpot = i - 1 >= 0 ? jackpotList.get(i - 1) : Jackpot.getEmpty();
            SpecialSetBridge bigDoubleSet = new SpecialSetBridge(Const.BIG_DOUBLE_SET_NAME,
                    Const.BIG_DOUBLE_SET, new JackpotHistory(i, jackpot));
            bigDoubleSets.add(bigDoubleSet);
        }
        if (bigDoubleSets.isEmpty()) {
            view.ShowSet(Const.BIG_DOUBLE_SET);
        } else {
            view.ShowBigDoubleSet(bigDoubleSets);
        }
    }

    public void GetDoubleSet(List<Jackpot> jackpotList, int numberOfDay) {
        List<SpecialSetBridge> doubleSets = new ArrayList<>();
        for (int i = 0; i < numberOfDay; i++) {
            Jackpot jackpot = i - 1 >= 0 ? jackpotList.get(i - 1) : Jackpot.getEmpty();
            SpecialSetBridge doubleSet = new SpecialSetBridge(Const.DOUBLE_SET_NAME,
                    Const.DOUBLE_SET, new JackpotHistory(i, jackpot));
            doubleSets.add(doubleSet);
        }
        if (doubleSets.isEmpty()) {
            view.ShowSet(Const.DOUBLE_SET);
        } else {
            view.ShowDoubleSet(doubleSets);
        }
    }

    public void GetNearDoubleSet(List<Jackpot> jackpotList, int numberOfDay) {
        List<SpecialSetBridge> nearDoubleSets = new ArrayList<>();
        for (int i = 0; i < numberOfDay; i++) {
            Jackpot jackpot = i - 1 >= 0 ? jackpotList.get(i - 1) : Jackpot.getEmpty();
            SpecialSetBridge nearDoubleSet = new SpecialSetBridge(Const.NEAR_DOUBLE_SET_NAME,
                    Const.NEAR_DOUBLE_SET, new JackpotHistory(i, jackpot));
            nearDoubleSets.add(nearDoubleSet);
        }
        if (nearDoubleSets.isEmpty()) {
            view.ShowSet(Const.NEAR_DOUBLE_SET);
        } else {
            view.ShowNearDoubleSet(nearDoubleSets);
        }
    }


}
