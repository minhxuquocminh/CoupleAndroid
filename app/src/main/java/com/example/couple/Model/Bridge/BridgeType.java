package com.example.couple.Model.Bridge;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum BridgeType {

    /**
     * touch bridge
     */
    COMBINE_TOUCH(1001, "Chạm kết hợp"),
    SHADOW_TOUCH(1002, "Cầu chạm bóng"),
    LOTTO_TOUCH(1003, "Cầu chạm lô tô"),
    POSITIVE_SHADOW(1004, "Cầu chạm bóng +"),
    NEGATIVE_SHADOW(1005, "Cầu chạm bóng -"),

    /**
     * mapping bridge
     */
    MAPPING(2001, "Cầu ánh xạ"),
    RIGHT_MAPPING(2002, "Cầu ánh xạ P"),
    TRIAD_MAPPING(2003, "Cầu 2 ánh xạ"),

    /**
     * cycle bridge
     */
    COMPATIBLE_CYCLE(3001, "Cầu can chi hợp"),
    INCOMPATIBLE_CYCLE(3002, "Cầu can chi khắc"),
    BRANCH_IN_TWO_DAYS_BRIDGE(3003, "Chi trong 2 ngày"),

    /**
     * connected bridge
     */
    CONNECTED(4001, "Cầu liên thông"),
    CONNECTED_SET(4002, "Cầu liên bộ"),

    /**
     * special set
     */
    BIG_DOUBLE(5001, "Bộ kép to"),
    SAME_DOUBLE(5002, "Bộ kép bằng"),
    POSITIVE_DOUBLE(5003, "Bộ kép lệch"),

    /**
     * others bridge
     */
    ESTIMATED(9001, "Cầu ước lượng"),
    UNAPPEARED_BIG_DOUBLE(9002, "Kép chưa ra");

    public final int value;
    public final String name;

}
