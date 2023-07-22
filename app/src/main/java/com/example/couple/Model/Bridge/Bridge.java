package com.example.couple.Model.Bridge;

import com.example.couple.Custom.Handler.CoupleHandler;
import com.example.couple.Model.Support.JackpotHistory;

import java.util.List;

public abstract class Bridge {

    public String showBridge() {
        String show = "";
        String win = getJackpotHistory().isEmpty() ? "" : (isWin() ? "trúng" : "trượt");
        String showDetail = !getTouchs().isEmpty() ? showTouchs() :
                showNumbers() + " (" + getNumbers().size() + " số).";
        show += " * " + getJackpotHistory().show() + " - " + win + "\n";
        show += "    - " + getBridgeName() + ": " + showDetail;
        return show;
    }

    public String showCompactBridge() {
        String show = "";
        String win = getJackpotHistory().isEmpty() ? "" : (isWin() ? " (trúng)" : " (trượt)");
        String showDetail = !getTouchs().isEmpty() ? showTouchs() : getNumbers().size() + " số.";
        show += "    - " + getBridgeName() + win + ": " + showDetail;
        return show;
    }

    public boolean isWin() {
        return CoupleHandler.isWin(getJackpotHistory(), getNumbers());
    }

    public String showTouchs() {
        return CoupleHandler.showTouchs(getTouchs());
    }

    public String showNumbers() {
        return CoupleHandler.showCoupleNumbers(getNumbers());
    }

    public abstract List<Integer> getTouchs();

    public abstract String getBridgeName();

    public abstract List<Integer> getNumbers();

    public abstract JackpotHistory getJackpotHistory();

}
