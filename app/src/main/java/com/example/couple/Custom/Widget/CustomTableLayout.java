package com.example.couple.Custom.Widget;

import android.content.Context;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.example.couple.Base.Handler.IOFileBase;
import com.example.couple.Base.Handler.SingleBase;
import com.example.couple.Base.View.WidgetBase;
import com.example.couple.Custom.Const.Const;
import com.example.couple.Custom.Const.FileName;
import com.example.couple.Custom.Const.IdStart;
import com.example.couple.Custom.Handler.Bridge.BCoupleBridgeHandler;
import com.example.couple.Model.Display.BCouple;
import com.example.couple.Model.Display.JackpotNextDay;
import com.example.couple.Model.Display.NearestTime;
import com.example.couple.Model.Origin.Couple;
import com.example.couple.Model.Origin.Jackpot;
import com.example.couple.Model.Time.DateBase;
import com.example.couple.R;

import java.util.List;

public class CustomTableLayout {

    public static TableLayout getCoupleByWeekTableLayout(Context context, List<Jackpot> reverseJackpotList, int numberOfWeek) {
        TableLayout tableLayout = new TableLayout(context);
        tableLayout.setLayoutParams(new TableLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT));
        tableLayout.setShrinkAllColumns(true);
        tableLayout.setStretchAllColumns(true);

