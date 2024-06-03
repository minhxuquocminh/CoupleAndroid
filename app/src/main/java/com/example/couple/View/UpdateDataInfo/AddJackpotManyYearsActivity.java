package com.example.couple.View.UpdateDataInfo;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.couple.Base.Handler.InternetBase;
import com.example.couple.Base.Handler.NumberBase;
import com.example.couple.Base.View.DialogBase;
import com.example.couple.Custom.Const.TimeInfo;
import com.example.couple.Custom.Handler.Bridge.OtherBridgeHandler;
import com.example.couple.Custom.Handler.JackpotHandler;
import com.example.couple.Model.Display.NumberSetHistory;
import com.example.couple.Model.Origin.Jackpot;
import com.example.couple.Model.Time.Cycle.Branch;
import com.example.couple.R;
import com.example.couple.ViewModel.UpdateDataInfo.AddJackpotManyYearsViewModel;

import java.util.Collections;
import java.util.List;

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
                    String title = "Nạp dữ liệu";
                    String message = "Bạn có chắc muốn nạp dữ liệu XS Đặc biệt trong " +
                            "tất cả các năm kể từ năm " + startingYear + " không?";
                    DialogBase.showWithConfirmation(AddJackpotManyYearsActivity.this, title, message, () -> {
                        viewModel.updateJackpotDataInManyYears(Integer.parseInt(startingYear), true);
                    });
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
                    String title = "Nạp dữ liệu";
                    String message = "Bạn có chắc muốn nạp thêm dữ liệu XS Đặc biệt nhiều " +
                            "năm kể từ năm " + startingYear + " không?";
                    DialogBase.showWithConfirmation(AddJackpotManyYearsActivity.this, title, message, () -> {
                        if (InternetBase.isInternetAvailable(AddJackpotManyYearsActivity.this)) {
                            viewModel.updateJackpotDataInManyYears(Integer.parseInt(startingYear), false);
                        } else {
                            showMessage("Bạn đang offline!");
                        }
                    });
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
                testNotification(AddJackpotManyYearsActivity.this);
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

    public void testNotification(Context context) {
//        Calendar calendar = Calendar.getInstance();
//        int hour = calendar.get(Calendar.HOUR_OF_DAY);
//        int minute = calendar.get(Calendar.MINUTE);
//        AlarmBase.registerAlarmOneTime(context, AlarmTest.class,
//                1234, hour, minute + 1, 0);
//        showMessage("Đã đăng ký alarm !");
        int max = 0;
        List<Jackpot> jackpotList = JackpotHandler.getReverseJackpotListManyYears(context, 4);
        String mess = "";
        for (int i = 0; i < TimeInfo.EARTHLY_BRANCHES.size(); i++) {
            NumberSetHistory branch = OtherBridgeHandler.getNumberSetHistory(jackpotList,
                    TimeInfo.EARTHLY_BRANCHES.get(i) + " " + i, (new Branch(i)).getTailsOfYear());
            int maxbeat = Collections.max(branch.getBeatList());
            max = Math.max(maxbeat, max);
            mess += branch.show() + "\n";
        }
        DialogBase.showBasic(context, "ttt" + jackpotList.size() + " - max=" + max, mess);
    }

}
