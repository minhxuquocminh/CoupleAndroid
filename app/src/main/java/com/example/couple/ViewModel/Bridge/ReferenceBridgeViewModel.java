package com.example.couple.ViewModel.Bridge;

import android.content.Context;

import com.example.couple.Custom.Const.Const;
import com.example.couple.Custom.Const.TimeInfo;
import com.example.couple.Custom.Handler.Bridge.ConnectedBridgeHandler;
import com.example.couple.Custom.Handler.Bridge.OtherBridgeHandler;
import com.example.couple.Custom.Handler.JackpotHandler;
import com.example.couple.Custom.Handler.LotteryHandler;
import com.example.couple.Custom.Statistics.JackpotStatistics;
import com.example.couple.Model.Bridge.Connected.TriadBridge;
import com.example.couple.Model.Bridge.Touch.ConnectedBridge;
import com.example.couple.Model.Bridge.Double.JackpotSign;
import com.example.couple.Model.Bridge.LongBeat.NearestTime;
import com.example.couple.Model.Bridge.Double.NumberDouble;
import com.example.couple.Model.Bridge.NumberSet.Set;
import com.example.couple.Model.Origin.Jackpot;
import com.example.couple.Model.Origin.Lottery;
import com.example.couple.Model.Bridge.Connected.ClawSupport;
import com.example.couple.Model.Bridge.TriadClaw.Single;
import com.example.couple.View.Bridge.ReferenceBridgeView;

import java.util.ArrayList;
import java.util.List;

public class ReferenceBridgeViewModel {
    ReferenceBridgeView referenceBridgeView;
    Context context;

    public ReferenceBridgeViewModel(ReferenceBridgeView referenceBridgeView, Context context) {
        this.referenceBridgeView = referenceBridgeView;
        this.context = context;
    }

    //
    public void getJackpotList(int numberOfDays) {
        List<Jackpot> jackpotList = JackpotHandler.getReserveJackpotListFromFile(context, numberOfDays);
        if (jackpotList.isEmpty()) {
            referenceBridgeView.showMessage("Lỗi không lấy được các cầu theo XS Đặc biệt!");
        } else {
            referenceBridgeView.showJackpotList(jackpotList);
        }
    }

    public void getTouchThirdClawBridge(List<Jackpot> jackpotList) {
        List<Single> singles = OtherBridgeHandler.getTouchsByThirdClawBridge(jackpotList, 0);
        referenceBridgeView.showTouchThirdClawBridge(singles, jackpotList.size());
    }

    //
    public void getJackpotListThisYear() {
        List<Jackpot> jackpotList = JackpotHandler.getReserveJackpotListByYear(context, TimeInfo.CURRENT_YEAR);
        if (jackpotList.isEmpty()) {
            referenceBridgeView.showMessage("Lỗi không lấy được các cầu theo XS Đặc biệt năm nay!");
        } else {
            referenceBridgeView.showJackpotListThisYear(jackpotList);
        }
    }

    public void getRareSameDoubleList(List<Jackpot> jackpotList) {
        List<NearestTime> nearestTimeList = JackpotStatistics.getSameDoubleInNearestTime(jackpotList);
        List<NearestTime> subNearestTimeList = new ArrayList<>();
        int count = 0;
        for (int i = 0; i < nearestTimeList.size(); i++) {
            if (nearestTimeList.get(i).getDayNumberBefore() == Const.MAX_DAY_NUMBER_BEFORE) {
                count++;
            }
            if (nearestTimeList.get(i).getAppearanceTimes() <= 3) {
                subNearestTimeList.add(nearestTimeList.get(i));
            }
        }
        if (count <= 5) referenceBridgeView.showRareSameDoubleList(subNearestTimeList);
    }

    public void getCoupleDoNotAppearThisYear(List<Jackpot> jackpotList) {
        int[] coupleCounting = JackpotStatistics.getCoupleCounting(jackpotList, Const.MAX_ROW_COUNT_TABLE);
        List<Integer> numbers = new ArrayList<>();
        int count = 0;
        for (int i = 0; i < Const.MAX_ROW_COUNT_TABLE; i++) {
            if (coupleCounting[i] == 0) {
                count++;
                numbers.add(i);
            }
            if (count > 50) break;
        }
        if (count <= 50) referenceBridgeView.showCoupleDoNotAppearThisYear(numbers);
    }

