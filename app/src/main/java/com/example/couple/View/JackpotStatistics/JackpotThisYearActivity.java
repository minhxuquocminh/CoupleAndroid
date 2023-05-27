package com.example.couple.View.JackpotStatistics;

import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.couple.Custom.Widget.CustomTableLayout;
import com.example.couple.Model.Display.NearestTime;
import com.example.couple.Model.Origin.Jackpot;
import com.example.couple.R;
import com.example.couple.ViewModel.JackpotStatistics.JackpotThisYearViewModel;

import java.util.List;

public class JackpotThisYearActivity extends AppCompatActivity implements JackpotThisYearView {
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
        viewModel.GetReserveJackpotListThisYear();

    }

    @Override
    public void ShowError(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void ShowReserveJackpotListThisYear(List<Jackpot> jackpotList) {
        viewModel.GetSameDoubleAndDayNumberTotal(jackpotList);
        viewModel.GetSameDoubleInNearestTime(jackpotList);
        viewModel.GetHeadAndTailInNearestTime(jackpotList);
    }

    @Override
    public void ShowSameDoubleAndDayNumberTotal(int sameDoubleTotal, int numberOfDaysTotal) {
        double times = (sameDoubleTotal == 0) ? 0 :
                (double) Math.round(numberOfDaysTotal * 100 / sameDoubleTotal) / 100;
        tvDoubleSame.setText("Thống kê kép bằng (trong " + numberOfDaysTotal + " ngày có tất cả " +
                sameDoubleTotal + " con kép bằng, tương đương cứ " + times + " ngày lại có 1 con kép bằng):");
        tvHeadAndTail.setText("Thống kê đầu đuôi:");
    }

    @Override
    public void ShowSameDoubleInNearestTime(List<NearestTime> nearestTimeList) {
        TableLayout tableLayout = CustomTableLayout.getNearestTimeTableLayout(this, nearestTimeList);
        linearDoubleSame.removeAllViews();
        linearDoubleSame.addView(tableLayout);
    }

    @Override
    public void ShowHeadAndTailInNearestTime(List<NearestTime> nearestTimeList) {
        TableLayout tableLayout = CustomTableLayout.getNearestTimeTableLayout(this, nearestTimeList);
        linearHeadAndTail.removeAllViews();
        linearHeadAndTail.addView(tableLayout);
    }
}
