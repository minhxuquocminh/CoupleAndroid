package com.example.couple.ViewModel.Main.HomePage;

import android.content.Context;

import com.example.couple.Base.Handler.AlarmBase;
import com.example.couple.Base.Handler.IOFileBase;
import com.example.couple.Base.Handler.InternetBase;
import com.example.couple.Base.Handler.MainThreadBase;
import com.example.couple.Base.Handler.MainThreadCallback;
import com.example.couple.Custom.Const.Const;
import com.example.couple.Custom.Const.FileName;
import com.example.couple.Custom.Const.TimeInfo;
import com.example.couple.Custom.Handler.Api;
import com.example.couple.Custom.Handler.Bridge.BCoupleBridgeHandler;
import com.example.couple.Custom.Handler.CheckUpdate;
import com.example.couple.Custom.Handler.JackpotHandler;
import com.example.couple.Custom.Handler.LotteryHandler;
import com.example.couple.Custom.Handler.TimeHandler;
import com.example.couple.Custom.Handler.UpdateDataAlarm;
import com.example.couple.Custom.Statistics.JackpotStatistics;
import com.example.couple.Model.Display.BSingle;
import com.example.couple.Model.Display.NearestTime;
import com.example.couple.Model.Origin.Jackpot;
import com.example.couple.Model.Origin.Lottery;
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

    public void updateAllDataIfNeeded(boolean isMainThread) {
        if (!InternetBase.isInternetAvailable(context)) {
            showError("Bạn đang offline.", isMainThread);
            return;
        }
        updateAllData(isMainThread);
        boolean isUpdateTime = CheckUpdate.checkUpdateTime(context);
        if (isUpdateTime) {

        }
    }

    public void updateAllData(boolean isMainThread) {
        if (!InternetBase.isInternetAvailable(context)) {
            showError("Bạn đang offline.", isMainThread);
            return;
        }
        String timeStatus = updateTime(isMainThread) ? "(done)" : "(failed)";
        String jackpotStatus = updateJackpot(false, isMainThread) ? "(done)" : "(failed)";
        String lotteryStatus = updateLottery(Const.MAX_DAYS_TO_GET_LOTTERY,
                false, isMainThread) ? "(done)" : "(failed)";
        String cycleStatus = TimeHandler.updateAllSexagenaryCycle(context) ? "(done)" : "(failed)";
        new MainThreadBase(new MainThreadCallback() {
            @Override
            public void postMainThread() {
                homePageView.showAllDataStatus("Cập nhật hoàn tất: thời gian " + timeStatus +
                        ", XS Đặc biệt " + jackpotStatus + ", XSMB " + lotteryStatus +
                        ", thời gian can chi " + cycleStatus + ".");
            }
        }, isMainThread).post();
    }

    public boolean updateTime(boolean isMainThread) {
        try {
            String timeData = Api.GetTimeDataFromInternet(context);
            if (timeData.equals("")) {
                showError("Lỗi không lấy được thông tin thời gian!", isMainThread);
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

    public boolean updateJackpot(boolean showMessage, boolean isMainThread) {
        try {
            String jackpotData = Api.GetJackpotDataFromInternet(context, TimeInfo.CURRENT_YEAR);
            if (jackpotData.equals("")) {
                if (showMessage)
                    showError("Lỗi không lấy được thông tin XS Đặc biệt.", isMainThread);
                return false;
            }
            IOFileBase.saveDataToFile(context, "jackpot" + TimeInfo.CURRENT_YEAR + ".txt",
                    jackpotData, 0);
            List<Jackpot> jackpotList = JackpotHandler.GetReserveJackpotListByYear(context, TimeInfo.CURRENT_YEAR);
            if (jackpotList.size() < TimeInfo.DAY_OF_WEEK) {
                String lastJackpotData = Api.GetJackpotDataFromInternet(context, TimeInfo.CURRENT_YEAR - 1);
                IOFileBase.saveDataToFile(context, "jackpot" + (TimeInfo.CURRENT_YEAR - 1)
                        + ".txt", lastJackpotData, 0);
            }
            if (showMessage) {
                new MainThreadBase(new MainThreadCallback() {
                    @Override
                    public void postMainThread() {
                        homePageView.updateJackpotSuccess("Cập nhật XS Đặc biệt thành công.");
                    }
                }, isMainThread).post();
            }
            return true;
        } catch (ExecutionException e) {
            return false;
        } catch (InterruptedException e) {
            return false;
        }
    }

    public boolean updateLottery(int numberOfDays, boolean showMessage, boolean isMainThread) {
        try {
            String lotteryData = Api.GetLotteryDataFromInternet(context, numberOfDays);
            if (lotteryData.equals("")) {
                if (showMessage) showError("Lỗi không lấy được thông tin XSMB!", isMainThread);
                return false;
            }
            IOFileBase.saveDataToFile(context, FileName.LOTTERY, lotteryData, 0);
            if (showMessage) {
                new MainThreadBase(new MainThreadCallback() {
                    @Override
                    public void postMainThread() {
                        homePageView.updateLotterySuccess("Cập nhật XSMB thành công!");
                    }
                }, isMainThread).post();
            }
            return true;
        } catch (ExecutionException e) {
            return false;
        } catch (InterruptedException e) {
            return false;
        }
    }

    public void getTimeDataFromFile() {
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
        homePageView.showTimeDataFromFile(time);
    }

    public void getJackpotDataFromFile() {
        List<Jackpot> jackpotList = JackpotHandler.GetReserveJackpotListFromFile(context, 7);
        if (jackpotList.isEmpty()) return;
        homePageView.showJackpotDataFromFile(jackpotList);
    }

    public void getLotteryList(int numberOfDays) {
        List<Lottery> lotteries = LotteryHandler.getLotteryListFromFile(context, numberOfDays);
        if (lotteries.isEmpty()) return;
        homePageView.showLotteryList(lotteries);
    }

    public void getHeadAndTailInLongestTime(List<Jackpot> jackpotList) {
        List<NearestTime> nearestTimeList = JackpotStatistics.GetHeadAndTailInNearestTime(jackpotList);
        if (nearestTimeList.isEmpty()) return;
        homePageView.showHeadAndTailInLongestTime(nearestTimeList);
    }

    public void getTouchBridge(List<Jackpot> jackpotList) {
        if (jackpotList.size() >= 2) {
            List<BSingle> touchList = BCoupleBridgeHandler.GetTouchBridge(jackpotList);
            homePageView.showTouchBridge(touchList);
        }
    }

    public void getSpecialTouchBridge(List<Jackpot> jackpotList) {
        if (jackpotList.size() >= 4) {
            List<Integer> touchList = BCoupleBridgeHandler.GetSpecialTouchBridge(jackpotList);
            homePageView.showSpecialTouchBridge(touchList);
        }
    }

    public void getNote() {
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
        homePageView.showNote(note);
    }

    private void showError(String message, boolean isMainThread) {
        new MainThreadBase(new MainThreadCallback() {
            @Override
            public void postMainThread() {
                homePageView.showError(message);
            }
        }, isMainThread).post();
    }
}
