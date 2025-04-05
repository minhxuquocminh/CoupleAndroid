package com.example.couple.View.JackpotStatistics;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.couple.Base.View.DialogBase;
import com.example.couple.Base.View.Table.TableData;
import com.example.couple.Base.View.Table.TableLayoutBase;
import com.example.couple.Base.View.WidgetBase;
import com.example.couple.Custom.Handler.Display.TableDataConverter;
import com.example.couple.Base.View.ActivityBase;
import com.example.couple.Model.Statistics.JackpotNextDay;
import com.example.couple.R;
import com.example.couple.View.UpdateDataInfo.AddJackpotManyYearsActivity;
import com.example.couple.ViewModel.JackpotStatistics.JackpotNextDayViewModel;

import java.util.List;

public class JackpotNextDayActivity extends ActivityBase implements JackpotNextDayView {
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
        String numberOfYearsStr = edtYearNumber.getText().toString().trim();
        viewModel.getJackpotNextDay(5, 0);

        edtYearNumber.setSelection(numberOfYearsStr.length());

        tvView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                WidgetBase.hideKeyboard(JackpotNextDayActivity.this);
                String yearNumber = edtYearNumber.getText().toString().trim();
                String dayNumberBefore = edtDayNumberBefore.getText().toString().trim();
                if (yearNumber.isEmpty()) {
                    showMessage("Vui lòng nhập số năm hiển thị!");
                } else if (dayNumberBefore.isEmpty()) {
                    showMessage("Vui lòng nhập số ngày trước đó!");
                } else {
                    viewModel.getJackpotNextDay(Integer.parseInt(yearNumber), Integer.parseInt(dayNumberBefore));
                }
            }
        });
    }

    @Override
    public void showMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showNumberOfYears(int numberOfYears) {
        edtYearNumber.setText(numberOfYears + "");
    }

    @Override
    public void showJackpotNextDay(List<JackpotNextDay> jackpotNextDayList) {
        TableData tableData = TableDataConverter.getJackpotNextDayTable(jackpotNextDayList);
        TableLayout tableLayout = TableLayoutBase.getTableLayout(this, tableData, true);
        linearJackpotNextDay.removeAllViews();
        linearJackpotNextDay.addView(tableLayout);
    }

    @Override
    public void showRequestLoadMoreData(int startYear_file, int endYear_file) {
        String title = "Cập nhật XS Đặc biệt?";
        String message = "Dữ liệu hiện có từ năm " + startYear_file + " đến năm " + endYear_file +
                ". Bạn cần cập nhật thêm dữ liệu XS Đặc biệt nhiều năm mới có thể xem thông tin" +
                " mà bạn đã yêu cầu. Bạn có muốn tiếp tục không?";
        DialogBase.showWithConfirmation(this, title, message, () -> {
            startActivity(new Intent(JackpotNextDayActivity.this,
                    AddJackpotManyYearsActivity.class));
        });
    }

    @Override
    public Context getContext() {
        return this;
    }
}
