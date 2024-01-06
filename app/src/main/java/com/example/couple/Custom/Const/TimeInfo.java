package com.example.couple.Custom.Const;

import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

public class TimeInfo {
    public static final int DAY_OF_MONTH = 31;
    public static final int DAY_OF_WEEK = 7;
    public static final int DAY_OF_YEAR = 365;
    public static final int MONTH_OF_YEAR = 12;
    public static final int CYCLE_START_YEAR = 1900;
    public static final int CURRENT_YEAR = Calendar.getInstance().get(Calendar.YEAR);
    public static final int CURRENT_MONTH = Calendar.getInstance().get(Calendar.MONTH) + 1;
    public static final int CURRENT_DAY = Calendar.getInstance().get(Calendar.DAY_OF_MONTH);

    public static final List<String> HEAVENLY_STEMS = Arrays.asList(
            "Canh", "Tân", "Nhâm", "Quý", "Giáp", "Ất", "Bính", "Đinh", "Mậu", "Kỷ"
    );

    public static final List<String> EARTHLY_BRANCHES = Arrays.asList(
            "Tý", "Sửu", "Dần", "Mão", "Thìn", "Tỵ", "Ngọ", "Mùi", "Thân", "Dậu", "Tuất", "Hợi"
    );

}
