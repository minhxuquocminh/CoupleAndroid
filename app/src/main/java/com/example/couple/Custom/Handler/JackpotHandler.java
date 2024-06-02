package com.example.couple.Custom.Handler;

import android.content.Context;

import com.example.couple.Base.Handler.IOFileBase;
import com.example.couple.Custom.Const.Const;
import com.example.couple.Custom.Const.FileName;
import com.example.couple.Custom.Const.TimeInfo;
import com.example.couple.Custom.Statistics.JackpotStatistics;
import com.example.couple.Model.Origin.Jackpot;
import com.example.couple.Model.Time.Cycle.Cycle;
import com.example.couple.Model.Time.DateBase;
import com.example.couple.Model.Time.TimeBase;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class JackpotHandler {

    public static boolean updateJackpot(Context context) {
        try {
            String jackpotData = Api.getJackpotDataFromInternet(context, TimeInfo.CURRENT_YEAR);
            if (jackpotData.isEmpty()) {
                return false;
            }

            IOFileBase.saveDataToFile(context, "jackpot" + TimeInfo.CURRENT_YEAR + ".txt",
                    jackpotData, 0);
            List<Jackpot> jackpotList = JackpotHandler.getReserveJackpotListByYear(context, TimeInfo.CURRENT_YEAR);
            if (jackpotList.size() < TimeInfo.DAY_OF_WEEK) {
                String lastJackpotData = Api.getJackpotDataFromInternet(context, TimeInfo.CURRENT_YEAR - 1);
                IOFileBase.saveDataToFile(context, "jackpot" + (TimeInfo.CURRENT_YEAR - 1)
                        + ".txt", lastJackpotData, 0);
            }
            return true;
        } catch (ExecutionException | InterruptedException e) {
            return false;
        }
    }

    // lấy tối đa 2 năm tức 365 * 2 ngày
    public static List<Jackpot> getReserveJackpotListFromFile(Context context, int numberOfDays) {
        String data = IOFileBase.readDataFromFile(context,
                "jackpot" + TimeInfo.CURRENT_YEAR + ".txt");
        if (data.isEmpty()) return new ArrayList<>();
        String[][] matrix = getJackpotFlagMatrix(data,
                TimeInfo.DAY_OF_MONTH, TimeInfo.MONTH_OF_YEAR, TimeInfo.CURRENT_YEAR);
        List<Jackpot> jackpotList = getAllReverseJackpotList(matrix,
                TimeInfo.DAY_OF_MONTH, TimeInfo.CURRENT_MONTH, TimeInfo.CURRENT_YEAR);
        int sizeOfJackpotList = jackpotList.size();
        if (jackpotList.size() >= numberOfDays) {
            List<Jackpot> jackpots = new ArrayList<>(jackpotList.subList(0, numberOfDays));
            setDayCycleForJackpotList(context, jackpots);
            return jackpots;
        }

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
        setDayCycleForJackpotList(context, jackpotList);
        return jackpotList;
    }

    public static Cycle getCycleNextDay(Context context) {
        TimeBase today = CycleHandler.getTimeBaseToday(context);
        DateBase jackpotLastDate = getLastDate(context);
        if (today.isEmpty() || jackpotLastDate.isEmpty()) return Cycle.getEmpty();

        if (today.getDateBase().equals(jackpotLastDate))
            return CycleHandler.getTimeBaseNextDay(context).getDateCycle().getDay();
        if (today.getDateBase().addDays(-1).equals(jackpotLastDate))
            return today.getDateCycle().getDay();
        return Cycle.getEmpty();
    }

    // chỉ setCycle khi đủ data Jackpot (tính cả DAY_OFF)
    private static void setDayCycleForJackpotList(Context context, List<Jackpot> jackpotList) {
        Cycle nextDay = getCycleNextDay(context);
        if (nextDay.isEmpty()) return;
        for (int i = 0; i < jackpotList.size(); i++) {
            Cycle dayCycle = nextDay.addDays(-i - 1);
            jackpotList.get(i).setDayCycle(dayCycle);
            if (jackpotList.get(i).isEmpty()) {
                jackpotList.remove(i);
                i--;
            }
        }
    }

    public static List<Jackpot> getReserveJackpotListByYear(Context context, int year) {
        String data = IOFileBase.readDataFromFile(context, "jackpot" + year + ".txt");
        if (data.isEmpty()) return new ArrayList<>();
        String[][] matrix = getJackpotFlagMatrix(data,
                TimeInfo.DAY_OF_MONTH, TimeInfo.MONTH_OF_YEAR, year);
        return getReverseJackpotList(matrix,
                TimeInfo.DAY_OF_MONTH, TimeInfo.CURRENT_MONTH, year);
    }

    public static List<Jackpot> getReverseJackpotListManyYears(Context context, int numberOfYears) {
        int[] startAndEndYearFile = JackpotStatistics.getStartAndEndYearFile(context);
        if (startAndEndYearFile == null) return new ArrayList<>();
        List<Jackpot> jackpots = new ArrayList<>();

        int startYear = startAndEndYearFile[0];
        int endYear = startAndEndYearFile[1];
        int lengthYear_file = endYear - startYear + 1;
        int lengthYear = Math.min(numberOfYears, lengthYear_file);

        for (int i = endYear; i >= endYear - lengthYear + 1; i--) {
            String data = IOFileBase.readDataFromFile(context, "jackpot" + i + ".txt");
            String[][] matrix = getJackpotFlagMatrix(data,
                    TimeInfo.DAY_OF_MONTH, TimeInfo.MONTH_OF_YEAR, i);
            List<Jackpot> jackpotList = getReverseJackpotList(matrix,
                    TimeInfo.DAY_OF_MONTH, TimeInfo.MONTH_OF_YEAR, i);
            jackpots.addAll(jackpotList);
        }
        return jackpots;
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

    public static void saveLastDate(Context context, List<Jackpot> jackpotList) {
        if (jackpotList.isEmpty()) return;
        String lastDate = jackpotList.get(0).getDateBase().toString("-");
        IOFileBase.saveDataToFile(context, FileName.JACKPOT_LAST_DATE, lastDate, Context.MODE_PRIVATE);
    }

    public static DateBase getLastDate(Context context) {
        String lastDateStr = IOFileBase.readDataFromFile(context, FileName.JACKPOT_LAST_DATE);
        return DateBase.fromString(lastDateStr, "-");
    }

    private static List<Jackpot> getAllReverseJackpotList(String[][] matrix, int m, int n, int year) {
        if (matrix == null) return new ArrayList<>();
        List<Jackpot> jackpotList = new ArrayList<>();
        for (int j = n - 1; j >= 0; j--) {
            for (int i = m - 1; i >= 0; i--) {
                String cursor = matrix[i][j];
                if (!cursor.equals(Const.INVALID_DATE) && !cursor.equals(Const.FUTURE_DAY)) {
                    String jackpot = cursor.equals(Const.DAY_OFF) ? Const.EMPTY_JACKPOT : cursor;
                    DateBase dateBase = new DateBase(i + 1, j + 1, year);
                    jackpotList.add(new Jackpot(jackpot, dateBase));
                }
            }
        }
        return jackpotList;
    }

    private static List<Jackpot> getReverseJackpotList(String[][] matrix, int m, int n, int year) {
        if (matrix == null) return new ArrayList<>();
        List<Jackpot> jackpotList = new ArrayList<>();
        for (int j = n - 1; j >= 0; j--) {
            for (int i = m - 1; i >= 0; i--) {
                String cursor = matrix[i][j];
                if (!cursor.equals(Const.INVALID_DATE)
                        && !cursor.equals(Const.FUTURE_DAY) && !cursor.equals(Const.DAY_OFF)) {
                    DateBase dateBase = new DateBase(i + 1, j + 1, year);
                    jackpotList.add(new Jackpot(cursor, dateBase));
                }
            }
        }
        return jackpotList;
    }

    private static List<Jackpot> getJackpotList(String[][] matrix, int m, int n, int year) {
        if (matrix == null) return new ArrayList<>();
        List<Jackpot> jackpotList = new ArrayList<>();
        for (int j = 0; j < n; j++) {
            for (int i = 0; i < m; i++) {
                String cursor = matrix[i][j];
                if (!cursor.equals(Const.INVALID_DATE)
                        && !cursor.equals(Const.FUTURE_DAY) && !cursor.equals(Const.DAY_OFF)) {
                    DateBase dateBase = new DateBase(i + 1, j + 1, year);
                    jackpotList.add(new Jackpot(cursor, dateBase));
                }
            }
        }
        return jackpotList;
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
