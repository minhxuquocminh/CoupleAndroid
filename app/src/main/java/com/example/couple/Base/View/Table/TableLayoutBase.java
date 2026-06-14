package com.example.couple.Base.View.Table;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Paint;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.example.couple.Base.View.DialogBase;
import com.example.couple.Base.View.Spacing;
import com.example.couple.Base.View.TextViewBase;
import com.example.couple.Base.View.TextViewPositionManager;
import com.example.couple.Base.View.WidgetBase;
import com.example.couple.Model.Bridge.Bridge;
import com.example.couple.R;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class TableLayoutBase {

    public static TableLayout getTableLayout(Context context, String[][] matrix, int row, int col, boolean isMatchParent) {
        TableLayout tableLayout = new TableLayout(context);
        tableLayout.setBackground(WidgetBase.getDrawable(context, R.drawable.border_table));
        if (isMatchParent) {
            tableLayout.setShrinkAllColumns(true);
            tableLayout.setStretchAllColumns(true);
            tableLayout.setLayoutParams(new TableLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT));
        } else {
            tableLayout.setLayoutParams(new TableLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT));
        }

        TableRow headerRow = new TableRow(context);
        for (int j = 0; j < col; j++) {
            headerRow.addView(getHeaderCell(context, matrix[0][j], j < col - 1, null));
        }
        tableLayout.addView(headerRow);
        tableLayout.addView(getBottomBorder(context));

        for (int i = 1; i < row; i++) {
            TableRow tableRow = new TableRow(context);
            for (int j = 0; j < col; j++) {
                tableRow.addView(getCell(context, matrix[i][j], j < col - 1, null));
            }
            tableLayout.addView(tableRow);
            tableLayout.addView(getBottomBorder(context));
        }

        return tableLayout;
    }

    public static TableLayout getTableLayout(Context context, String[] headers, String[][] matrix,
                                             int row, int col, boolean isMatchParent) {
        TableLayout tableLayout = new TableLayout(context);
        tableLayout.setBackground(WidgetBase.getDrawable(context, R.drawable.border_table));
        if (isMatchParent) {
            tableLayout.setShrinkAllColumns(true);
            tableLayout.setStretchAllColumns(true);
            tableLayout.setLayoutParams(new TableLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT));
        } else {
            tableLayout.setLayoutParams(new TableLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT));
        }

        if (headers != null) {
            TableRow headerRow = new TableRow(context);
            for (int j = 0; j < col; j++) {
                headerRow.addView(getHeaderCell(context, headers[j], j < col - 1, null));
            }
            tableLayout.addView(headerRow);
            tableLayout.addView(getBottomBorder(context));
        }

        for (int i = 0; i < row; i++) {
            TableRow tableRow = new TableRow(context);
            for (int j = 0; j < col; j++) {
                tableRow.addView(getCell(context, matrix[i][j], j < col - 1, null));
            }
            tableLayout.addView(tableRow);
            tableLayout.addView(getBottomBorder(context));
        }

        return tableLayout;
    }

    public static TableLayout getTableLayoutWithNewStyleCell(Context context, String[] headers, String[] rowHeaders, String[][] matrix,
                                                             int row, int col, Map<Integer, TextViewBase> headerManager,
                                                             Map<Integer, TextViewBase> rowHeaderManager, TextViewPositionManager bodyManager,
                                                             boolean isMatchParent) {
        TableLayout tableLayout = new TableLayout(context);
        tableLayout.setBackground(WidgetBase.getDrawable(context, R.drawable.border_table));
        if (isMatchParent) {
            tableLayout.setShrinkAllColumns(true);
            tableLayout.setStretchAllColumns(true);
            tableLayout.setLayoutParams(new TableLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT));
        } else {
            tableLayout.setLayoutParams(new TableLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT));
        }

        if (headers != null) {
            TableRow headerRow = new TableRow(context);
            headerRow.addView(getHeaderCell(context, rowHeaders[0], true, rowHeaderManager.get(0)));
            for (int j = 0; j < col; j++) {
                headerRow.addView(getHeaderCell(context, headers[j], j < col - 1, headerManager.get(j)));
            }
            tableLayout.addView(headerRow);
            tableLayout.addView(getBottomBorder(context));
        }

        for (int i = 0; i < row; i++) {
            TableRow tableRow = new TableRow(context);
            tableRow.addView(getHeaderCell(context, rowHeaders[i + 1], true, rowHeaderManager.get(i + 1)));
            for (int j = 0; j < col; j++) {
                tableRow.addView(getCell(context, matrix[i][j], j < col - 1, bodyManager.getTextViewBase(i, j)));
            }
            tableLayout.addView(tableRow);
            tableLayout.addView(getBottomBorder(context));
        }

        return tableLayout;
    }

    public static TableLayout getTableLayout(Context context, TableData tableData, boolean isMatchParent) {
        TableLayout tableLayout = new TableLayout(context);
        tableLayout.setBackground(WidgetBase.getDrawable(context, R.drawable.border_table));
        if (isMatchParent) {
            tableLayout.setShrinkAllColumns(true);
            tableLayout.setStretchAllColumns(true);
            tableLayout.setLayoutParams(new TableLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT));
        } else {
            tableLayout.setLayoutParams(new TableLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT));
        }

        if (!tableData.getHeaders().isEmpty()) {
            TableRow headerRow = new TableRow(context);
            int count = 0, cellSize = tableData.getHeaders().size();
            for (String header : tableData.getHeaders()) {
                count++;
                headerRow.addView(getHeaderCell(context, header, count != cellSize, null));
            }
            tableLayout.addView(headerRow);
            tableLayout.addView(getBottomBorder(context));
        }

        for (RowData row : tableData.getRows()) {
            TableRow tableRow = new TableRow(context);
            int count = 0, cellSize = row.getCells().size();
            for (String cell : row.getCells()) {
                count++;
                tableRow.addView(getCell(context, cell, count != cellSize, null));
            }
            tableLayout.addView(tableRow);
            tableLayout.addView(getBottomBorder(context));
        }

        return tableLayout;
    }

    public static TableLayout getBridgeHistoryTableLayout(Context context, List<Bridge> bridges, boolean isMatchParent) {
        TableLayout tableLayout = new TableLayout(context);
        tableLayout.setBackground(WidgetBase.getDrawable(context, R.drawable.border_table));
        if (isMatchParent) {
            tableLayout.setShrinkAllColumns(true);
            tableLayout.setStretchAllColumns(true);
            tableLayout.setLayoutParams(new TableLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT));
        } else {
            tableLayout.setLayoutParams(new TableLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT));
        }

        TableRow headerRow = new TableRow(context);
        for (String header : Arrays.asList("Ngày", "Thông tin", "KQ")) {
            headerRow.addView(getHeaderCell(context, header, !"KQ".equals(header), null));
        }
        tableLayout.addView(headerRow);
        tableLayout.addView(getBottomBorder(context));

        for (Bridge bridge : bridges) {
            TableRow tableRow = new TableRow(context);
            tableRow.addView(getBridgeDateCell(context, bridge));
            tableRow.addView(getCell(context, bridge.showCompactInfo().trim(), true, null));
            tableRow.addView(getCell(context, getBridgeResultInfo(bridge), false, null));
            tableLayout.addView(tableRow);
            tableLayout.addView(getBottomBorder(context));
        }

        return tableLayout;
    }

    private static String getBridgeResultInfo(Bridge bridge) {
        return (bridge.isWin() ? "O" : "X") + "-"
                + bridge.getJackpotHistory().getJackpot().getCouple().show();
    }

    public static TableLayout getTableLayoutWithNewStyleRow(Context context, TableData tableData, TextViewBase headerManager,
                                                            Map<Integer, TextViewBase> bodyManager, boolean isMatchParent) {
        TableLayout tableLayout = new TableLayout(context);
        tableLayout.setBackground(WidgetBase.getDrawable(context, R.drawable.border_table));
        if (isMatchParent) {
            tableLayout.setShrinkAllColumns(true);
            tableLayout.setStretchAllColumns(true);
            tableLayout.setLayoutParams(new TableLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT));
        } else {
            tableLayout.setLayoutParams(new TableLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT));
        }

        if (!tableData.getHeaders().isEmpty()) {
            TableRow headerRow = new TableRow(context);
            int col = 0, cellSize = tableData.getHeaders().size();
            for (String header : tableData.getHeaders()) {
                headerRow.addView(getHeaderCell(context, header, col != cellSize - 1, headerManager));
                col++;
            }
            tableLayout.addView(headerRow);
            tableLayout.addView(getBottomBorder(context));
        }

        int row = 0;
        for (RowData rowData : tableData.getRows()) {
            TableRow tableRow = new TableRow(context);
            int col = 0, cellSize = rowData.getCells().size();
            TextViewBase newStyleCell = bodyManager.get(row);
            for (String cell : rowData.getCells()) {
                tableRow.addView(getCell(context, cell, col != cellSize - 1, newStyleCell));
                col++;
            }
            tableLayout.addView(tableRow);
            tableLayout.addView(getBottomBorder(context));
            row++;
        }

        return tableLayout;
    }

    public static TableLayout getPickerTableLayout(Context context, String[][] matrix, Integer[][] idMatrix,
                                                   int row, int col, boolean isMatchParent) {
        TableLayout tableLayout = new TableLayout(context);
        tableLayout.setBackground(WidgetBase.getDrawable(context, R.drawable.border_table));
        if (isMatchParent) {
            tableLayout.setShrinkAllColumns(true);
            tableLayout.setStretchAllColumns(true);
            tableLayout.setLayoutParams(new TableLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT));
        } else {
            tableLayout.setLayoutParams(new TableLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT));
        }

        for (int i = 0; i < row; i++) {
            TableRow tableRow = new TableRow(context);
            for (int j = 0; j < col; j++) {
                tableRow.addView(getPickerCell(context, matrix[i][j], idMatrix[i][j], j < col - 1));
            }
            tableLayout.addView(tableRow);
            tableLayout.addView(getBottomBorder(context));
        }

        return tableLayout;
    }

    public static TableLayout getCoupleTableLayout(Context context, String[][] matrix, Integer[][] idMatrix,
                                                   int row, int col, boolean isMatchParent) {
        TableLayout tableLayout = new TableLayout(context);
        tableLayout.setBackground(WidgetBase.getDrawable(context, R.drawable.border_table));
        if (isMatchParent) {
            tableLayout.setShrinkAllColumns(true);
            tableLayout.setStretchAllColumns(true);
            tableLayout.setLayoutParams(new TableLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT));
        } else {
            tableLayout.setLayoutParams(new TableLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT));
        }

        for (int i = 0; i < row; i++) {
            TableRow tableRow = new TableRow(context);
            for (int j = 0; j < col; j++) {
                tableRow.addView(getCoupleCell(context, matrix[i][j], idMatrix[i][j], j < col - 1));
            }
            tableLayout.addView(tableRow);
            tableLayout.addView(getBottomBorder(context));
        }

        return tableLayout;
    }

    public static TableLayout getPickerTableLayout(Context context, String[] array, Integer[] idArray,
                                                   int size, boolean isMatchParent) {
        TableLayout tableLayout = new TableLayout(context);
        tableLayout.setBackground(WidgetBase.getDrawable(context, R.drawable.border_table));
        if (isMatchParent) {
            tableLayout.setShrinkAllColumns(true);
            tableLayout.setStretchAllColumns(true);
            tableLayout.setLayoutParams(new TableLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT));
        } else {
            tableLayout.setLayoutParams(new TableLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT));
        }

        TableRow tableRow = new TableRow(context);
        for (int i = 0; i < size; i++) {
            tableRow.addView(getPickerCell(context, array[i], idArray[i], i < size - 1));
        }
        tableLayout.addView(tableRow);

        return tableLayout;
    }

    public static TableLayout getPickerTableLayoutHaveSuperscript(Context context, Integer[] cellIdArray, String[] array,
                                                                  Integer[] idArray, String[] scriptArray,
                                                                  Integer[] scriptIdArray, int size, boolean isMatchParent) {
        TableLayout tableLayout = new TableLayout(context);
        tableLayout.setBackground(WidgetBase.getDrawable(context, R.drawable.border_table));
        if (isMatchParent) {
            tableLayout.setShrinkAllColumns(true);
            tableLayout.setStretchAllColumns(true);
            tableLayout.setLayoutParams(new TableLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT));
        } else {
            tableLayout.setLayoutParams(new TableLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT));
        }

        TableRow tableRow = new TableRow(context);
        for (int i = 0; i < size; i++) {
            tableRow.addView(getCellHaveSuperscript(context, cellIdArray[i],
                    array[i], idArray[i], scriptArray[i], scriptIdArray[i], i < size - 1));
        }
        tableLayout.addView(tableRow);

        return tableLayout;
    }

    private static FrameLayout getCoupleCell(Context context, String text, Integer id, boolean showRightBorder) {
        FrameLayout cellContainer = new FrameLayout(context);
        TableRow.LayoutParams cellParams = new TableRow.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        cellContainer.setLayoutParams(cellParams);
        cellContainer.addView(TextViewBase.getCoupleTextView(context, text, id));

        if (showRightBorder) {
            View borderRight = new View(context);
            FrameLayout.LayoutParams borderParams = new FrameLayout.LayoutParams(
                    1, FrameLayout.LayoutParams.MATCH_PARENT);
            borderParams.gravity = Gravity.END;
            borderRight.setLayoutParams(borderParams);
            borderRight.setBackgroundColor(WidgetBase.getColorId(context, R.color.colorDivider));
            cellContainer.addView(borderRight);
        }

        return cellContainer;
    }

    private static FrameLayout getPickerCell(Context context, String text, Integer id, boolean showRightBorder) {
        FrameLayout cellContainer = new FrameLayout(context);
        TableRow.LayoutParams cellParams = new TableRow.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        cellContainer.setLayoutParams(cellParams);
        cellContainer.addView(TextViewBase.getPickerTextView(context, text, id));

        if (showRightBorder) {
            View borderRight = new View(context);
            FrameLayout.LayoutParams borderParams = new FrameLayout.LayoutParams(
                    1, FrameLayout.LayoutParams.MATCH_PARENT);
            borderParams.gravity = Gravity.END;
            borderRight.setLayoutParams(borderParams);
            borderRight.setBackgroundColor(WidgetBase.getColorId(context, R.color.colorDivider));
            cellContainer.addView(borderRight);
        }

        return cellContainer;
    }

    @SuppressLint("RtlHardcoded")
    private static FrameLayout getCellHaveSuperscript(Context context, Integer idCell, String text,
                                                      Integer id, String scriptText, Integer scriptId, boolean showRightBorder) {
        FrameLayout cellContainer = new FrameLayout(context);
        cellContainer.setId(idCell);
        cellContainer.setLayoutParams(new TableRow.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));

        cellContainer.addView(TextViewBase.builder().context(context).id(id).text(text).textSize(18).gravity(Gravity.CENTER)
                .padding(Spacing.by(40, 30, 40, 30)).textColor(R.color.colorText).build().toTextView());
        cellContainer.addView(TextViewBase.builder().context(context).id(scriptId).text(scriptText).textSize(10).gravity(Gravity.TOP | Gravity.RIGHT)
                .padding(Spacing.by(0, 5, 18, 0)).textColor(R.color.colorTextJackpot).bold(true).build().toTextView());

        if (showRightBorder) {
            View borderRight = new View(context);
            FrameLayout.LayoutParams borderParams = new FrameLayout.LayoutParams(
                    1, FrameLayout.LayoutParams.MATCH_PARENT);
            borderParams.gravity = Gravity.END;
            borderRight.setLayoutParams(borderParams);
            borderRight.setBackgroundColor(WidgetBase.getColorId(context, R.color.colorDivider));
            cellContainer.addView(borderRight);
        }

        return cellContainer;
    }


    private static FrameLayout getCell(Context context, String text, boolean showRightBorder, TextViewBase newCell) {
        FrameLayout cellContainer = new FrameLayout(context);
        TableRow.LayoutParams cellParams = new TableRow.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        cellContainer.setLayoutParams(cellParams);

        if (newCell != null) {
            newCell.setText(text);
            cellContainer.addView(newCell.toTextView());
        } else {
            cellContainer.addView(TextViewBase.builder().context(context).text(text).textSize(15)
                    .gravity(Gravity.CENTER).padding(Spacing.by(20, 10, 20, 10))
                    .textColor(R.color.colorText).build().toTextView());
        }

        if (showRightBorder) {
            View borderRight = new View(context);
            FrameLayout.LayoutParams borderParams = new FrameLayout.LayoutParams(
                    1, FrameLayout.LayoutParams.MATCH_PARENT);
            borderParams.gravity = Gravity.END;
            borderRight.setLayoutParams(borderParams);
            borderRight.setBackgroundColor(WidgetBase.getColorId(context, R.color.colorDivider));
            cellContainer.addView(borderRight);
        }

        return cellContainer;
    }

    private static FrameLayout getBridgeDateCell(Context context, Bridge bridge) {
        FrameLayout cellContainer = new FrameLayout(context);
        TableRow.LayoutParams cellParams = new TableRow.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        cellContainer.setLayoutParams(cellParams);

        TextView textView = TextViewBase.builder()
                .context(context)
                .text(bridge.getJackpotHistory().getJackpot().getDateBase().showDDMM("/"))
                .textSize(15)
                .gravity(Gravity.CENTER)
                .padding(Spacing.by(20, 10, 20, 10))
                .textColor(R.color.colorTextLink)
                .bold(true)
                .build()
                .toTextView();
        textView.setPaintFlags(textView.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
        textView.setOnClickListener(v -> DialogBase.showBasic(
                context,
                bridge.getType().name,
                bridge.showDetailInfo()
        ));
        cellContainer.addView(textView);

        View borderRight = new View(context);
        FrameLayout.LayoutParams borderParams = new FrameLayout.LayoutParams(
                1, FrameLayout.LayoutParams.MATCH_PARENT);
        borderParams.gravity = Gravity.END;
        borderRight.setLayoutParams(borderParams);
        borderRight.setBackgroundColor(WidgetBase.getColorId(context, R.color.colorDivider));
        cellContainer.addView(borderRight);

        return cellContainer;
    }

    private static FrameLayout getHeaderCell(Context context, String text, boolean showRightBorder, TextViewBase newCell) {
        FrameLayout cellContainer = new FrameLayout(context);
        TableRow.LayoutParams cellParams = new TableRow.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        cellContainer.setLayoutParams(cellParams);

        if (newCell != null) {
            newCell.setText(text);
            cellContainer.addView(newCell.toTextView());
        } else {
            cellContainer.addView(TextViewBase.builder().context(context).text(text).textSize(15).gravity(Gravity.CENTER)
                    .padding(Spacing.by(20, 10, 20, 10)).bold(true)
                    .textColor(R.color.colorPrimary).build().toTextView());
        }

        if (showRightBorder) {
            View borderRight = new View(context);
            FrameLayout.LayoutParams borderParams = new FrameLayout.LayoutParams(
                    1, FrameLayout.LayoutParams.MATCH_PARENT);
            borderParams.gravity = Gravity.END;
            borderRight.setLayoutParams(borderParams);
            borderRight.setBackgroundColor(WidgetBase.getColorId(context, R.color.colorDivider));
            cellContainer.addView(borderRight);
        }

        return cellContainer;
    }

    private static View getBottomBorder(Context context) {
        View borderBottom = new View(context);
        TableLayout.LayoutParams params = new TableLayout.LayoutParams(
                TableLayout.LayoutParams.MATCH_PARENT, 1);
        borderBottom.setLayoutParams(params);
        borderBottom.setBackgroundColor(WidgetBase.getColorId(context, R.color.colorDivider));
        return borderBottom;
    }

}
