package com.example.couple.View.Main.NumberPicker;

import com.example.couple.Model.Display.Number;
import com.example.couple.Model.Origin.Couple;

import java.util.List;

public interface NumberPickerView {
    void showMessage(String message);
    void showSubCoupleList(List<Couple> subCoupleList);
    void showSubCoupleNextDay(List<Couple> subCoupleList);
    void showSubCoupleLastMonth(List<Couple> subCoupleList);
    void showTableType1(List<Number> numbers );
    void showTableType2(List<Number> numbers);
    void saveDataSuccess(String message);
    void showTableAList(List<Number> numbers);
    void showTableBList(List<Number> numbers);
    void deleteAllDataSuccess(String message, boolean isTableA);
}
