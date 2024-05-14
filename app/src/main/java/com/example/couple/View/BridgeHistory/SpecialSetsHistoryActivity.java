package com.example.couple.View.BridgeHistory;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.HorizontalScrollView;
import android.widget.TableLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.couple.Base.Handler.NumberBase;
import com.example.couple.Base.View.TableLayoutBase;
import com.example.couple.Base.View.WidgetBase;
import com.example.couple.Custom.Const.TimeInfo;
import com.example.couple.Model.Display.SpecialSetHistory;
import com.example.couple.Model.Origin.Jackpot;
import com.example.couple.Model.UI.RowUI;
import com.example.couple.Model.UI.TableUI;
import com.example.couple.R;
import com.example.couple.ViewModel.JackpotStatistics.SpecialSetsHistoryViewModel;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SpecialSetsHistoryActivity extends AppCompatActivity implements SpecialSetsHistoryView {
    EditText edtDayNumber;
    Button btnView;
    HorizontalScrollView hsTable;

    SpecialSetsHistoryViewModel viewModel;

    private static final int NUMBER_OF_DAYS_START = TimeInfo.DAY_OF_YEAR;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_special_sets_history);

        edtDayNumber = findViewById(R.id.edtDayNumber);
        btnView = findViewById(R.id.btnView);
        hsTable = findViewById(R.id.hsTable);

        edtDayNumber.setText(NUMBER_OF_DAYS_START + "");
        edtDayNumber.setSelection(edtDayNumber.length());

        viewModel = new SpecialSetsHistoryViewModel(this, this);
        viewModel.getJackpotList(NUMBER_OF_DAYS_START);
    }

    @Override
    public void showMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showJackpotList(List<Jackpot> jackpotList) {
        viewModel.getSpecialSetsHistory(jackpotList);
        btnView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                WidgetBase.hideKeyboard(SpecialSetsHistoryActivity.this);
                String dayNumberStr = edtDayNumber.getText().toString().trim();
                if (dayNumberStr.isEmpty()) {
                    showMessage("Bạn chưa nhập số ngày.");
                } else if (Integer.parseInt(dayNumberStr) > NUMBER_OF_DAYS_START) {
                    showMessage("Nằm ngoài phạm vi.");
                } else {
                    viewModel.getSpecialSetsHistory(jackpotList
                            .subList(0, Integer.parseInt(dayNumberStr) - 1));
                }

            }
        });
    }

    @Override
    public void showSpecialSetsHistory(List<SpecialSetHistory> historyList) {
        List<String> headers = new ArrayList<>();
        List<RowUI> rows = new ArrayList<>();

        for (SpecialSetHistory history : historyList) {
            String name = history.getSpecialSet().getName();
            String appearanceTimes = history.getAppearanceTimes() + " lần";
            String dayNumberBefore = history.getDayNumberBefore() + " ngày";
            String numberSize = history.getSpecialSet().getNumbers().size() + " số";
            String beats = NumberBase.showNumbers(history.getBeatList(), ", ");
            List<String> cells = Arrays.asList(name, appearanceTimes, dayNumberBefore, numberSize, beats);
            RowUI row = new RowUI(cells);
            rows.add(row);
        }

        TableUI tableUI = new TableUI(headers, rows);
        TableLayout tableLayout = TableLayoutBase.getTableLayout(this, tableUI);
        hsTable.removeAllViews();
        hsTable.addView(tableLayout);
    }
}
