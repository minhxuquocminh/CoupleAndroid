package com.example.couple.ViewModel.Main.HomePage;

import android.content.Context;

import com.example.couple.Base.Handler.IOFileBase;
import com.example.couple.Custom.Const.FileName;
import com.example.couple.Custom.Statistics.JackpotStatistics;
import com.example.couple.Model.Display.NearestTime;
import com.example.couple.Model.Origin.Jackpot;
import com.example.couple.View.Main.HomePage.HomePageView;

import java.util.List;

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
        String data = IOFileBase.readDataFromFile(context, FileName.NOTE);
        String[] arr = data.split("===");
        StringBuilder note = new StringBuilder();
        for (int i = 0; i < arr.length; i++) {
            note.append(" + ").append(arr[i]);
            if (i != arr.length - 1) {
                note.append("\n");
            }
        }
        if (note.toString().equals(" + ")) note = new StringBuilder();
        homePageView.showNote(note.toString());
    }


}
