package com.example.couple.View.Bridge;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.couple.Base.View.WidgetBase;
import com.example.couple.Custom.Const.Const;
import com.example.couple.Custom.Handler.CoupleHandler;
import com.example.couple.Model.Bridge.CombineBridge;
import com.example.couple.Model.Bridge.Couple.MappingBridge;
import com.example.couple.Model.Bridge.Couple.PeriodBridge;
import com.example.couple.Model.Bridge.Couple.ShadowMappingBridge;
import com.example.couple.Model.Bridge.Couple.SpecialSetBridge;
import com.example.couple.Model.Bridge.Single.CombineTouchBridge;
import com.example.couple.Model.Bridge.Single.ShadowTouchBridge;
import com.example.couple.Model.Origin.Jackpot;
import com.example.couple.Model.Origin.Lottery;
import com.example.couple.Model.Time.TimeBase;
import com.example.couple.R;
import com.example.couple.ViewModel.Bridge.BridgeCombinationViewModel;

import java.util.List;

public class BridgeCombinationActivity extends AppCompatActivity implements BridgeCombinationView {
    EditText edtDayNumber;
    CheckBox cboSets;
    EditText edtSets;
    CheckBox cboTouchs;
    EditText edtTouchs;
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
    CheckBox cboPeriodBridge;
    CheckBox cboMappingBridge1;
    CheckBox cboCompatible;
    CheckBox cboIncompatible;
    CheckBox cboMatchMappingBridge;
    CheckBox cboTriadMappingBridge;
    CheckBox cboShadowExchangeBridge;
    //
    CheckBox cboBigDoubleSet;
    CheckBox cboSameDoubleSet;
    CheckBox cboNearDoubleSet;
    //
    Button btnFindingBridge;
    Button btnBridgeAnnotation;
    Button btnPeriodBridge;
    Button btnMappingBridge;
    Button btnShadowMappingBridge;
    Button btnBigDoubleSet;
    Button btnDoubleSet;
    Button btnNearDoubleSet;
    TextView tvShowCombineBridge;

