package com.example.couple.Model.Bridge.LongBeat;

import androidx.annotation.NonNull;

import com.example.couple.Base.Handler.CoupleBase;
import com.example.couple.Base.Handler.NumberBase;
import com.example.couple.Custom.Const.TimeInfo;
import com.example.couple.Model.Display.SpecialSetHistory;
import com.example.couple.Model.Support.BranchInDaySupport;
import com.example.couple.Model.Time.Cycle.Branch;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class BranchInDayBridge {
    Branch nextDayBranch;
    List<Integer> beatList;
    List<BranchInDaySupport> supports;
    List<Integer> numbers;

    public static BranchInDayBridge getEmpty() {
        return new BranchInDayBridge(Branch.getEmpty(), new ArrayList<>());
    }

    public boolean isEmpty() {
        return nextDayBranch.isEmpty() || supports.isEmpty();
    }

    public BranchInDayBridge(Branch nextDayBranch, List<Integer> beatList) {
        this.nextDayBranch = nextDayBranch;
        this.beatList = beatList;
        this.supports = new ArrayList<>();
        this.numbers = new ArrayList<>();
        List<Integer> dayList = Arrays.asList(4, 5, 9, 10, 14, 15, 19, 20);
        if (!beatList.isEmpty()) {
            boolean runFlag = false;
            int dayNumberBefore = 0;
            for (int i = beatList.size() - 1; i >= 0; i--) {
                dayNumberBefore += beatList.get(i);
                Branch dayBranch = nextDayBranch.plusDays(-dayNumberBefore);
                this.supports.add(new BranchInDaySupport(dayBranch, dayNumberBefore));
                if (dayList.contains(dayNumberBefore)) {
                    runFlag = true;
                }
                if (dayNumberBefore > 20) break;
            }
            if (runFlag) {
                this.numbers = nextDayBranch.getTailsOfYear(TimeInfo.CURRENT_YEAR);
            }
        }
    }

    public SpecialSetHistory toSpecialSetHistory() {
        return new SpecialSetHistory("CTN", numbers, beatList);
    }

    @NonNull
    @Override
    public String toString() {
        String show = "  + Nhịp chạy: " + NumberBase.showNumbers(beatList, ", ") + ".";
        for (BranchInDaySupport support : supports) {
            int position = support.getLastBranchInDay().getPosition();
            show += "\n  + Chi " + CoupleBase.showCouple(position)
                    + " đã chạy cách đây " + CoupleBase.showCouple(support.getDayNumberBefore()) + " ngày.";
        }
        if (!numbers.isEmpty()) {
            show += "\n=> Ngày tiếp theo: " + CoupleBase.showCoupleNumbers(numbers);
        }
        return show;
    }
}
