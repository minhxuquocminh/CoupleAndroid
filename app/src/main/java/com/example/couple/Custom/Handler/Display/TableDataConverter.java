package com.example.couple.Custom.Handler.Display;

import com.example.couple.Base.View.Table.RowData;
import com.example.couple.Base.View.Table.TableData;
import com.example.couple.Custom.Const.Const;
import com.example.couple.Custom.Handler.Bridge.BCoupleBridgeHandler;
import com.example.couple.Model.Bridge.NumberSet.NumberSetHistory;
import com.example.couple.Model.Origin.Couple;
import com.example.couple.Model.Origin.Jackpot;
import com.example.couple.Model.Statistics.BCouple;
import com.example.couple.Model.Statistics.JackpotNextDay;

import java.util.Arrays;
import java.util.List;

public class TableDataConverter {

    public static TableData getBalanceCouple(List<Jackpot> jackpotList, int dayNumber, int picker) {
        if (jackpotList.size() <= 2) return null;
        int viewSize = Math.min(dayNumber, jackpotList.size() - 2);
        TableData tableData = new TableData();
        tableData.createHeaders(Arrays.asList("D/M", "B1", "B2", "B3", "B4", "+/-", "KQ", "CL"));

        for (int i = viewSize; i >= -1; i--) {
            RowData rowData = new RowData();
            Couple first = jackpotList.get(i + 2).getCouple();
            Couple second = jackpotList.get(i + 1).getCouple();
            List<BCouple> balanceBCouples = BCoupleBridgeHandler.getBalanceCouples(
                    first.toBCouple(), second.toBCouple());
            rowData.addCell(i != -1 ? jackpotList.get(i).getDateBase().showDDMM("/") :
                    jackpotList.get(0).getDateBase().addDays(1).showDDMM("/"));
            for (BCouple bCouple : balanceBCouples) {
                rowData.addCell(bCouple.showDot());
            }
            rowData.addCell("{" + first.plus(second) + "," + first.sub(second) + "}");
            rowData.addCell(i != -1 ? jackpotList.get(i).getCouple().showDot() :
                    (picker != Const.EMPTY_VALUE ? (picker / 10) + "." + (picker % 10) : "x.x"));
            rowData.addCell(i != -1 ? jackpotList.get(i).getCouple().getCL() :
                    (picker != Const.EMPTY_VALUE ? ((picker / 10) % 2 == 0 ? "C" : "L") + ((picker % 10) % 2 == 0 ? "C" : "L") : "xx"));
            tableData.addRow(rowData);
        }
        return tableData;
    }

    public static TableData getJackpotNextDayTable(List<JackpotNextDay> jackpotNextDayList) {
        TableData tableData = new TableData();
        tableData.createHeaders(Arrays.asList("Ngày hôm trước", "Giải đặc biệt", "Giải đặc biệt", "Ngày hôm trước"));
        for (JackpotNextDay jackpotNextDay : jackpotNextDayList) {
            RowData rowData = new RowData();
            rowData.addCell(jackpotNextDay.getJackpotFirst().getDateBase().showFullChars());
            rowData.addCell(jackpotNextDay.getJackpotFirst().getJackpot());
            rowData.addCell(jackpotNextDay.getJackpotSecond().getJackpot());
            rowData.addCell(jackpotNextDay.getJackpotSecond().getDateBase().showFullChars());
            tableData.addRow(rowData);
        }
        return tableData;
    }

    public static TableData getNumberSetHistoryTable(String typeName, List<NumberSetHistory> numberSetHistories) {
        TableData tableData = new TableData();
        tableData.createHeaders(Arrays.asList(typeName, "Xuất hiện", "Chưa về"));
        for (NumberSetHistory history : numberSetHistories) {
            RowData rowData = new RowData();
            rowData.addCell(history.getNumberSet().getName());
            rowData.addCell(history.getAppearanceTimes() + " lần");
            rowData.addCell(history.getDayNumberBefore() + " ngày");
            tableData.addRow(rowData);
        }
        return tableData;
    }

}
