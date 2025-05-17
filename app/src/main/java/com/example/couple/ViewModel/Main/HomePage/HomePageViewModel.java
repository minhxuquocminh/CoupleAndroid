package com.example.couple.ViewModel.Main.HomePage;

import android.content.Context;

import com.example.couple.Base.Handler.StorageBase;
import com.example.couple.Custom.Enum.StorageType;
import com.example.couple.View.Main.HomePage.HomePageView;

public class HomePageViewModel {
    HomePageView homePageView;
    Context context;

    public HomePageViewModel(HomePageView homePageView, Context context) {
        this.homePageView = homePageView;
        this.context = context;
    }

    public void getNote() {
        String note = StorageBase.getString(context, StorageType.STRING_OF_NOTE);
        homePageView.showNote(note);
    }


}
