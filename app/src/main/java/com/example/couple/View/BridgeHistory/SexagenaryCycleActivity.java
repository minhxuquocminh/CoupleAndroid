package com.example.couple.View.BridgeHistory;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.HorizontalScrollView;
import android.widget.TableLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.couple.Base.Handler.CoupleBase;
import com.example.couple.Base.View.TableLayoutBase;
import com.example.couple.Base.View.WidgetBase;
import com.example.couple.Custom.Const.TimeInfo;
import com.example.couple.Model.Origin.Jackpot;
import com.example.couple.Model.Time.Cycle.Branch;
import com.example.couple.Model.Time.Cycle.Cycle;
import com.example.couple.Model.Time.Cycle.YearCycle;
import com.example.couple.Model.Time.TimeBase;
import com.example.couple.Model.UI.RowUI;
import com.example.couple.Model.UI.TableUI;
import com.example.couple.R;
import com.example.couple.ViewModel.BridgeHistory.SexagenaryCycleViewModel;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
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
        viewModel.getSexagenaryCycle(18);

        btnView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                WidgetBase.hideKeyboard(SexagenaryCycleActivity.this);
                String dayNumberStr = edtDayNumber.getText().toString().trim();
                if (dayNumberStr.isEmpty()) {
                    showMessage("Bạn chưa nhập số ngày.");
                    return;
                }
                viewModel.getSexagenaryCycle(Integer.parseInt(dayNumberStr));
            }
        });
    }

    @Override
    public void showMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void updateSuccess() {
        Toast.makeText(this, "Cập nhật thông tin can chi thành công.", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showSexagenaryCycle(List<TimeBase> cycleList, List<Jackpot> allJackpotList) {
        List<String> headers = new ArrayList<>();
        List<RowUI> rows = new ArrayList<>();
        int index = -1;
        for (TimeBase timeBase : cycleList) {
            for (int i = 0; i < allJackpotList.size(); i++) {
                if (timeBase.getDateBase().equals(allJackpotList.get(i).getDateBase())) {
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
                String stems = dayCycle.getStem().getPosition() + "";
                String branches = dayCycle.getBranch().getPosition() % 10 + "";
                List<String> cells = Arrays.asList(dateBase, dateLunar, dateCycle, stems, branches);
                RowUI row = new RowUI(cells);
                rows.add(row);
            }
        } else {
            int count = index;
            List<Jackpot> jackpots = new ArrayList<>();
            jackpots.add(Jackpot.getEmpty());
            jackpots.addAll(allJackpotList);
//            headers = Arrays.asList("Ngày dương", "Ngày âm", "Can chi", "Can", "Chi",
//                    "Can hợp", "Can khắc", "Chi hợp", "Chi khắc", "Năm hợp khắc", "Năm hợp", "Năm khắc");
            for (TimeBase timeBase : cycleList) {
                String dateBase = timeBase.getDateBase().showDDMM("-");
                String dateLunar = timeBase.getDateLunar().showDDMM("-");
                String dateCycle = timeBase.getDateCycle().show();
                Branch branch = timeBase.getDateCycle().getDay().getBranch();
                String stem = timeBase.getDateCycle().getDay().getStem().getPosition() + "";
                String branches = branch.getPosition() % 10 + "";
                List<Branch> branchList = count == 0 ? Collections.singletonList(branch) :
                        Branch.getBranchsByYear(jackpots.get(count).getCoupleInt(), TimeInfo.CURRENT_YEAR);
                String coupleBranches1 = "";
                String coupleBranches2 = "";
                if (!branchList.isEmpty()) coupleBranches1 = branchList.get(0).show();
                if (branchList.size() > 1) coupleBranches2 = branchList.get(1).show();
                int distance = branch.getDistance(branchList.get(0));
                String distanceStr = count == 0 ? "" : (distance > 0 ? "+" + distance : distance + "");
                String jackpot = count == 0 ? "?????" : jackpots.get(count).getJackpot();

                String status = count == 0 ? "" :
                        branch.getStatus(jackpots.get(count).getCoupleInt(), TimeInfo.CURRENT_YEAR);
                List<YearCycle> yearBranch = branch.getYearCycles(TimeInfo.CURRENT_YEAR);
                String yearBranchStr = "";
                for (YearCycle cycle : yearBranch) {
                    yearBranchStr += CoupleBase.showCouple(cycle.getCoupleInt()) + " ";
                }
                List<String> cells = Arrays.asList(dateBase, dateLunar, dateCycle, stem, branches,
                        coupleBranches1, coupleBranches2, distanceStr, jackpot, status, yearBranchStr);
                RowUI row = new RowUI(cells);
                rows.add(row);
                count++;
            }
        }

        TableUI tableUI = new TableUI(headers, rows);
        TableLayout tableLayout = TableLayoutBase.getTableLayout(this, tableUI);
        hsTable.removeAllViews();
        hsTable.addView(tableLayout);
    }
}
