package com.example.couple.Model.Support;

import com.example.couple.Base.Handler.NumberBase;
import com.example.couple.Custom.Handler.NumberArrayHandler;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class NumberArray implements Serializable {
    String sets;
    String sums;
    String touchs;
    String heads;
    String tails;
    String adds;
    String removes;
    String numbers;
    String selectiveNumbers;

    public NumberArray(String sets, String sums, String touchs, String heads,
                       String tails, String adds, String removes) {
        this.sets = sets;
        this.sums = sums;
        this.touchs = touchs;
        this.heads = heads;
        this.tails = tails;
        this.adds = adds;
        this.removes = removes;

        List<Integer> setVerify = NumberBase.verifyNumberArray(sets, 2);
        List<Integer> sumVerify = NumberBase.verifyNumberArray(sums, 1);
        List<Integer> touchVerify = NumberBase.verifyNumberArray(touchs, 1);
        List<Integer> headVerify = NumberBase.verifyNumberArray(heads, 1);
        List<Integer> tailVerify = NumberBase.verifyNumberArray(tails, 1);

        List<Integer> setList = NumberArrayHandler.getSetsByCouples(setVerify);
        List<Integer> sumList = NumberArrayHandler.getSums(sumVerify);
        List<Integer> touchList = NumberArrayHandler.getTouchs(touchVerify);
        List<Integer> headList = NumberArrayHandler.getHeads(headVerify);
        List<Integer> tailList = NumberArrayHandler.getTails(tailVerify);
        List<Integer> addList = NumberBase.verifyNumberArray(adds, 2);
        List<Integer> removeList = NumberBase.verifyNumberArray(removes, 2);

        List<Integer> list = new ArrayList<>();
        list.addAll(setList);
        list.addAll(sumList);
        list.addAll(touchList);
        list.addAll(headList);
        list.addAll(tailList);
        list.addAll(addList);
        List<Integer> filter = NumberBase.filterDuplicatedNumbers(list);
        List<Integer> numberList = new ArrayList<>();
        for (int number : filter) {
            if (!removeList.contains(number)) {
                numberList.add(number);
            }
        }
        this.numbers = NumberBase.showNumbers(numberList, "; ", 2);
    }

}
