package com.example.couple.ViewModel.PredictionBridge;

import android.content.Context;

import com.example.couple.Base.Handler.FirebaseBase;
import com.example.couple.Model.Display.Prediction;
import com.example.couple.View.PredictionBridge.PredictionBridgeView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class PredictionBridgeViewModel {
    PredictionBridgeView view;
    Context context;

    public PredictionBridgeViewModel(PredictionBridgeView view, Context context) {
        this.view = view;
        this.context = context;
    }

    public void GetPredictionBridge() {
        FirebaseBase firebaseBase = new FirebaseBase("weekly");
        firebaseBase.getmRef().addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                List<Prediction> pbList = new ArrayList<>();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    Prediction pb = dataSnapshot.getValue(Prediction.class);
                    pbList.add(pb);
                }
                if (!pbList.isEmpty()) {
                    view.ShowWeeklyPredictionBridge(pbList);
                }
            }

            @Override
            public void onCancelled(DatabaseError error) {

            }
        });

        FirebaseBase firebaseBase2 = new FirebaseBase("monthly");
        firebaseBase2.getmRef().addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                List<Prediction> pbList = new ArrayList<>();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    Prediction pb = dataSnapshot.getValue(Prediction.class);
                    pbList.add(pb);
                }
                if (!pbList.isEmpty()) {
                    view.ShowMonthlyPredictionBridge(pbList);
                }
            }

            @Override
            public void onCancelled(DatabaseError error) {

            }
        });
    }
}
