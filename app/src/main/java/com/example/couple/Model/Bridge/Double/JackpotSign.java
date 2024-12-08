package com.example.couple.Model.Bridge.Double;

import com.example.couple.Model.Origin.Jackpot;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class JackpotSign {
    List<Jackpot> jackpotList;
    List<Integer> beatList;
    int sameDouble;

    public String show() {
        String show = "";
        for (int i = 0; i < jackpotList.size(); i++) {
            show += jackpotList.get(i).getJackpot() + " (" + beatList.get(i) + ")";
            if (i != jackpotList.size() - 1) {
                show += ", ";
            }
        }
        String showSameDouble = (sameDouble == -1) ? "??" : sameDouble + "";
        return show + " => " + showSameDouble;
    }
}
