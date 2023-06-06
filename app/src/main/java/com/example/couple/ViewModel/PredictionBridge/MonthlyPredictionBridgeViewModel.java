package com.example.couple.ViewModel.PredictionBridge;

import android.content.Context;

import com.example.couple.Base.Handler.FirebaseBase;
import com.example.couple.Model.Display.Prediction;
import com.example.couple.View.PredictionBridge.MonthlyPredictionBridgeView;

public class MonthlyPredictionBridgeViewModel {
    MonthlyPredictionBridgeView view;
    Context context;

    public MonthlyPredictionBridgeViewModel(MonthlyPredictionBridgeView view, Context context) {
        this.view = view;
        this.context = context;
    }

    public void addPredictionBridge(Prediction pb) {
        FirebaseBase fb = new FirebaseBase("monthly");
        fb.addObject(pb, pb.getId());
        view.AddPredictionBridgeSuccess();
    }

    public void updatePredictionBridge(Prediction pb, String id) {
        FirebaseBase fb = new FirebaseBase("monthly");
        fb.updateObject(pb.toMap(), id);
        view.UpdatePredictionBridgeSuccess();
    }
}
