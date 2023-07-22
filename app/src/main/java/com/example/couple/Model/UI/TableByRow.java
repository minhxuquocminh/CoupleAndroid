package com.example.couple.Model.UI;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class TableByRow {
    List<String> headers;
    List<Row> rows;

    public int getRow() {
        int header = headers.isEmpty() ? 0 : 1;
        return rows.size() + header;
    }

    public int getCol() {
        if (rows.isEmpty()) return headers.size();
        return rows.get(0).getCells().size();
    }

//    public String[][] convertToMatrix() {
//        if (rows.isEmpty() || headers.isEmpty()) return null;
//        int row = getRow();
//        int col = getCol();
//        String[][] matrix = new String[row][col];
//        for (int j = 0; j < col; j++) {
//            matrix[0][j] = headers.get(j);
//        }
//        for (int i = 1; i < row; i++) {
//            for (int j = 0; j < col; j++) {
//                matrix[i][j] = rows.get(i - 1).getCells().get(j);
//            }
//        }
//        return matrix;
//    }
}
