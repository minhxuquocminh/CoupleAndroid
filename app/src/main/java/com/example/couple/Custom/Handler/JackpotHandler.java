package com.example.couple.Custom.Handler;

import android.content.Context;

import com.example.couple.Base.Handler.IOFileBase;
import com.example.couple.Custom.Const.Const;
import com.example.couple.Custom.Const.TimeInfo;
import com.example.couple.Custom.Statistics.JackpotStatistics;
import com.example.couple.Model.Origin.Jackpot;
import com.example.couple.Model.Time.DateBase;

import java.util.ArrayList;
import java.util.List;

public class JackpotHandler {

    public static DateBase getLastDate(Context context) {
        List<Jackpot> jackpotList = getReserveJackpotListFromFile(context, 1);
        if (jackpotList.isEmpty()) return new DateBase();
        return jackpotList.get(0).getDateBase();
    }

    public static List<Jackpot> getJackpotListManyYears(Context context, int numberOfYears) {
        int[] startAndEndYearFile = JackpotStatistics.getStartAndEndYearFile(context);
        if (startAndEndYearFile == null) return new ArrayList<>();
        List<Jackpot> jackpots = new ArrayList<>();

        int startYear = startAndEndYearFile[0];
        int endYear = startAndEndYearFile[1];
        int lengthYear_file = endYear - startYear + 1;
        int lengthYear = Math.min(numberOfYears, lengthYear_file);

        for (int i = endYear - lengthYear + 1; i <= endYear; i++) {
            String data = IOFileBase.readDataFromFile(context, "jackpot" + i + ".txt");
            String[][] matrix = getJackpotFlagMatrix(data,
                    TimeInfo.DAY_OF_MONTH, TimeInfo.MONTH_OF_YEAR, i);
            List<Jackpot> jackpotList = getJackpotList(matrix,
                    TimeInfo.DAY_OF_MONTH, TimeInfo.MONTH_OF_YEAR, i);
            jackpots.addAll(jackpotList);
        }
        return jackpots;
    }

    // lấy tối đa 2 năm tức 365 * 2 ngày
    public static List<Jackpot> getReserveJackpotListFromFile(Context context, int numberOfDays) {
        String data = IOFileBase.readDataFromFile(context,
                "jackpot" + TimeInfo.CURRENT_YEAR + ".txt");
        if (data.isEmpty()) return new ArrayList<>();
        String[][] matrix = getJackpotFlagMatrix(data,
                TimeInfo.DAY_OF_MONTH, TimeInfo.MONTH_OF_YEAR, TimeInfo.CURRENT_YEAR);
        List<Jackpot> jackpotList = getReverseJackpotList(matrix,
                TimeInfo.DAY_OF_MONTH, TimeInfo.CURRENT_MONTH, TimeInfo.CURRENT_YEAR);
        int sizeOfJackpotList = jackpotList.size();
        if (jackpotList.size() >= numberOfDays) return jackpotList.subList(0, numberOfDays);
        String lastData = IOFileBase.readDataFromFile(context,
                "jackpot" + (TimeInfo.CURRENT_YEAR - 1) + ".txt");
        if (!lastData.isEmpty()) {
            String[][] lastMatrix = getJackpotFlagMatrix(lastData,
                    TimeInfo.DAY_OF_MONTH, TimeInfo.MONTH_OF_YEAR, TimeInfo.CURRENT_YEAR - 1);
            List<Jackpot> lastJackpotList = getReverseJackpotList(lastMatrix,
                    TimeInfo.DAY_OF_MONTH, TimeInfo.MONTH_OF_YEAR, TimeInfo.CURRENT_YEAR - 1);
            // vd lấy 5 ngày mà jackpot có 3 ngày => last_jackpot lấy từ 0 đến 5-3-1
            int lastNumberOfDays = Math.min(lastJackpotList.size(), (numberOfDays - sizeOfJackpotList));
            jackpotList.addAll(lastJackpotList.subList(0, lastNumberOfDays));
        }
        return jackpotList;
    }

    // lấy tối đa 2 năm tức 365 * 2 ngày, all tức là lấy cả ngày ko quay
    public static List<Jackpot> getAllReserveJackpotListFromFile(Context context, int numberOfDays) {
        String data = IOFileBase.readDataFromFile(context,
                "jackpot" + TimeInfo.CURRENT_YEAR + ".txt");
        if (data.isEmpty()) return new ArrayList<>();
        String[][] matrix = getJackpotFlagMatrix(data,
                TimeInfo.DAY_OF_MONTH, TimeInfo.MONTH_OF_YEAR, TimeInfo.CURRENT_YEAR);
        List<Jackpot> jackpotList = getAllReverseJackpotList(matrix,
                TimeInfo.DAY_OF_MONTH, TimeInfo.CURRENT_MONTH, TimeInfo.CURRENT_YEAR);
        int sizeOfJackpotList = jackpotList.size();
        if (jackpotList.size() >= numberOfDays) return jackpotList.subList(0, numberOfDays);
        String lastData = IOFileBase.readDataFromFile(context,
                "jackpot" + (TimeInfo.CURRENT_YEAR - 1) + ".txt");
        if (!lastData.isEmpty()) {
            String[][] lastMatrix = getJackpotFlagMatrix(lastData,
                    TimeInfo.DAY_OF_MONTH, TimeInfo.MONTH_OF_YEAR, TimeInfo.CURRENT_YEAR - 1);
            List<Jackpot> lastJackpotList = getAllReverseJackpotList(lastMatrix,
                    TimeInfo.DAY_OF_MONTH, TimeInfo.MONTH_OF_YEAR, TimeInfo.CURRENT_YEAR - 1);
            // vd lấy 5 ngày mà jackpot có 3 ngày => last_jackpot lấy từ 0 đến 5-3-1
            int lastNumberOfDays = Math.min(lastJackpotList.size(), (numberOfDays - sizeOfJackpotList));
            jackpotList.addAll(lastJackpotList.subList(0, lastNumberOfDays));
        }
        return jackpotList;
    }


