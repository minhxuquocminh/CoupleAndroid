package com.example.couple.ViewModel.Main.PredictionBridge;

import android.content.Context;

import com.example.couple.Base.Handler.FirebaseBase;
import com.example.couple.Model.Display.Prediction;
import com.example.couple.View.PredictionBridge.WeeklyPredictionBridgeView;

public class WeeklyPredictionBridgeViewModel {
    WeeklyPredictionBridgeView view;
    Context context;

    public WeeklyPredictionBridgeViewModel(WeeklyPredictionBridgeView view, Context context) {
        this.view = view;
        this.context = context;
    }

    public void addPredictionBridge(Prediction pb) {
        FirebaseBase fb = new FirebaseBase("weekly");
        fb.addObject(pb, pb.getId());
        view.AddPredictionBridgeSuccess();
    }

    public void updatePredictionBridge(Prediction pb, String id) {
        FirebaseBase fb = new FirebaseBase("weekly");
        fb.updateObject(pb.toMap(), id);
        view.UpdatePredictionBridgeSuccess();
    }
}
