package com.example.couple.View.Main;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
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
import com.example.couple.View.Bridge.AfterDoubleBridgeActivity;
import com.example.couple.View.Bridge.BridgeCombinationActivity;
import com.example.couple.View.Bridge.ConnectedBridgeActivity;
import com.example.couple.View.Bridge.EstimatedBridgeActivity;
import com.example.couple.View.Bridge.TouchBridgeActivity;
import com.example.couple.View.BridgeHistory.NumberSetHistoryActivity;
import com.example.couple.View.BridgeHistory.SexagenaryCycleActivity;
import com.example.couple.View.Couple.BalanceCoupleActivity;
import com.example.couple.View.Couple.CoupleByWeekActivity;
import com.example.couple.View.JackpotStatistics.JackpotAllYearActivity;
import com.example.couple.View.JackpotStatistics.JackpotByYearActivity;
import com.example.couple.View.JackpotStatistics.JackpotNextDayActivity;
import com.example.couple.View.JackpotStatistics.JackpotThisYearActivity;
import com.example.couple.View.Lottery.LotteryActivity;
import com.example.couple.View.Main.CreateNumberArray.CreateNumberArrayFragment;
import com.example.couple.View.Main.FunctionDisplay.FunctionDisplayFragment;
import com.example.couple.View.Main.HomePage.HomePageFragment;
import com.example.couple.View.Main.NumberPicker.NumberPickerFragment;
import com.example.couple.View.Main.Personal.PersonalFragment;
import com.example.couple.View.SubScreen.CalculatingBalanceCoupleActivity;
import com.example.couple.View.SubScreen.CycleByYearActivity;
import com.example.couple.View.SubScreen.NoteActivity;
import com.example.couple.View.UpdateDataInfo.AddJackpotManyYearsActivity;
import com.example.couple.View.UpdateDataInfo.UrlAndParamsActivity;
import com.example.couple.ViewModel.Main.MainViewModel;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.List;

import lombok.Getter;
import lombok.Setter;


public class MainActivity extends ActivityBase implements MainView, UpdateDataView {
    BottomNavigationView navigationView;
    private PopupWindow navigationPopupWindow;

    private static final String TOOLBAR_MENU_TAG = "toolbar_navigation_menu";

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
                bindNavigationMenu(fragmentView);
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

