package com.example.couple.View.Bridge;

import com.example.couple.Model.Bridge.NumberSet.SetBase;
import com.example.couple.Model.Bridge.Touch.ConnectedBridge;
import com.example.couple.Model.Bridge.Double.JackpotSign;
import com.example.couple.Model.Bridge.LongBeat.NearestTime;
import com.example.couple.Model.Bridge.Double.NumberDouble;
import com.example.couple.Model.Origin.Jackpot;
import com.example.couple.Model.Origin.Lottery;
import com.example.couple.Model.Bridge.Connected.ClawSupport;
import com.example.couple.Model.Bridge.TriadClaw.Single;

import java.util.List;

public interface ReferenceBridgeView {
    void showMessage(String message);
    //
    void showJackpotList(List<Jackpot> jackpotList);
    void showTouchThirdClawBridge(List<Single> singles, int frame);
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
    void showTriadBridge(List<SetBase> triadSetBaseList, List<SetBase> cancelSetBaseList);
    void showSignInLottery(List<Integer> numberList);
    //
    void showJackpotListInManyDays(List<Jackpot> jackpotList);
    void showNumberOfDaysBeforeSDB(int numberOfDays);
    void showBeatOfSameDouble(List<Integer> beatList);
    void showSignInJackpot(List<JackpotSign> jackpotSignList);
    void showNumberBeforeSameDoubleAppear(List<NumberDouble> numberDoubleList);
    void showHeadForALongTime(int runningDayNumber, List<NearestTime> subNearestTimeList);
    void showTailForALongTime(int runningDayNumber, List<NearestTime> subNearestTimeList);
}
