package com.example.couple.Model.Bridge.Connected;

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

    public String show() {
        String show = "Số " + value + " (" + position.showPrize() + " - B: ";
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
