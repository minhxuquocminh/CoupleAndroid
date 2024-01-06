package com.example.couple.Model.Display;

import com.example.couple.Base.Handler.CoupleBase;
import com.example.couple.Base.Handler.NumberBase;
import com.example.couple.Custom.Const.Const;
import com.example.couple.Model.Origin.Couple;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;

@Getter
public class Set {
    int first;
    int second;

    public Set(int first, int second) {
        if (first < 0 || second < 0) {
            this.first = first;
            this.second = second;
            return;
        }
        int cache1 = CoupleBase.getSmallShadow(first);
        int cache2 = CoupleBase.getSmallShadow(second);
        this.first = cache1 < cache2 ? cache1 : cache2;
        this.second = cache1 < cache2 ? cache2 : cache1;
    }

    public static Set getEmpty() {
        return new Set(Const.EMPTY_VALUE, Const.EMPTY_VALUE);
    }

    public boolean isEmpty() {
        return first == Const.EMPTY_VALUE || second == Const.EMPTY_VALUE;
    }

    public Set(int couple) {
        if (couple < 0) return;
        if (couple < 10) {
            this.first = 0;
            this.second = CoupleBase.getSmallShadow(couple);
        } else {
            String coupleStr = couple + "";
            int first = Integer.parseInt(coupleStr.charAt(0) + "");
            int second = Integer.parseInt(coupleStr.charAt(1) + "");
            this.first = CoupleBase.getSmallShadow(first);
            this.second = CoupleBase.getSmallShadow(second);
        }
    }

    public boolean isItMatch(Couple couple) {
        int shadow = CoupleBase.getSmallShadow(couple.getCoupleInt());
        return shadow == getSetInt();
    }

    @Override
    public String toString() {
        return first + "" + second + ", ";
    }

    public int getSetInt() {
        return Integer.parseInt(first + "" + second);
    }

    public String show() {
        return first + "" + second;
    }

    public boolean equalsSet(Set set) {
        return first == set.getFirst() && second == set.getSecond();
    }

    public List<Integer> getSetsDetail() {
        int shadowOfFirst = first + 5;
        int shadowOfSecond = second + 5;

        List<Integer> numbers = new ArrayList<>();
        numbers.add(Integer.valueOf(first + "" + second));
        numbers.add(Integer.valueOf(second + "" + first));
        numbers.add(Integer.valueOf(first + "" + shadowOfSecond));
        numbers.add(Integer.valueOf(shadowOfSecond + "" + first));
        numbers.add(Integer.valueOf(shadowOfFirst + "" + second));
        numbers.add(Integer.valueOf(second + "" + shadowOfFirst));
        numbers.add(Integer.valueOf(shadowOfFirst + "" + shadowOfSecond));
        numbers.add(Integer.valueOf(shadowOfSecond + "" + shadowOfFirst));

        return NumberBase.filterDuplicatedNumbers(numbers);
    }
}
