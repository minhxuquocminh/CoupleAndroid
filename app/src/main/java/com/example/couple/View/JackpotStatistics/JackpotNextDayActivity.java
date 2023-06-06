package com.example.couple.View.JackpotStatistics;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.couple.Base.View.WidgetBase;
import com.example.couple.Custom.Widget.CustomTableLayout;
import com.example.couple.Model.Display.JackpotNextDay;
import com.example.couple.R;
import com.example.couple.View.Main.Personal.AddJackpotManyYearsActivity;
import com.example.couple.ViewModel.JackpotStatistics.JackpotNextDayViewModel;

import java.util.List;

public class JackpotNextDayActivity extends AppCompatActivity implements JackpotNextDayView {
    EditText edtYearNumber;
    EditText edtDayNumberBefore;
    TextView tvView;
    LinearLayout linearJackpotNextDay;

    JackpotNextDayViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jackpot_next_day);

        edtYearNumber = findViewById(R.id.edtYearNumber);
        edtDayNumberBefore = findViewById(R.id.edtDayNumberBefore);
        tvView = findViewById(R.id.tvView);
        linearJackpotNextDay = findViewById(R.id.linearJackpotNextDay);

        viewModel = new JackpotNextDayViewModel(this, this);
        viewModel.GetNumberOfYears();
        String numberOfYearsStr = edtYearNumber.getText().toString().trim();
        viewModel.GetJackpotNextDay(numberOfYearsStr, 0 + "");

        edtYearNumber.setSelection(numberOfYearsStr.length());

        tvView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                WidgetBase.hideKeyboard(JackpotNextDayActivity.this);
                String yearNumber = edtYearNumber.getText().toString().trim();
                String dayNumberBefore = edtDayNumberBefore.getText().toString().trim();
                viewModel.GetJackpotNextDay(yearNumber, dayNumberBefore);
            }
        });
    }

    @Override
    public void ShowError(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void ShowNumberOfYears(int numberOfYears) {
        edtYearNumber.setText(numberOfYears + "");
    }

    @Override
    public void ShowJackpotNextDay(List<JackpotNextDay> jackpotNextDayList) {
        TableLayout tableLayout = CustomTableLayout.getJackpotNextDayTableLayout(this, jackpotNextDayList);
        linearJackpotNextDay.removeAllViews();
        linearJackpotNextDay.addView(tableLayout);
    }

    @Override
    public void ShowRequestLoadMoreData(int startYear_file, int endYear_file) {
        new AlertDialog.Builder(this)
                .setTitle("Cập nhật XS Đặc biệt?")
                .setMessage("Dữ liệu hiện có từ năm " + startYear_file + " đến năm " + endYear_file +
                        ". Bạn cần cập nhật thêm dữ liệu XS Đặc biệt nhiều năm mới có thể xem thông tin" +
                        " mà bạn đã yêu cầu. Bạn có muốn tiếp tục không?")
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        startActivity(new Intent(JackpotNextDayActivity.this,
                                AddJackpotManyYearsActivity.class));
                    }
                })
                .setNegativeButton(android.R.string.no, null)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }
}
