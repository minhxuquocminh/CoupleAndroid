package com.example.couple.View.Lottery;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.couple.Base.View.DialogBase;
import com.example.couple.Base.View.WidgetBase;
import com.example.couple.Custom.Widget.SpeechToTextActivity;
import com.example.couple.Model.Origin.Lottery;
import com.example.couple.R;
import com.example.couple.View.Adapter.LotteryAdapter;
import com.example.couple.ViewModel.Lottery.LotteryViewModel;

import java.util.List;

public class LotteryActivity extends SpeechToTextActivity implements LotteryView {
    TextView tvGetData;
    EditText edtDayNumber;
    RecyclerView rvTableOfLottery;

    LotteryViewModel lotteryViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lottery);

        edtDayNumber = findViewById(R.id.edtDayNumber);
        tvGetData = findViewById(R.id.tvGetData);
        rvTableOfLottery = findViewById(R.id.rvTableOfLottery);

        lotteryViewModel = new LotteryViewModel(this, this);

        String startDayNumber = 20 + "";
        edtDayNumber.setText(startDayNumber);
        edtDayNumber.setSelection(edtDayNumber.length());
        lotteryViewModel.getLotteryList(Integer.parseInt(startDayNumber));

        tvGetData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                WidgetBase.hideKeyboard(LotteryActivity.this);
                String numberOfDaysStr = edtDayNumber.getText().toString().trim();
                if (numberOfDaysStr.isEmpty()) {
                    showMessage("Bạn chưa nhập số ngày để lấy dữ liệu!");
                } else {
                    lotteryViewModel.getLotteryList(Integer.parseInt(numberOfDaysStr));
                }
            }
        });
    }

    @Override
    public void showMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showLotteryList(List<Lottery> lotteries) {
        LotteryAdapter adapter = new LotteryAdapter(this,
                R.layout.custom_item_rv_lottery, lotteries);
        rvTableOfLottery.removeAllViews();
        rvTableOfLottery.setLayoutManager(new LinearLayoutManager(this));
        rvTableOfLottery.setAdapter(adapter);
    }

    @Override
    public void showRequestToUpdateLottery(int maxDayNumber, int dayNumber) {
        String title = "Cập nhật XSMB?";
        String message = "Database hiện có dữ liệu XSMB trong vòng " + maxDayNumber + " ngày," +
                " bạn có muốn cập nhật thêm dữ liệu để xem không?";
        DialogBase.showWithConfirmation(this, title, message, () -> {
            lotteryViewModel.updateLottery(dayNumber);
        });
    }

    @Override
    public void updateLotterySuccess(String message, int numberOfDays) {
        lotteryViewModel.getLotteryList(numberOfDays);
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }


    @Override
    public Context getContext() {
        return this;
    }
}
