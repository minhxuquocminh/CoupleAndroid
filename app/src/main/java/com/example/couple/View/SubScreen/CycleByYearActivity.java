package com.example.couple.View.SubScreen;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.HorizontalScrollView;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.couple.Base.Handler.CoupleBase;
import com.example.couple.Base.View.Table.RowData;
import com.example.couple.Base.View.Table.TableData;
import com.example.couple.Base.View.Table.TableLayoutBase;
import com.example.couple.Base.View.WidgetBase;
import com.example.couple.Custom.Const.TimeInfo;
import com.example.couple.Base.View.ActivityBase;
import com.example.couple.Model.DateTime.Date.Cycle.Branch;
import com.example.couple.Model.DateTime.Date.Cycle.YearCycle;
import com.example.couple.R;
import com.example.couple.ViewModel.SubScreen.CycleByYearViewModel;

import java.util.ArrayList;
import java.util.List;

public class CycleByYearActivity extends ActivityBase implements CycleByYearView {
    HorizontalScrollView hsTable;
    Spinner spnBranches;
    TextView tvCopy;

    CycleByYearViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cycle_by_year);

        hsTable = findViewById(R.id.hsTable);
        spnBranches = findViewById(R.id.spnBranches);
        tvCopy = findViewById(R.id.tvCopy);

        viewModel = new CycleByYearViewModel(this, this);
        GetCycleByYear();

        List<String> branchesList = new ArrayList<String>();
        for (int i = 0; i < 12; i++) {
            Branch branch = new Branch(i);
            branchesList.add(branch.show());
        }
        ArrayAdapter<?> adapter = new ArrayAdapter<>(this,
                R.layout.custom_item_spinner, R.id.tvItemSpinner, branchesList);
        spnBranches.setAdapter(adapter);

        tvCopy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int position = spnBranches.getSelectedItemPosition();
                int start = TimeInfo.CYCLE_START_YEAR;
                int end = TimeInfo.CURRENT_YEAR;
                List<Integer> numbers = new ArrayList<>();
                for (int i = position; i <= (end - start); i += 12) {
                    numbers.add(i % 100);
                }
                WidgetBase.copyToClipboard(CycleByYearActivity.this,
                        "numbers", CoupleBase.showCoupleNumbers(numbers));
                Toast.makeText(CycleByYearActivity.this, "Đã copy.", Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void GetCycleByYear() {
        List<String> headers = TimeInfo.HEAVENLY_STEMS;
        TableData tableData = new TableData();
        tableData.createHeaders(headers);
        for (int i = 0; i < 13; i++) {
            RowData rowData = new RowData();
            for (int j = 0; j < 10; j++) {
                YearCycle yearCycle = new YearCycle(1900 + i * 10 + j);
                rowData.addCell(yearCycle.showByCouple());
            }
            tableData.addRow(rowData);
        }
        TableLayout tableLayout = TableLayoutBase.getTableLayout(this, tableData, false);
        hsTable.removeAllViews();
        hsTable.addView(tableLayout);
    }

    @Override
    public void showMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public Context getContext() {
        return this;
    }
}
