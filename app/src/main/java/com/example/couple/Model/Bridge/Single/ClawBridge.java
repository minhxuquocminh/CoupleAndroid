package com.example.couple.Model.Bridge.Single;

import com.example.couple.Base.Handler.SingleBase;
import com.example.couple.Custom.Const.Const;
import com.example.couple.Custom.Handler.CoupleHandler;
import com.example.couple.Model.Support.JackpotHistory;

import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class ClawBridge {
    List<Integer> touchs;
    int bridgeType;
    JackpotHistory jackpotHistory;


    public boolean isWin() {
        return CoupleHandler.isTouch(jackpotHistory, touchs);
    }

    public String showBridge() {
        String show = "";
        String win = jackpotHistory.isEmpty() ? "" : (isWin() ? "trúng" : "trượt");
        show += " * " + jackpotHistory.show() + " - " + win + "\n";
        show += "    - Cầu càng: " + showTouchs() + ".";
        return show;
    }

    public String showTouchs() {
        return SingleBase.showTouchs(touchs);
    }

    public static ClawBridge getEmpty() {
        return new ClawBridge(new ArrayList<>(), Const.EMPTY_VALUE, JackpotHistory.getEmpty());
    }
}
