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
import com.example.couple.Custom.Handler.Bridge.MappingBridgeHandler;
import com.example.couple.Custom.Handler.JackpotHandler;
import com.example.couple.Model.Bridge.Bridge;
import com.example.couple.Model.Bridge.Mapping.MappingBridge;
import com.example.couple.Model.Origin.Jackpot;
import com.example.couple.R;

import java.util.ArrayList;
import java.util.List;

public class MappingBridgeActivity extends ActivityBase {
    private static final int HISTORY_SIZE = 20;

    TextView tvDayMappingTitle;
    TextView tvDayMapping;
    TextView tvDayRightMappingTitle;
    TextView tvDayRightMapping;
    TextView tvWeekMappingTitle;
    TextView tvWeekMapping;
    TextView tvMonthMappingTitle;
    TextView tvMonthMapping;
    TextView tvDayMappingHistoryLabel;
    TextView tvDayRightMappingHistoryLabel;
    TextView tvWeekMappingHistoryLabel;
    TextView tvMonthMappingHistoryLabel;
    LinearLayout linearDayMappingHistory;
    LinearLayout linearDayRightMappingHistory;
    LinearLayout linearWeekMappingHistory;
    LinearLayout linearMonthMappingHistory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mapping_bridge);

        tvDayMappingTitle = findViewById(R.id.tvDayMappingTitle);
        tvDayMapping = findViewById(R.id.tvDayMapping);
        tvDayRightMappingTitle = findViewById(R.id.tvDayRightMappingTitle);
        tvDayRightMapping = findViewById(R.id.tvDayRightMapping);
        tvWeekMappingTitle = findViewById(R.id.tvWeekMappingTitle);
        tvWeekMapping = findViewById(R.id.tvWeekMapping);
        tvMonthMappingTitle = findViewById(R.id.tvMonthMappingTitle);
        tvMonthMapping = findViewById(R.id.tvMonthMapping);
        tvDayMappingHistoryLabel = findViewById(R.id.tvDayMappingHistoryLabel);
        tvDayRightMappingHistoryLabel = findViewById(R.id.tvDayRightMappingHistoryLabel);
        tvWeekMappingHistoryLabel = findViewById(R.id.tvWeekMappingHistoryLabel);
        tvMonthMappingHistoryLabel = findViewById(R.id.tvMonthMappingHistoryLabel);
        linearDayMappingHistory = findViewById(R.id.linearDayMappingHistory);
        linearDayRightMappingHistory = findViewById(R.id.linearDayRightMappingHistory);
        linearWeekMappingHistory = findViewById(R.id.linearWeekMappingHistory);
        linearMonthMappingHistory = findViewById(R.id.linearMonthMappingHistory);

        showMappingBridge();
    }

    private void showMappingBridge() {
        List<Jackpot> jackpotList = JackpotHandler.getJackpotListByDays(this, TimeInfo.DAY_OF_YEAR);
        if (jackpotList.isEmpty()) {
            Toast.makeText(this, "Lỗi không lấy được thông tin XS Đặc biệt!", Toast.LENGTH_SHORT).show();
            hideAll();
            return;
        }

        showMappingBridge(MappingBridgeHandler.getDayMappingBridge(jackpotList, 0),
                tvDayMappingTitle, tvDayMapping, "Cầu ánh xạ ngày");
        showMappingBridge(MappingBridgeHandler.getDayRightMappingBridge(jackpotList, 0),
                tvDayRightMappingTitle, tvDayRightMapping, "Cầu ánh xạ phải");
        showMappingBridge(MappingBridgeHandler.getWeekMappingBridge(jackpotList, 0),
                tvWeekMappingTitle, tvWeekMapping, "Cầu ánh xạ tuần");
        showMappingBridge(MappingBridgeHandler.getMonthMappingBridge(jackpotList, 0),
                tvMonthMappingTitle, tvMonthMapping, "Cầu ánh xạ tháng");

        showHistories(jackpotList);
    }

    private void hideAll() {
        tvDayMappingTitle.setVisibility(View.GONE);
        tvDayMapping.setVisibility(View.GONE);
        tvDayRightMappingTitle.setVisibility(View.GONE);
        tvDayRightMapping.setVisibility(View.GONE);
        tvWeekMappingTitle.setVisibility(View.GONE);
        tvWeekMapping.setVisibility(View.GONE);
        tvMonthMappingTitle.setVisibility(View.GONE);
        tvMonthMapping.setVisibility(View.GONE);
        tvDayMappingHistoryLabel.setVisibility(View.GONE);
        tvDayRightMappingHistoryLabel.setVisibility(View.GONE);
        tvWeekMappingHistoryLabel.setVisibility(View.GONE);
        tvMonthMappingHistoryLabel.setVisibility(View.GONE);
        linearDayMappingHistory.setVisibility(View.GONE);
        linearDayRightMappingHistory.setVisibility(View.GONE);
        linearWeekMappingHistory.setVisibility(View.GONE);
        linearMonthMappingHistory.setVisibility(View.GONE);
    }

    private void showMappingBridge(MappingBridge bridge, TextView title, TextView view, String name) {
        if (bridge.isEmpty()) {
            title.setVisibility(View.GONE);
            view.setVisibility(View.GONE);
            return;
        }

        title.setVisibility(View.VISIBLE);
        view.setVisibility(View.VISIBLE);
        String show = name + ":\n";
        show += bridge.showCompactInfo();
        show += "\nDàn số:\n" + bridge.showNumbers();
        view.setText(show.trim());
    }

    private void showHistories(List<Jackpot> jackpotList) {
        showBridgeHistory(getDayMappingHistory(jackpotList),
                tvDayMappingHistoryLabel, linearDayMappingHistory);
        showBridgeHistory(getDayRightMappingHistory(jackpotList),
                tvDayRightMappingHistoryLabel, linearDayRightMappingHistory);
        showBridgeHistory(getWeekMappingHistory(jackpotList),
                tvWeekMappingHistoryLabel, linearWeekMappingHistory);
        showBridgeHistory(getMonthMappingHistory(jackpotList),
                tvMonthMappingHistoryLabel, linearMonthMappingHistory);
    }

    private List<Bridge> getDayMappingHistory(List<Jackpot> jackpotList) {
        List<Bridge> bridges = new ArrayList<>();
        int maxHistorySize = Math.min(HISTORY_SIZE, jackpotList.size());
        for (int i = 1; i <= maxHistorySize; i++) {
            MappingBridge bridge = MappingBridgeHandler.getDayMappingBridge(jackpotList, i);
            if (!bridge.isEmpty()) bridges.add(bridge);
        }
        return bridges;
    }

    private List<Bridge> getDayRightMappingHistory(List<Jackpot> jackpotList) {
        List<Bridge> bridges = new ArrayList<>();
        int maxHistorySize = Math.min(HISTORY_SIZE, jackpotList.size());
        for (int i = 1; i <= maxHistorySize; i++) {
            MappingBridge bridge = MappingBridgeHandler.getDayRightMappingBridge(jackpotList, i);
            if (!bridge.isEmpty()) bridges.add(bridge);
        }
        return bridges;
    }

    private List<Bridge> getWeekMappingHistory(List<Jackpot> jackpotList) {
        List<Bridge> bridges = new ArrayList<>();
        int maxHistorySize = Math.min(HISTORY_SIZE, jackpotList.size());
        for (int i = 1; i <= maxHistorySize; i++) {
            MappingBridge bridge = MappingBridgeHandler.getWeekMappingBridge(jackpotList, i);
            if (!bridge.isEmpty()) bridges.add(bridge);
        }
        return bridges;
    }

    private List<Bridge> getMonthMappingHistory(List<Jackpot> jackpotList) {
        List<Bridge> bridges = new ArrayList<>();
        int maxHistorySize = Math.min(HISTORY_SIZE, jackpotList.size());
        for (int i = 1; i <= maxHistorySize; i++) {
            MappingBridge bridge = MappingBridgeHandler.getMonthMappingBridge(jackpotList, i);
            if (!bridge.isEmpty()) bridges.add(bridge);
        }
        return bridges;
    }

    private void showBridgeHistory(List<Bridge> bridges, TextView label, LinearLayout container) {
        if (bridges.isEmpty()) {
            label.setVisibility(View.GONE);
            container.setVisibility(View.GONE);
            return;
        }

        label.setVisibility(View.VISIBLE);
        container.setVisibility(View.VISIBLE);
        container.removeAllViews();
        TableLayout tableLayout = TableLayoutBase.getBridgeHistoryTableLayout(this, bridges, true);
        container.addView(tableLayout);
    }

    @Override
    public Context getContext() {
        return this;
    }
}
