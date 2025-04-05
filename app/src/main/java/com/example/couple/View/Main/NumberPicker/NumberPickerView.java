package com.example.couple.View.Main.NumberPicker;

import com.example.couple.Model.Bridge.Estimated.PeriodHistory;
import com.example.couple.Model.Handler.Picker;
import com.example.couple.Model.Origin.Jackpot;

import java.util.List;

public interface NumberPickerView {
    void showMessage(String message);
    void showPeriodHistory(List<PeriodHistory> periodHistoryList);
    void showSubJackpotTable(List<Jackpot> jackpotsLastWeek, List<Jackpot> jackpotsNextDay, List<Jackpot> subJackpots);
    void showTableType1(List<Picker> pickers);
    void showTableType2(List<Picker> pickers);
    void saveDataSuccess(String message);
    void showTableAList(List<Picker> pickers);
    void showTableBList(List<Picker> pickers);
    void deleteAllDataSuccess(String message, boolean isTableA);
}
