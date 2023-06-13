package com.example.couple.ViewModel.Lottery;

import android.content.Context;

import com.example.couple.Base.Handler.IOFileBase;
import com.example.couple.Custom.Const.Const;
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

    public void GetLotteryList(int numberOfDays) {
        List<Lottery> lotteries = LotteryHandler.getLotteryListFromFile(context, numberOfDays);
        if (lotteries.isEmpty()) {
            lotteryView.ShowError("Lỗi không tải được dữ liệu!");
        } else if (lotteries.size() < numberOfDays) {
            lotteryView.ShowRequestToUpdateLottery(lotteries.size(), numberOfDays);
        } else {
            lotteryView.ShowLotteryList(lotteries);
        }
    }

    public void UpdateLottery(int numberOfDays) {
        try {
            String lotteryData = Api.GetLotteryDataFromInternet(context, numberOfDays);
            if (lotteryData.equals("")) {
                lotteryView.ShowError("Lỗi không lấy được thông tin XSMB!");
            } else {
                IOFileBase.saveDataToFile(context, Const.LOTTERY_FILE_NAME, lotteryData, 0);
                lotteryView.UpdateLotterySuccess("Cập nhật dữ liệu thành công!", numberOfDays);
            }
        } catch (ExecutionException e) {
            lotteryView.ShowError("Lỗi mạng!");
        } catch (InterruptedException e) {
            lotteryView.ShowError("Lỗi mạng!");
        }
    }
}
