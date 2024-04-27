package com.example.couple.View.UpdateDataInfo;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.couple.Base.Handler.IOFileBase;
import com.example.couple.Base.Handler.InternetBase;
import com.example.couple.Base.Handler.NotificationBase;
import com.example.couple.Base.Handler.NumberBase;
import com.example.couple.Custom.Const.Const;
import com.example.couple.Custom.Const.FileName;
import com.example.couple.Custom.Handler.Api;
import com.example.couple.Custom.Handler.CheckUpdate;
import com.example.couple.R;
import com.example.couple.ViewModel.UpdateDataInfo.AddJackpotManyYearsViewModel;

import java.util.List;
import java.util.concurrent.ExecutionException;

public class AddJackpotManyYearsActivity extends AppCompatActivity implements AddJackpotManyYearsView {
    EditText edtStart;
    Button btnAddData;
    Button btnLoadAllData;
    Button btnCancel;
    Button btnTestNotif;

    AddJackpotManyYearsViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_jackpot_many_years);

        edtStart = findViewById(R.id.edtStart);
        btnAddData = findViewById(R.id.btnAddData);
        btnLoadAllData = findViewById(R.id.btnLoadAllData);
        btnCancel = findViewById(R.id.tvCancel);
        btnTestNotif = findViewById(R.id.btnTestNotif);

        viewModel = new AddJackpotManyYearsViewModel(this, this);

        viewModel.getStartYear();

        btnLoadAllData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String startingYear = edtStart.getText().toString().trim();
                if (startingYear.isEmpty()) {
                    showMessage("Vui lòng nhập năm bắt đầu!");
                } else if (!InternetBase.isInternetAvailable(AddJackpotManyYearsActivity.this)) {
                    showMessage("Bạn đang offline.");
                } else {
                    new AlertDialog.Builder(AddJackpotManyYearsActivity.this)
                            .setTitle("Nạp dữ liệu")
                            .setMessage("Bạn có chắc muốn nạp dữ liệu XS Đặc biệt trong " +
                                    "tất cả các năm kể từ năm " + startingYear + " không?")
                            .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    viewModel.updateJackpotDataInManyYears(Integer.parseInt(startingYear), true);
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
                if (startingYear.isEmpty()) {
                    showMessage("Vui lòng nhập năm bắt đầu!");
                } else {
                    new AlertDialog.Builder(AddJackpotManyYearsActivity.this)
                            .setTitle("Nạp dữ liệu")
                            .setMessage("Bạn có chắc muốn nạp thêm dữ liệu XS Đặc biệt nhiều " +
                                    "năm kể từ năm " + startingYear + " không?")
                            .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    if (InternetBase.isInternetAvailable(AddJackpotManyYearsActivity.this)) {
                                        viewModel.updateJackpotDataInManyYears(Integer.parseInt(startingYear), false);
                                    } else {
                                        showMessage("Bạn đang offline!");
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

        btnTestNotif.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getData(AddJackpotManyYearsActivity.this);
            }
        });
    }

    @Override
    public void showMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showStartYear(int year) {
        edtStart.setText(year + "");
        edtStart.setSelection(edtStart.length());
    }

    @Override
    public void updateJackpotDataInManyYearsError(List<Integer> years) {
        String show = "Đã cập nhật dữ liệu. Các năm chưa được cập nhật là: \n[" +
                NumberBase.showNumbers(years, ", ") + "]";
        Toast.makeText(this, show, Toast.LENGTH_LONG).show();
    }

    public void getData(Context context) {
        String title = "XSMB";
        String content = 1 + "";
        NotificationBase.pushNotification(context, Integer.parseInt(content), title, content);
    }

    private void getDataIfNeeded(Context context) {
        boolean checkUpdateTime = CheckUpdate.checkUpdateTime(context);
        boolean checkUpdateLottery = CheckUpdate.checkUpdateLottery(context);
        try {
            if (checkUpdateTime) {
                String time = Api.getTimeDataFromInternet(context);
                IOFileBase.saveDataToFile(context, FileName.TIME, time, 0);
            }
            if (checkUpdateLottery) {
                String lottery = Api.getLotteryDataFromInternet(context, Const.MAX_DAYS_TO_GET_LOTTERY);
                IOFileBase.saveDataToFile(context, FileName.LOTTERY, lottery, 0);
            }
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }
    }

}
