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
import com.example.couple.R;
import com.example.couple.View.Main.Personal.AddJackpotManyYearsActivity;
import com.example.couple.ViewModel.JackpotStatistics.JackpotAllYearViewModel;

public class JackpotAllYearActivity extends AppCompatActivity implements JackpotAllYearView {
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

        viewModel.GetAllStatistics("", "");

        tvView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                WidgetBase.hideKeyboard(JackpotAllYearActivity.this);
                String yearNumberBalanceCouple = edtYearNumberBalanceCouple.getText().toString().trim();
                String yearNumberDoubleSame = edtYearNumberSDB.getText().toString().trim();
                viewModel.GetAllStatistics(yearNumberBalanceCouple, yearNumberDoubleSame);
            }
        });

    }

    @Override
    public void ShowError(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void ShowSameDoubleCountingManyYears(int[][] matrixSDB, int m, int n, int startYear, int[] dayNumberArr) {
        TableLayout tableLayout = CustomTableLayout.getCountSameDoubleTableLayout(this,
                matrixSDB, m, n, startYear, dayNumberArr);
        linearSDB.removeAllViews();
        linearSDB.addView(tableLayout);
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
                        startActivity(new Intent(JackpotAllYearActivity.this,
                                AddJackpotManyYearsActivity.class));
                    }
                })
                .setNegativeButton(android.R.string.no, null)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }
}
