package com.example.couple.Model.Handler;

import com.example.couple.Base.Handler.NumberBase;

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

    public boolean isEmpty() {
        return data.trim().isEmpty() || inputNumbers.isEmpty();
    }

    public boolean isError() {
        return !data.trim().isEmpty() && inputNumbers.isEmpty();
    }

    public boolean isValid() {
        return !data.trim().isEmpty() && !inputNumbers.isEmpty();
    }

    public List<Integer> getNumbers() {
        if (isError()) return new ArrayList<>();
        return inputType.getNumbers(inputNumbers);
    }

}
