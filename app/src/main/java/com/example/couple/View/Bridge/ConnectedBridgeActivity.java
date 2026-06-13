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
import com.example.couple.Custom.Handler.Bridge.ConnectedBridgeHandler;
import com.example.couple.Custom.Handler.LotteryHandler;
import com.example.couple.Model.Bridge.Bridge;
import com.example.couple.Model.Bridge.Connected.ConnectedSetBridge;
import com.example.couple.Model.Bridge.Connected.ConnectedSupport;
import com.example.couple.Model.Bridge.Connected.PairConnectedBridge;
import com.example.couple.Model.Bridge.Connected.PairConnectedSupport;
import com.example.couple.Model.Bridge.Connected.TriadBridge;
import com.example.couple.Model.Bridge.Touch.ConnectedBridge;
import com.example.couple.Model.Origin.Lottery;
import com.example.couple.R;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ConnectedBridgeActivity extends ActivityBase {
    private static final int HISTORY_SIZE = 20;

    TextView tvConnectedTouch;
    TextView tvConnectedTouchTitle;
    TextView tvConnectedSupport;
    TextView tvConnectedSupportTitle;
    TextView tvConnectedSetBridge;
    TextView tvConnectedSetBridgeTitle;
    TextView tvPairConnectedBridge;
    TextView tvPairConnectedBridgeTitle;
    TextView tvTriadSetBridge;
    TextView tvTriadSetBridgeTitle;
    TextView tvConnectedHistoryLabel;
    TextView tvConnectedSetHistoryLabel;
    LinearLayout linearConnectedHistory;
    LinearLayout linearConnectedSetHistory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_connected_bridge);

        tvConnectedTouch = findViewById(R.id.tvConnectedTouch);
        tvConnectedTouchTitle = findViewById(R.id.tvConnectedTouchTitle);
        tvConnectedSupport = findViewById(R.id.tvConnectedSupport);
        tvConnectedSupportTitle = findViewById(R.id.tvConnectedSupportTitle);
        tvConnectedSetBridge = findViewById(R.id.tvConnectedSetBridge);
        tvConnectedSetBridgeTitle = findViewById(R.id.tvConnectedSetBridgeTitle);
        tvPairConnectedBridge = findViewById(R.id.tvPairConnectedBridge);
        tvPairConnectedBridgeTitle = findViewById(R.id.tvPairConnectedBridgeTitle);
        tvTriadSetBridge = findViewById(R.id.tvTriadSetBridge);
        tvTriadSetBridgeTitle = findViewById(R.id.tvTriadSetBridgeTitle);
        tvConnectedHistoryLabel = findViewById(R.id.tvConnectedHistoryLabel);
        tvConnectedSetHistoryLabel = findViewById(R.id.tvConnectedSetHistoryLabel);
        linearConnectedHistory = findViewById(R.id.linearConnectedHistory);
        linearConnectedSetHistory = findViewById(R.id.linearConnectedSetHistory);

        showConnectedBridge();
    }

    private void showConnectedBridge() {
        List<Lottery> lotteries = LotteryHandler.getLotteryListFromFile(this, Const.MAX_DAYS_TO_GET_LOTTERY);
        if (lotteries.isEmpty()) {
            Toast.makeText(this, "Lỗi không lấy được thông tin XSMB!", Toast.LENGTH_SHORT).show();
            hideAll();
            return;
        }

        ConnectedBridge connectedBridge = ConnectedBridgeHandler.getConnectedBridge(lotteries,
                0, Const.CONNECTED_BRIDGE_FINDING_DAYS, Const.CONNECTED_BRIDGE_MAX_DISPLAY);
        ConnectedSetBridge connectedSetBridge = ConnectedBridgeHandler.getConnectedSetBridge(lotteries,
                0, Const.CONNECTED_BRIDGE_FINDING_DAYS, Const.CONNECTED_BRIDGE_MAX_DISPLAY);
        PairConnectedBridge pairConnectedBridge = ConnectedBridgeHandler.getConnectedVer2Bridge(lotteries,
                0, Const.CONNECTED_BRIDGE_FINDING_DAYS, Const.CONNECTED_BRIDGE_MAX_DISPLAY);
        List<TriadBridge> triadBridges = ConnectedBridgeHandler.getTriadBridge(lotteries,
                0, Const.CONNECTED_BRIDGE_FINDING_DAYS, Const.CONNECTED_BRIDGE_MAX_DISPLAY);

        showTouchs(connectedBridge);
        showSupports(connectedBridge.getConnectedSupports());
        showConnectedSetBridge(connectedSetBridge);
        showPairConnectedBridge(pairConnectedBridge);
        showTriadSetBridge(triadBridges);
        showHistory(lotteries);
    }

    private void hideAll() {
        tvConnectedTouchTitle.setVisibility(View.GONE);
        tvConnectedTouch.setVisibility(View.GONE);
        tvConnectedSupportTitle.setVisibility(View.GONE);
        tvConnectedSupport.setVisibility(View.GONE);
        tvConnectedSetBridgeTitle.setVisibility(View.GONE);
        tvConnectedSetBridge.setVisibility(View.GONE);
        tvPairConnectedBridgeTitle.setVisibility(View.GONE);
        tvPairConnectedBridge.setVisibility(View.GONE);
        tvTriadSetBridgeTitle.setVisibility(View.GONE);
        tvTriadSetBridge.setVisibility(View.GONE);
        tvConnectedHistoryLabel.setVisibility(View.GONE);
        tvConnectedSetHistoryLabel.setVisibility(View.GONE);
        linearConnectedHistory.setVisibility(View.GONE);
        linearConnectedSetHistory.setVisibility(View.GONE);
    }

    private void showTouchs(ConnectedBridge bridge) {
        if (bridge.isEmpty()) {
            tvConnectedTouchTitle.setVisibility(View.GONE);
            tvConnectedTouch.setVisibility(View.GONE);
            return;
        }

        tvConnectedTouchTitle.setVisibility(View.VISIBLE);
        tvConnectedTouch.setVisibility(View.VISIBLE);
        String show = "Chạm liên thông: " + SingleBase.showTouches(bridge.getTouches(), ", ");
        tvConnectedTouch.setText(show.trim());
    }

    private void showSupports(List<ConnectedSupport> supports) {
        if (supports.isEmpty()) {
            tvConnectedSupportTitle.setVisibility(View.GONE);
            tvConnectedSupport.setVisibility(View.GONE);
            return;
        }

        tvConnectedSupportTitle.setVisibility(View.VISIBLE);
        tvConnectedSupport.setVisibility(View.VISIBLE);
        String show = "Thông tin rút gọn:\n";
        show += supports.stream()
                .map(ConnectedSupport::show)
                .collect(Collectors.joining("\n"));
        tvConnectedSupport.setText(show.trim());
    }

    private void showConnectedSetBridge(ConnectedSetBridge bridge) {
        if (bridge.isEmpty()) {
            tvConnectedSetBridgeTitle.setVisibility(View.GONE);
            tvConnectedSetBridge.setVisibility(View.GONE);
            return;
        }

        tvConnectedSetBridgeTitle.setVisibility(View.VISIBLE);
        tvConnectedSetBridge.setVisibility(View.VISIBLE);
        String show = "Cầu liên bộ: " + bridge.showCompactNumbers();
        tvConnectedSetBridge.setText(show.trim());
    }

    private void showPairConnectedBridge(PairConnectedBridge bridge) {
        if (bridge.isEmpty()) {
            tvPairConnectedBridgeTitle.setVisibility(View.GONE);
            tvPairConnectedBridge.setVisibility(View.GONE);
            return;
        }

        tvPairConnectedBridgeTitle.setVisibility(View.VISIBLE);
        tvPairConnectedBridge.setVisibility(View.VISIBLE);
        String show = "Cầu ghép cặp:\n";
        show += bridge.getPairConnectedSupports().stream()
                .map(PairConnectedSupport::show)
                .collect(Collectors.joining("\n"));
        tvPairConnectedBridge.setText(show.trim());
    }

    private void showTriadSetBridge(List<TriadBridge> bridges) {
        if (bridges.isEmpty()) {
            tvTriadSetBridgeTitle.setVisibility(View.GONE);
            tvTriadSetBridge.setVisibility(View.GONE);
            return;
        }

        tvTriadSetBridgeTitle.setVisibility(View.VISIBLE);
        tvTriadSetBridge.setVisibility(View.VISIBLE);
        String show = "Cầu bộ ba:\n";
        show += bridges.stream()
                .map(TriadBridge::show)
                .collect(Collectors.joining("\n"));
        tvTriadSetBridge.setText(show.trim());
    }

    private void showHistory(List<Lottery> lotteries) {
        showBridgeHistory(
                getConnectedBridgeHistory(lotteries),
                tvConnectedHistoryLabel,
                linearConnectedHistory
        );
        showBridgeHistory(
                getConnectedSetBridgeHistory(lotteries),
                tvConnectedSetHistoryLabel,
                linearConnectedSetHistory
        );
    }

    private List<Bridge> getConnectedBridgeHistory(List<Lottery> lotteries) {
        List<Bridge> bridges = new ArrayList<>();
        int maxHistorySize = Math.min(HISTORY_SIZE, lotteries.size());
        for (int i = 1; i <= maxHistorySize; i++) {
            ConnectedBridge bridge = ConnectedBridgeHandler.getConnectedBridge(lotteries,
                    i, Const.CONNECTED_BRIDGE_FINDING_DAYS, Const.CONNECTED_BRIDGE_MAX_DISPLAY);
            if (!bridge.isEmpty()) {
                bridges.add(bridge);
            }
        }
        return bridges;
    }

    private List<Bridge> getConnectedSetBridgeHistory(List<Lottery> lotteries) {
        List<Bridge> bridges = new ArrayList<>();
        int maxHistorySize = Math.min(HISTORY_SIZE, lotteries.size());
        for (int i = 1; i <= maxHistorySize; i++) {
            ConnectedSetBridge bridge = ConnectedBridgeHandler.getConnectedSetBridge(lotteries,
                    i, Const.CONNECTED_BRIDGE_FINDING_DAYS, Const.CONNECTED_BRIDGE_MAX_DISPLAY);
            if (!bridge.isEmpty()) {
                bridges.add(bridge);
            }
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
