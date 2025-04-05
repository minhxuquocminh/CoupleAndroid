package com.example.couple.Base.View.Table;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;

@Getter
public class TableData {
    List<String> headers;
    List<RowData> rows;

    public TableData() {
        this.headers = new ArrayList<>();
        this.rows = new ArrayList<>();
    }

    public void createHeaders(List<String> headers) {
        this.headers = headers;
    }

    public void addRow(RowData rowData) {
        rows.add(rowData);
    }
}
