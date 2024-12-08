package com.example.couple.Model.Bridge.NumberSet;

import androidx.annotation.NonNull;

import com.example.couple.Base.Handler.CoupleBase;
import com.example.couple.Custom.Const.Const;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import lombok.Getter;

@Getter
public class Set {
    int first;
    int second;

    public Set(int first, int second) {
        if (first < 0 || first > 9 || second < 0 || second > 9) {
            this.first = Const.EMPTY_VALUE;
            this.second = Const.EMPTY_VALUE;
            return;
        }
        this.first = first;
        this.second = second;
    }

    public static Set getFrom(int couple) {
        if (couple < 0 || couple > 99) {
            return Set.getEmpty();
        }
        return new Set(couple / 10, couple % 10);
    }

    public static Set getEmpty() {
        return new Set(Const.EMPTY_VALUE, Const.EMPTY_VALUE);
    }

    public boolean isEmpty() {
        return first == Const.EMPTY_VALUE || second == Const.EMPTY_VALUE;
    }

    public boolean isItMatch(int coupleInt) {
        int shadow = CoupleBase.getSmallShadow(coupleInt);
        return shadow == CoupleBase.getSmallShadow(getSetInt());
    }

    public int getSetInt() {
        return first * 10 + second;
    }

    @NonNull
    @Override
    public String toString() { // to show in GenericBase
        return first + "" + second;
    }

    public String show() {
        return first + "" + second;
    }

    public boolean equalsSet(Set set) {
        return CoupleBase.getSmallShadow(getSetInt()) == CoupleBase.getSmallShadow(set.getSetInt());
    }

    public List<Integer> getSetsDetail() {
        int smallShadow = CoupleBase.getSmallShadow(getSetInt());
        int smallFirst = smallShadow / 10;
        int smallSecond = smallShadow % 10;
        int bigFirst = smallFirst + 5;
        int bigSecond = smallSecond + 5;

        List<Integer> numbers = new ArrayList<>();
        numbers.add(smallFirst * 10 + smallSecond);
        numbers.add(smallSecond * 10 + smallFirst);
        numbers.add(smallFirst * 10 + bigSecond);
        numbers.add(bigSecond * 10 + smallFirst);
        numbers.add(bigFirst * 10 + smallSecond);
        numbers.add(smallSecond * 10 + bigFirst);
        numbers.add(bigFirst * 10 + bigSecond);
        numbers.add(bigSecond * 10 + bigFirst);

        return numbers.stream().distinct().collect(Collectors.toList());
    }
}
