package com.example.couple.View.Bridge;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.couple.Base.Handler.SingleBase;
import com.example.couple.Base.View.ActivityBase;
import com.example.couple.Base.View.Table.TableLayoutBase;
import com.example.couple.Custom.Const.Const;
import com.example.couple.Custom.Const.TimeInfo;
import com.example.couple.Custom.Handler.Bridge.TouchBridgeHandler;
import com.example.couple.Custom.Handler.JackpotHandler;
import com.example.couple.Custom.Handler.LotteryHandler;
import com.example.couple.Model.Bridge.Bridge;
import com.example.couple.Model.Bridge.Touch.CombineTouchBridge;
import com.example.couple.Model.Bridge.Touch.LottoTouchBridge;
import com.example.couple.Model.Bridge.Touch.ShadowTouchBridge;
import com.example.couple.Model.Bridge.Touch.TouchBridge;
import com.example.couple.Model.Origin.Jackpot;
import com.example.couple.Model.Origin.Lottery;
import com.example.couple.R;

import java.util.ArrayList;
import java.util.List;

public class TouchBridgeActivity extends ActivityBase {
    private static final int HISTORY_SIZE = 20;

    TextView tvCombineTouchTitle;
    TextView tvCombineTouch;
    TextView tvShadowTouchTitle;
    TextView tvShadowTouch;
    TextView tvLastDayShadowTitle;
    TextView tvLastDayShadow;
    TextView tvLastWeekShadowTitle;
    TextView tvLastWeekShadow;
    TextView tvLottoTouchTitle;
    TextView tvLottoTouch;
    TextView tvCombineTouchHistoryLabel;
    TextView tvShadowTouchHistoryLabel;
    TextView tvLastDayShadowHistoryLabel;
    TextView tvLastWeekShadowHistoryLabel;
    TextView tvLottoTouchHistoryLabel;
    LinearLayout linearCombineTouchHistory;
    LinearLayout linearShadowTouchHistory;
    LinearLayout linearLastDayShadowHistory;
    LinearLayout linearLastWeekShadowHistory;
    LinearLayout linearLottoTouchHistory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_touch_bridge);

        tvCombineTouchTitle = findViewById(R.id.tvCombineTouchTitle);
        tvCombineTouch = findViewById(R.id.tvCombineTouch);
        tvShadowTouchTitle = findViewById(R.id.tvShadowTouchTitle);
        tvShadowTouch = findViewById(R.id.tvShadowTouch);
        tvLastDayShadowTitle = findViewById(R.id.tvLastDayShadowTitle);
        tvLastDayShadow = findViewById(R.id.tvLastDayShadow);
        tvLastWeekShadowTitle = findViewById(R.id.tvLastWeekShadowTitle);
        tvLastWeekShadow = findViewById(R.id.tvLastWeekShadow);
        tvLottoTouchTitle = findViewById(R.id.tvLottoTouchTitle);
        tvLottoTouch = findViewById(R.id.tvLottoTouch);
        tvCombineTouchHistoryLabel = findViewById(R.id.tvCombineTouchHistoryLabel);
        tvShadowTouchHistoryLabel = findViewById(R.id.tvShadowTouchHistoryLabel);
        tvLastDayShadowHistoryLabel = findViewById(R.id.tvLastDayShadowHistoryLabel);
        tvLastWeekShadowHistoryLabel = findViewById(R.id.tvLastWeekShadowHistoryLabel);
        tvLottoTouchHistoryLabel = findViewById(R.id.tvLottoTouchHistoryLabel);
        linearCombineTouchHistory = findViewById(R.id.linearCombineTouchHistory);
        linearShadowTouchHistory = findViewById(R.id.linearShadowTouchHistory);
        linearLastDayShadowHistory = findViewById(R.id.linearLastDayShadowHistory);
        linearLastWeekShadowHistory = findViewById(R.id.linearLastWeekShadowHistory);
        linearLottoTouchHistory = findViewById(R.id.linearLottoTouchHistory);

        showTouchBridge();
    }

    private void showTouchBridge() {
        List<Jackpot> jackpotList = JackpotHandler.getJackpotListByDays(this, TimeInfo.DAY_OF_YEAR);
        List<Lottery> lotteries = LotteryHandler.getLotteryListFromFile(this, Const.MAX_DAYS_TO_GET_LOTTERY);
        if (jackpotList.isEmpty() || lotteries.isEmpty()) {
            Toast.makeText(this, "Lỗi không lấy được thông tin cầu!", Toast.LENGTH_SHORT).show();
            hideAll();
            return;
        }

        showTouchBridge(TouchBridgeHandler.getCombineTouchBridge(jackpotList, lotteries, 0),
                tvCombineTouchTitle, tvCombineTouch, "Chạm kết hợp");
        showTouchBridge(TouchBridgeHandler.getShadowTouchBridge(jackpotList, 0),
                tvShadowTouchTitle, tvShadowTouch, "Chạm bóng");
        showTouchBridge(TouchBridgeHandler.getLastDayShadowTouchBridge(jackpotList, 0),
                tvLastDayShadowTitle, tvLastDayShadow, "Chạm bóng ngày");
        showTouchBridge(TouchBridgeHandler.getLastWeekShadowTouchBridge(jackpotList, 0),
                tvLastWeekShadowTitle, tvLastWeekShadow, "Chạm bóng tuần");
        showTouchBridge(TouchBridgeHandler.getLottoTouchBridge(lotteries, 0),
                tvLottoTouchTitle, tvLottoTouch, "Chạm lô tô");

        showHistories(jackpotList, lotteries);
    }

    private void hideAll() {
        tvCombineTouchTitle.setVisibility(View.GONE);
        tvCombineTouch.setVisibility(View.GONE);
        tvShadowTouchTitle.setVisibility(View.GONE);
        tvShadowTouch.setVisibility(View.GONE);
        tvLastDayShadowTitle.setVisibility(View.GONE);
        tvLastDayShadow.setVisibility(View.GONE);
        tvLastWeekShadowTitle.setVisibility(View.GONE);
        tvLastWeekShadow.setVisibility(View.GONE);
        tvLottoTouchTitle.setVisibility(View.GONE);
        tvLottoTouch.setVisibility(View.GONE);
        tvCombineTouchHistoryLabel.setVisibility(View.GONE);
        tvShadowTouchHistoryLabel.setVisibility(View.GONE);
        tvLastDayShadowHistoryLabel.setVisibility(View.GONE);
        tvLastWeekShadowHistoryLabel.setVisibility(View.GONE);
        tvLottoTouchHistoryLabel.setVisibility(View.GONE);
        linearCombineTouchHistory.setVisibility(View.GONE);
        linearShadowTouchHistory.setVisibility(View.GONE);
        linearLastDayShadowHistory.setVisibility(View.GONE);
        linearLastWeekShadowHistory.setVisibility(View.GONE);
        linearLottoTouchHistory.setVisibility(View.GONE);
    }

    private void showTouchBridge(TouchBridge bridge, TextView title, TextView view, String name) {
        if (bridge.getTouches().isEmpty() || bridge.getNumbers().isEmpty()) {
            title.setVisibility(View.GONE);
            view.setVisibility(View.GONE);
            return;
        }

        title.setVisibility(View.VISIBLE);
        view.setVisibility(View.VISIBLE);
        view.setText((name + ": " + SingleBase.showTouches(bridge.getTouches(), ", ")).trim());
    }

    private void showHistories(List<Jackpot> jackpotList, List<Lottery> lotteries) {
        showBridgeHistory(getCombineTouchHistory(jackpotList, lotteries),
                tvCombineTouchHistoryLabel, linearCombineTouchHistory);
        showBridgeHistory(getShadowTouchHistory(jackpotList),
                tvShadowTouchHistoryLabel, linearShadowTouchHistory);
        showBridgeHistory(getLastDayShadowHistory(jackpotList),
                tvLastDayShadowHistoryLabel, linearLastDayShadowHistory);
        showBridgeHistory(getLastWeekShadowHistory(jackpotList),
                tvLastWeekShadowHistoryLabel, linearLastWeekShadowHistory);
        showBridgeHistory(getLottoTouchHistory(lotteries),
                tvLottoTouchHistoryLabel, linearLottoTouchHistory);
    }

    private List<Bridge> getCombineTouchHistory(List<Jackpot> jackpotList, List<Lottery> lotteries) {
        List<Bridge> bridges = new ArrayList<>();
        int maxHistorySize = Math.min(HISTORY_SIZE, Math.min(jackpotList.size(), lotteries.size()));
        for (int i = 1; i <= maxHistorySize; i++) {
            CombineTouchBridge bridge = TouchBridgeHandler.getCombineTouchBridge(jackpotList, lotteries, i);
            if (!bridge.isEmpty() && !bridge.getNumbers().isEmpty()) bridges.add(bridge);
        }
        return bridges;
    }

    private List<Bridge> getShadowTouchHistory(List<Jackpot> jackpotList) {
        List<Bridge> bridges = new ArrayList<>();
        int maxHistorySize = Math.min(HISTORY_SIZE, jackpotList.size());
        for (int i = 1; i <= maxHistorySize; i++) {
            ShadowTouchBridge bridge = TouchBridgeHandler.getShadowTouchBridge(jackpotList, i);
            if (!bridge.isEmpty() && !bridge.getNumbers().isEmpty()) bridges.add(bridge);
        }
        return bridges;
    }

    private List<Bridge> getLastDayShadowHistory(List<Jackpot> jackpotList) {
        List<Bridge> bridges = new ArrayList<>();
        int maxHistorySize = Math.min(HISTORY_SIZE, jackpotList.size());
        for (int i = 1; i <= maxHistorySize; i++) {
            ShadowTouchBridge bridge = TouchBridgeHandler.getLastDayShadowTouchBridge(jackpotList, i);
            if (!bridge.isEmpty() && !bridge.getNumbers().isEmpty()) bridges.add(bridge);
        }
        return bridges;
    }

    private List<Bridge> getLastWeekShadowHistory(List<Jackpot> jackpotList) {
        List<Bridge> bridges = new ArrayList<>();
        int maxHistorySize = Math.min(HISTORY_SIZE, jackpotList.size());
        for (int i = 1; i <= maxHistorySize; i++) {
            ShadowTouchBridge bridge = TouchBridgeHandler.getLastWeekShadowTouchBridge(jackpotList, i);
            if (!bridge.isEmpty() && !bridge.getNumbers().isEmpty()) bridges.add(bridge);
        }
        return bridges;
    }

    private List<Bridge> getLottoTouchHistory(List<Lottery> lotteries) {
        List<Bridge> bridges = new ArrayList<>();
        int maxHistorySize = Math.min(HISTORY_SIZE, lotteries.size());
        for (int i = 1; i <= maxHistorySize; i++) {
            LottoTouchBridge bridge = TouchBridgeHandler.getLottoTouchBridge(lotteries, i);
            if (!bridge.isEmpty() && !bridge.getNumbers().isEmpty()) bridges.add(bridge);
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
