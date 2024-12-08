package com.example.couple.Model.Bridge;

import com.example.couple.Base.Handler.NumberBase;
import com.example.couple.Model.Bridge.Estimated.EstimatedBridge;
import com.example.couple.Model.Bridge.Mapping.MappingBridge;
import com.example.couple.Model.Bridge.Touch.CombineTouchBridge;
import com.example.couple.Model.Origin.Jackpot;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import lombok.Getter;

@Getter
public class SyntheticBridge extends Bridge {
    CombineTouchBridge combineTouchBridge;
    MappingBridge mappingBridge;
    EstimatedBridge estimatedBridge;
    JackpotHistory jackpotHistory;
    List<Integer> numbers;

    public SyntheticBridge(List<Jackpot> lastTwoJackpot, CombineTouchBridge combineTouchBridge,
                           MappingBridge mappingBridge, EstimatedBridge estimatedBridge,
                           JackpotHistory jackpotHistory) {
        this.combineTouchBridge = combineTouchBridge;
        this.mappingBridge = mappingBridge;
        this.estimatedBridge = estimatedBridge;
        this.numbers = combineTouchBridge.getNumbers();
        this.jackpotHistory = jackpotHistory;
        int mappingSize = mappingBridge.getNumbers().size();
        int estimatedSize = estimatedBridge.getNumbers().size();
        boolean sameHeadTail = isSameHeadTail(lastTwoJackpot);
        boolean isZeroHead = isZeroHead(lastTwoJackpot);
        if (isUseMappingBridge(mappingSize, sameHeadTail, isZeroHead)) {
            numbers = NumberBase.getMatchNumbers(numbers, mappingBridge.getNumbers());
        }
        List<Integer> badList = Arrays.asList(57, 62, 68, 72, 78, 73, 76, 77);
        if (estimatedSize >= 55 && !badList.contains(estimatedSize)) {
            numbers = NumberBase.getMatchNumbers(numbers, estimatedBridge.getNumbers());
        }
    }

    public boolean isUseMappingBridge(int sizeBridge, boolean sameHeadTail, boolean isZeroHead) {
        if (isZeroHead) return sizeBridge >= 90;
        if (sameHeadTail) return sizeBridge >= 80;
        return sizeBridge > 70;
    }

    public boolean isSameHeadTail(List<Jackpot> lastTwoJackpot) {
        if (lastTwoJackpot.size() < 2) return false;
        int firstCouple = lastTwoJackpot.get(0).getCoupleInt();
        int secondCouple = lastTwoJackpot.get(0).getCoupleInt();
        if (firstCouple / 10 == secondCouple / 10) return true;
        return firstCouple % 10 == secondCouple % 10;
    }

    public boolean isZeroHead(List<Jackpot> lastTwoJackpot) {
        if (lastTwoJackpot.size() < 2) return false;
        int firstCouple = lastTwoJackpot.get(0).getCoupleInt();
        int secondCouple = lastTwoJackpot.get(0).getCoupleInt();
        if (firstCouple / 10 == 0) return true;
        return secondCouple % 10 == 0;
    }


    @Override
    public String showCompactNumbers() {
        return "";
    }

    @Override
    public String getBridgeName() {
        return BridgeType.SYNTHETIC.name;
    }

    public static SyntheticBridge getEmpty() {
        return new SyntheticBridge(new ArrayList<>(), CombineTouchBridge.getEmpty(), MappingBridge.getEmpty(),
                EstimatedBridge.getEmpty(), JackpotHistory.getEmpty());
    }

    public boolean isEmpty() {
        return combineTouchBridge.isEmpty() || numbers.isEmpty();
    }
}
