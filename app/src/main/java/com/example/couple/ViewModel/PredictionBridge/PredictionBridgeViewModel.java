package com.example.couple.ViewModel.PredictionBridge;

import android.content.Context;

import androidx.annotation.NonNull;

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

    public void getPredictionBridge() {
        FirebaseBase firebaseBase = new FirebaseBase("weekly");
        firebaseBase.getMRef().addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                List<Prediction> pbList = new ArrayList<>();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    Prediction pb = dataSnapshot.getValue(Prediction.class);
                    pbList.add(pb);
                }
                if (!pbList.isEmpty()) {
                    view.showWeeklyPredictionBridge(pbList);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        FirebaseBase firebaseBase2 = new FirebaseBase("monthly");
        firebaseBase2.getMRef().addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                List<Prediction> pbList = new ArrayList<>();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    Prediction pb = dataSnapshot.getValue(Prediction.class);
                    pbList.add(pb);
                }
                if (!pbList.isEmpty()) {
                    view.showMonthlyPredictionBridge(pbList);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}
