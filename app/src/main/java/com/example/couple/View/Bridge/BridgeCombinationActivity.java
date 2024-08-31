package com.example.couple.View.Bridge;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.couple.Base.View.DialogBase;
import com.example.couple.Base.View.WidgetBase;
import com.example.couple.Custom.Const.Const;
import com.example.couple.Model.Bridge.Bridge;
import com.example.couple.Model.Bridge.BridgeType;
import com.example.couple.Model.Bridge.CombineBridge;
import com.example.couple.Model.Handler.InputType;
import com.example.couple.Model.Handler.Input;
import com.example.couple.Model.Origin.Jackpot;
import com.example.couple.Model.Origin.Lottery;
import com.example.couple.R;
import com.example.couple.ViewModel.Bridge.BridgeCombinationViewModel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class BridgeCombinationActivity extends AppCompatActivity implements BridgeCombinationView {
    ImageView imgBridgeAnnotation;
    EditText edtDayNumber;
    EditText edtSet;
    EditText edtTouch;
    EditText edtSum;
    EditText edtBranch;
    EditText edtHead;
    EditText edtTail;
    EditText edtCombine;
    //
    CheckBox cboLottoTouchBridge;
    CheckBox cboCombineTouchBridge;
    CheckBox cboShadowTouchBridge;
    CheckBox cboConnectedBridge;
    CheckBox cboLastDayShadowBridge;
    CheckBox cboLastWeekShadowBridge;
    //
    CheckBox cboMappingBridge;
    CheckBox cboConnectedSetBridge;
    CheckBox cboEstimatedBridge;
    CheckBox cboUnappearedBigDoubleBridge;
    CheckBox cboCompatible;
    CheckBox cboIncompatible;
    CheckBox cboRightMappingBridge;
    CheckBox cboTriadMappingBridge;
    CheckBox cboBranchInTwoDaysBridge;
    //
    CheckBox cboBigDoubleSet;
    CheckBox cboSameDoubleSet;
    CheckBox cboPositiveDoubleSet;
    //
    Button btnFindingBridge;

    BridgeCombinationViewModel viewModel;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bridge_combination);

        imgBridgeAnnotation = findViewById(R.id.imgBridgeAnnotation);
        edtDayNumber = findViewById(R.id.edtDayNumber);
        edtSet = findViewById(R.id.edtSet);
        edtTouch = findViewById(R.id.edtTouch);
        edtSum = findViewById(R.id.edtSum);
        edtBranch = findViewById(R.id.edtBranch);
        edtHead = findViewById(R.id.edtHead);
        edtTail = findViewById(R.id.edtTail);
        edtCombine = findViewById(R.id.edtCombine);
        // touch
        cboLottoTouchBridge = findViewById(R.id.cboLottoTouchBridge);
        cboCombineTouchBridge = findViewById(R.id.cboCombineTouchBridge);
        cboShadowTouchBridge = findViewById(R.id.cboShadowTouchBridge);
        cboConnectedBridge = findViewById(R.id.cboConnectedBridge);
        cboLastDayShadowBridge = findViewById(R.id.cboLastDayShadowBridge);
        cboLastWeekShadowBridge = findViewById(R.id.cboLastWeekShadowBridge);
        // mapping, period
        cboMappingBridge = findViewById(R.id.cboMappingBridge);
        cboRightMappingBridge = findViewById(R.id.cboRightMappingBridge);
        cboCompatible = findViewById(R.id.cboCompatible);
        cboIncompatible = findViewById(R.id.cboIncompatible);
        cboConnectedSetBridge = findViewById(R.id.cboConnectedSetBridge);
        cboEstimatedBridge = findViewById(R.id.cboEstimatedBridge);
        cboUnappearedBigDoubleBridge = findViewById(R.id.cboUnappearedBigDoubleBridge);
        cboTriadMappingBridge = findViewById(R.id.cboTriadMappingBridge);
        cboBranchInTwoDaysBridge = findViewById(R.id.cboBranchInTwoDaysBridge);
        // special set
        cboBigDoubleSet = findViewById(R.id.cboBigDoubleSet);
        cboSameDoubleSet = findViewById(R.id.cboSameDoubleSet);
        cboPositiveDoubleSet = findViewById(R.id.cboPositiveDoubleSet);
        // button
        btnFindingBridge = findViewById(R.id.btnFindingBridge);

        edtDayNumber.setText("18");
        edtDayNumber.setSelection(edtDayNumber.getText().length());

        viewModel = new BridgeCombinationViewModel(this, this);
        viewModel.getAllData();


    }

    @Override
    public void showMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showAllData(List<Jackpot> jackpotList, List<Lottery> lotteryList) {
        viewModel.getAllBridgeToday(jackpotList, lotteryList);
        btnFindingBridge.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                WidgetBase.hideKeyboard(BridgeCombinationActivity.this);
                String numberOfDayStr = edtDayNumber.getText().toString().trim();
                Map<BridgeType, Boolean> bridgeTypeFlag = new HashMap<>();
                // touch
                bridgeTypeFlag.put(BridgeType.COMBINE_TOUCH, cboCombineTouchBridge.isChecked());
                bridgeTypeFlag.put(BridgeType.CONNECTED, cboConnectedBridge.isChecked());
                bridgeTypeFlag.put(BridgeType.SHADOW_TOUCH, cboShadowTouchBridge.isChecked());
                bridgeTypeFlag.put(BridgeType.LOTTO_TOUCH, cboLottoTouchBridge.isChecked());
                bridgeTypeFlag.put(BridgeType.LAST_DAY_SHADOW, cboLastDayShadowBridge.isChecked());
                bridgeTypeFlag.put(BridgeType.LAST_WEEK_SHADOW, cboLastWeekShadowBridge.isChecked());
                // mapping, estimated
                bridgeTypeFlag.put(BridgeType.MAPPING, cboMappingBridge.isChecked());
                bridgeTypeFlag.put(BridgeType.CONNECTED_SET, cboConnectedSetBridge.isChecked());
                bridgeTypeFlag.put(BridgeType.ESTIMATED, cboEstimatedBridge.isChecked());
                bridgeTypeFlag.put(BridgeType.RIGHT_MAPPING, cboRightMappingBridge.isChecked());
                bridgeTypeFlag.put(BridgeType.COMPATIBLE_CYCLE, cboCompatible.isChecked());
                bridgeTypeFlag.put(BridgeType.INCOMPATIBLE_CYCLE, cboIncompatible.isChecked());
                bridgeTypeFlag.put(BridgeType.UNAPPEARED_BIG_DOUBLE, cboUnappearedBigDoubleBridge.isChecked());
                bridgeTypeFlag.put(BridgeType.TRIAD_MAPPING, cboTriadMappingBridge.isChecked());
                bridgeTypeFlag.put(BridgeType.BRANCH_IN_TWO_DAYS_BRIDGE, cboBranchInTwoDaysBridge.isChecked());
                // special set
                bridgeTypeFlag.put(BridgeType.BIG_DOUBLE, cboBigDoubleSet.isChecked());
                bridgeTypeFlag.put(BridgeType.SAME_DOUBLE, cboSameDoubleSet.isChecked());
                bridgeTypeFlag.put(BridgeType.POSITIVE_DOUBLE, cboPositiveDoubleSet.isChecked());
                // other set
                List<Input> inputs = new ArrayList<>();
                inputs.add(new Input(InputType.SET, edtSet.getText().toString().trim(), 2));
                inputs.add(new Input(InputType.TOUCH, edtTouch.getText().toString().trim(), 1));
                inputs.add(new Input(InputType.SUM, edtSum.getText().toString().trim(), 1));
                inputs.add(new Input(InputType.BRANCH, edtBranch.getText().toString().trim(), 2));
                inputs.add(new Input(InputType.HEAD, edtHead.getText().toString().trim(), 1));
                inputs.add(new Input(InputType.TAIL, edtTail.getText().toString().trim(), 1));
                inputs.add(new Input(InputType.COMBINE, edtCombine.getText().toString().trim(), 2));
                if (!numberOfDayStr.isEmpty()) {
                    int numberOfDay = Boolean.TRUE.equals(bridgeTypeFlag.get(BridgeType.CONNECTED)) &&
                            Integer.parseInt(numberOfDayStr) >
                                    lotteryList.size() - Const.CONNECTED_BRIDGE_FINDING_DAYS ?
                            lotteryList.size() - Const.CONNECTED_BRIDGE_FINDING_DAYS :
                            Integer.parseInt(numberOfDayStr);
                    viewModel.getCombineBridgeList(jackpotList, lotteryList, numberOfDay, bridgeTypeFlag,
                            inputs);
                }
            }
        });

        imgBridgeAnnotation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                WidgetBase.hideKeyboard(BridgeCombinationActivity.this);
                DialogBase.showBasic(
                        BridgeCombinationActivity.this, "Chú giải", Const.BRIDGE_ANNOTATION);
            }
        });
    }

    @Override
    public void showAllBridgeToday(Map<BridgeType, Bridge> bridgeMap) {
        String combine = Objects.requireNonNull(bridgeMap.get(BridgeType.COMBINE_TOUCH)).showCompactNumbers();
        String connected = Objects.requireNonNull(bridgeMap.get(BridgeType.CONNECTED)).showCompactNumbers();
        String shadow = Objects.requireNonNull(bridgeMap.get(BridgeType.SHADOW_TOUCH)).showCompactNumbers();
        String lotto = Objects.requireNonNull(bridgeMap.get(BridgeType.LOTTO_TOUCH)).showCompactNumbers();
        String lastDay = Objects.requireNonNull(bridgeMap.get(BridgeType.LAST_DAY_SHADOW)).showCompactNumbers();
        String lastWeek = Objects.requireNonNull(bridgeMap.get(BridgeType.LAST_WEEK_SHADOW)).showCompactNumbers();
        String mapping = "" + Objects.requireNonNull(bridgeMap.get(BridgeType.MAPPING)).getNumbers().size();
        String conectedSet = Objects.requireNonNull(bridgeMap.get(BridgeType.CONNECTED_SET)).showCompactNumbers();
        String estimated = "" + Objects.requireNonNull(bridgeMap.get(BridgeType.ESTIMATED)).getNumbers().size();
        cboCombineTouchBridge.setText("kết hợp " + combine);
        cboConnectedBridge.setText("liên thông " + connected);
        cboShadowTouchBridge.setText("bóng " + shadow);
        cboLottoTouchBridge.setText("lô tô " + lotto);
        cboLastDayShadowBridge.setText("ngày " + lastDay);
        cboLastWeekShadowBridge.setText("tuần " + lastWeek);
        cboMappingBridge.setText("ánh xạ " + mapping);
        cboConnectedSetBridge.setText("liên bộ " + conectedSet);
        cboEstimatedBridge.setText("ước lg " + estimated);
    }

    @Override
    public void showCombineBridgeList(List<CombineBridge> combineBridges) {
        StringBuilder show = new StringBuilder();
        int count = 0;
        for (CombineBridge bridge : combineBridges) {
            show.append(bridge.showBridge()).append("\n");
            if (bridge.isWin()) count++;
        }
        DialogBase.showWithCopiedText(this,
                "Cầu kết hợp", "Tỉ lệ: " + count + "/" + combineBridges.size() +
                        "\n" + show, combineBridges.get(0).showNumbers(), "KQ");
    }

}
