package com.example.couple.View.JackpotStatistics;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.HorizontalScrollView;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.couple.Base.Handler.DateBase;
import com.example.couple.Base.Handler.IOFileBase;
import com.example.couple.Base.View.WidgetBase;
import com.example.couple.Custom.Const.Const;
import com.example.couple.Custom.Const.TimeInfo;
import com.example.couple.Custom.Handler.JackpotHandler;
import com.example.couple.Custom.Widget.CustomTableLayout;
import com.example.couple.R;
import com.example.couple.ViewModel.JackpotStatistics.JackpotByYearViewModel;

import java.util.ArrayList;
import java.util.List;


public class JackpotByYearActivity extends AppCompatActivity implements JackpotByYearView {
    Spinner spnYear;
    TextView tvGetData;
    HorizontalScrollView hsTable;
    TextView tvNote;

    JackpotByYearViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jackpot_by_year);

        spnYear = findViewById(R.id.spnYear);
        tvGetData = findViewById(R.id.tvGetData);
        hsTable = findViewById(R.id.hsTable);
        tvNote = findViewById(R.id.tvNote);

        tvNote.setVisibility(View.GONE);

        viewModel = new JackpotByYearViewModel(this, this);
        viewModel.GetYearList();

        tvGetData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                WidgetBase.hideKeyboard(JackpotByYearActivity.this);
                String year = spnYear.getSelectedItem().toString().split(" ")[1].trim();
                viewModel.GetTableOfJackpot(Integer.parseInt(year));
            }
        });

    }

    @Override
    public void ShowError(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void ShowYearList(List<Integer> yearList) {
        List<String> yearStrList = new ArrayList<>();
        for (int year : yearList) {
            yearStrList.add("Năm " + year);
        }
        ArrayAdapter adapter = new ArrayAdapter(this,
                R.layout.custom_item_spn_year, R.id.tvYear, yearStrList);
        spnYear.setAdapter(adapter);
        viewModel.GetTableOfJackpot(TimeInfo.CURRENT_YEAR);
    }

    @Override
    public void ShowTableOfJackpot(String[][] matrix, int year) {
        DateBase lastDate = JackpotHandler.getLastDate(this);
        DateBase selectedDate = lastDate.plusDays(1);
        int row = selectedDate.getDay() - 1;
        int col = selectedDate.getMonth() - 1;
        String selected = IOFileBase.readDataFromFile(this, Const.SELECTED_NUMBER_FILE_NAME);
        if (year == TimeInfo.CURRENT_YEAR) {
            matrix[row][col] = selected.equals("") ? "" : "888" + selected;
        }
        tvNote.setVisibility(View.VISIBLE);
        TableLayout tableLayout = CustomTableLayout.getJackpotMatrixByYear(this, matrix,
                31, 12, year);
        hsTable.removeAllViews();
        hsTable.addView(tableLayout);

    }

}
