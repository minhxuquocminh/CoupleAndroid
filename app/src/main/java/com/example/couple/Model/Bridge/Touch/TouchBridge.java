package com.example.couple.Model.Bridge.Touch;

import com.example.couple.Base.Handler.SingleBase;
import com.example.couple.Model.Bridge.Bridge;

import java.util.List;

public abstract class TouchBridge extends Bridge {
    public abstract List<Integer> getTouches();

    @Override
    public String showCompactInfo() {
        return SingleBase.showTouches(getTouches());
    }

    @Override
    public String showDetailInfo() {
        String show = showJackpotInfo();
        show += "\n\nChạm:\n" + SingleBase.showTouches(getTouches(), ", ");
        show += "\n\nDàn số:\n" + showNumbers();
        return show.trim();
    }
}
