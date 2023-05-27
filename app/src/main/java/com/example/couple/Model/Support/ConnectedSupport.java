package com.example.couple.Model.Support;

import com.example.couple.Custom.Handler.LotteryHandler;
import com.example.couple.Model.Support.JackpotHistory;
import com.example.couple.Model.Support.Position;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class ConnectedSupport {
    int value;
    Position position;
    List<Integer> typeList;
    int numberOfRuns;
    JackpotHistory jackpotHistory;

    public String show() {
        String show = "Số " + value + " (G" + LotteryHandler.swapPrizeName[position.getFirstLevel()] +
                " - VT" + (position.getSecondLevel() + 1) + " - B: ";
        for (int i = 0; i < typeList.size(); i++) {
            int type = typeList.get(i);
            show += type < 0 ? "+" + type * -1 : type + "";
            if (i != typeList.size() - 1) {
                show += ", ";
            }
        }
        show += ")";
        return show;
    }

    public String showShort() {
        return value + " (" + typeList.size() + ")";
    }
    
}
