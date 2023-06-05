package com.example.couple.ViewModel.Main.HomePage;

import android.content.Context;

import com.example.couple.Base.Handler.AlarmBase;
import com.example.couple.Base.Handler.IOFileBase;
import com.example.couple.Custom.Const.Const;
import com.example.couple.Custom.Const.TimeInfo;
import com.example.couple.Custom.Handler.Api;
import com.example.couple.Custom.Handler.CheckUpdate;
import com.example.couple.Custom.Handler.CoupleBridgeHandler;
import com.example.couple.Custom.Handler.JackpotBridgeHandler;
import com.example.couple.Custom.Handler.JackpotHandler;
import com.example.couple.Custom.Handler.LotteryHandler;
import com.example.couple.Custom.Handler.UpdateDataAlarm;
import com.example.couple.Model.Display.BSingle;
import com.example.couple.Model.Origin.Jackpot;
import com.example.couple.Model.Origin.Lottery;
import com.example.couple.View.Main.HomePage.HomePageView;

import java.util.Arrays;
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
        String data1 = IOFileBase.readDataFromFile(context, "urljackpot.txt");
        String data2 = IOFileBase.readDataFromFile(context, "urllottery.txt");
        if (data1.equals("") || data2.equals("")) {
            data1 = "http://ketqua8.net/bang-dac-biet-nam\nchu16";
            data2 = "https://ketqua8.net/so-ket-qua\nwatermark";
            IOFileBase.saveDataToFile(context, "urljackpot.txt", data1, 0);
            IOFileBase.saveDataToFile(context, "urllottery.txt", data2, 0);
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
        boolean isUpdateTime = CheckUpdate.checkUpdateTime(context);
        if (isUpdateTime) {
            UpdateAllData();
        }
    }

    public void UpdateAllData() {
        String timeStatus = UpdateTime(false) ? "(done)" : "(failed)";
        String jackpotStatus = UpdateJackpot(false) ? "(done)" : "(failed)";
        String lotteryStatus =
                UpdateLottery(Const.MAX_DAYS_TO_GET_LOTTERY, false) ? "(done)" : "(failed)";
        homePageView.ShowAllDataStatus("Trạng thái: thời gian "
                + timeStatus + ", XS Đặc biệt " + jackpotStatus + ", XSMB " + lotteryStatus + ".");
    }

    public boolean UpdateTime(boolean showMessage) {
        try {
            String timeData = Api.GetTimeDataFromInternet();
            if (timeData.equals("")) {
                homePageView.ShowError(showMessage ? "Lỗi không lấy được thông tin thời gian!" : "");
                return false;
            }
            IOFileBase.saveDataToFile(context, "time.txt", timeData, 0);
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
                homePageView.ShowError(showMessage ? "Lỗi không lấy được thông tin XS Đặc biệt." : "");
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
            homePageView.UpdateJackpotSuccess(showMessage ? "Cập nhật XS Đặc biệt thành công." : "");
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
                homePageView.ShowError(showMessage ? "Lỗi không lấy được thông tin XSMB!" : "");
                return false;
            }
            IOFileBase.saveDataToFile(context, "lottery.txt", lotteryData, 0);
            homePageView.UpdateLotterySuccess(showMessage ? "Cập nhật XSMB thành công!" : "");
            return true;
        } catch (ExecutionException e) {
            return false;
        } catch (InterruptedException e) {
            return false;
        }
    }

    public void GetTimeDataFromFile() {
        String data = IOFileBase.readDataFromFile(context, "time.txt");
        String time = "Lỗi cập nhật thời gian!";
        try {
            String sub[] = data.split("===");
            String calendarWeek = sub[0];

            String calendarDay = sub[1];
            String calendarMonth = sub[2];
            String lunarDay = sub[3];

            String elements[] = lunarDay.split(",");
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
        if (jackpotList.size() > 0) {
            homePageView.ShowJackpotDataFromFile(jackpotList);
        }
    }

    public void GetLotteryList(String dayNumberStr) {
        int numberOfDays = Integer.parseInt(dayNumberStr);
        List<Lottery> lotteries = LotteryHandler.getLotteryListFromFile(context, numberOfDays);
        if (lotteries.size() != 0) {
            homePageView.ShowLotteryList(lotteries);
        }
    }

    public void GetTouchsByClawBridge(List<Lottery> lotteries) {
        List<Integer> searchDaysList = Arrays.asList(12, 14, 16, 18);
        List<Integer> touchs = JackpotBridgeHandler.GetTouchsByClawSupport(lotteries,
                searchDaysList, 0, 8, 0);
        homePageView.ShowTouchsByClawBridge(touchs);
    }

    public void GetTouchBridge(List<Jackpot> jackpotList) {
        if (jackpotList.size() >= 2) {
            List<BSingle> touchList = CoupleBridgeHandler.GetTouchBridge(jackpotList);
            homePageView.ShowTouchBridge(touchList);
        }
    }

    public void GetSpecialTouchBridge(List<Jackpot> jackpotList) {
        if (jackpotList.size() >= 4) {
            List<Integer> touchList = CoupleBridgeHandler.GetSpecialTouchBridge(jackpotList);
            homePageView.ShowSpecialTouchBridge(touchList);
        }
    }

    public void GetNote() {
        String data = IOFileBase.readDataFromFile(context, "note.txt");
        String arr[] = data.split("===");
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
