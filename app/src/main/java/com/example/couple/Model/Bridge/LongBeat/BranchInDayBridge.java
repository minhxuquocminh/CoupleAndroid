package com.example.couple.Model.Bridge.LongBeat;

import com.example.couple.Base.Handler.CoupleBase;
import com.example.couple.Custom.Const.TimeInfo;
import com.example.couple.Model.Time.TimeBase;

import java.util.ArrayList;
import java.util.List;

public class BranchInDayBridge {
    TimeBase nextDay;
    List<TimeBase> lastCyclesToday;
    List<Integer> dayNumbersBefore;
    List<Integer> numbers;

    public static BranchInDayBridge getEmpty() {
        return new BranchInDayBridge(TimeBase.getEmpty(), new ArrayList<>());
    }

    public boolean isEmpty() {
        return nextDay.isEmpty() || lastCyclesToday.isEmpty();
    }

    public BranchInDayBridge(TimeBase nextDay, List<TimeBase> lastCyclesToday) {
        this.nextDay = nextDay;
        this.lastCyclesToday = lastCyclesToday;
        this.dayNumbersBefore = new ArrayList<>();
        this.numbers = new ArrayList<>();
        if (!lastCyclesToday.isEmpty()) {
            boolean runFlag = false;
            for (TimeBase timeBase : lastCyclesToday) {
                int diff = (int) timeBase.getDateBase().distance(nextDay.getDateBase());
                this.dayNumbersBefore.add(diff);
                if (diff == 6 || diff == 11 || diff == 16 || diff == 21) {
                    runFlag = true;
                }
            }
            if (runFlag) {
                this.numbers = nextDay.getDateCycle().getDay()
                        .getBranch().getIntYears(TimeInfo.CURRENT_YEAR);
            }
        }
    }

    public String show() {
        String show = "";
        for (int i = 0; i < lastCyclesToday.size(); i++) {
            int position = lastCyclesToday.get(i).getDateCycle().getDay().getBranch().getPosition();
            show += "  + Chi " + position + " đã chạy cách đây " + dayNumbersBefore.get(i) + " ngày.";
            if (i != lastCyclesToday.size() - 1) {
                show += "\n";
            }
        }
        if (!numbers.isEmpty()) {
            show += "\n=> Ngày tiếp theo: " + CoupleBase.showCoupleNumbers(numbers);
        }
        return show;
    }
}
