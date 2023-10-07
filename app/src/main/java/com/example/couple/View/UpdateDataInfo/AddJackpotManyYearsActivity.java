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
import com.example.couple.Custom.Const.Const;
import com.example.couple.Custom.Const.FileName;
import com.example.couple.Custom.Const.TimeInfo;
import com.example.couple.Custom.Handler.Api;
import com.example.couple.Custom.Handler.CheckUpdate;
import com.example.couple.Custom.Handler.JackpotHandler;
import com.example.couple.Model.Origin.Jackpot;
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

        viewModel.GetStartYear();

        btnLoadAllData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String startingYear = edtStart.getText().toString().trim();
                if (startingYear.equals("")) {
                    ShowError("Vui lòng nhập năm bắt đầu!");
                } else if (!InternetBase.isInternetAvailable(AddJackpotManyYearsActivity.this)) {
                    ShowError("Bạn đang offline.");
                } else {
                    new AlertDialog.Builder(AddJackpotManyYearsActivity.this)
                            .setTitle("Nạp dữ liệu")
                            .setMessage("Bạn có chắc muốn nạp dữ liệu XS Đặc biệt trong " +
                                    "tất cả các năm kể từ năm " + startingYear + " không?")
                            .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    viewModel.GetJackpotDataInManyYears(Integer.parseInt(startingYear), false);
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
                                    if (InternetBase.isInternetAvailable(AddJackpotManyYearsActivity.this)) {
                                        viewModel.GetJackpotDataInManyYears(Integer.parseInt(startingYear), true);
                                    } else {
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

        btnTestNotif.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getData(AddJackpotManyYearsActivity.this);
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
        edtStart.setSelection(edtStart.length());
    }

    @Override
    public void GetJackpotDataSuccess(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
        viewModel.GetStartYear();
    }

    public void getData(Context context) {
        String title = "XSMB";
        String content = "";
        if (CheckUpdate.checkUpdateJackpot(context)) {
            try {
                String jackpot = Api.GetJackpotDataFromInternet(context, TimeInfo.CURRENT_YEAR);
                IOFileBase.saveDataToFile(context, "jackpot" +
                        TimeInfo.CURRENT_YEAR + ".txt", jackpot, 0);
                if (!CheckUpdate.checkUpdateJackpot(context)) {
                    List<Jackpot> jackpotList = JackpotHandler
                            .GetReserveJackpotListFromFile(context, 1);
                    content = "Kết quả XS Đặc biệt Miền Bắc hôm nay là: " +
                            jackpotList.get(0).getJackpot() + ".";
                    NotificationBase.pushNotification(context, title, content);
                    getDataIfNeeded(context);
                }
            } catch (ExecutionException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private void getDataIfNeeded(Context context) {
        boolean checkUpdateTime = CheckUpdate.checkUpdateTime(context);
        boolean checkUpdateLottery = CheckUpdate.checkUpdateLottery(context);
        try {
            if (checkUpdateTime) {
                String time = Api.GetTimeDataFromInternet(context);
                IOFileBase.saveDataToFile(context, FileName.TIME, time, 0);
            }
            if (checkUpdateLottery) {
                String lottery = Api.GetLotteryDataFromInternet(context, Const.MAX_DAYS_TO_GET_LOTTERY);
                IOFileBase.saveDataToFile(context, FileName.LOTTERY, lottery, 0);
            }
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
