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
        List<Integer> numbers = new ArrayList<>();
        switch (this.inputType) {
            case SET:
                numbers = NumberArrayHandler.getSetsByCouples(inputNumbers);
                break;
            case TOUCH:
                numbers = NumberArrayHandler.getTouchs(inputNumbers);
                break;
            case SUM:
                numbers = NumberArrayHandler.getSums(inputNumbers);
                break;
            case BRANCH:
                numbers = NumberArrayHandler.getBranches(inputNumbers);
                break;
            case HEAD:
                numbers = NumberArrayHandler.getHeads(inputNumbers);
                break;
            case TAIL:
                numbers = NumberArrayHandler.getTails(inputNumbers);
                break;
            default:
                numbers = inputNumbers;
                break;
        }
        return numbers;
    }

    public boolean isEmpty() {
        return data.trim().isEmpty() || inputNumbers.isEmpty();
    }

    public boolean isError() {
        return !data.trim().isEmpty() && inputNumbers.isEmpty();
    }
}
