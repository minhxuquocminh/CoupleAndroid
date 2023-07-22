package com.example.couple.Base.View;

import android.content.Context;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.example.couple.Model.UI.Row;
import com.example.couple.Model.UI.TableByColumn;
import com.example.couple.Model.UI.TableByRow;
import com.example.couple.R;

public class TableLayoutBase {

    public static TableLayout getTableLayout(Context context, String matrix[][], int row, int col) {
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

    public static TableLayout getTableLayout(Context context, TableByRow tableByRow) {
        TableLayout tableLayout = new TableLayout(context);
        tableLayout.setLayoutParams(new TableLayout
                .LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));

        if (!tableByRow.getHeaders().isEmpty()) {
            TableRow rowHeader = new TableRow(context);
            for (String header : tableByRow.getHeaders()) {
                TextView tvHeader = getHeaderCell(context, header);
                rowHeader.addView(tvHeader);
            }
        }

        for (Row row : tableByRow.getRows()) {
            TableRow tableRow = new TableRow(context);
            for (String cell : row.getCells()) {
                TextView tv = getCell(context, cell);
                tableRow.addView(tv);
            }
            tableLayout.addView(tableRow);
        }

        return tableLayout;
    }

    public static TableLayout getTableLayoutWrapContent(Context context, TableByRow tableByRow) {
        TableLayout tableLayout = new TableLayout(context);
        tableLayout.setLayoutParams(new TableLayout
                .LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));

        if (!tableByRow.getHeaders().isEmpty()) {
            TableRow rowHeader = new TableRow(context);
            for (String header : tableByRow.getHeaders()) {
                TextView tvHeader = getHeaderCell(context, header);
                rowHeader.addView(tvHeader);
            }
        }

        for (Row row : tableByRow.getRows()) {
            TableRow tableRow = new TableRow(context);
            for (String cell : row.getCells()) {
                TextView tv = getCell(context, cell);
                tableRow.addView(tv);
            }
            tableLayout.addView(tableRow);
        }
        return tableLayout;
    }

    public static TableLayout getTableLayout(Context context, TableByColumn tableByColumn) {
        TableLayout tableLayout = new TableLayout(context);
        tableLayout.setLayoutParams(new TableLayout
                .LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        tableLayout.setShrinkAllColumns(true);
        tableLayout.setStretchAllColumns(true);

        String matrix[][] = tableByColumn.convertToMatrix();
        TableRow rowHeader = new TableRow(context);
        for (int j = 0; j < tableByColumn.getCol(); j++) {
            TextView tvHeader = getHeaderCell(context, matrix[0][j]);
            rowHeader.addView(tvHeader);
        }

        for (int i = 1; i < tableByColumn.getRow(); i++) {
            TableRow row = new TableRow(context);
            for (int j = 0; j < tableByColumn.getCol(); j++) {
                TextView tv = getCell(context, matrix[i][j]);
                row.addView(tv);
            }
            tableLayout.addView(row);
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
