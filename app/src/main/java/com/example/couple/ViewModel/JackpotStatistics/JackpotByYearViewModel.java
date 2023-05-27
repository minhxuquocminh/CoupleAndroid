package com.example.couple.ViewModel.JackpotStatistics;

import android.content.Context;

import com.example.couple.Custom.Old.Statistics.JackpotStatistics;
import com.example.couple.Custom.Const.TimeInfo;
import com.example.couple.Custom.Handler.JackpotHandler;
import com.example.couple.View.JackpotStatistics.JackpotByYearView;

public class JackpotByYearViewModel {
    JackpotByYearView jackpotByYearView;
    Context context;

    public JackpotByYearViewModel(JackpotByYearView jackpotByYearView, Context context) {
        this.jackpotByYearView = jackpotByYearView;
        this.context = context;
    }

    public void GetTableOfJackpot(String year) {
        // vì khi lưu data của jackpot+{currentYear}+.txt ko lưu dữ liệu vào year.txt
        // nên hiển thị ưu tiên năm hiện tại

        if (year.length() == 0) {
            jackpotByYearView.ShowError("Bạn chưa nhập năm!");
        } else if (Integer.parseInt(year) == TimeInfo.CURRENT_YEAR) {
            String matrix[][] = JackpotHandler.GetJackpotMaxtrixByYear(context, TimeInfo.CURRENT_YEAR);
            if (matrix == null) {
                jackpotByYearView.ShowError("Lỗi không lấy được thông tin bảng XS Đặc biệt năm nay!");
            } else {
                jackpotByYearView.ShowTableOfJackpot(matrix, TimeInfo.CURRENT_YEAR);
            }
        } else {
            int startAndEndYearFile[] = JackpotStatistics.GetStartAndEndYearFile(context);
            if (startAndEndYearFile == null) {
                jackpotByYearView.ShowError("Bạn cần nạp dữ liệu XS Đặc biệt các năm mới có thể xem được " +
                        "bảng Xổ số Đặc biệt theo năm!");
            } else {
                int startYear = startAndEndYearFile[0];
                int endYear = startAndEndYearFile[1];
                int yearInt = Integer.parseInt(year);
                if (yearInt > TimeInfo.CURRENT_YEAR || yearInt < TimeInfo.CURRENT_YEAR - 9 + 1) {
                    jackpotByYearView.ShowError("Nằm ngoài phạm vi!");
                } else if (yearInt < startYear || endYear < TimeInfo.CURRENT_YEAR - 1) {
                    jackpotByYearView.ShowError("Bạn cần nạp thêm dữ liệu XS Đặc biệt các năm mới có thể xem" +
                            " được bảng XS Đặc biệt năm " + yearInt + "!");
                } else {
                    String matrix[][] = JackpotHandler.GetJackpotMaxtrixByYear(context, yearInt);
                    if (matrix == null) {
                        jackpotByYearView.ShowError("Lỗi không lấy được thông tin bảng XS Đặc biệt năm "
                                + yearInt + "!");
                    } else {
                        jackpotByYearView.ShowTableOfJackpot(matrix, yearInt);
                    }
                }
            }
        }

    }
}
