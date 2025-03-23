package com.example.couple.Custom.Statistics;

import com.example.couple.Base.Handler.CoupleBase;
import com.example.couple.Custom.Const.Const;
import com.example.couple.Custom.Const.TimeInfo;
import com.example.couple.Model.Bridge.Double.JackpotSign;
import com.example.couple.Model.Bridge.Double.NumberDouble;
import com.example.couple.Model.Bridge.LongBeat.NearestTime;
import com.example.couple.Model.DateTime.Date.DateBase;
import com.example.couple.Model.Origin.Couple;
import com.example.couple.Model.Origin.Jackpot;
import com.example.couple.Model.Origin.Lottery;
import com.example.couple.Model.Statistics.JackpotNextDay;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class JackpotStatistics {

    public static String[][] sortMatrixByColumn(String[][] matrix, int columnIndex) {
        if (matrix.length <= 1) return matrix; // chỉ header hoặc rỗng

        // Header
        String[] header = matrix[0];

        // Dữ liệu: bỏ header
        List<String[]> dataRows = new ArrayList<>(Arrays.asList(matrix).subList(1, matrix.length));

        // Sort theo cột cụ thể, convert giá trị về số để sort đúng
        dataRows.sort(Comparator.comparingInt(row -> Integer.parseInt(row[columnIndex])));

        // Ghép lại: header + sorted data
        List<String[]> sortedMatrix = new ArrayList<>();
        sortedMatrix.add(header);
        sortedMatrix.addAll(dataRows);

        return sortedMatrix.toArray(new String[0][0]);
    }

    public static String[][] getCounterMatrixByYears(Map<Integer, int[]> counterByYears, List<Integer> couples) {
        String[][] matrix = new String[couples.size() + 1][counterByYears.size() + 2];
        matrix[0][0] = "Đề";
        int row = 1;
        int col = 1;
        int[] sum = new int[couples.size() + 1];
        for (Map.Entry<Integer, int[]> entry : counterByYears.entrySet()) {
            matrix[0][col] = entry.getKey() + "";
            for (Integer couple : couples) {
                int counter = entry.getValue()[couple];
                matrix[row][col] = counter + "";
                sum[row] += counter;
                row++;
            }
            row = 1;
            col++;
        }
        for (Integer couple : couples) {
            matrix[row][0] = couple + "";
            matrix[row][col] = sum[row] + "";
            row++;
        }
        matrix[0][col] = "Tổng";
        return matrix;
    }

    public static String[][] getCounterMatrixByYears(Map<Integer, int[]> counterByYears) {
        String[][] matrix = new String[100 + 1][counterByYears.size() + 2];
        matrix[0][0] = "Đề";
        int col = 1;
        int[] sum = new int[100 + 1];
        for (Map.Entry<Integer, int[]> entry : counterByYears.entrySet()) {
            matrix[0][col] = entry.getKey() + "";
            for (int couple = 0; couple < 100; couple++) {
                int counter = entry.getValue()[couple];
                matrix[couple + 1][col] = counter + "";
                sum[couple + 1] += counter;
            }
            col++;
        }
        for (int couple = 0; couple < 100; couple++) {
            matrix[couple + 1][0] = couple + "";
            matrix[couple + 1][col] = sum[couple + 1] + "";
        }
        matrix[0][col] = "Tổng";
        return matrix;
    }

    public static Map<Integer, int[]> getCounterByYears(Map<Integer, String[][]> matrixByYears) {
        Map<Integer, int[]> counterByYears = new LinkedHashMap<>();
        matrixByYears.forEach((year, matrix) -> {
            int[] counter = new int[100];
            for (int i = 0; i < TimeInfo.DAY_OF_MONTH; i++) {
                for (int j = 0; j < TimeInfo.MONTH_OF_YEAR; j++) {
                    Jackpot jackpot = new Jackpot(matrix[i][j], new DateBase(i + 1, j + 1, year));
                    if (!jackpot.isEmptyOrInvalid()) {
                        counter[jackpot.getCoupleInt()]++;
                    }
                }
            }
            counterByYears.put(year, counter);
        });
        return counterByYears;
    }

    public static List<NearestTime> getHeadAndTailInNearestTime(List<Jackpot> jackpotList) {
        List<NearestTime> nearestTimeList = new ArrayList<>();
        int[] appearanceTimes1 = new int[10];
        int[] nearestIndex1 = new int[10];
        int[] appearanceTimes2 = new int[10];
        int[] nearestIndex2 = new int[10];

        for (int i = 0; i < 10; i++) {
            nearestIndex1[i] = -1;
            nearestIndex2[i] = -1;
        }

        for (int i = 0; i < jackpotList.size(); i++) {
            int first = jackpotList.get(i).getCouple().getFirst();
            int second = jackpotList.get(i).getCouple().getSecond();
            appearanceTimes1[first]++;
            appearanceTimes2[second]++;
            if (nearestIndex1[first] == -1)
                nearestIndex1[first] = i;
            if (nearestIndex2[second] == -1)
                nearestIndex2[second] = i;
        }

        for (int i = 0; i < 10; i++) {
            int dayNumberBefore1 = nearestIndex1[i] == -1 ?
                    Const.MAX_DAY_NUMBER_BEFORE : nearestIndex1[i] + 1;
            NearestTime nearestTime1 = new NearestTime(i, dayNumberBefore1, appearanceTimes1[i], Const.HEAD);
            nearestTimeList.add(nearestTime1);
            int dayNumberBefore2 = nearestIndex2[i] == -1 ?
                    Const.MAX_DAY_NUMBER_BEFORE : nearestIndex2[i] + 1;
            NearestTime nearestTime2 = new NearestTime(i, dayNumberBefore2, appearanceTimes2[i], Const.TAIL);
            nearestTimeList.add(nearestTime2);
        }

        nearestTimeList.sort(new Comparator<NearestTime>() {
            @Override
            public int compare(NearestTime o1, NearestTime o2) {
                return Integer.compare(o2.getDayNumberBefore(), o1.getDayNumberBefore());
            }
        });

        return nearestTimeList;
    }

    public static List<NearestTime> getSameDoubleInNearestTime(List<Jackpot> jackpotList) {
        List<NearestTime> nearestTimeList = new ArrayList<>();
        int[] appearanceTimes = new int[10];
        int[] nearestIndex = new int[10];

        for (int i = 0; i < 10; i++) {
            nearestIndex[i] = -1;
        }

        for (int i = 0; i < jackpotList.size(); i++) {
            if (jackpotList.get(i).getCouple().isSameDouble()) {
                int first = jackpotList.get(i).getCouple().getFirst();
                appearanceTimes[first]++;
                if (nearestIndex[first] == -1)
                    nearestIndex[first] = i;
            }
        }

        for (int i = 0; i < 10; i++) {
            int number = Integer.parseInt(i + "" + i);
            int dayNumberBefore = nearestIndex[i] == -1 ? Const.MAX_DAY_NUMBER_BEFORE : nearestIndex[i] + 1;
            NearestTime nearestTime = new NearestTime(number, dayNumberBefore, appearanceTimes[i], Const.DOUBLE);
            nearestTimeList.add(nearestTime);
        }

        nearestTimeList.sort(new Comparator<NearestTime>() {
            @Override
            public int compare(NearestTime o1, NearestTime o2) {
                return Integer.compare(o2.getDayNumberBefore(), o1.getDayNumberBefore());
            }
        });

        return nearestTimeList;
    }

    public static List<JackpotNextDay> getJackpotNextDayList(Map<Integer, String[][]> matrixByYears, int lastCouple) {
        List<DateBase> dateBases = new ArrayList<>();
        matrixByYears.forEach((year, matrix) -> {
            for (int i = 0; i < TimeInfo.DAY_OF_MONTH; i++) {
                for (int j = 0; j < TimeInfo.MONTH_OF_YEAR; j++) {
                    int couple = CoupleBase.getCouple(matrix[i][j]);
                    if (couple == lastCouple) {
                        dateBases.add(new DateBase(i + 1, j + 1, year));
                    }
                }
            }
        });

        Collections.reverse(dateBases);
        List<JackpotNextDay> results = new ArrayList<>();
        dateBases.forEach(dateBase -> {
            DateBase nextDateBase = dateBase.addDays(1);
            Jackpot jackpot = Jackpot.getEmpty();
            Jackpot jackpotNextDay = Jackpot.getEmpty();
            for (Map.Entry<Integer, String[][]> entry : matrixByYears.entrySet()) {
                Jackpot first = new Jackpot(entry.getValue()[dateBase.getDay() - 1][dateBase.getMonth() - 1], dateBase);
                if (dateBase.getYear() == entry.getKey() && !first.isInvalid()) {
                    jackpot = first;
                }
                Jackpot second = new Jackpot(entry.getValue()[nextDateBase.getDay() - 1][nextDateBase.getMonth() - 1], nextDateBase);
                if (nextDateBase.getYear() == entry.getKey() && !second.isInvalid()) {
                    jackpotNextDay = second;
                }
                if (!jackpot.isEmpty() && !jackpotNextDay.isEmpty()) {
                    results.add(new JackpotNextDay(first, second));
                    break;
                }
            }
        });
        return results;
    }

    public static List<Integer> getBeatOfSameDouble(List<Jackpot> jackpotList) {
        if (jackpotList.isEmpty()) return new ArrayList<>();
        List<Integer> beatList = new ArrayList<>();
        int runningSize = Math.min(jackpotList.size(), 150);
        int beat = 0;
        int sizeOfBeat = 0;
        for (int i = 0; i < runningSize; i++) {
            beat++;
            if (jackpotList.get(i).getCouple().isSameDouble()) {
                beatList.add(beat); // nhịp đầu tiên tính từ hiện tại tới ngày có kép bằng
                sizeOfBeat++;
                beat = 0;
            }
            if (sizeOfBeat > 8) break;
        }
        Collections.reverse(beatList);
        return beatList;
    }

    public static List<Integer> getSignInLottery(Lottery lastLottery) {
        List<Integer> numberList = new ArrayList<>();
        List<Couple> coupleList = lastLottery.getCoupleList();
        for (int i = 0; i < coupleList.size(); i++) {
            if (coupleList.get(i).isDeviatedDouble()) {
                numberList.add(Integer.valueOf(coupleList.get(i).toString()));
            }
        }
        return numberList;
    }

    public static List<JackpotSign> getSignInJackpot(List<Jackpot> jackpotList) {
        if (jackpotList.isEmpty()) return new ArrayList<>();
        List<JackpotSign> jackpotSignList = new ArrayList<>();
        int runningSize = Math.min(jackpotList.size(), 150);
        List<Jackpot> subJackpotList = new ArrayList<>();
        List<Integer> beatList = new ArrayList<>();
        int beat = 0;
        int sizeOfJackpotSign = 0;
        int sameDouble = -1; // nếu sameDouble = -1 thì tìm dấu hiệu khi mà chưa ra kép.
        for (int i = 0; i < runningSize; i++) {
            beat++;
            if (jackpotList.get(i).getCouple().isSameDouble()) {
                sizeOfJackpotSign++;
                Collections.reverse(subJackpotList);
                Collections.reverse(beatList);
                jackpotSignList.add(new JackpotSign(subJackpotList, beatList, sameDouble));
                sameDouble = jackpotList.get(i).getCoupleInt();
                subJackpotList = new ArrayList<>();
                beatList = new ArrayList<>();
                beat = 0;
            }
            if (jackpotList.get(i).isSameSequentlySign()) {
                subJackpotList.add(jackpotList.get(i));
                beatList.add(beat);
            }
            if (sizeOfJackpotSign > 8) break;
        }
        Collections.reverse(jackpotSignList);
        return jackpotSignList;
    }

    public static List<NumberDouble> getNumberBeforeSameDoubleAppear(List<Jackpot> jackpotList) {
        if (jackpotList.isEmpty()) return new ArrayList<>();
        List<NumberDouble> numberDoubleList = new ArrayList<>();
        numberDoubleList.add(new NumberDouble(jackpotList.get(0).getCoupleInt(), -1));
        int runningSize = Math.min(jackpotList.size(), 150);
        int sizeOfNumberDouble = 0;
        for (int i = 0; i < runningSize - 1; i++) {
            if (jackpotList.get(i).getCouple().isSameDouble()) {
                int couple1 = jackpotList.get(i + 1).getCoupleInt();
                int couple2 = jackpotList.get(i).getCoupleInt();
                NumberDouble numberDouble = new NumberDouble(couple1, couple2);
                numberDoubleList.add(numberDouble);
                sizeOfNumberDouble++;
            }
            if (sizeOfNumberDouble > 8) break;
        }
        Collections.reverse(numberDoubleList);
        return numberDoubleList;
    }

    public static int[] getCoupleCounting(List<Jackpot> jackpotList, int m) {
        if (jackpotList.isEmpty()) return null;
        int[] numberArr = new int[m];
        for (int i = 0; i < jackpotList.size(); i++) {
            numberArr[jackpotList.get(i).getCoupleInt()]++;
        }
        return numberArr;
    }

    public static List<Jackpot> getJackpotListLastMonth(List<Jackpot> jackpotList, List<DateBase> dateBases) {
        if (jackpotList.isEmpty()) return new ArrayList<>();
        List<Jackpot> results = new ArrayList<>();
        List<DateBase> dates = dateBases.stream().distinct().collect(Collectors.toList());
        int count = 0;
        for (int i = jackpotList.size() - 1; i >= 0; i--) {
            if (dates.contains(jackpotList.get(i).getDateBase())) {
                results.add(jackpotList.get(i));
                count++;
            }
            if (count == dates.size()) break;
        }
        return results;
    }
}
