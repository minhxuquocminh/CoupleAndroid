package com.example.couple.Base.View;

import android.content.Context;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.example.couple.R;

public class TableLayoutBase {

    public static TableLayout getTableLayout(Context context, String[][] matrix, int row, int col) {
        TableLayout tableLayout = new TableLayout(context);
        tableLayout.setLayoutParams(new TableLayout
                .LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        tableLayout.setShrinkAllColumns(true);
        tableLayout.setStretchAllColumns(true);

        TableRow rowHeader = new TableRow(context);
        for (int j = 0; j < col; j++) {
            TextView tvHeader = getHeaderCell(context, matrix[0][j]);
            rowHeader.addView(tvHeader);
        }
        tableLayout.addView(rowHeader);

        for (int i = 1; i < row; i++) {
            TableRow tableRow = new TableRow(context);
            for (int j = 0; j < col; j++) {
                TextView tv = getCell(context, matrix[i][j]);
                tableRow.addView(tv);
            }
            tableLayout.addView(tableRow);
        }
        return tableLayout;
    }

    public static TableLayout getTableLayoutWrapContent(Context context, String[][] matrix, int row, int col) {
        TableLayout tableLayout = new TableLayout(context);
        tableLayout.setLayoutParams(new TableLayout
                .LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));

        TableRow rowHeader = new TableRow(context);
        for (int j = 0; j < col; j++) {
            TextView tvHeader = getHeaderCell(context, matrix[0][j]);
            rowHeader.addView(tvHeader);
        }
        tableLayout.addView(rowHeader);

        for (int i = 1; i < row; i++) {
            TableRow tableRow = new TableRow(context);
            for (int j = 0; j < col; j++) {
                TextView tv = getCell(context, matrix[i][j]);
                tableRow.addView(tv);
            }
            tableLayout.addView(tableRow);
        }
        return tableLayout;
    }

    public static TableLayout getTableLayout(Context context, TableData tableData) {
        TableLayout tableLayout = new TableLayout(context);
        tableLayout.setLayoutParams(new TableLayout
                .LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));

        if (!tableData.getHeaders().isEmpty()) {
            TableRow rowHeader = new TableRow(context);
            for (String header : tableData.getHeaders()) {
                TextView tvHeader = getHeaderCell(context, header);
                rowHeader.addView(tvHeader);
            }
            tableLayout.addView(rowHeader);
        }

        for (RowData row : tableData.getRows()) {
            TableRow tableRow = new TableRow(context);
            for (String cell : row.getCells()) {
                TextView tv = getCell(context, cell);
                tableRow.addView(tv);
            }
            tableLayout.addView(tableRow);
        }

        return tableLayout;
    }

    public static TableLayout getTableLayoutWrapContent(Context context, TableData tableData) {
        TableLayout tableLayout = new TableLayout(context);
        tableLayout.setLayoutParams(new TableLayout
                .LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));

        if (!tableData.getHeaders().isEmpty()) {
            TableRow rowHeader = new TableRow(context);
            for (String header : tableData.getHeaders()) {
                TextView tvHeader = getHeaderCell(context, header);
                rowHeader.addView(tvHeader);
            }
            tableLayout.addView(rowHeader);
        }

        for (RowData row : tableData.getRows()) {
            TableRow tableRow = new TableRow(context);
            for (String cell : row.getCells()) {
                TextView tv = getCell(context, cell);
                tableRow.addView(tv);
            }
            tableLayout.addView(tableRow);
        }
        return tableLayout;
    }

    private static TextView getHeaderCell(Context context, String text) {
        TextView tv = new TextView(context);
        tv.setText(text);
        tv.setTextSize(15);
        tv.setGravity(Gravity.CENTER);
        tv.setPadding(10, 5, 10, 5);
        tv.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
        tv.setTextColor(WidgetBase.getColorId(context, R.color.colorPrimary));
        Drawable background = WidgetBase.getDrawable(context, R.drawable.cell_pink_table);
        tv.setBackground(background);
        return tv;
    }

    // Background is white
    private static TextView getCell(Context context, String text) {
        TextView tv = new TextView(context);
        tv.setText(text);
        tv.setTextSize(15);
        tv.setGravity(Gravity.CENTER);
        tv.setPadding(10, 5, 10, 5);
        tv.setTextColor(WidgetBase.getColorId(context, R.color.colorText));
        Drawable background = WidgetBase.getDrawable(context, R.drawable.cell_white_table);
        tv.setBackground(background);
        return tv;
    }
}
