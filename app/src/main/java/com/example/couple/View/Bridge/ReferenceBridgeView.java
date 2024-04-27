package com.example.couple.View.Bridge;

import com.example.couple.Model.Support.ClawSupport;
import com.example.couple.Model.Bridge.Single.ConnectedBridge;
import com.example.couple.Model.Display.HeadTail;
import com.example.couple.Model.Display.JackpotSign;
import com.example.couple.Model.Display.NearestTime;
import com.example.couple.Model.Display.NumberDouble;
import com.example.couple.Model.Display.Set;
import com.example.couple.Model.Display.BSingle;
import com.example.couple.Model.Origin.Jackpot;
import com.example.couple.Model.Origin.Lottery;

import java.util.List;

public interface ReferenceBridgeView {
    void showMessage(String message);
    //
    void showJackpotList(List<Jackpot> jackpotList);
    void showTouchBridge(List<BSingle> touchList);
    void showSpecialTouchBridge(List<Integer> touchList);
    void showTouchThirdClawBridge(List<BSingle> BSingleList, int frame);
    //
    void showJackpotListThisYear(List<Jackpot> jackpotList);
    void showRareSameDoubleList(List<NearestTime> subNearestTimeList);
    void showCoupleDoNotAppearThisYear(List<Integer> numbers);
    //
    void showJackpotListLastYear(List<Jackpot> jackpotList);
    void showRareCoupleLastYear(List<Integer> noAppearanceList, List<Integer> oneAppearanceList);
    //
    void showLotteryList(List<Lottery> lotteryList);
    void showConnectedBridge(ConnectedBridge connectedBridge);
    void showThirdClawBridge(List<ClawSupport> clawSupportList);
    void showTriadBridge(List<Set> triadSetList, List<Set> cancelSetList);
    void showSignInLottery(List<Integer> numberList);
    //
    void showJackpotListInManyDays(List<Jackpot> jackpotList);
    void showNumberOfDaysBeforeSDB(int numberOfDays);
    void showBeatOfSameDouble(List<Integer> beatList);
    void showSignInJackpot(List<JackpotSign> jackpotSignList);
    void showNumberBeforeSameDoubleAppear(List<NumberDouble> numberDoubleList);
    void showHeadForALongTime(int runningDayNumber, List<NearestTime> subNearestTimeList);
    void showTailForALongTime(int runningDayNumber, List<NearestTime> subNearestTimeList);
    void showHeadAndTaiFromPreviousDaySHead(int runningDayNumber, int head, HeadTail headTail);
    void showHeadAndTaiFromPreviousDaySTail(int runningDayNumber, int tail, HeadTail headTail);
}
