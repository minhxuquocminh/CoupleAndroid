package com.example.couple.View.SubScreen;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.HorizontalScrollView;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.couple.Base.Handler.CoupleBase;
import com.example.couple.Base.View.TableLayoutBase;
import com.example.couple.Base.View.WidgetBase;
import com.example.couple.Custom.Const.TimeInfo;
import com.example.couple.Model.DateTime.Date.Cycle.Branch;
import com.example.couple.Model.DateTime.Date.Cycle.YearCycle;
import com.example.couple.Model.UI.RowUI;
import com.example.couple.Model.UI.TableUI;
import com.example.couple.R;
import com.example.couple.ViewModel.SubScreen.CycleByYearViewModel;

import java.util.ArrayList;
import java.util.List;

public class CycleByYearActivity extends AppCompatActivity implements CycleByYearView {
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
        List<RowUI> rows = new ArrayList<>();
        for (int i = 0; i < 13; i++) {
            List<String> cells = new ArrayList<>();
            for (int j = 0; j < 10; j++) {
                YearCycle yearCycle = new YearCycle(1900 + i * 10 + j);
                cells.add(yearCycle.showByCouple());
            }
            rows.add(new RowUI(cells));
        }
        TableUI tableUI = new TableUI(headers, rows);
        TableLayout tableLayout = TableLayoutBase.getTableLayoutWrapContent(this, tableUI);
        hsTable.removeAllViews();
        hsTable.addView(tableLayout);
    }

    @Override
    public void showMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}
