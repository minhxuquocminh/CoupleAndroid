package com.example.couple.View.BridgeHistory;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.HorizontalScrollView;
import android.widget.TableLayout;
import android.widget.Toast;

import com.example.couple.Base.Handler.CoupleBase;
import com.example.couple.Base.View.Table.RowData;
import com.example.couple.Base.View.Table.TableData;
import com.example.couple.Base.View.Table.TableLayoutBase;
import com.example.couple.Base.View.WidgetBase;
import com.example.couple.Custom.Const.TimeInfo;
import com.example.couple.Base.View.ActivityBase;
import com.example.couple.Model.DateTime.Date.Cycle.Branch;
import com.example.couple.Model.DateTime.Date.Cycle.Cycle;
import com.example.couple.Model.DateTime.Date.Cycle.YearCycle;
import com.example.couple.Model.DateTime.Date.DateData;
import com.example.couple.Model.Origin.Jackpot;
import com.example.couple.R;
import com.example.couple.ViewModel.BridgeHistory.SexagenaryCycleViewModel;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SexagenaryCycleActivity extends ActivityBase implements SexagenaryCycleView {
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
    public void showSexagenaryCycle(List<DateData> dateDataList, List<Jackpot> jackpotList) {
        int index = -1; // timebase index
        for (Jackpot jackpot : jackpotList) {
            for (int i = 0; i < dateDataList.size(); i++) {
                if (dateDataList.get(i).getDateBase().equals(jackpot.getDateBase())) {
                    index = i;
                    break;
                }
            }
            if (index != -1) break;
        }
        TableData tableData = new TableData();
        if (index < 1) {
//            headers = Arrays.asList("Ngày dương", "Ngày âm", "Can chi", "Can", "Chi",
//                    "Can hợp", "Can khắc", "Chi hợp", "Chi khắc", "Năm hợp", "Năm khắc");
            for (DateData dateData : dateDataList) {
                RowData row = new RowData();
                row.addCell(dateData.getDateBase().showDDMM("-"));
                row.addCell(dateData.getDateLunar().showDDMM("-"));
                row.addCell(dateData.getDateCycle().show());
                Cycle dayCycle = dateData.getDateCycle().getDay();
                row.addCell(dayCycle.getStem().getPosition() + "");
                row.addCell(dayCycle.getBranch().getPosition() % 10 + "");
                tableData.addRow(row);
            }
        } else {
            int count = 0;
            List<Jackpot> jackpots = new ArrayList<>();
            jackpots.add(Jackpot.getEmpty());
            jackpots.addAll(jackpotList);
//            headers = Arrays.asList("Ngày dương", "Ngày âm", "Can chi", "Can", "Chi",
//                    "Can hợp", "Can khắc", "Chi hợp", "Chi khắc", "Năm hợp khắc", "Năm hợp", "Năm khắc");
            for (int i = index - 1; i < dateDataList.size(); i++) {
                RowData row = new RowData();
                row.addCell(dateDataList.get(i).getDateBase().showDDMM("-"));
                row.addCell(dateDataList.get(i).getDateLunar().showDDMM("-"));
                row.addCell(dateDataList.get(i).getDateCycle().show());
                Branch branch = dateDataList.get(i).getDateCycle().getDay().getBranch();
                row.addCell(dateDataList.get(i).getDateCycle().getDay().getStem().getPosition() + "");
                row.addCell(branch.getPosition() % 10 + "");
                Jackpot jackpot = jackpots.get(count);
                List<Branch> branchList = count == 0 ? Collections.singletonList(branch) :
                        Branch.getBranchsByYear(jackpot.getCoupleInt(), TimeInfo.CURRENT_YEAR);

                row.addCell(!branchList.isEmpty() ? branchList.get(0).show() : "");
                row.addCell(branchList.size() > 1 ? branchList.get(1).show() : "");
                int distance = branch.getDistance(branchList.get(0));
                row.addCell(count == 0 ? "" : (distance > 0 ? "+" + distance : distance + ""));
                row.addCell(count == 0 ? "?????" : (jackpot.isDayOff() ? "Nghỉ" : jackpot.getJackpot()));
                row.addCell(count == 0 ? "" : branch.getStatus(jackpot.getCoupleInt(), TimeInfo.CURRENT_YEAR));
                List<YearCycle> yearBranch = branch.getYearCycles(TimeInfo.CURRENT_YEAR);
                String yearBranchStr = "";
                for (YearCycle cycle : yearBranch) {
                    yearBranchStr += CoupleBase.showCouple(cycle.getCoupleInt()) + " ";
                }
                row.addCell(yearBranchStr);
                tableData.addRow(row);
                count++;
                if (count == jackpots.size()) break;
            }
        }

        TableLayout tableLayout = TableLayoutBase.getTableLayout(this, tableData, false);
        hsTable.removeAllViews();
        hsTable.addView(tableLayout);
    }

    @Override
    public Context getContext() {
        return this;
    }

}
