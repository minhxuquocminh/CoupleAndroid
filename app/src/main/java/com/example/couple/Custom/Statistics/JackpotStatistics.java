package com.example.couple.Custom.Statistics;

import android.content.Context;

import com.example.couple.Base.Handler.IOFileBase;
import com.example.couple.Custom.Const.Const;
import com.example.couple.Custom.Const.FileName;
import com.example.couple.Model.Display.BSingle;
import com.example.couple.Model.Display.HeadTail;
import com.example.couple.Model.Display.JackpotNextDay;
import com.example.couple.Model.Display.JackpotSign;
import com.example.couple.Model.Display.NearestTime;
import com.example.couple.Model.Display.NumberDouble;
import com.example.couple.Model.Origin.Couple;
import com.example.couple.Model.Origin.Jackpot;
import com.example.couple.Model.Origin.Lottery;
import com.example.couple.Model.Time.DateBase;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class JackpotStatistics {

    // nếu status = 0 thì ko sắp xếp, status = 1 thì sx năm gần nhất, status = 2 thì sx tổng.
    public static int[][] getSortMatrix(int[][] matrix, int m, int n, int status) {
        if (matrix == null) return null;
        int[] arr = new int[m];
        int[] index = new int[m];
        for (int i = 0; i < m; i++) {
            index[i] = i;
        }

        int[][] new_matrix = new int[m][n];
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                new_matrix[i][j] = matrix[i][j];
                if (j == n - 1) {
                    arr[i] = matrix[i][j];
                }
            }
        }

        if (status == 0) return matrix;

        if (status == 1) {
            for (int i = 0; i < m - 1; i++) {
                for (int k = i + 1; k < m; k++) {
                    if (arr[i] > arr[k]) {
                        int swap = index[i];
                        index[i] = index[k];
                        index[k] = swap;

                        int t = arr[i];
                        arr[i] = arr[k];
                        arr[k] = t;
                    }
                }
            }
            for (int i = 0; i < m; i++) {
                for (int j = 0; j < n; j++) {
                    new_matrix[i][j] = matrix[index[i]][j];
                }
            }
        }

        if (status == 2) {
            for (int i = 0; i < m - 1; i++) {
                for (int k = i + 1; k < m; k++) {
                    int sum1 = 0;
                    int sum2 = 0;
                    for (int j = 1; j < n; j++) {
                        sum1 += new_matrix[i][j];
                        sum2 += new_matrix[k][j];
                    }
                    if (sum1 > sum2) {
                        int swap = index[i];
                        index[i] = index[k];
                        index[k] = swap;
                        for (int j = 0; j < n; j++) {
                            int t = new_matrix[i][j];
                            new_matrix[i][j] = new_matrix[k][j];
                            new_matrix[k][j] = t;
                        }
                    }
                }
            }
        }
        return new_matrix;
    }

    public static int[][] getCountCoupleMatrix(List<Jackpot> jackpotList, int m, int n, int startYear) {
        int[][] matrix = new int[m][n];
        for (int i = 0; i < m; i++) {
            matrix[i][0] = i;
            for (int j = 1; j < n; j++) {
                for (int k = 0; k < jackpotList.size(); k++) {
                    if (i == jackpotList.get(k).getCoupleInt() &&
                            (j - 1 + startYear) == jackpotList.get(k).getDateBase().getYear())
                        matrix[i][j]++;
                }
            }
        }
        return matrix;
    }

    public static int[] getStartAndEndYearFile(Context context) {
        String yearData = IOFileBase.readDataFromFile(context, FileName.JACKPOT_YEARS);
        if (yearData.isEmpty()) return null;
        String[] yearArr = yearData.split("-");
        int[] results = new int[2];
        results[0] = Integer.parseInt(yearArr[0]);
        results[1] = Integer.parseInt(yearArr[yearArr.length - 1]);
        return results;
    }

    public static int getMaxStartNumberOfYears(Context context, int START_NUMBER_OF_YEARS) {
        String yearData = IOFileBase.readDataFromFile(context, FileName.JACKPOT_YEARS);
        if (yearData.isEmpty()) return 0;
        int numberOfYearsFile = yearData.split("-").length;
        return Math.min(numberOfYearsFile, START_NUMBER_OF_YEARS);
    }

    public static int[] getDayNumberByYear(int[][] matrix, int m, int n) {
        if (matrix == null) return null;
        int[] dayNumberArr = new int[n];
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                dayNumberArr[j] += matrix[i][j];
            }
        }
        return dayNumberArr;
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

    public static List<JackpotNextDay> getJackpotNextDayList(List<Jackpot> jackpotList, int lastCouple) {
        List<JackpotNextDay> jackpotNextDayList = new ArrayList<>();
        for (int i = jackpotList.size() - 2; i >= 0; i--) {
            if (jackpotList.get(i).getCoupleInt() == lastCouple) {
                Jackpot jackpotFirst = jackpotList.get(i);
                Jackpot jackpotSecond = jackpotList.get(i + 1);
                jackpotNextDayList.add(new JackpotNextDay(jackpotFirst, jackpotSecond));
            }
        }
        return jackpotNextDayList;
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

    public static HeadTail getHeadAndTaiFromPreviousDaySCouple(List<Jackpot> jackpotList, int number, int type) {
        if (jackpotList.isEmpty()) return null;
        int[] headArr = new int[10];
        int[] tailArr = new int[10];
        int runningSize = Math.min(jackpotList.size(), 150);
        for (int i = 1; i < runningSize; i++) {
            if (type == 1) {
                if (jackpotList.get(i).getCouple().getFirst() == number) {
                    Couple couple = jackpotList.get(i - 1).getCouple();
                    headArr[couple.getFirst()]++;
                    tailArr[couple.getSecond()]++;
                }
            }
            if (type == 2) {
                if (jackpotList.get(i).getCouple().getSecond() == number) {
                    Couple couple = jackpotList.get(i - 1).getCouple();
                    headArr[couple.getFirst()]++;
                    tailArr[couple.getSecond()]++;
                }
            }
        }
        List<BSingle> headList = new ArrayList<>();
        List<BSingle> tailList = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            headList.add(new BSingle(i, headArr[i]));
            tailList.add(new BSingle(i, tailArr[i]));
        }

        headList.sort(new Comparator<BSingle>() {
            @Override
            public int compare(BSingle o1, BSingle o2) {
                return Integer.compare(o2.getLevel(), o1.getLevel());
            }
        });

        tailList.sort(new Comparator<BSingle>() {
            @Override
            public int compare(BSingle o1, BSingle o2) {
                return Integer.compare(o2.getLevel(), o1.getLevel());
            }
        });

        int countHead = 0;
        List<BSingle> filteredHeadList = new ArrayList<>();
        for (int i = 0; i < headList.size(); i++) {
            filteredHeadList.add(headList.get(i));
            countHead++;
            if (countHead > 3) break;
        }

        int countTail = 0;
        List<BSingle> filteredTailList = new ArrayList<>();
        for (int i = 0; i < tailList.size(); i++) {
            filteredTailList.add(tailList.get(i));
            countTail++;
            if (countTail > 3) break;
        }

        return new HeadTail(filteredHeadList, filteredTailList);
    }

    public static List<Jackpot> getJackpotListLastMonth(List<Jackpot> jackpotList, List<DateBase> dateBases) {
        if (jackpotList.isEmpty()) return new ArrayList<>();
        List<Jackpot> results = new ArrayList<>();
        List<DateBase> distinctList = new ArrayList<>();
        for (DateBase dateBase : dateBases) {
            if (!distinctList.contains(dateBase)) {
                distinctList.add(dateBase);
            }
        }
        int count = 0;
        for (int i = jackpotList.size() - 1; i >= 0; i--) {
            if (distinctList.contains(jackpotList.get(i).getDateBase())) {
                results.add(jackpotList.get(i));
                count++;
            }
            if (count == distinctList.size()) break;
        }
        return results;
    }
}
