package com.example.couple.Custom.Const;

import java.util.Arrays;
import java.util.List;

public class Const {

    /**
     * value
     */
    public static final int EMPTY_VALUE = -999;
    public static final int NUMBER_OF_PRIZES = 27;
    public static final int MAX_LENGTH_OF_PRIZE = 5;
    public static final int AMPLITUDE_OF_PERIOD = 13;
    public static final int MAX_ROW_COUNT_TABLE = 100;
    public static final int MAX_DAY_NUMBER_BEFORE = 999;
    public static final int MAX_DAYS_TO_GET_LOTTERY = 60;
    public static final int CONNECTED_BRIDGE_FINDING_DAYS = 10;
    public static final int CONNECTED_BRIDGE_MAX_DISPLAY = 20;
    public static final int TRIAD_SET_BRIDGE_FINDING_DAYS = 10;
    public static final int TRIAD_SET_BRIDGE_MAX_DISPLAY = 25;
    public static final int CLAW_BRIDGE_MAX_DISPLAY = 8;
    public static final int TIME_OUT = 3000;

    /**
     * text
     */
    public static final String EMPTY = "EMPTY";
    public static final String EMPTY_JACKPOT = "-99999";
    public static final String HEAD = "Đầu";
    public static final String TAIL = "Đuôi";
    public static final String SET = "Bộ";
    public static final String SUM = "Tổng";
    public static final String DOUBLE = "Kép bằng";
    public static final String BRANCH_IN_DAY_ACRONYM = "CTN";

    /**
     * array
     */
    public static final List<Integer> SMALL_SETS =
            Arrays.asList(00, 01, 02, 03, 04, 11, 12, 13, 14, 22, 23, 24, 33, 34, 44);
    public static final List<Integer> SMALL_SETS_NOT_DOUBLE =
            Arrays.asList(01, 02, 03, 04, 12, 13, 14, 23, 24, 34);

    /**
     * url and params
     */
    public static final String GOOGLE_URL = "www.google.com";
    public static final String TIME_URL = "https://lichvannien365.com/";
    public static final String JACKPOT_URL_AND_PARAMS = "https://mketqua1.net/bang-dac-biet-nam\nchu16";
    public static final String LOTTERY_URL_AND_PARAMS = "https://mketqua1.net/so-ket-qua\nwatermark";

    /**
     * paragraph
     */
    public static final String BRIDGE_ANNOTATION =
            " * Ghi chú: " +
                    "    - Đối với cầu khoảng: nên chơi khi số lượng KQ tổ hợp lớn hơn hoặc bằng 55, " +
                    "nếu số lượng nhỏ hơn 55 thì cần xem lại thông tin chi tiết của cầu.\n" +
                    "    - Đối với cầu ánh xạ: nếu hôm trước có đầu 0 thì hôm sau dễ xịt, " +
                    "khi tạo hình của 2 hôm trước tạo nước đi khó thì cũng dễ bị lọt. " +
                    "Cần xem kĩ các thế đi của đề để có thể tạo ra tỉ lệ trúng cao. " +
                    "Số lượng tổ hợp hay xịt là { 72, 79 }.\n" +
                    "    - Đối với cầu ánh xạ bóng: nếu 1-2 hôm trước ra kép thì chơi ít lại, " +
                    "gặp số lượng tổ hợp là { 57, 52, 51 } thì hay bị xịt. " +
                    "Tỉ lệ trúng cao khi SLTH lớn hơn 70 rồi đến 60, " +
                    "các trường hợp nhỏ hơn vẫn có thể trúng nhưng xác suất khá thấp.\n";

}
