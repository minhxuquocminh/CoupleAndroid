package com.example.couple.Custom.Handler.Display;

import com.example.couple.Base.View.Table.RowData;
import com.example.couple.Base.View.Table.TableData;
import com.example.couple.Custom.Const.Const;
import com.example.couple.Custom.Handler.Bridge.BCoupleBridgeHandler;
import com.example.couple.Model.Bridge.Bridge;
import com.example.couple.Model.Bridge.NumberSet.NumberSetHistory;
import com.example.couple.Model.Origin.Couple;
import com.example.couple.Model.Origin.Jackpot;
import com.example.couple.Model.Statistics.BCouple;
import com.example.couple.Model.Statistics.EventFrequency;
import com.example.couple.Model.Statistics.JackpotNextDay;

import java.util.Arrays;
import java.util.List;

public class TableDataConverter {

    public static TableData getBalanceCouple(List<Jackpot> jackpotList, int dayNumber, int picker) {
        return getBalanceCouple(jackpotList, dayNumber, picker, true);
    }

    public static TableData getBalanceCouple(List<Jackpot> jackpotList, int dayNumber,
                                             int picker, boolean showClColumn) {
        if (jackpotList.size() <= 2)
            return null;
        int viewSize = Math.min(dayNumber, jackpotList.size() - 2);
        TableData tableData = new TableData();
        tableData.createHeaders(showClColumn
                ? Arrays.asList("D/M", "B1", "B2", "B3", "B4", "+/-", "KQ", "CL")
                : Arrays.asList("D/M", "B1", "B2", "B3", "B4", "+/-", "KQ"));

        for (int i = viewSize; i >= -1; i--) {
            RowData rowData = new RowData();
            Couple first = jackpotList.get(i + 2).getCouple();
            Couple second = jackpotList.get(i + 1).getCouple();
            List<BCouple> balanceBCouples = BCoupleBridgeHandler.getBalanceCouples(
                    first.toBCouple(), second.toBCouple());
            rowData.addCell(i != -1 ? jackpotList.get(i).getDateBase().showDDMM("/")
                    : jackpotList.get(0).getDateBase().addDays(1).showDDMM("/"));
            for (BCouple bCouple : balanceBCouples) {
                rowData.addCell(bCouple.showDot());
            }
            rowData.addCell("{" + first.plus(second) + "," + first.sub(second) + "}");
            rowData.addCell(i != -1 ? jackpotList.get(i).getCouple().showDot()
                    : (picker != Const.EMPTY_VALUE ? (picker / 10) + "." + (picker % 10) : "x.x"));
            if (showClColumn) {
                rowData.addCell(i != -1 ? jackpotList.get(i).getCouple().getCL()
                        : (picker != Const.EMPTY_VALUE
                                ? ((picker / 10) % 2 == 0 ? "C" : "L") + ((picker % 10) % 2 == 0 ? "C" : "L")
                                : "xx"));
            }
            tableData.addRow(rowData);
        }
        return tableData;
    }

    public static TableData getJackpotNextDayTable(List<JackpotNextDay> jackpotNextDayList) {
        TableData tableData = new TableData();
        tableData.createHeaders(Arrays.asList("Ngày hôm trước", "Giải đặc biệt", "Giải đặc biệt", "Ngày hôm sau"));
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

    public static TableData getJackpotNextDayTable(List<JackpotNextDay> jackpotNextDayList,
                                                   boolean compactDate) {
        if (!compactDate) {
            return getJackpotNextDayTable(jackpotNextDayList);
        }

        TableData tableData = new TableData();
        tableData.createHeaders(Arrays.asList("Truoc", "DB", "DB", "Sau"));
        for (JackpotNextDay jackpotNextDay : jackpotNextDayList) {
            RowData rowData = new RowData();
            rowData.addCell(jackpotNextDay.getJackpotFirst().getDateBase().showDDMM("/"));
            rowData.addCell(jackpotNextDay.getJackpotFirst().getJackpot());
            rowData.addCell(jackpotNextDay.getJackpotSecond().getJackpot());
            rowData.addCell(jackpotNextDay.getJackpotSecond().getDateBase().showDDMM("/"));
            tableData.addRow(rowData);
        }
        return tableData;
    }

    public static TableData getEventFrequencyTable(List<EventFrequency> eventFrequencies) {
        TableData tableData = new TableData();
        tableData.createHeaders(Arrays.asList("Kiểu chạy", "Ngày ra"));
        for (EventFrequency eventFrequency : eventFrequencies) {
            RowData rowData = new RowData();
            rowData.addCell(eventFrequency.getName());
            rowData.addCell(eventFrequency.getDetailInfo());
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

    public static TableData getBridgeHistoryTable(List<Bridge> bridges) {
        TableData tableData = new TableData();
        tableData.createHeaders(Arrays.asList("Ngày", "Thông tin", "KQ"));
        for (Bridge bridge : bridges) {
            RowData rowData = new RowData();
            rowData.addCell(bridge.getJackpotHistory().getJackpot().getDateBase().showDDMM("/"));
            rowData.addCell(bridge.showCompactInfo().trim());
            rowData.addCell((bridge.isWin() ? "O" : "X") + " "
                    + bridge.getJackpotHistory().getJackpot().getCouple().show());
            tableData.addRow(rowData);
        }
        return tableData;
    }

}
