package com.example.couple.View.PredictionBridge;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.couple.Base.Handler.InternetBase;
import com.example.couple.Model.Display.Prediction;
import com.example.couple.R;
import com.example.couple.View.Adapter.PredictionBridgeAdapter;
import com.example.couple.View.Lottery.LotteryActivity;
import com.example.couple.ViewModel.PredictionBridge.PredictionBridgeViewModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Calendar;
import java.util.List;

public class PredictionBridgeActivity extends AppCompatActivity implements PredictionBridgeView {
    TextView tvCalendar;
    TextView tvViewLottery;
    ImageView imgWeeklyAdd;
    ImageView imgMonthlyAdd;
    RecyclerView rvWeeklyBridge;
    RecyclerView rvMonthlyBridge;

    PredictionBridgeViewModel viewModel;
    String[] dayOfWeek = {"CN", "T2", "T3", "T4", "T5", "T6", "T7"};

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prediction_bridge);

        tvCalendar = findViewById(R.id.tvCalendar);
        tvViewLottery = findViewById(R.id.tvViewLottery);
        imgWeeklyAdd = findViewById(R.id.imgWeeklyAdd);
        imgMonthlyAdd = findViewById(R.id.imgMonthlyAdd);
        rvWeeklyBridge = findViewById(R.id.rvWeeklyBridge);
        rvMonthlyBridge = findViewById(R.id.rvMonthlyBridge);

        viewModel = new PredictionBridgeViewModel(this, this);

        if (InternetBase.isInternetAvailable(this)) {
            FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
            if (firebaseUser != null) {
                viewModel.getPredictionBridge();
            }
        } else {
            showMessage("Bạn đang offline.");
        }

        Calendar current = Calendar.getInstance();
        String dayOfWeekStr = dayOfWeek[current.get(Calendar.DAY_OF_WEEK) - 1];
        int day = current.get(Calendar.DAY_OF_MONTH);
        int month = current.get(Calendar.MONTH) + 1;
        int year = current.get(Calendar.YEAR);
        tvCalendar.setText(dayOfWeekStr + ", ngày " + day + " tháng " + month + " năm " + year);

        tvViewLottery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(PredictionBridgeActivity.this, LotteryActivity.class));
            }
        });

        imgWeeklyAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(PredictionBridgeActivity.this, WeeklyPredictionBridgeActivity.class));
            }
        });

        imgMonthlyAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(PredictionBridgeActivity.this, MonthlyPredictionBridgeActivity.class));
            }
        });
    }

    @Override
    public void showMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @SuppressLint("NotifyDataSetChanged")
    @Override
    public void showWeeklyPredictionBridge(List<Prediction> weeklyList) {
        PredictionBridgeAdapter adapter =
                new PredictionBridgeAdapter(this, R.layout.custom_item_rv_bridge, weeklyList);
        rvWeeklyBridge.setLayoutManager(new LinearLayoutManager(this));
        rvWeeklyBridge.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void showMonthlyPredictionBridge(List<Prediction> monthlyList) {
        PredictionBridgeAdapter adapter =
                new PredictionBridgeAdapter(this, R.layout.custom_item_rv_bridge, monthlyList);
        rvMonthlyBridge.setLayoutManager(new LinearLayoutManager(this));
        rvMonthlyBridge.setAdapter(adapter);
    }

}
