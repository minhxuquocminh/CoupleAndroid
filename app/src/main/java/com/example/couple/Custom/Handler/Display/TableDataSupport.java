package com.example.couple.Custom.Handler.Display;

import android.content.Context;
import android.view.Gravity;

import com.example.couple.Base.View.Spacing;
import com.example.couple.Base.View.TextViewBase;
import com.example.couple.Model.DateTime.Date.DateBase;
import com.example.couple.Model.Origin.Jackpot;
import com.example.couple.R;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TableDataSupport {

    public static Map<Integer, TextViewBase> getSundayTextViewManager(Context context, List<Jackpot> jackpotList, int dayNumber) {
        if (jackpotList.size() <= 2) return new HashMap<>();
        int viewSize = Math.min(dayNumber, jackpotList.size() - 2);
        Map<Integer, TextViewBase> textViewBaseMap = new HashMap<>();
        int count = 0;
        for (int i = viewSize; i >= -1; i--) {
            DateBase dateBase = i != -1 ? jackpotList.get(i).getDateBase() :
                    jackpotList.get(0).getDateBase().addDays(1);
            if (dateBase.isItOnSunday()) {
                textViewBaseMap.put(count, TextViewBase.builder().context(context).textSize(15)
                        .gravity(Gravity.CENTER).padding(Spacing.by(10, 5, 10, 5))
                        .textColor(R.color.colorText).background(R.color.colorBgSunday).build());
            }
            count++;
        }
        return textViewBaseMap;
    }

}
