package com.example.couple.Model.Bridge;

import com.example.couple.Base.Handler.CoupleBase;
import com.example.couple.Custom.Handler.CoupleHandler;

import java.util.List;

public abstract class Bridge {

    public String showCompactBridge() {
        String show = "";
        String win = getJackpotHistory().isEmpty() ? "" : (isWin() ? " (trúng)" : " (trượt)");
        String showDetail = !showCompactNumbers().isEmpty() ? showCompactNumbers() : getNumbers().size() + " số.";
        show += "    - " + getType().name + win + ": " + showDetail;
        return show;
    }

    public boolean isWin() {
        return CoupleHandler.isWin(getJackpotHistory(), getNumbers());
    }

    public boolean isUncheckable() {
        return getJackpotHistory().isEmpty();
    }

    public String showNumbers() {
        return CoupleBase.showCoupleNumbers(getNumbers());
    }

    public abstract String showCompactNumbers();

    public abstract BridgeType getType();

    public abstract List<Integer> getNumbers();

    public abstract JackpotHistory getJackpotHistory();

}
