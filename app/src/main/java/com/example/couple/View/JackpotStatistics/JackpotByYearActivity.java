package com.example.couple.View.JackpotStatistics;

import android.content.Context;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.HorizontalScrollView;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.couple.Base.Handler.CoupleBase;
import com.example.couple.Base.Handler.StorageBase;
import com.example.couple.Base.View.Spacing;
import com.example.couple.Base.View.Table.TableLayoutBase;
import com.example.couple.Base.View.TextViewBase;
import com.example.couple.Base.View.TextViewPositionManager;
import com.example.couple.Base.View.WidgetBase;
import com.example.couple.Custom.Const.Const;
import com.example.couple.Custom.Const.TimeInfo;
import com.example.couple.Custom.Enum.StorageType;
import com.example.couple.Custom.Handler.JackpotHandler;
import com.example.couple.Base.View.ActivityBase;
import com.example.couple.Model.DateTime.Date.DateBase;
import com.example.couple.R;
import com.example.couple.ViewModel.JackpotStatistics.JackpotByYearViewModel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.IntStream;
import java.util.stream.Stream;


public class JackpotByYearActivity extends ActivityBase implements JackpotByYearView {
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
        DateBase nextDate = JackpotHandler.getLastDate(this).addDays(1);
        int selected = StorageBase.getNumber(this, StorageType.NUMBER_OF_PICKER);
        if (year == TimeInfo.CURRENT_YEAR) {
            matrix[nextDate.getDay() - 1][nextDate.getMonth() - 1] = selected == Const.EMPTY_VALUE ?
                    "" : "888" + CoupleBase.showCouple(selected);
        }

        int row = TimeInfo.DAY_OF_MONTH, col = TimeInfo.MONTH_OF_YEAR;
        String[] headers = IntStream.rangeClosed(1, col).mapToObj(String::valueOf).toArray(String[]::new);
        String[] rowHeaders = Stream.concat(Stream.of("D/M"),
                IntStream.rangeClosed(1, row).mapToObj(String::valueOf)).toArray(String[]::new);
        TextViewPositionManager manager = new TextViewPositionManager();
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                DateBase dateBase = new DateBase(i + 1, j + 1, year);
                if (dateBase.isValid() && dateBase.isItOnSunday()) {
                    manager.addTextViewBase(i, j, TextViewBase.builder().context(this).textSize(15).gravity(Gravity.CENTER)
                            .padding(Spacing.by(10, 5, 10, 5)).background(R.color.colorBgSunday)
                            .textColor(R.color.colorText).build());
                }
            }
        }

        TableLayout tableLayout = TableLayoutBase.getTableLayoutWithNewStyleCell(this, headers,
                rowHeaders, matrix, row, col, new HashMap<>(),
                new HashMap<>(), manager, false);
        hsTable.removeAllViews();
        hsTable.addView(tableLayout);
        tvNote.setVisibility(View.VISIBLE);
    }

    @Override
    public Context getContext() {
        return this;
    }
}
