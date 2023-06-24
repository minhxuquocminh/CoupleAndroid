package com.example.couple.Model.BridgeSingle;

import com.example.couple.Custom.Handler.CoupleHandler;
import com.example.couple.Custom.Const.Const;
import com.example.couple.Custom.Handler.NumberArrayHandler;
import com.example.couple.Model.BridgeCouple.CombineInterface;
import com.example.couple.Model.Support.ConnectedSupport;
import com.example.couple.Model.Support.JackpotHistory;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;

@Getter
public class ConnectedBridge implements CombineInterface {
    List<ConnectedSupport> connectedSupports;
    JackpotHistory jackpotHistory;
    List<Integer> touchs;
    List<Integer> numbers;

    public ConnectedBridge(List<ConnectedSupport> connectedSupports, JackpotHistory jackpotHistory) {
        this.connectedSupports = connectedSupports;
        this.jackpotHistory = jackpotHistory;
        this.touchs = new ArrayList<>();
        if (!connectedSupports.isEmpty()) {
            int smallShadow0 = CoupleHandler.getSmallShadow(connectedSupports.get(0).getValue());
            touchs.add(smallShadow0);
            touchs.add(CoupleHandler.getShadow(smallShadow0));
            for (int i = 0; i < connectedSupports.size(); i++) {
                int smallShadow = CoupleHandler.getSmallShadow(connectedSupports.get(i).getValue());
                if (smallShadow0 != smallShadow) {
                    touchs.add(smallShadow);
                    touchs.add(CoupleHandler.getShadow(smallShadow));
                    break;
                }
            }
        }
        this.numbers = NumberArrayHandler.getTouchs(touchs);
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
        show += "    - " + Const.CONNECTED_BRIDGE_NAME + ": " + showTouchs() + ".";
        return show;
    }

    public String showCompactBridge() {
        String show = "";
        String win = jackpotHistory.isEmpty() ? "" : (isWin() ? " (trúng)" : " (trượt)");
        show += "    - " + Const.CONNECTED_BRIDGE_NAME + win + ": " + showTouchs() + ".";
        return show;
    }

    public static ConnectedBridge getEmpty() {
        return new ConnectedBridge(new ArrayList<>(), JackpotHistory.getEmpty());
    }

    public boolean isEmpty() {
        return connectedSupports.isEmpty() || numbers.isEmpty();
    }

}
