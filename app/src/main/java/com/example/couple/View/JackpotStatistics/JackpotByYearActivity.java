package com.example.couple.View.JackpotStatistics;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.HorizontalScrollView;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.couple.Custom.Widget.CustomTableLayout;
import com.example.couple.Custom.Const.TimeInfo;
import com.example.couple.Base.Handler.IOFileBase;
import com.example.couple.Base.View.WidgetBase;
import com.example.couple.Custom.Handler.JackpotHandler;
import com.example.couple.Base.Handler.DateBase;
import com.example.couple.R;
import com.example.couple.ViewModel.JackpotStatistics.JackpotByYearViewModel;


public class JackpotByYearActivity extends AppCompatActivity implements JackpotByYearView {
    EditText edtYear;
    TextView tvGetData;
    HorizontalScrollView hsTable;
    TextView tvNote;

    JackpotByYearViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jackpot_by_year);

        edtYear = findViewById(R.id.edtYear);
        tvGetData = findViewById(R.id.tvGetData);
        hsTable = findViewById(R.id.hsTable);
        tvNote = findViewById(R.id.tvNote);

        viewModel = new JackpotByYearViewModel(this, this);

        edtYear.setText(TimeInfo.CURRENT_YEAR + "");
        edtYear.setSelection(edtYear.getText().length());
        tvNote.setVisibility(View.GONE);

        viewModel.GetTableOfJackpot(TimeInfo.CURRENT_YEAR + "");

        tvGetData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                WidgetBase.hideKeyboard(JackpotByYearActivity.this);
                String year = edtYear.getText().toString().trim();
                viewModel.GetTableOfJackpot(year);
            }
        });

    }

    @Override
    public void ShowError(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void ShowTableOfJackpot(String[][] matrix, int year) {
        DateBase lastDate = JackpotHandler.getLastDate(this);
        DateBase selectedDate = lastDate.plusDays(1);
        int row = selectedDate.getDay() - 1;
        int col = selectedDate.getMonth() - 1;
        String selected = IOFileBase.readDataFromFile(this, "selected.txt");
        matrix[row][col] = selected.equals("") ? "" : "123" + selected;
        tvNote.setVisibility(View.VISIBLE);
        TableLayout tableLayout = CustomTableLayout.getJackpotMatrixByYear(this, matrix,
                31, 12, year);
        hsTable.removeAllViews();
        hsTable.addView(tableLayout);

    }

}
