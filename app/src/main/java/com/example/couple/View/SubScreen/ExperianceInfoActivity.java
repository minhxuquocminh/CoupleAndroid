package com.example.couple.View.SubScreen;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.couple.R;
import com.example.couple.ViewModel.SubScreen.ExperianceInfoViewModel;

public class ExperianceInfoActivity extends AppCompatActivity implements ExperianceInfoView {
    EditText edtExperiance;
    Button btnSave;
    Button btnCancel;

    ExperianceInfoViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_experiance_info);

        edtExperiance = findViewById(R.id.edtExperiance);
        btnSave = findViewById(R.id.btnSave);
        btnCancel = findViewById(R.id.tvCancel);

        viewModel = new ExperianceInfoViewModel(this, this);

        Intent intent = getIntent();
        String experiance = intent.getStringExtra("EXPERIANCE");
        if (experiance != null || !experiance.equals("")) {
            edtExperiance.setText(experiance);
        }

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String experiance = edtExperiance.getText().toString().trim();
                viewModel.UpdateExperiance(experiance);
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    public void UpdateExperianceSuccess(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
        setResult(RESULT_OK);
        finish();
    }

    @Override
    public void ShowError(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}
