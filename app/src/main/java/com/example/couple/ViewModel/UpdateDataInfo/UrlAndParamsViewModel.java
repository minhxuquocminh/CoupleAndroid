package com.example.couple.ViewModel.UpdateDataInfo;

import android.content.Context;

import com.example.couple.Base.Handler.IOFileBase;
import com.example.couple.Custom.Const.FileName;
import com.example.couple.View.UpdateDataInfo.UrlAndParamsView;

public class UrlAndParamsViewModel {
    UrlAndParamsView view;
    Context context;

    public UrlAndParamsViewModel(UrlAndParamsView view, Context context) {
        this.view = view;
        this.context = context;
    }

    public void GetUrlAndParams() {
        String urlJackpot = IOFileBase.readDataFromFile(context, FileName.JACKPOT_URL);
        String urlLottery = IOFileBase.readDataFromFile(context, FileName.LOTTERY_URL);
        String[] arrUrlJackpot = urlJackpot.split("\n");
        String[] arrUrlLottery = urlLottery.split("\n");
        view.ShowUrlAndParams(arrUrlJackpot, arrUrlLottery);
    }

}
