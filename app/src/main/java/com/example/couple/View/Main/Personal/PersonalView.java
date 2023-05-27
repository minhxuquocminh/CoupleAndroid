package com.example.couple.View.Main.Personal;

public interface PersonalView {
    void ShowError(String message);
    void NotLoggedIn();
    void LoggedIn(String name, String email);
    void SignOutSuccess();
}