    private void bindNavigationMenu(View fragmentView) {
        Toolbar toolbar = fragmentView.findViewById(R.id.toolbar);
        if (toolbar == null) return;

        View existedMenu = toolbar.findViewWithTag(TOOLBAR_MENU_TAG);
        if (existedMenu == null) {
            TextView tvMenu = new TextView(this);
            tvMenu.setTag(TOOLBAR_MENU_TAG);
            tvMenu.setText("\u2630");
            tvMenu.setTextColor(getResources().getColor(R.color.colorThemeLight));
            tvMenu.setTextSize(26);
            tvMenu.setGravity(android.view.Gravity.CENTER);
            tvMenu.setTypeface(Typeface.DEFAULT_BOLD);
            tvMenu.setContentDescription("Mở menu điều hướng");

            Toolbar.LayoutParams params = new Toolbar.LayoutParams(dp(52), ViewGroup.LayoutParams.MATCH_PARENT);
            params.gravity = android.view.Gravity.START | android.view.Gravity.CENTER_VERTICAL;
            toolbar.addView(tvMenu, params);
            existedMenu = tvMenu;
        }

        existedMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showNavigationMenu(view);
            }
        });
    }

    private void showNavigationMenu(View anchor) {
        if (navigationPopupWindow != null && navigationPopupWindow.isShowing()) {
            navigationPopupWindow.dismiss();
            return;
        }

        ScrollView scrollView = new ScrollView(this);
        scrollView.setBackgroundColor(Color.WHITE);

        LinearLayout container = new LinearLayout(this);
        container.setOrientation(LinearLayout.VERTICAL);
        container.setPadding(0, dp(6), 0, dp(6));
        scrollView.addView(container, new ScrollView.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
        ));

        NavigationMenuGroup[] menuGroups = createNavigationMenuGroups();
        for (NavigationMenuGroup menuGroup : menuGroups) {
            addGroupView(container, menuGroup);
        }

        navigationPopupWindow = new PopupWindow(
                scrollView,
                dp(280),
                ViewGroup.LayoutParams.WRAP_CONTENT,
                true
        );
        navigationPopupWindow.setBackgroundDrawable(new ColorDrawable(Color.WHITE));
        navigationPopupWindow.setOutsideTouchable(true);
        navigationPopupWindow.setElevation(dp(6));
        navigationPopupWindow.showAsDropDown(anchor, 0, 0);
    }

    private void addGroupView(LinearLayout container, NavigationMenuGroup menuGroup) {
        TextView tvGroup = new TextView(this);
        tvGroup.setText(menuGroup.title);
        tvGroup.setTextColor(getResources().getColor(R.color.colorPrimary));
        tvGroup.setTextSize(16);
        tvGroup.setTypeface(Typeface.DEFAULT_BOLD);
        tvGroup.setPadding(dp(14), dp(9), dp(14), dp(5));
        container.addView(tvGroup, new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
        ));

        for (final NavigationMenuItem menuItem : menuGroup.items) {
            TextView tvItem = new TextView(this);
            tvItem.setText(menuItem.title);
            tvItem.setTextColor(getResources().getColor(R.color.colorText));
            tvItem.setTextSize(15);
            tvItem.setPadding(dp(32), dp(8), dp(14), dp(8));
            tvItem.setBackgroundColor(Color.WHITE);
            tvItem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    openNavigationItem(menuItem);
                }
            });
            container.addView(tvItem, new LinearLayout.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT
            ));
        }
    }

    private void openNavigationItem(NavigationMenuItem menuItem) {
        if (navigationPopupWindow != null) {
            navigationPopupWindow.dismiss();
        }
        if (menuItem.targetClass == null) {
            Toast.makeText(this, "Chưa cấu hình màn hình: " + menuItem.title, Toast.LENGTH_SHORT).show();
            return;
        }
        startActivity(new Intent(this, menuItem.targetClass));
    }

    private NavigationMenuGroup[] createNavigationMenuGroups() {
        return new NavigationMenuGroup[]{
                new NavigationMenuGroup("Xem thông tin", new NavigationMenuItem[]{
                        new NavigationMenuItem("XSMB", LotteryActivity.class),
                        new NavigationMenuItem("XS Đặc Biệt", JackpotByYearActivity.class),
                        new NavigationMenuItem("ĐB hôm sau", JackpotNextDayActivity.class),
                        new NavigationMenuItem("ĐB theo tuần", CoupleByWeekActivity.class),
                        new NavigationMenuItem("BSCB", BalanceCoupleActivity.class),
                        new NavigationMenuItem("Can Chi", SexagenaryCycleActivity.class)
                }),
                new NavigationMenuGroup("Xem cầu", new NavigationMenuItem[]{
                        new NavigationMenuItem("Cầu liên thông", ConnectedBridgeActivity.class),
                        new NavigationMenuItem("Cầu sau khi ra kép", AfterDoubleBridgeActivity.class),
                        new NavigationMenuItem("Cầu chạm", TouchBridgeActivity.class),
                        new NavigationMenuItem("Cầu ước lượng", EstimatedBridgeActivity.class),
                        new NavigationMenuItem("Tổng hợp cầu", BridgeCombinationActivity.class)
                }),
                new NavigationMenuGroup("Thống kê", new NavigationMenuItem[]{
                        new NavigationMenuItem("Đặc Biệt năm nay", JackpotThisYearActivity.class),
                        new NavigationMenuItem("Đặc Biệt nhiều năm", JackpotAllYearActivity.class),
                        new NavigationMenuItem("Nhịp chạy ĐB", NumberSetHistoryActivity.class)
                }),
                new NavigationMenuGroup("Cài đặt", new NavigationMenuItem[]{
                        new NavigationMenuItem("Sửa URL và Param", UrlAndParamsActivity.class),
                        new NavigationMenuItem("Lưu dữ liệu ĐB", AddJackpotManyYearsActivity.class),
                        new NavigationMenuItem("Cài đặt thông báo", null)
                }),
                new NavigationMenuGroup("Khác", new NavigationMenuItem[]{
                        new NavigationMenuItem("Can chi theo năm", CycleByYearActivity.class),
                        new NavigationMenuItem("Note", NoteActivity.class),
                        new NavigationMenuItem("Tính BSCB", CalculatingBalanceCoupleActivity.class)
                })
        };
    }

    private int dp(int value) {
        return (int) (value * getResources().getDisplayMetrics().density + 0.5f);
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

    private static class NavigationMenuGroup {
        final String title;
        final NavigationMenuItem[] items;

        NavigationMenuGroup(String title, NavigationMenuItem[] items) {
            this.title = title;
            this.items = items;
        }
    }

    private static class NavigationMenuItem {
        final String title;
        final Class<?> targetClass;

        NavigationMenuItem(String title, Class<?> targetClass) {
            this.title = title;
            this.targetClass = targetClass;
        }
    }
}
