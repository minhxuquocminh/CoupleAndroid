package com.example.couple.ViewModel.UpdateDataInfo;

import android.content.Context;

import com.example.couple.Base.Handler.IOFileBase;
import com.example.couple.Base.Handler.InternetBase;
import com.example.couple.Custom.Const.FileName;
import com.example.couple.Custom.Const.TimeInfo;
import com.example.couple.Custom.Handler.Api;
import com.example.couple.View.UpdateDataInfo.AddJackpotManyYearsView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class AddJackpotManyYearsViewModel {
    AddJackpotManyYearsView view;
    Context context;

    public AddJackpotManyYearsViewModel(AddJackpotManyYearsView view, Context context) {
        this.view = view;
        this.context = context;
    }

    public void getStartYear() {
        String data = IOFileBase.readDataFromFile(context, FileName.JACKPOT_YEARS);
        if (data.isEmpty()) {
            view.showStartYear(TimeInfo.CURRENT_YEAR - 4);
            return;
        }
        String[] arr = data.split("-");
        view.showStartYear(Integer.parseInt(arr[0].trim()));
    }

    public void updateJackpotDataInManyYears(int startYear, boolean isGetAll) {
        if (TimeInfo.CURRENT_YEAR - startYear + 1 > 9) {
            view.showMessage("Năm bắt đầu nhỏ nhất là " + (TimeInfo.CURRENT_YEAR - 9 + 1) + "!");
            return;
        }

        if (startYear > TimeInfo.CURRENT_YEAR) {
            view.showMessage("Năm bắt đầu phải nhỏ hơn năm hiện tại!");
            return;
        }

        if (!InternetBase.isInternetAvailable(context)) {
            view.showMessage("Bạn đang offline.");
            return;
        }

        List<Integer> lastUpdatedYears = new ArrayList<>();
        String yearData = IOFileBase.readDataFromFile(context, FileName.JACKPOT_YEARS);
        if (!isGetAll && !yearData.isEmpty()) {
            String[] yearArr = yearData.split("-");
            for (String yearStr : yearArr) {
                lastUpdatedYears.add(Integer.valueOf(yearStr));
            }
        }

        List<Integer> yearsToUpdate = new ArrayList<>();
        for (int year = startYear; year <= TimeInfo.CURRENT_YEAR; year++) {
            if (!lastUpdatedYears.contains(year)) {
                yearsToUpdate.add(year);
            }
        }

        if (yearsToUpdate.isEmpty()) {
            view.showMessage("Không có dữ liệu nào cần thêm.");
            return;
        }

        List<Integer> updatedYears = this.updateJackpotDataInManyYears(yearsToUpdate);
        if (updatedYears.isEmpty()) {
            view.showMessage("Cập nhật dữ liệu thất bại.");
            return;
        }

        lastUpdatedYears.addAll(updatedYears);
        Collections.sort(lastUpdatedYears);
        StringBuilder yearData2 = new StringBuilder();
        for (int year : lastUpdatedYears) {
            yearData2.append(year).append("-");
        }

        IOFileBase.saveDataToFile(context, FileName.JACKPOT_YEARS, yearData2.toString(), 0);
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

    private List<Integer> updateJackpotDataInManyYears(List<Integer> years) {
        List<Integer> updatedYears = new ArrayList<>();
        for (int year : years) {
            try {
                String data = Api.getJackpotDataFromInternet(context, year);
                if (!data.isEmpty()) {
                    IOFileBase.saveDataToFile(context, "jackpot" + year + ".txt", data, 0);
                    updatedYears.add(year);
                }
            } catch (ExecutionException | InterruptedException e) {
                view.showMessage("Lỗi mạng!");
            }
        }
        return updatedYears;
    }
}
