package com.example.couple.ViewModel.Main.CreateNumberArray;

import android.content.Context;

import com.example.couple.Custom.Const.Const;
import com.example.couple.Custom.Handler.Bridge.TouchBridgeHandler;
import com.example.couple.Custom.Handler.CoupleHandler;
import com.example.couple.Model.Bridge.Touch.CombineTouchBridge;
import com.example.couple.Model.Origin.Jackpot;
import com.example.couple.Model.Origin.Lottery;
import com.example.couple.View.Main.CreateNumberArray.CreateNumberArrayView;
import com.example.couple.View.Main.CreateNumberArray.QuickNumberGeneratorView;

import java.util.List;
import java.util.Locale;

public class QuickNumberGeneratorViewModel {
    QuickNumberGeneratorView view;
    Context context;

    public QuickNumberGeneratorViewModel(QuickNumberGeneratorView view, Context context) {
        this.view = view;
        this.context = context;
    }


    public void getCombineTouchState(List<Jackpot> jackpotList, List<Lottery> lotteries) {
        CombineTouchBridge combineTouchBridge=TouchBridgeHandler.getCombineTouchBridge(jackpotList,
                lotteries, Const.MAX_DAYS_TO_GET_LOTTERY);
        //view.showCombineTouchState(combineTouchBridge);

    }
}
