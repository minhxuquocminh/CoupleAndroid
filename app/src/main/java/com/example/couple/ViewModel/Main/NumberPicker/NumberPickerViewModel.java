package com.example.couple.ViewModel.Main.NumberPicker;

import android.content.Context;

import com.example.couple.Base.Handler.IOFileBase;
import com.example.couple.Custom.Const.Const;
import com.example.couple.Custom.Const.FileName;
import com.example.couple.Custom.Const.TimeInfo;
import com.example.couple.Custom.Handler.Bridge.EstimatedBridgeHandler;
import com.example.couple.Custom.Handler.JackpotHandler;
import com.example.couple.Custom.Handler.Statistics.JackpotStatistics;
import com.example.couple.Model.Bridge.Estimated.PeriodHistory;
import com.example.couple.Model.Handler.Picker;
import com.example.couple.Model.Origin.Jackpot;
import com.example.couple.Model.Statistics.JackpotNextDay;
import com.example.couple.View.Main.NumberPicker.NumberPickerView;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

public class NumberPickerViewModel {
    NumberPickerView numberPickerView;
    Context context;

    public NumberPickerViewModel(NumberPickerView numberPickerView, Context context) {
        this.numberPickerView = numberPickerView;
        this.context = context;
    }

    public void getPeriodHistory() {
        List<Jackpot> jackpotList = JackpotHandler.getJackpotListByDays(context, TimeInfo.DAY_OF_YEAR);
        if (jackpotList.isEmpty()) {
            numberPickerView.showMessage("Không có dữ liệu XS Đặc Biệt.");
            return;
        }

        List<PeriodHistory> periodHistoryList = EstimatedBridgeHandler.getEstimatedHistoryList(jackpotList,
                0, 2, Const.AMPLITUDE_OF_PERIOD);
        if (periodHistoryList.isEmpty()) {
            numberPickerView.showMessage("Không lấy được lịch sử các cách chạy.");
        } else {
            numberPickerView.showPeriodHistory(periodHistoryList);
        }
    }

    public void getSubJackpotTable(List<Jackpot> jackpotList) {
        if (jackpotList.isEmpty()) return;
        int lastCouple = jackpotList.get(0).getCoupleInt();
        Map<Integer, String[][]> matrixByYears = JackpotHandler.getJackpotMatrixByYears(context, 2);
        List<JackpotNextDay> jackpotNextDayList =
                JackpotStatistics.getJackpotNextDayList(matrixByYears, lastCouple);
        List<Jackpot> jackpotsNextDay = new ArrayList<>();
        for (int i = 0; i < jackpotNextDayList.size(); i++) {
            jackpotsNextDay.add(jackpotNextDayList.get(i).getJackpotSecond());
            if (i + 1 == 4) break;
        }
        List<Jackpot> jackpotsLastWeek = JackpotStatistics.getJackpotListLastWeek(jackpotList);
        numberPickerView.showSubJackpotTable(jackpotsLastWeek, jackpotsNextDay, jackpotList.subList(0, 4));
    }

    public void getTableType1(boolean isTableA) {
        String data = "";
        String importantData = "";
        if (isTableA) {
            data = IOFileBase.readDataFromFile(context, FileName.TABLE_A);
            importantData = IOFileBase.readDataFromFile(context, FileName.IMP_TABLE_A);
        } else {
            data = IOFileBase.readDataFromFile(context, FileName.TABLE_B);
            importantData = IOFileBase.readDataFromFile(context, FileName.IMP_TABLE_B);
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
            importantData = IOFileBase.readDataFromFile(context, FileName.IMP_TABLE_A);
        } else {
            data = IOFileBase.readDataFromFile(context, FileName.TABLE_B);
            importantData = IOFileBase.readDataFromFile(context, FileName.IMP_TABLE_B);
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
        pickers.sort(new Comparator<Picker>() {
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
        String importantData = IOFileBase.readDataFromFile(context, FileName.IMP_TABLE_A);
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
        String importantData = IOFileBase.readDataFromFile(context, FileName.IMP_TABLE_B);
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
