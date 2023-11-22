package com.example.couple.ViewModel.JackpotStatistics;

import android.content.Context;

import com.example.couple.Custom.Const.Const;
import com.example.couple.Custom.Const.TimeInfo;
import com.example.couple.Custom.Handler.Bridge.CycleBridgeHandler;
import com.example.couple.Custom.Handler.Bridge.OtherBridgeHandler;
import com.example.couple.Custom.Handler.JackpotHandler;
import com.example.couple.Custom.Handler.NumberArrayHandler;
import com.example.couple.Custom.Handler.TimeHandler;
import com.example.couple.Model.Display.Set;
import com.example.couple.Model.Display.SpecialSetHistory;
import com.example.couple.Model.Origin.Jackpot;
import com.example.couple.Model.Time.Cycle.Branch;
import com.example.couple.Model.Time.TimeBase;
import com.example.couple.View.BridgeHistory.SpecialSetsHistoryView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class SpecialSetsHistoryViewModel {
    Context context;
    SpecialSetsHistoryView specialSetsHistoryView;

    public void GetJackpotListAndTimeBaseData(int numberOfDays) {
        List<Jackpot> jackpotList = JackpotHandler.GetReserveJackpotListFromFile(context, numberOfDays);
        TimeBase timeBaseNextDay = TimeHandler.getTimeBaseNextDay(context);
        specialSetsHistoryView.ShowJackpotListAndTimeBaseData(jackpotList, timeBaseNextDay);
    }

    public void GetSpecialSetsHistory(List<Jackpot> jackpotList, TimeBase timeBaseNextDay) {
        List<SpecialSetHistory> historyList = new ArrayList<>();
        // for cycle
        List<SpecialSetHistory> branches1 = new ArrayList<>();
        for (int i = 0; i < TimeInfo.EARTHLY_BRANCHES.size(); i++) {
            SpecialSetHistory branch = OtherBridgeHandler.GetSpecialSetHistory(jackpotList,
                    TimeInfo.EARTHLY_BRANCHES.get(i) + " " + i, (new Branch(i)).getIntYearCycles());
            branches1.add(branch);
        }
        Collections.sort(branches1, (x, y) -> y.getDayNumberBefore() - x.getDayNumberBefore());
        historyList.addAll(branches1);

        List<SpecialSetHistory> branches2 = new ArrayList<>();
        Branch nextDayBranch = timeBaseNextDay.getDateCycle().getDay().getBranch();
        for (int i = -6; i < 6; i++) {
            String specialSetName = i < 0 ? "KC" + i : "KC+" + i;
            SpecialSetHistory branch = CycleBridgeHandler
                    .GetBranchDistanceHistory(jackpotList, specialSetName, i, nextDayBranch);
            branches2.add(branch);
        }
        Collections.sort(branches2, (x, y) -> y.getDayNumberBefore() - x.getDayNumberBefore());
        historyList.addAll(branches2);

        List<SpecialSetHistory> branches3 = new ArrayList<>();
        for (int i = -6; i < 6; i++) {
            String specialSetName = i < 0 ? "TS" + i : "TS+" + i;
            SpecialSetHistory branch = CycleBridgeHandler
                    .GetBranchDistanceHistoryEach2Days(jackpotList, specialSetName, i);
            branches3.add(branch);
        }
        Collections.sort(branches3, (x, y) -> y.getDayNumberBefore() - x.getDayNumberBefore());
        historyList.addAll(branches3);

        SpecialSetHistory branch4 = CycleBridgeHandler
                .GetPositive12BranchHistory(jackpotList, "Chi 12 dương");
        historyList.add(branch4);

        SpecialSetHistory branch5 = CycleBridgeHandler
                .GetNegative12BranchHistory(jackpotList, "Chi 12 âm");
        historyList.add(branch5);

        SpecialSetHistory branch6 = CycleBridgeHandler
                .GetPositive13BranchHistory(jackpotList, "Chi 13 dương");
        historyList.add(branch6);

        SpecialSetHistory branch7 = CycleBridgeHandler
                .GetNegative13BranchHistory(jackpotList, "Chi 13 âm");
        historyList.add(branch7);

        // others
        List<SpecialSetHistory> headtails = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            SpecialSetHistory head = OtherBridgeHandler.GetSpecialSetHistory(jackpotList,
                    Const.HEAD + " " + i, NumberArrayHandler.getHeads(i));
            headtails.add(head);
            SpecialSetHistory tail = OtherBridgeHandler.GetSpecialSetHistory(jackpotList,
                    Const.TAIL + " " + i, NumberArrayHandler.getTails(i));
            headtails.add(tail);
        }
        Collections.sort(headtails, (x, y) -> y.getDayNumberBefore() - x.getDayNumberBefore());
        historyList.addAll(headtails);

        List<SpecialSetHistory> sums = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            SpecialSetHistory sum = OtherBridgeHandler.GetSpecialSetHistory(jackpotList,
                    Const.SUM + " " + i, NumberArrayHandler.getSums(i));
            sums.add(sum);
        }
        Collections.sort(sums, (x, y) -> y.getDayNumberBefore() - x.getDayNumberBefore());
        historyList.addAll(sums);

        List<SpecialSetHistory> sets = new ArrayList<>();
        for (int i : Const.SMALL_SETS_NOT_DOUBLE) {
            SpecialSetHistory set = OtherBridgeHandler.GetSpecialSetHistory(jackpotList,
                    Const.SET + " " + i, (new Set(i)).getSetsDetail());
            sets.add(set);
        }
        Collections.sort(sets, (x, y) -> y.getDayNumberBefore() - x.getDayNumberBefore());
        historyList.addAll(sets);

        List<SpecialSetHistory> others = new ArrayList<>();
        SpecialSetHistory doubleHistory = OtherBridgeHandler
                .GetSpecialSetHistory(jackpotList, Const.DOUBLE, Const.DOUBLE_SET);
        others.add(doubleHistory);
        SpecialSetHistory deviatedHistory = OtherBridgeHandler
                .GetSpecialSetHistory(jackpotList, Const.POSITIVE_DOUBLE_SET_NAME, Const.POSITIVE_DOUBLE_SET);
        others.add(deviatedHistory);
        SpecialSetHistory nearIncreaseHistory = OtherBridgeHandler
                .GetSpecialSetHistory(jackpotList, Const.NEAR_DOUBLE_INCREASE, Const.NEAR_DOUBLE_INCREASE_SET);
        others.add(nearIncreaseHistory);
        SpecialSetHistory nearDecreaseHistory = OtherBridgeHandler
                .GetSpecialSetHistory(jackpotList, Const.NEAR_DOUBLE_DECREASE, Const.NEAR_DOUBLE_DECREASE_SET);
        others.add(nearDecreaseHistory);
        Collections.sort(others, (x, y) -> y.getDayNumberBefore() - x.getDayNumberBefore());
        historyList.addAll(others);

        if (!historyList.isEmpty()) {
            specialSetsHistoryView.ShowSpecialSetsHistory(historyList);
        }
    }
}
