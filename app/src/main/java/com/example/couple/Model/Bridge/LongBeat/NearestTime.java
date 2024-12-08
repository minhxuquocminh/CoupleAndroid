package com.example.couple.Model.Bridge.LongBeat;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class NearestTime {
    int number;
    int dayNumberBefore; // Const.MAX_DAY_NUMBER_BEFORE <=> ko thấy.
    int appearanceTimes;
    String type; // 0 : same double, 1: head, 2: tail

    public String show() {
        String show = number + "";
        if (appearanceTimes == 0) {
            show += " chưa về lần nào";
        } else {
            show += " đã về " + appearanceTimes + " lần, lần gần nhất cách đây " + dayNumberBefore + " ngày";
        }
        return show;
    }

}
