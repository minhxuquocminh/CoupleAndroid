package com.example.couple.View.Main.NumberPicker;

import com.example.couple.Model.Bridge.Estimated.PeriodHistory;
import com.example.couple.Model.Origin.Jackpot;

import java.util.List;

public interface NumberPickerView {
    void showMessage(String message);
    void showPeriodHistory(List<PeriodHistory> periodHistoryList);
    void showSubJackpotTable(List<Jackpot> jackpotsLastWeek, List<Jackpot> jackpotsNextDay, List<Jackpot> subJackpots);
    void showTableType1(List<Integer> normals, List<Integer> imports);
    void showTableType2(List<Integer> normals, List<Integer> imports);
    void saveDataSuccess(String message);
    void showTableAList(List<Integer> normals, List<Integer> imports);
    void showTableBList(List<Integer> normals, List<Integer> imports);
    void deleteAllDataSuccess(String message, boolean isTableA);
}
