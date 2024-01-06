package com.example.couple.Model.Bridge.LongBeat;

import com.example.couple.Base.Handler.CoupleBase;
import com.example.couple.Custom.Const.Const;
import com.example.couple.Model.Display.BCouple;
import com.example.couple.Model.Display.Set;
import com.example.couple.Model.Origin.Couple;
import com.example.couple.Model.Time.DateBase;

import java.util.List;

import lombok.Getter;

@Getter
public class AfterDoubleBridge {
    List<Couple> lastDoubleRange;
    int doubleInt;
    int dayNumberBefore;
    BCouple firstCouple;
    BCouple secondCouple;
    Set firstSet;
    Set secondSet;

    public AfterDoubleBridge(List<Couple> lastDoubleRange, int dayNumberBefore) {
        this.lastDoubleRange = lastDoubleRange;
        this.doubleInt = -1;
        this.dayNumberBefore = dayNumberBefore;
        this.firstCouple = BCouple.getEmpty();
        this.secondCouple = BCouple.getEmpty();
        this.firstSet = Set.getEmpty();
        this.secondSet = Set.getEmpty();
        if (lastDoubleRange.size() >= 2) {
            BCouple bCouple1 = BCouple.fromCouple(lastDoubleRange.get(0));
            BCouple bCouple2 = BCouple.fromCouple(lastDoubleRange.get(1));
            this.doubleInt = lastDoubleRange.get(1).getCoupleInt();
            this.firstCouple = bCouple1.balanceTwo(bCouple2);
            this.firstSet = new Set(firstCouple.getCoupleInt());
            if (lastDoubleRange.size() >= 3) {
                BCouple bCouple3 = BCouple.fromCouple(lastDoubleRange.get(2));
                this.secondCouple = bCouple2.balanceTwo(bCouple3);
                this.secondSet = new Set(secondCouple.getCoupleInt());
            }
        }

    }

    public String show() {
        String show = "";
        show = "  + " + CoupleBase.showCouple(doubleInt) +
                " (" + dayNumberBefore + " ngày):";
        if (!firstCouple.isEmpty()) {
            show += " bộ " + firstCouple.getCoupleInt() + ";";
        }
        if (!secondCouple.isEmpty()) {
            show += " bộ " + secondCouple.getCoupleInt() + ";";
        }
        return show;
    }

}
