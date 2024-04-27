package com.example.couple.Model.Bridge;

import com.example.couple.Base.Handler.CoupleBase;
import com.example.couple.Custom.Handler.CoupleHandler;
import com.example.couple.Model.Support.JackpotHistory;

import java.util.List;

public abstract class Bridge {

    public String showCompactBridge() {
        String show = "";
        String win = getJackpotHistory().isEmpty() ? "" : (isWin() ? " (trúng)" : " (trượt)");
        String showDetail = !showCompactNumbers().isEmpty() ? showCompactNumbers() : getNumbers().size() + " số.";
        show += "    - " + getBridgeName() + win + ": " + showDetail;
        return show;
    }

    public boolean isWin() {
        return CoupleHandler.isWin(getJackpotHistory(), getNumbers());
    }

    public String showNumbers() {
        return CoupleBase.showCoupleNumbers(getNumbers());
    }

    public abstract String showCompactNumbers();

    public abstract String getBridgeName();

    public abstract List<Integer> getNumbers();

    public abstract JackpotHistory getJackpotHistory();

}
