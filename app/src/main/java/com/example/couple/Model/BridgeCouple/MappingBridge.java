package com.example.couple.Model.BridgeCouple;

import com.example.couple.Custom.Handler.CoupleHandler;
import com.example.couple.Custom.Const.Const;
import com.example.couple.Model.Support.JackpotHistory;

import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class MappingBridge implements CombineInterface {
    List<Integer> numbers;
    JackpotHistory jackpotHistory;

    public String showBridge() {
        String show = "";
        String win = jackpotHistory.isEmpty() ? "" : (isWin() ? "trúng" : "trượt");
        show += " * " + jackpotHistory.show() + " - " + win + "\n";
        show += "    - " + Const.MAPPING_BRIDGE_NAME + ": " + showNumbers() + " (" + numbers.size() + " số).";
        return show;
    }

    public String showCompactBridge() {
        String show = "";
        String win = jackpotHistory.isEmpty() ? "" : (isWin() ? " (trúng)" : " (trượt)");
        show += "    - " + Const.MAPPING_BRIDGE_NAME + win + ": " + numbers.size() + " số.";
        return show;
    }

    public boolean isWin() {
        return CoupleHandler.isWin(jackpotHistory, numbers);
    }

    public String showNumbers() {
        return CoupleHandler.showCoupleNumbers(numbers);
    }

    public static MappingBridge getEmpty() {
        return new MappingBridge(new ArrayList<>(), JackpotHistory.getEmpty());
    }

    public boolean isEmpty() {
        return numbers.isEmpty();
    }
}
