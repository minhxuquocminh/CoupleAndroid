package com.example.couple.ViewModel.Lottery;

import android.content.Context;

import com.example.couple.Base.Handler.IOFileBase;
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

    public void GetLotteryList(String numberOfDaysStr) {
        if (numberOfDaysStr.equals("")) {
            lotteryView.ShowError("Bạn chưa nhập số ngày để lấy dữ liệu!");
        } else if (Integer.parseInt(numberOfDaysStr) <= 0) {
            lotteryView.ShowError("Số ngày không hợp lệ!");
        } else {
            int numberOfDays = Integer.parseInt(numberOfDaysStr);
            List<Lottery> lotteries = LotteryHandler.getLotteryListFromFile(context, numberOfDays);
            if (lotteries.size() == 0) {
                lotteryView.ShowError("Lỗi không tải được dữ liệu!");
            } else if (lotteries.size() < numberOfDays) {
                lotteryView.ShowRequestToUpdateLottery(lotteries.size(), numberOfDaysStr);
            } else {
                lotteryView.ShowLotteryList(lotteries);
            }
        }
    }

    public void UpdateLottery(int numberOfDays) {
        try {
            String lotteryData = Api.GetLotteryDataFromInternet(context, numberOfDays);
            if (lotteryData.equals("")) {
                lotteryView.ShowError("Lỗi không lấy được thông tin XSMB!");
            } else {
                IOFileBase.saveDataToFile(context, "lottery.txt", lotteryData, 0);
                lotteryView.UpdateLotterySuccess("Cập nhật dữ liệu thành công!", numberOfDays);
            }
        } catch (ExecutionException e) {
            lotteryView.ShowError("Lỗi mạng!");
        } catch (InterruptedException e) {
            lotteryView.ShowError("Lỗi mạng!");
        }
    }
}
