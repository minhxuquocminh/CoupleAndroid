package com.example.couple.View.Bridge;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.couple.Base.Handler.GenericBase;
import com.example.couple.Base.View.ActivityBase;
import com.example.couple.Model.Bridge.AfterDouble.AfterDoubleSetMapping;
import com.example.couple.Model.Bridge.Cycle.BranchInDayBridge;
import com.example.couple.Model.Bridge.Cycle.BranchInTwoDaysBridge;
import com.example.couple.Model.Bridge.Double.SignOfDouble;
import com.example.couple.Model.Bridge.NumberSet.NumberSetHistory;
import com.example.couple.Model.Origin.Jackpot;
import com.example.couple.Model.Origin.Lottery;
import com.example.couple.R;
import com.example.couple.View.BridgeHistory.JackpotNumberSetRhythmActivity;
import com.example.couple.ViewModel.Bridge.SelectiveBridgeViewModel;

import java.util.List;

public class SelectiveBridgeActivity extends ActivityBase implements SelectiveBridgeView {
    TextView tvViewLongBeatBridge;
    TextView tvAfterDoubleBridge;
    TextView tvBranchInDayBridge;
    TextView tvLongBeatBridge;

    TextView tvViewBridgeInDay;
    TextView tvViewAfterDoubleBridge;
    TextView tvViewConnectedBridge;
    TextView tvViewEstimatedBridge;
    TextView tvViewTouchBridge;
    TextView tvBranchIn2DaysBridge;
    TextView tvSignOfDouble;

    SelectiveBridgeViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selective_bridge);

        tvViewLongBeatBridge = findViewById(R.id.tvViewLongBeatBridge);
        tvAfterDoubleBridge = findViewById(R.id.tvAfterDoubleBridge);
        tvBranchInDayBridge = findViewById(R.id.tvBranchInDayBridge);
        tvLongBeatBridge = findViewById(R.id.tvLongBeatBridge);

        tvViewBridgeInDay = findViewById(R.id.tvViewBridgeInDay);
        tvViewAfterDoubleBridge = findViewById(R.id.tvViewAfterDoubleBridge);
        tvViewConnectedBridge = findViewById(R.id.tvViewConnectedBridge);
        tvViewEstimatedBridge = findViewById(R.id.tvViewEstimatedBridge);
        tvViewTouchBridge = findViewById(R.id.tvViewTouchBridge);
        tvBranchIn2DaysBridge = findViewById(R.id.tvBranchIn2DaysBridge);
        tvSignOfDouble = findViewById(R.id.tvSignOfDouble);

        viewModel = new SelectiveBridgeViewModel(this, this);

        viewModel.getAllData();

        tvViewLongBeatBridge.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(SelectiveBridgeActivity.this, JackpotNumberSetRhythmActivity.class));
            }
        });

        tvViewBridgeInDay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(SelectiveBridgeActivity.this, FindingBridgeActivity.class));
            }
        });

        tvViewAfterDoubleBridge.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(SelectiveBridgeActivity.this, AfterDoubleBridgeActivity.class));
            }
        });

        tvViewConnectedBridge.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(SelectiveBridgeActivity.this, ConnectedBridgeActivity.class));
            }
        });

        tvViewEstimatedBridge.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(SelectiveBridgeActivity.this, EstimatedBridgeActivity.class));
            }
        });

        tvViewTouchBridge.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(SelectiveBridgeActivity.this, TouchBridgeActivity.class));
            }
        });

    }

    @Override
    public void showMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showJackpotList(List<Jackpot> jackpotList) {
        viewModel.getAfterDoubleSetMappings(jackpotList);
        viewModel.getLongBeatBridge(jackpotList);
        viewModel.getBranchInTwoDaysBridge(jackpotList);
        viewModel.getSignOfDouble(jackpotList);
        viewModel.getBranchInDayBridge(jackpotList);
    }

    @Override
    public void showLotteryList(List<Lottery> lotteries) {
    }

    @Override
    public void showAfterDoubleSetMappings(List<AfterDoubleSetMapping> bridges) {
        if (bridges.isEmpty()) {
            tvAfterDoubleBridge.setVisibility(View.GONE);
        } else {
            tvAfterDoubleBridge.setVisibility(View.VISIBLE);
            String show = "Cầu sau khi ra kép:\n" + GenericBase.getDelimiterString(bridges, "\n");
            tvAfterDoubleBridge.setText(show.trim());
        }
    }

    @Override
    public void showLongBeatBridge(List<NumberSetHistory> histories) {
        if (histories.isEmpty()) {
            tvLongBeatBridge.setVisibility(View.GONE);
        } else {
            tvLongBeatBridge.setVisibility(View.VISIBLE);
            String show = "Cầu gan:\n";
            for (NumberSetHistory history : histories) {
                show += history.show() + "\n";
            }
            tvLongBeatBridge.setText(show.trim());
        }
    }

    @Override
    public void showBranchInTwoDaysBridge(BranchInTwoDaysBridge bridge) {
        if (bridge.getRunningTimes() == 0) {
            tvBranchIn2DaysBridge.setVisibility(View.GONE);
        } else {
            tvBranchIn2DaysBridge.setVisibility(View.VISIBLE);
            String show = "Cầu chi trong 2 ngày (" + bridge.getRunningTimes() + " lần): " + bridge.showNumbers();
            tvBranchIn2DaysBridge.setText(show.trim());
        }
    }

    @Override
    public void showSignOfDouble(SignOfDouble sign) {
        if (sign.isEmpty()) {
            tvSignOfDouble.setVisibility(View.GONE);
        } else {
            tvSignOfDouble.setVisibility(View.VISIBLE);
            String show = "Dấu hiệu ra kép:\n" + sign.show();
            tvSignOfDouble.setText(show.trim());
        }
    }

    @Override
    public void showBranchInDayBridge(BranchInDayBridge bridge) {
        if (bridge.isEmpty()) {
            tvBranchInDayBridge.setVisibility(View.GONE);
        } else {
            tvBranchInDayBridge.setVisibility(View.VISIBLE);
            String show = "Cầu chi theo ngày:\n" + bridge.toString();
            tvBranchInDayBridge.setText(show.trim());
        }
    }

    @Override
    public Context getContext() {
        return this;
    }

}
