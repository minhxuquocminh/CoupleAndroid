package com.example.couple.View.JackpotStatistics;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.couple.Base.View.ActivityBase;
import com.example.couple.Base.View.SpinnerBase;
import com.example.couple.Base.View.Table.TableData;
import com.example.couple.Base.View.Table.TableLayoutBase;
import com.example.couple.Custom.Handler.Display.TableDataConverter;
import com.example.couple.Model.Bridge.NumberSet.NumberSetHistory;
import com.example.couple.Model.Origin.Jackpot;
import com.example.couple.Model.Statistics.EventFrequency;
import com.example.couple.Model.Statistics.EventFrequencyType;
import com.example.couple.R;
import com.example.couple.ViewModel.JackpotStatistics.JackpotThisYearViewModel;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class CurrentYearJackpotStatisticsActivity extends ActivityBase implements CurrentYearJackpotStatisticsView {
    private static final int MODE_THIS_YEAR = 0;
    private static final int MODE_365_DAYS = 1;

    Spinner spnStatisticMode;
    LinearLayout linearSynthetic;
    TextView tvHeadAndTail;
    LinearLayout linearHeadAndTail;

    JackpotThisYearViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jackpot_this_year);

        spnStatisticMode = findViewById(R.id.spnStatisticMode);
        linearSynthetic = findViewById(R.id.linearSynthetic);
        tvHeadAndTail = findViewById(R.id.tvHeadAndTail);
        linearHeadAndTail = findViewById(R.id.linearHeadAndTail);

        viewModel = new JackpotThisYearViewModel(this, this);
        setupStatisticModeSpinner();
    }

    private void setupStatisticModeSpinner() {
        List<String> modes = Arrays.asList("Th\u1ed1ng k\u00ea n\u0103m nay", "Th\u1ed1ng k\u00ea 365 ng\u00e0y");
        SpinnerBase.bindFilterSpinner(this, spnStatisticMode, modes);
        spnStatisticMode.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                loadStatistics(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void loadStatistics(int mode) {
        clearStatistics();
        if (mode == MODE_365_DAYS) {
            viewModel.getReserveJackpotListLastDays(365);
            return;
        }
        viewModel.getReserveJackpotListThisYear();
    }

    private void clearStatistics() {
        linearSynthetic.removeAllViews();
        linearHeadAndTail.removeAllViews();
    }

    @Override
    public void showMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showReserveJackpotList(List<Jackpot> jackpotList) {
        viewModel.getEventFrequency(jackpotList);
        viewModel.getHeadAndTailInNearestTime(jackpotList);
    }

    @Override
    public void showEventFrequency(Map<EventFrequencyType, EventFrequency> eventFrequencyMap) {
        TableData tableData = TableDataConverter.getEventFrequencyTable(new ArrayList<>(eventFrequencyMap.values()));
        TableLayout tableLayout = TableLayoutBase.getTableLayout(this, tableData, true);
        linearSynthetic.removeAllViews();
        linearSynthetic.addView(tableLayout);
    }

    @Override
    public void showHeadAndTailInNearestTime(List<NumberSetHistory> headTailHistories) {
        tvHeadAndTail.setText("\u0110\u1ea7u \u0111u\u00f4i:");
        TableData tableData = TableDataConverter.getNumberSetHistoryTable("\u0110\u1ea7u \u0111u\u00f4i", headTailHistories);
        TableLayout tableLayout = TableLayoutBase.getTableLayout(this, tableData, true);
        linearHeadAndTail.removeAllViews();
        linearHeadAndTail.addView(tableLayout);
    }

    @Override
    public Context getContext() {
        return this;
    }
}
