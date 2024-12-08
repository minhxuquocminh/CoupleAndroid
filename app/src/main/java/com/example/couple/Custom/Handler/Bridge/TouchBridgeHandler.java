package com.example.couple.Custom.Handler.Bridge;

import com.example.couple.Custom.Const.Const;
import com.example.couple.Custom.Const.TimeInfo;
import com.example.couple.Model.Bridge.BridgeType;
import com.example.couple.Model.Bridge.Touch.CombineTouchBridge;
import com.example.couple.Model.Bridge.Touch.LottoTouchBridge;
import com.example.couple.Model.Bridge.Touch.ShadowTouchBridge;
import com.example.couple.Model.Bridge.Touch.TouchBridge;
import com.example.couple.Model.Origin.Couple;
import com.example.couple.Model.Origin.Jackpot;
import com.example.couple.Model.Origin.Lottery;
import com.example.couple.Model.Bridge.JackpotHistory;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class TouchBridgeHandler {

    public static ShadowTouchBridge getLastDayShadowTouchBridge(List<Jackpot> reverseJackpotList, int dayNumberBefore) {
        if (reverseJackpotList.size() < dayNumberBefore) return ShadowTouchBridge.getEmpty();
        Couple coupleLastDay = reverseJackpotList.get(dayNumberBefore).getCouple();
        List<Integer> touchs = coupleLastDay.getTouchsAndShadows();
        Jackpot jackpot = dayNumberBefore == 0 ?
                Jackpot.getEmpty() : reverseJackpotList.get(dayNumberBefore - 1);
        return new ShadowTouchBridge(BridgeType.LAST_DAY_SHADOW.name, touchs,
                new JackpotHistory(dayNumberBefore, jackpot));
    }

    public static ShadowTouchBridge getLastWeekShadowTouchBridge(List<Jackpot> jackpotList, int dayNumberBefore) {
        if (jackpotList.size() < TimeInfo.DAY_OF_WEEK + dayNumberBefore)
            return ShadowTouchBridge.getEmpty();
        Couple coupleLastDay = jackpotList.get(TimeInfo.DAY_OF_WEEK + dayNumberBefore - 1).getCouple();
        List<Integer> touchs = coupleLastDay.getTouchsAndShadows();
        Jackpot jackpot = dayNumberBefore == 0 ?
                Jackpot.getEmpty() : jackpotList.get(dayNumberBefore - 1);
        return new ShadowTouchBridge(BridgeType.LAST_WEEK_SHADOW.name, touchs,
                new JackpotHistory(dayNumberBefore, jackpot));
    }

    public static ShadowTouchBridge getShadowTouchBridge(List<Jackpot> jackpotList, int dayNumberBefore) {
        if (jackpotList.size() - TimeInfo.DAY_OF_WEEK < dayNumberBefore)
            return ShadowTouchBridge.getEmpty();
        Couple coupleLastWeek = jackpotList.get(TimeInfo.DAY_OF_WEEK + dayNumberBefore - 1).getCouple();
        List<Integer> touchs = coupleLastWeek.getShadows();
        if (coupleLastWeek.isDouble()) touchs.add(coupleLastWeek.getFirst());
        List<Integer> results = touchs.stream().distinct().sorted().collect(Collectors.toList());
        Jackpot jackpot = dayNumberBefore == 0 ?
                Jackpot.getEmpty() : jackpotList.get(dayNumberBefore - 1);
        return new ShadowTouchBridge(BridgeType.SHADOW_TOUCH.name, results,
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
        List<TouchBridge> touchBridges = new ArrayList<>();
        touchBridges.add(ConnectedBridgeHandler.getConnectedBridge(lotteries, dayNumberBefore,
                Const.CONNECTED_BRIDGE_FINDING_DAYS, Const.CONNECTED_BRIDGE_MAX_DISPLAY));
        touchBridges.add(getLastDayShadowTouchBridge(jackpotList, dayNumberBefore));
        touchBridges.add(getLastWeekShadowTouchBridge(jackpotList, dayNumberBefore));
        //touchBridges.add(getLottoTouchBridge(lotteries, dayNumberBefore));
        Jackpot jackpot = dayNumberBefore == 0 ? Jackpot.getEmpty() : jackpotList.get(dayNumberBefore - 1);
        return new CombineTouchBridge(touchBridges, new JackpotHistory(dayNumberBefore, jackpot));
    }

}
