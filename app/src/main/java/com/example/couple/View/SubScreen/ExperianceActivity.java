package com.example.couple.View.SubScreen;

import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.couple.R;
import com.example.couple.ViewModel.SubScreen.ExperianceViewModel;

public class ExperianceActivity extends AppCompatActivity implements ExperianceView {
    TextView tvExperiance;

    ExperianceViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_experiance);

        tvExperiance = findViewById(R.id.tvExperiance);
        viewModel = new ExperianceViewModel(this, this);
        viewModel.getExperiance();
    }

    @Override
    public void showExperiance(String experiance) {
        tvExperiance.setText(experiance);
    }

    @Override
    public void showMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

}
