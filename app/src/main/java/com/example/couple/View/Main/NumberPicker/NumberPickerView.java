package com.example.couple.View.Main.NumberPicker;

import com.example.couple.Model.Display.Number;
import com.example.couple.Model.Origin.Couple;

import java.util.List;

public interface NumberPickerView {
    void ShowError(String message);
    void ShowSubCoupleList(List<Couple> subCoupleList);
    void ShowSubCoupleNextDay(List<Couple> subCoupleList);
    void ShowSubCoupleLastMonth(List<Couple> subCoupleList);
    void ShowTableType1(List<Number> numbers );
    void ShowTableType2(List<Number> numbers);
    void SaveDataSuccess(String message);
    void ShowTableAList(List<Number> numbers);
    void ShowTableBList(List<Number> numbers);
    void DeleteAllDataSuccess(String message, boolean isTableA);
}
