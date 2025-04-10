package com.example.couple.Model.Bridge.Connected;

import com.example.couple.Base.Handler.CoupleBase;
import com.example.couple.Base.Handler.GenericBase;
import com.example.couple.Custom.Handler.NumberArrayHandler;
import com.example.couple.Model.Bridge.JackpotHistory;
import com.example.couple.Model.Bridge.NumberSet.SetBase;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class TriadBridge {
    SupportTriad firstBridge;
    SupportTriad secondBridge;
    SupportTriad thirdBridge;
    List<TriadStatus> triadStatusList;
    JackpotHistory jackpotHistory;

    public int getEnoughTouchs() {
        int count = 0;
        for (int i = 0; i < triadStatusList.size(); i++) {
            if (!triadStatusList.get(i).haveZeroTouch()) {
                count++;
            }
        }
        return count;
    }

    public List<Integer> getSortedSmallShadowSingles() {
        List<Integer> singles = new ArrayList<>();
        singles.add(CoupleBase.getSmallShadow(firstBridge.getValue()));
        singles.add(CoupleBase.getSmallShadow(secondBridge.getValue()));
        singles.add(CoupleBase.getSmallShadow(thirdBridge.getValue()));
        Collections.sort(singles);
        return singles;
    }

    public boolean equalsSmallShadowSingles(TriadBridge triadBridge) {
        List<Integer> firstList = getSortedSmallShadowSingles();
        List<Integer> secondList = triadBridge.getSortedSmallShadowSingles();
        int count = 0;
        for (int i = 0; i < firstList.size(); i++) {
            if (firstList.get(i).equals(secondList.get(i))) {
                count++;
            }
        }
        return count == firstList.size();
    }

    public List<SetBase> getSetList() {
        List<Integer> numbers = new ArrayList<>();
        numbers.add(firstBridge.getValue());
        numbers.add(secondBridge.getValue());
        numbers.add(thirdBridge.getValue());
        return NumberArrayHandler.getSetListBySingles(numbers);
    }

    public String show() {
        return "  + Các số: " + firstBridge.getValue() + ", " + secondBridge.getValue() + ", "
                + thirdBridge.getValue() + " - " + firstBridge.getPosition().showPrize() +
                ", " + secondBridge.getPosition().showPrize() + ", " +
                thirdBridge.getPosition().showPrize() + " \n  TT: " +
                GenericBase.getDelimiterString(triadStatusList, ", ");
    }

}
