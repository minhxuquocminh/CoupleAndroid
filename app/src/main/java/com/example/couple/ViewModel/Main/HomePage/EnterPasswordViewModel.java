package com.example.couple.ViewModel.Main.HomePage;

import android.content.Context;

import androidx.annotation.NonNull;

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

    public void checkPassword(String password) {
        FirebaseBase firebaseBase = new FirebaseBase("experiancePassword");
        firebaseBase.getMRef().addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String data = snapshot.getValue(String.class);
                if (data == null || data.isEmpty()) {
                    firebaseBase.getMRef().setValue("15907530");
                } else {
                    if (password.equals(data)) {
                        view.checkPasswordSuccess();
                    } else {
                        view.showMessage("Sai mật khẩu.");
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}
