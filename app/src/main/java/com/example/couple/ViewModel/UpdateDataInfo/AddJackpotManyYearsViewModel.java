package com.example.couple.ViewModel.UpdateDataInfo;

import android.content.Context;

import com.example.couple.Base.Handler.IOFileBase;
import com.example.couple.Base.Handler.InternetBase;
import com.example.couple.Custom.Const.FileName;
import com.example.couple.Custom.Const.TimeInfo;
import com.example.couple.Custom.Handler.Api;
import com.example.couple.View.UpdateDataInfo.AddJackpotManyYearsView;

import java.util.concurrent.ExecutionException;

public class AddJackpotManyYearsViewModel {
    AddJackpotManyYearsView view;
    Context context;

    public AddJackpotManyYearsViewModel(AddJackpotManyYearsView view, Context context) {
        this.view = view;
        this.context = context;
    }

    public void GetStartYear() {
        String data = IOFileBase.readDataFromFile(context, FileName.JACKPOT_YEARS);
        if (data.equals("")) {
            view.ShowStartYear(TimeInfo.CURRENT_YEAR - 4);
            return;
        }
        String[] arr = data.split("-");
        view.ShowStartYear(Integer.parseInt(arr[0].trim()));
    }

    public void GetJackpotDataInManyYears(int startYear, boolean availableData) {
        if (TimeInfo.CURRENT_YEAR - startYear + 1 > 9) {
            view.ShowError("Năm bắt đầu nhỏ nhất là " + (TimeInfo.CURRENT_YEAR - 9 + 1) + "!");
            return;
        }

        if (startYear > TimeInfo.CURRENT_YEAR) {
            view.ShowError("Năm bắt đầu phải nhỏ hơn năm hiện tại!");
            return;
        }

        if (!InternetBase.isInternetAvailable(context)) {
            view.ShowError("Bạn đang offline.");
            return;
        }

        String years = IOFileBase.readDataFromFile(context, FileName.JACKPOT_YEARS);
        if (years.equals("")) {
            availableData = false;
        }

        int countYear = 0;
        String yearData = "";
        for (int i = startYear; i <= TimeInfo.CURRENT_YEAR; i++) {
            if (availableData) {
                String[] yearArr = years.split("-");
                int count = 0;
                for (int j = 0; j < yearArr.length; j++) {
                    if (Integer.parseInt(yearArr[j]) == i) {
                        count++;
                    }
                }
                if (count > 0) {
                    yearData += i + "-";
                    countYear++;
                    continue;
                }
            }

            try {
                String data = Api.GetJackpotDataFromInternet(context, i);
                if (!data.equals("")) {
                    IOFileBase.saveDataToFile(context, "jackpot" + i + ".txt", data, 0);
                    yearData += i + "-";
                    countYear++;
                }
            } catch (ExecutionException e) {
                view.ShowError("Lỗi mạng!");
            } catch (InterruptedException e) {
                view.ShowError("Lỗi mạng!");
            }
        }
        if (countYear == TimeInfo.CURRENT_YEAR - startYear + 1) {
            IOFileBase.saveDataToFile(context, FileName.JACKPOT_YEARS, yearData, 0);
            view.GetJackpotDataSuccess("Thêm dữ liệu thành công!");
        } else {
            view.ShowError("Lỗi nạp dữ liệu!");
        }
    }
}
