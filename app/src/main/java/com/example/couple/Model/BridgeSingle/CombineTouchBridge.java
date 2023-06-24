package com.example.couple.Model.BridgeSingle;

import com.example.couple.Custom.Handler.CoupleHandler;
import com.example.couple.Base.Handler.NumberBase;
import com.example.couple.Custom.Const.Const;
import com.example.couple.Custom.Handler.NumberArrayHandler;
import com.example.couple.Model.BridgeCouple.CombineInterface;
import com.example.couple.Model.Support.JackpotHistory;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;

@Getter
public class CombineTouchBridge implements CombineInterface {
    ShadowTouchBridge shadowTouchBridge;
    ConnectedBridge connectedBridge;
    LottoTouchBridge lottoTouchBridge;
    JackpotHistory jackpotHistory;
    List<Integer> touchs;
    List<Integer> numbers;

    public CombineTouchBridge(ShadowTouchBridge shadowTouchBridge, ConnectedBridge connectedBridge,
                              LottoTouchBridge lottoTouchBridge, JackpotHistory jackpotHistory) {
        this.shadowTouchBridge = shadowTouchBridge;
        this.connectedBridge = connectedBridge;
        this.lottoTouchBridge = lottoTouchBridge;
        this.touchs = new ArrayList<>();
        this.numbers = new ArrayList<>();
        List<Integer> shadowTouchs = shadowTouchBridge.getTouchs();
        List<Integer> connecteds = connectedBridge.getTouchs();
        List<Integer> lottoTouchs = lottoTouchBridge.getTouchs();
        if (lottoTouchs.size() < 4) {
            touchs.addAll(connecteds);
        } else {
            if (shadowTouchs.size() < 4) {
                touchs.addAll(connecteds);
            } else {
                List<Integer> all = new ArrayList<>();
                List<Integer> match1 = NumberBase.getMatchNumbers(shadowTouchs, connecteds);
                List<Integer> match2 = NumberBase.getMatchNumbers(connecteds, lottoTouchs);
                List<Integer> match3 = NumberBase.getMatchNumbers(lottoTouchs, shadowTouchs);
                all.addAll(match1);
                all.addAll(match2);
                all.addAll(match3);
                touchs.addAll(NumberBase.filterDuplicatedNumbers(all));
            }
        }
        this.numbers.addAll(NumberArrayHandler.getTouchs(touchs));
        this.jackpotHistory = jackpotHistory;
    }

    public boolean isWin() {
        return CoupleHandler.isTouch(jackpotHistory, getTouchs());
    }

    public String showNumbers() {
        return CoupleHandler.showCoupleNumbers(numbers);
    }

    public String showTouchs() {
        return CoupleHandler.showTouchs(touchs);
    }

    public String showBridge() {
        String show = "";
        String win = jackpotHistory.isEmpty() ? "" : (isWin() ? "trúng" : "trượt");
        show += " * " + jackpotHistory.show() + " - " + win + "\n";
        show += "    - " + Const.COMBINE_TOUCH_BRIDGE_NAME + ": " + showTouchs() + ".";
        return show;
    }

    public String showCompactBridge() {
        String show = "";
        String win = jackpotHistory.isEmpty() ? "" : (isWin() ? " (trúng)" : " (trượt)");
        show += "    - " + Const.COMBINE_TOUCH_BRIDGE_NAME + win + ": " + showTouchs() + ".";
        return show;
    }

    public static CombineTouchBridge getEmpty() {
        return new CombineTouchBridge(ShadowTouchBridge.getEmpty(), ConnectedBridge.getEmpty(),
                LottoTouchBridge.getEmpty(), JackpotHistory.getEmpty());
    }

    public boolean isEmpty() {
        return numbers.isEmpty();
    }
}
