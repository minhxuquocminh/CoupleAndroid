package com.example.couple.Custom.Const;

import java.util.Calendar;

public class TimeInfo {
    public static final int CURRENT_YEAR = Calendar.getInstance().get(Calendar.YEAR);
    public static final int CURRENT_MONTH = Calendar.getInstance().get(Calendar.MONTH) + 1;
    public static final int CURRENT_DAY = Calendar.getInstance().get(Calendar.DAY_OF_MONTH);
}
