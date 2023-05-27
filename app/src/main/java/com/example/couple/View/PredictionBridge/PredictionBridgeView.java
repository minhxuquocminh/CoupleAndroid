package com.example.couple.View.PredictionBridge;

import com.example.couple.Model.Display.Prediction;

import java.util.List;

public interface PredictionBridgeView {
    void ShowError(String message);
    void ShowWeeklyPredictionBridge(List<Prediction> weeklyList);
    void ShowMonthlyPredictionBridge(List<Prediction> monthlyList);
}
