package com.example.couple.View.Main;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.MutableLiveData;

import com.example.couple.Base.Handler.AlarmBase;
import com.example.couple.Base.Handler.ThreadBase;
import com.example.couple.Base.View.ActivityBase;
import com.example.couple.Custom.Const.Const;
import com.example.couple.Custom.Handler.UpdateData.UpdateDataService;
import com.example.couple.Custom.Handler.UpdateData.UpdateDataView;
import com.example.couple.Model.Origin.Jackpot;
import com.example.couple.Model.Origin.Lottery;
import com.example.couple.R;
import com.example.couple.View.Main.CreateNumberArray.CreateNumberArrayFragment;
import com.example.couple.View.Main.FunctionDisplay.FunctionDisplayFragment;
import com.example.couple.View.Main.HomePage.HomePageFragment;
import com.example.couple.View.Main.NumberPicker.NumberPickerFragment;
import com.example.couple.View.Main.Personal.PersonalFragment;
import com.example.couple.ViewModel.Main.MainViewModel;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.List;

import lombok.Getter;
import lombok.Setter;


public class MainActivity extends ActivityBase implements MainView, UpdateDataView {
    BottomNavigationView navigationView;

    FragmentManager fm = getSupportFragmentManager();
    public static Fragment fragment1 = new HomePageFragment();
    public static Fragment fragment2 = new NumberPickerFragment();
    public static Fragment fragment3 = new CreateNumberArrayFragment();
    public static Fragment fragment4 = new FunctionDisplayFragment();
    public static Fragment fragment5 = new PersonalFragment();
    public static Fragment active = fragment1;

    MainViewModel mainViewModel;
    UpdateDataService updateDataService;

    @Getter
    @Setter
    private MutableLiveData<String> time = new MutableLiveData<>();
    @Getter
    @Setter
    private MutableLiveData<List<Jackpot>> jackpotList = new MutableLiveData<>();
    @Getter
    @Setter
    private MutableLiveData<List<Lottery>> lotteryList = new MutableLiveData<>();
    @Getter
    @Setter
    private MutableLiveData<List<Integer>> couplesToTransfer = new MutableLiveData<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        navigationView = findViewById(R.id.bottom_navigation);

        fm.beginTransaction().add(R.id.container, fragment5, "5").hide(fragment5).commit();
        fm.beginTransaction().add(R.id.container, fragment4, "4").hide(fragment4).commit();
        fm.beginTransaction().add(R.id.container, fragment3, "3").hide(fragment3).commit();
        fm.beginTransaction().add(R.id.container, fragment2, "2").hide(fragment2).commit();
        fm.beginTransaction().add(R.id.container, fragment1, "1").commit();

        mainViewModel = new MainViewModel(this, this);
        updateDataService = new UpdateDataService(this, this);
        updateDataService.getTimeData(true);
        updateDataService.getJackpotData(true, true);
        updateDataService.getLotteryData(Const.MAX_DAYS_TO_GET_LOTTERY, true);
        new ThreadBase<>((param) -> {
            mainViewModel.setUrlAndParamsIfNoData();
            return null;
        }, "").start();

        navigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.itHome:
                        if (active != fragment1) {
                            fm.beginTransaction().show(fragment1).hide(active).commit();
                            active = fragment1;
                            initSpeechToText(0);
                        }
                        return true;
                    case R.id.itNumberPicker:
                        if (active != fragment2) {
                            fm.beginTransaction().show(fragment2).hide(active).commit();
                            active = fragment2;
                            initSpeechToText(1);
                        }
                        return true;
                    case R.id.itCreateNumberArray:
                        if (active != fragment3) {
                            fm.beginTransaction().show(fragment3).hide(active).commit();
                            active = fragment3;
                            initSpeechToText(2);
                        }
                        return true;
                    case R.id.itFunction:
                        if (active != fragment4) {
                            fm.beginTransaction().show(fragment4).hide(active).commit();
                            active = fragment4;
                            initSpeechToText(3);
                        }
                        return true;
                    case R.id.itPersonal:
                        if (active != fragment5) {
                            fm.beginTransaction().show(fragment5).hide(active).commit();
                            active = fragment5;
                            initSpeechToText(4);
                        }
                        return true;
                }
                return false;
            }
        });
    }

    private final boolean[] isEventBound = new boolean[5];

    private void initSpeechToText(int index) {
        if (active != null) {
            View fragmentView = active.getView();
            if (fragmentView != null) {
                // Tìm kiếm View bên trong Fragment
                TextView tvToolbar = fragmentView.findViewById(R.id.tvToolbar);
                if (tvToolbar != null && !isEventBound[index]) {
                    tvToolbar.setOnLongClickListener(new View.OnLongClickListener() {
                        @Override
                        public boolean onLongClick(View view) {
                            MainActivity.super.startListening();
                            return false;
                        }
                    });
                    isEventBound[index] = true;
                }
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        updateDataService.getAllIfDataIsOld(time.getValue(), jackpotList.getValue(), lotteryList.getValue());
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (!AlarmBase.isEnableExactAlarmPermission(this)) {
            askToEnableExactAlarmPermission(this);
        } else {
            mainViewModel.registerBackgoundRuntime();
        }
        new ThreadBase<>((param) -> {
            updateDataService.updateAllData(false, false);
            return null;
        }, "").start();
        initSpeechToText(0);
    }

    private static void askToEnableExactAlarmPermission(Context context) {
        new AlertDialog.Builder(context)
                .setTitle("Yêu cầu quyền")
                .setMessage("Ứng dụng cần quyền đặt báo thức chính xác để hoạt động đúng. Bạn có muốn cấp quyền không?")
                .setPositiveButton("Có", (dialog, which) -> {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                        Intent intent = new Intent(Settings.ACTION_REQUEST_SCHEDULE_EXACT_ALARM);
                        context.startActivity(intent);
                    }
                })
                .setNegativeButton("Không", (dialog, which) -> {
                    dialog.dismiss();
                })
                .show();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        System.exit(0);
    }

    @Override
    public void showMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showLongMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }

    @Override
    public void showTimeData(String time) {
        this.time.setValue(time);
    }

    @Override
    public void showJackpotData(List<Jackpot> jackpotList) {
        this.jackpotList.setValue(jackpotList);
    }

    @Override
    public void showLotteryData(List<Lottery> lotteries) {
        this.lotteryList.setValue(lotteries);
    }

    @Override
    public Context getContext() {
        return this;
    }
}