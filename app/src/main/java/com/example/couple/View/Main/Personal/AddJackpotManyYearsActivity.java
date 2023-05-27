package com.example.couple.View.Main.Personal;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.couple.Base.Handler.InternetBase;
import com.example.couple.R;
import com.example.couple.ViewModel.Main.Personal.AddJackpotManyYearsViewModel;

public class AddJackpotManyYearsActivity extends AppCompatActivity implements AddJackpotManyYearsView {
    EditText edtStart;
    Button btnAddData;
    Button btnLoadAllData;
    Button btnCancel;

    AddJackpotManyYearsViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_jackpot_many_years);

        edtStart = findViewById(R.id.edtStart);
        btnAddData = findViewById(R.id.btnAddData);
        btnLoadAllData = findViewById(R.id.btnLoadAllData);
        btnCancel = findViewById(R.id.tvCancel);

        viewModel = new AddJackpotManyYearsViewModel(this, this);

        viewModel.GetStartYear();

        btnLoadAllData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String startingYear = edtStart.getText().toString().trim();
                if (startingYear.equals("")) {
                    ShowError("Vui lòng nhập năm bắt đầu!");
                } else if(!InternetBase.isNetworkAvailable(AddJackpotManyYearsActivity.this)) {
                    ShowError("Bạn đang offline.");
                }else {
                    new AlertDialog.Builder(AddJackpotManyYearsActivity.this)
                            .setTitle("Nạp dữ liệu")
                            .setMessage("Bạn có chắc muốn nạp dữ liệu XS Đặc biệt trong " +
                                    "tất cả các năm kể từ năm " + startingYear + " không?")
                            .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    viewModel.GetJackpotDataInManyYears(Integer.parseInt(startingYear),false);
                                }
                            })
                            .setNegativeButton(android.R.string.no, null)
                            .setIcon(android.R.drawable.ic_dialog_alert)
                            .show();
                }
            }
        });

        btnAddData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String startingYear = edtStart.getText().toString().trim();
                if (startingYear.equals("")) {
                    ShowError("Vui lòng nhập năm bắt đầu!");
                } else {
                    new AlertDialog.Builder(AddJackpotManyYearsActivity.this)
                            .setTitle("Nạp dữ liệu")
                            .setMessage("Bạn có chắc muốn nạp thêm dữ liệu XS Đặc biệt nhiều " +
                                    "năm kể từ năm " + startingYear + " không?")
                            .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    if(InternetBase.isNetworkAvailable(AddJackpotManyYearsActivity.this)) {
                                        viewModel.GetJackpotDataInManyYears(Integer.parseInt(startingYear), true);
                                    }else {
                                        ShowError("Bạn đang offline!");
                                    }
                                }
                            })
                            .setNegativeButton(android.R.string.no, null)
                            .setIcon(android.R.drawable.ic_dialog_alert)
                            .show();
                }
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
    public void ShowError(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void ShowStartYear(int year) {
        edtStart.setText(year + "");
    }

    @Override
    public void GetJackpotDataSuccess(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
        viewModel.GetStartYear();
    }

}
