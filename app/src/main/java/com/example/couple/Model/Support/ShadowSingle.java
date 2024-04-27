package com.example.couple.Model.Support;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class ShadowSingle {
    List<Single> firstsTens;
    List<Single> secondsTens;
    List<Single> firstsUnits;
    List<Single> secondsUnits;

    public static List<ShadowCouple> getShadowCoupleList(List<Single> firsts, List<Single> seconds) {
        List<ShadowCouple> shadowCouples = new ArrayList<>();
        for (Single first : firsts) {
            for (Single second : seconds) {
                ShadowCouple shadowCouple = new ShadowCouple(first.getNumber() * 10 + second.getNumber(),
                        first.getType() + second.getType());
                ShadowCouple shadowCouple2 = new ShadowCouple(second.getNumber() * 10 + first.getNumber(),
                        first.getType() + second.getType());
                shadowCouples.add(shadowCouple);
                shadowCouples.add(shadowCouple2);
            }
        }
        for (int i = 0; i < shadowCouples.size(); i++) {
            for (int j = i + 1; j < shadowCouples.size(); j++) {
                if (shadowCouples.get(i).getNumber().equals(shadowCouples.get(j).getNumber())) {
                    shadowCouples.remove(j);
                    j--;
                }
            }
        }
        return shadowCouples;
    }

    public List<ShadowCouple> getShadowCouplesTens() {
        return ShadowSingle.getShadowCoupleList(firstsTens, secondsTens);
    }

    public List<ShadowCouple> getShadowCouplesUnits() {
        return ShadowSingle.getShadowCoupleList(firstsUnits, secondsUnits);
    }

    public List<ShadowCouple> getAllShadowCouples() {
        List<ShadowCouple> results = new ArrayList<>();
        List<ShadowCouple> tensList = getShadowCouplesTens();
        List<ShadowCouple> unitsList = getShadowCouplesUnits();
        results.addAll(tensList);
        results.addAll(unitsList);

        for (int i = 0; i < results.size(); i++) {
            for (int j = i + 1; j < results.size(); j++) {
                if (results.get(i).getNumber().equals(results.get(j).getNumber())) {
                    results.remove(j);
                    j--;
                }
            }
        }
        Collections.sort(results, (x, y) -> x.getType() - y.getType());
        return results;
    }

    public static ShadowSingle getEmpty() {
        return new ShadowSingle(new ArrayList<>(),
                new ArrayList<>(), new ArrayList<>(), new ArrayList<>());
    }

    public boolean isEmpty() {
        return firstsTens.isEmpty() &&
                secondsTens.isEmpty() && firstsUnits.isEmpty() && secondsUnits.isEmpty();
    }

}
