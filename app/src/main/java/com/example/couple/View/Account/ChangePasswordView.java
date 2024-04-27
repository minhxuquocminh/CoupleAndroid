package com.example.couple.View.Account;

public interface ChangePasswordView {
    void showMessage(String message);
    void checkOldPasswordSuccess(String newPassword);
    void updatePasswordSuccess(String message);
}