        int count = 0;
        TableRow row = new TableRow(context);
        int start = numberOfWeek * Const.DAY_OF_WEEK < reverseJackpotList.size() ?
                numberOfWeek * Const.DAY_OF_WEEK - 1 : reverseJackpotList.size() - 1;
        int monday_start = 0;
        for (int i = start; i >= 0; i--) {
            if (reverseJackpotList.get(i).getDateBase().getDayOfWeek() == 2) {
                monday_start = i;
                break;
            }
        }
        for (int i = monday_start; i >= 0; i--) {
            count++;
            String couple = reverseJackpotList.get(i).getCouple().show();
            int firstNegativeShadow = SingleBase.getNegativeShadow(Integer.parseInt(couple.charAt(0) + ""));
            int secondNegativeShadow = SingleBase.getNegativeShadow(Integer.parseInt(couple.charAt(1) + ""));
            row.addView(CustomLinearLayout.GetItemCoupleByWeekLinearLayout(context,
                    couple, firstNegativeShadow, secondNegativeShadow));
            if (i == 0) {
                int emptyLength = Const.DAY_OF_WEEK - count;
                for (int j = 0; j < emptyLength; j++) {
                    count++;
                    row.addView(CustomLinearLayout.GetEmptyLinearLayout(context));
                }
            }
            if (count == Const.DAY_OF_WEEK) {
                tableLayout.addView(row);
                count = 0;
                row = new TableRow(context);
            }
        }
        return tableLayout;
    }

    public static TableLayout getCoupleTableLayout(Context context, List<Couple> couples, int type) {
        TableLayout tableLayout = new TableLayout(context);
        tableLayout.setLayoutParams(new TableLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT));
        tableLayout.setShrinkAllColumns(true);
        tableLayout.setStretchAllColumns(true);

        for (int i = 0; i < couples.size(); i++) {
            TableRow row = new TableRow(context);
            row.addView(getCellOfCoupleTable(context, couples.get(i).show()));
            tableLayout.addView(row);
        }
        if (type >= 0) {
            TableRow row = new TableRow(context);
            TextView textView = getCellOfCoupleTable(context, "??");
            int idPrediction = IdStart.MY_PREDICTION + type;
            textView.setId(idPrediction);
            row.addView(textView);
            tableLayout.addView(row);
        }
        return tableLayout;
    }

    public static TableLayout getChooseThirdClawTableLayout(Context context) {
        TableLayout tableLayout = new TableLayout(context);
        tableLayout.setLayoutParams(new TableLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT));

        TableRow row = new TableRow(context);
        for (int i = 0; i < 10; i++) {
            row.addView(getCellOfChooseThirdClawTable(context, IdStart.THIRD_CLAW_PARENT + i,
                    IdStart.THIRD_CLAW + i, i + "", IdStart.THIRD_CLAW_COUNTER + i));
        }
        tableLayout.addView(row);
        return tableLayout;
    }


    public static TableLayout getChooseNumberTableLayout(Context context) {
        TableLayout tableLayout = new TableLayout(context);
        tableLayout.setLayoutParams(new TableLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT));

        TableRow row = new TableRow(context);
        for (int i = 0; i < 10; i++) {
            row.addView(getCellOfChooseNumberTable(context, (IdStart.TOUCH + i), i + ""));
        }
        tableLayout.addView(row);
        return tableLayout;
    }


    public static TableLayout getFilteredNumberTableLayout(Context context, int touch) {
        TableLayout tableLayout = new TableLayout(context);
        tableLayout.setLayoutParams(new TableLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT));

        TableRow row = new TableRow(context);
        TableRow row2 = new TableRow(context);
        for (int i = 0; i < 10; i++) {
            String text = (touch * 10 + i) < 10 ? "0" + (touch * 10 + i) : "" + (touch * 10 + i);
            row.addView(getCellOfNumberTable(context, IdStart.TOUCHED_NUMBER + (touch * 10 + i), text));
            if ((touch * 10 + i) == (i * 10 + touch)) continue;
            String text2 = (i * 10 + touch) < 10 ? "0" + (i * 10 + touch) : "" + (i * 10 + touch);
            row2.addView(getCellOfNumberTable(context, IdStart.TOUCHED_NUMBER + (i * 10 + touch), text2));
        }
        tableLayout.addView(row);
        tableLayout.addView(row2);
        return tableLayout;
    }

    public static TableLayout getNumberByThirdClawTableLayout(Context context) {
        TableLayout tableLayout = new TableLayout(context);
        tableLayout.setLayoutParams(new TableLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT));

        for (int i = 0; i < 10; i++) {
            TableRow row = new TableRow(context);
            for (int j = 0; j < 10; j++) {
                String text = i == 0 ? "0" + (10 * i + j) : "" + (10 * i + j);
                row.addView(getCellOfNumberTable(context, IdStart.NUMBERS_BY_THIRD_CLAW + (10 * i + j), text));
            }
            tableLayout.addView(row);
        }
        return tableLayout;
    }


    public static TableLayout getNumberTableLayout(Context context) {
        TableLayout tableLayout = new TableLayout(context);
        tableLayout.setLayoutParams(new TableLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT));

        for (int i = 0; i < 10; i++) {
            TableRow row = new TableRow(context);
            for (int j = 0; j < 10; j++) {
                String text = i == 0 ? "0" + (10 * i + j) : "" + (10 * i + j);
                row.addView(getCellOfNumberTable(context, (10 * i + j), text));
            }
            tableLayout.addView(row);
        }
        return tableLayout;
    }

    public static TableLayout getChooseHeadTailTableLayout(Context context) {
        TableLayout tableLayout = new TableLayout(context);
        tableLayout.setLayoutParams(new TableLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT));

        TableRow row = new TableRow(context);
        for (int i = 0; i < 10; i++) {
            row.addView(getCellOfNumberTable(context, (IdStart.HEAD + i), i + "x"));
        }
        tableLayout.addView(row);
        TableRow row2 = new TableRow(context);
        for (int i = 0; i < 10; i++) {
            row2.addView(getCellOfNumberTable(context, (IdStart.TAIL + i), "x" + i));
        }
        tableLayout.addView(row2);
        return tableLayout;
    }

    public static TableLayout getNearestTimeTableLayout(Context context, List<NearestTime> nearestTimeList) {
        TableLayout tableLayout = new TableLayout(context);
        tableLayout.setLayoutParams(new TableLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT));
        tableLayout.setShrinkAllColumns(true);
        tableLayout.setStretchAllColumns(true);

        TableRow rowHeader = new TableRow(context);
        rowHeader.addView(getHeaderCell(context, "Số"));
        rowHeader.addView(getHeaderCell(context, "Thuộc"));
        rowHeader.addView(getHeaderCell(context, "Số lần xuất hiện"));
        rowHeader.addView(getHeaderCell(context, "Số ngày chưa về"));
        tableLayout.addView(rowHeader);

        for (NearestTime nearestTime : nearestTimeList) {
            TableRow row = new TableRow(context);
            String numberStr = nearestTime.getNumber() == 0 &&
                    nearestTime.getType().equals(Const.DOUBLE) ? "00" : nearestTime.getNumber() + "";
            String dayNumberBeforeStr = nearestTime.getDayNumberBefore() == Const.MAX_DAY_NUMBER_BEFORE ?
                    "MAX" : nearestTime.getDayNumberBefore() + "";
            row.addView(getCellInEvenRows(context, numberStr));
            row.addView(getCellInEvenRows(context, nearestTime.getType()));
            row.addView(getCellInEvenRows(context, nearestTime.getAppearanceTimes() + ""));
            row.addView(getCellInEvenRows(context, dayNumberBeforeStr));
            tableLayout.addView(row);
        }
        return tableLayout;
    }

    public static TableLayout getJackpotNextDayTableLayout(Context context, List<JackpotNextDay> jackpotNextDayList) {
        TableLayout tableLayout = new TableLayout(context);
        tableLayout.setLayoutParams(new TableLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT));
        tableLayout.setShrinkAllColumns(true);
        tableLayout.setStretchAllColumns(true);

        TableRow rowHeader = new TableRow(context);
        rowHeader.addView(getHeaderCell(context, "Ngày hôm trước"));
        rowHeader.addView(getHeaderCell(context, "Giải Đặc biệt"));
        rowHeader.addView(getHeaderCell(context, "Giải Đặc biệt"));
        rowHeader.addView(getHeaderCell(context, "Ngày hôm sau"));
        tableLayout.addView(rowHeader);

        for (JackpotNextDay jackpotNextDay : jackpotNextDayList) {
            TableRow row = new TableRow(context);
            Jackpot jackpot1 = jackpotNextDay.getJackpotFirst();
            Jackpot jackpot2 = jackpotNextDay.getJackpotSecond();
            row.addView(getCellInEvenRows(context, jackpot1.getDateBase().showFullChars() + ""));
            row.addView(getCellInEvenRows(context, jackpot1.getJackpot() + ""));
            row.addView(getCellInEvenRows(context, jackpot2.getJackpot() + ""));
            row.addView(getCellInEvenRows(context, jackpot2.getDateBase().showFullChars() + ""));
            tableLayout.addView(row);
        }
        return tableLayout;
    }

    public static TableLayout getCountSameDoubleTableLayout(Context context, int[][] matrix,
                                                            int m, int n, int startingYear, int[] dayNumberArr) {
        TableLayout tableLayout = new TableLayout(context);
        tableLayout.setLayoutParams(new TableLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT));
        tableLayout.setShrinkAllColumns(true);
        tableLayout.setStretchAllColumns(true);

        // n = numberOfYears + 1
        TableRow rowHeader = new TableRow(context);
        rowHeader.addView(getHeaderCell(context, "Đề"));
        for (int j = startingYear; j < startingYear + n - 1; j++) {
            int showYear = n - 1 > 5 ? j % 100 : j;
            rowHeader.addView(getHeaderCell(context, showYear + ""));
        }
        rowHeader.addView(getHeaderCell(context, "Tổng"));
        tableLayout.addView(rowHeader);
        int[] sumK = new int[n];
        for (int i = 0; i < m; i++) {
            TableRow row = new TableRow(context);
            int sumOfRow = 0;
            for (int j = 0; j < n; j++) {
                if (j != 0) {
                    sumOfRow += matrix[i][j];
                    sumK[j] += matrix[i][j];
                }
                row.addView(getCellInEvenRows(context, matrix[i][j] + ""));
            }
            row.addView(getCellInEvenRows(context, sumOfRow + ""));
            tableLayout.addView(row);
        }
        TableRow rowFooter1 = new TableRow(context);
        TableRow rowFooter2 = new TableRow(context);
        TableRow rowFooter3 = new TableRow(context);
        rowFooter1.addView(getCellInEvenRows(context, "(K)"));
        rowFooter2.addView(getCellInEvenRows(context, "(N)"));
        rowFooter3.addView(getCellInEvenRows(context, "(f)"));
        int sum_rowSumK = 0;
        int sum_rowSumN = 0;
        for (int j = 1; j < n; j++) {
            sum_rowSumK += sumK[j];
            sum_rowSumN += dayNumberArr[j];
            rowFooter1.addView(getCellInEvenRows(context, sumK[j] + ""));
            rowFooter2.addView(getCellInEvenRows(context, dayNumberArr[j] + ""));
            double freq = sumK[j] == 0 ? 0 : (double) Math.round(dayNumberArr[j] * 10 / sumK[j]) / 10;
            rowFooter3.addView(getCellInEvenRows(context, freq + ""));
        }
        rowFooter1.addView(getCellInEvenRows(context, sum_rowSumK + ""));
        rowFooter2.addView(getCellInEvenRows(context, sum_rowSumN + ""));
        double freqOfSum = sum_rowSumK == 0 ? 0 : (double) Math.round(sum_rowSumN * 10 / sum_rowSumK) / 10;
        rowFooter3.addView(getCellInEvenRows(context, freqOfSum + ""));
        tableLayout.addView(rowFooter1);
        tableLayout.addView(rowFooter2);
        tableLayout.addView(rowFooter3);

        return tableLayout;
    }

    public static TableLayout getCountCoupleTableLayout(Context context, int[][] matrix,
                                                        int m, int n, int startingYear) {
        TableLayout tableLayout = new TableLayout(context);
        tableLayout.setLayoutParams(new TableLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT));
        tableLayout.setShrinkAllColumns(true);
        tableLayout.setStretchAllColumns(true);

        // n = numberOfYears + 1
        TableRow rowHeader = new TableRow(context);
        rowHeader.addView(getHeaderCell(context, "Đề"));
        for (int j = startingYear; j < startingYear + n - 1; j++) {
            int showYear = n - 1 > 5 ? j % 100 : j;
            rowHeader.addView(getHeaderCell(context, showYear + ""));
        }
        rowHeader.addView(getHeaderCell(context, "Tổng"));
        tableLayout.addView(rowHeader);
        for (int i = 0; i < m; i++) {
            TableRow row = new TableRow(context);
            int sumOfRow = 0;
            for (int j = 0; j < n; j++) {
                if (j != 0) sumOfRow += matrix[i][j];
                row.addView(getCellInEvenRows(context, matrix[i][j] + ""));
            }
            row.addView(getCellInEvenRows(context, sumOfRow + ""));
            tableLayout.addView(row);
        }
        return tableLayout;
    }

    public static TableLayout getNormalTableLayout(Context context, String[][] matrix, int m, int n) {
        TableLayout tableLayout = new TableLayout(context);
        tableLayout.setLayoutParams(new TableLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        tableLayout.setShrinkAllColumns(true);
        tableLayout.setStretchAllColumns(true);

        for (int i = 0; i < m; i++) {
            TableRow row = new TableRow(context);

            TextView tvHeader = getCellInEvenRows(context, matrix[i][0]);
            row.addView(tvHeader);

            for (int j = 1; j < n; j++) {
                TextView tv = new TextView(context);
                if (j % 2 != 0) {
                    tv = getCellInOddRows(context, matrix[i][j]);
                } else {
                    tv = getCellInEvenRows(context, matrix[i][j]);
                }
                row.addView(tv);
            }
            tableLayout.addView(row);
        }
        return tableLayout;
    }

    public static TableLayout getPaddingTableLayout(Context context, String[][] matrix, int m, int n) {
        TableLayout tableLayout = new TableLayout(context);
        tableLayout.setLayoutParams(new TableLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        tableLayout.setShrinkAllColumns(true);
        tableLayout.setStretchAllColumns(true);

        for (int i = 0; i < m; i++) {
            TableRow row = new TableRow(context);

            TextView tvHeader = getCellInEvenRows(context, matrix[i][0]);
            row.addView(tvHeader);

            for (int j = 1; j < n; j++) {
                TextView tv = new TextView(context);
                if (j % 2 != 0) {
                    tv = getPaddingCellInOddRows(context, matrix[i][j]);
                } else {
                    tv = getPaddingCellInEvenRows(context, matrix[i][j]);
                }
                row.addView(tv);
            }
            tableLayout.addView(row);
        }
        return tableLayout;
    }

    public static TableLayout getBalanceCoupleTableLayout(Context context, List<Jackpot> jackpotList, int dayNumber) {
        TableLayout tableLayout = new TableLayout(context);
        tableLayout.setLayoutParams(new TableLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        tableLayout.setShrinkAllColumns(true);
        tableLayout.setStretchAllColumns(true);

        TableRow rowHeader = new TableRow(context);
        rowHeader.addView(getHeaderCell(context, "D/M"));
        rowHeader.addView(getHeaderCell(context, "B1"));
        rowHeader.addView(getHeaderCell(context, "B2"));
        rowHeader.addView(getHeaderCell(context, "B3"));
        rowHeader.addView(getHeaderCell(context, "B4"));
        rowHeader.addView(getHeaderCell(context, "+/-"));
        rowHeader.addView(getHeaderCell(context, "KQ"));
        rowHeader.addView(getHeaderCell(context, "CL"));
        tableLayout.addView(rowHeader);

        int start = (dayNumber - 1 + 2 < jackpotList.size() - 1) ?
                dayNumber - 1 + 2 : jackpotList.size() - 1;
        // -1 in below is position which we don't know digit 2d
        // we have more 1 row is -1 position

        for (int i = start; i >= -1 + 2; i--) {
            TableRow row = new TableRow(context);

            Couple dgFirst = jackpotList.get(i).getCouple(); //the day before 2 day
            Couple dgSecond = jackpotList.get(i - 1).getCouple(); // the day before 1 day
            List<BCouple> balanceBCouples = BCoupleBridgeHandler.GetBalanceCouples(
                    dgFirst.toBCouple(), dgSecond.toBCouple());

            //Current row is at i - 2 position

            TextView tvDay = new TextView(context);
            if (i - 2 >= 0) {
                tvDay = getCellInEvenRows(context,
                        jackpotList.get(i - 2).getDateBase().getDay() + "/" +
                                jackpotList.get(i - 2).getDateBase().getMonth());
                if (jackpotList.get(i - 2).getDateBase().isItOnSunday()) {
                    tvDay.setBackgroundResource(R.drawable.cell_row_sunday);
                }
            } else {
                DateBase currentDate = jackpotList.get(i - 1).getDateBase();
                DateBase new_date = currentDate.plusDays(1);
                tvDay = getCellInEvenRows(context, new_date.getDay() + "/" + new_date.getMonth());
                if (jackpotList.get(i - 1).getDateBase().isItOnSaturday()) {
                    tvDay.setBackgroundResource(R.drawable.cell_row_sunday);
                }
            }
            row.addView(tvDay);

            // balance digit2D set
            for (BCouple BCouple : balanceBCouples) {
                TextView tv = getCellInEvenRows(context, BCouple.showDot());
                if (i - 2 >= 0 && jackpotList.get(i - 2).getDateBase().isItOnSunday()) {
                    tv.setBackgroundResource(R.drawable.cell_row_sunday);
                }

                if (i - 2 == -1 && jackpotList.get(i - 1).getDateBase().isItOnSaturday()) {
                    tv.setBackgroundResource(R.drawable.cell_row_sunday);
                }

                row.addView(tv);
            }

            // up down status
            int sum = dgFirst.plus(dgSecond);
            int diff = dgFirst.sub(dgSecond);
            TextView tvUpDown = getCellInEvenRows(context, "{" + sum + "," + diff + "}");
            if (i - 2 >= 0 && jackpotList.get(i - 2).getDateBase().isItOnSunday()) {
                tvUpDown.setBackgroundResource(R.drawable.cell_row_sunday);
            }
            if (i - 2 == -1 && jackpotList.get(i - 1).getDateBase().isItOnSaturday()) {
                tvUpDown.setBackgroundResource(R.drawable.cell_row_sunday);
            }
            row.addView(tvUpDown);

            // result couple
            if (i - 2 >= 0) {
                TextView tv1 = getCellInEvenRows(context, jackpotList.get(i - 2).getCouple().showDot());
                TextView tv2 = getCellInEvenRows(context, jackpotList.get(i - 2).getCouple().getCL());
                if (jackpotList.get(i - 2).getDateBase().isItOnSunday()) {
                    tv1.setBackgroundResource(R.drawable.cell_row_sunday);
                    tv2.setBackgroundResource(R.drawable.cell_row_sunday);
                }
                row.addView(tv1);
                row.addView(tv2);
            } else {
                String number = "xx";
                String cl = "xx";
                String data = IOFileBase.readDataFromFile(context, FileName.SELECTED_NUMBER);
                if (!data.equals("")) {
                    int first = Integer.parseInt(data.charAt(0) + "");
                    int second = Integer.parseInt(data.charAt(1) + "");
                    if (first % 2 == 0) {
                        cl = "C";
                    } else {
                        cl = "L";
                    }
                    if (second % 2 == 0) {
                        cl += "C";
                    } else {
                        cl += "L";
                    }
                    number = first + "." + second;
                }
                TextView tv1 = getCellInEvenRows(context, number);
                TextView tv2 = getCellInEvenRows(context, cl);
                if (jackpotList.get(i - 1).getDateBase().isItOnSaturday()) {
                    tv1.setBackgroundResource(R.drawable.cell_row_sunday);
                    tv2.setBackgroundResource(R.drawable.cell_row_sunday);
                }
                row.addView(tv1);
                row.addView(tv2);
            }

            tableLayout.addView(row);

        }
        return tableLayout;
    }

    public static TableLayout getJackpotMatrixByYear(Context context, String[][] matrix, int m, int n, int year) {
        TableLayout tableLayout = new TableLayout(context);
        tableLayout.setLayoutParams(new TableLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT));

        TableRow rowHeader = new TableRow(context);
        rowHeader.addView(getMatrixByYearHeaderCell(context, "D/M"));
        for (int i = 1; i <= 12; i++) {
            rowHeader.addView(getMatrixByYearHeaderCell(context, i + ""));
        }
        tableLayout.addView(rowHeader);

        for (int i = 0; i < m; i++) {
            TableRow row = new TableRow(context);
            row.addView(getMatrixByYearCell(context, (i + 1) + ""));
            for (int j = 0; j < n; j++) {
                TextView tv = getMatrixByYearCell(context, matrix[i][j]);
                DateBase dateBase = new DateBase((i + 1), (j + 1), year);
                if (dateBase.isItOnSunday()) {
                    tv.setBackgroundResource(R.drawable.cell_row_sunday);
                }
                row.addView(tv);
            }
            tableLayout.addView(row);
        }
        return tableLayout;
    }

    private static TextView getMatrixByYearHeaderCell(Context context, String text) {
        TextView tv = new TextView(context);
        tv.setText(text);
        tv.setTextSize(15);
        tv.setGravity(Gravity.CENTER);
        tv.setPadding(10, 5, 10, 5);
        tv.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
        tv.setTextColor(WidgetBase.getColorId(context, R.color.colorPrimary));
        tv.setBackgroundResource(R.drawable.cell_pink_table);
        return tv;
    }

    private static TextView getCellOfCoupleTable(Context context, String text) {
        TextView tv = new TextView(context);
        tv.setText(text);
        tv.setTextSize(18);
        tv.setGravity(Gravity.CENTER);
        tv.setTypeface(tv.getTypeface(), Typeface.BOLD);
        tv.setPadding(20, 15, 20, 15);
        tv.setTextColor(WidgetBase.getColorId(context, R.color.colorTextJackpot));
        tv.setBackgroundResource(R.drawable.cell_pink_table);
        return tv;
    }

    private static FrameLayout getCellOfChooseThirdClawTable(Context context, int idParent,
                                                             int idMain, String text, int idSub) {
        FrameLayout frameLayout = new FrameLayout(context);
        frameLayout.setId(idParent);

        TextView tv = new TextView(context);
        tv.setId(idMain);
        tv.setText(text);
        tv.setTextSize(18);
        tv.setGravity(Gravity.CENTER);
        tv.setPadding(40, 30, 40, 30);
        tv.setTextColor(WidgetBase.getColorId(context, R.color.colorText));
        Drawable background = WidgetBase.getDrawable(context, R.drawable.cell_pink_table);
        tv.setBackground(background);

        TextView tvSub = new TextView(context);
        tvSub.setId(idSub);
        tvSub.setTextSize(10);
        tvSub.setText("0");
        tvSub.setGravity(Gravity.TOP);
        tvSub.setGravity(Gravity.RIGHT);
        tvSub.setPadding(0, 5, 18, 0);
        tvSub.setTextColor(WidgetBase.getColorId(context, R.color.colorTextJackpot));
        tvSub.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));

        frameLayout.addView(tv);
        frameLayout.addView(tvSub);

        return frameLayout;
    }

    private static TextView getCellOfChooseNumberTable(Context context, int id, String text) {
        TextView tv = new TextView(context);
        tv.setId(id);
        tv.setTag(id);
        tv.setText(text);
        tv.setTextSize(18);
        tv.setGravity(Gravity.CENTER);
        tv.setPadding(40, 30, 40, 30);
        tv.setTextColor(WidgetBase.getColorId(context, R.color.colorText));
        Drawable background = WidgetBase.getDrawable(context, R.drawable.cell_pink_table);
        tv.setBackground(background);
        return tv;
    }

    private static TextView getCellOfNumberTable(Context context, int id, String text) {
        TextView tv = new TextView(context);
        tv.setId(id);
        tv.setTag(id);
        tv.setText(text);
        tv.setTextSize(18);
        tv.setGravity(Gravity.CENTER);
        tv.setPadding(35, 30, 35, 30);
        tv.setTextColor(WidgetBase.getColorId(context, R.color.colorText));
        Drawable background = WidgetBase.getDrawable(context, R.drawable.cell_pink_table);
        tv.setBackground(background);
        return tv;
    }

    // TextView Matrix By Year
    private static TextView getMatrixByYearCell(Context context, String text) {
        TextView tv = new TextView(context);
        tv.setText(text);
        tv.setTextSize(15);
        tv.setGravity(Gravity.CENTER);
        tv.setPadding(10, 5, 10, 5);
        tv.setTextColor(WidgetBase.getColorId(context, R.color.colorText));
        Drawable background = WidgetBase.getDrawable(context, R.drawable.cell_pink_table);
        tv.setBackground(background);
        return tv;
    }

    private static TextView getHeaderCell(Context context, String text) {
        TextView tv = new TextView(context);
        tv.setText(text);
        tv.setTextSize(15);
        tv.setGravity(Gravity.CENTER);
        tv.setPadding(0, 5, 0, 5);
        tv.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
        tv.setTextColor(WidgetBase.getColorId(context, R.color.colorPrimary));
        Drawable background = WidgetBase.getDrawable(context, R.drawable.cell_pink_table);
        tv.setBackground(background);
        return tv;
    }

    // Background is white
    private static TextView getCellInOddRows(Context context, String text) {
        TextView tv = new TextView(context);
        tv.setText(text);
        tv.setGravity(Gravity.CENTER);
        tv.setTextColor(WidgetBase.getColorId(context, R.color.colorText));
        Drawable background = WidgetBase.getDrawable(context, R.drawable.cell_white_table);
        tv.setBackground(background);
        return tv;
    }

    // Background is theme
    private static TextView getCellInEvenRows(Context context, String text) {
        TextView tv = new TextView(context);
        tv.setText(text);
        tv.setTextSize(15);
        tv.setGravity(Gravity.CENTER);
        tv.setTextColor(WidgetBase.getColorId(context, R.color.colorText));
        Drawable background = WidgetBase.getDrawable(context, R.drawable.cell_pink_table);
        tv.setBackground(background);
        return tv;
    }

    private static TextView getPaddingCellInEvenRows(Context context, String text) {
        TextView tv = new TextView(context);
        tv.setText(text);
        tv.setGravity(Gravity.CENTER);
        tv.setPadding(5, 5, 5, 5);
        tv.setTextColor(WidgetBase.getColorId(context, R.color.colorText));
        Drawable background = WidgetBase.getDrawable(context, R.drawable.cell_white_table);
        tv.setBackground(background);
        return tv;
    }

    private static TextView getPaddingCellInOddRows(Context context, String text) {
        TextView tv = new TextView(context);
        tv.setText(text);
        tv.setGravity(Gravity.CENTER);
        tv.setPadding(5, 5, 5, 5);
        tv.setTextColor(WidgetBase.getColorId(context, R.color.colorText));
        Drawable background = WidgetBase.getDrawable(context, R.drawable.cell_pink_table);
        tv.setBackground(background);
        return tv;
    }

}
