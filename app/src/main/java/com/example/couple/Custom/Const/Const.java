package com.example.couple.Custom.Const;

import java.util.Arrays;
import java.util.List;

public class Const {

    /**
     * set value
     */
    public static final int EMPTY_VALUE = -999;
    public static final int NUMBER_OF_PRIZES = 27;
    public static final int MAX_LENGTH_OF_PRIZE = 5;
    public static final int AMPLITUDE_OF_PERIOD = 13;
    public static final int MAX_ROW_COUNT_TABLE = 100;
    public static final int MAX_DAY_NUMBER_BEFORE = 999;
    public static final int MAX_DAYS_TO_GET_LOTTERY = 60;
    public static final int BRANCH_IN_DAY_BRIDGE_FINDING_DAYS = 60;
    public static final int CONNECTED_BRIDGE_FINDING_DAYS = 10;
    public static final int CONNECTED_BRIDGE_MAX_DISPLAY = 20;
    public static final int TRIAD_SET_BRIDGE_FINDING_DAYS = 10;
    public static final int TRIAD_SET_BRIDGE_MAX_DISPLAY = 25;
    public static final List<Integer> CLAW_BRIDGE_FINDING_DAYS = Arrays.asList(12, 14, 16, 18);
    public static final int CLAW_BRIDGE_MAX_DISPLAY = 8;
    public static final int MAPPING_ALL = 999;

    /**
     * set text
     */
    public static final String SHADOW_TOUCH_BRIDGE_NAME = "Cầu chạm bóng";
    public static final String LOTTO_TOUCH_BRIDGE_NAME = "Cầu chạm lô tô";
    public static final String MAPPING_BRIDGE_NAME = "Cầu ánh xạ";
    public static final String MAPPING_BRIDGE_NAME_1 = "Cầu ánh xạ 1";
    public static final String RIGHT_MAPPING_BRIDGE_NAME = "Cầu ánh xạ P";
    public static final String COMPACT_MAPPING_BRIDGE_NAME = "Cầu ánh xạ P1";
    public static final String TRIAD_MAPPING_BRIDGE_NAME = "Cầu 2 ánh xạ";
    public static final String MATCH_MAPPING_BRIDGE_NAME = "Cầu ánh xạ hợp";
    public static final String COMPATIBLE_CYCLE_BRIDGE_NAME = "Cầu can chi hợp";
    public static final String INCOMPATIBLE_CYCLE_BRIDGE_NAME = "Cầu can chi khắc";
    public static final String SHADOW_MAPPING_BRIDGE_NAME = "Cầu ánh xạ bóng";
    public static final String SHADOW_EXCHANGE_BRIDGE_NAME = "Cầu đổi bóng";
    public static final String ESTIMATED_BRIDGE_NAME = "Cầu ước lượng";
    public static final String CONNECTED_BRIDGE_NAME = "Cầu liên thông";
    public static final String CONNECTED_SET_BRIDGE_NAME = "Cầu liên bộ";
    public static final String NEGATIVE_SHADOW_BRIDGE_NAME = "Cầu chạm bóng -";
    public static final String POSITIVE_SHADOW_BRIDGE_NAME = "Cầu chạm bóng +";
    public static final String COMBINE_TOUCH_BRIDGE_NAME = "Chạm kết hợp";
    public static final String BIG_DOUBLE_SET_NAME = "Bộ kép to";
    public static final String DOUBLE_SET_NAME = "Bộ kép bằng";
    public static final String POSITIVE_DOUBLE_SET_NAME = "Bộ kép dương";
    public static final String HEAD = "Đầu";
    public static final String TAIL = "Đuôi";
    public static final String SET = "Bộ";
    public static final String SUM = "Tổng";
    public static final String DOUBLE = "Kép bằng";
    public static final String NEAR_DOUBLE_INCREASE = "Sát kép tăng";
    public static final String NEAR_DOUBLE_DECREASE = "Sát kép giảm";

    /**
     * set array
     */
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
    public static final List<Integer> NEAR_DOUBLE_INCREASE_SET = Arrays.asList
            (
                    01,
                    12,
                    23,
                    34,
                    45,
                    56,
                    67,
                    78,
                    89
            );
    public static final List<Integer> NEAR_DOUBLE_DECREASE_SET = Arrays.asList
            (
                    10,
                    21,
                    32,
                    43,
                    54,
                    65,
                    76,
                    87,
                    98
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
    public static final List<Integer> POSITIVE_DOUBLE_SET = Arrays.asList
            (
                    05, 50,
                    16, 61,
                    27, 72,
                    38, 83,
                    49, 94
            );
    public static final List<Integer> SMALL_SETS =
            Arrays.asList(00, 01, 02, 03, 04, 11, 12, 13, 14, 22, 23, 24, 33, 34, 44);
    public static final List<Integer> SMALL_SETS_NOT_DOUBLE =
            Arrays.asList(01, 02, 03, 04, 12, 13, 14, 23, 24, 34);

    /**
     * set url and params
     */
    public static final String TIME_URL = "https://lichvannien365.com/";
    public static final String JACKPOT_URL_AND_PARAMS = "http://ketqua9.net/bang-dac-biet-nam\nchu16";
    public static final String LOTTERY_URL_AND_PARAMS = "https://ketqua9.net/so-ket-qua\nwatermark";

    /**
     * set paragraph
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
