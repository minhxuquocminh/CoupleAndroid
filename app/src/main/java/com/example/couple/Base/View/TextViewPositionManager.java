package com.example.couple.Base.View;

import java.util.HashMap;
import java.util.Map;


public class TextViewPositionManager {
    Map<String, TextViewBase> textViewBaseMap;

    public TextViewPositionManager() {
        this.textViewBaseMap = new HashMap<>();
    }

    public void addTextViewBase(int row, int col, TextViewBase textViewBase) {
        textViewBaseMap.put(row + "," + col, textViewBase);
    }

    public TextViewBase getTextViewBase(int row, int col) {
        return textViewBaseMap.get(row + "," + col);
    }

}
