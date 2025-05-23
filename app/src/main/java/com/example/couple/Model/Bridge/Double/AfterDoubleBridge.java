package com.example.couple.Model.Bridge.Double;

import androidx.annotation.NonNull;

import com.example.couple.Base.Handler.CoupleBase;
import com.example.couple.Model.Bridge.NumberSet.SetBase;
import com.example.couple.Model.Statistics.BCouple;
import com.example.couple.Model.Origin.Couple;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AfterDoubleBridge {
    List<Couple> lastDoubleRange;
    int doubleInt;
    int dayNumberBefore;
    Map<Integer, SetBase> setMap;

    public AfterDoubleBridge(List<Couple> lastDoubleRange, int dayNumberBefore) {
        this.lastDoubleRange = lastDoubleRange;
        this.doubleInt = -1;
        this.dayNumberBefore = dayNumberBefore;
        this.setMap = new HashMap<>();
        if (lastDoubleRange.size() >= 3) {
            BCouple bCouple1 = BCouple.fromCouple(lastDoubleRange.get(0));
            BCouple bCouple2 = BCouple.fromCouple(lastDoubleRange.get(1));
            BCouple bCouple3 = BCouple.fromCouple(lastDoubleRange.get(2));
            this.doubleInt = lastDoubleRange.get(2).getInt();
            BCouple upFirstCouple = bCouple1.balanceTwo(bCouple2);
            BCouple firstCouple = bCouple2.balanceTwo(bCouple3);
            this.setMap.put(1, SetBase.getFrom(firstCouple.getCoupleInt()));
            int thirdCouple = firstCouple.getSecond() * 10 + Math.abs(upFirstCouple.getFirst());
            int fourthCouple = firstCouple.getSecond() * 10 + Math.abs(upFirstCouple.getSecond());
            this.setMap.put(3, SetBase.getFrom(thirdCouple));
            this.setMap.put(4, SetBase.getFrom(fourthCouple));
            if (lastDoubleRange.size() >= 4) {
                BCouple bCouple4 = BCouple.fromCouple(lastDoubleRange.get(3));
                BCouple secondCouple = bCouple3.balanceTwo(bCouple4);
                this.setMap.put(2, SetBase.getFrom(secondCouple.getCoupleInt()));
            }
        }

    }

    @NonNull
    @Override
    public String toString() { // for GenericBase
        String show = "  + " + CoupleBase.showCouple(doubleInt) +
                " (" + CoupleBase.showCouple(dayNumberBefore) + " ngày): các bộ";
        for (int i = 1; i <= 4; i++) {
            if (this.setMap.containsKey(i)) {
                show += " " + Objects.requireNonNull(setMap.get(i)).show() + " (" + i + ");";
            }
        }
        return show;
    }

}
