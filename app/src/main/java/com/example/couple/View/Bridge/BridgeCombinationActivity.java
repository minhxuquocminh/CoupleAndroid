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

import com.example.couple.Base.View.WidgetBase;
import com.example.couple.Custom.Const.Const;
import com.example.couple.Model.Bridge.CombineBridge;
import com.example.couple.Model.Origin.Jackpot;
import com.example.couple.Model.Origin.Lottery;
import com.example.couple.Model.Time.TimeBase;
import com.example.couple.R;
import com.example.couple.ViewModel.Bridge.BridgeCombinationViewModel;

import java.util.List;

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
    CheckBox cboShadowMappingBridge;
    CheckBox cboEstimatedBridge;
    CheckBox cboCompactRightMappingBridge;
    CheckBox cboCompatible;
    CheckBox cboIncompatible;
    CheckBox cboRightMappingBridge;
    CheckBox cboTriadMappingBridge;
    CheckBox cboShadowExchangeBridge;
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
        cboShadowMappingBridge = findViewById(R.id.cboShadowMappingBridge);
        cboEstimatedBridge = findViewById(R.id.cboEstimatedBridge);
        cboCompactRightMappingBridge = findViewById(R.id.cboCompactRightMappingBridge);
        cboTriadMappingBridge = findViewById(R.id.cboTriadMappingBridge);
        cboShadowExchangeBridge = findViewById(R.id.cboShadowExchangeBridge);
        // special set
        cboBigDoubleSet = findViewById(R.id.cboBigDoubleSet);
        cboSameDoubleSet = findViewById(R.id.cboSameDoubleSet);
        cboPositiveDoubleSet = findViewById(R.id.cboPositiveDoubleSet);
        // button
        btnFindingBridge = findViewById(R.id.btnFindingBridge);

        edtDayNumber.setText("18");
        edtDayNumber.setSelection(edtDayNumber.getText().length());

        viewModel = new BridgeCombinationViewModel(this, this);
        viewModel.GetTimeBaseNextDayAndLotteryAndJackpotList();


    }

    @Override
    public void ShowError(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void ShowLotteryAndJackpotAndTimeBaseList(List<Jackpot> jackpotList,
                                                     List<Lottery> lotteryList, TimeBase timeBaseNextDay) {
        viewModel.GetAllBridgeToday(jackpotList, lotteryList);
        btnFindingBridge.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                WidgetBase.hideKeyboard(BridgeCombinationActivity.this);
                String numberOfDayStr = edtDayNumber.getText().toString().trim();
                // touch
                boolean combineTouch = cboCombineTouchBridge.isChecked();
                boolean connected = cboConnectedBridge.isChecked();
                boolean shadowTouch = cboShadowTouchBridge.isChecked();
                boolean lottoTouch = cboLottoTouchBridge.isChecked();
                boolean negativeShadow = cboNegativeShadowBridge.isChecked();
                boolean positiveShadow = cboPositiveShadowBridge.isChecked();
                // mapping, estimated
                boolean mapping = cboMappingBridge.isChecked();
                boolean shadowMapping = cboShadowMappingBridge.isChecked();
                boolean estimated = cboEstimatedBridge.isChecked();
                boolean rightMapping = cboRightMappingBridge.isChecked();
                boolean compatible = cboCompatible.isChecked();
                boolean incompatible = cboIncompatible.isChecked();
                boolean compactRightMapping = cboCompactRightMappingBridge.isChecked();
                boolean triadMapping = cboTriadMappingBridge.isChecked();
                boolean shadowExchange = cboShadowExchangeBridge.isChecked();
                // special set
                boolean bigDouble = cboBigDoubleSet.isChecked();
                boolean sameDouble = cboSameDoubleSet.isChecked();
                boolean positiveDouble = cboPositiveDoubleSet.isChecked();
                // other set
                String setData = edtSet.getText().toString().trim();
                String touchData = edtTouch.getText().toString().trim();
                String sumData = edtSum.getText().toString().trim();
                String branchData = edtBranch.getText().toString().trim();
                String headData = edtHead.getText().toString().trim();
                String tailData = edtTail.getText().toString().trim();
                String combineData = edtCombine.getText().toString().trim();
                if (!numberOfDayStr.equals("")) {
                    int numberOfDay = connected && Integer.parseInt(numberOfDayStr) >
                            lotteryList.size() - Const.CONNECTED_BRIDGE_FINDING_DAYS ?
                            lotteryList.size() - Const.CONNECTED_BRIDGE_FINDING_DAYS :
                            Integer.parseInt(numberOfDayStr);
                    viewModel.GetCombineBridgeList(jackpotList, lotteryList, timeBaseNextDay, numberOfDay,
                            combineTouch, connected, shadowTouch, lottoTouch, negativeShadow, positiveShadow,
                            mapping, shadowMapping, estimated, rightMapping, compatible, incompatible,
                            compactRightMapping, triadMapping, shadowExchange, bigDouble, sameDouble, positiveDouble,
                            setData, touchData, sumData, branchData, headData, tailData, combineData
                    );
                }
            }
        });

        imgBridgeAnnotation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                WidgetBase.hideKeyboard(BridgeCombinationActivity.this);
                WidgetBase.showDialog(
                        BridgeCombinationActivity.this, "Chú giải", Const.BRIDGE_ANNOTATION);
            }
        });
    }

    @Override
    public void ShowAllBridgeToday(CombineBridge combineBridge) {
        cboCombineTouchBridge.setText("kết hợp " + combineBridge.getBridgeList().get(0).showTouchs());
        cboConnectedBridge.setText("liên thông " + combineBridge.getBridgeList().get(1).showTouchs());
        cboShadowTouchBridge.setText("bóng " + combineBridge.getBridgeList().get(2).showTouchs());
        cboLottoTouchBridge.setText("lô tô " + combineBridge.getBridgeList().get(3).showTouchs());
        cboNegativeShadowBridge.setText("bóng âm " + combineBridge.getBridgeList().get(4).showTouchs());
        cboPositiveShadowBridge.setText("bóng dương " + combineBridge.getBridgeList().get(5).showTouchs());
        cboMappingBridge.setText("ánh xạ " + combineBridge.getBridgeList().get(6).getNumbers().size());
        cboShadowMappingBridge.setText("ánh xạ bóng " + combineBridge.getBridgeList().get(7).getNumbers().size());
        cboEstimatedBridge.setText("ước lg " + combineBridge.getBridgeList().get(8).getNumbers().size());
    }

    @Override
    public void ShowCombineBridgeList(List<CombineBridge> combineBridges) {
        String show = "";
        int count = 0;
        for (CombineBridge bridge : combineBridges) {
            show += bridge.showBridge() + "\n";
            if (bridge.isWin()) count++;
        }
        WidgetBase.showDialogCanBeCopied(this,
                "Cầu kết hợp", "Tỉ lệ: " + count + "/" + combineBridges.size() +
                        "\n" + show, combineBridges.get(0).showNumbers());
    }

}
