package com.example.couple.Model.Bridge.Couple;

import com.example.couple.Custom.Const.Const;
import com.example.couple.Model.Bridge.Bridge;
import com.example.couple.Model.Support.JackpotHistory;

import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class UnappearedBigDoubleBridge extends Bridge {
    List<Integer> numbers;
    JackpotHistory jackpotHistory;

    @Override
    public String showCompactNumbers() {
        return "";
    }

    @Override
    public String getBridgeName() {
        return Const.UNAPPEARED_BIG_DOUBLE_BRIDGE_NAME;
    }

    public static UnappearedBigDoubleBridge getEmpty() {
        return new UnappearedBigDoubleBridge(new ArrayList<>(), JackpotHistory.getEmpty());
    }

    public boolean isEmpty() {
        return numbers.isEmpty();
    }
}
