package com.example.couple.ViewModel.Main.Personal;

import android.content.Context;

import com.example.couple.Base.Handler.IOFileBase;
import com.example.couple.View.Main.Personal.UrlAndParamsView;

public class UrlAndParamsViewModel {
    UrlAndParamsView view;
    Context context;

    public UrlAndParamsViewModel(UrlAndParamsView view, Context context) {
        this.view = view;
        this.context = context;
    }

    public void GetUrlAndParams() {
        String urlJackpot = IOFileBase.readDataFromFile(context, "urljackpot.txt");
        String urlLottery = IOFileBase.readDataFromFile(context, "urllottery.txt");
        String arrUrlJackpot[] = urlJackpot.split("\n");
        String arrUrlLottery[] = urlLottery.split("\n");
        view.ShowUrlAndParams(arrUrlJackpot, arrUrlLottery);
    }

}
