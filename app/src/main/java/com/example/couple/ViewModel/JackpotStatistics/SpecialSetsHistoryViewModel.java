package com.example.couple.ViewModel.JackpotStatistics;

import android.content.Context;

import com.example.couple.Custom.Const.Const;
import com.example.couple.Custom.Const.TimeInfo;
import com.example.couple.Custom.Handler.JackpotBridgeHandler;
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

    public void GetSortSpecialSetsHistory(List<Jackpot> jackpotList, TimeBase timeBaseNextDay) {
        List<SpecialSetHistory> historyList = new ArrayList<>();
        // for cycle
        for (int i = 0; i < TimeInfo.EARTHLY_BRANCHES.size(); i++) {
            SpecialSetHistory branch = JackpotBridgeHandler.GetSpecialSetHistory(jackpotList,
                    TimeInfo.EARTHLY_BRANCHES.get(i) + " " + i, (new Branch(i)).getIntYearCycles());
            historyList.add(branch);
        }
        Branch nextDayBranch = timeBaseNextDay.getDateCycle().getDay().getBranch();
        for (int i = -6; i < 6; i++) {
            String specialSetName = i < 0 ? "KC" + i : "KC+" + i;
            SpecialSetHistory branch = JackpotBridgeHandler
                    .GetSpecialSetHistoryByBranchDistance(jackpotList, specialSetName, i, nextDayBranch);
            historyList.add(branch);
        }

        // others
        for (int i = 0; i < 10; i++) {
            SpecialSetHistory head = JackpotBridgeHandler.GetSpecialSetHistory(jackpotList,
                    Const.HEAD + " " + i, NumberArrayHandler.getHeads(i));
            historyList.add(head);
            SpecialSetHistory tail = JackpotBridgeHandler.GetSpecialSetHistory(jackpotList,
                    Const.TAIL + " " + i, NumberArrayHandler.getTails(i));
            historyList.add(tail);
            SpecialSetHistory sum = JackpotBridgeHandler.GetSpecialSetHistory(jackpotList,
                    Const.SUM + " " + i, NumberArrayHandler.getSums(i));
            historyList.add(sum);
        }
        for (int i : Const.SMALL_SETS) {
            SpecialSetHistory set = JackpotBridgeHandler.GetSpecialSetHistory(jackpotList,
                    Const.SET + " " + i, (new Set(i)).getSetsDetail());
            historyList.add(set);
        }
        SpecialSetHistory doubleHistory = JackpotBridgeHandler
                .GetSpecialSetHistory(jackpotList, Const.DOUBLE, Const.DOUBLE_SET);
        historyList.add(doubleHistory);
        SpecialSetHistory deviatedHistory = JackpotBridgeHandler
                .GetSpecialSetHistory(jackpotList, Const.DEVIATED_DOUBLE, Const.DEVIATED_DOUBLE_SET);
        historyList.add(deviatedHistory);
        SpecialSetHistory nearHistory = JackpotBridgeHandler
                .GetSpecialSetHistory(jackpotList, Const.NEAR_DOUBLE, Const.NEAR_DOUBLE_SET);
        historyList.add(nearHistory);
        Collections.sort(historyList, (x, y) -> y.getDayNumberBefore() - x.getDayNumberBefore());
        if (!historyList.isEmpty()) {
            specialSetsHistoryView.ShowSpecialSetsHistory(historyList);
        }
    }

    public void GetSpecialSetsHistory(List<Jackpot> jackpotList, TimeBase timeBaseNextDay) {
        List<SpecialSetHistory> historyList = new ArrayList<>();
        // for cycle
        List<SpecialSetHistory> branches1 = new ArrayList<>();
        for (int i = 0; i < TimeInfo.EARTHLY_BRANCHES.size(); i++) {
            SpecialSetHistory branch = JackpotBridgeHandler.GetSpecialSetHistory(jackpotList,
                    TimeInfo.EARTHLY_BRANCHES.get(i) + " " + i, (new Branch(i)).getIntYearCycles());
            branches1.add(branch);
        }
        Collections.sort(branches1, (x, y) -> y.getDayNumberBefore() - x.getDayNumberBefore());
        historyList.addAll(branches1);
        
        List<SpecialSetHistory> branches2 = new ArrayList<>();
        Branch nextDayBranch = timeBaseNextDay.getDateCycle().getDay().getBranch();
        for (int i = -6; i < 6; i++) {
            String specialSetName = i < 0 ? "KC" + i : "KC+" + i;
            SpecialSetHistory branch = JackpotBridgeHandler
                    .GetSpecialSetHistoryByBranchDistance(jackpotList, specialSetName, i, nextDayBranch);
            branches2.add(branch);
        }
        Collections.sort(branches2, (x, y) -> y.getDayNumberBefore() - x.getDayNumberBefore());
        historyList.addAll(branches2);

        // others
        List<SpecialSetHistory> headtails = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            SpecialSetHistory head = JackpotBridgeHandler.GetSpecialSetHistory(jackpotList,
                    Const.HEAD + " " + i, NumberArrayHandler.getHeads(i));
            headtails.add(head);
            SpecialSetHistory tail = JackpotBridgeHandler.GetSpecialSetHistory(jackpotList,
                    Const.TAIL + " " + i, NumberArrayHandler.getTails(i));
            headtails.add(tail);
        }
        Collections.sort(headtails, (x, y) -> y.getDayNumberBefore() - x.getDayNumberBefore());
        historyList.addAll(headtails);

        List<SpecialSetHistory> sums = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            SpecialSetHistory sum = JackpotBridgeHandler.GetSpecialSetHistory(jackpotList,
                    Const.SUM + " " + i, NumberArrayHandler.getSums(i));
            sums.add(sum);
        }
        Collections.sort(sums, (x, y) -> y.getDayNumberBefore() - x.getDayNumberBefore());
        historyList.addAll(sums);

        List<SpecialSetHistory> sets = new ArrayList<>();
        for (int i : Const.SMALL_SETS) {
            SpecialSetHistory set = JackpotBridgeHandler.GetSpecialSetHistory(jackpotList,
                    Const.SET + " " + i, (new Set(i)).getSetsDetail());
            sets.add(set);
        }
        Collections.sort(sets, (x, y) -> y.getDayNumberBefore() - x.getDayNumberBefore());
        historyList.addAll(sets);

        List<SpecialSetHistory> others = new ArrayList<>();
        SpecialSetHistory doubleHistory = JackpotBridgeHandler
                .GetSpecialSetHistory(jackpotList, Const.DOUBLE, Const.DOUBLE_SET);
        others.add(doubleHistory);
        SpecialSetHistory deviatedHistory = JackpotBridgeHandler
                .GetSpecialSetHistory(jackpotList, Const.DEVIATED_DOUBLE, Const.DEVIATED_DOUBLE_SET);
        others.add(deviatedHistory);
        SpecialSetHistory nearHistory = JackpotBridgeHandler
                .GetSpecialSetHistory(jackpotList, Const.NEAR_DOUBLE, Const.NEAR_DOUBLE_SET);
        others.add(nearHistory);
        Collections.sort(others, (x, y) -> y.getDayNumberBefore() - x.getDayNumberBefore());
        historyList.addAll(others);

        if (!historyList.isEmpty()) {
            specialSetsHistoryView.ShowSpecialSetsHistory(historyList);
        }
    }
}
