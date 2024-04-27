package com.example.couple.View.Lottery;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.couple.Base.View.WidgetBase;
import com.example.couple.Model.Origin.Lottery;
import com.example.couple.R;
import com.example.couple.View.Adapter.LotteryAdapter;
import com.example.couple.ViewModel.Lottery.LotteryViewModel;

import java.util.List;

public class LotteryActivity extends AppCompatActivity implements LotteryView {
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
        new AlertDialog.Builder(this)
                .setTitle("Cập nhật XSMB?")
                .setMessage("Database hiện có dữ liệu XSMB trong vòng " + maxDayNumber + " ngày," +
                        " bạn có muốn cập nhật thêm dữ liệu để xem không?")
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        lotteryViewModel.updateLottery(dayNumber);
                    }
                })
                .setNegativeButton(android.R.string.no, null)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }

    @Override
    public void updateLotterySuccess(String message, int numberOfDays) {
        lotteryViewModel.getLotteryList(numberOfDays);
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }


}
