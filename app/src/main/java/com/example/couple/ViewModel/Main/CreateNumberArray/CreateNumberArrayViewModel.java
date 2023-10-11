package com.example.couple.ViewModel.Main.CreateNumberArray;

import android.content.Context;

import com.example.couple.Base.Handler.IOFileBase;
import com.example.couple.Base.Handler.NumberBase;
import com.example.couple.Custom.Const.Const;
import com.example.couple.Custom.Const.FileName;
import com.example.couple.Custom.Handler.JackpotBridgeHandler;
import com.example.couple.Custom.Handler.JackpotHandler;
import com.example.couple.Custom.Handler.LotteryHandler;
import com.example.couple.Custom.Handler.NumberArrayHandler;
import com.example.couple.Model.Display.Number;
import com.example.couple.Model.Origin.Jackpot;
import com.example.couple.Model.Origin.Lottery;
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

    public void GetLotteryAndJackpotList() {
        List<Jackpot> jackpotList = JackpotHandler.GetReserveJackpotListFromFile(context, Const.DAY_OF_YEAR);
        List<Lottery> lotteryList = LotteryHandler.getLotteryListFromFile(context, Const.MAX_DAYS_TO_GET_LOTTERY);
        view.ShowLotteryAndJackpotList(jackpotList, lotteryList);
    }

    public void GetPeriodHistory(List<Jackpot> jackpotList) {
        List<PeriodHistory> periodHistoryList = JackpotBridgeHandler.GetPeriodHistoryList(jackpotList,
                0, 2, Const.AMPLITUDE_OF_PERIOD);
        if (periodHistoryList.isEmpty()) {
            view.ShowError("Không lấy được lịch sử các cách chạy.");
        } else {
            view.ShowPeriodHistory(periodHistoryList);
        }
    }

    public void CreateNumberArray(String set, String touch, String sum, String thirdClaw,
                                  String head, String tail, String combine, String add, String remove) {

        String error = "Vui lòng kiểm tra nhập tại";
        int countError = 0;

        // set

        List<Integer> setListIn = NumberBase.verifyNumberArray(set, 1);
        List<Integer> setListOut = new ArrayList<>();
        if (setListIn.size() == 0) {
            setListIn = new ArrayList<>();
            setListIn = NumberBase.verifyNumberArray(set, 2);
            if (setListIn.size() == 0) {
                if (!set.equals("")) {
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
        if (touchListIn.size() == 0) {
            if (!touch.equals("")) {
                countError++;
                error += " chạm;";
            }
        } else {
            touchListOut = NumberArrayHandler.getTouchs(touchListIn);
        }

        // sum

        List<Integer> sumListIn = NumberBase.verifyNumberArray(sum, 1);
        List<Integer> sumListOut = new ArrayList<>();
        if (sumListIn.size() == 0) {
            if (!sum.equals("")) {
                countError++;
                error += " tổng;";
            }
        } else {
            sumListOut = NumberArrayHandler.getSums(sumListIn);
        }

        // head

        List<Integer> headListIn = NumberBase.verifyNumberArray(head, 1);
        List<Integer> headListOut = new ArrayList<>();
        if (headListIn.size() == 0) {
            if (!head.equals("")) {
                countError++;
                error += " đầu;";
            }
        } else {
            headListOut = NumberArrayHandler.getHeads(headListIn);
        }

        // tail

        List<Integer> tailListIn = NumberBase.verifyNumberArray(tail, 1);
        List<Integer> tailListOut = new ArrayList<>();
        if (tailListIn.size() == 0) {
            if (!tail.equals("")) {
                countError++;
                error += " đuôi;";
            }
        } else {
            tailListOut = NumberArrayHandler.getTails(tailListIn);
        }

        // add

        List<Integer> addList = NumberBase.verifyNumberArray(add, 2);
        if (addList.size() == 0) {
            if (!add.equals("")) {
                countError++;
                error += " thêm;";
            }
        }

        // remove

        List<Integer> removeList = NumberBase.verifyNumberArray(remove, 2);
        if (removeList.size() == 0) {
            if (!remove.equals("")) {
                countError++;
                error += " bỏ;";
            }
        }

        // combine

        List<Integer> combineList = NumberBase.verifyNumberArray(combine, 2);
        if (combineList.size() == 0) {
            if (!combine.equals("")) {
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
        if (thirdClawListIn.size() == 0) {
            if (!thirdClaw.equals("")) {
                countError++;
                error += " càng 3;";
            }
            numbers = results;
            typeOfNumber = 2;
        } else {
            numbers = NumberArrayHandler.getThreeClaws(results, thirdClawListIn);
            typeOfNumber = 3;
        }
        view.ShowNumberArray(numbers, typeOfNumber);

        if (countError > 0) {
            view.ShowError(error);
        }
    }

    public void GetNumberArrayCounter(String array) {
        List<Integer> numbers = NumberBase.verifyNumberArray(array, 2);
        if (numbers.size() == 0) {
            numbers = NumberBase.verifyNumberArray(array, 3);
        }
        view.ShowNumberArrayCounter(numbers.size());
    }

    public void VerifyCoupleArray(String numberArray) {
        List<Integer> numbers = NumberBase.verifyNumberArray(numberArray, 2);
        if (numbers.size() == 0) {
            view.ShowError("Chuỗi không hợp lệ!");
        } else {
            view.VerifyCoupleArraySuccess(numberArray);
        }
    }

    public void VerifyTriadArray(String numberArray) {
        List<Number> numbers = NumberBase.verifyNumberArr(numberArray, 3);
        if (numbers.size() == 0) {
            view.ShowError("Chuỗi không hợp lệ!");
        } else {
            view.VerifyTriadArraySuccess(numbers);
        }
    }

    public void VerifyString(String numberArray) {
        int typeOfNumber = 2;
        List<Integer> numbers = NumberBase.verifyNumberArray(numberArray, 2);
        if (numbers.size() == 0) {
            numbers = NumberBase.verifyNumberArray(numberArray, 3);
            typeOfNumber = 3;
        }
        if (numbers.size() == 0) {
            view.ShowError("Chuỗi không hợp lệ!");
        } else {
            view.ShowVerifyStringSuccess(numbers, typeOfNumber);
        }
    }

    public void GetSubJackpotList(int numberOfDays) {
        List<Jackpot> jackpotList = JackpotHandler.GetReserveJackpotListFromFile(context, numberOfDays);
        view.ShowSubJackpotList(jackpotList);
    }

    public void GetTriadTable() {
        String data = IOFileBase.readDataFromFile(context, FileName.TRIAD);
        String importantData = IOFileBase.readDataFromFile(context, FileName.ITRIAD);

        if (data.equals("") && importantData.equals("")) {
            view.ShowTriadTable(new ArrayList<>());
        } else {
            String[] arr = data.trim().split(",");
            String[] importantArr = importantData.trim().split(",");
            List<Number> numbers = new ArrayList<>();
            if (!data.equals("")) {
                for (int i = 0; i < arr.length; i++) {
                    int number = Integer.parseInt(arr[i].trim());
                    numbers.add(new Number(number, 1));
                }
            }
            if (!importantData.equals("")) {
                for (int i = 0; i < importantArr.length; i++) {
                    int number = Integer.parseInt(importantArr[i].trim());
                    numbers.add(new Number(number, 2));
                }
            }
            view.ShowTriadTable(numbers);
        }
    }

    public void SaveDataToFile(List<Number> numbers) {
        Collections.sort(numbers, new Comparator<Number>() {
            @Override
            public int compare(Number o1, Number o2) {
                if (o1.getNumber() > o2.getNumber()) {
                    return 1;
                } else {
                    return -1;
                }
            }
        });
        String data1 = "";
        String data2 = "";
        for (int i = 0; i < numbers.size(); i++) {
            if (numbers.get(i).getLevel() == 1) {
                data1 += numbers.get(i).getNumber() + ",";
            } else {
                data2 += numbers.get(i).getNumber() + ",";
            }
        }
        IOFileBase.saveDataToFile(context, FileName.TRIAD, data1, 0);
        IOFileBase.saveDataToFile(context, FileName.ITRIAD, data2, 0);
        view.SaveDataSuccess("Lưu dữ liệu thành công!");
    }

    public void GetTriadList() {
        String data = IOFileBase.readDataFromFile(context, FileName.TRIAD);
        String importantData = IOFileBase.readDataFromFile(context, FileName.ITRIAD);
        if (data.equals("") && importantData.equals("")) {
            view.ShowTriadList(new ArrayList<>());
        } else {
            String[] arr = data.trim().split(",");
            String[] importantArr = importantData.trim().split(",");
            List<Number> numbers = new ArrayList<>();
            if (!data.equals("")) {
                for (int i = 0; i < arr.length; i++) {
                    int number = Integer.parseInt(arr[i].trim());
                    numbers.add(new Number(number, 1));
                }
            }
            if (!importantData.equals("")) {
                for (int i = 0; i < importantArr.length; i++) {
                    int number = Integer.parseInt(importantArr[i].trim());
                    numbers.add(new Number(number, 2));
                }
            }
            Collections.sort(numbers, new Comparator<Number>() {
                @Override
                public int compare(Number o1, Number o2) {
                    if (o1.getNumber() > o2.getNumber()) {
                        return 1;
                    } else {
                        return -1;
                    }
                }
            });
            view.ShowTriadList(numbers);
        }
    }

}
