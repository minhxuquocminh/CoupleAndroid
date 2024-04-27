package com.example.couple.ViewModel.Main.NumberPicker;

import android.content.Context;

import com.example.couple.Base.Handler.IOFileBase;
import com.example.couple.Custom.Const.FileName;
import com.example.couple.Custom.Const.TimeInfo;
import com.example.couple.Custom.Handler.JackpotHandler;
import com.example.couple.Custom.Statistics.JackpotStatistics;
import com.example.couple.Model.Display.JackpotNextDay;
import com.example.couple.Model.Display.Number;
import com.example.couple.Model.Origin.Couple;
import com.example.couple.Model.Origin.Jackpot;
import com.example.couple.Model.Time.DateBase;
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

    public void getSubCoupleList(int length) {
        List<Jackpot> jackpotList = JackpotHandler.getReserveJackpotListFromFile(context, length);
        int size = Math.min(jackpotList.size(), length);
        List<Couple> subCoupleList = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            subCoupleList.add(jackpotList.get(i).getCouple());
        }
        Collections.reverse(subCoupleList);
        if (!subCoupleList.isEmpty()) {
            numberPickerView.showSubCoupleList(subCoupleList);
        }

    }

    public void getSubCoupleNextDay(int digit2D, int length) {
        int[] startEndYearFile = JackpotStatistics.getStartAndEndYearFile(context);
        if (startEndYearFile != null) {
            int startYearFile = startEndYearFile[0];
            int endYearFile = startEndYearFile[1];
            if (endYearFile == TimeInfo.CURRENT_YEAR) {
                int numberOfYearsFile = endYearFile - startYearFile + 1;
                int numberOfYears = Math.min(numberOfYearsFile, 3);
                List<Jackpot> jackpotList = JackpotHandler.getJackpotListManyYears(context, numberOfYears);
                List<JackpotNextDay> jackpotNextDayList = JackpotStatistics.getJackpotNextDayList(jackpotList, digit2D);
                int count = 0;
                List<Couple> subCoupleList = new ArrayList<>();
                for (int i = 0; i < jackpotNextDayList.size(); i++) {
                    count++;
                    subCoupleList.add(jackpotNextDayList.get(i).getJackpotSecond().getCouple());
                    if (count == length) break;
                }
                Collections.reverse(subCoupleList);
                if (!subCoupleList.isEmpty())
                    numberPickerView.showSubCoupleNextDay(subCoupleList);
            }
        }
    }

    public void getSubCoupleLastMonth(DateBase dateBase) {
        DateBase nextDateBase = dateBase.plusDays(1);
        int month = nextDateBase.getMonth() - 1;
        int year = nextDateBase.getYear();
        int numberOfYears = 1;
        if (month == 0) {
            month = TimeInfo.MONTH_OF_YEAR;
            year--;
            numberOfYears = 2;
        }
        List<Jackpot> jackpotList = JackpotHandler.getJackpotListManyYears(context, numberOfYears);
        List<Jackpot> monthJackpotList = JackpotStatistics.getMonthJackpotList(jackpotList, month, year);
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
        if (!subCoupleList.isEmpty()) numberPickerView.showSubCoupleLastMonth(subCoupleList);
    }

    public void getTableType1(boolean isTableA) {
        String data = "";
        String importantData = "";
        if (isTableA) {
            data = IOFileBase.readDataFromFile(context, FileName.TABLE_A);
            importantData = IOFileBase.readDataFromFile(context, FileName.ITABLE_A);
        } else {
            data = IOFileBase.readDataFromFile(context, FileName.TABLE_B);
            importantData = IOFileBase.readDataFromFile(context, FileName.ITABLE_B);
        }
        if (data.isEmpty() && importantData.isEmpty()) {
            numberPickerView.showTableType1(new ArrayList<>());
        } else {
            String[] arr = data.trim().split(",");
            String[] importantArr = importantData.trim().split(",");
            List<Number> numbers = new ArrayList<>();
            if (!data.isEmpty()) {
                for (String num : arr) {
                    int number = Integer.parseInt(num.trim());
                    numbers.add(new Number(number, 1));
                }
            }
            if (!importantData.isEmpty()) {
                for (String imp : importantArr) {
                    int number = Integer.parseInt(imp.trim());
                    numbers.add(new Number(number, 2));
                }
            }
            numberPickerView.showTableType1(numbers);
        }
    }

    public void getTableType2(boolean isTableA) {
        String data = "";
        String importantData = "";
        if (isTableA) {
            data = IOFileBase.readDataFromFile(context, FileName.TABLE_A);
            importantData = IOFileBase.readDataFromFile(context, FileName.ITABLE_A);
        } else {
            data = IOFileBase.readDataFromFile(context, FileName.TABLE_B);
            importantData = IOFileBase.readDataFromFile(context, FileName.ITABLE_B);
        }
        if (data.isEmpty() && importantData.isEmpty()) {
            numberPickerView.showTableType2(new ArrayList<>());
        } else {
            String[] arr = data.trim().split(",");
            String[] importantArr = importantData.trim().split(",");
            List<Number> numbers = new ArrayList<>();
            if (!data.isEmpty()) {
                for (String s : arr) {
                    int number = Integer.parseInt(s.trim());
                    numbers.add(new Number(number, 1));
                }
            }
            if (!importantData.isEmpty()) {
                for (String imp : importantArr) {
                    int number = Integer.parseInt(imp.trim());
                    numbers.add(new Number(number, 2));
                }
            }
            numberPickerView.showTableType2(numbers);
        }
    }

    public void saveDataToFile(List<Number> numbers, boolean isTableA) {
        StringBuilder data1 = new StringBuilder();
        StringBuilder data2 = new StringBuilder();
        Collections.sort(numbers, new Comparator<Number>() {
            @Override
            public int compare(Number o1, Number o2) {
                return Integer.compare(o1.getNumber(), o2.getNumber());
            }
        });
        for (int i = 0; i < numbers.size(); i++) {
            if (numbers.get(i).getLevel() == 1) {
                data1.append(numbers.get(i).getNumber()).append(",");
            } else {
                data2.append(numbers.get(i).getNumber()).append(",");
            }
        }
        String fileName = isTableA ? FileName.TABLE_A : FileName.TABLE_B;
        IOFileBase.saveDataToFile(context, fileName, data1.toString(), 0);
        IOFileBase.saveDataToFile(context, "i" + fileName, data2.toString(), 0);
        numberPickerView.saveDataSuccess("Lưu dữ liệu thành công!");
    }

    public void getTableAList() {
        String data = IOFileBase.readDataFromFile(context, FileName.TABLE_A);
        String importantData = IOFileBase.readDataFromFile(context, FileName.ITABLE_A);
        if (data.isEmpty() && importantData.isEmpty()) {
            numberPickerView.showTableAList(new ArrayList<>());
        } else {
            String[] arr = data.trim().split(",");
            String[] importantArr = importantData.trim().split(",");
            List<Number> numbers = new ArrayList<>();
            if (!data.isEmpty()) {
                for (String num : arr) {
                    int number = Integer.parseInt(num.trim());
                    numbers.add(new Number(number, 1));
                }
            }
            if (!importantData.isEmpty()) {
                for (String imp : importantArr) {
                    int number = Integer.parseInt(imp.trim());
                    numbers.add(new Number(number, 2));
                }
            }
            numberPickerView.showTableAList(numbers);
        }
    }

    public void getTableBList() {
        String data = IOFileBase.readDataFromFile(context, FileName.TABLE_B);
        String importantData = IOFileBase.readDataFromFile(context, FileName.ITABLE_B);
        if (data.isEmpty() && importantData.isEmpty()) {
            numberPickerView.showTableBList(new ArrayList<>());
        } else {
            String[] arr = data.trim().split(",");
            String[] importantArr = importantData.trim().split(",");
            List<Number> numbers = new ArrayList<>();
            if (!data.isEmpty()) {
                for (String num : arr) {
                    int number = Integer.parseInt(num.trim());
                    numbers.add(new Number(number, 1));
                }
            }
            if (!importantData.isEmpty()) {
                for (String imp : importantArr) {
                    int number = Integer.parseInt(imp.trim());
                    numbers.add(new Number(number, 2));
                }
            }
            numberPickerView.showTableBList(numbers);
        }
    }

    public void deleteAllData(boolean isTableA) {
        String fileName = isTableA ? FileName.TABLE_A : FileName.TABLE_B;
        IOFileBase.saveDataToFile(context, fileName, "", 0);
        IOFileBase.saveDataToFile(context, "i" + fileName, "", 0);
        numberPickerView.deleteAllDataSuccess("Xóa dữ liệu thành công!", isTableA);
    }


}
