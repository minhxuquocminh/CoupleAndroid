package com.example.couple.View.Sub;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.couple.R;
import com.example.couple.ViewModel.Sub.ExperianceViewModel;

public class ExperianceActivity extends AppCompatActivity implements ExperianceView {
    TextView tvTitle;
    ImageView imgEdit;
    TextView tvExperiance;

    ExperianceViewModel viewModel;
    String experiance = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_experiance);

        tvTitle = findViewById(R.id.tvTitle);
        imgEdit = findViewById(R.id.imgEdit);
        tvExperiance = findViewById(R.id.tvExperiance);

        viewModel = new ExperianceViewModel(this, this);
        viewModel.GetExperiance();

        imgEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ExperianceActivity.this, ExperianceInfoActivity.class);
                intent.putExtra("EXPERIANCE", experiance);
                startActivity(intent);
            }
        });
    }

    @Override
    public void ShowExperiance(String experiance) {
        tvTitle.setText("Những kinh nghiệm về cầu kèo, chọn số:");
        tvExperiance.setVisibility(View.VISIBLE);
        tvExperiance.setText(experiance);
        this.experiance = experiance;
    }

    @Override
    public void HideExperiance() {
        tvTitle.setText("Chưa có thông tin nào!");
        tvExperiance.setVisibility(View.GONE);
    }

    @Override
    public void ShowError(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            viewModel.GetExperiance();
        }
    }
}
