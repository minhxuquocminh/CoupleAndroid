package com.example.couple.View.Main.NumberPicker;

import com.example.couple.Model.Handler.Picker;
import com.example.couple.Model.Origin.Couple;
import com.example.couple.Model.Origin.Jackpot;

import java.util.List;

public interface NumberPickerView {
    void showMessage(String message);
    void showJackpotsManyYears(List<Jackpot> jackpotList, Couple lastCouple);
    void showSubJackpotNextDay(List<Jackpot> subJackpotList);
    void showSubJackpotLastMonth(List<Jackpot> subJackpotList);
    void showTableType1(List<Picker> pickers);
    void showTableType2(List<Picker> pickers);
    void saveDataSuccess(String message);
    void showTableAList(List<Picker> pickers);
    void showTableBList(List<Picker> pickers);
    void deleteAllDataSuccess(String message, boolean isTableA);
}
