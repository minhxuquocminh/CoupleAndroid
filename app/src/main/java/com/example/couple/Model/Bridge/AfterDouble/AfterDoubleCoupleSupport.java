package com.example.couple.Model.Bridge.AfterDouble;

import com.example.couple.Base.Handler.CoupleBase;
import com.example.couple.Model.Origin.Couple;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
public class AfterDoubleCoupleSupport {
    Couple predictedCouple;
    List<SourceDoubleCouple> sourceDoubleCouples;
    int priority;

    public AfterDoubleCoupleSupport(Couple predictedCouple) {
        this.predictedCouple = predictedCouple;
        this.sourceDoubleCouples = new ArrayList<>();
        this.priority = 3;
    }

    public void addSource(Couple sourceDoubleCouple, int dayNumberBefore, int beatCount) {
        int sourcePriority = getPriority(sourceDoubleCouple, dayNumberBefore);
        sourceDoubleCouples.add(new SourceDoubleCouple(sourceDoubleCouple, dayNumberBefore, beatCount, sourcePriority));
        priority = Math.min(priority, sourcePriority);
    }

    private int getPriority(Couple sourceDoubleCouple, int dayNumberBefore) {
        if (dayNumberBefore > 5) return 3;
        return sourceDoubleCouple.isNegativeDouble() ? 2 : 1;
    }

    public int getDayNumberBefore() {
        return sourceDoubleCouples.stream()
                .mapToInt(SourceDoubleCouple::getDayNumberBefore)
                .min()
                .orElse(0);
    }

    public String show() {
        String sources = sourceDoubleCouples.stream()
                .map(SourceDoubleCouple::show)
                .collect(Collectors.joining(", "));
        return "  + " + predictedCouple.show() + ": " + sources;
    }

    @AllArgsConstructor
    @Getter
    public static class SourceDoubleCouple {
        Couple sourceDoubleCouple;
        int dayNumberBefore;
        int beatCount;
        int priority;

        public String show() {
            return sourceDoubleCouple.show() + " (" + CoupleBase.showCouple(dayNumberBefore) +
                    " ngày, " + (beatCount == 0 ? "" : beatCount + " lần") + ")";
        }
    }
}
