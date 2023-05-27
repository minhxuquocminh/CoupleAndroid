package com.example.couple.Model.BridgeSingle;

import com.example.couple.Base.Handler.NumberBase;
import com.example.couple.Custom.Handler.NumberArrayHandler;
import com.example.couple.Model.BridgeCouple.CombineInterface;
import com.example.couple.Model.BridgeCouple.MappingBridge;
import com.example.couple.Model.Support.ConnectedSupport;
import com.example.couple.Model.Support.JackpotHistory;

import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
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
            int smallShadow0 = NumberBase.getSmallShadow(connectedSupports.get(0).getValue());
            touchs.add(smallShadow0);
            touchs.add(NumberBase.getShadow(smallShadow0));
            for (int i = 0; i < connectedSupports.size(); i++) {
                int smallShadow = NumberBase.getSmallShadow(connectedSupports.get(i).getValue());
                if (smallShadow0 != smallShadow) {
                    touchs.add(smallShadow);
                    touchs.add(NumberBase.getShadow(smallShadow));
                    break;
                }
            }
        }
        this.numbers = NumberArrayHandler.getTouchs(touchs);
    }

    public boolean isWin() {
        return NumberBase.isTouch(jackpotHistory, getTouchs());
    }

    public String showNumbers() {
        return NumberBase.showNumbers(numbers);
    }

    public String showTouchs() {
        return NumberBase.showTouchs(touchs);
    }

    public String showBridge() {
        String show = "";
        String win = isWin() ? "trúng" : "trượt";
        show += " * " + jackpotHistory.show() + " - " + win + "\n";
        show += " - Cầu liên thông: " + showTouchs() + ".";
        return show;
    }

    public String showCompactBridge() {
        return showBridge();
    }

    public static ConnectedBridge getEmpty() {
        return new ConnectedBridge(new ArrayList<>(), new JackpotHistory());
    }

    public boolean isEmpty() {
        return jackpotHistory.isEmpty();
    }

}
