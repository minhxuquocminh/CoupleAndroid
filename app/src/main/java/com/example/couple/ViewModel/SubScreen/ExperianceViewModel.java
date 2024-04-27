package com.example.couple.ViewModel.SubScreen;

import android.content.Context;

import androidx.annotation.NonNull;

import com.example.couple.Base.Handler.FirebaseBase;
import com.example.couple.View.SubScreen.ExperianceView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

public class ExperianceViewModel {
    ExperianceView experianceView;
    Context context;

    public ExperianceViewModel(ExperianceView experianceView, Context context) {
        this.experianceView = experianceView;
        this.context = context;
    }

    public void getExperiance() {
        FirebaseBase firebaseBase = new FirebaseBase("experiance");
        firebaseBase.getMRef().addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String data = snapshot.getValue(String.class);
                if (data == null || data.isEmpty()) {
                    experianceView.hideExperiance();
                } else {
                    experianceView.showExperiance(data);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}
