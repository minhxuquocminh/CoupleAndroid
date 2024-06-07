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
import com.example.couple.Model.Bridge.BridgeType;
import com.example.couple.Model.Bridge.CombineBridge;
import com.example.couple.Model.Couple.CoupleType;
import com.example.couple.Model.Handler.Input;
import com.example.couple.Model.Origin.Jackpot;
import com.example.couple.Model.Origin.Lottery;
import com.example.couple.R;
import com.example.couple.ViewModel.Bridge.BridgeCombinationViewModel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    CheckBox cboNegativeShadowBridge;
    CheckBox cboPositiveShadowBridge;
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
        cboNegativeShadowBridge = findViewById(R.id.cboNegativeShadowBridge);
        cboPositiveShadowBridge = findViewById(R.id.cboPositiveShadowBridge);
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
                bridgeTypeFlag.put(BridgeType.NEGATIVE_SHADOW, cboNegativeShadowBridge.isChecked());
                bridgeTypeFlag.put(BridgeType.POSITIVE_SHADOW, cboPositiveShadowBridge.isChecked());
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
                inputs.add(new Input(CoupleType.SET, edtSet.getText().toString().trim(), 2));
                inputs.add(new Input(CoupleType.TOUCH, edtTouch.getText().toString().trim(), 1));
                inputs.add(new Input(CoupleType.SUM, edtSum.getText().toString().trim(), 1));
                inputs.add(new Input(CoupleType.BRANCH, edtBranch.getText().toString().trim(), 2));
                inputs.add(new Input(CoupleType.HEAD, edtHead.getText().toString().trim(), 1));
                inputs.add(new Input(CoupleType.TAIL, edtTail.getText().toString().trim(), 1));
                inputs.add(new Input(CoupleType.COMBINE, edtCombine.getText().toString().trim(), 2));
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
    public void showAllBridgeToday(CombineBridge combineBridge) {
        cboCombineTouchBridge.setText("kết hợp " + combineBridge.getBridgeList().get(0).showCompactNumbers());
        cboConnectedBridge.setText("liên thông " + combineBridge.getBridgeList().get(1).showCompactNumbers());
        cboShadowTouchBridge.setText("bóng " + combineBridge.getBridgeList().get(2).showCompactNumbers());
        cboLottoTouchBridge.setText("lô tô " + combineBridge.getBridgeList().get(3).showCompactNumbers());
        cboNegativeShadowBridge.setText("bóng âm " + combineBridge.getBridgeList().get(4).showCompactNumbers());
        cboPositiveShadowBridge.setText("bóng dương " + combineBridge.getBridgeList().get(5).showCompactNumbers());
        cboMappingBridge.setText("ánh xạ " + combineBridge.getBridgeList().get(6).getNumbers().size());
        cboConnectedSetBridge.setText("liên bộ " + combineBridge.getBridgeList().get(7).showCompactNumbers());
        cboEstimatedBridge.setText("ước lg " + combineBridge.getBridgeList().get(8).getNumbers().size());
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
