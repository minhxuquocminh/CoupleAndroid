package com.example.couple.ViewModel.Sub;

import android.content.Context;

import com.example.couple.Base.Handler.FirebaseBase;
import com.example.couple.View.Sub.ExperianceInfoView;

public class ExperianceInfoViewModel {
    ExperianceInfoView view;
    Context context;

    public ExperianceInfoViewModel(ExperianceInfoView view, Context context) {
        this.view = view;
        this.context = context;
    }

    public void UpdateExperiance(String experiance) {
        FirebaseBase firebaseBase =new FirebaseBase("experiance");
        firebaseBase.getmRef().setValue(experiance);
        view.UpdateExperianceSuccess("Cập nhật thông tin thành công!");
    }
}
