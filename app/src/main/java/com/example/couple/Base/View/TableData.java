package com.example.couple.Base.View;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class TableData {
    List<String> headers;
    List<RowData> rows;
}
