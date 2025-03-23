package com.example.couple.View.JackpotStatistics;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.couple.Base.View.TableLayoutBase;
import com.example.couple.Base.View.WidgetBase;
import com.example.couple.Custom.Widget.SpeechToTextActivity;
import com.example.couple.R;
import com.example.couple.ViewModel.JackpotStatistics.CoupleByYearViewModel;

public class CoupleByYearActivity extends SpeechToTextActivity implements CoupleByYearView {
    EditText edtNumberOfYears;
    EditText edtTens;
    EditText edtUnit;
    TextView tvFilter;
    CheckBox cboNearestYear;
    CheckBox cboSumOfYears;
    TextView tvLabel;
    LinearLayout linearFreqCouple;

    CoupleByYearViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_couple_by_year);

        edtNumberOfYears = findViewById(R.id.edtNumberOfYears);
        edtTens = findViewById(R.id.edtTens);
        edtUnit = findViewById(R.id.edtUnit);
        tvFilter = findViewById(R.id.tvFilter);
        cboNearestYear = findViewById(R.id.cboNearestYear);
        cboSumOfYears = findViewById(R.id.cboSumOfYears);
        tvLabel = findViewById(R.id.tvLabel);
        linearFreqCouple = findViewById(R.id.linearFreqCouple);

        viewModel = new CoupleByYearViewModel(this, this);
        viewModel.getCoupleCountingTable(3, "", "", 0);

        tvFilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                WidgetBase.hideKeyboard(CoupleByYearActivity.this);
                String numberOfYears = edtNumberOfYears.getText().toString().trim();
                String tens = edtTens.getText().toString().trim();
                String unit = edtUnit.getText().toString().trim();
                boolean cbo1_isChecked = cboNearestYear.isChecked();
                boolean cbo2_isChecked = cboSumOfYears.isChecked();
                int status;
                if (cbo1_isChecked) {
                    status = 1;
                } else if (cbo2_isChecked) {
                    status = 2;
                } else {
                    status = 0;
                }
                if (numberOfYears.isEmpty()) {
                    showMessage("Vui lòng nhập số năm.");
                } else {
                    viewModel.getCoupleCountingTable(Integer.parseInt(numberOfYears), tens, unit, status);
                }
            }
        });

        cboNearestYear.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    cboSumOfYears.setChecked(false);
                }
            }
        });

        cboSumOfYears.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    cboNearestYear.setChecked(false);
                }
            }
        });
    }

    @Override
    public void showMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showCoupleCountingTable(String[][] matrix, int row, int col) {
        TableLayout tableLayout = TableLayoutBase.getTableLayout(this, matrix, row, col);
        linearFreqCouple.removeAllViews();
        linearFreqCouple.addView(tableLayout);
    }

    @Override
    public Context getContext() {
        return this;
    }
}
