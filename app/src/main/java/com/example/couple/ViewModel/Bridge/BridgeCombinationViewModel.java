package com.example.couple.ViewModel.Bridge;

import android.content.Context;

import com.example.couple.Custom.Const.Const;
import com.example.couple.Custom.Handler.JackpotBridgeHandler;
import com.example.couple.Custom.Handler.JackpotHandler;
import com.example.couple.Custom.Handler.LotteryHandler;
import com.example.couple.Custom.Handler.TimeHandler;
import com.example.couple.Model.BridgeCouple.CombineBridge;
import com.example.couple.Model.BridgeCouple.CycleBridge;
import com.example.couple.Model.BridgeCouple.MappingBridge;
import com.example.couple.Model.BridgeCouple.PeriodBridge;
import com.example.couple.Model.BridgeCouple.ShadowMappingBridge;
import com.example.couple.Model.BridgeCouple.SpecialSet;
import com.example.couple.Model.BridgeSingle.CombineTouchBridge;
import com.example.couple.Model.BridgeSingle.ConnectedBridge;
import com.example.couple.Model.BridgeSingle.LottoTouchBridge;
import com.example.couple.Model.BridgeSingle.ShadowTouchBridge;
import com.example.couple.Model.Origin.Jackpot;
import com.example.couple.Model.Origin.Lottery;
import com.example.couple.Model.Support.JackpotHistory;
import com.example.couple.Model.Support.TimeBase;
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

    public void GetLotteryAndJackpotList() {
        List<Jackpot> jackpotList = JackpotHandler.GetReserveJackpotListFromFile(context, Const.DAY_OF_YEAR);
        List<Lottery> lotteryList = LotteryHandler.getLotteryListFromFile(context, Const.MAX_DAYS_TO_GET_LOTTERY);
        List<TimeBase> timeBaseList = TimeHandler.getAllSexagenaryCycle(context, Const.MAX_DAYS_TO_GET_CYCLE);
        view.ShowLotteryAndJackpotList(jackpotList, lotteryList, timeBaseList);
    }

    public void GetAllBridgeToday(List<Jackpot> jackpotList, List<Lottery> lotteryList) {
        if (!jackpotList.isEmpty()) {
            // touch
            CombineTouchBridge combineTouchBridge =
                    JackpotBridgeHandler.GetCombineTouchBridge(jackpotList, lotteryList, 0);
            ConnectedBridge connectedBridge = JackpotBridgeHandler.GetConnectedBridge(lotteryList,
                    Const.CONNECTED_BRIDGE_FINDING_DAYS, 0, Const.CONNECTED_BRIDGE_MAX_DISPLAY);
            ShadowTouchBridge shadowTouchBridge = JackpotBridgeHandler
                    .GetShadowTouchBridge(jackpotList, 0);
            LottoTouchBridge lottoTouchBridge =
                    JackpotBridgeHandler.GetLottoTouchBridge(lotteryList, 0);
            ShadowTouchBridge negativeShadowBridge =
                    JackpotBridgeHandler.GetNegativeShadowTouchBridge(jackpotList, 0);
            ShadowTouchBridge positiveShadowBridge =
                    JackpotBridgeHandler.GetPositiveShadowTouchBridge(jackpotList, 0);
            // mapping, period
            MappingBridge mappingBridge =
                    JackpotBridgeHandler.GetMappingBridge(jackpotList, Const.MAPPING_ALL, 0);
            ShadowMappingBridge shadowMappingBridge =
                    JackpotBridgeHandler.GetShadowMappingBridge(jackpotList, 0);
            PeriodBridge periodBridge = JackpotBridgeHandler.GetPeriodBridge(jackpotList, 0);
            CombineBridge combineBridge = new CombineBridge(combineTouchBridge, connectedBridge,
                    shadowTouchBridge, lottoTouchBridge, negativeShadowBridge, positiveShadowBridge,
                    mappingBridge, shadowMappingBridge, periodBridge, MappingBridge.getEmpty(),
                    CycleBridge.getEmpty(), CycleBridge.getEmpty(), SpecialSet.getEmpty(),
                    SpecialSet.getEmpty(), SpecialSet.getEmpty(),
                    new JackpotHistory(0, Jackpot.getEmpty()));
            view.ShowAllBridgeToday(combineBridge);
        }
    }

    public void GetCombineBridgeList(List<Jackpot> jackpotList, List<Lottery> lotteryList,
                                     List<TimeBase> timeBaseList, int numberOfDay,
                                     boolean combineTouch, boolean connected, boolean shadowTouch,
                                     boolean lottoTouch, boolean negativeShadow, boolean positiveShadow,
                                     boolean mapping, boolean shadowMapping, boolean period, boolean mapping1,
                                     boolean compatible, boolean incompatible, boolean bigDouble,
                                     boolean sameDouble, boolean nearDouble) {
        List<CombineBridge> combineBridges = new ArrayList<>();
        if (connected && numberOfDay > lotteryList.size() - Const.CONNECTED_BRIDGE_FINDING_DAYS) {
            view.ShowError("Đặt lại giới hạn số ngày cho cầu liên thông là " +
                    (lotteryList.size() - Const.CONNECTED_BRIDGE_FINDING_DAYS) + " ngày");
        }
        int newDayNumber = connected && numberOfDay >
                lotteryList.size() - Const.CONNECTED_BRIDGE_FINDING_DAYS ?
                lotteryList.size() - Const.CONNECTED_BRIDGE_FINDING_DAYS : numberOfDay;
        for (int i = 0; i < newDayNumber; i++) {
            // touch
            CombineTouchBridge combineTouchBridge = combineTouch ? JackpotBridgeHandler
                    .GetCombineTouchBridge(jackpotList, lotteryList, i) : CombineTouchBridge.getEmpty();
            ConnectedBridge connectedBridge = connected ? JackpotBridgeHandler
                    .GetConnectedBridge(lotteryList, Const.CONNECTED_BRIDGE_FINDING_DAYS, i,
                            Const.CONNECTED_BRIDGE_MAX_DISPLAY) : ConnectedBridge.getEmpty();
            ShadowTouchBridge shadowTouchBridge = shadowTouch ? JackpotBridgeHandler
                    .GetShadowTouchBridge(jackpotList, i) : ShadowTouchBridge.getEmpty();
            LottoTouchBridge lottoTouchBridge = lottoTouch ? JackpotBridgeHandler
                    .GetLottoTouchBridge(lotteryList, i) : LottoTouchBridge.getEmpty();
            ShadowTouchBridge negativeShadowBridge = negativeShadow ? JackpotBridgeHandler
                    .GetNegativeShadowTouchBridge(jackpotList, i) : ShadowTouchBridge.getEmpty();
            ShadowTouchBridge positiveShadowBridge = positiveShadow ? JackpotBridgeHandler
                    .GetPositiveShadowTouchBridge(jackpotList, i) : ShadowTouchBridge.getEmpty();
            // mapping, period
            MappingBridge mappingBridge = mapping ? JackpotBridgeHandler
                    .GetMappingBridge(jackpotList, Const.MAPPING_ALL, i) : MappingBridge.getEmpty();
            ShadowMappingBridge shadowMappingBridge = shadowMapping ? JackpotBridgeHandler
                    .GetShadowMappingBridge(jackpotList, i) : ShadowMappingBridge.getEmpty();
            PeriodBridge periodBridge = period ? JackpotBridgeHandler
                    .GetPeriodBridge(jackpotList, i) : PeriodBridge.getEmpty();
            MappingBridge mappingBridge1 = mapping1 ? JackpotBridgeHandler
                    .GetMappingBridge(jackpotList, 1, i) : MappingBridge.getEmpty();
            CycleBridge compatibleBridge = compatible ? JackpotBridgeHandler
                    .GetCompatibleCycleBridge(jackpotList, timeBaseList, i) : CycleBridge.getEmpty();
            CycleBridge incompatibleBridge = incompatible ? JackpotBridgeHandler
                    .GetIncompatibleCycleBridge(jackpotList, timeBaseList, i) : CycleBridge.getEmpty();
            // jackpot
            Jackpot jackpot = i - 1 >= 0 ? jackpotList.get(i - 1) : Jackpot.getEmpty();
            // special set
            SpecialSet bigDoubleSet = bigDouble ? new SpecialSet(Const.BIG_DOUBLE_SET_NAME,
                    Const.BIG_DOUBLE_SET, new JackpotHistory(i, jackpot)) : SpecialSet.getEmpty();
            SpecialSet sameDoubleSet = sameDouble ? new SpecialSet(Const.DOUBLE_SET_NAME,
                    Const.DOUBLE_SET, new JackpotHistory(i, jackpot)) : SpecialSet.getEmpty();
            SpecialSet nearDoubleSet = nearDouble ? new SpecialSet(Const.NEAR_DOUBLE_SET_NAME,
                    Const.NEAR_DOUBLE_SET, new JackpotHistory(i, jackpot)) : SpecialSet.getEmpty();
            CombineBridge combineBridge = new CombineBridge(combineTouchBridge, connectedBridge,
                    shadowTouchBridge, lottoTouchBridge, negativeShadowBridge, positiveShadowBridge,
                    mappingBridge, shadowMappingBridge, periodBridge, mappingBridge1, compatibleBridge,
                    incompatibleBridge, bigDoubleSet, sameDoubleSet, nearDoubleSet, new JackpotHistory(i, jackpot));
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
        List<SpecialSet> bigDoubleSets = new ArrayList<>();
        for (int i = 0; i < numberOfDay; i++) {
            Jackpot jackpot = i - 1 >= 0 ? jackpotList.get(i - 1) : Jackpot.getEmpty();
            SpecialSet bigDoubleSet = new SpecialSet(Const.BIG_DOUBLE_SET_NAME,
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
        List<SpecialSet> doubleSets = new ArrayList<>();
        for (int i = 0; i < numberOfDay; i++) {
            Jackpot jackpot = i - 1 >= 0 ? jackpotList.get(i - 1) : Jackpot.getEmpty();
            SpecialSet doubleSet = new SpecialSet(Const.DOUBLE_SET_NAME,
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
        List<SpecialSet> nearDoubleSets = new ArrayList<>();
        for (int i = 0; i < numberOfDay; i++) {
            Jackpot jackpot = i - 1 >= 0 ? jackpotList.get(i - 1) : Jackpot.getEmpty();
            SpecialSet nearDoubleSet = new SpecialSet(Const.NEAR_DOUBLE_SET_NAME,
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
