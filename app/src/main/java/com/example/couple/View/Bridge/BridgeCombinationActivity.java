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
import com.example.couple.Model.BridgeCouple.CombineBridge;
import com.example.couple.Model.BridgeCouple.MappingBridge;
import com.example.couple.Model.BridgeCouple.PeriodBridge;
import com.example.couple.Model.BridgeCouple.ShadowMappingBridge;
import com.example.couple.Model.BridgeCouple.SpecialSet;
import com.example.couple.Model.BridgeSingle.CombineTouchBridge;
import com.example.couple.Model.BridgeSingle.ShadowTouchBridge;
import com.example.couple.Model.Origin.Jackpot;
import com.example.couple.Model.Origin.Lottery;
import com.example.couple.R;
import com.example.couple.ViewModel.Bridge.BridgeCombinationViewModel;

import java.util.List;

public class BridgeCombinationActivity extends AppCompatActivity implements BridgeCombinationView {
    EditText edtDayNumber;
    CheckBox cboSets;
    EditText edtSets;
    CheckBox cboTouchs;
    EditText edtTouchs;
    CheckBox cboShadowTouchBridge;
    CheckBox cboConnectedBridge;
    CheckBox cboMappingBridge;
    CheckBox cboShadowMappingBridge;
    CheckBox cboPeriodBridge;
    CheckBox cboNegativeShadowBridge;
    CheckBox cboPositiveShadowBridge;
    CheckBox cboLottoTouchBridge;
    CheckBox cboCombineTouchBridge;
    CheckBox cboBigDoubleSet;
    Button btnFindingBridge;
    Button btnTouchBridge;
    Button btnShadowTouchBridge;
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
        cboShadowTouchBridge = findViewById(R.id.cboShadowTouchBridge);
        cboConnectedBridge = findViewById(R.id.cboConnectedBridge);
        cboMappingBridge = findViewById(R.id.cboMappingBridge);
        cboShadowMappingBridge = findViewById(R.id.cboShadowMappingBridge);
        cboPeriodBridge = findViewById(R.id.cboPeriodBridge);
        cboNegativeShadowBridge = findViewById(R.id.cboNegativeShadowBridge);
        cboPositiveShadowBridge = findViewById(R.id.cboPositiveShadowBridge);
        cboLottoTouchBridge = findViewById(R.id.cboLottoTouchBridge);
        cboCombineTouchBridge = findViewById(R.id.cboCombineTouchBridge);
        cboBigDoubleSet = findViewById(R.id.cboBigDoubleSet);
        btnFindingBridge = findViewById(R.id.btnFindingBridge);
        btnTouchBridge = findViewById(R.id.btnTouchBridge);
        btnShadowTouchBridge = findViewById(R.id.btnShadowTouchBridge);
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
        viewModel.GetLotteryAndJackpotList();


    }

    @Override
    public void ShowError(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void ShowLotteryAndJackpotList(List<Jackpot> jackpotList, List<Lottery> lotteryList) {
        viewModel.GetAllBridgeToday(jackpotList, lotteryList);
        btnFindingBridge.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                WidgetBase.hideKeyboard(BridgeCombinationActivity.this);
                String numberOfDayStr = edtDayNumber.getText().toString().trim();
                boolean shadowTouch = cboShadowTouchBridge.isChecked();
                boolean connected = cboConnectedBridge.isChecked();
                boolean lottoTouch = cboLottoTouchBridge.isChecked();
                boolean mapping = cboMappingBridge.isChecked();
                boolean shadowMapping = cboShadowMappingBridge.isChecked();
                boolean period = cboPeriodBridge.isChecked();
                boolean negativeShadow = cboNegativeShadowBridge.isChecked();
                boolean positiveShadow = cboPositiveShadowBridge.isChecked();
                boolean combineTouch = cboCombineTouchBridge.isChecked();
                boolean bigDouble = cboBigDoubleSet.isChecked();
                if (!numberOfDayStr.equals("")) {
                    int numberOfDay = connected && Integer.parseInt(numberOfDayStr) >
                            lotteryList.size() - Const.CONNECTED_BRIDGE_FINDING_DAYS ?
                            lotteryList.size() - Const.CONNECTED_BRIDGE_FINDING_DAYS :
                            Integer.parseInt(numberOfDayStr);
                    viewModel.GetCombineBridgeList(jackpotList, lotteryList, numberOfDay,
                            shadowTouch, connected, lottoTouch, mapping, shadowMapping, period,
                            negativeShadow, positiveShadow, combineTouch, bigDouble);
                }
            }
        });
        btnTouchBridge.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                WidgetBase.hideKeyboard(BridgeCombinationActivity.this);
                String numberOfDay = edtDayNumber.getText().toString().trim();
                if (!numberOfDay.equals("") && Integer.parseInt(numberOfDay) <
                        lotteryList.size() - Const.CONNECTED_BRIDGE_FINDING_DAYS) {
                    viewModel.GetTouchBridgeList(jackpotList, lotteryList, Integer.parseInt(numberOfDay));
                }

            }
        });

        btnShadowTouchBridge.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                WidgetBase.hideKeyboard(BridgeCombinationActivity.this);
                String numberOfDay = edtDayNumber.getText().toString().trim();
                if (!numberOfDay.equals(""))
                    viewModel.GetShadowTouchBridgeList(jackpotList, Integer.parseInt(numberOfDay));
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
        cboShadowTouchBridge.setText("Chạm bóng " + combineBridge.getShadowTouchBridge().showTouchs());
        cboConnectedBridge.setText("Liên thông " + combineBridge.getConnectedBridge().showTouchs());
        cboMappingBridge.setText("Ánh xạ " + combineBridge.getMappingBridge().getNumbers().size());
        cboShadowMappingBridge.setText("Ánh xạ bóng " + combineBridge.getShadowMappingBridge().getNumbers().size());
        cboPeriodBridge.setText("Khoảng " + combineBridge.getPeriodBridge().getNumbers().size());
        cboNegativeShadowBridge.setText("Chạm bóng âm " + combineBridge.getNegativeShadowBridge().showTouchs());
        cboPositiveShadowBridge.setText("Chạm bóng dương " + combineBridge.getPositiveShadowBridge().showTouchs());
        cboLottoTouchBridge.setText("Chạm lô tô " + combineBridge.getLottoTouchBridge().showTouchs());
        cboCombineTouchBridge.setText("Chạm kết hợp " + combineBridge.getCombineTouchBridge().showTouchs());
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
        String show = "";
        int count = 0;
        for (CombineTouchBridge bridge : combineTouchBridges) {
            show += bridge.showBridge() + "\n";
            if (bridge.isWin()) count++;
        }
        WidgetBase.showDialogCanBeCopied(this,
                "Cầu chạm", "Tỉ lệ: " + count + "/" + combineTouchBridges.size() +
                        "\n" + show, combineTouchBridges.get(0).showTouchs());
    }

    @Override
    public void ShowShadowTouchBridgeList(List<ShadowTouchBridge> touchBridges) {
        String show = "";
        int count = 0;
        for (ShadowTouchBridge bridge : touchBridges) {
            show += bridge.showBridge() + "\n";
            if (bridge.isWin()) count++;
        }
        WidgetBase.showDialogCanBeCopied(this,
                "Cầu chạm bóng", "Tỉ lệ: " + count + "/" + touchBridges.size() +
                        "\n" + show, touchBridges.get(0).showNumbers());
    }

    @Override
    public void ShowPeriodBridgeList(List<PeriodBridge> periodBridges) {
        String show = "Ghi chú: nên chơi khi số lượng KQ tổ hợp lớn hơn hoặc bằng 55, " +
                " nếu số lượng nhỏ hơn 55 thì cần xem lại thông tin chi tiết của cầu.\n";
        int count = 0;
        for (PeriodBridge bridge : periodBridges) {
            show += bridge.showBridge() + "\n";
            if (bridge.isWin()) count++;
        }
        WidgetBase.showDialogCanBeCopied(this,
                "Cầu khoảng", "Tỉ lệ: " + count + "/" + periodBridges.size() +
                        "\n" + show, periodBridges.get(0).showNumbers());
    }

    @Override
    public void ShowMappingBridgeList(List<MappingBridge> mappingBridgeList) {
        String show = "Ghi chú: nếu hôm trước có đầu 0 thì hôm sau dễ xịt, " +
                "khi tạo hình của 2 hôm trước tạo nước đi khó thì cũng dễ bị lọt. " +
                "Cần xem kĩ các thế đi của đề để có thể tạo ra tỉ lệ trúng cao. " +
                "Số lượng tổ hợp hay xịt là { 72, 79 }.\n";
        int count = 0;
        for (MappingBridge bridge : mappingBridgeList) {
            show += bridge.showBridge() + "\n";
            if (bridge.isWin()) count++;
        }
        WidgetBase.showDialogCanBeCopied(this,
                "Cầu ánh xạ", "Tỉ lệ: " + count + "/" + mappingBridgeList.size() +
                        "\n" + show, mappingBridgeList.get(0).showNumbers());
    }

    @Override
    public void ShowShadowMappingBridgeList(List<ShadowMappingBridge> shadowMappingBridgeList) {
        String show = "Ghi chú: nếu 1-2 hôm trước ra kép thì chơi ít lại, " +
                "gặp số lượng tổ hợp là { 57, 52, 51 } thì hay bị xịt. " +
                "Tỉ lệ trúng cao khi SLTH lớn hơn 70 rồi đến 60, " +
                "các trường hợp nhỏ hơn vẫn có thể trúng nhưng xác suất khá thấp.\n";
        int count = 0;
        for (ShadowMappingBridge bridge : shadowMappingBridgeList) {
            show += bridge.showBridge() + "\n";
            if (bridge.isWin()) count++;
        }
        WidgetBase.showDialogCanBeCopied(this, "Cầu ánh xạ bóng",
                "Tỉ lệ: " + count + "/" + shadowMappingBridgeList.size() + "\n" + show,
                shadowMappingBridgeList.get(0).showCopyCombineShadowCouples());
    }

    @Override
    public void ShowSet(List<Integer> bigDoubleSet) {
        WidgetBase.showDialogCanBeCopied(this, "Bộ số", "Thông tin bộ số: "
                + CoupleHandler.showCoupleNumbers(bigDoubleSet), CoupleHandler.showCoupleNumbers(bigDoubleSet));
    }

    @Override
    public void ShowBigDoubleSet(List<SpecialSet> bigDoubleSets) {
        String show = "";
        int count = 0;
        for (SpecialSet set : bigDoubleSets) {
            show += set.showBridge() + "\n";
            if (set.isWin()) count++;
        }
        WidgetBase.showDialogCanBeCopied(this, bigDoubleSets.get(0).getBridgeName(),
                "Tỉ lệ: " + count + "/" + bigDoubleSets.size() + "\n" + show,
                bigDoubleSets.get(0).showNumbers());
    }

    @Override
    public void ShowDoubleSet(List<SpecialSet> doubleSets) {
        String show = "";
        int count = 0;
        for (SpecialSet set : doubleSets) {
            show += set.showBridge() + "\n";
            if (set.isWin()) count++;
        }
        WidgetBase.showDialogCanBeCopied(this, doubleSets.get(0).getBridgeName(),
                "Tỉ lệ: " + count + "/" + doubleSets.size() + "\n" + show,
                doubleSets.get(0).showNumbers());
    }

    @Override
    public void ShowNearDoubleSet(List<SpecialSet> nearDoubleSets) {
        String show = "";
        int count = 0;
        for (SpecialSet set : nearDoubleSets) {
            show += set.showBridge() + "\n";
            if (set.isWin()) count++;
        }
        WidgetBase.showDialogCanBeCopied(this, nearDoubleSets.get(0).getBridgeName(),
                "Tỉ lệ: " + count + "/" + nearDoubleSets.size() + "\n" + show,
                nearDoubleSets.get(0).showNumbers());
    }

}
