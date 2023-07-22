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
    void ShowError(String message);
    //
    void ShowJackpotList(List<Jackpot> jackpotList);
    void ShowTouchBridge(List<BSingle> touchList);
    void ShowSpecialTouchBridge(List<Integer> touchList);
    void ShowTouchThirdClawBridge(List<BSingle> BSingleList, int frame);
    //
    void ShowJackpotListThisYear(List<Jackpot> jackpotList);
    void ShowRareSameDoubleList(List<NearestTime> subNearestTimeList);
    void ShowCoupleDoNotAppearThisYear(List<Integer> numbers);
    //
    void ShowJackpotListLastYear(List<Jackpot> jackpotList);
    void ShowRareCoupleLastYear(List<Integer> noAppearanceList, List<Integer> oneAppearanceList);
    //
    void ShowLotteryList(List<Lottery> lotteryList);
    void ShowConnectedBridge(ConnectedBridge connectedBridge);
    void ShowThirdClawBridge(List<ClawSupport> clawSupportList);
    void ShowTriadBridge(List<Set> triadSetList, List<Set> cancelSetList);
    void ShowSignInLottery(List<Integer> numberList);
    //
    void ShowJackpotListInManyDays(List<Jackpot> jackpotList);
    void ShowNumberOfDaysBeforeSDB(int numberOfDays);
    void ShowBeatOfSameDouble(List<Integer> beatList);
    void ShowSignInJackpot(List<JackpotSign> jackpotSignList);
    void ShowNumberBeforeSameDoubleAppear(List<NumberDouble> numberDoubleList);
    void ShowHeadForALongTime(int runningDayNumber, List<NearestTime> subNearestTimeList);
    void ShowTailForALongTime(int runningDayNumber, List<NearestTime> subNearestTimeList);
    void ShowHeadAndTaiFromPreviousDaySHead(int runningDayNumber, int head, HeadTail headTail);
    void ShowHeadAndTaiFromPreviousDaySTail(int runningDayNumber, int tail, HeadTail headTail);
}
