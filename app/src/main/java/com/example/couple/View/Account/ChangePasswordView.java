package com.example.couple.View.Account;

public interface ChangePasswordView {
    void ShowError(String message);
    void OldPasswordIsRight(String newPassword);
    void ChangePasswordSuccess(String message);
}
