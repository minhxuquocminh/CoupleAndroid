package com.example.couple.Model.Bridge.Touch;

import com.example.couple.Base.Handler.SingleBase;
import com.example.couple.Custom.Handler.NumberArrayHandler;
import com.example.couple.Model.Bridge.BridgeType;
import com.example.couple.Model.Bridge.JackpotHistory;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import lombok.Getter;

@Getter
public class CombineTouchBridge extends TouchBridge {
    List<TouchBridge> touchBridges;
    JackpotHistory jackpotHistory;
    List<Integer> touchs;
    List<Integer> numbers;

    public CombineTouchBridge(List<TouchBridge> touchBridges, JackpotHistory jackpotHistory) {
        this.touchBridges = touchBridges;
        this.touchs = new ArrayList<>();
        this.numbers = new ArrayList<>();
        int[] a = new int[10];
        touchBridges.forEach(touchBridge -> {
            touchBridge.getTouchs().forEach(touch -> {
                a[touch]++;
            });
        });
        List<TouchData> touchDatas = new ArrayList<>();
        for (int i = 0; i < a.length; i++) {
            if (a[i] == 0) continue;
            touchDatas.add(new TouchData(i, a[i]));
        }
        touchDatas.sort(Comparator.comparingInt(TouchData::getTimes).reversed());
        int minSize = Math.min(touchDatas.size(), 4);
        for (int i = 0; i < minSize; i++) {
            this.touchs.add(touchDatas.get(i).getTouch());
        }
        this.numbers.addAll(NumberArrayHandler.getTouchs(touchs));
        this.jackpotHistory = jackpotHistory;
    }

    public static CombineTouchBridge getEmpty() {
        return new CombineTouchBridge(new ArrayList<>(), JackpotHistory.getEmpty());
    }

    public boolean isEmpty() {
        return numbers.isEmpty();
    }

    @Override
    public String showCompactNumbers() {
        return SingleBase.showTouchs(touchs);
    }

    @Override
    public String getBridgeName() {
        return BridgeType.COMBINE_TOUCH.name;
    }
}