    BridgeCombinationViewModel viewModel;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bridge_combination);

        edtDayNumber = findViewById(R.id.edtDayNumber);
        cboSets = findViewById(R.id.cboSets);
        edtSets = findViewById(R.id.edtSets);
        cboTouchs = findViewById(R.id.cboTouchs);
        edtTouchs = findViewById(R.id.edtTouchs);
        // touch
        cboLottoTouchBridge = findViewById(R.id.cboLottoTouchBridge);
        cboCombineTouchBridge = findViewById(R.id.cboCombineTouchBridge);
        cboShadowTouchBridge = findViewById(R.id.cboShadowTouchBridge);
        cboConnectedBridge = findViewById(R.id.cboConnectedBridge);
        cboNegativeShadowBridge = findViewById(R.id.cboNegativeShadowBridge);
        cboPositiveShadowBridge = findViewById(R.id.cboPositiveShadowBridge);
        // mapping, period
        cboMappingBridge = findViewById(R.id.cboMappingBridge);
        cboMappingBridge1 = findViewById(R.id.cboMappingBridge1);
        cboCompatible = findViewById(R.id.cboCompatible);
        cboIncompatible = findViewById(R.id.cboIncompatible);
        cboShadowMappingBridge = findViewById(R.id.cboShadowMappingBridge);
        cboPeriodBridge = findViewById(R.id.cboPeriodBridge);
        cboMatchMappingBridge = findViewById(R.id.cboMatchMappingBridge);
        cboTriadMappingBridge = findViewById(R.id.cboTriadMappingBridge);
        cboShadowExchangeBridge = findViewById(R.id.cboShadowExchangeBridge);
        // special set
        cboBigDoubleSet = findViewById(R.id.cboBigDoubleSet);
        cboSameDoubleSet = findViewById(R.id.cboSameDoubleSet);
        cboNearDoubleSet = findViewById(R.id.cboNearDoubleSet);
        // button
        btnFindingBridge = findViewById(R.id.btnFindingBridge);
        btnBridgeAnnotation = findViewById(R.id.btnBridgeAnnotation);
        btnPeriodBridge = findViewById(R.id.btnPeriodBridge);
        btnMappingBridge = findViewById(R.id.btnMappingBridge);
        btnShadowMappingBridge = findViewById(R.id.btnShadowMappingBridge);
        btnBigDoubleSet = findViewById(R.id.btnBigDoubleSet);
        btnDoubleSet = findViewById(R.id.btnDoubleSet);
        btnNearDoubleSet = findViewById(R.id.btnNearDoubleSet);
        tvShowCombineBridge = findViewById(R.id.tvShowCombineBridge);

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
                // mapping, period
                boolean mapping = cboMappingBridge.isChecked();
                boolean shadowMapping = cboShadowMappingBridge.isChecked();
                boolean period = cboPeriodBridge.isChecked();
                boolean mapping1 = cboMappingBridge1.isChecked();
                boolean compatible = cboCompatible.isChecked();
                boolean incompatible = cboIncompatible.isChecked();
                boolean matchMapping = cboMatchMappingBridge.isChecked();
                boolean triadMapping = cboTriadMappingBridge.isChecked();
                boolean shadowExchange = cboShadowExchangeBridge.isChecked();
                // special set
                boolean bigDouble = cboBigDoubleSet.isChecked();
                boolean sameDouble = cboSameDoubleSet.isChecked();
                boolean nearDouble = cboNearDoubleSet.isChecked();
                if (!numberOfDayStr.equals("")) {
                    int numberOfDay = connected && Integer.parseInt(numberOfDayStr) >
                            lotteryList.size() - Const.CONNECTED_BRIDGE_FINDING_DAYS ?
                            lotteryList.size() - Const.CONNECTED_BRIDGE_FINDING_DAYS :
                            Integer.parseInt(numberOfDayStr);
                    viewModel.GetCombineBridgeList(jackpotList, lotteryList, timeBaseNextDay, numberOfDay,
                            combineTouch, connected, shadowTouch, lottoTouch, negativeShadow, positiveShadow,
                            mapping, shadowMapping, period, mapping1, compatible, incompatible, matchMapping,
                            triadMapping, shadowExchange, bigDouble, sameDouble, nearDouble);
                }
            }
        });

        btnBridgeAnnotation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                WidgetBase.hideKeyboard(BridgeCombinationActivity.this);
                WidgetBase.showDialog(
                        BridgeCombinationActivity.this, "Chú giải", Const.BRIDGE_ANNOTATION);
            }
        });

        btnPeriodBridge.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                WidgetBase.hideKeyboard(BridgeCombinationActivity.this);
                String numberOfDay = edtDayNumber.getText().toString().trim();
                if (!numberOfDay.equals(""))
                    viewModel.GetPeriodBridgeList(jackpotList, Integer.parseInt(numberOfDay));
            }
        });

        btnMappingBridge.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                WidgetBase.hideKeyboard(BridgeCombinationActivity.this);
                String numberOfDay = edtDayNumber.getText().toString().trim();
                if (!numberOfDay.equals(""))
                    viewModel.GetMappingBridgeList(jackpotList, Integer.parseInt(numberOfDay));
            }
        });

        btnShadowMappingBridge.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                WidgetBase.hideKeyboard(BridgeCombinationActivity.this);
                String numberOfDay = edtDayNumber.getText().toString().trim();
                if (!numberOfDay.equals(""))
                    viewModel.GetShadowBridgeList(jackpotList, Integer.parseInt(numberOfDay));
            }
        });

        btnBigDoubleSet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                WidgetBase.hideKeyboard(BridgeCombinationActivity.this);
                String numberOfDay = edtDayNumber.getText().toString().trim();
                if (!numberOfDay.equals(""))
                    viewModel.GetBigDoubleSet(jackpotList, Integer.parseInt(numberOfDay));
            }
        });

        btnDoubleSet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                WidgetBase.hideKeyboard(BridgeCombinationActivity.this);
                String numberOfDay = edtDayNumber.getText().toString().trim();
                if (!numberOfDay.equals(""))
                    viewModel.GetDoubleSet(jackpotList, Integer.parseInt(numberOfDay));
            }
        });

        btnNearDoubleSet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                WidgetBase.hideKeyboard(BridgeCombinationActivity.this);
                String numberOfDay = edtDayNumber.getText().toString().trim();
                if (!numberOfDay.equals(""))
                    viewModel.GetNearDoubleSet(jackpotList, Integer.parseInt(numberOfDay));
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
        cboPeriodBridge.setText("khoảng " + combineBridge.getBridgeList().get(8).getNumbers().size());
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

    @Override
    public void ShowTouchBridgeList(List<CombineTouchBridge> combineTouchBridges) {
//        String show = "";
//        int count = 0;
//        for (CombineTouchBridge bridge : combineTouchBridges) {
//            show += bridge.showBridge() + "\n";
//            if (bridge.isWin()) count++;
//        }
//        WidgetBase.showDialogCanBeCopied(this,
//                "Cầu chạm", "Tỉ lệ: " + count + "/" + combineTouchBridges.size() +
//                        "\n" + show, combineTouchBridges.get(0).showTouchs());
    }

    @Override
    public void ShowShadowTouchBridgeList(List<ShadowTouchBridge> touchBridges) {
//        String show = "";
//        int count = 0;
//        for (ShadowTouchBridge bridge : touchBridges) {
//            show += bridge.showBridge() + "\n";
//            if (bridge.isWin()) count++;
//        }
//        WidgetBase.showDialogCanBeCopied(this,
//                "Cầu chạm bóng", "Tỉ lệ: " + count + "/" + touchBridges.size() +
//                        "\n" + show, touchBridges.get(0).showNumbers());
    }

    @Override
    public void ShowPeriodBridgeList(List<PeriodBridge> periodBridges) {
//        String show = "";
//        int count = 0;
//        for (PeriodBridge bridge : periodBridges) {
//            show += bridge.showBridge() + "\n";
//            if (bridge.isWin()) count++;
//        }
//        WidgetBase.showDialogCanBeCopied(this,
//                "Cầu khoảng", "Tỉ lệ: " + count + "/" + periodBridges.size() +
//                        "\n" + show, periodBridges.get(0).showNumbers());
    }

    @Override
    public void ShowMappingBridgeList(List<MappingBridge> mappingBridgeList) {
//        String show = "";
//        int count = 0;
//        for (MappingBridge bridge : mappingBridgeList) {
//            show += bridge.showBridge() + "\n";
//            if (bridge.isWin()) count++;
//        }
//        WidgetBase.showDialogCanBeCopied(this,
//                "Cầu ánh xạ", "Tỉ lệ: " + count + "/" + mappingBridgeList.size() +
//                        "\n" + show, mappingBridgeList.get(0).showNumbers());
    }

    @Override
    public void ShowShadowMappingBridgeList(List<ShadowMappingBridge> shadowMappingBridgeList) {
//        String show = "";
//        int count = 0;
//        for (ShadowMappingBridge bridge : shadowMappingBridgeList) {
//            show += bridge.showBridge() + "\n";
//            if (bridge.isWin()) count++;
//        }
//        WidgetBase.showDialogCanBeCopied(this, "Cầu ánh xạ bóng",
//                "Tỉ lệ: " + count + "/" + shadowMappingBridgeList.size() + "\n" + show,
//                shadowMappingBridgeList.get(0).showCopyCombineShadowCouples());
    }

    @Override
    public void ShowSet(List<Integer> bigDoubleSet) {
        WidgetBase.showDialogCanBeCopied(this, "Bộ số", "Thông tin bộ số: "
                + CoupleHandler.showCoupleNumbers(bigDoubleSet), CoupleHandler.showCoupleNumbers(bigDoubleSet));
    }

    @Override
    public void ShowBigDoubleSet(List<SpecialSetBridge> bigDoubleSets) {
//        String show = "";
//        int count = 0;
//        for (SpecialSet set : bigDoubleSets) {
//            show += set.showBridge() + "\n";
//            if (set.isWin()) count++;
//        }
//        WidgetBase.showDialogCanBeCopied(this, bigDoubleSets.get(0).getBridgeName(),
//                "Tỉ lệ: " + count + "/" + bigDoubleSets.size() + "\n" + show,
//                bigDoubleSets.get(0).showNumbers());
    }

    @Override
    public void ShowDoubleSet(List<SpecialSetBridge> doubleSets) {
//        String show = "";
//        int count = 0;
//        for (SpecialSet set : doubleSets) {
//            show += set.showBridge() + "\n";
//            if (set.isWin()) count++;
//        }
//        WidgetBase.showDialogCanBeCopied(this, doubleSets.get(0).getBridgeName(),
//                "Tỉ lệ: " + count + "/" + doubleSets.size() + "\n" + show,
//                doubleSets.get(0).showNumbers());
    }

    @Override
    public void ShowNearDoubleSet(List<SpecialSetBridge> nearDoubleSets) {
//        String show = "";
//        int count = 0;
//        for (SpecialSet set : nearDoubleSets) {
//            show += set.showBridge() + "\n";
//            if (set.isWin()) count++;
//        }
//        WidgetBase.showDialogCanBeCopied(this, nearDoubleSets.get(0).getBridgeName(),
//                "Tỉ lệ: " + count + "/" + nearDoubleSets.size() + "\n" + show,
//                nearDoubleSets.get(0).showNumbers());
    }

}
