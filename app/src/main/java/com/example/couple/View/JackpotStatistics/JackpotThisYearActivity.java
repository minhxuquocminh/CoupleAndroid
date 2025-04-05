package com.example.couple.View.JackpotStatistics;

import android.content.Context;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.couple.Base.View.Table.TableData;
import com.example.couple.Base.View.Table.TableLayoutBase;
import com.example.couple.Custom.Handler.Display.TableDataConverter;
import com.example.couple.Base.View.ActivityBase;
import com.example.couple.Model.Bridge.NumberSet.NumberSetHistory;
import com.example.couple.R;
import com.example.couple.ViewModel.JackpotStatistics.JackpotThisYearViewModel;

import java.util.List;

public class JackpotThisYearActivity extends ActivityBase implements JackpotThisYearView {
    TextView tvDoubleSame;
    LinearLayout linearDoubleSame;
    TextView tvHeadAndTail;
    LinearLayout linearHeadAndTail;

    JackpotThisYearViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jackpot_this_year);

        tvDoubleSame = findViewById(R.id.tvDoubleSame);
        linearDoubleSame = findViewById(R.id.linearSDB);
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
    public void showSameDoubleInNearestTime(List<NumberSetHistory> doubleHistories, int jackpotSize) {
        int sum = 0;
        for (NumberSetHistory history : doubleHistories) {
            sum += history.getAppearanceTimes();
        }
        double ratio = sum == 0 ? 0 : Math.round(((double) jackpotSize / sum) * 100.0) / 100.0;
        String doubleShow = "Thống kê kép bằng (cứ " + ratio + " ngày lại có 1 con kép bằng, tương đương " +
                jackpotSize + " ngày có " + sum + " con kép):";
        tvDoubleSame.setText(doubleShow);
        TableData tableData = TableDataConverter.getNumberSetHistoryTable("Kép", doubleHistories);
        TableLayout tableLayout = TableLayoutBase.getTableLayout(this, tableData, true);
        linearDoubleSame.removeAllViews();
        linearDoubleSame.addView(tableLayout);
    }

    @Override
    public void showHeadAndTailInNearestTime(List<NumberSetHistory> headTailHistories) {
        String statisticShow = "Thống kê đầu đuôi:";
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
