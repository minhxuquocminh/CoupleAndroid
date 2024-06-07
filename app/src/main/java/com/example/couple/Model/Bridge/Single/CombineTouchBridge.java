package com.example.couple.Model.Bridge.Single;

import com.example.couple.Base.Handler.NumberBase;
import com.example.couple.Base.Handler.SingleBase;
import com.example.couple.Custom.Handler.NumberArrayHandler;
import com.example.couple.Model.Bridge.Bridge;
import com.example.couple.Model.Bridge.BridgeType;
import com.example.couple.Model.Support.JackpotHistory;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import lombok.Getter;

@Getter
public class CombineTouchBridge extends Bridge {
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
                touchs.addAll(all.stream().distinct().collect(Collectors.toList()));
            }
        }
        this.numbers.addAll(NumberArrayHandler.getTouchs(touchs));
        this.jackpotHistory = jackpotHistory;
    }

    public static CombineTouchBridge getEmpty() {
        return new CombineTouchBridge(ShadowTouchBridge.getEmpty(), ConnectedBridge.getEmpty(),
                LottoTouchBridge.getEmpty(), JackpotHistory.getEmpty());
    }

    public boolean isEmpty() {
        return numbers.isEmpty();
    }

    @Override
    public String showCompactNumbers() {
        return SingleBase.showTouchs(touchs);
    }

    public String getBridgeName() {
        return BridgeType.COMBINE_TOUCH.name;
    }
}
