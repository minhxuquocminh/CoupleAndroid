package com.example.couple.ViewModel.Main.CreateNumberArray;

import android.content.Context;

import com.example.couple.Base.Handler.IOFileBase;
import com.example.couple.Base.Handler.NumberBase;
import com.example.couple.Custom.Const.Const;
import com.example.couple.Custom.Const.FileName;
import com.example.couple.Custom.Handler.Bridge.EstimatedBridgeHandler;
import com.example.couple.Custom.Handler.NumberArrayHandler;
import com.example.couple.Model.Display.Picker;
import com.example.couple.Model.Origin.Jackpot;
import com.example.couple.Model.Support.PeriodHistory;
import com.example.couple.View.Main.CreateNumberArray.CreateNumberArrayView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class CreateNumberArrayViewModel {
    CreateNumberArrayView view;
    Context context;

    public CreateNumberArrayViewModel(CreateNumberArrayView view, Context context) {
        this.view = view;
        this.context = context;
    }

    public void getPeriodHistory(List<Jackpot> jackpotList) {
        List<PeriodHistory> periodHistoryList =
                EstimatedBridgeHandler.getEstimatedHistoryList(jackpotList,
                        0, 2, Const.AMPLITUDE_OF_PERIOD);
        if (periodHistoryList.isEmpty()) {
            view.showMessage("Không lấy được lịch sử các cách chạy.");
        } else {
            view.showPeriodHistory(periodHistoryList);
        }
    }

    public void createNumberArray(String set, String touch, String sum, String thirdClaw,
                                  String head, String tail, String combine, String add, String remove) {

        String error = "Vui lòng kiểm tra nhập tại";
        int countError = 0;

        // set

        List<Integer> setListIn = NumberBase.verifyNumberArray(set, 1);
        List<Integer> setListOut = new ArrayList<>();
        if (setListIn.isEmpty()) {
            setListIn = NumberBase.verifyNumberArray(set, 2);
            if (setListIn.isEmpty()) {
                if (!set.isEmpty()) {
                    countError++;
                    error += " bộ;";
                }
            } else {
                setListOut = NumberArrayHandler.getSetsByCouples(setListIn);
            }
        } else {
            setListOut = NumberArrayHandler.getSetsBySingles(setListIn);
        }

        // touch

        List<Integer> touchListIn = NumberBase.verifyNumberArray(touch, 1);
        List<Integer> touchListOut = new ArrayList<>();
        if (touchListIn.isEmpty()) {
            if (!touch.isEmpty()) {
                countError++;
                error += " chạm;";
            }
        } else {
            touchListOut = NumberArrayHandler.getTouchs(touchListIn);
        }

        // sum

        List<Integer> sumListIn = NumberBase.verifyNumberArray(sum, 1);
        List<Integer> sumListOut = new ArrayList<>();
        if (sumListIn.isEmpty()) {
            if (!sum.isEmpty()) {
                countError++;
                error += " tổng;";
            }
        } else {
            sumListOut = NumberArrayHandler.getSums(sumListIn);
        }

        // head

        List<Integer> headListIn = NumberBase.verifyNumberArray(head, 1);
        List<Integer> headListOut = new ArrayList<>();
        if (headListIn.isEmpty()) {
            if (!head.isEmpty()) {
                countError++;
                error += " đầu;";
            }
        } else {
            headListOut = NumberArrayHandler.getHeads(headListIn);
        }

        // tail

        List<Integer> tailListIn = NumberBase.verifyNumberArray(tail, 1);
        List<Integer> tailListOut = new ArrayList<>();
        if (tailListIn.isEmpty()) {
            if (!tail.isEmpty()) {
                countError++;
                error += " đuôi;";
            }
        } else {
            tailListOut = NumberArrayHandler.getTails(tailListIn);
        }

        // add

        List<Integer> addList = NumberBase.verifyNumberArray(add, 2);
        if (addList.isEmpty()) {
            if (!add.isEmpty()) {
                countError++;
                error += " thêm;";
            }
        }

        // remove

        List<Integer> removeList = NumberBase.verifyNumberArray(remove, 2);
        if (removeList.isEmpty()) {
            if (!remove.isEmpty()) {
                countError++;
                error += " bỏ;";
            }
        }

        // combine

        List<Integer> combineList = NumberBase.verifyNumberArray(combine, 2);
        if (combineList.isEmpty()) {
            if (!combine.isEmpty()) {
                countError++;
                error += " kết hợp;";
            }
        }

        // match set, touch, sum

        List<Integer> firstMatch = NumberBase.getMatchNumbers(setListOut, touchListOut, sumListOut);

        // match head, tail
        List<Integer> secondMatch = NumberBase.getMatchNumbers(firstMatch, headListOut, tailListOut);

        // add

        secondMatch.addAll(addList);

        // remove

        for (int i = 0; i < secondMatch.size(); i++) {
            if (removeList.contains(secondMatch.get(i))) {
                secondMatch.remove(i);
                i--;
            }
        }

        // match combine

        List<Integer> combineNumbers = NumberBase.getMatchNumbers(combineList, secondMatch);

        // loại bỏ trùng lặp

        List<Integer> results = new ArrayList<>();
        for (int number : combineNumbers) {
            if (!results.contains(number)) {
                results.add(number);
            }
        }

        // sắp xếp

        Collections.sort(results);

        // thêm càng thứ 3

        List<Integer> thirdClawListIn = NumberBase.verifyNumberArray(thirdClaw, 1);
        List<Integer> numbers = new ArrayList<>();
        int typeOfNumber = 0;
        if (thirdClawListIn.isEmpty()) {
            if (!thirdClaw.isEmpty()) {
                countError++;
                error += " càng 3;";
            }
            numbers = results;
            typeOfNumber = 2;
        } else {
            numbers = NumberArrayHandler.getThreeClaws(results, thirdClawListIn);
            typeOfNumber = 3;
        }
        view.showNumberArray(numbers, typeOfNumber);

        if (countError > 0) {
            view.showMessage(error);
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
        List<Picker> pickers = NumberBase.verifyNumberArr(numberArray, 2);
        if (pickers.isEmpty()) {
            view.showMessage("Chuỗi không hợp lệ!");
        } else {
            view.verifyCoupleArraySuccess(pickers);
        }
    }

    public void verifyTriadArray(String numberArray) {
        List<Picker> pickers = NumberBase.verifyNumberArr(numberArray, 3);
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
        String importantData = IOFileBase.readDataFromFile(context, FileName.ITRIAD);

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
        IOFileBase.saveDataToFile(context, FileName.ITRIAD, data2.toString(), 0);
        view.saveDataSuccess("Lưu dữ liệu thành công!");
    }

    public void getTriadList() {
        String data = IOFileBase.readDataFromFile(context, FileName.TRIAD);
        String importantData = IOFileBase.readDataFromFile(context, FileName.ITRIAD);
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
