package com.example.couple.Model.Display;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class NearestTime {
    int number;
    int type; // 0 : same double, 1: head, 2: tail
    int dayNumberBefore; // Const.MAX_DAY_NUMBER_BEFORE <=> ko thấy.
    int appearanceTimes;

    public String show() {
        String show = number + "";
        if (appearanceTimes == 0) {
            show += " chưa về lần nào";
        } else {
            show += " đã về " + appearanceTimes + " lần, lần gần nhất cách đây " + dayNumberBefore + " ngày";
        }
        return show;
    }

    public String showType() {
        if (type == 0) return "kép";
        if (type == 1) return "đầu";
        if (type == 2) return "đuôi";
        return "";
    }
}
