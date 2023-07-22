package com.example.couple.Custom.Handler;

import android.content.Context;

import com.example.couple.Model.Time.DateBase;
import com.example.couple.Base.Handler.IOFileBase;
import com.example.couple.Custom.Const.Const;
import com.example.couple.Custom.Const.TimeInfo;
import com.example.couple.Custom.Statistics.JackpotStatistics;
import com.example.couple.Model.Origin.Jackpot;

import java.util.ArrayList;
import java.util.List;

public class JackpotHandler {

    public static DateBase getLastDate(Context context) {
        List<Jackpot> jackpotList = GetReserveJackpotListFromFile(context, 1);
        if (jackpotList.isEmpty()) return new DateBase();
        return jackpotList.get(0).getDateBase();
    }

    public static List<Jackpot> GetJackpotListManyYears(Context context, int numberOfYears) {
        int[] startAndEndYearFile = JackpotStatistics.GetStartAndEndYearFile(context);
        if (startAndEndYearFile == null) return new ArrayList<>();
        List<Jackpot> jackpots = new ArrayList<>();

        int startYear_file = startAndEndYearFile[0];
        int endYear_file = startAndEndYearFile[1];
        int lengthYear_file = endYear_file - startYear_file + 1;
        int lengthYear = numberOfYears < lengthYear_file ? numberOfYears : lengthYear_file;
        int startYear = endYear_file - lengthYear + 1;
        int endYear = endYear_file;

        for (int i = startYear; i <= endYear; i++) {
            String data = IOFileBase.readDataFromFile(context, "jackpot" + i + ".txt");
            String matrix[][] = GetJackpotMatrix(data, Const.MAX_DAY_OF_MONTH, Const.MONTH_OF_YEAR);
            List<Jackpot> jackpotList = GetJackpotList(matrix, Const.MAX_DAY_OF_MONTH, Const.MONTH_OF_YEAR, i);
            jackpots.addAll(jackpotList);
        }
        return jackpots;
    }

    // lấy tối đa 2 năm tức 365 * 2 ngày
    public static List<Jackpot> GetReserveJackpotListFromFile(Context context, int numberOfDays) {
        String data = IOFileBase.readDataFromFile(context, "jackpot" + TimeInfo.CURRENT_YEAR + ".txt");
        if (data.equals("")) return new ArrayList<>();
        String[][] matrix = GetJackpotMatrix(data, Const.MAX_DAY_OF_MONTH, Const.MONTH_OF_YEAR);
        List<Jackpot> jackpotList = GetReverseJackpotList(matrix, Const.MAX_DAY_OF_MONTH,
                TimeInfo.CURRENT_MONTH, TimeInfo.CURRENT_YEAR);
        int sizeOfJackpotList = jackpotList.size();
        if (jackpotList.size() >= numberOfDays) return jackpotList.subList(0, numberOfDays);
        String lastData = IOFileBase.readDataFromFile(context, "jackpot" +
                (TimeInfo.CURRENT_YEAR - 1) + ".txt");
        if (!lastData.equals("")) {
            String[][] lastMatrix = GetJackpotMatrix(lastData, Const.MAX_DAY_OF_MONTH, Const.MONTH_OF_YEAR);
            List<Jackpot> lastJackpotList = GetReverseJackpotList(lastMatrix, Const.MAX_DAY_OF_MONTH,
                    Const.MONTH_OF_YEAR, TimeInfo.CURRENT_YEAR - 1);
            // vd lấy 5 ngày mà jackpot có 3 ngày => lastjackpot lấy từ 0 đến 5-3-1
            int lastNumberOfDays = lastJackpotList.size() < (numberOfDays - sizeOfJackpotList) ?
                    lastJackpotList.size() : (numberOfDays - sizeOfJackpotList);
            jackpotList.addAll(lastJackpotList.subList(0, lastNumberOfDays));
        }
        return jackpotList;
    }

    public static String[][] GetJackpotMaxtrixByYear(Context context, int year) {
        String data = IOFileBase.readDataFromFile(context, "jackpot" + year + ".txt");
        if (data.equals("")) return null;
        return GetJackpotMatrix(data, Const.MAX_DAY_OF_MONTH, Const.MONTH_OF_YEAR);
    }

    public static List<Jackpot> GetReserveJackpotListByYear(Context context, int year) {
        String data = IOFileBase.readDataFromFile(context, "jackpot" + year + ".txt");
        if (data.equals("")) return new ArrayList<>();
        String[][] matrix = GetJackpotMatrix(data, Const.MAX_DAY_OF_MONTH, Const.MONTH_OF_YEAR);
        List<Jackpot> jackpotList = GetReverseJackpotList(matrix, Const.MAX_DAY_OF_MONTH,
                TimeInfo.CURRENT_MONTH, year);
        return jackpotList;
    }

    private static List<Jackpot> GetReverseJackpotList(String matrix[][], int m, int n, int year) {
        if (matrix == null) return new ArrayList<>();
        List<Jackpot> listJackpots = new ArrayList<>();
        for (int j = n - 1; j >= 0; j--) {
            for (int i = m - 1; i >= 0; i--) {
                if (!matrix[i][j].equals("")) {
                    DateBase dateBase = new DateBase(i + 1, j + 1, year);
                    listJackpots.add(new Jackpot(matrix[i][j], dateBase));
                }
            }
        }
        return listJackpots;
    }

    private static List<Jackpot> GetJackpotList(String matrix[][], int m, int n, int year) {
        if (matrix == null) return new ArrayList<>();
        List<Jackpot> listJackpots = new ArrayList<>();
        for (int j = 0; j < n; j++) {
            for (int i = 0; i < m; i++) {
                if (!matrix[i][j].equals("")) {
                    DateBase dateBase = new DateBase(i + 1, j + 1, year);
                    listJackpots.add(new Jackpot(matrix[i][j], dateBase));
                }
            }
        }
        return listJackpots;
    }

    private static String[][] GetJackpotMatrix(String data, int m, int n) {
        if (data.equals("")) return null;
        String matrix[][] = new String[m][n];

        String[] numbers = data.split("---");
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                try {
                    Integer.parseInt(numbers[n * i + j]);
                    matrix[i][j] = numbers[n * i + j];
                } catch (Exception e) {
                    matrix[i][j] = "";
                }
            }
        }
        return matrix;
    }
}
