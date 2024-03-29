package com.example.couple.Model.Bridge.Couple;

import com.example.couple.Custom.Handler.CoupleHandler;
import com.example.couple.Custom.Handler.LotteryHandler;
import com.example.couple.Custom.Handler.NumberArrayHandler;
import com.example.couple.Model.Display.Set;
import com.example.couple.Model.Display.Status;
import com.example.couple.Model.Support.JackpotHistory;
import com.example.couple.Model.Support.Position;
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
    List<Status> statusList;
    JackpotHistory jackpotHistory;

    public int getEnoughTouchs() {
        int count = 0;
        for (int i = 0; i < statusList.size(); i++) {
            if (!statusList.get(i).haveZeroTouch()) {
                count++;
            }
        }
        return count;
    }

    public List<Integer> getSortedSmallShadowSingles() {
        List<Integer> singles = new ArrayList<>();
        singles.add(CoupleHandler.getSmallShadow(firstBridge.getValue()));
        singles.add(CoupleHandler.getSmallShadow(secondBridge.getValue()));
        singles.add(CoupleHandler.getSmallShadow(thirdBridge.getValue()));
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
        Position first = firstBridge.getPosition();
        Position second = secondBridge.getPosition();
        Position third = thirdBridge.getPosition();

        String show = "Các số: " + firstBridge.getValue() + ", " + secondBridge.getValue() + ", "
                + thirdBridge.getValue() + " - G" + LotteryHandler.swapPrizeName[first.getFirstLevel()] +
                " VT" + (first.getSecondLevel() + 1) + ", G" +
                LotteryHandler.swapPrizeName[second.getFirstLevel()] +
                " VT" + (second.getSecondLevel() + 1) + ", G" +
                LotteryHandler.swapPrizeName[third.getFirstLevel()] +
                " VT" + (third.getSecondLevel() + 1) + " \n TT: ";
        for (int i = 0; i < statusList.size(); i++) {
            show += statusList.get(i).show();
            if (i != statusList.size() - 1) {
                show += ", ";
            }
        }
        return show;
    }

}
