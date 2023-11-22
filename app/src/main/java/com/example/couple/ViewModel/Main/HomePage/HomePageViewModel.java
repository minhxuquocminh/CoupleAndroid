package com.example.couple.ViewModel.Main.HomePage;

import android.content.Context;

import com.example.couple.Base.Handler.AlarmBase;
import com.example.couple.Base.Handler.IOFileBase;
import com.example.couple.Base.Handler.InternetBase;
import com.example.couple.Custom.Const.Const;
import com.example.couple.Custom.Const.FileName;
import com.example.couple.Custom.Const.TimeInfo;
import com.example.couple.Custom.Handler.Api;
import com.example.couple.Custom.Handler.CheckUpdate;
import com.example.couple.Custom.Handler.Bridge.BCoupleBridgeHandler;
import com.example.couple.Custom.Handler.JackpotHandler;
import com.example.couple.Custom.Handler.LotteryHandler;
import com.example.couple.Custom.Handler.TimeHandler;
import com.example.couple.Custom.Handler.UpdateDataAlarm;
import com.example.couple.Custom.Statistics.JackpotStatistics;
import com.example.couple.Model.Display.BSingle;
import com.example.couple.Model.Display.NearestTime;
import com.example.couple.Model.Origin.Jackpot;
import com.example.couple.Model.Origin.Lottery;
import com.example.couple.Model.Time.DateBase;
import com.example.couple.View.Main.HomePage.HomePageView;

import java.util.List;
import java.util.concurrent.ExecutionException;

public class HomePageViewModel {
    HomePageView homePageView;
    Context context;

    public HomePageViewModel(HomePageView homePageView, Context context) {
        this.homePageView = homePageView;
        this.context = context;
    }

    /**
     * init url data if needed
     */
    public void setUrlAndParamsIfNoData() {
        String data1 = IOFileBase.readDataFromFile(context, FileName.JACKPOT_URL);
        String data2 = IOFileBase.readDataFromFile(context, FileName.LOTTERY_URL);
        if (data1.equals("") || data2.equals("")) {
            IOFileBase.saveDataToFile(context,
                    FileName.JACKPOT_URL, Const.JACKPOT_URL_AND_PARAMS, 0);
            IOFileBase.saveDataToFile(context,
                    FileName.LOTTERY_URL, Const.LOTTERY_URL_AND_PARAMS, 0);
        }
    }

    public void registerBackgoundRuntime() {
        AlarmBase.startAlarmEveryDay(context, UpdateDataAlarm.class, 1111,
                18, 30, 0);
        AlarmBase.startAlarmEveryDay(context, UpdateDataAlarm.class, 2222,
                18, 31, 0);
        AlarmBase.startAlarmEveryDay(context, UpdateDataAlarm.class, 3333,
                18, 32, 0);
        AlarmBase.startAlarmEveryDay(context, UpdateDataAlarm.class, 4444,
                18, 33, 0);
    }

    public void UpdateAllDataIfNeeded() {
        if (!InternetBase.isInternetAvailable(context)) {
            homePageView.ShowError("Bạn đang offline.");
            return;
        }
        boolean isUpdateTime = CheckUpdate.checkUpdateTime(context);
        if (isUpdateTime) {
            UpdateAllData();
        }
    }

    public void UpdateAllData() {
        if (!InternetBase.isInternetAvailable(context)) {
            homePageView.ShowError("Bạn đang offline.");
            return;
        }
        String timeStatus = UpdateTime() ? "(done)" : "(failed)";
        String jackpotStatus = UpdateJackpot(false) ? "(done)" : "(failed)";
        String lotteryStatus =
                UpdateLottery(Const.MAX_DAYS_TO_GET_LOTTERY, false) ? "(done)" : "(failed)";
        String cycleStatus = TimeHandler.updateAllSexagenaryCycle(context) ? "(done)" : "(failed)";
        homePageView.ShowAllDataStatus("Trạng thái: thời gian " + timeStatus + ", XS Đặc biệt "
                + jackpotStatus + ", XSMB " + lotteryStatus + ", thời gian can chi " + cycleStatus + ".");
    }

    public boolean UpdateTime() {
        try {
            String timeData = Api.GetTimeDataFromInternet(context);
            if (timeData.equals("")) {
                homePageView.ShowError("Lỗi không lấy được thông tin thời gian!");
                return false;
            }
            IOFileBase.saveDataToFile(context, FileName.TIME, timeData, 0);
            return true;
        } catch (ExecutionException e) {
            return false;
        } catch (InterruptedException e) {
            return false;
        }
    }

    public boolean UpdateSexagenaryCycle() {
        try {
            String timeData = Api.GetSexagenaryCycleByDay(context, DateBase.getCurrentDate());
            if (timeData.equals("")) {
                homePageView.ShowError("Lỗi không lấy được thông tin can chi!");
                return false;
            }
            IOFileBase.saveDataToFile(context, FileName.TIME, timeData, 0);
            return true;
        } catch (ExecutionException e) {
            return false;
        } catch (InterruptedException e) {
            return false;
        }
    }

