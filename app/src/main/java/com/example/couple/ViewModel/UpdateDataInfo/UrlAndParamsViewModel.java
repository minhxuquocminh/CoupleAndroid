package com.example.couple.ViewModel.UpdateDataInfo;

import android.content.Context;

import com.example.couple.Base.Handler.IOFileBase;
import com.example.couple.Custom.Const.Const;
import com.example.couple.View.UpdateDataInfo.UrlAndParamsView;

public class UrlAndParamsViewModel {
    UrlAndParamsView view;
    Context context;

    public UrlAndParamsViewModel(UrlAndParamsView view, Context context) {
        this.view = view;
        this.context = context;
    }

    public void GetUrlAndParams() {
        String urlJackpot = IOFileBase.readDataFromFile(context, Const.JACKPOT_URL_FILE_NAME);
        String urlLottery = IOFileBase.readDataFromFile(context, Const.LOTTERY_URL_FILE_NAME);
        String arrUrlJackpot[] = urlJackpot.split("\n");
        String arrUrlLottery[] = urlLottery.split("\n");
        view.ShowUrlAndParams(arrUrlJackpot, arrUrlLottery);
    }

}
