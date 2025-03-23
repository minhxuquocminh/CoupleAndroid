package com.example.couple.View.Main.CreateNumberArray;

import com.example.couple.Model.Bridge.CombineBridge;
import com.example.couple.Model.Bridge.NumberSet.NumberSetHistory;

import java.util.List;

public interface QuickNumberGeneratorView {
    void showMessage(String message);
    void showCombineBridgesToday(CombineBridge combineBridge);
    void showLongBeatNumbers(List<NumberSetHistory> histories);
    void showMappingAndTouchState(List<CombineBridge> combineBridges);
    void showSetsState(List<CombineBridge> combineBridges);
}
