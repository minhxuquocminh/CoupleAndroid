package com.example.couple.View.UpdateDataInfo;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.couple.R;
import com.example.couple.ViewModel.UpdateDataInfo.UrlAndParamsViewModel;

public class UrlAndParamsActivity extends AppCompatActivity implements UrlAndParamsView {
    ImageView imgEditUrlJackpot;
    TextView tvUrlJackpot;
    TextView tvParamsJackpot;
    ImageView imgEditUrlLottery;
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
        tvUrlJackpot = findViewById(R.id.tvUrlJackpot);
        tvParamsJackpot = findViewById(R.id.tvParamsJackpot);
        imgEditUrlLottery = findViewById(R.id.imgEditUrlLottery);
        tvUrlLottery = findViewById(R.id.tvUrlLottery);
        tvParamsLottery = findViewById(R.id.tvParamsLottery);

        viewModel = new UrlAndParamsViewModel(this, this);
        viewModel.getUrlAndParams();

        imgEditUrlJackpot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UrlAndParamsActivity.this, UrlAndParamsInfoActivity.class);
                intent.putExtra("URL_TYPE", RC_URL_JACKPOT);
                startActivityForResult(intent, RC_URL_JACKPOT);
            }
        });

        imgEditUrlLottery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UrlAndParamsActivity.this, UrlAndParamsInfoActivity.class);
                intent.putExtra("URL_TYPE", RC_URL_LOTTERY);
                startActivityForResult(intent, RC_URL_LOTTERY);
            }
        });

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
}
