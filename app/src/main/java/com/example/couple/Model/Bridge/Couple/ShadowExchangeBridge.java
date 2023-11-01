package com.example.couple.Model.Bridge.Couple;

import com.example.couple.Custom.Const.Const;
import com.example.couple.Model.Bridge.Bridge;
import com.example.couple.Model.Origin.Couple;
import com.example.couple.Model.Support.JackpotHistory;

import java.util.List;

import lombok.Getter;

@Getter
public class ShadowExchangeBridge extends Bridge {
    Couple coupleBefore;
    List<Integer> numbers;
    JackpotHistory jackpotHistory;

    public ShadowExchangeBridge(Couple coupleBefore, JackpotHistory jackpotHistory) {
        this.coupleBefore = coupleBefore;
        this.jackpotHistory = jackpotHistory;
        this.numbers = coupleBefore.getShadowExchange();
    }

    @Override
    public String showCompactNumbers() {
        return "";
    }

    @Override
    public String getBridgeName() {
        return Const.SHADOW_EXCHANGE_BRIDGE_NAME;
    }

    public static ShadowExchangeBridge getEmpty() {
        return new ShadowExchangeBridge(Couple.getEmpty(), JackpotHistory.getEmpty());
    }

    public boolean isEmpty() {
        return coupleBefore.isEmpty();
    }

}
