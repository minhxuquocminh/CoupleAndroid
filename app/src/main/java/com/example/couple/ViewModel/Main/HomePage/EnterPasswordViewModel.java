package com.example.couple.ViewModel.Main.HomePage;

import android.content.Context;

import com.example.couple.Base.Handler.FirebaseBase;
import com.example.couple.View.Main.HomePage.EnterPasswordView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

public class EnterPasswordViewModel {
    Context context;
    EnterPasswordView view;

    public EnterPasswordViewModel(Context context, EnterPasswordView view) {
        this.context = context;
        this.view = view;
    }

    public void CheckPassword(String password) {
        FirebaseBase firebaseBase =new FirebaseBase("experiancePassword");
        firebaseBase.getmRef().addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                String data=snapshot.getValue(String.class);
                if(data == null || data.equals("")){
                    firebaseBase.getmRef().setValue("666888");
                }else {
                    if(password.equals(data)){
                        view.PasswordIsRight();
                    }else {
                        view.ShowError("Sai mật khẩu.");
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError error) {

            }
        });
    }
}
