package com.example.couple.Model.Bridge.LongBeat;

import com.example.couple.Base.Handler.CoupleBase;
import com.example.couple.Model.Couple.CoupleBeat;
import com.example.couple.Model.Display.BCouple;
import com.example.couple.Model.Origin.Couple;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AfterDoubleExtendBridge {
    List<Couple> lastDoubleRange;
    Couple doubleCouple;
    int dayNumberBefore;
    Map<Couple, List<CoupleBeat>> beatsByCouple;

    public AfterDoubleExtendBridge(List<Couple> lastDoubleRange, int dayNumberBefore) {
        this.lastDoubleRange = lastDoubleRange;
        this.doubleCouple = Couple.getEmpty();
        this.dayNumberBefore = dayNumberBefore;
        this.beatsByCouple = new LinkedHashMap<>();
        if (lastDoubleRange.size() >= 3) {
            BCouple bCouple1 = BCouple.fromCouple(lastDoubleRange.get(0));
            BCouple bCouple2 = BCouple.fromCouple(lastDoubleRange.get(1));
            BCouple bCouple3 = BCouple.fromCouple(lastDoubleRange.get(2));
            this.doubleCouple = lastDoubleRange.get(2).getCouple();
            BCouple firstCouple = bCouple1.balanceTwo(bCouple2);
            BCouple secondCouple = bCouple2.balanceTwo(bCouple3);
            this.beatsByCouple.put(firstCouple.toCouple(), new ArrayList<>());
            this.beatsByCouple.put(secondCouple.toCouple(), new ArrayList<>());
            if (lastDoubleRange.size() >= 4) {
                BCouple bCouple4 = BCouple.fromCouple(lastDoubleRange.get(3));
                BCouple thirdCouple = bCouple3.balanceTwo(bCouple4);
                this.beatsByCouple.put(thirdCouple.toCouple(), new ArrayList<>());
            }
        }

    }

    public String show() {
        String show = "  + " + doubleCouple.show() +
                " (" + CoupleBase.showCouple(dayNumberBefore) + " ngày): ";
        for (Couple couple : beatsByCouple.keySet()) {
            List<CoupleBeat> beats = new ArrayList<>(Objects.requireNonNull(beatsByCouple.get(couple)));
            Collections.reverse(beats);
            String showBeats = beats.isEmpty() ? "" : " => " +
                    beats.stream().map(CoupleBeat::show).collect(Collectors.joining(" "));
            show += "\n     " + couple.show() + showBeats;
        }
        return show;
    }


}
