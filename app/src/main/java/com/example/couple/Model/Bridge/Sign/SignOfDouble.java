package com.example.couple.Model.Bridge.Sign;

import com.example.couple.Base.Handler.CoupleBase;

import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class SignOfDouble {
    List<Integer> upMonthList;
    List<Integer> monthList;
    List<Integer> downMonthList;
    List<Integer> weekList;
    List<DayDoubleSign> dayList;

    public static SignOfDouble getEmpty() {
        return new SignOfDouble(new ArrayList<>(), new ArrayList<>(),
                new ArrayList<>(), new ArrayList<>(), new ArrayList<>());
    }

    public boolean isEmpty() {
        return upMonthList.isEmpty() && monthList.isEmpty() &&
                downMonthList.isEmpty() && weekList.isEmpty() && dayList.isEmpty();
    }

    public String show() {
        String show = "";
        for (DayDoubleSign sign : dayList) {
            show += "  + GĐB " + sign.getJackpot().getJackpot()
                    + " đã ra cách đây " + CoupleBase.showCouple(sign.getDayNumberBefore()) + " ngày.\n";
        }

        if (!weekList.isEmpty()) {
            show += "  + Đường chạy tuần: " + CoupleBase.showCoupleNumbers(weekList, " ") + "\n";
        }

        if (!upMonthList.isEmpty()) {
            show += "  + Đường chéo tháng trên: " + CoupleBase.showCoupleNumbers(upMonthList, " ") + "\n";
        }

        if (!monthList.isEmpty()) {
            show += "  + Đường chéo tháng: " + CoupleBase.showCoupleNumbers(monthList, " ") + "\n";
        }

        if (!downMonthList.isEmpty()) {
            show += "  + Đường chéo tháng dưới: " + CoupleBase.showCoupleNumbers(downMonthList, " ") + "\n";
        }

        return show;
    }
}
