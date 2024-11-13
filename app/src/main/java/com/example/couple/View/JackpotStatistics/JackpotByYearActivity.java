package com.example.couple.View.JackpotStatistics;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.HorizontalScrollView;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.couple.Base.Handler.IOFileBase;
import com.example.couple.Base.View.WidgetBase;
import com.example.couple.Custom.Const.FileName;
import com.example.couple.Custom.Const.TimeInfo;
import com.example.couple.Custom.Handler.JackpotHandler;
import com.example.couple.Custom.Widget.CustomTableLayout;
import com.example.couple.Custom.Widget.SpeechToTextActivity;
import com.example.couple.Model.DateTime.Date.DateBase;
import com.example.couple.R;
import com.example.couple.ViewModel.JackpotStatistics.JackpotByYearViewModel;

import java.util.ArrayList;
import java.util.List;


public class JackpotByYearActivity extends SpeechToTextActivity implements JackpotByYearView {
    Spinner spnYear;
    TextView tvGetData;
    HorizontalScrollView hsTable;
    TextView tvNote;

    JackpotByYearViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jackpot_by_year);

        spnYear = findViewById(R.id.spnYear);
        tvGetData = findViewById(R.id.tvGetData);
        hsTable = findViewById(R.id.hsTable);
        tvNote = findViewById(R.id.tvNote);

        tvNote.setVisibility(View.GONE);

        viewModel = new JackpotByYearViewModel(this, this);
        viewModel.getYearList();

        tvGetData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                WidgetBase.hideKeyboard(JackpotByYearActivity.this);
                String year = spnYear.getSelectedItem().toString().split(" ")[1].trim();
                viewModel.getTableOfJackpot(Integer.parseInt(year));
            }
        });

    }

    @Override
    public void showMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showYearList(List<Integer> yearList) {
        List<String> yearStrList = new ArrayList<String>();
        for (int year : yearList) {
            yearStrList.add("Năm " + year);
        }
        ArrayAdapter<?> adapter = new ArrayAdapter<>(this,
                R.layout.custom_item_spinner, R.id.tvItemSpinner, yearStrList);
        spnYear.setAdapter(adapter);
        viewModel.getTableOfJackpot(TimeInfo.CURRENT_YEAR);
    }

    @Override
    public void showTableOfJackpot(String[][] matrix, int year) {
        DateBase lastDate = JackpotHandler.getLastDate(this);
        DateBase selectedDate = lastDate.addDays(1);
        int row = selectedDate.getDay() - 1;
        int col = selectedDate.getMonth() - 1;
        String selected = IOFileBase.readDataFromFile(this, FileName.PICKED_NUMBER);
        if (year == TimeInfo.CURRENT_YEAR) {
            matrix[row][col] = selected.isEmpty() ? "" : "888" + selected;
        }
        tvNote.setVisibility(View.VISIBLE);
        TableLayout tableLayout = CustomTableLayout.getJackpotMatrixByYear(this, matrix,
                31, 12, year);
        hsTable.removeAllViews();
        hsTable.addView(tableLayout);

    }

    @Override
    public Context getContext() {
        return this;
    }
}
