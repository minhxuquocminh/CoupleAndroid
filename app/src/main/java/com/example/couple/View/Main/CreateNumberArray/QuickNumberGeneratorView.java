package com.example.couple.View.Main.CreateNumberArray;

import com.example.couple.Model.Bridge.CombinedBridge;
import com.example.couple.Model.Bridge.NumberSet.NumberSetHistory;

import java.util.List;

public interface QuickNumberGeneratorView {
    void showMessage(String message);
    void showCombineBridgesToday(CombinedBridge combinedBridge);
    void showLongBeatNumbers(List<NumberSetHistory> histories);
    void showMappingAndTouchState(List<CombinedBridge> combinedBridges);
    void showSetsState(List<CombinedBridge> combinedBridges);
}
