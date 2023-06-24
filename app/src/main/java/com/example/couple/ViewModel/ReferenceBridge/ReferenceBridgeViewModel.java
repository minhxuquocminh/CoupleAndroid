package com.example.couple.ViewModel.ReferenceBridge;

import android.content.Context;

import com.example.couple.Custom.Const.Const;
import com.example.couple.Custom.Const.TimeInfo;
import com.example.couple.Custom.Handler.CoupleBridgeHandler;
import com.example.couple.Custom.Handler.JackpotBridgeHandler;
import com.example.couple.Custom.Handler.JackpotHandler;
import com.example.couple.Custom.Handler.LotteryHandler;
import com.example.couple.Custom.Statistics.JackpotStatistics;
import com.example.couple.Model.BridgeCouple.TriadBridge;
import com.example.couple.Model.BridgeSingle.ConnectedBridge;
import com.example.couple.Model.Display.BSingle;
import com.example.couple.Model.Display.HeadTail;
import com.example.couple.Model.Display.JackpotSign;
import com.example.couple.Model.Display.NearestTime;
import com.example.couple.Model.Display.NumberDouble;
import com.example.couple.Model.Display.Set;
import com.example.couple.Model.Origin.Jackpot;
import com.example.couple.Model.Origin.Lottery;
import com.example.couple.Model.Support.ClawSupport;
import com.example.couple.View.ReferenceBridge.ReferenceBridgeView;

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
    public void GetJackpotList(int numberOfDays) {
        List<Jackpot> jackpotList = JackpotHandler.GetReserveJackpotListFromFile(context, numberOfDays);
        if (jackpotList.size() == 0) {
            referenceBridgeView.ShowError("Lỗi không lấy được các cầu theo XS Đặc biệt!");
        } else {
            referenceBridgeView.ShowJackpotList(jackpotList);
        }
    }

    public void GetTouchBridge(List<Jackpot> jackpotList) {
        if (jackpotList.size() >= 2) {
            List<BSingle> touchList = CoupleBridgeHandler.GetTouchBridge(jackpotList);
            referenceBridgeView.ShowTouchBridge(touchList);
        }
    }

    public void GetSpecialTouchBridge(List<Jackpot> jackpotList) {
        if (jackpotList.size() >= 4) {
            List<Integer> touchList = CoupleBridgeHandler.GetSpecialTouchBridge(jackpotList);
            referenceBridgeView.ShowSpecialTouchBridge(touchList);
        }
    }

    public void GetTouchThirdClawBridge(List<Jackpot> jackpotList) {
        List<BSingle> BSingleList = JackpotBridgeHandler.GetTouchsByThirdClawBridge(jackpotList, 0);
        referenceBridgeView.ShowTouchThirdClawBridge(BSingleList, jackpotList.size());
    }

    //
    public void GetJackpotListThisYear() {
        List<Jackpot> jackpotList = JackpotHandler.GetReserveJackpotListByYear(context, TimeInfo.CURRENT_YEAR);
        if (jackpotList.size() == 0) {
            referenceBridgeView.ShowError("Lỗi không lấy được các cầu theo XS Đặc biệt năm nay!");
        } else {
            referenceBridgeView.ShowJackpotListThisYear(jackpotList);
        }
    }

    public void GetRareSameDoubleList(List<Jackpot> jackpotList) {
        List<NearestTime> nearestTimeList = JackpotStatistics.GetSameDoubleInNearestTime(jackpotList);
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
        if (count <= 5) referenceBridgeView.ShowRareSameDoubleList(subNearestTimeList);
    }

    public void GetCoupleDoNotAppearThisYear(List<Jackpot> jackpotList) {
        int[] coupleCounting = JackpotStatistics.GetCoupleCounting(jackpotList, Const.MAX_ROW_COUNT_TABLE);
        List<Integer> numbers = new ArrayList<>();
        int count = 0;
        for (int i = 0; i < Const.MAX_ROW_COUNT_TABLE; i++) {
            if (coupleCounting[i] == 0) {
                count++;
                numbers.add(i);
            }
            if (count > 50) break;
        }
        if (count <= 50) referenceBridgeView.ShowCoupleDoNotAppearThisYear(numbers);
    }

    //
    public void GetJackpotListLastYear() {
        List<Jackpot> jackpotList = JackpotHandler.GetReserveJackpotListByYear(context,
                TimeInfo.CURRENT_YEAR - 1);
        if (jackpotList.size() == 0) {
            referenceBridgeView.ShowError("Lỗi không lấy được các cầu theo XS Đặc biệt năm ngoái!");
        } else {
            referenceBridgeView.ShowJackpotListLastYear(jackpotList);
        }
    }

    public void GetRareCoupleLastYear(List<Jackpot> jackpotList) {
        int[] coupleCounting = JackpotStatistics.GetCoupleCounting(jackpotList, Const.MAX_ROW_COUNT_TABLE);
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
        referenceBridgeView.ShowRareCoupleLastYear(noAppearanceList, oneAppearanceList);
    }

    //
    public void GetLotteryList(int numberOfDays) {
        List<Lottery> lotteryList = LotteryHandler.getLotteryListFromFile(context, numberOfDays);
        if (lotteryList.size() == 0) {
            referenceBridgeView.ShowError("Lỗi không lấy được các cầu theo XSMB!");
        } else {
            referenceBridgeView.ShowLotteryList(lotteryList);
        }
    }

    public void GetConnectedBridge(List<Lottery> lotteries) {
        ConnectedBridge connectedBridge = JackpotBridgeHandler.GetConnectedBridge(lotteries,
                10, 0, Const.CONNECTED_BRIDGE_MAX_DISPLAY);
        for (int i = 0; i < connectedBridge.getConnectedSupports().size(); i++) {
            for (int j = i + 1; j < connectedBridge.getConnectedSupports().size(); j++) {
                if (connectedBridge.getConnectedSupports().get(j).getValue()
                        == connectedBridge.getConnectedSupports().get(i).getValue()) {
                    connectedBridge.getConnectedSupports().remove(j);
                }
            }
        }
        referenceBridgeView.ShowConnectedBridge(connectedBridge);
    }

    public void GetTriadClawBridge(List<Lottery> lotteryList) {
        List<ClawSupport> clawSupportList = JackpotBridgeHandler.GetClawSupport(lotteryList,
                10, 0, 3, Const.CLAW_BRIDGE_MAX_DISPLAY);
        referenceBridgeView.ShowThirdClawBridge(clawSupportList);
    }

    public void GetTriadBridge(List<Lottery> lotteryList) {
        List<TriadBridge> triadBridgeList = JackpotBridgeHandler.GetTriadBridge(lotteryList,
                Const.TRIAD_SET_BRIDGE_SEARCHING_DAYS, 0, Const.TRIAD_SET_BRIDGE_MAX_DISPLAY);

        List<Set> triadSetList = new ArrayList<>();
        List<Set> cancelSetList = new ArrayList<>();
        for (int i = 0; i < triadBridgeList.size(); i++) {
            if (triadBridgeList.get(i).getStatusList().size() > 6) {
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

        referenceBridgeView.ShowTriadBridge(triadSetList, cancelSetList);

    }

    public void GetSignInLottery(Lottery lotteryToday) {
        List<Integer> numberList = JackpotStatistics.GetSignInLottery(lotteryToday);
        referenceBridgeView.ShowSignInLottery(numberList);
    }

    //
    public void GetJackpotListInManyDays(int numberOfDays) {
        List<Jackpot> jackpotList = JackpotHandler.GetReserveJackpotListFromFile(context, numberOfDays);
        if (jackpotList.size() > 0) {
            if (jackpotList.size() < numberOfDays) {
                referenceBridgeView.ShowError("Vui lòng nạp dữ liệu XS Đặc biệt nhiều năm để xem " +
                        "được nhiều thông tin hơn!");
            } else {
                referenceBridgeView.ShowJackpotListInManyDays(jackpotList);
            }
        }
    }

    public void GetNumberOfDaysBeforeSDB(List<Jackpot> jackpotList) {
        int numberOfDays = 0;
        for (int i = 0; i < jackpotList.size(); i++) {
            numberOfDays++;
            if (jackpotList.get(i).getCouple().isSameDouble()) {
                break;
            }
        }
        if (numberOfDays != 0) {
            referenceBridgeView.ShowNumberOfDaysBeforeSDB(numberOfDays);
        }
    }

    public void GetBeatOfSameDouble(List<Jackpot> jackpotList) {
        List<Integer> beatList = JackpotStatistics.GetBeatOfSameDouble(jackpotList);
        referenceBridgeView.ShowBeatOfSameDouble(beatList);
    }

    public void GetSignInJackpot(List<Jackpot> jackpotList) {
        List<JackpotSign> jackpotSignList = JackpotStatistics.GetSignInJackpot(jackpotList);
        referenceBridgeView.ShowSignInJackpot(jackpotSignList);
    }

    public void GetNumberBeforeSameDoubleAppear(List<Jackpot> jackpotList) {
        List<NumberDouble> numberDoubleList = JackpotStatistics.GetNumberBeforeSameDoubleAppear(jackpotList);
        referenceBridgeView.ShowNumberBeforeSameDoubleAppear(numberDoubleList);
    }

    public void GetHeadForALongTime(List<Jackpot> jackpotList) {
        int runningDayNumber = jackpotList.size() < 150 ? jackpotList.size() : 150;
        List<NearestTime> nearestTimeList = JackpotStatistics
                .GetHeadAndTailInNearestTime(jackpotList.subList(0, runningDayNumber - 1));
        List<NearestTime> subNearestTimeList = new ArrayList<>();
        int count = 0;
        for (int i = 0; i < nearestTimeList.size(); i++) {
            if (nearestTimeList.get(i).getType().equals(Const.HEAD)) {
                count++;
                subNearestTimeList.add(nearestTimeList.get(i));
            }
            if (count > 2) break;
        }
        referenceBridgeView.ShowHeadForALongTime(runningDayNumber, subNearestTimeList);
    }

    public void GetTailForALongTime(List<Jackpot> jackpotList) {
        int runningDayNumber = jackpotList.size() < 150 ? jackpotList.size() : 150;
        List<NearestTime> nearestTimeList = JackpotStatistics
                .GetHeadAndTailInNearestTime(jackpotList.subList(0, runningDayNumber - 1));
        List<NearestTime> subNearestTimeList = new ArrayList<>();
        int count = 0;
        for (int i = 0; i < nearestTimeList.size(); i++) {
            if (nearestTimeList.get(i).getType().equals(Const.TAIL)) {
                count++;
                subNearestTimeList.add(nearestTimeList.get(i));
            }
            if (count > 2) break;
        }
        referenceBridgeView.ShowTailForALongTime(runningDayNumber, subNearestTimeList);
    }

    public void GetHeadAndTailFromPreviousDaySHead(List<Jackpot> jackpotList, int head) {
        int runningDayNumber = jackpotList.size() < 150 ? jackpotList.size() : 150;
        HeadTail headTail = JackpotStatistics.GetHeadAndTaiFromPreviousDaySCouple(jackpotList.subList(0,
                runningDayNumber - 1), head, 1);
        referenceBridgeView.ShowHeadAndTaiFromPreviousDaySHead(runningDayNumber, head, headTail);
    }

    public void GetHeadAndTailFromPreviousDaySTail(List<Jackpot> jackpotList, int tail) {
        int runningDayNumber = jackpotList.size() < 150 ? jackpotList.size() : 150;
        HeadTail headTail = JackpotStatistics.GetHeadAndTaiFromPreviousDaySCouple(jackpotList.subList(0,
                runningDayNumber - 1), tail, 2);
        referenceBridgeView.ShowHeadAndTaiFromPreviousDaySTail(runningDayNumber, tail, headTail);
    }
}
