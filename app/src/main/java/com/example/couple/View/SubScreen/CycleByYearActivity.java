package com.example.couple.View.SubScreen;

import android.os.Bundle;
import android.widget.HorizontalScrollView;
import android.widget.TableLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.couple.Base.View.TableLayoutBase;
import com.example.couple.Custom.Const.TimeInfo;
import com.example.couple.Model.Base.Row;
import com.example.couple.Model.Base.TableByRow;
import com.example.couple.Model.Cycle.YearCycle;
import com.example.couple.R;
import com.example.couple.ViewModel.SubScreen.CycleByYearViewModel;

import java.util.ArrayList;
import java.util.List;

public class CycleByYearActivity extends AppCompatActivity implements CycleByYearView {
    HorizontalScrollView hsTable;

    CycleByYearViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cycle_by_year);

        hsTable = findViewById(R.id.hsTable);

        viewModel = new CycleByYearViewModel(this, this);
        GetCycleByYear();

    }

    private void GetCycleByYear() {
        List<String> headers = TimeInfo.HEAVENLY_STEMS;
        List<Row> rows = new ArrayList<>();
        for (int i = 0; i < 13; i++) {
            List<String> cells = new ArrayList<>();
            for (int j = 0; j < 10; j++) {
                YearCycle yearCycle = new YearCycle(1900 + i * 10 + j);
                cells.add(yearCycle.showByCouple());
            }
            rows.add(new Row(cells));
        }
        TableByRow tableByRow = new TableByRow(headers, rows);
        TableLayout tableLayout = TableLayoutBase.getTableLayoutWrapContent(this, tableByRow);
        hsTable.removeAllViews();
        hsTable.addView(tableLayout);
    }

    @Override
    public void ShowError(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}
