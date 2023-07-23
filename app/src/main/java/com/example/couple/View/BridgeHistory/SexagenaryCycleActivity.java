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
import com.example.couple.Custom.Handler.CoupleHandler;
import com.example.couple.Model.Origin.Jackpot;
import com.example.couple.Model.Time.Cycle.Cycle;
import com.example.couple.Model.Time.Cycle.YearCycle;
import com.example.couple.Model.Time.TimeBase;
import com.example.couple.Model.UI.Row;
import com.example.couple.Model.UI.TableByRow;
import com.example.couple.R;
import com.example.couple.ViewModel.BridgeHistory.SexagenaryCycleViewModel;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SexagenaryCycleActivity extends AppCompatActivity implements SexagenaryCycleView {
    EditText edtDayNumber;
    Button btnView;
    HorizontalScrollView hsTable;

    SexagenaryCycleViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sexagenary_cycle);

        edtDayNumber = findViewById(R.id.edtDayNumber);
        btnView = findViewById(R.id.btnView);
        hsTable = findViewById(R.id.hsTable);

        edtDayNumber.setText("18");
        edtDayNumber.setSelection(edtDayNumber.length());

        viewModel = new SexagenaryCycleViewModel(this, this);
        viewModel.GetSexagenaryCycle(18);

        btnView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                WidgetBase.hideKeyboard(SexagenaryCycleActivity.this);
                String dayNumberStr = edtDayNumber.getText().toString().trim();
                if (dayNumberStr.equals("")) {
                    ShowError("Bạn chưa nhập số ngày.");
                    return;
                }
                viewModel.GetSexagenaryCycle(Integer.parseInt(dayNumberStr));
            }
        });
    }

    @Override
    public void ShowError(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void ShowUpdateSuccess() {
        Toast.makeText(this, "Cập nhật thông tin can chi thành công.", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void ShowSexagenaryCycle(List<TimeBase> cycleList, List<Jackpot> jackpotList) {
        List<String> headers = new ArrayList<>();
        List<Row> rows = new ArrayList<>();
        int index = -1;
        for (TimeBase timeBase : cycleList) {
            for (int i = 0; i < jackpotList.size(); i++) {
                if (timeBase.getDateBase().equals(jackpotList.get(i).getDateBase())) {
                    index = i;
                    break;
                }
            }
            if (index != -1) break;
        }
        if (index < 0) {
//            headers = Arrays.asList("Ngày dương", "Ngày âm", "Can chi", "Can", "Chi",
//                    "Can hợp", "Can khắc", "Chi hợp", "Chi khắc", "Năm hợp", "Năm khắc");
            for (TimeBase timeBase : cycleList) {
                String dateBase = timeBase.getDateBase().showDDMM("-");
                String dateLunar = timeBase.getDateLunar().showDDMM("-");
                String dateCycle = timeBase.getDateCycle().show();
                Cycle dayCycle = timeBase.getDateCycle().getDay();
                String stems = dayCycle.getStems().getPosition() + "";
                String branches = dayCycle.getBranches().getPosition() % 10 + "";
                List<YearCycle> comYear = dayCycle
                        .getCompatibleYearCyclesByBranches(TimeInfo.CYCLE_START_YEAR, TimeInfo.CURRENT_YEAR);
                String comCycle = "(" + comYear.size() + " số)";
                for (YearCycle yearCycle : comYear) {
                    comCycle += yearCycle.showByCouple() + " ";
                }
                List<YearCycle> incomYear = dayCycle
                        .getIncompatibleYearCyclesByBranches(TimeInfo.CYCLE_START_YEAR, TimeInfo.CURRENT_YEAR);
                String incomCycle = "(" + incomYear.size() + " số)";
                for (YearCycle yearCycle : incomYear) {
                    incomCycle += yearCycle.showByCouple() + " ";
                }
                List<String> cells = Arrays.asList(dateBase,
                        dateLunar, dateCycle, stems, branches, comCycle, incomCycle);
                Row row = new Row(cells);
                rows.add(row);
            }
        } else {
            int count = index;
            List<Jackpot> jackpots = new ArrayList<>();
            jackpots.add(Jackpot.getEmpty());
            jackpots.addAll(jackpotList);
//            headers = Arrays.asList("Ngày dương", "Ngày âm", "Can chi", "Can", "Chi",
//                    "Can hợp", "Can khắc", "Chi hợp", "Chi khắc", "Năm hợp khắc", "Năm hợp", "Năm khắc");
            for (TimeBase timeBase : cycleList) {
                String dateBase = timeBase.getDateBase().showDDMM("-");
                String dateLunar = timeBase.getDateLunar().showDDMM("-");
                String dateCycle = timeBase.getDateCycle().show();
                Cycle dayCycle = timeBase.getDateCycle().getDay();
                String stems = dayCycle.getStems().getPosition() + "";
                String branches = dayCycle.getBranches().getPosition() % 10 + "";
                List<Cycle> cycles = count == 0 ? Arrays.asList(dayCycle) :
                        Cycle.getCycleList(jackpots.get(count).getCouple(),
                                TimeInfo.CYCLE_START_YEAR, TimeInfo.CURRENT_YEAR);
                String coupleBranches1 = "";
                String coupleBranches2 = "";
                if (!cycles.isEmpty()) coupleBranches1 = cycles.get(0).getBranches().show();
                if (cycles.size() > 1) coupleBranches2 = cycles.get(1).getBranches().show();
                String jackpot = count == 0 ? "?????" : jackpots.get(count).getJackpot();
                String status = "";
                List<YearCycle> comYear = dayCycle
                        .getCompatibleYearCyclesByBranches(TimeInfo.CYCLE_START_YEAR, TimeInfo.CURRENT_YEAR);
                String comCycle = "(" + comYear.size() + " số)";
                for (YearCycle yearCycle : comYear) {
                    comCycle += yearCycle.getCouple() + " ";
                    if (yearCycle.getYear() % 100 == jackpots.get(count).getCoupleInt()) {
                        status += "H";
                    }
                }
                List<YearCycle> incomYear = dayCycle
                        .getIncompatibleYearCyclesByBranches(TimeInfo.CYCLE_START_YEAR, TimeInfo.CURRENT_YEAR);
                String incomCycle = "(" + incomYear.size() + " số)";
                for (YearCycle yearCycle : incomYear) {
                    incomCycle += yearCycle.getCouple() + " ";
                    if (yearCycle.getYear() % 100 == jackpots.get(count).getCoupleInt()) {
                        status += "K";
                    }
                }
                if (count == 0) status = "";
                List<Integer> matchList = new ArrayList<>();
                for (YearCycle com : comYear) {
                    for (YearCycle incom : incomYear) {
                        if (com.getCouple().equals(incom.getCouple())) {
                            matchList.add(Integer.parseInt(com.getCouple()));
                        }
                    }
                }
                List<Integer> matchs = NumberBase.filterDuplicatedNumbers(matchList);
                List<Integer> matchs2 = NumberBase.getDuplicatedNumbers(matchList);
                String match = "(" + matchs.size() + " số) " + CoupleHandler.showCoupleNumbers(matchs);
                String match2 = "(" + matchs2.size() + " số) " + CoupleHandler.showCoupleNumbers(matchs2);
                List<String> cells = Arrays.asList(dateBase, dateLunar, dateCycle, stems, branches,
                        coupleBranches1, coupleBranches2, jackpot, status, match, match2, comCycle, incomCycle);
                Row row = new Row(cells);
                rows.add(row);
                count++;
            }
        }

        TableByRow tableByRow = new TableByRow(headers, rows);
        TableLayout tableLayout = TableLayoutBase.getTableLayout(this, tableByRow);
        hsTable.removeAllViews();
        hsTable.addView(tableLayout);
    }
}
