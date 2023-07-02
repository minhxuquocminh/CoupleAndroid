package com.example.couple.Model.Base;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class TableByColumn {
    List<String> headers;
    List<Column> columns;

    public int getRow() {
        int header = headers.isEmpty() ? 0 : 1;
        if (columns.isEmpty()) return header;
        return columns.get(0).getCellList().size() + header;
    }

    public int getCol() {
        return columns.size() < headers.size() ? columns.size() : headers.size();
    }

    public String[][] convertToMatrix() {
        if (columns.isEmpty()) return null;
        int row = getRow();
        int col = getCol();
        String[][] matrix = new String[row][col];
        for (int j = 0; j < col; j++) {
            matrix[0][j] = headers.get(j);
        }
        for (int i = 1; i < row; i++) {
            for (int j = 0; j < col; j++) {
                matrix[i][j] = columns.get(j).getCellList().get(i);
            }
        }
        return matrix;
    }

}
