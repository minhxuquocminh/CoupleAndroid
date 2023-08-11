package com.example.couple.View.Couple;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.couple.Custom.Handler.CoupleHandler;
import com.example.couple.Base.View.WidgetBase;
import com.example.couple.Custom.Const.Const;
import com.example.couple.Custom.Widget.CustomTableLayout;
import com.example.couple.Custom.Widget.CustomTextView;
import com.example.couple.Model.Origin.Jackpot;
import com.example.couple.Model.Support.PeriodHistory;
import com.example.couple.R;
import com.example.couple.ViewModel.Couple.BalanceCoupleViewModel;

import java.util.List;

public class BanlanceCoupleActivity extends AppCompatActivity implements BalanceCoupleView {
    CheckBox cboCombineBridgeCheck;
    EditText edtNumberOfDays;
    EditText edtDayNumberBefore;
    EditText edtFilterDays;
    TextView tvViewPeriodBridge;
    TextView tvViewCombineBridge;
    TextView tvGetData;
    LinearLayout linearLayout;

    BalanceCoupleViewModel viewModel;

    public static final int NUMBER_OF_DAYS = 18;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_balance_couple);

        cboCombineBridgeCheck = findViewById(R.id.cboCombineBridgeCheck);
        edtNumberOfDays = findViewById(R.id.edtFindingDays);
        edtDayNumberBefore = findViewById(R.id.edtDayNumberBefore);
        edtFilterDays = findViewById(R.id.edtFilterDays);
        tvViewPeriodBridge = findViewById(R.id.tvViewPeriodBridge);
        tvViewCombineBridge = findViewById(R.id.tvViewCombineBridge);
        tvGetData = findViewById(R.id.tvGetData);
        linearLayout = findViewById(R.id.linearLayout);

        edtNumberOfDays.setText(NUMBER_OF_DAYS + "");
        edtNumberOfDays.setSelection(edtNumberOfDays.getText().length());

        viewModel = new BalanceCoupleViewModel(this, this);
        viewModel.GetJackpotDataFromFile();
    }

    @Override
    public void ShowError(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void ShowJackpotData(List<Jackpot> jackpotList) {
        viewModel.GetTableOfBalanceCouple(jackpotList, NUMBER_OF_DAYS);

        tvGetData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                WidgetBase.hideKeyboard(BanlanceCoupleActivity.this);
                String numberOfDaysStr = edtNumberOfDays.getText().toString().trim();
                if (numberOfDaysStr.length() == 0) {
                    ShowError("Bạn chưa nhập số ngày để lấy dữ liệu!");
                } else if (Integer.parseInt(numberOfDaysStr) > Const.DAY_OF_YEAR
                        || Integer.parseInt(numberOfDaysStr) < 0) {
                    ShowError("Nằm ngoài phạm vi!");
                } else {
                    viewModel.GetTableOfBalanceCouple(jackpotList, Integer.parseInt(numberOfDaysStr));
                }
            }
        });

        tvViewPeriodBridge.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                WidgetBase.hideKeyboard(BanlanceCoupleActivity.this);
                String dayNumberBefore = edtDayNumberBefore.getText().toString().trim();
                String filterDays = edtFilterDays.getText().toString().trim();
                if (!dayNumberBefore.equals("") && !filterDays.equals("")) {
                    viewModel.GetPeriodHistory(jackpotList, dayNumberBefore, filterDays);
                }
            }
        });
        tvViewCombineBridge.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                WidgetBase.hideKeyboard(BanlanceCoupleActivity.this);
                String dayNumberBefore = edtDayNumberBefore.getText().toString().trim();
                int bridgeType = cboCombineBridgeCheck.isChecked() ? 1 : 0;
                if (!dayNumberBefore.equals("")) {
                    viewModel.GetCombineBridge(jackpotList, dayNumberBefore, bridgeType);
                }
            }
        });

    }

    @Override
    public void ShowTableOfBalanceCouple(List<Jackpot> jackpotList, int numberOfDays) {
        TableLayout tableLayout = CustomTableLayout.getBalanceCoupleTableLayout(this,
                jackpotList, numberOfDays);
        linearLayout.removeAllViews();
        linearLayout.addView(tableLayout);
        linearLayout.addView(CustomTextView.getTextViewNote(this));
    }

    @Override
    public void ShowPeriodHistory(List<PeriodHistory> periodHistoryList) {
        String show = periodHistoryList.isEmpty() ? "Không có khoảng cần tìm"
                : "Các khoảng gần giống khoảng gần đây:\n";
        for (PeriodHistory periodHistory : periodHistoryList) {
            show += periodHistory.show() + "\n";
        }
        new AlertDialog.Builder(this)
                .setTitle("Cầu tìm khoảng")
                .setMessage(show)
                .setNegativeButton("OK", null)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }

    @Override
    public void ShowTest(List<Integer> touchs) {
        String message = "Chạm: ";
        message += CoupleHandler.showTouchs(touchs);
        WidgetBase.showDialogCanBeCopied(this,
                "Cầu chạm.", message, CoupleHandler.showTouchs(touchs));
    }

}
