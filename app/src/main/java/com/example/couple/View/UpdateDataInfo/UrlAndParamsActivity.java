package com.example.couple.View.UpdateDataInfo;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.couple.Base.View.ActivityBase;
import com.example.couple.R;
import com.example.couple.ViewModel.UpdateDataInfo.UrlAndParamsViewModel;

public class UrlAndParamsActivity extends ActivityBase implements UrlAndParamsView {
    ImageView imgEditUrlJackpot;
    LinearLayout linearEditUrlJackpot;
    TextView tvUrlJackpot;
    TextView tvParamsJackpot;
    ImageView imgEditUrlLottery;
    LinearLayout linearEditUrlLottery;
    TextView tvUrlLottery;
    TextView tvParamsLottery;

    UrlAndParamsViewModel viewModel;
    public static final int RC_URL_JACKPOT = 222;
    public static final int RC_URL_LOTTERY = 333;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_url_and_params);

        imgEditUrlJackpot = findViewById(R.id.imgEditUrlJackpot);
        linearEditUrlJackpot = findViewById(R.id.linearEditUrlJackpot);
        tvUrlJackpot = findViewById(R.id.tvUrlJackpot);
        tvParamsJackpot = findViewById(R.id.tvParamsJackpot);
        imgEditUrlLottery = findViewById(R.id.imgEditUrlLottery);
        linearEditUrlLottery = findViewById(R.id.linearEditUrlLottery);
        tvUrlLottery = findViewById(R.id.tvUrlLottery);
        tvParamsLottery = findViewById(R.id.tvParamsLottery);

        viewModel = new UrlAndParamsViewModel(this, this);
        viewModel.getUrlAndParams();

        imgEditUrlJackpot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openUrlAndParamsInfo(RC_URL_JACKPOT);
            }
        });

        linearEditUrlJackpot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openUrlAndParamsInfo(RC_URL_JACKPOT);
            }
        });

        imgEditUrlLottery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openUrlAndParamsInfo(RC_URL_LOTTERY);
            }
        });

        linearEditUrlLottery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openUrlAndParamsInfo(RC_URL_LOTTERY);
            }
        });

    }

    private void openUrlAndParamsInfo(int requestCode) {
        Intent intent = new Intent(UrlAndParamsActivity.this, UrlAndParamsInfoActivity.class);
        intent.putExtra("URL_TYPE", requestCode);
        startActivityForResult(intent, requestCode);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            viewModel.getUrlAndParams();
        }

    }

    @Override
    public void showMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showUrlAndParams(String[] urlJackpotArr, String[] urlLotteryArr) {
        tvUrlJackpot.setText(urlJackpotArr[0].trim());
        tvParamsJackpot.setText(urlJackpotArr[1].trim());
        tvUrlLottery.setText(urlLotteryArr[0].trim());
        tvParamsLottery.setText(urlLotteryArr[1].trim());
    }

    @Override
    public Context getContext() {
        return this;
    }
}
