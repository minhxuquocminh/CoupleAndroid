package com.example.couple.View.JackpotStatistics;

import android.content.Context;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.couple.Custom.Widget.CustomTableLayout;
import com.example.couple.Custom.Widget.SpeechToTextActivity;
import com.example.couple.Model.Display.NearestTime;
import com.example.couple.Model.Origin.Jackpot;
import com.example.couple.R;
import com.example.couple.ViewModel.JackpotStatistics.JackpotThisYearViewModel;

import java.util.List;

public class JackpotThisYearActivity extends SpeechToTextActivity implements JackpotThisYearView {
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
    public void showReserveJackpotListThisYear(List<Jackpot> jackpotList) {
        viewModel.getSameDoubleAndDayNumberTotal(jackpotList);
        viewModel.getSameDoubleInNearestTime(jackpotList);
        viewModel.getHeadAndTailInNearestTime(jackpotList);
    }

    @Override
    public void showSameDoubleAndDayNumberTotal(int sameDoubleTotal, int numberOfDaysTotal) {
        double times = (sameDoubleTotal == 0) ? 0 :
                (double) Math.round((float) (numberOfDaysTotal * 100) / sameDoubleTotal) / 100;
        String doubleShow = "Thống kê kép bằng (trong " + numberOfDaysTotal + " ngày có tất cả " +
                sameDoubleTotal + " con kép bằng, tương đương cứ " + times + " ngày lại có 1 con kép bằng):";
        String statisticShow = "Thống kê đầu đuôi:";
        tvDoubleSame.setText(doubleShow);
        tvHeadAndTail.setText(statisticShow);
    }

    @Override
    public void showSameDoubleInNearestTime(List<NearestTime> nearestTimeList) {
        TableLayout tableLayout = CustomTableLayout.getNearestTimeTableLayout(this, nearestTimeList);
        linearDoubleSame.removeAllViews();
        linearDoubleSame.addView(tableLayout);
    }

    @Override
    public void showHeadAndTailInNearestTime(List<NearestTime> nearestTimeList) {
        TableLayout tableLayout = CustomTableLayout.getNearestTimeTableLayout(this, nearestTimeList);
        linearHeadAndTail.removeAllViews();
        linearHeadAndTail.addView(tableLayout);
    }

    @Override
    public Context getContext() {
        return this;
    }
}
