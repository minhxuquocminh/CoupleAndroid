package com.example.couple.ViewModel.UpdateDataInfo;

import android.content.Context;

import com.example.couple.Base.Handler.InternetBase;
import com.example.couple.Custom.Const.Const;
import com.example.couple.Custom.Const.TimeInfo;
import com.example.couple.Custom.Handler.JackpotHandler;
import com.example.couple.View.UpdateDataInfo.AddJackpotManyYearsView;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class AddJackpotManyYearsViewModel {
    AddJackpotManyYearsView view;
    Context context;

    public AddJackpotManyYearsViewModel(AddJackpotManyYearsView view, Context context) {
        this.view = view;
        this.context = context;
    }

    public void getStartYear() {
        int currentYear = TimeInfo.CURRENT_YEAR;
        List<Integer> years = JackpotHandler.getUpdatedYears(context);
        int startYear = years.isEmpty() ? currentYear - 5 : Math.min(years.get(0), currentYear - 5);
        view.showStartYear(startYear);
    }

    public void updateJackpotDataInManyYears(int startYear, boolean isGetAll) {
        int currentYear = TimeInfo.CURRENT_YEAR;
        if (currentYear - startYear + 1 > Const.MAX_YEARS) {
            view.showMessage("Năm bắt đầu nhỏ nhất là " + (currentYear - Const.MAX_YEARS + 1) + "!");
            return;
        }

        if (startYear > currentYear) {
            view.showMessage("Năm bắt đầu phải nhỏ hơn năm hiện tại!");
            return;
        }

        if (!InternetBase.isInternetAvailable(context)) {
            view.showMessage("Bạn đang offline.");
            return;
        }

        List<Integer> years = JackpotHandler.getUpdatedYears(context);
        List<Integer> yearsToUpdate = isGetAll ?
                IntStream.rangeClosed(startYear, currentYear).boxed().collect(Collectors.toList()) :
                IntStream.rangeClosed(startYear, currentYear).boxed()
                        .filter(x -> !years.contains(x)).collect(Collectors.toList());

        if (yearsToUpdate.isEmpty()) {
            view.showMessage("Không có dữ liệu nào cần thêm.");
            return;
        }

        List<Integer> updatedYears = JackpotHandler.updateJackpotDataByYears(context, yearsToUpdate);
        if (updatedYears.isEmpty()) {
            view.showMessage("Cập nhật dữ liệu thất bại.");
            return;
        }

        if (updatedYears.size() == yearsToUpdate.size()) {
            view.showMessage("Cập nhật dữ liệu thành công.");
            return;
        }

        List<Integer> errorYears = new ArrayList<>();
        for (int toUpdate : yearsToUpdate) {
            if (!updatedYears.contains(toUpdate)) {
                errorYears.add(toUpdate);
            }
        }
        view.updateJackpotDataInManyYearsError(errorYears);
    }


}
