package com.example.couple.View.Bridge;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.couple.Base.View.ActivityBase;
import com.example.couple.Base.View.Table.TableLayoutBase;
import com.example.couple.Custom.Const.TimeInfo;
import com.example.couple.Custom.Handler.Bridge.EstimatedBridgeHandler;
import com.example.couple.Custom.Handler.JackpotHandler;
import com.example.couple.Model.Bridge.Bridge;
import com.example.couple.Model.Bridge.Estimated.EstimatedBridge;
import com.example.couple.Model.Bridge.Estimated.PeriodHistory;
import com.example.couple.Model.Origin.Jackpot;
import com.example.couple.R;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class EstimatedBridgeActivity extends ActivityBase {
    private static final int HISTORY_SIZE = 20;

    TextView tvEstimatedBridgeTitle;
    TextView tvEstimatedBridge;
    TextView tvEstimatedHistoryLabel;
    LinearLayout linearEstimatedHistory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_estimated_bridge);

        tvEstimatedBridgeTitle = findViewById(R.id.tvEstimatedBridgeTitle);
        tvEstimatedBridge = findViewById(R.id.tvEstimatedBridge);
        tvEstimatedHistoryLabel = findViewById(R.id.tvEstimatedHistoryLabel);
        linearEstimatedHistory = findViewById(R.id.linearEstimatedHistory);

        showEstimatedBridge();
    }

    private void showEstimatedBridge() {
        List<Jackpot> jackpotList = JackpotHandler.getJackpotListByDays(this, TimeInfo.DAY_OF_YEAR);
        if (jackpotList.isEmpty()) {
            Toast.makeText(this, "Lỗi không lấy được thông tin XS Đặc biệt!", Toast.LENGTH_SHORT).show();
            hideAll();
            return;
        }

        showEstimatedBridge(EstimatedBridgeHandler.getEstimatedBridge(jackpotList, 0));
        showHistory(jackpotList);
    }

    private void hideAll() {
        tvEstimatedBridgeTitle.setVisibility(View.GONE);
        tvEstimatedBridge.setVisibility(View.GONE);
        tvEstimatedHistoryLabel.setVisibility(View.GONE);
        linearEstimatedHistory.setVisibility(View.GONE);
    }

    private void showEstimatedBridge(EstimatedBridge bridge) {
        if (bridge.isEmpty()) {
            tvEstimatedBridgeTitle.setVisibility(View.GONE);
            tvEstimatedBridge.setVisibility(View.GONE);
            return;
        }

        tvEstimatedBridgeTitle.setVisibility(View.VISIBLE);
        tvEstimatedBridge.setVisibility(View.VISIBLE);
        String show = "Thông tin rút gọn:\n";
        show += bridge.getPeriodHistories().stream()
                .map(PeriodHistory::show)
                .collect(Collectors.joining("\n"));
        tvEstimatedBridge.setText(show.trim());
    }

    private void showHistory(List<Jackpot> jackpotList) {
        List<Bridge> bridges = new ArrayList<>();
        int maxHistorySize = Math.min(HISTORY_SIZE, jackpotList.size());
        for (int i = 1; i <= maxHistorySize; i++) {
            EstimatedBridge bridge = EstimatedBridgeHandler.getEstimatedBridge(jackpotList, i);
            if (!bridge.isEmpty()) {
                bridges.add(bridge);
            }
        }

        if (bridges.isEmpty()) {
            tvEstimatedHistoryLabel.setVisibility(View.GONE);
            linearEstimatedHistory.setVisibility(View.GONE);
            return;
        }

        tvEstimatedHistoryLabel.setVisibility(View.VISIBLE);
        linearEstimatedHistory.setVisibility(View.VISIBLE);
        linearEstimatedHistory.removeAllViews();
        TableLayout tableLayout = TableLayoutBase.getBridgeHistoryTableLayout(this, bridges, true);
        linearEstimatedHistory.addView(tableLayout);
    }

    @Override
    public Context getContext() {
        return this;
    }
}
