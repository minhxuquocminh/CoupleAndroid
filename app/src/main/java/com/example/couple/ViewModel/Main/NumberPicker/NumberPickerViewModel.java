package com.example.couple.ViewModel.Main.NumberPicker;

import android.content.Context;

import com.example.couple.Model.Time.DateBase;
import com.example.couple.Base.Handler.IOFileBase;
import com.example.couple.Custom.Const.Const;
import com.example.couple.Custom.Const.TimeInfo;
import com.example.couple.Custom.Handler.JackpotHandler;
import com.example.couple.Custom.Statistics.JackpotStatistics;
import com.example.couple.Model.Display.JackpotNextDay;
import com.example.couple.Model.Display.Number;
import com.example.couple.Model.Origin.Couple;
import com.example.couple.Model.Origin.Jackpot;
import com.example.couple.View.Main.NumberPicker.NumberPickerView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class NumberPickerViewModel {
    NumberPickerView numberPickerView;
    Context context;

    public NumberPickerViewModel(NumberPickerView numberPickerView, Context context) {
        this.numberPickerView = numberPickerView;
        this.context = context;
    }

    public void GetSubCoupleList(int length) {
        List<Jackpot> jackpotList = JackpotHandler.GetReserveJackpotListFromFile(context, length);
        int size = jackpotList.size() < length ? jackpotList.size() : length;
        List<Couple> subCoupleList = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            subCoupleList.add(jackpotList.get(i).getCouple());
        }
        Collections.reverse(subCoupleList);
        if (subCoupleList.size() > 0) {
            numberPickerView.ShowSubCoupleList(subCoupleList);
        }

    }

    public void GetSubCoupleNextDay(int digit2D, int length) {
        int[] startEndYearFile = JackpotStatistics.GetStartAndEndYearFile(context);
        if (startEndYearFile != null) {
            int startYearFile = startEndYearFile[0];
            int endYearFile = startEndYearFile[1];
            if (endYearFile == TimeInfo.CURRENT_YEAR) {
                int numberOfYearsFile = endYearFile - startYearFile + 1;
                int numberOfYears = numberOfYearsFile < 3 ? numberOfYearsFile : 3;
                List<Jackpot> jackpotList = JackpotHandler.GetJackpotListManyYears(context, numberOfYears);
                List<JackpotNextDay> jackpotNextDayList = JackpotStatistics.GetJackpotNextDayList(jackpotList, digit2D);
                int count = 0;
                List<Couple> subCoupleList = new ArrayList<>();
                for (int i = 0; i < jackpotNextDayList.size(); i++) {
                    count++;
                    subCoupleList.add(jackpotNextDayList.get(i).getJackpotSecond().getCouple());
                    if (count == length) break;
                }
                Collections.reverse(subCoupleList);
                if (subCoupleList.size() > 0)
                    numberPickerView.ShowSubCoupleNextDay(subCoupleList);
            }
        }
    }

    public void GetSubCoupleLastMonth(DateBase dateBase) {
        DateBase nextDateBase = dateBase.plusDays(1);
        int month = nextDateBase.getMonth() - 1;
        int year = nextDateBase.getYear();
        int numberOfYears = 1;
        if (month == 0) {
            month = Const.MONTH_OF_YEAR;
            year--;
            numberOfYears = 2;
        }
        List<Jackpot> jackpotList = JackpotHandler.GetJackpotListManyYears(context, numberOfYears);
        List<Jackpot> monthJackpotList = JackpotStatistics.GetMonthJackpotList(jackpotList, month, year);
        List<Couple> subCoupleList = new ArrayList<>();
        for (int i = 0; i < monthJackpotList.size(); i++) {
            DateBase date = monthJackpotList.get(i).getDateBase();
            if (date.getDay() == nextDateBase.getDay()) {
                DateBase toptopDate = new DateBase(date.getDay() - 2, month, year);
                DateBase topDate = new DateBase(date.getDay() - 1, month, year);
                DateBase centerDate = new DateBase(date.getDay(), month, year);
                DateBase bottomDate = new DateBase(date.getDay() + 1, month, year);
                DateBase bottombottomDate = new DateBase(date.getDay() + 2, month, year);
                if (toptopDate.isValid()) {
                    subCoupleList.add(monthJackpotList.get(i - 2).getCouple());
                }
                if (topDate.isValid()) {
                    subCoupleList.add(monthJackpotList.get(i - 1).getCouple());
                }
                if (centerDate.isValid()) {
                    subCoupleList.add(monthJackpotList.get(i).getCouple());
                }
                if (bottomDate.isValid()) {
                    subCoupleList.add(monthJackpotList.get(i + 1).getCouple());
                }
                if (bottombottomDate.isValid()) {
                    subCoupleList.add(monthJackpotList.get(i + 2).getCouple());
                }
            }
        }
        if (subCoupleList.size() > 0) numberPickerView.ShowSubCoupleLastMonth(subCoupleList);
    }

    public void GetTableType1(boolean isTableA) {
        String data = "";
        String importantData = "";
        if (isTableA) {
            data = IOFileBase.readDataFromFile(context, Const.TABLE_A_FILE_NAME);
            importantData = IOFileBase.readDataFromFile(context, Const.ITABLE_A_FILE_NAME);
        } else {
            data = IOFileBase.readDataFromFile(context, Const.TABLE_B_FILE_NAME);
            importantData = IOFileBase.readDataFromFile(context, Const.ITABLE_B_FILE_NAME);
        }
        if (data.equals("") && importantData.equals("")) {
            numberPickerView.ShowTableType1(new ArrayList<>());
        } else {
            String arr[] = data.trim().split(",");
            String importantArr[] = importantData.trim().split(",");
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
            numberPickerView.ShowTableType1(numbers);
        }
    }

    public void GetTableType2(boolean isTableA) {
        String data = "";
        String importantData = "";
        if (isTableA) {
            data = IOFileBase.readDataFromFile(context, Const.TABLE_A_FILE_NAME);
            importantData = IOFileBase.readDataFromFile(context, Const.ITABLE_A_FILE_NAME);
        } else {
            data = IOFileBase.readDataFromFile(context, Const.TABLE_B_FILE_NAME);
            importantData = IOFileBase.readDataFromFile(context, Const.ITABLE_B_FILE_NAME);
        }
        if (data.equals("") && importantData.equals("")) {
            numberPickerView.ShowTableType2(new ArrayList<>());
        } else {
            String arr[] = data.trim().split(",");
            String importantArr[] = importantData.trim().split(",");
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
            numberPickerView.ShowTableType2(numbers);
        }
    }

    public void SaveDataToFile(List<Number> numbers, boolean isTableA) {
        String data1 = "";
        String data2 = "";
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
        for (int i = 0; i < numbers.size(); i++) {
            if (numbers.get(i).getLevel() == 1) {
                data1 += numbers.get(i).getNumber() + ",";
            } else {
                data2 += numbers.get(i).getNumber() + ",";
            }
        }
        String fileName = isTableA ? Const.TABLE_A_FILE_NAME : Const.TABLE_B_FILE_NAME;
        IOFileBase.saveDataToFile(context, fileName, data1, 0);
        IOFileBase.saveDataToFile(context, "i" + fileName, data2, 0);
        numberPickerView.SaveDataSuccess("Lưu dữ liệu thành công!");
    }

    public void GetTableAList() {
        String data = IOFileBase.readDataFromFile(context, Const.TABLE_A_FILE_NAME);
        String importantData = IOFileBase.readDataFromFile(context, Const.ITABLE_A_FILE_NAME);
        if (data.equals("") && importantData.equals("")) {
            numberPickerView.ShowTableAList(new ArrayList<>());
        } else {
            String arr[] = data.trim().split(",");
            String importantArr[] = importantData.trim().split(",");
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
            numberPickerView.ShowTableAList(numbers);
        }
    }

    public void GetTableBList() {
        String data = IOFileBase.readDataFromFile(context, Const.TABLE_B_FILE_NAME);
        String importantData = IOFileBase.readDataFromFile(context, Const.ITABLE_B_FILE_NAME);
        if (data.equals("") && importantData.equals("")) {
            numberPickerView.ShowTableBList(new ArrayList<>());
        } else {
            String arr[] = data.trim().split(",");
            String importantArr[] = importantData.trim().split(",");
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
            numberPickerView.ShowTableBList(numbers);
        }
    }

    public void DeleteAllData(boolean isTableA) {
        String fileName = isTableA ? Const.TABLE_A_FILE_NAME : Const.TABLE_B_FILE_NAME;
        IOFileBase.saveDataToFile(context, fileName, "", 0);
        IOFileBase.saveDataToFile(context, "i" + fileName, "", 0);
        numberPickerView.DeleteAllDataSuccess("Xóa dữ liệu thành công!", isTableA);
    }


}
