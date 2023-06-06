package com.example.couple.ViewModel.Account;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;

import com.example.couple.View.Account.SignUpView;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.UserProfileChangeRequest;

public class SignUpViewModel {
    Context context;
    SignUpView signUpView;

    public SignUpViewModel(Context context, SignUpView signUpView) {
        this.context = context;
        this.signUpView = signUpView;
    }


    public void signUp(String name, String email, String password) {
        FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            signUpView.SignUpSuccess(name);
                        } else {
                            signUpView.ShowError("Lỗi đăng ký! Vui lòng thử lại sau!");
                            Log.d("MINHTRAN", task.getException().getMessage());
                        }
                    }
                });
    }

    public void UpdateDisplayName(String name) {
        UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                .setDisplayName(name)
                .build();
        FirebaseAuth.getInstance().getCurrentUser().updateProfile(profileUpdates)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (!task.isSuccessful()) {
                            signUpView.ShowError("Lỗi lưu tên");
                        }
                    }
                });

    }
}
