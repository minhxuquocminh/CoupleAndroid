package com.example.couple.View.BridgeHistory;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.HorizontalScrollView;
import android.widget.TableLayout;
import android.widget.Toast;

import com.example.couple.Base.Handler.NumberBase;
import com.example.couple.Base.View.TableLayoutBase;
import com.example.couple.Base.View.WidgetBase;
import com.example.couple.Custom.Const.TimeInfo;
import com.example.couple.Custom.Widget.SpeechToTextActivity;
import com.example.couple.Model.Bridge.NumberSet.NumberSetHistory;
import com.example.couple.Model.Origin.Jackpot;
import com.example.couple.Base.View.RowData;
import com.example.couple.Base.View.TableData;
import com.example.couple.R;
import com.example.couple.ViewModel.JackpotStatistics.NumberSetHistoryViewModel;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class NumberSetHistoryActivity extends SpeechToTextActivity implements NumberSetHistoryView {
    EditText edtDayNumber;
    Button btnView;
    HorizontalScrollView hsTable;

    NumberSetHistoryViewModel viewModel;

    private static final int NUMBER_OF_DAYS_START = TimeInfo.DAY_OF_YEAR;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_number_set_history);

        edtDayNumber = findViewById(R.id.edtDayNumber);
        btnView = findViewById(R.id.btnView);
        hsTable = findViewById(R.id.hsTable);

        edtDayNumber.setText(NUMBER_OF_DAYS_START + "");
        edtDayNumber.setSelection(edtDayNumber.length());

        viewModel = new NumberSetHistoryViewModel(this, this);
        viewModel.getJackpotList(NUMBER_OF_DAYS_START);
    }

    @Override
    public void showMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showJackpotList(List<Jackpot> jackpotList) {
        edtDayNumber.setText(jackpotList.size() + "");
        edtDayNumber.setSelection(edtDayNumber.length());
        viewModel.getSpecialSetsHistory(jackpotList);
        btnView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                WidgetBase.hideKeyboard(NumberSetHistoryActivity.this);
                String dayNumberStr = edtDayNumber.getText().toString().trim();
                if (dayNumberStr.isEmpty()) {
                    showMessage("Bạn chưa nhập số ngày.");
                } else if (Integer.parseInt(dayNumberStr) > NUMBER_OF_DAYS_START) {
                    showMessage("Nằm ngoài phạm vi.");
                } else {
                    int minDays = Math.min(Integer.parseInt(dayNumberStr), jackpotList.size());
                    viewModel.getSpecialSetsHistory(jackpotList.subList(0, minDays));
                }

            }
        });
    }

    @Override
    public void showSpecialSetsHistory(List<NumberSetHistory> historyList) {
        List<String> headers = new ArrayList<>();
        List<RowData> rows = new ArrayList<>();

        for (NumberSetHistory history : historyList) {
            String name = history.getNumberSet().getName();
            String appearanceTimes = history.getAppearanceTimes() + " lần";
            String dayNumberBefore = history.getDayNumberBefore() + " ngày";
            String numberSize = history.getNumberSet().getNumbers().size() + " số";
            String beats = NumberBase.showNumbers(history.getBeatList(), ", ");
            List<String> cells = Arrays.asList(name, appearanceTimes, dayNumberBefore, numberSize, beats);
            RowData row = new RowData(cells);
            rows.add(row);
        }

        TableData tableData = new TableData(headers, rows);
        TableLayout tableLayout = TableLayoutBase.getTableLayout(this, tableData);
        hsTable.removeAllViews();
        hsTable.addView(tableLayout);
    }

    @Override
    public Context getContext() {
        return this;
    }
}
