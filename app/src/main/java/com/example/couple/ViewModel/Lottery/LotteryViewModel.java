package com.example.couple.ViewModel.Lottery;

import android.content.Context;

import com.example.couple.Custom.Handler.LotteryHandler;
import com.example.couple.Model.Origin.Lottery;
import com.example.couple.View.Lottery.LotteryView;

import java.util.List;

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
        boolean updateState = LotteryHandler.updateLottery(context, numberOfDays);
        if (!updateState) {
            lotteryView.showMessage("Lỗi không lấy được thông tin XSMB!");
        } else {
            lotteryView.updateLotterySuccess("Cập nhật dữ liệu thành công!", numberOfDays);
        }
    }
}
