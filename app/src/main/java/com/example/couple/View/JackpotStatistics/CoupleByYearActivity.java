package com.example.couple.View.JackpotStatistics;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.couple.Base.View.DialogBase;
import com.example.couple.Base.View.WidgetBase;
import com.example.couple.Custom.Widget.CustomTableLayout;
import com.example.couple.R;
import com.example.couple.View.UpdateDataInfo.AddJackpotManyYearsActivity;
import com.example.couple.ViewModel.JackpotStatistics.CoupleByYearViewModel;

public class CoupleByYearActivity extends AppCompatActivity implements CoupleByYearView {
    EditText edtNumberOfYears;
    EditText edtTens;
    EditText edtUnit;
    TextView tvFilter;
    CheckBox cboNearestYear;
    CheckBox cboSumOfYears;
    TextView tvLabel;
    LinearLayout linearFreqCouple;

    CoupleByYearViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_couple_by_year);

        edtNumberOfYears = findViewById(R.id.edtNumberOfYears);
        edtTens = findViewById(R.id.edtTens);
        edtUnit = findViewById(R.id.edtUnit);
        tvFilter = findViewById(R.id.tvFilter);
        cboNearestYear = findViewById(R.id.cboNearestYear);
        cboSumOfYears = findViewById(R.id.cboSumOfYears);
        tvLabel = findViewById(R.id.tvLabel);
        linearFreqCouple = findViewById(R.id.linearFreqCouple);

        viewModel = new CoupleByYearViewModel(this, this);
        viewModel.getCoupleCountingTable("", "", "", 0);

        tvFilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                WidgetBase.hideKeyboard(CoupleByYearActivity.this);
                String numberOfYears = edtNumberOfYears.getText().toString().trim();
                String tens = edtTens.getText().toString().trim();
                String unit = edtUnit.getText().toString().trim();
                boolean cbo1_isChecked = cboNearestYear.isChecked();
                boolean cbo2_isChecked = cboSumOfYears.isChecked();
                int status;
                if (cbo1_isChecked) {
                    status = 1;
                } else if (cbo2_isChecked) {
                    status = 2;
                } else {
                    status = 0;
                }
                viewModel.getCoupleCountingTable(numberOfYears, tens, unit, status);
            }
        });

        cboNearestYear.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    cboSumOfYears.setChecked(false);
                }
            }
        });

        cboSumOfYears.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    cboNearestYear.setChecked(false);
                }
            }
        });
    }

    @Override
    public void showMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showCoupleCountingTable(int[][] matrix, int m, int n, int startYear) {
        TableLayout tableLayout =
                CustomTableLayout.getCountCoupleTableLayout(this, matrix, m, n, startYear);
        linearFreqCouple.removeAllViews();
        linearFreqCouple.addView(tableLayout);
    }

    @Override
    public void showRequestLoadMoreData(int startYear_file, int endYear_file) {
        String title = "Cập nhật XS Đặc biệt?";
        String message = "Dữ liệu hiện có từ năm " + startYear_file + " đến năm " + endYear_file +
                ". Bạn cần cập nhật thêm dữ liệu XS Đặc biệt nhiều năm mới có thể xem thông tin" +
                " mà bạn đã yêu cầu. Bạn có muốn tiếp tục không?";
        DialogBase.showWithConfirmation(this, title, message, () -> {
            startActivity(new Intent(CoupleByYearActivity.this,
                    AddJackpotManyYearsActivity.class));
        });
    }

}
