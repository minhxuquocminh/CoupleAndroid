package com.example.couple.Model.Display;

import com.example.couple.Base.Handler.NumberBase;
import com.example.couple.Model.Origin.Couple;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;

@Getter
public class Set {
    int first;
    int second;

    public Set(int first, int second) {
        int cache1 = NumberBase.getSmallShadow(first);
        int cache2 = NumberBase.getSmallShadow(second);
        this.first = cache1 < cache2 ? cache1 : cache2;
        this.second = cache1 < cache2 ? cache2 : cache1;
    }

    public Set(int couple) {
        if (couple < 0) return;
        if (couple < 10) {
            this.first = 0;
            this.second = couple;
        } else {
            String coupleStr = couple + "";
            int first = Integer.parseInt(coupleStr.charAt(0) + "");
            int second = Integer.parseInt(coupleStr.charAt(1) + "");
            this.first = NumberBase.getSmallShadow(first);
            this.second = NumberBase.getSmallShadow(second);
        }
    }

    public boolean isItMatch(Couple couple) {
        int shadow = NumberBase.getSmallShadow(couple.getCoupleInt());
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

        List<Integer> compactNumbers = new ArrayList<>();
        for (int i : numbers) {
            if (!compactNumbers.contains(i)) {
                compactNumbers.add(i);
            }
        }
        return compactNumbers;
    }
}