    //
    public void getJackpotListLastYear() {
        List<Jackpot> jackpotList = JackpotHandler.getReserveJackpotListByYear(context,
                TimeInfo.CURRENT_YEAR - 1);
        if (jackpotList.isEmpty()) {
            referenceBridgeView.showMessage("Lỗi không lấy được các cầu theo XS Đặc biệt năm ngoái!");
        } else {
            referenceBridgeView.showJackpotListLastYear(jackpotList);
        }
    }

    public void getRareCoupleLastYear(List<Jackpot> jackpotList) {
        int[] coupleCounting = JackpotStatistics.getCoupleCounting(jackpotList, Const.MAX_ROW_COUNT_TABLE);
        List<Integer> noAppearanceList = new ArrayList<>();
        List<Integer> oneAppearanceList = new ArrayList<>();
        for (int i = 0; i < Const.MAX_ROW_COUNT_TABLE; i++) {
            if (coupleCounting[i] == 0) {
                noAppearanceList.add(i);
            }
            if (coupleCounting[i] == 1) {
                oneAppearanceList.add(i);
            }
        }
        referenceBridgeView.showRareCoupleLastYear(noAppearanceList, oneAppearanceList);
    }

    //
    public void getLotteryList(int numberOfDays) {
        List<Lottery> lotteryList = LotteryHandler.getLotteryListFromFile(context, numberOfDays);
        if (lotteryList.isEmpty()) {
            referenceBridgeView.showMessage("Lỗi không lấy được các cầu theo XSMB!");
        } else {
            referenceBridgeView.showLotteryList(lotteryList);
        }
    }

    public void getConnectedBridge(List<Lottery> lotteries) {
        ConnectedBridge connectedBridge = ConnectedBridgeHandler.getConnectedBridge(lotteries,
                0, Const.CONNECTED_BRIDGE_FINDING_DAYS, Const.CONNECTED_BRIDGE_MAX_DISPLAY);
        for (int i = 0; i < connectedBridge.getConnectedSupports().size(); i++) {
            for (int j = i + 1; j < connectedBridge.getConnectedSupports().size(); j++) {
                if (connectedBridge.getConnectedSupports().get(j).getValue()
                        == connectedBridge.getConnectedSupports().get(i).getValue()) {
                    connectedBridge.getConnectedSupports().remove(j);
                }
            }
        }
        referenceBridgeView.showConnectedBridge(connectedBridge);
    }

    public void getTriadClawBridge(List<Lottery> lotteryList) {
        List<ClawSupport> clawSupportList = ConnectedBridgeHandler.getClawSupport(lotteryList,
                0, 10, Const.CLAW_BRIDGE_MAX_DISPLAY, 3);
        referenceBridgeView.showThirdClawBridge(clawSupportList);
    }

    public void getTriadBridge(List<Lottery> lotteryList) {
        List<TriadBridge> triadBridgeList = ConnectedBridgeHandler.getTriadBridge(lotteryList,
                0, Const.TRIAD_SET_BRIDGE_FINDING_DAYS, Const.TRIAD_SET_BRIDGE_MAX_DISPLAY);

        List<Set> triadSetList = new ArrayList<>();
        List<Set> cancelSetList = new ArrayList<>();
        for (int i = 0; i < triadBridgeList.size(); i++) {
            if (triadBridgeList.get(i).getTriadStatusList().size() > 6) {
                cancelSetList.addAll(triadBridgeList.get(i).getSetList());
            } else {
                triadSetList.addAll(triadBridgeList.get(i).getSetList());
            }
        }

        for (int i = 0; i < triadSetList.size(); i++) {
            for (int j = i + 1; j < triadSetList.size(); j++) {
                if (triadSetList.get(i).equalsSet(triadSetList.get(j))) {
                    triadSetList.remove(j);
                    j--;
                }
            }
        }

        for (int i = 0; i < cancelSetList.size(); i++) {
            for (int j = i + 1; j < cancelSetList.size(); j++) {
                if (cancelSetList.get(i).equalsSet(cancelSetList.get(j))) {
                    cancelSetList.remove(j);
                    j--;
                }
            }
        }

        for (int i = 0; i < triadSetList.size(); i++) {
            for (int j = 0; j < cancelSetList.size(); j++) {
                if (triadSetList.get(i).equalsSet(cancelSetList.get(j))) {
                    triadSetList.remove(i);
                    i--;
                    break;
                }
            }
        }

        referenceBridgeView.showTriadBridge(triadSetList, cancelSetList);

    }

