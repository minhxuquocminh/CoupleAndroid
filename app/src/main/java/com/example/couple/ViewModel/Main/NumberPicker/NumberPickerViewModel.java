package com.example.couple.ViewModel.Main.NumberPicker;

import android.content.Context;

import com.example.couple.Base.Handler.IOFileBase;
import com.example.couple.Custom.Const.FileName;
import com.example.couple.Custom.Const.TimeInfo;
import com.example.couple.Custom.Handler.JackpotHandler;
import com.example.couple.Custom.Statistics.JackpotStatistics;
import com.example.couple.Model.Display.JackpotNextDay;
import com.example.couple.Model.Display.Picker;
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

    public void getJackpotsManyYears(Couple lastCouple) {
        List<Jackpot> jackpotList = JackpotHandler.getJackpotListManyYears(context, 2);
        if (jackpotList.isEmpty()) return;
        numberPickerView.showJackpotsManyYears(jackpotList, lastCouple);
    }

    public void getSubJackpotNextDay(List<Jackpot> jackpotList, int lastCouple, int length) {
        List<JackpotNextDay> jackpotNextDayList =
                JackpotStatistics.getJackpotNextDayList(jackpotList, lastCouple);
        List<Jackpot> subJackpotList = new ArrayList<>();
        for (int i = 0; i < jackpotNextDayList.size(); i++) {
            subJackpotList.add(jackpotNextDayList.get(i).getJackpotSecond());
            if (i + 1 == length) break;
        }
        Collections.reverse(subJackpotList);
        if (!subJackpotList.isEmpty())
            numberPickerView.showSubJackpotNextDay(subJackpotList);
    }

    public void getSubJackpotLastMonth(List<Jackpot> jackpotList, DateBase lastDate) {
        DateBase nextDate = lastDate.plusDays(1);
        int month = nextDate.getMonth() - 1 == 0 ? TimeInfo.MONTH_OF_YEAR : nextDate.getMonth() - 1;
        int year = nextDate.getMonth() - 1 == 0 ? nextDate.getYear() - 1 : nextDate.getYear();
        List<DateBase> dateBases = new ArrayList<>();
        for (int i = -2; i <= 2; i++) {
            DateBase dateBase = new DateBase(nextDate.getDay() + i, month, year);
            if (dateBase.isValid()) {
                dateBases.add(dateBase);
            }
        }
        List<Jackpot> subJackpotList = JackpotStatistics.getJackpotListLastMonth(jackpotList, dateBases);
        if (!subJackpotList.isEmpty()) numberPickerView.showSubJackpotLastMonth(subJackpotList);
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
            numberPickerView.showTableType1(pickers);
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
            List<Picker> pickers = new ArrayList<>();
            if (!data.isEmpty()) {
                for (String s : arr) {
                    int number = Integer.parseInt(s.trim());
                    pickers.add(new Picker(number, 1));
                }
            }
            if (!importantData.isEmpty()) {
                for (String imp : importantArr) {
                    int number = Integer.parseInt(imp.trim());
                    pickers.add(new Picker(number, 2));
                }
            }
            numberPickerView.showTableType2(pickers);
        }
    }

    public void saveDataToFile(List<Picker> pickers, boolean isTableA) {
        StringBuilder data1 = new StringBuilder();
        StringBuilder data2 = new StringBuilder();
        Collections.sort(pickers, new Comparator<Picker>() {
            @Override
            public int compare(Picker o1, Picker o2) {
                return Integer.compare(o1.getNumber(), o2.getNumber());
            }
        });
        for (int i = 0; i < pickers.size(); i++) {
            if (pickers.get(i).getLevel() == 1) {
                data1.append(pickers.get(i).getNumber()).append(",");
            } else {
                data2.append(pickers.get(i).getNumber()).append(",");
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
            numberPickerView.showTableAList(pickers);
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
            numberPickerView.showTableBList(pickers);
        }
    }

    public void deleteAllData(boolean isTableA) {
        String fileName = isTableA ? FileName.TABLE_A : FileName.TABLE_B;
        IOFileBase.saveDataToFile(context, fileName, "", 0);
        IOFileBase.saveDataToFile(context, "i" + fileName, "", 0);
        numberPickerView.deleteAllDataSuccess("Xóa dữ liệu thành công!", isTableA);
    }

}
