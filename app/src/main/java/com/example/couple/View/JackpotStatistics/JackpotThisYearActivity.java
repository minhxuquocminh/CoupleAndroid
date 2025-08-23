package com.example.couple.View.JackpotStatistics;

import android.content.Context;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.couple.Base.View.ActivityBase;
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
import java.util.List;
import java.util.Map;

public class JackpotThisYearActivity extends ActivityBase implements JackpotThisYearView {
    TextView tvDouble;
    LinearLayout linearDouble;
    LinearLayout linearSynthetic;
    TextView tvHeadAndTail;
    LinearLayout linearHeadAndTail;

    JackpotThisYearViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jackpot_this_year);

        linearSynthetic = findViewById(R.id.linearSynthetic);
        tvDouble = findViewById(R.id.tvDouble);
        linearDouble = findViewById(R.id.linearDouble);
        tvHeadAndTail = findViewById(R.id.tvHeadAndTail);
        linearHeadAndTail = findViewById(R.id.linearHeadAndTail);

        viewModel = new JackpotThisYearViewModel(this, this);
        viewModel.getReserveJackpotListThisYear();
    }

    @Override
    public void showMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showReserveJackpotListThisYear(List<Jackpot> jackpotList) {
        viewModel.getEventFrequency(jackpotList);
        viewModel.getSameDoubleInNearestTime(jackpotList);
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
    public void showSameDoubleInNearestTime(List<NumberSetHistory> doubleHistories, int jackpotSize) {
        int sum = 0;
        for (NumberSetHistory history : doubleHistories) {
            sum += history.getAppearanceTimes();
        }
        double ratio = sum == 0 ? 0 : Math.round(((double) jackpotSize / sum) * 100.0) / 100.0;
        String doubleShow = "Kép (cứ " + ratio + " ngày lại có 1 con kép, tương đương " +
                jackpotSize + " ngày có " + sum + " con kép):";
        tvDouble.setText(doubleShow);
        TableData tableData = TableDataConverter.getNumberSetHistoryTable("Kép", doubleHistories);
        TableLayout tableLayout = TableLayoutBase.getTableLayout(this, tableData, true);
        linearDouble.removeAllViews();
        linearDouble.addView(tableLayout);
    }

    @Override
    public void showHeadAndTailInNearestTime(List<NumberSetHistory> headTailHistories) {
        String statisticShow = "Đầu đuôi:";
        tvHeadAndTail.setText(statisticShow);
        TableData tableData = TableDataConverter.getNumberSetHistoryTable("Đầu đuôi", headTailHistories);
        TableLayout tableLayout = TableLayoutBase.getTableLayout(this, tableData, true);
        linearHeadAndTail.removeAllViews();
        linearHeadAndTail.addView(tableLayout);
    }

    @Override
    public Context getContext() {
        return this;
    }
}
