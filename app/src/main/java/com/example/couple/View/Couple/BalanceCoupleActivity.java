package com.example.couple.View.Couple;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.couple.Base.Handler.SingleBase;
import com.example.couple.Base.Handler.StorageBase;
import com.example.couple.Base.View.ActivityBase;
import com.example.couple.Base.View.DialogBase;
import com.example.couple.Base.View.Table.TableData;
import com.example.couple.Base.View.Table.TableLayoutBase;
import com.example.couple.Base.View.TextViewBase;
import com.example.couple.Base.View.WidgetBase;
import com.example.couple.Custom.Const.TimeInfo;
import com.example.couple.Custom.Enum.StorageType;
import com.example.couple.Custom.Handler.Display.TableDataConverter;
import com.example.couple.Custom.Handler.Display.TableDataSupport;
import com.example.couple.Model.Bridge.Estimated.PeriodHistory;
import com.example.couple.Model.Origin.Jackpot;
import com.example.couple.R;
import com.example.couple.ViewModel.Couple.BalanceCoupleViewModel;

import java.util.List;
import java.util.Map;

public class BalanceCoupleActivity extends ActivityBase implements BalanceCoupleView {
    EditText edtNumberOfDays;
    EditText edtDayNumberBefore;
    EditText edtFilterDays;
    TextView tvViewPeriodHistory;
    TextView tvGetData;
    LinearLayout linearLayout;

    BalanceCoupleViewModel viewModel;

    public static final int NUMBER_OF_DAYS = 18;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_balance_couple);

        edtNumberOfDays = findViewById(R.id.edtFindingDays);
        edtDayNumberBefore = findViewById(R.id.edtDayNumberBefore);
        edtFilterDays = findViewById(R.id.edtFilterDays);
        tvViewPeriodHistory = findViewById(R.id.tvViewPeriodHistory);
        tvGetData = findViewById(R.id.tvGetData);
        linearLayout = findViewById(R.id.linearLayout);

        edtNumberOfDays.setText(NUMBER_OF_DAYS + "");
        edtNumberOfDays.setSelection(edtNumberOfDays.getText().length());

        viewModel = new BalanceCoupleViewModel(this, this);
        viewModel.getJackpotDataFromFile();
    }

    @Override
    public void showMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showJackpotData(List<Jackpot> jackpotList) {
        viewModel.getTableOfBalanceCouple(jackpotList, NUMBER_OF_DAYS);

        tvGetData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                WidgetBase.hideKeyboard(BalanceCoupleActivity.this);
                String numberOfDaysStr = edtNumberOfDays.getText().toString().trim();
                if (numberOfDaysStr.isEmpty()) {
                    showMessage("Bạn chưa nhập số ngày để lấy dữ liệu!");
                } else if (Integer.parseInt(numberOfDaysStr) > TimeInfo.DAY_OF_YEAR
                        || Integer.parseInt(numberOfDaysStr) < 0) {
                    showMessage("Nằm ngoài phạm vi!");
                } else {
                    viewModel.getTableOfBalanceCouple(jackpotList, Integer.parseInt(numberOfDaysStr));
                }
            }
        });

        tvViewPeriodHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                WidgetBase.hideKeyboard(BalanceCoupleActivity.this);
                String dayNumberBefore = edtDayNumberBefore.getText().toString().trim();
                String filterDays = edtFilterDays.getText().toString().trim();
                if (!dayNumberBefore.isEmpty() && !filterDays.isEmpty()) {
                    viewModel.getPeriodHistory(jackpotList, dayNumberBefore, filterDays);
                }
            }
        });
    }

    @Override
    public void showTableOfBalanceCouple(List<Jackpot> jackpotList, int numberOfDays) {
        int picker = StorageBase.getNumber(this, StorageType.NUMBER_OF_PICKER);
        TableData tableData = TableDataConverter.getBalanceCouple(jackpotList, numberOfDays, picker);
        Map<Integer, TextViewBase> bodyManager = TableDataSupport
                .getSundayTextViewManager(this, jackpotList, numberOfDays);
        TableLayout tableLayout = TableLayoutBase.getTableLayoutWithNewStyleRow(this,
                tableData, null, bodyManager, true);
        linearLayout.removeAllViews();
        linearLayout.addView(tableLayout);
        linearLayout.addView(TextViewBase.getNoteTextView(this, "Note: Các ô màu xanh ứng với ngày chủ nhật."));
    }

    @Override
    public void showPeriodHistory(List<PeriodHistory> periodHistoryList) {
        String show = periodHistoryList.isEmpty() ? "Không có khoảng cần tìm"
                : "Lịch sử các cách chạy gần giống khoảng gần đây:\n";
        for (PeriodHistory periodHistory : periodHistoryList) {
            show += periodHistory.show() + "\n";
        }
        DialogBase.showBasic(this, "Lịch sử", show);
    }

    @Override
    public void showTest(List<Integer> touchs) {
        String message = "Chạm: ";
        message += SingleBase.showTouches(touchs);
        DialogBase.showWithCopiedText(this,
                "Cầu chạm.", message, SingleBase.showTouches(touchs), "test");
    }

    @Override
    public Context getContext() {
        return this;
    }
}
