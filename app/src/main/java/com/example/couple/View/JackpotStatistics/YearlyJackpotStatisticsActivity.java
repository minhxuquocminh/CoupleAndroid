package com.example.couple.View.JackpotStatistics;

import android.content.Context;
import android.os.Bundle;
import android.text.InputFilter;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.Toast;

import com.example.couple.Base.View.ActivityBase;
import com.example.couple.Base.View.SpinnerBase;
import com.example.couple.Base.View.Table.TableLayoutBase;
import com.example.couple.Base.View.WidgetBase;
import com.example.couple.Custom.Handler.JackpotHandler;
import com.example.couple.R;
import com.example.couple.ViewModel.JackpotStatistics.CoupleByYearViewModel;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class YearlyJackpotStatisticsActivity extends ActivityBase implements YearlyJackpotStatisticsView {
    EditText edtFilterValue;
    Button tvFilter;
    Spinner spnStatisticType;
    Spinner spnNumberOfYears;
    Spinner spnSortType;
    LinearLayout linearFreqCouple;

    CoupleByYearViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_couple_by_year);

        edtFilterValue = findViewById(R.id.edtFilterValue);
        tvFilter = findViewById(R.id.tvFilter);
        spnStatisticType = findViewById(R.id.spnStatisticType);
        spnNumberOfYears = findViewById(R.id.spnNumberOfYears);
        spnSortType = findViewById(R.id.spnSortType);
        linearFreqCouple = findViewById(R.id.linearFreqCouple);

        viewModel = new CoupleByYearViewModel(this, this);
        setupStatisticTypeSpinner();
        setupNumberOfYearsSpinner();
        setupSortTypeSpinner();
        runStatistic();

        tvFilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                WidgetBase.hideKeyboard(YearlyJackpotStatisticsActivity.this);
                runStatistic();
            }
        });
    }

    private void setupStatisticTypeSpinner() {
        List<String> types = Arrays.asList("S\u1ed1 \u0111\u1ec1", "K\u00e9p b\u1eb1ng", "B\u1ed9",
                "\u0110\u1ea7u", "\u0110u\u00f4i", "T\u1ed5ng");
        SpinnerBase.bindFilterSpinner(this, spnStatisticType, types);
        spnStatisticType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                updateFilterInput(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void setupSortTypeSpinner() {
        List<String> types = Arrays.asList("Nh\u00f3m/S\u1ed1 \u0111\u1ec1", "N\u0103m g\u1ea7n nh\u1ea5t",
                "T\u1ed5ng c\u00e1c n\u0103m");
        SpinnerBase.bindFilterSpinner(this, spnSortType, types);
    }

    private void setupNumberOfYearsSpinner() {
        int maxYears = Math.min(JackpotHandler.getUpdatedYears(this).size(), 7);
        if (maxYears < 1) maxYears = 1;
        List<String> years = new ArrayList<>();
        for (int year = 1; year <= maxYears; year++) {
            years.add(year + "");
        }
        SpinnerBase.bindFilterSpinner(this, spnNumberOfYears, years);
        spnNumberOfYears.setSelection(Math.min(3, maxYears) - 1);
    }

    private void updateFilterInput(int type) {
        edtFilterValue.setText("");
        edtFilterValue.setVisibility(type == CoupleByYearViewModel.TYPE_DOUBLE ? View.GONE : View.VISIBLE);
        if (type == CoupleByYearViewModel.TYPE_NUMBER || type == CoupleByYearViewModel.TYPE_SET) {
            edtFilterValue.setFilters(new InputFilter[]{new InputFilter.LengthFilter(2)});
        } else {
            edtFilterValue.setFilters(new InputFilter[]{new InputFilter.LengthFilter(1)});
        }

        switch (type) {
            case CoupleByYearViewModel.TYPE_NUMBER:
                setFilterHint("S\u1ed1 \u0111\u1ec1");
                break;
            case CoupleByYearViewModel.TYPE_SET:
                setFilterHint("B\u1ed9");
                break;
            case CoupleByYearViewModel.TYPE_HEAD:
                setFilterHint("\u0110\u1ea7u");
                break;
            case CoupleByYearViewModel.TYPE_TAIL:
                setFilterHint("\u0110u\u00f4i");
                break;
            case CoupleByYearViewModel.TYPE_SUM:
                setFilterHint("T\u1ed5ng");
                break;
            default:
                setFilterHint("");
                break;
        }
    }

    private void setFilterHint(String hint) {
        edtFilterValue.setHint(hint);
    }

    private void runStatistic() {
        String value = edtFilterValue.getText().toString().trim();
        int numberOfYears = Integer.parseInt(spnNumberOfYears.getSelectedItem().toString());
        int type = spnStatisticType.getSelectedItemPosition();
        int sortType = spnSortType.getSelectedItemPosition();
        viewModel.getCoupleCountingTable(numberOfYears, value, sortType, type);
    }

    @Override
    public void showMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showCoupleCountingTable(String[][] matrix, int row, int col) {
        TableLayout tableLayout = TableLayoutBase.getTableLayout(this, matrix, row, col, true);
        linearFreqCouple.removeAllViews();
        linearFreqCouple.addView(tableLayout);
    }

    @Override
    public Context getContext() {
        return this;
    }
}
