package com.example.couple.View.Bridge;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.couple.Base.Handler.SingleBase;
import com.example.couple.Model.Bridge.Couple.BranchInTwoDaysBridge;
import com.example.couple.Model.Bridge.Couple.ConnectedSetBridge;
import com.example.couple.Model.Bridge.Couple.TriadBridge;
import com.example.couple.Model.Bridge.LongBeat.AfterDoubleBridge;
import com.example.couple.Model.Bridge.LongBeat.BranchInDayBridge;
import com.example.couple.Model.Bridge.Sign.SignOfDouble;
import com.example.couple.Model.Display.SpecialSetHistory;
import com.example.couple.Model.Origin.Jackpot;
import com.example.couple.Model.Origin.Lottery;
import com.example.couple.Model.Time.TimeBase;
import com.example.couple.R;
import com.example.couple.View.BridgeHistory.SpecialSetsHistoryActivity;
import com.example.couple.ViewModel.Bridge.SelectiveBridgeViewModel;

import java.util.List;

public class SelectiveBridgeActivity extends AppCompatActivity implements SelectiveBridgeView {
    TextView tvViewLongBeatBridge;
    TextView tvAfterDoubleBridge;
    TextView tvBranchInDayBridge;
    TextView tvLongBeatBridge;

    TextView tvViewBridgeInDay;
    TextView tvBranchIn2DaysBridge;
    TextView tvConnectedTouch;
    TextView tvShadowTouch;
    TextView tvSignOfDouble;
    TextView tvConnectedSetBridge;
    TextView tvTriadSetBridge;

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
        tvBranchIn2DaysBridge = findViewById(R.id.tvBranchIn2DaysBridge);
        tvConnectedTouch = findViewById(R.id.tvConnectedTouch);
        tvShadowTouch = findViewById(R.id.tvShadowTouch);
        tvSignOfDouble = findViewById(R.id.tvSignOfDouble);
        tvConnectedSetBridge = findViewById(R.id.tvConnectedSetBridge);
        tvTriadSetBridge = findViewById(R.id.tvTriadSetBridge);

        viewModel = new SelectiveBridgeViewModel(this, this);

        viewModel.GetAllData();

        tvViewLongBeatBridge.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(SelectiveBridgeActivity.this, SpecialSetsHistoryActivity.class));
            }
        });

        tvViewBridgeInDay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(SelectiveBridgeActivity.this, FindingBridgeActivity.class));
            }
        });

    }

    @Override
    public void ShowError(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void ShowNextDayTimeAndJackpotList(TimeBase nextDay,
                                              List<Jackpot> allJackpotList, List<Jackpot> jackpotList) {
        // from jackpot
        viewModel.GetAfterDoubleBridge(jackpotList);
        viewModel.GetLongBeatBridge(jackpotList);
        viewModel.GetBranchInTwoDaysBridge(jackpotList);
        viewModel.GetSignOfDouble(jackpotList);
        viewModel.GetShadowTouchs(jackpotList);
        // from time and jackpot
        if (!nextDay.isEmpty()) {
            viewModel.GetBranchInDayBridge(allJackpotList, nextDay.getDateCycle().getDay().getBranch());
        }
    }

    @Override
    public void ShowLotteryList(List<Lottery> lotteries) {
        viewModel.GetConnectedSetBridge(lotteries);
        viewModel.GetTriadSetBridge(lotteries);
        viewModel.GetConnectedTouchs(lotteries);
    }

    @Override
    public void ShowAfterDoubleBridge(List<AfterDoubleBridge> bridges) {
        if (bridges.isEmpty()) {
            tvAfterDoubleBridge.setVisibility(View.GONE);
        } else {
            tvAfterDoubleBridge.setVisibility(View.VISIBLE);
            String show = "Cầu sau khi ra kép:\n";
            for (AfterDoubleBridge bridge : bridges) {
                show += bridge.show() + "\n";
            }
            tvAfterDoubleBridge.setText(show.trim());
        }
    }

    @Override
    public void ShowLongBeatBridge(List<SpecialSetHistory> histories) {
        if (histories.isEmpty()) {
            tvLongBeatBridge.setVisibility(View.GONE);
        } else {
            tvLongBeatBridge.setVisibility(View.VISIBLE);
            String show = "Cầu gan:\n";
            for (SpecialSetHistory history : histories) {
                show += history.showCompact() + "\n";
            }
            tvLongBeatBridge.setText(show.trim());
        }
    }

    @Override
    public void ShowBranchInTwoDaysBridge(BranchInTwoDaysBridge bridge) {
        if (bridge.getRunningTimes() == 0) {
            tvBranchIn2DaysBridge.setVisibility(View.GONE);
        } else {
            tvBranchIn2DaysBridge.setVisibility(View.VISIBLE);
            String show = "Cầu chi trong 2 ngày (" + bridge.getRunningTimes() + " lần): " + bridge.showNumbers();
            tvBranchIn2DaysBridge.setText(show.trim());
        }
    }

    @Override
    public void ShowConnectedTouchs(List<Integer> touchs) {
        if (touchs.isEmpty()) {
            tvConnectedTouch.setVisibility(View.GONE);
        } else {
            tvConnectedTouch.setVisibility(View.VISIBLE);
            String show = "Chạm liên thông: " + SingleBase.showTouchs(touchs, ", ");
            tvConnectedTouch.setText(show.trim());
        }
    }

    @Override
    public void ShowShadowTouchs(List<Integer> touchs) {
        if (touchs.isEmpty()) {
            tvShadowTouch.setVisibility(View.GONE);
        } else {
            tvShadowTouch.setVisibility(View.VISIBLE);
            String show = "Chạm bóng: " + SingleBase.showTouchs(touchs, ", ");
            tvShadowTouch.setText(show.trim());
        }
    }

    @Override
    public void ShowSignOfDouble(SignOfDouble sign) {
        if (sign.isEmpty()) {
            tvSignOfDouble.setVisibility(View.GONE);
        } else {
            tvSignOfDouble.setVisibility(View.VISIBLE);
            String show = "Dấu hiệu ra kép:\n" + sign.show();
            tvSignOfDouble.setText(show.trim());
        }
    }

    @Override
    public void ShowBranchInDayBridge(BranchInDayBridge bridge) {
        if (bridge.isEmpty()) {
            tvBranchInDayBridge.setVisibility(View.GONE);
        } else {
            tvBranchInDayBridge.setVisibility(View.VISIBLE);
            String show = "Cầu chi theo ngày:\n" + bridge.show();
            tvBranchInDayBridge.setText(show.trim());
        }
    }

    @Override
    public void ShowConnectedSetBridge(ConnectedSetBridge bridge) {
        if (bridge.isEmpty()) {
            tvConnectedSetBridge.setVisibility(View.GONE);
        } else {
            tvConnectedSetBridge.setVisibility(View.VISIBLE);
            String show = "Cầu liên bộ bao gồm các bộ: " + bridge.showCompactNumbers();
            tvConnectedSetBridge.setText(show.trim());
        }
    }

    @Override
    public void ShowTriadSetBridge(List<TriadBridge> bridges) {
        if (bridges.isEmpty()) {
            tvTriadSetBridge.setVisibility(View.GONE);
        } else {
            tvTriadSetBridge.setVisibility(View.VISIBLE);
            String show = "Cầu bộ ba:\n";
            for (TriadBridge bridge : bridges) {
                show += bridge.show() + "\n";
                if (bridge.getTriadStatusList().size() == 5) {
                    break;
                }
            }
            tvTriadSetBridge.setText(show.trim());
        }
    }

}
