package com.example.couple.ViewModel.Main.CreateNumberArray;

import android.content.Context;

import com.example.couple.Base.Handler.IOFileBase;
import com.example.couple.Base.Handler.NumberBase;
import com.example.couple.Custom.Const.FileName;
import com.example.couple.Custom.Handler.NumberArrayHandler;
import com.example.couple.Model.Handler.Input;
import com.example.couple.Model.Handler.InputType;
import com.example.couple.Model.Handler.Picker;
import com.example.couple.View.Main.CreateNumberArray.CreateNumberArrayView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

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
        String data = IOFileBase.readDataFromFile(context, FileName.TRIAD);
        String importantData = IOFileBase.readDataFromFile(context, FileName.IMP_TRIAD);

        if (data.isEmpty() && importantData.isEmpty()) {
            view.showTriadTable(new ArrayList<>());
        } else {
            String[] arr = data.trim().split(",");
            String[] importantArr = importantData.trim().split(",");
            List<Picker> pickers = new ArrayList<>();
            if (!data.isEmpty()) {
                for (String num : arr) {
                    int number = Integer.parseInt(num.trim());
                    pickers.add(new Picker(number, 1));
                }
            }
            if (!importantData.isEmpty()) {
                for (String imp : importantArr) {
                    int number = Integer.parseInt(imp.trim());
                    pickers.add(new Picker(number, 2));
                }
            }
            view.showTriadTable(pickers);
        }
    }

    public void saveDataToFile(List<Picker> pickers) {
        Collections.sort(pickers, new Comparator<Picker>() {
            @Override
            public int compare(Picker o1, Picker o2) {
                return Integer.compare(o1.getNumber(), o2.getNumber());
            }
        });
        StringBuilder data1 = new StringBuilder();
        StringBuilder data2 = new StringBuilder();
        for (int i = 0; i < pickers.size(); i++) {
            if (pickers.get(i).getLevel() == 1) {
                data1.append(pickers.get(i).getNumber()).append(",");
            } else {
                data2.append(pickers.get(i).getNumber()).append(",");
            }
        }
        IOFileBase.saveDataToFile(context, FileName.TRIAD, data1.toString(), 0);
        IOFileBase.saveDataToFile(context, FileName.IMP_TRIAD, data2.toString(), 0);
        view.saveDataSuccess("Lưu dữ liệu thành công!");
    }

    public void getTriadList() {
        String data = IOFileBase.readDataFromFile(context, FileName.TRIAD);
        String importantData = IOFileBase.readDataFromFile(context, FileName.IMP_TRIAD);
        if (data.isEmpty() && importantData.isEmpty()) {
            view.showTriadList(new ArrayList<>());
        } else {
            String[] arr = data.trim().split(",");
            String[] importantArr = importantData.trim().split(",");
            List<Picker> pickers = new ArrayList<>();
            if (!data.isEmpty()) {
                for (String num : arr) {
                    int number = Integer.parseInt(num.trim());
                    pickers.add(new Picker(number, 1));
                }
            }
            if (!importantData.isEmpty()) {
                for (String imp : importantArr) {
                    int number = Integer.parseInt(imp.trim());
                    pickers.add(new Picker(number, 2));
                }
            }
            Collections.sort(pickers, new Comparator<Picker>() {
                @Override
                public int compare(Picker o1, Picker o2) {
                    return Integer.compare(o1.getNumber(), o2.getNumber());
                }
            });
            view.showTriadList(pickers);
        }
    }

}
