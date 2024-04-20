package com.example.couple.Model.Bridge.Couple;

import com.example.couple.Base.Handler.CoupleBase;
import com.example.couple.Custom.Handler.LotteryHandler;
import com.example.couple.Custom.Handler.NumberArrayHandler;
import com.example.couple.Model.Display.Set;
import com.example.couple.Model.Display.TriadStatus;
import com.example.couple.Model.Support.JackpotHistory;
import com.example.couple.Model.Support.SupportTriad;

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
            if (firstList.get(i) == secondList.get(i)) {
                count++;
            }
        }
        return count == firstList.size();
    }

    public List<Set> getSetList() {
        List<Integer> numbers = new ArrayList<>();
        numbers.add(firstBridge.getValue());
        numbers.add(secondBridge.getValue());
        numbers.add(thirdBridge.getValue());
        return NumberArrayHandler.getSetListBySingles(numbers);
    }

    public String show() {
        String show = "  + Các số: " + firstBridge.getValue() + ", " + secondBridge.getValue() + ", "
                + thirdBridge.getValue() + " - " + LotteryHandler.showPrize(firstBridge.getPosition()) +
                ", " + LotteryHandler.showPrize(secondBridge.getPosition()) + ", " +
                LotteryHandler.showPrize(thirdBridge.getPosition()) + " \n  TT: ";
        for (int i = 0; i < triadStatusList.size(); i++) {
            show += triadStatusList.get(i).show();
            if (i != triadStatusList.size() - 1) {
                show += ", ";
            }
        }
        return show;
    }

}
