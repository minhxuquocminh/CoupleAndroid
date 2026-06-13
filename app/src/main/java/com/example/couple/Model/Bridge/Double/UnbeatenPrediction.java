package com.example.couple.Model.Bridge.Double;

import com.example.couple.Base.Handler.CoupleBase;
import com.example.couple.Model.Origin.Couple;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
public class UnbeatenPrediction {
    Couple predictedCouple;
    List<SourceDoubleCouple> sourceDoubleCouples;

    public UnbeatenPrediction(Couple predictedCouple) {
        this.predictedCouple = predictedCouple;
        this.sourceDoubleCouples = new ArrayList<>();
    }

    public void addSource(Couple sourceDoubleCouple, int dayNumberBefore) {
        sourceDoubleCouples.add(new SourceDoubleCouple(sourceDoubleCouple, dayNumberBefore));
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

        public String show() {
            return sourceDoubleCouple.show() + " (" + CoupleBase.showCouple(dayNumberBefore) + " ngày)";
        }
    }
}
