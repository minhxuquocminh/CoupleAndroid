package com.example.couple.View.UpdateDataInfo;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.couple.R;
import com.example.couple.ViewModel.UpdateDataInfo.UrlAndParamsInfoViewModel;

public class UrlAndParamsInfoActivity extends AppCompatActivity implements UrlAndParamsInfoView {
    TextView tvTitleToolbar;
    EditText edtUrl;
    EditText edtClassName;
    Button btnSave;
    Button btnCancel;

    UrlAndParamsInfoViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_url_and_params_info);

        tvTitleToolbar = findViewById(R.id.tvToolbar);
        edtUrl = findViewById(R.id.edtUrl);
        edtClassName = findViewById(R.id.edtClassName);
        btnSave = findViewById(R.id.btnSave);
        btnCancel = findViewById(R.id.tvCancel);

        viewModel = new UrlAndParamsInfoViewModel(this, this);

        int activity = getIntent().getIntExtra("URL_TYPE", 0);
        String type = "";
        if (activity == UrlAndParamsActivity.RC_URL_JACKPOT) {
            type = "jackpot";
            tvTitleToolbar.setText("Sửa đường dẫn và tham số XS Đặc biệt");
        } else if (activity == UrlAndParamsActivity.RC_URL_LOTTERY) {
            type = "lottery";
            tvTitleToolbar.setText("Sửa đường dẫn và tham số XSMB");
        }

        viewModel.getUrlAndParams(type);

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = edtUrl.getText().toString().trim();
                String className = edtClassName.getText().toString().trim();
                viewModel.saveData(url, className);
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
    public void showMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showUrlAndParams(String[] arr) {
        edtUrl.setText(arr[0].trim());
        edtUrl.setSelection(edtUrl.length());
        edtClassName.setText(arr[1].trim());
    }

    @Override
    public void saveDataSuccess(int status) {
        if (status == 0) {
            setResult(RESULT_CANCELED);
        } else if (status == 1) {
            setResult(RESULT_OK);
            Toast.makeText(this, "Lưu thành công.", Toast.LENGTH_SHORT).show();
        }
        finish();
    }
}
