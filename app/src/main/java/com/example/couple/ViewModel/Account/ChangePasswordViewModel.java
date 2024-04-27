package com.example.couple.ViewModel.Account;

import android.content.Context;

import androidx.annotation.NonNull;

import com.example.couple.View.Account.ChangePasswordView;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class ChangePasswordViewModel {
    Context context;
    ChangePasswordView view;

    public ChangePasswordViewModel(Context context, ChangePasswordView view) {
        this.context = context;
        this.view = view;
    }

    public void checkOldPassword(String oldPassword, String newPassword) {
        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        String currentEmail = firebaseUser.getEmail();
        AuthCredential credential = EmailAuthProvider.getCredential(currentEmail, oldPassword);

        firebaseUser.reauthenticate(credential)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            view.checkOldPasswordSuccess(newPassword);
                        } else {
                            view.showMessage("Sai mật khẩu!");
                        }
                    }
                });
    }

    public void updatePassword(String newPassword) {
        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        firebaseUser.updatePassword(newPassword)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            view.updatePasswordSuccess("Đã thay đổi mật khẩu!");
                        } else {
                            view.showMessage("Đã xảy ra lỗi khi cập nhật mật khẩu!");
                        }
                    }
                });
    }
}
