package com.example.couple.Model.Handler;

import com.example.couple.Custom.Handler.NumberArrayHandler;
import com.example.couple.Model.Bridge.BridgeType;

import java.util.List;

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

    public List<Integer> getNumbers(List<Integer> inputNumbers) {
        switch (this) {
            case SET:
                return NumberArrayHandler.getSetsByCouples(inputNumbers);
            case TOUCH:
                return NumberArrayHandler.getTouchs(inputNumbers);
            case SUM:
                return NumberArrayHandler.getSums(inputNumbers);
            case BRANCH:
                return NumberArrayHandler.getBranches(inputNumbers);
            case HEAD:
                return NumberArrayHandler.getHeads(inputNumbers);
            case TAIL:
                return NumberArrayHandler.getTails(inputNumbers);
            default:
                return inputNumbers;
        }
    }

    public BridgeType toBridgeType() {
        switch (this) {
            case SET:
                return BridgeType.INPUT_SET;
            case TOUCH:
                return BridgeType.INPUT_TOUCH;
            case SUM:
                return BridgeType.INPUT_SUM;
            case BRANCH:
                return BridgeType.INPUT_BRANCH;
            case HEAD:
                return BridgeType.INPUT_HEAD;
            case TAIL:
                return BridgeType.INPUT_TAIL;
            case COMBINE:
                return BridgeType.INPUT_COMBINE;
            default:
                return null;
        }
    }
}
