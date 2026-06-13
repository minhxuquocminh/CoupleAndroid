package com.example.couple.Model.Bridge.Double;

import com.example.couple.Model.Bridge.Bridge;
import com.example.couple.Model.Bridge.BridgeType;
import com.example.couple.Model.Bridge.JackpotHistory;

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
    public String showCompactInfo() {
        return BridgeType.UNAPPEARED_BIG_DOUBLE.name;
    }

    @Override
    public String showDetailInfo() {
        String show = showJackpotInfo();
        show += "\n\n" + BridgeType.UNAPPEARED_BIG_DOUBLE.name;
        show += "\n\nDàn số:\n" + showNumbers();
        return show.trim();
    }

    @Override
    public BridgeType getType() {
        return BridgeType.UNAPPEARED_BIG_DOUBLE;
    }

    public static UnappearedBigDoubleBridge getEmpty() {
        return new UnappearedBigDoubleBridge(new ArrayList<>(), JackpotHistory.getEmpty());
    }

    public boolean isEmpty() {
        return numbers.isEmpty();
    }
}
