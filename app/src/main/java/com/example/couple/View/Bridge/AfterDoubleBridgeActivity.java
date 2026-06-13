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
import com.example.couple.Custom.Handler.Bridge.AfterDoubleBridgeHandler;
import com.example.couple.Custom.Handler.JackpotHandler;
import com.example.couple.Model.Bridge.Bridge;
import com.example.couple.Model.Bridge.AfterDouble.AfterDoubleCoupleBridge;
import com.example.couple.Model.Bridge.AfterDouble.AfterDoubleCoupleMapping;
import com.example.couple.Model.Bridge.AfterDouble.AfterDoubleCoupleSupport;
import com.example.couple.Model.Origin.Jackpot;
import com.example.couple.R;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class AfterDoubleBridgeActivity extends ActivityBase {
    private static final int HISTORY_SIZE = 20;

    TextView tvAfterDoubleSupport;
    TextView tvAfterDoubleCoupleMapping;
    TextView tvAfterDoubleHistoryLabel;
    LinearLayout linearAfterDoubleHistory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_after_double_bridge);

        tvAfterDoubleSupport = findViewById(R.id.tvAfterDoubleSupport);
        tvAfterDoubleCoupleMapping = findViewById(R.id.tvAfterDoubleCoupleMapping);
        tvAfterDoubleHistoryLabel = findViewById(R.id.tvAfterDoubleHistoryLabel);
        linearAfterDoubleHistory = findViewById(R.id.linearAfterDoubleHistory);

        showAfterDoubleBridge();
    }

    private void showAfterDoubleBridge() {
        List<Jackpot> jackpotList = JackpotHandler.getJackpotListByDays(this, TimeInfo.DAY_OF_YEAR);
        if (jackpotList.isEmpty()) {
            Toast.makeText(this, "Lỗi không lấy được thông tin XS Đặc biệt!", Toast.LENGTH_SHORT).show();
            tvAfterDoubleSupport.setVisibility(View.GONE);
            tvAfterDoubleCoupleMapping.setVisibility(View.GONE);
            tvAfterDoubleHistoryLabel.setVisibility(View.GONE);
            linearAfterDoubleHistory.setVisibility(View.GONE);
            return;
        }

        List<AfterDoubleCoupleMapping> mappings = AfterDoubleBridgeHandler.getAfterDoubleCoupleMappings(jackpotList);
        AfterDoubleCoupleBridge supportBridge = AfterDoubleBridgeHandler.getAfterDoubleCoupleBridge(jackpotList, 0);

        showSupports(supportBridge);
        showMappings(mappings);
        showHistory(jackpotList);
    }

    private void showSupports(AfterDoubleCoupleBridge bridge) {
        if (bridge.isEmpty()) {
            tvAfterDoubleSupport.setVisibility(View.GONE);
            return;
        }

        tvAfterDoubleSupport.setVisibility(View.VISIBLE);
        String show = "Thông tin rút gọn:\n";
        show += bridge.getSupports().stream()
                .map(AfterDoubleCoupleSupport::show)
                .collect(Collectors.joining("\n"));
        tvAfterDoubleSupport.setText(show.trim());
    }

    private void showMappings(List<AfterDoubleCoupleMapping> mappings) {
        if (mappings.isEmpty()) {
            tvAfterDoubleCoupleMapping.setVisibility(View.GONE);
            return;
        }

        tvAfterDoubleCoupleMapping.setVisibility(View.VISIBLE);
        String show = "Kết quả sau khi tính toán:\n";
        show += mappings.stream()
                .map(AfterDoubleCoupleMapping::show)
                .collect(Collectors.joining("\n"));
        tvAfterDoubleCoupleMapping.setText(show.trim());
    }

    private void showHistory(List<Jackpot> jackpotList) {
        List<Bridge> bridges = new ArrayList<>();
        int maxHistorySize = Math.min(HISTORY_SIZE, jackpotList.size());
        for (int i = 1; i <= maxHistorySize; i++) {
            AfterDoubleCoupleBridge bridge = AfterDoubleBridgeHandler.getAfterDoubleCoupleBridge(jackpotList, i);
            if (!bridge.isEmpty()) {
                bridges.add(bridge);
            }
        }

        if (bridges.isEmpty()) {
            tvAfterDoubleHistoryLabel.setVisibility(View.GONE);
            linearAfterDoubleHistory.setVisibility(View.GONE);
            return;
        }

        tvAfterDoubleHistoryLabel.setVisibility(View.VISIBLE);
        linearAfterDoubleHistory.setVisibility(View.VISIBLE);
        linearAfterDoubleHistory.removeAllViews();
        TableLayout tableLayout = TableLayoutBase.getBridgeHistoryTableLayout(this, bridges, true);
        linearAfterDoubleHistory.addView(tableLayout);
    }

    @Override
    public Context getContext() {
        return this;
    }
}
