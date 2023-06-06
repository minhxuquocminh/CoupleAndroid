package com.example.couple.View.Sub;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.couple.Base.View.WidgetBase;
import com.example.couple.R;
import com.example.couple.ViewModel.Sub.CalculatingBalanceCoupleViewModel;


public class CalculatingBalanceCoupleActivity extends AppCompatActivity implements CalculatingBalanceCoupleView {
    TextView tvShow;
    EditText edtFirstNumber;
    EditText edtSecondNumber;
    TextView tvCalculate;

    CalculatingBalanceCoupleViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calculating_balance_couple);

        tvShow = findViewById(R.id.tvInfo);
        edtFirstNumber = findViewById(R.id.edtFirstNumber);
        edtSecondNumber = findViewById(R.id.edtSecondNumber);
        tvCalculate = findViewById(R.id.tvCalculate);

        viewModel = new CalculatingBalanceCoupleViewModel(this);

        tvCalculate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                WidgetBase.hideKeyboard(CalculatingBalanceCoupleActivity.this);
                String firstNumber = edtFirstNumber.getText().toString().trim();
                String secondNumber = edtSecondNumber.getText().toString().trim();
                viewModel.CalculateBalance2D(firstNumber, secondNumber);
            }
        });

    }

    @Override
    public void ShowResult(String result) {
        tvShow.setText(result);
    }

    @Override
    public void ShowError(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT)
                .show();
    }
}
