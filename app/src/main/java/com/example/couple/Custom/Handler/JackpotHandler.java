package com.example.couple.Custom.Handler;

import android.content.Context;

import com.example.couple.Base.Handler.IOFileBase;
import com.example.couple.Base.Handler.StorageBase;
import com.example.couple.Custom.Const.Const;
import com.example.couple.Custom.Const.FileName;
import com.example.couple.Custom.Const.TimeInfo;
import com.example.couple.Custom.Enum.Split;
import com.example.couple.Custom.Enum.StorageType;
import com.example.couple.Model.State.JackpotDayState;
import com.example.couple.Model.DateTime.Date.Cycle.Cycle;
import com.example.couple.Model.DateTime.Date.DateBase;
import com.example.couple.Model.DateTime.Date.DateData;
import com.example.couple.Model.Origin.Jackpot;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class JackpotHandler {

    public static boolean updateJackpot(Context context) {
        try {
            int currentYear = TimeInfo.CURRENT_YEAR;
            String jackpotData = Api.getJackpotDataFromInternet(context, currentYear);
            if (jackpotData.isEmpty()) return false;
            IOFileBase.saveDataToFile(context, "jackpot" + currentYear + ".txt", jackpotData, 0);
            List<Jackpot> jackpotList = getJackpotListByYear(context, currentYear);
            addUpdatedYear(context, currentYear);
            if (jackpotList.size() < Const.DAY_NUMBER_TO_GET_JACKPOT) {
                String lastJackpotData = Api.getJackpotDataFromInternet(context, currentYear - 1);
                IOFileBase.saveDataToFile(context, "jackpot" + (currentYear - 1) + ".txt", lastJackpotData, 0);
                if (jackpotList.isEmpty()) {
                    jackpotList = getJackpotListByYear(context, currentYear - 1);
                }
                addUpdatedYear(context, currentYear - 1);
            }
            saveLastDate(context, jackpotList);
            return true;
        } catch (ExecutionException | InterruptedException e) {
            return false;
        }
    }

    public static List<Integer> updateJackpotDataByYears(Context context, List<Integer> years) {
        List<Integer> updatedYears = new ArrayList<>();
        for (int year : years) {
            try {
                String data = Api.getJackpotDataFromInternet(context, year);
                if (!data.isEmpty()) {
                    IOFileBase.saveDataToFile(context, "jackpot" + year + ".txt", data, 0);
                    updatedYears.add(year);
                    addUpdatedYear(context, year);
                }
                List<Jackpot> jackpotList = getJackpotListByDays(context, 1);
                saveLastDate(context, jackpotList);
            } catch (ExecutionException | InterruptedException ignored) {

            }
        }
        return updatedYears;
    }

    public static List<Integer> getUpdatedYears(Context context) {
        return StorageBase.getNumberList(context, StorageType.LIST_OF_YEARS);
    }

    private static void addUpdatedYear(Context context, Integer year) {
        List<Integer> updatedYears = getUpdatedYears(context);
        if (updatedYears.contains(year)) return;
        List<Integer> years = Stream.concat(updatedYears.stream(), Stream.of(year))
                .distinct().sorted().collect(Collectors.toList());
        StorageBase.setNumberList(context, StorageType.LIST_OF_YEARS, years);
    }

    // lấy tối đa 2 năm tức 365 * 2 ngày
    public static List<Jackpot> getJackpotListByDays(Context context, int numberOfDays) {
        List<Jackpot> jackpotList = getJackpotListByYear(context, TimeInfo.CURRENT_YEAR);
        int sizeOfJackpotList = jackpotList.size();
        if (jackpotList.size() >= numberOfDays) {
            List<Jackpot> jackpots = new ArrayList<>(jackpotList.subList(0, numberOfDays));
            setDayCycleForJackpotList(context, jackpots);
            return jackpots;
        }
        List<Jackpot> lastJackpotList = getJackpotListByYear(context, TimeInfo.CURRENT_YEAR - 1);
        // vd lấy 5 ngày mà jackpot có 3 ngày => last_jackpot lấy từ 0 đến 5-3-1
        int lastNumberOfDays = Math.min(lastJackpotList.size(), (numberOfDays - sizeOfJackpotList));
        jackpotList.addAll(lastJackpotList.subList(0, lastNumberOfDays));
        setDayCycleForJackpotList(context, jackpotList);
        return jackpotList;
    }

    // lấy tối đa 2 năm tức 365 * 2 ngày
    public static List<Jackpot> getAllJackpotListByDays(Context context, int numberOfDays) {
        List<Jackpot> jackpotList = getAllJackpotListByYear(context, TimeInfo.CURRENT_YEAR);
        int sizeOfJackpotList = jackpotList.size();
        if (jackpotList.size() >= numberOfDays) {
            List<Jackpot> jackpots = new ArrayList<>(jackpotList.subList(0, numberOfDays));
            setDayCycleForAllJackpotList(context, jackpots);
            return jackpots;
        }
        List<Jackpot> lastJackpotList = getAllJackpotListByYear(context, TimeInfo.CURRENT_YEAR - 1);
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

    public static List<Jackpot> getJackpotListManyYears(Context context, int numberOfYears) {
        List<Integer> years = getUpdatedYears(context);
        int currentYear = TimeInfo.CURRENT_YEAR;
        int startYear = currentYear;
        for (int year = currentYear; year > currentYear - numberOfYears; year--) {
            if (!years.contains(year)) break;
            startYear = year;
        }
        List<Jackpot> jackpots = new ArrayList<>();
        for (int i = currentYear; i >= startYear; i--) {
            jackpots.addAll(getJackpotListByYear(context, i));
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

    public static List<Jackpot> getAllJackpotListByYear(Context context, int year) {
        String[][] matrix = getJackpotMatrixWithState(context, year);
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

    public static List<Jackpot> getJackpotListByYear(Context context, int year) {
        String[][] matrix = getJackpotMatrixWithState(context, year);
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

    public static Map<Integer, String[][]> getJackpotMatrixByYears(Context context, int numberOfYears) {
        if (numberOfYears < 1) return new LinkedHashMap<>();
        Map<Integer, String[][]> matrixMap = new LinkedHashMap<>();
        int startYear = TimeInfo.CURRENT_YEAR - numberOfYears + 1;
        int endYear = TimeInfo.CURRENT_YEAR;

        for (int year = startYear; year <= endYear; year++) {
            String[][] matrix = getJackpotMatrixByYear(context, year);
            if (matrix != null) {
                matrixMap.put(year, matrix);
            }
        }
        return matrixMap;
    }

    public static String[][] getJackpotMatrixByYear(Context context, int year) {
        String data = IOFileBase.readDataFromFile(context, "jackpot" + year + ".txt");
        if (data.isEmpty()) return null;
        String[][] matrix = new String[TimeInfo.DAY_OF_MONTH][TimeInfo.MONTH_OF_YEAR];
        String[] numbers = data.split(Split.FIRST_ROUND.value);
        for (int i = 0; i < TimeInfo.DAY_OF_MONTH; i++) {
            for (int j = 0; j < TimeInfo.MONTH_OF_YEAR; j++) {
                try {
                    String value = numbers[TimeInfo.MONTH_OF_YEAR * i + j];
                    Integer.parseInt(value);
                    matrix[i][j] = value;
                } catch (Exception e) {
                    DateBase dateBase = new DateBase(i + 1, j + 1, year);
                    matrix[i][j] = !dateBase.isValid() ? "" : JackpotDayState.INVALID_DATE.nameToShow;
                }
            }
        }
        return matrix;
    }

    private static String[][] getJackpotMatrixWithState(Context context, int year) {
        String data = IOFileBase.readDataFromFile(context, "jackpot" + year + ".txt");
        if (data.isEmpty()) return null;
        String[][] matrix = new String[TimeInfo.DAY_OF_MONTH][TimeInfo.MONTH_OF_YEAR];
        String[] numbers = data.split(Split.FIRST_ROUND.value);
        for (int i = 0; i < TimeInfo.DAY_OF_MONTH; i++) {
            for (int j = 0; j < TimeInfo.MONTH_OF_YEAR; j++) {
                try {
                    Integer.parseInt(numbers[TimeInfo.MONTH_OF_YEAR * i + j]);
                    matrix[i][j] = numbers[TimeInfo.MONTH_OF_YEAR * i + j];
                } catch (Exception e) {
                    DateBase dateBase = new DateBase(i + 1, j + 1, year);
                    if (dateBase.isValid()) {
                        matrix[i][j] = dateBase.isBefore(DateBase.today()) ?
                                JackpotDayState.DAY_OFF.name : JackpotDayState.FUTURE_DAY.name;
                    } else {
                        matrix[i][j] = JackpotDayState.INVALID_DATE.name;
                    }
                }
            }
        }
        return matrix;
    }

}
