package com.example.couple.ViewModel.JackpotStatistics;

import android.content.Context;

import com.example.couple.Custom.Const.TimeInfo;
import com.example.couple.Custom.Handler.JackpotHandler;
import com.example.couple.Custom.Old.Statistics.JackpotStatistics;
import com.example.couple.Model.Display.JackpotNextDay;
import com.example.couple.View.JackpotStatistics.JackpotNextDayView;

import java.util.List;

public class JackpotNextDayViewModel {
    JackpotNextDayView view;
    Context context;

    public static final int START_NUMBER_OF_YEARS = 5;

    public JackpotNextDayViewModel(JackpotNextDayView view, Context context) {
        this.view = view;
        this.context = context;
    }

    public void GetNumberOfYears() {
        int numberOfYears = JackpotStatistics.GetMaxStartNumberOfYears(context, START_NUMBER_OF_YEARS);
        view.ShowNumberOfYears(numberOfYears);
    }

    public void GetJackpotNextDay(String yearNumber, String dayNumberBeforeStr) {
        if (yearNumber.equals("")) {
            view.ShowError("Vui lòng nhập số năm hiển thị!");
        } else if (dayNumberBeforeStr.equals("")) {
            view.ShowError("Vui lòng nhập số ngày trước đó!");
        } else {
            int startAndEndYearFile[] = JackpotStatistics.GetStartAndEndYearFile(context);
            if (startAndEndYearFile == null) {
                view.ShowError("Bạn cần nạp dữ liệu XS Đặc biệt các năm mới có thể xem được giải " +
                        "Đặc biệt trong những ngày tiếp theo!");
            } else {
                int startYear_file = startAndEndYearFile[0];
                int endYear_file = startAndEndYearFile[1];
                int numberOfYears_file = endYear_file - startYear_file + 1;
                int yearInt = Integer.parseInt(yearNumber);
                if (endYear_file < TimeInfo.CURRENT_YEAR || numberOfYears_file < yearInt) {
                    view.ShowRequestLoadMoreData(startYear_file, endYear_file);
                } else {
                    List<com.example.couple.Model.Origin.Jackpot> jackpotList = JackpotHandler.GetReserveJackpotListFromFile(context, 10);
                    int dayNumberBefore = Integer.parseInt(dayNumberBeforeStr);
                    int couple = jackpotList.get(dayNumberBefore).getCoupleInt();
                    List<com.example.couple.Model.Origin.Jackpot> jackpotListManyYears = JackpotHandler.GetJackpotListManyYears(context, yearInt);
                    List<JackpotNextDay> jackpotNextDayList =
                            JackpotStatistics.GetJackpotNextDayList(jackpotListManyYears, couple);
                    view.ShowJackpotNextDay(jackpotNextDayList);
                }
            }
        }
    }


}
