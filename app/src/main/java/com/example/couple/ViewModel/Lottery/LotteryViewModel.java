package com.example.couple.ViewModel.Lottery;

import android.content.Context;

import com.example.couple.Base.Handler.IOFileBase;
import com.example.couple.Custom.Const.FileName;
import com.example.couple.Custom.Handler.Api;
import com.example.couple.Custom.Handler.LotteryHandler;
import com.example.couple.Model.Origin.Lottery;
import com.example.couple.View.Lottery.LotteryView;

import java.util.List;
import java.util.concurrent.ExecutionException;

public class LotteryViewModel {
    LotteryView lotteryView;
    Context context;

    public LotteryViewModel(LotteryView lotteryView, Context context) {
        this.lotteryView = lotteryView;
        this.context = context;
    }

    public void getLotteryList(int numberOfDays) {
        List<Lottery> lotteries = LotteryHandler.getLotteryListFromFile(context, numberOfDays);
        if (lotteries.isEmpty()) {
            lotteryView.showMessage("Lỗi không tải được dữ liệu!");
        } else {
            lotteryView.showLotteryList(lotteries);
        }
    }

    public void updateLottery(int numberOfDays) {
        try {
            String lotteryData = Api.getLotteryDataFromInternet(context, numberOfDays);
            if (lotteryData.isEmpty()) {
                lotteryView.showMessage("Lỗi không lấy được thông tin XSMB!");
            } else {
                IOFileBase.saveDataToFile(context, FileName.LOTTERY, lotteryData, 0);
                lotteryView.updateLotterySuccess("Cập nhật dữ liệu thành công!", numberOfDays);
            }
        } catch (ExecutionException | InterruptedException e) {
            lotteryView.showMessage("Lỗi mạng!");
        }
    }
}