    public void getSignInLottery(Lottery lotteryToday) {
        List<Integer> numberList = JackpotStatistics.getSignInLottery(lotteryToday);
        referenceBridgeView.showSignInLottery(numberList);
    }

    //
    public void getJackpotListInManyDays(int numberOfDays) {
        List<Jackpot> jackpotList = JackpotHandler.getReserveJackpotListFromFile(context, numberOfDays);
        if (!jackpotList.isEmpty()) {
            if (jackpotList.size() < numberOfDays) {
                referenceBridgeView.showMessage("Vui lòng nạp dữ liệu XS Đặc biệt nhiều năm để xem " +
                        "được nhiều thông tin hơn!");
            } else {
                referenceBridgeView.showJackpotListInManyDays(jackpotList);
            }
        }
    }

    public void getNumberOfDaysBeforeSDB(List<Jackpot> jackpotList) {
        int numberOfDays = 0;
        for (int i = 0; i < jackpotList.size(); i++) {
            numberOfDays++;
            if (jackpotList.get(i).getCouple().isSameDouble()) {
                break;
            }
        }
        if (numberOfDays != 0) {
            referenceBridgeView.showNumberOfDaysBeforeSDB(numberOfDays);
        }
    }

    public void getBeatOfSameDouble(List<Jackpot> jackpotList) {
        List<Integer> beatList = JackpotStatistics.getBeatOfSameDouble(jackpotList);
        referenceBridgeView.showBeatOfSameDouble(beatList);
    }

    public void getSignInJackpot(List<Jackpot> jackpotList) {
        List<JackpotSign> jackpotSignList = JackpotStatistics.getSignInJackpot(jackpotList);
        referenceBridgeView.showSignInJackpot(jackpotSignList);
    }

    public void getNumberBeforeSameDoubleAppear(List<Jackpot> jackpotList) {
        List<NumberDouble> numberDoubleList = JackpotStatistics.getNumberBeforeSameDoubleAppear(jackpotList);
        referenceBridgeView.showNumberBeforeSameDoubleAppear(numberDoubleList);
    }

    public void getHeadForALongTime(List<Jackpot> jackpotList) {
        int runningDayNumber = Math.min(jackpotList.size(), 150);
        List<NearestTime> nearestTimeList = JackpotStatistics
                .getHeadAndTailInNearestTime(jackpotList.subList(0, runningDayNumber - 1));
        List<NearestTime> subNearestTimeList = new ArrayList<>();
        int count = 0;
        for (int i = 0; i < nearestTimeList.size(); i++) {
            if (nearestTimeList.get(i).getType().equals(Const.HEAD)) {
                count++;
                subNearestTimeList.add(nearestTimeList.get(i));
            }
            if (count > 2) break;
        }
        referenceBridgeView.showHeadForALongTime(runningDayNumber, subNearestTimeList);
    }

    public void getTailForALongTime(List<Jackpot> jackpotList) {
        int runningDayNumber = Math.min(jackpotList.size(), 150);
        List<NearestTime> nearestTimeList = JackpotStatistics
                .getHeadAndTailInNearestTime(jackpotList.subList(0, runningDayNumber - 1));
        List<NearestTime> subNearestTimeList = new ArrayList<>();
        int count = 0;
        for (int i = 0; i < nearestTimeList.size(); i++) {
            if (nearestTimeList.get(i).getType().equals(Const.TAIL)) {
                count++;
                subNearestTimeList.add(nearestTimeList.get(i));
            }
            if (count > 2) break;
        }
        referenceBridgeView.showTailForALongTime(runningDayNumber, subNearestTimeList);
    }

}
