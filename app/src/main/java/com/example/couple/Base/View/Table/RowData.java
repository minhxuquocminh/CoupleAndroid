package com.example.couple.Base.View.Table;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;

@Getter
public class RowData {
    List<String> cells;

    public RowData() {
        this.cells = new ArrayList<>();
    }

    public void addCell(String cell) {
        this.cells.add(cell);
    }
}
