package com.example.couple.Model.BridgeSingle;

import com.example.couple.Base.Handler.NumberBase;
import com.example.couple.Custom.Handler.NumberArrayHandler;
import com.example.couple.Model.Support.JackpotHistory;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;

@Getter
public class CombineTouchBridge {
    String firstBridgeName;
    List<Integer> firstList;
    String secondBridgeName;
    List<Integer> secondList;
    String thirdBridgeName;
    List<Integer> thirdList;
    JackpotHistory jackpotHistory;
    List<Integer> touchs;
    List<String> bridgeNames;

    public CombineTouchBridge() {
        this.firstBridgeName = "";
        this.firstList = new ArrayList<>();
        this.secondBridgeName = "";
        this.secondList = new ArrayList<>();
        this.thirdBridgeName = "";
        this.thirdList = new ArrayList<>();
        this.jackpotHistory = new JackpotHistory();
    }

    public CombineTouchBridge(String firstBridgeName, List<Integer> firstList, String secondBridgeName,
                              List<Integer> secondList, String thirdBridgeName, List<Integer> thirdList,
                              JackpotHistory jackpotHistory) {
        this.firstBridgeName = firstBridgeName;
        this.firstList = firstList;
        this.secondBridgeName = secondBridgeName;
        this.secondList = secondList;
        this.thirdBridgeName = thirdBridgeName;
        this.thirdList = thirdList;
        this.jackpotHistory = jackpotHistory;
        this.bridgeNames = new ArrayList<>();
        if (!firstBridgeName.equals("")) {
            bridgeNames.add(firstBridgeName);
        }
        if (!secondBridgeName.equals("")) {
            bridgeNames.add(secondBridgeName);
        }
        if (!thirdBridgeName.equals("")) {
            bridgeNames.add(thirdBridgeName);
        }

        this.touchs = new ArrayList<>();
        for (String bridgeName : bridgeNames) {
            List<Integer> bridgeTouchs = getBridgeTouchs(bridgeName);
            this.touchs = NumberArrayHandler.getMatchNumbers(this.touchs, bridgeTouchs);
        }
    }

    private List<Integer> getBridgeTouchs(String bridgeName) {
        if (bridgeName.equals(firstBridgeName)) return firstList;
        if (bridgeName.equals(secondBridgeName)) return secondList;
        if (bridgeName.equals(thirdBridgeName)) return thirdList;
        return new ArrayList<>();
    }

    public boolean isWin() {
        return NumberBase.isTouch(jackpotHistory, touchs);
    }

    public String showTouchs() {
        return NumberBase.showTouchs(touchs);
    }

    public String showBridge() {
        String show = "";
        for (String bridgeName : bridgeNames) {
            List<Integer> bridgeTouchs = getBridgeTouchs(bridgeName);
            String win = NumberBase.isTouch(jackpotHistory, bridgeTouchs) ? "trúng" : "trượt";
            show += " * " + jackpotHistory.show() + " - " + win + "\n";
            show += " - " + bridgeName + ": " + NumberBase.showTouchs(bridgeTouchs) + ".\n";
        }
        String win = isWin() ? "trúng" : "trượt";
        show += " * KQ tổ hợp (" + win + "): " + showTouchs();
        return show.trim();
    }

}
