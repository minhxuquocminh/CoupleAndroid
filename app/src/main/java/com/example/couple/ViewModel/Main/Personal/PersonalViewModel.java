package com.example.couple.ViewModel.Main.Personal;

import android.content.Context;

import com.example.couple.View.Main.Personal.PersonalView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class PersonalViewModel {
    Context context;
    PersonalView personalView;

    public PersonalViewModel(Context context, PersonalView personalView) {
        this.context = context;
        this.personalView = personalView;
    }

    public void signOut() {
        FirebaseAuth.getInstance().signOut();
        personalView.signOutSuccess();
    }

    public void checkLoggedIn() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user == null) {
            personalView.loginError();
        } else {
            String name = user.getDisplayName();
            String email = user.getEmail();
            personalView.loginSuccess(name, email);
        }
    }
}
