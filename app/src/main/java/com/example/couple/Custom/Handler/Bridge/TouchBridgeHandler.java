package com.example.couple.Custom.Handler.Bridge;

import com.example.couple.Base.Handler.NumberBase;
import com.example.couple.Base.Handler.SingleBase;
import com.example.couple.Custom.Const.Const;
import com.example.couple.Custom.Const.TimeInfo;
import com.example.couple.Model.Bridge.Single.CombineTouchBridge;
import com.example.couple.Model.Bridge.Single.ConnectedBridge;
import com.example.couple.Model.Bridge.Single.LottoTouchBridge;
import com.example.couple.Model.Bridge.Single.ShadowTouchBridge;
import com.example.couple.Model.Origin.Couple;
import com.example.couple.Model.Origin.Jackpot;
import com.example.couple.Model.Origin.Lottery;
import com.example.couple.Model.Support.JackpotHistory;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class TouchBridgeHandler {

    public static ShadowTouchBridge getNegativeShadowTouchBridge(List<Jackpot> reverseJackpotList, int dayNumberBefore) {
        if (reverseJackpotList.size() < TimeInfo.DAY_OF_WEEK + dayNumberBefore)
            return ShadowTouchBridge.getEmpty();
        List<Integer> touchs = new ArrayList<>();
        Couple coupleLastWeek = reverseJackpotList.get(TimeInfo.DAY_OF_WEEK + dayNumberBefore - 1).getCouple();
        int first = coupleLastWeek.getFirst();
        int second = coupleLastWeek.getSecond();
        touchs.add(SingleBase.getNegativeShadow(first));
        touchs.add(SingleBase.getNegativeShadow(second));
        if (first == second) touchs.add(first);
        List<Integer> results = NumberBase.filterDuplicatedNumbers(touchs);
        Collections.sort(results, (x, y) -> x - y);
        Jackpot jackpot = dayNumberBefore == 0 ?
                Jackpot.getEmpty() : reverseJackpotList.get(dayNumberBefore - 1);
        return new ShadowTouchBridge(Const.NEGATIVE_SHADOW_BRIDGE_NAME, results,
                new JackpotHistory(dayNumberBefore, jackpot));
    }

    public static ShadowTouchBridge getPositiveShadowTouchBridge(List<Jackpot> reverseJackpotList, int dayNumberBefore) {
        if (reverseJackpotList.size() < TimeInfo.DAY_OF_WEEK + dayNumberBefore)
            return ShadowTouchBridge.getEmpty();
        List<Integer> touchs = new ArrayList<>();
        Couple coupleLastWeek = reverseJackpotList.get(TimeInfo.DAY_OF_WEEK + dayNumberBefore - 1).getCouple();
        int first = coupleLastWeek.getFirst();
        int second = coupleLastWeek.getSecond();
        touchs.add(SingleBase.getShadow(first));
        touchs.add(SingleBase.getShadow(second));
        if (first == second) touchs.add(first);
        List<Integer> results = NumberBase.filterDuplicatedNumbers(touchs);
        Collections.sort(results, (x, y) -> x - y);
        Jackpot jackpot = dayNumberBefore == 0 ?
                Jackpot.getEmpty() : reverseJackpotList.get(dayNumberBefore - 1);
        return new ShadowTouchBridge(Const.POSITIVE_SHADOW_BRIDGE_NAME, results,
                new JackpotHistory(dayNumberBefore, jackpot));
    }

    public static ShadowTouchBridge getShadowTouchBridge(List<Jackpot> jackpotList, int dayNumberBefore) {
        if (jackpotList.size() - TimeInfo.DAY_OF_WEEK < dayNumberBefore)
            return ShadowTouchBridge.getEmpty();
        List<Integer> touchs = new ArrayList<>();
        Couple coupleLastWeek = jackpotList.get(TimeInfo.DAY_OF_WEEK + dayNumberBefore - 1).getCouple();
        int first = coupleLastWeek.getFirst();
        int second = coupleLastWeek.getSecond();
        touchs.add(SingleBase.getNegativeShadow(first));
        touchs.add(SingleBase.getNegativeShadow(second));
        touchs.add(SingleBase.getShadow(first));
        touchs.add(SingleBase.getShadow(second));
        if (first == second) touchs.add(first);
        List<Integer> results = NumberBase.filterDuplicatedNumbers(touchs);
        Collections.sort(results, (x, y) -> x - y);
        Jackpot jackpot = dayNumberBefore == 0 ?
                Jackpot.getEmpty() : jackpotList.get(dayNumberBefore - 1);
        return new ShadowTouchBridge(Const.SHADOW_TOUCH_BRIDGE_NAME, results,
                new JackpotHistory(dayNumberBefore, jackpot));
    }

    public static LottoTouchBridge getLottoTouchBridge(List<Lottery> lotteries, int dayNumberBefore) {
        if (lotteries.size() - dayNumberBefore < 1) return LottoTouchBridge.getEmpty();
        Jackpot jackpot = dayNumberBefore == 0 ?
                Jackpot.getEmpty() : lotteries.get(dayNumberBefore - 1).getJackpot();
        Lottery lottery = lotteries.get(dayNumberBefore);
        return new LottoTouchBridge(lottery.getHeadLotoList(), lottery.getTailLotoList(),
                new JackpotHistory(dayNumberBefore, jackpot));
    }

    public static CombineTouchBridge getCombineTouchBridge(List<Jackpot> jackpotList,
                                                           List<Lottery> lotteries, int dayNumberBefore) {
        if (jackpotList.size() - TimeInfo.DAY_OF_WEEK < dayNumberBefore ||
                lotteries.size() - Const.CONNECTED_BRIDGE_FINDING_DAYS < dayNumberBefore)
            return CombineTouchBridge.getEmpty();
        ShadowTouchBridge shadowTouchBridge = getShadowTouchBridge(jackpotList, dayNumberBefore);
        ConnectedBridge connectedBridge = ConnectedBridgeHandler.getConnectedBridge(lotteries, dayNumberBefore,
                Const.CONNECTED_BRIDGE_FINDING_DAYS, Const.CONNECTED_BRIDGE_MAX_DISPLAY);
        LottoTouchBridge lottoTouchBridge = getLottoTouchBridge(lotteries, dayNumberBefore);
        Jackpot jackpot = dayNumberBefore == 0 ? Jackpot.getEmpty() : jackpotList.get(dayNumberBefore - 1);
        return new CombineTouchBridge(shadowTouchBridge, connectedBridge, lottoTouchBridge,
                new JackpotHistory(dayNumberBefore, jackpot));
    }

}
