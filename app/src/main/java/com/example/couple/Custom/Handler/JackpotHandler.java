package com.example.couple.Custom.Handler;

import android.content.Context;

import com.example.couple.Base.Handler.IOFileBase;
import com.example.couple.Custom.Const.Const;
import com.example.couple.Custom.Const.FileName;
import com.example.couple.Custom.Const.TimeInfo;
import com.example.couple.Custom.Handler.UpdateData.JackpotDayState;
import com.example.couple.Custom.Statistics.JackpotStatistics;
import com.example.couple.Model.DateTime.Date.Cycle.Cycle;
import com.example.couple.Model.DateTime.Date.DateBase;
import com.example.couple.Model.DateTime.Date.DateData;
import com.example.couple.Model.Origin.Jackpot;

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
            List<Jackpot> jackpotList = getReverseJackpotListByYear(context, TimeInfo.CURRENT_YEAR);
            if (jackpotList.size() < TimeInfo.DAY_OF_WEEK) {
                String lastJackpotData = Api.getJackpotDataFromInternet(context, TimeInfo.CURRENT_YEAR - 1);
                IOFileBase.saveDataToFile(context, "jackpot" + (TimeInfo.CURRENT_YEAR - 1)
                        + ".txt", lastJackpotData, 0);
                if (jackpotList.isEmpty()) {
                    jackpotList = getReverseJackpotListByYear(context, TimeInfo.CURRENT_YEAR - 1);
                }
            }
            saveLastDate(context, jackpotList);
            return true;
        } catch (ExecutionException | InterruptedException e) {
            return false;
        }
    }

    public static List<Integer> updateJackpotDataInManyYears(Context context, List<Integer> years) {
        List<Integer> updatedYears = new ArrayList<>();
        for (int year : years) {
            try {
                String data = Api.getJackpotDataFromInternet(context, year);
                if (!data.isEmpty()) {
                    IOFileBase.saveDataToFile(context, "jackpot" + year + ".txt", data, 0);
                    updatedYears.add(year);
                }
                List<Jackpot> jackpotList = getReverseJackpotListByDays(context, 1);
                saveLastDate(context, jackpotList);
            } catch (ExecutionException | InterruptedException ignored) {

            }
        }
        return updatedYears;
    }

    // lấy tối đa 2 năm tức 365 * 2 ngày
    public static List<Jackpot> getReverseJackpotListByDays(Context context, int numberOfDays) {
        List<Jackpot> jackpotList = getReverseJackpotListByYear(context, TimeInfo.CURRENT_YEAR);
        int sizeOfJackpotList = jackpotList.size();
        if (jackpotList.size() >= numberOfDays) {
            List<Jackpot> jackpots = new ArrayList<>(jackpotList.subList(0, numberOfDays));
            setDayCycleForJackpotList(context, jackpots);
            return jackpots;
        }
        List<Jackpot> lastJackpotList = getReverseJackpotListByYear(context, TimeInfo.CURRENT_YEAR - 1);
        // vd lấy 5 ngày mà jackpot có 3 ngày => last_jackpot lấy từ 0 đến 5-3-1
        int lastNumberOfDays = Math.min(lastJackpotList.size(), (numberOfDays - sizeOfJackpotList));
        jackpotList.addAll(lastJackpotList.subList(0, lastNumberOfDays));
        setDayCycleForJackpotList(context, jackpotList);
        return jackpotList;
    }

    // lấy tối đa 2 năm tức 365 * 2 ngày
    public static List<Jackpot> getAllReverseJackpotListByDays(Context context, int numberOfDays) {
        List<Jackpot> jackpotList = getAllReverseJackpotListByYear(context, TimeInfo.CURRENT_YEAR);
        int sizeOfJackpotList = jackpotList.size();
        if (jackpotList.size() >= numberOfDays) {
            List<Jackpot> jackpots = new ArrayList<>(jackpotList.subList(0, numberOfDays));
            setDayCycleForAllJackpotList(context, jackpots);
            return jackpots;
        }
        List<Jackpot> lastJackpotList = getAllReverseJackpotListByYear(context, TimeInfo.CURRENT_YEAR - 1);
        // vd lấy 5 ngày mà jackpot có 3 ngày => last_jackpot lấy từ 0 đến 5-3-1
        int lastNumberOfDays = Math.min(lastJackpotList.size(), (numberOfDays - sizeOfJackpotList));
        jackpotList.addAll(lastJackpotList.subList(0, lastNumberOfDays));
        setDayCycleForAllJackpotList(context, jackpotList);
        return jackpotList;
    }

    private static Cycle getNextDayCycle(Context context) {
        DateData today = DateHandler.getDateDataToday(context);
        DateBase jackpotLastDate = getLastDate(context);
        if (today.isEmpty() || jackpotLastDate.isEmpty()) return Cycle.getEmpty();

        if (today.getDateBase().equals(jackpotLastDate))
            return DateHandler.getDateDataNextDay(context).getDateCycle().getDay();
        if (today.getDateBase().addDays(-1).equals(jackpotLastDate))
            return today.getDateCycle().getDay();
        return Cycle.getEmpty();
    }

    private static void setDayCycleForJackpotList(Context context, List<Jackpot> jackpotList) {
        Cycle nextDayCycle = getNextDayCycle(context);
        DateBase nextDay = getLastDate(context).addDays(1);
        if (nextDayCycle.isEmpty()) return;
        for (Jackpot jackpot : jackpotList) {
            int dayNumber = (int) jackpot.getDateBase().getDistance(nextDay);
            Cycle dayCycle = nextDayCycle.addDays(-dayNumber);
            jackpot.setDayCycle(dayCycle);
        }
    }

    private static void setDayCycleForAllJackpotList(Context context, List<Jackpot> jackpotList) {
        Cycle nextDayCycle = getNextDayCycle(context);
        if (nextDayCycle.isEmpty()) return;
        for (int i = 0; i < jackpotList.size(); i++) {
            Cycle dayCycle = nextDayCycle.addDays(-i - 1);
            jackpotList.get(i).setDayCycle(dayCycle);
        }
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
            List<Jackpot> jackpotList = getReverseJackpotListByYear(context, i);
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
            List<Jackpot> jackpotList = getJackpotListByYear(context, i);
            jackpots.addAll(jackpotList);
        }
        return jackpots;
    }

    private static void saveLastDate(Context context, List<Jackpot> jackpotList) {
        if (jackpotList.isEmpty()) return;
        String lastDate = jackpotList.get(0).getDateBase().toString("-");
        IOFileBase.saveDataToFile(context, FileName.JACKPOT_LAST_DATE, lastDate, Context.MODE_PRIVATE);
    }

    public static DateBase getLastDate(Context context) {
        String lastDateStr = IOFileBase.readDataFromFile(context, FileName.JACKPOT_LAST_DATE);
        return DateBase.fromString(lastDateStr, "-");
    }

    public static List<Jackpot> getAllReverseJackpotListByYear(Context context, int year) {
        String data = IOFileBase.readDataFromFile(context, "jackpot" + year + ".txt");
        if (data.isEmpty()) return new ArrayList<>();
        String[][] matrix = convertToJackpotMatrix(data, year);
        if (matrix == null) return new ArrayList<>();
        List<Jackpot> jackpotList = new ArrayList<>();
        for (int j = TimeInfo.MONTH_OF_YEAR - 1; j >= 0; j--) {
            for (int i = TimeInfo.DAY_OF_MONTH - 1; i >= 0; i--) {
                String cursor = matrix[i][j];
                if (!cursor.equals(JackpotDayState.INVALID_DATE.name)
                        && !cursor.equals(JackpotDayState.FUTURE_DAY.name)) {
                    String jackpot =
                            cursor.equals(JackpotDayState.DAY_OFF.name) ? Const.EMPTY_JACKPOT : cursor;
                    DateBase dateBase = new DateBase(i + 1, j + 1, year);
                    jackpotList.add(new Jackpot(jackpot, dateBase));
                }
            }
        }
        return jackpotList;
    }

    public static List<Jackpot> getReverseJackpotListByYear(Context context, int year) {
        String data = IOFileBase.readDataFromFile(context, "jackpot" + year + ".txt");
        if (data.isEmpty()) return new ArrayList<>();
        String[][] matrix = convertToJackpotMatrix(data, year);
        if (matrix == null) return new ArrayList<>();
        List<Jackpot> jackpotList = new ArrayList<>();
        for (int j = TimeInfo.MONTH_OF_YEAR - 1; j >= 0; j--) {
            for (int i = TimeInfo.DAY_OF_MONTH - 1; i >= 0; i--) {
                String cursor = matrix[i][j];
                if (!cursor.equals(JackpotDayState.INVALID_DATE.name)
                        && !cursor.equals(JackpotDayState.FUTURE_DAY.name)
                        && !cursor.equals(JackpotDayState.DAY_OFF.name)) {
                    DateBase dateBase = new DateBase(i + 1, j + 1, year);
                    jackpotList.add(new Jackpot(cursor, dateBase));
                }
            }
        }
        return jackpotList;
    }

    private static List<Jackpot> getJackpotListByYear(Context context, int year) {
        String data = IOFileBase.readDataFromFile(context, "jackpot" + year + ".txt");
        if (data.isEmpty()) return new ArrayList<>();
        String[][] matrix = convertToJackpotMatrix(data, year);
        if (matrix == null) return new ArrayList<>();
        List<Jackpot> jackpotList = new ArrayList<>();
        for (int j = 0; j < TimeInfo.MONTH_OF_YEAR; j++) {
            for (int i = 0; i < TimeInfo.DAY_OF_MONTH; i++) {
                String cursor = matrix[i][j];
                if (!cursor.equals(JackpotDayState.INVALID_DATE.name)
                        && !cursor.equals(JackpotDayState.FUTURE_DAY.name)
                        && !cursor.equals(JackpotDayState.DAY_OFF.name)) {
                    DateBase dateBase = new DateBase(i + 1, j + 1, year);
                    jackpotList.add(new Jackpot(cursor, dateBase));
                }
            }
        }
        return jackpotList;
    }

    private static String[][] convertToJackpotMatrix(String data, int year) {
        if (data.isEmpty()) return null;
        String[][] matrix = new String[TimeInfo.DAY_OF_MONTH][TimeInfo.MONTH_OF_YEAR];
        String[] numbers = data.split("---");
        for (int i = 0; i < TimeInfo.DAY_OF_MONTH; i++) {
            for (int j = 0; j < TimeInfo.MONTH_OF_YEAR; j++) {
                try {
                    Integer.parseInt(numbers[TimeInfo.MONTH_OF_YEAR * i + j]);
                    matrix[i][j] = numbers[TimeInfo.MONTH_OF_YEAR * i + j];
                } catch (Exception e) {
                    DateBase dateBase = new DateBase(i + 1, j + 1, year);
                    if (dateBase.isValid()) {
                        matrix[i][j] = dateBase.isToday() || dateBase.isAfter(DateBase.TO_DAY()) ?
                                JackpotDayState.FUTURE_DAY.name : JackpotDayState.DAY_OFF.name;
                    } else {
                        matrix[i][j] = JackpotDayState.INVALID_DATE.name;
                    }
                }
            }
        }
        return matrix;
    }

    /**
     * for showing table of jackpot by year
     */
    public static String[][] getJackpotMatrixToShow(Context context, int year) {
        String data = IOFileBase.readDataFromFile(context, "jackpot" + year + ".txt");
        if (data.isEmpty()) return null;
        String[][] matrix = new String[TimeInfo.DAY_OF_MONTH][TimeInfo.MONTH_OF_YEAR];

        String[] numbers = data.split("---");
        for (int i = 0; i < TimeInfo.DAY_OF_MONTH; i++) {
            for (int j = 0; j < TimeInfo.MONTH_OF_YEAR; j++) {
                try {
                    Integer.parseInt(numbers[TimeInfo.MONTH_OF_YEAR * i + j]);
                    matrix[i][j] = numbers[TimeInfo.MONTH_OF_YEAR * i + j];
                } catch (Exception e) {
                    matrix[i][j] = "     ";
                }
            }
        }
        return matrix;
    }

}
