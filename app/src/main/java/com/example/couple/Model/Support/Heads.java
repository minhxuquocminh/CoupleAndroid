package com.example.couple.Model.Support;

import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
public class Heads {
    int lottoNumber;
    int headNumber;
    List<Lotto> headList;
    List<Integer> headListInt;

    public Heads(int lottoNumber, List<Lotto> headList) {
        this.lottoNumber = lottoNumber;
        this.headNumber = headList.size();
        this.headList = headList;
        this.headListInt = new ArrayList<>();
        for (Lotto headLotto : headList) {
            headListInt.add(headLotto.getValue());
        }
    }
}
