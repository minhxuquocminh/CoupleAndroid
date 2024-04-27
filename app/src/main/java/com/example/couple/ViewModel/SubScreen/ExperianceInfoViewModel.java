package com.example.couple.ViewModel.SubScreen;

import android.content.Context;

import com.example.couple.Base.Handler.FirebaseBase;
import com.example.couple.View.SubScreen.ExperianceInfoView;

public class ExperianceInfoViewModel {
    ExperianceInfoView view;
    Context context;

    public ExperianceInfoViewModel(ExperianceInfoView view, Context context) {
        this.view = view;
        this.context = context;
    }

    public void updateExperiance(String experiance) {
        FirebaseBase firebaseBase = new FirebaseBase("experiance");
        firebaseBase.getMRef().setValue(experiance);
        view.updateExperianceSuccess("Cập nhật thông tin thành công!");
    }
}
