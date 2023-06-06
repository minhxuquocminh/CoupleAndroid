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

    public void SignOut() {
        FirebaseAuth.getInstance().signOut();
        personalView.SignOutSuccess();
    }

    public void CheckLoggedIn() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user == null) {
            personalView.NotLoggedIn();
        } else {
            String name = user.getDisplayName();
            String email = user.getEmail();
            personalView.LoggedIn(name, email);
        }
    }
}
