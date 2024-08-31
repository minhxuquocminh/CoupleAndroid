package com.example.couple.View.Main;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.MutableLiveData;

import com.example.couple.Base.Handler.ThreadBase;
import com.example.couple.Custom.Service.UpdateDataService;
import com.example.couple.Custom.Service.UpdateDataView;
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


public class MainActivity extends AppCompatActivity implements MainView, UpdateDataView {
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
        updateDataService.getJackpotData(true);
        new ThreadBase<>((param) -> {
            mainViewModel.setUrlAndParamsIfNoData();
            mainViewModel.registerBackgoundRuntime();
            updateDataService.updateAllData(false, false);
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
                        }
                        return true;
                    case R.id.itNumberPicker:
                        if (active != fragment2) {
                            fm.beginTransaction().show(fragment2).hide(active).commit();
                            active = fragment2;
                        }
                        return true;
                    case R.id.itCreateNumberArray:
                        if (active != fragment3) {
                            fm.beginTransaction().show(fragment3).hide(active).commit();
                            active = fragment3;
                        }
                        return true;
                    case R.id.itFunction:
                        if (active != fragment4) {
                            fm.beginTransaction().show(fragment4).hide(active).commit();
                            active = fragment4;
                        }
                        return true;
                    case R.id.itPersonal:
                        if (active != fragment5) {
                            fm.beginTransaction().show(fragment5).hide(active).commit();
                            active = fragment5;
                        }
                        return true;
                }
                return false;
            }
        });
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
}