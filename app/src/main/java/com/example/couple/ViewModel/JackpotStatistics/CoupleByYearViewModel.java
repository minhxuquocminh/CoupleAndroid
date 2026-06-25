package com.example.couple.ViewModel.JackpotStatistics;

import android.content.Context;

import com.example.couple.Custom.Const.Const;
import com.example.couple.Custom.Handler.JackpotHandler;
import com.example.couple.Custom.Handler.NumberArrayHandler;
import com.example.couple.Custom.Handler.Statistics.JackpotStatistics;
import com.example.couple.Model.Bridge.NumberSet.SetBase;
import com.example.couple.Model.Bridge.NumberSet.SpecialSet;
import com.example.couple.View.JackpotStatistics.YearlyJackpotStatisticsView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class CoupleByYearViewModel {
    public static final int TYPE_NUMBER = 0;
    public static final int TYPE_DOUBLE = 1;
    public static final int TYPE_SET = 2;
    public static final int TYPE_HEAD = 3;
    public static final int TYPE_TAIL = 4;
    public static final int TYPE_SUM = 5;

    public static final int SORT_NUMBER = 0;
    public static final int SORT_NEAREST_YEAR = 1;
    public static final int SORT_TOTAL = 2;

    YearlyJackpotStatisticsView view;
    Context context;

    public CoupleByYearViewModel(YearlyJackpotStatisticsView view, Context context) {
        this.view = view;
        this.context = context;
    }

    public void getCoupleCountingTable(int years, String value, int sortType, int type) {
        years = Math.min(years, 7);
        List<Integer> invalidYears = JackpotHandler.getInvalidJackpotDataYears(context, years);
        if (!invalidYears.isEmpty()) {
            view.showMessage("D\u1eef li\u1ec7u XSDB b\u1ecb thi\u1ebfu ho\u1eb7c ch\u01b0a \u0111\u1ee7 \u1edf c\u00e1c n\u0103m: " + invalidYears);
            return;
        }

        Map<Integer, String[][]> matrixByYears = JackpotHandler.getJackpotMatrixByYears(context, years);
        Map<Integer, int[]> counterByYears = JackpotStatistics.getCounterByYears(matrixByYears);
        TableResult result = getTableResult(counterByYears, value, type);
        if (result == null) return;

        int col = counterByYears.size() + 2;
        if (sortType == SORT_NEAREST_YEAR) {
            result.matrix = JackpotStatistics.sortMatrixByColumn(result.matrix, col - 2);
        } else if (sortType == SORT_TOTAL) {
            result.matrix = JackpotStatistics.sortMatrixByColumn(result.matrix, col - 1);
        }
        view.showCoupleCountingTable(result.matrix, result.row, col);
    }

    private TableResult getTableResult(Map<Integer, int[]> counterByYears, String value, int type) {
        switch (type) {
            case TYPE_DOUBLE:
                return new TableResult(
                        JackpotStatistics.getCounterMatrixByYears(counterByYears, SpecialSet.DOUBLE.values),
                        SpecialSet.DOUBLE.values.size() + 1);
            case TYPE_SET:
                return getSetResult(counterByYears, value);
            case TYPE_HEAD:
                return getSingleDigitResult(counterByYears, value, "\u0110\u1ea7u", GroupType.HEAD);
            case TYPE_TAIL:
                return getSingleDigitResult(counterByYears, value, "\u0110u\u00f4i", GroupType.TAIL);
            case TYPE_SUM:
                return getSingleDigitResult(counterByYears, value, "T\u1ed5ng", GroupType.SUM);
            case TYPE_NUMBER:
            default:
                return getNumberResult(counterByYears, value);
        }
    }

    private TableResult getNumberResult(Map<Integer, int[]> counterByYears, String value) {
        if (value.isEmpty()) {
            return new TableResult(JackpotStatistics.getCounterMatrixByYears(counterByYears), 101);
        }
        Integer number = parseNumber(value, 0, 99, "S\u1ed1 \u0111\u1ec1 ph\u1ea3i t\u1eeb 00 \u0111\u1ebfn 99.");
        if (number == null) return null;
        List<Integer> numbers = Collections.singletonList(number);
        return new TableResult(JackpotStatistics.getCounterMatrixByYears(counterByYears, numbers), 2);
    }

    private TableResult getSetResult(Map<Integer, int[]> counterByYears, String value) {
        List<String> names = new ArrayList<>();
        List<List<Integer>> groups = new ArrayList<>();
        if (value.isEmpty()) {
            for (Integer set : Const.SMALL_SETS_NOT_DOUBLE) {
                names.add("B\u1ed9 " + showCouple(set));
                groups.add(SetBase.getFrom(set).getSetsDetail());
            }
            return new TableResult(JackpotStatistics.getCounterMatrixByYears(counterByYears, names, groups),
                    Const.SMALL_SETS_NOT_DOUBLE.size() + 1);
        }

        Integer set = parseNumber(value, 0, 99, "B\u1ed9 ph\u1ea3i t\u1eeb 00 \u0111\u1ebfn 99.");
        if (set == null) return null;
        List<Integer> numbers = SetBase.getFrom(set).getSetsDetail();
        return new TableResult(JackpotStatistics.getCounterMatrixByYears(counterByYears, numbers),
                numbers.size() + 1);
    }

    private TableResult getSingleDigitResult(Map<Integer, int[]> counterByYears, String value,
                                             String label, GroupType type) {
        List<String> names = new ArrayList<>();
        List<List<Integer>> groups = new ArrayList<>();
        if (value.isEmpty()) {
            for (int i = 0; i < 10; i++) {
                names.add(label + " " + i);
                groups.add(getNumbers(i, type));
            }
            return new TableResult(JackpotStatistics.getCounterMatrixByYears(counterByYears, names, groups), 11);
        }

        Integer digit = parseNumber(value, 0, 9, label + " ph\u1ea3i t\u1eeb 0 \u0111\u1ebfn 9.");
        if (digit == null) return null;
        List<Integer> numbers = getNumbers(digit, type);
        return new TableResult(JackpotStatistics.getCounterMatrixByYears(counterByYears, numbers),
                numbers.size() + 1);
    }

    private List<Integer> getNumbers(int digit, GroupType type) {
        switch (type) {
            case HEAD:
                return NumberArrayHandler.getHeads(digit);
            case TAIL:
                return NumberArrayHandler.getTails(digit);
            case SUM:
            default:
                return NumberArrayHandler.getSums(digit);
        }
    }

    private Integer parseNumber(String value, int min, int max, String errorMessage) {
        try {
            int number = Integer.parseInt(value);
            if (number < min || number > max) {
                view.showMessage(errorMessage);
                return null;
            }
            return number;
        } catch (NumberFormatException e) {
            view.showMessage(errorMessage);
            return null;
        }
    }

    private String showCouple(int couple) {
        return couple < 10 ? "0" + couple : couple + "";
    }

    private enum GroupType {
        HEAD,
        TAIL,
        SUM
    }

    private static class TableResult {
        String[][] matrix;
        int row;

        TableResult(String[][] matrix, int row) {
            this.matrix = matrix;
            this.row = row;
        }
    }
}
