package com.example.couple.Model.Bridge;

import com.example.couple.Base.Handler.CoupleBase;
import com.example.couple.Custom.Handler.CoupleHandler;

import java.util.List;

public abstract class Bridge {

    public String showMappingInfo() {
        String show = "";
        String win = getJackpotHistory().isEmpty() ? "" : (isWin() ? " (trúng)" : " (trượt)");
        String showDetail = !showCompactNumbers().isEmpty() ? showCompactNumbers() : getNumbers().size() + " số.";
        show += "    - " + getType().name + win + ": " + showDetail;
        return show;
    }

    public String showCompactInfo() {
        return showMappingInfo();
    }

    public String showDetailInfo() {
        String show = showJackpotInfo();
        String compactInfo = showCompactInfo();
        if (!compactInfo.isEmpty()) {
            show += "\n\nChi tiết cầu:\n" + compactInfo;
        }
        if (!getNumbers().isEmpty()) {
            show += "\n\nDàn số:\n" + showNumbers();
        }
        return show.trim();
    }

    protected String showJackpotInfo() {
        if (getJackpotHistory().isEmpty()) return "";
        return "Ngày: " + getJackpotHistory().getJackpot().getDateBase().showFullChars()
                + "\nKQ XSĐB: " + getJackpotHistory().getJackpot().getJackpot();
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
