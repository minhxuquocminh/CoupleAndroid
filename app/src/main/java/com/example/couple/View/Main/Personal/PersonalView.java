package com.example.couple.View.Main.Personal;

public interface PersonalView {
    void showMessage(String message);
    void loginError();
    void loginSuccess(String name, String email);
    void signOutSuccess();
}
