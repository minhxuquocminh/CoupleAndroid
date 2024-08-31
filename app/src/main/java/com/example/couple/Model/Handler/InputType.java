package com.example.couple.Model.Handler;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum InputType {
    SET(1001, "Bộ"),
    TOUCH(1002, "Chạm"),
    SUM(1003, "Tổng"),
    BRANCH(1004, "Chi"),
    HEAD(1005, "Đầu"),
    TAIL(1006, "Đuôi"),

    // other
    ADD(2001, "Thêm"),
    REMOVE(2002, "Bỏ"),
    COMBINE(2003, "Kết hợp"),
    ADD_TRIAD(2004, "Càng 3");

    public final int value;
    public final String name;
}
