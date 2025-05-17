package com.example.couple.ViewModel.Main.NumberPicker;

import android.content.Context;

import com.example.couple.Base.Handler.StorageBase;
import com.example.couple.Custom.Const.Const;
import com.example.couple.Custom.Const.TimeInfo;
import com.example.couple.Custom.Enum.StorageType;
import com.example.couple.Custom.Handler.Bridge.EstimatedBridgeHandler;
import com.example.couple.Custom.Handler.JackpotHandler;
import com.example.couple.Custom.Handler.Statistics.JackpotStatistics;
import com.example.couple.Model.Bridge.Estimated.PeriodHistory;
import com.example.couple.Model.Origin.Jackpot;
import com.example.couple.Model.Statistics.JackpotNextDay;
import com.example.couple.View.Main.NumberPicker.NumberPickerView;

import java.util.ArrayList;
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

    public void getSavedNumbers(boolean tableType1, boolean isTableA) {
        List<Integer> normals = StorageBase.getNumberList(context, isTableA ? StorageType.LIST_OF_PICKER_A : StorageType.LIST_OF_PICKER_B);
        List<Integer> imports = StorageBase.getNumberList(context, isTableA ? StorageType.LIST_OF_IMP_PICKER_A : StorageType.LIST_OF_IMP_PICKER_B);
        if (tableType1) numberPickerView.showTableType1(normals, imports);
        else numberPickerView.showTableType2(normals, imports);
    }

    public void saveDataToFile(List<Integer> normals, List<Integer> imports, boolean isTableA) {
        StorageBase.setNumberList(context, isTableA ? StorageType.LIST_OF_PICKER_A : StorageType.LIST_OF_PICKER_B, normals);
        StorageBase.setNumberList(context, isTableA ? StorageType.LIST_OF_IMP_PICKER_A : StorageType.LIST_OF_IMP_PICKER_B, imports);
        numberPickerView.saveDataSuccess("Lưu dữ liệu thành công!");
    }

    public void getTableAList() {
        List<Integer> normals = StorageBase.getNumberList(context, StorageType.LIST_OF_PICKER_A);
        List<Integer> imports = StorageBase.getNumberList(context, StorageType.LIST_OF_IMP_PICKER_A);
        numberPickerView.showTableAList(normals, imports);
    }

    public void getTableBList() {
        List<Integer> normals = StorageBase.getNumberList(context, StorageType.LIST_OF_PICKER_B);
        List<Integer> imports = StorageBase.getNumberList(context, StorageType.LIST_OF_IMP_PICKER_B);
        numberPickerView.showTableBList(normals, imports);
    }

    public void deleteAllData(boolean isTableA) {
        StorageBase.setNumberList(context, isTableA ? StorageType.LIST_OF_PICKER_A : StorageType.LIST_OF_PICKER_B, new ArrayList<>());
        StorageBase.setNumberList(context, isTableA ? StorageType.LIST_OF_IMP_PICKER_A : StorageType.LIST_OF_IMP_PICKER_B, new ArrayList<>());
        numberPickerView.deleteAllDataSuccess("Xóa dữ liệu thành công!", isTableA);
    }

}
