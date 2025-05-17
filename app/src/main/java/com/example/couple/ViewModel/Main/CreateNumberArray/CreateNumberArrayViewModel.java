package com.example.couple.ViewModel.Main.CreateNumberArray;

import android.content.Context;

import com.example.couple.Base.Handler.NumberBase;
import com.example.couple.Base.Handler.StorageBase;
import com.example.couple.Custom.Enum.StorageType;
import com.example.couple.Custom.Handler.NumberArrayHandler;
import com.example.couple.Model.Handler.Input;
import com.example.couple.Model.Handler.InputType;
import com.example.couple.View.Main.CreateNumberArray.CreateNumberArrayView;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class CreateNumberArrayViewModel {
    CreateNumberArrayView view;
    Context context;

    public CreateNumberArrayViewModel(CreateNumberArrayView view, Context context) {
        this.view = view;
        this.context = context;
    }

    public void createNumberArray(List<Input> inputs) {
        List<Integer> matchs = new ArrayList<>();
        List<Integer> adds = new ArrayList<>();
        List<Integer> removes = new ArrayList<>();
        List<Integer> combines = new ArrayList<>();
        List<Integer> triads = new ArrayList<>();
        String errorMessage = "";
        for (Input input : inputs) {
            if (input.isError()) {
                errorMessage += " " + input.getInputType().name + ";";
            }
            if (input.getInputType() == InputType.COMBINE) {
                combines.addAll(input.getNumbers());
                continue;
            }
            if (input.getInputType() == InputType.ADD) {
                adds.addAll(input.getNumbers());
                continue;
            }
            if (input.getInputType() == InputType.REMOVE) {
                removes.addAll(input.getNumbers());
                continue;
            }
            if (input.getInputType() == InputType.ADD_TRIAD) {
                triads.addAll(input.getNumbers());
                continue;
            }
            matchs = NumberBase.getMatchNumbers(matchs, input.getNumbers());
        }
        matchs = NumberBase.getMatchNumbers(matchs, combines);
        matchs.addAll(adds);
        matchs.removeIf(removes::contains);
        List<Integer> results = matchs.stream().distinct().sorted().collect(Collectors.toList());

        int typeOfNumber = 2;
        if (!triads.isEmpty()) {
            results = NumberArrayHandler.getThreeClaws(results, triads);
            typeOfNumber = 3;
        }

        view.showNumberArray(results, typeOfNumber);

        if (!errorMessage.isEmpty()) {
            view.showMessage("Vui lòng kiểm tra nhập tại" + errorMessage);
        }
    }

    public void getNumberArrayCounter(String array) {
        List<Integer> numbers = NumberBase.verifyNumberArray(array, 2);
        if (numbers.isEmpty()) {
            numbers = NumberBase.verifyNumberArray(array, 3);
        }
        view.showNumberArrayCounter(numbers.size());
    }

    public void verifyCoupleArray(String numberArray) {
        List<Integer> pickers = NumberBase.verifyNumberArray(numberArray, 2);
        if (pickers.isEmpty()) {
            view.showMessage("Chuỗi không hợp lệ!");
        } else {
            view.verifyCoupleArraySuccess(pickers);
        }
    }

    public void verifyTriadArray(String numberArray) {
        List<Integer> pickers = NumberBase.verifyNumberArray(numberArray, 3);
        if (pickers.isEmpty()) {
            view.showMessage("Chuỗi không hợp lệ!");
        } else {
            view.verifyTriadArraySuccess(pickers);
        }
    }

    public void verifyString(String numberArray) {
        int typeOfNumber = 2;
        List<Integer> numbers = NumberBase.verifyNumberArray(numberArray, 2);
        if (numbers.isEmpty()) {
            numbers = NumberBase.verifyNumberArray(numberArray, 3);
            typeOfNumber = 3;
        }
        if (numbers.isEmpty()) {
            view.showMessage("Chuỗi không hợp lệ!");
        } else {
            view.showVerifyStringSuccess(numbers, typeOfNumber);
        }
    }

    public void getTriadTable() {
        List<Integer> normalNumbers = StorageBase.getNumberList(context, StorageType.LIST_OF_TRIAD);
        List<Integer> importantNumbers = StorageBase.getNumberList(context, StorageType.LIST_OF_IMP_TRIAD);
        view.showTriadTable(normalNumbers, importantNumbers);
    }

    public void saveNumbers(List<Integer> normalNumbers, List<Integer> importantNumbers) {
        StorageBase.setNumberList(context, StorageType.LIST_OF_TRIAD, normalNumbers);
        StorageBase.setNumberList(context, StorageType.LIST_OF_IMP_TRIAD, importantNumbers);
        view.saveDataSuccess("Lưu dữ liệu thành công!");
    }

    public void getTriadList() {
        List<Integer> normalNumbers = StorageBase.getNumberList(context, StorageType.LIST_OF_TRIAD);
        List<Integer> importantNumbers = StorageBase.getNumberList(context, StorageType.LIST_OF_IMP_TRIAD);
        List<Integer> numbers = Stream.concat(normalNumbers.stream(), importantNumbers.stream()).sorted().collect(Collectors.toList());
        view.showTriadList(numbers);
    }
}
