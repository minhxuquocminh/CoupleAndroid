package com.example.couple.View.Couple;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.couple.Base.View.WidgetBase;
import com.example.couple.Custom.Widget.CustomTableLayout;
import com.example.couple.Model.Origin.Jackpot;
import com.example.couple.R;
import com.example.couple.ViewModel.Couple.CoupleByWeekViewModel;

import java.util.List;

public class CoupleByWeekActivity extends AppCompatActivity implements CoupleByWeekView {
    EditText edtWeekNumber;
    TextView tvGetData;
    LinearLayout linearCoupleByWeek;

    CoupleByWeekViewModel viewModel;

    private static final int WEEK_NUMBER_START = 4;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_couple_by_week);

        edtWeekNumber = findViewById(R.id.edtWeekNumber);
        tvGetData = findViewById(R.id.tvGetData);
        linearCoupleByWeek = findViewById(R.id.linearCoupleByWeek);

        viewModel = new CoupleByWeekViewModel(this, this);
        edtWeekNumber.setText(WEEK_NUMBER_START + "");
        edtWeekNumber.setSelection(edtWeekNumber.getText().length());
        viewModel.getJackpotByWeek(WEEK_NUMBER_START);
        tvGetData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                WidgetBase.hideKeyboard(CoupleByWeekActivity.this);
                String dayNumber = edtWeekNumber.getText().toString().trim();
                if (dayNumber.isEmpty()) {
                    showMessage("Bạn chưa nhập số tuần.");
                } else {
                    viewModel.getJackpotByWeek(Integer.parseInt(dayNumber));
                }
            }
        });
    }

    @Override
    public void showMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showJackpotByWeek(List<Jackpot> jackpotList, int weekNumber) {
        TableLayout tableLayout = CustomTableLayout.getCoupleByWeekTableLayout(this,
                jackpotList, weekNumber);
        linearCoupleByWeek.removeAllViews();
        linearCoupleByWeek.addView(tableLayout);
    }
}
