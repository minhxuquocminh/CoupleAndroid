package com.example.couple.ViewModel.JackpotStatistics;

import android.content.Context;

import com.example.couple.Custom.Const.TimeInfo;
import com.example.couple.Custom.Handler.JackpotHandler;
import com.example.couple.Custom.Statistics.JackpotStatistics;
import com.example.couple.Model.Display.JackpotNextDay;
import com.example.couple.Model.Origin.Jackpot;
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

    public void getNumberOfYears() {
        int numberOfYears = JackpotStatistics.getMaxStartNumberOfYears(context, START_NUMBER_OF_YEARS);
        view.showNumberOfYears(numberOfYears);
    }

    public void getJackpotNextDay(String yearNumber, String dayNumberBeforeStr) {
        if (yearNumber.isEmpty()) {
            view.showMessage("Vui lòng nhập số năm hiển thị!");
        } else if (dayNumberBeforeStr.isEmpty()) {
            view.showMessage("Vui lòng nhập số ngày trước đó!");
        } else {
            int[] startAndEndYearFile = JackpotStatistics.getStartAndEndYearFile(context);
            if (startAndEndYearFile == null) {
                view.showMessage("Bạn cần nạp dữ liệu XS Đặc biệt các năm mới có thể xem được giải " +
                        "Đặc biệt trong những ngày tiếp theo!");
            } else {
                int startYear_file = startAndEndYearFile[0];
                int endYear_file = startAndEndYearFile[1];
                int numberOfYears_file = endYear_file - startYear_file + 1;
                int yearInt = Integer.parseInt(yearNumber);
                if (endYear_file < TimeInfo.CURRENT_YEAR || numberOfYears_file < yearInt) {
                    view.showRequestLoadMoreData(startYear_file, endYear_file);
                } else {
                    List<Jackpot> jackpotList = JackpotHandler.getReserveJackpotListFromFile(context, 10);
                    int dayNumberBefore = Integer.parseInt(dayNumberBeforeStr);
                    int couple = jackpotList.get(dayNumberBefore).getCoupleInt();
                    List<Jackpot> jackpotListManyYears = JackpotHandler.getJackpotListManyYears(context, yearInt);
                    List<JackpotNextDay> jackpotNextDayList =
                            JackpotStatistics.getJackpotNextDayList(jackpotListManyYears, couple);
                    view.showJackpotNextDay(jackpotNextDayList);
                }
            }
        }
    }


}