    public static List<Jackpot> getReserveJackpotListByYear(Context context, int year) {
        String data = IOFileBase.readDataFromFile(context, "jackpot" + year + ".txt");
        if (data.isEmpty()) return new ArrayList<>();
        String[][] matrix = getJackpotFlagMatrix(data,
                TimeInfo.DAY_OF_MONTH, TimeInfo.MONTH_OF_YEAR, year);
        return getReverseJackpotList(matrix,
                TimeInfo.DAY_OF_MONTH, TimeInfo.CURRENT_MONTH, year);
    }

    private static List<Jackpot> getAllReverseJackpotList(String[][] matrix, int m, int n, int year) {
        if (matrix == null) return new ArrayList<>();
        List<Jackpot> listJackpots = new ArrayList<>();
        for (int j = n - 1; j >= 0; j--) {
            for (int i = m - 1; i >= 0; i--) {
                String cursor = matrix[i][j];
                if (!cursor.equals(Const.INVALID_DATE) && !cursor.equals(Const.FUTURE_DAY)) {
                    String jackpot = cursor.equals(Const.DAY_OFF) ? Const.EMPTY_JACKPOT : cursor;
                    DateBase dateBase = new DateBase(i + 1, j + 1, year);
                    listJackpots.add(new Jackpot(jackpot, dateBase));
                }
            }
        }
        return listJackpots;
    }

    private static List<Jackpot> getReverseJackpotList(String[][] matrix, int m, int n, int year) {
        if (matrix == null) return new ArrayList<>();
        List<Jackpot> listJackpots = new ArrayList<>();
        for (int j = n - 1; j >= 0; j--) {
            for (int i = m - 1; i >= 0; i--) {
                String cursor = matrix[i][j];
                if (!cursor.equals(Const.INVALID_DATE)
                        && !cursor.equals(Const.FUTURE_DAY) && !cursor.equals(Const.DAY_OFF)) {
                    DateBase dateBase = new DateBase(i + 1, j + 1, year);
                    listJackpots.add(new Jackpot(cursor, dateBase));
                }
            }
        }
        return listJackpots;
    }

    private static List<Jackpot> getJackpotList(String[][] matrix, int m, int n, int year) {
        if (matrix == null) return new ArrayList<>();
        List<Jackpot> listJackpots = new ArrayList<>();
        for (int j = 0; j < n; j++) {
            for (int i = 0; i < m; i++) {
                String cursor = matrix[i][j];
                if (!cursor.equals(Const.INVALID_DATE)
                        && !cursor.equals(Const.FUTURE_DAY) && !cursor.equals(Const.DAY_OFF)) {
                    DateBase dateBase = new DateBase(i + 1, j + 1, year);
                    listJackpots.add(new Jackpot(cursor, dateBase));
                }
            }
        }
        return listJackpots;
    }

    private static String[][] getJackpotFlagMatrix(String data, int m, int n, int year) {
        if (data.isEmpty()) return null;
        String[][] matrix = new String[m][n];

        String[] numbers = data.split("---");
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                try {
                    Integer.parseInt(numbers[n * i + j]);
                    matrix[i][j] = numbers[n * i + j];
                } catch (Exception e) {
                    DateBase dateBase = new DateBase(i + 1, j + 1, year);
                    if (dateBase.isValid()) {
                        matrix[i][j] = dateBase.isToday() || dateBase.isFutureDay() ?
                                Const.FUTURE_DAY : Const.DAY_OFF;
                    } else {
                        matrix[i][j] = Const.INVALID_DATE;
                    }
                }
            }
        }
        return matrix;
    }

    /**
     * for showing table of jackpot by year
     */
    public static String[][] getJackpotMaxtrixByYear(Context context, int year) {
        String data = IOFileBase.readDataFromFile(context, "jackpot" + year + ".txt");
        if (data.isEmpty()) return null;
        return getJackpotMatrix(data, TimeInfo.DAY_OF_MONTH, TimeInfo.MONTH_OF_YEAR);
    }

    private static String[][] getJackpotMatrix(String data, int m, int n) {
        if (data.isEmpty()) return null;
        String[][] matrix = new String[m][n];

        String[] numbers = data.split("---");
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                try {
                    Integer.parseInt(numbers[n * i + j]);
                    matrix[i][j] = numbers[n * i + j];
                } catch (Exception e) {
                    matrix[i][j] = "     ";
                }
            }
        }
        return matrix;
    }
}
