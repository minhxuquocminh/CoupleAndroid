package com.example.couple.ViewModel.SubScreen;

import android.content.Context;

import com.example.couple.View.SubScreen.ExperianceView;

public class ExperianceViewModel {
    ExperianceView experianceView;
    Context context;

    public ExperianceViewModel(ExperianceView experianceView, Context context) {
        this.experianceView = experianceView;
        this.context = context;
    }

    public void getExperiance() {
        String data = "";
        experianceView.showExperiance(data);
    }
}
