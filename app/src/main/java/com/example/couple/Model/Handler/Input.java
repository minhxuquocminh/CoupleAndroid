package com.example.couple.Model.Handler;

import com.example.couple.Base.Handler.NumberBase;
import com.example.couple.Custom.Handler.NumberArrayHandler;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;

@Getter
public class Input {
    InputType inputType;
    String data;
    int numberLength;
    List<Integer> inputNumbers;

    public Input(InputType inputType, String data, int numberLength) {
        this.inputType = inputType;
        this.data = data;
        this.numberLength = numberLength;
        this.inputNumbers = new ArrayList<>();
        List<Integer> nums = NumberBase.verifyNumberArray(data, numberLength);
        if (!nums.isEmpty()) {
            this.inputNumbers.addAll(nums);
        }
    }

    public List<Integer> getNumbers() {
        switch (this.inputType) {
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

    public boolean isEmpty() {
        return data.trim().isEmpty() || inputNumbers.isEmpty();
    }

    public boolean isError() {
        return !data.trim().isEmpty() && inputNumbers.isEmpty();
    }
}
