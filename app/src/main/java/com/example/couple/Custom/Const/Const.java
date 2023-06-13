package com.example.couple.Custom.Const;

import java.util.Arrays;
import java.util.List;

public class Const {
    public static final int MAX_DAY_OF_MONTH = 31;
    public static final int DAY_OF_WEEK = 7;
    public static final int DAY_OF_YEAR = 365;
    public static final int MONTH_OF_YEAR = 12;
    public static final int MAX_ROW_COUNT_TABLE = 100;
    public static final int MAX_DAY_NUMBER_BEFORE = 999;
    public static final int NUMBER_OF_PRIZES = 27;
    public static final int MAX_DAYS_TO_GET_LOTTERY = 60;
    public static final int MAX_LENGTH_OF_PRIZE = 5;
    public static final int AMPLITUDE_OF_PERIOD_BRIDGE = 13;
    public static final int CONNECTED_BRIDGE_FINDING_DAYS = 10;
    public static final int CONNECTED_BRIDGE_MAX_DISPLAY = 20;
    public static final int CLAW_BRIDGE_MAX_DISPLAY = 8;
    public static final List<Integer> CLAW_BRIDGE_SEARCHING_DAYS = Arrays.asList(12, 14, 16, 18);
    public static final int TRIAD_SET_BRIDGE_MAX_DISPLAY = 25;
    public static final int TRIAD_SET_BRIDGE_SEARCHING_DAYS = 10;

    public static final String SHADOW_TOUCH_BRIDGE_NAME = "Cầu chạm bóng";
    public static final String MAPPING_BRIDGE_NAME = "Cầu ánh xạ";
    public static final String SHADOW_MAPPING_BRIDGE_NAME = "Cầu ánh xạ bóng";
    public static final String PERIOD_BRIDGE_NAME = "Cầu khoảng";
    public static final String CONNECTED_BRIDGE_NAME = "Cầu liên thông";
    public static final String NEGATIVE_SHADOW_BRIDGE_NAME = "Cầu chạm bóng -";
    public static final String POSITIVE_SHADOW_BRIDGE_NAME = "Cầu chạm bóng +";
    public static final String BIG_DOUBLE_SET_NAME = "Bộ kép to";
    public static final String DOUBLE_SET_NAME = "Bộ kép bằng";
    public static final String NEAR_DOUBLE_SET_NAME = "Bộ sát kép";

    public static final List<Integer> BIG_DOUBLE_SET = Arrays.asList
            (
                    03, 30,
                    05, 50,
                    07, 70,
                    14, 41,
                    16, 61,
                    27, 72,
                    29, 92,
                    35, 53,
                    36, 63,
                    37, 73,
                    38, 83,
                    46, 64,
                    49, 94,
                    57, 75,
                    58, 85,
                    79, 97
            );
    public static final List<Integer> NEAR_DOUBLE_SET = Arrays.asList
            (
                    01, 10,
                    12, 21,
                    23, 32,
                    34, 43,
                    45, 54,
                    56, 65,
                    67, 76,
                    78, 87,
                    89, 98
            );
    public static final List<Integer> DOUBLE_SET = Arrays.asList
            (
                    00,
                    11,
                    22,
                    33,
                    44,
                    55,
                    66,
                    77,
                    88,
                    99
            );

    public static final String TIME_FILE_NAME = "time.txt";
    public static final String LOTTERY_FILE_NAME = "lottery.txt";
    public static final String JACKPOT_YEARS_FILE_NAME = "jackpot_years.txt";
    public static final String JACKPOT_URL_FILE_NAME = "jackpot_url.txt";
    public static final String LOTTERY_URL_FILE_NAME = "lottery_url.txt";
    public static final String NOTE_FILE_NAME = "note.txt";
    public static final String TRIAD_FILE_NAME = "triad.txt";
    public static final String ITRIAD_FILE_NAME = "itriad.txt";
    public static final String TABLE_A_FILE_NAME = "table_a.txt";
    public static final String TABLE_B_FILE_NAME = "table_b.txt";
    public static final String ITABLE_A_FILE_NAME = "itable_a.txt";
    public static final String ITABLE_B_FILE_NAME = "itable_b.txt";
    public static final String TIME_URL = "https://lichvannien365.com/";
    public static final String JACKPOT_URL_AND_PARAMS = "http://ketqua8.net/bang-dac-biet-nam\nchu16";
    public static final String LOTTERY_URL_AND_PARAMS = "https://ketqua8.net/so-ket-qua\nwatermark";
}
