package com.example.couple.View.PredictionBridge;

import com.example.couple.Model.Display.Prediction;

import java.util.List;

public interface PredictionBridgeView {
    void showMessage(String message);
    void showWeeklyPredictionBridge(List<Prediction> weeklyList);
    void showMonthlyPredictionBridge(List<Prediction> monthlyList);
}
