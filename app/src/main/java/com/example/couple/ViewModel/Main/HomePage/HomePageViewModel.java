package com.example.couple.ViewModel.Main.HomePage;

import android.content.Context;

import com.example.couple.Base.Handler.StorageBase;
import com.example.couple.Custom.Enum.StorageType;
import com.example.couple.Custom.Statistics.JackpotStatistics;
import com.example.couple.Model.Bridge.LongBeat.NearestTime;
import com.example.couple.Model.Origin.Jackpot;
import com.example.couple.View.Main.HomePage.HomePageView;

import java.util.List;
import java.util.Set;

public class HomePageViewModel {
    HomePageView homePageView;
    Context context;

    public HomePageViewModel(HomePageView homePageView, Context context) {
        this.homePageView = homePageView;
        this.context = context;
    }

    public void getHeadAndTailInLongestTime(List<Jackpot> jackpotList) {
        List<NearestTime> nearestTimeList = JackpotStatistics.getHeadAndTailInNearestTime(jackpotList);
        if (nearestTimeList.isEmpty()) return;
        homePageView.showHeadAndTailInLongestTime(nearestTimeList);
    }

    public void getNote() {
        Set<String> notes = StorageBase.getStringSet(context, StorageType.STRING_OF_NOTES);
        String note = notes.isEmpty() ? "" : String.join("\n", notes);
        homePageView.showNote(note);
    }


}