    public boolean UpdateJackpot(boolean showMessage) {
        try {
            String jackpotData = Api.GetJackpotDataFromInternet(context, TimeInfo.CURRENT_YEAR);
            if (jackpotData.equals("")) {
                if (showMessage)
                    homePageView.ShowError("Lỗi không lấy được thông tin XS Đặc biệt.");
                return false;
            }
            IOFileBase.saveDataToFile(context, "jackpot" + TimeInfo.CURRENT_YEAR + ".txt",
                    jackpotData, 0);
            List<Jackpot> jackpotList = JackpotHandler.GetReserveJackpotListByYear(context, TimeInfo.CURRENT_YEAR);
            if (jackpotList.size() < Const.DAY_OF_WEEK) {
                String lastJackpotData = Api.GetJackpotDataFromInternet(context, TimeInfo.CURRENT_YEAR - 1);
                IOFileBase.saveDataToFile(context, "jackpot" + (TimeInfo.CURRENT_YEAR - 1)
                        + ".txt", lastJackpotData, 0);
            }
            homePageView.UpdateJackpotSuccess("Cập nhật XS Đặc biệt thành công.");
            return true;
        } catch (ExecutionException e) {
            return false;
        } catch (InterruptedException e) {
            return false;
        }
    }

    public boolean UpdateLottery(int numberOfDays, boolean showMessage) {
        try {
            String lotteryData = Api.GetLotteryDataFromInternet(context, numberOfDays);
            if (lotteryData.equals("")) {
                if (showMessage) homePageView.ShowError("Lỗi không lấy được thông tin XSMB!");
                return false;
            }
            IOFileBase.saveDataToFile(context, FileName.LOTTERY, lotteryData, 0);
            homePageView.UpdateLotterySuccess("Cập nhật XSMB thành công!");
            return true;
        } catch (ExecutionException e) {
            return false;
        } catch (InterruptedException e) {
            return false;
        }
    }

    public void GetTimeDataFromFile() {
        String data = IOFileBase.readDataFromFile(context, FileName.TIME);
        String time = "Lỗi cập nhật thời gian!";
        try {
            String[] sub = data.split("===");
            String calendarWeek = sub[0];

            String calendarDay = sub[1];
            String calendarMonth = sub[2];
            String lunarDay = sub[3];

            String[] elements = lunarDay.split(",");
            String day = elements[1].trim().split(" ")[1].trim();
            String month = elements[2].trim().split(" ")[1].trim();

            time = calendarWeek + ", Ngày " + calendarDay + " - " + calendarMonth
                    + "\n (Ngày " + day + "/" + month + " - Âm lịch)";
        } catch (Exception e) {
            e.printStackTrace();
        }
        homePageView.ShowTimeDataFromFile(time);
    }

    public void GetJackpotDataFromFile() {
        List<Jackpot> jackpotList = JackpotHandler.GetReserveJackpotListFromFile(context, 7);
        if (jackpotList.isEmpty()) return;
        homePageView.ShowJackpotDataFromFile(jackpotList);
    }

    public void GetLotteryList(String dayNumberStr) {
        int numberOfDays = Integer.parseInt(dayNumberStr);
        List<Lottery> lotteries = LotteryHandler.getLotteryListFromFile(context, numberOfDays);
        if (lotteries.isEmpty()) return;
        homePageView.ShowLotteryList(lotteries);
    }

    public void GetHeadAndTailInLongestTime(List<Jackpot> jackpotList) {
        List<NearestTime> nearestTimeList = JackpotStatistics.GetHeadAndTailInNearestTime(jackpotList);
        if (nearestTimeList.isEmpty()) return;
        homePageView.ShowHeadAndTailInLongestTime(nearestTimeList);
    }

    public void GetTouchBridge(List<Jackpot> jackpotList) {
        if (jackpotList.size() >= 2) {
            List<BSingle> touchList = BCoupleBridgeHandler.GetTouchBridge(jackpotList);
            homePageView.ShowTouchBridge(touchList);
        }
    }

    public void GetSpecialTouchBridge(List<Jackpot> jackpotList) {
        if (jackpotList.size() >= 4) {
            List<Integer> touchList = BCoupleBridgeHandler.GetSpecialTouchBridge(jackpotList);
            homePageView.ShowSpecialTouchBridge(touchList);
        }
    }

    public void GetNote() {
        String data = IOFileBase.readDataFromFile(context, FileName.NOTE);
        String[] arr = data.split("===");
        String note = "";
        for (int i = 0; i < arr.length; i++) {
            note += " + " + arr[i];
            if (i != arr.length - 1) {
                note += "\n";
            }
        }
        if (note.equals(" + ")) note = "";
        homePageView.ShowNote(note);
    }
}
