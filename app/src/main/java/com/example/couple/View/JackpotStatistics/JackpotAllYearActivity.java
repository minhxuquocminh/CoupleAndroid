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
import com.example.couple.Base.View.Table.TableLayoutBase;
import com.example.couple.Base.View.WidgetBase;
import com.example.couple.Base.View.ActivityBase;
import com.example.couple.R;
import com.example.couple.View.UpdateDataInfo.AddJackpotManyYearsActivity;
import com.example.couple.ViewModel.JackpotStatistics.JackpotAllYearViewModel;

public class JackpotAllYearActivity extends ActivityBase implements JackpotAllYearView {
    EditText edtYearNumberBalanceCouple;
    EditText edtYearNumberSDB;
    TextView tvView;
    LinearLayout linearBalanceCouple;
    LinearLayout linearSDB;

    JackpotAllYearViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jackpot_all_year);

        edtYearNumberBalanceCouple = findViewById(R.id.edtYearNumberBalanceCouple);
        edtYearNumberSDB = findViewById(R.id.edtYearNumberSDB);
        tvView = findViewById(R.id.tvView);
        linearBalanceCouple = findViewById(R.id.linearBalanceCouple);
        linearSDB = findViewById(R.id.linearSDB);

        viewModel = new JackpotAllYearViewModel(this, this);

        viewModel.getAllStatistics(5, 5);

        tvView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                WidgetBase.hideKeyboard(JackpotAllYearActivity.this);
                String balanceYears = edtYearNumberBalanceCouple.getText().toString().trim();
                String doubleYears = edtYearNumberSDB.getText().toString().trim();
                if (doubleYears.isEmpty()) {
                    showMessage("Vui lòng nhập số năm.");
                } else {
                    viewModel.getAllStatistics(5, Integer.parseInt(doubleYears));
                }
            }
        });

    }

    @Override
    public void showMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showSameDoubleCountingManyYears(String[][] matrix, int row, int col) {
        TableLayout tableLayout = TableLayoutBase.getTableLayout(this, matrix, row, col, true);
        linearSDB.removeAllViews();
        linearSDB.addView(tableLayout);
    }

    @Override
    public void showRequestLoadMoreData(int startYear_file, int endYear_file) {
        String title = "Cập nhật XS Đặc biệt?";
        String message = "Dữ liệu hiện có từ năm " + startYear_file + " đến năm " + endYear_file +
                ". Bạn cần cập nhật thêm dữ liệu XS Đặc biệt nhiều năm mới có thể xem thông tin" +
                " mà bạn đã yêu cầu. Bạn có muốn tiếp tục không?";
        DialogBase.showWithConfirmation(this, title, message, () -> {
            startActivity(new Intent(JackpotAllYearActivity.this,
                    AddJackpotManyYearsActivity.class));
        });
    }

    @Override
    public Context getContext() {
        return this;
    }
}
