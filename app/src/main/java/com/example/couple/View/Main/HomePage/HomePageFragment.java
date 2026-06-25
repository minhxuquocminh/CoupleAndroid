package com.example.couple.View.Main.HomePage;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;

import com.example.couple.Base.Handler.ThreadBase;
import com.example.couple.Base.View.DialogBase;
import com.example.couple.Custom.Const.Const;
import com.example.couple.Custom.Handler.DateHandler;
import com.example.couple.Custom.Handler.UpdateData.UpdateDataService;
import com.example.couple.Custom.Handler.UpdateData.UpdateDataView;
import com.example.couple.Model.DateTime.Date.DateBase;
import com.example.couple.Model.DateTime.Date.DateData;
import com.example.couple.Model.DateTime.Date.DateLunar;
import com.example.couple.Model.Origin.Jackpot;
import com.example.couple.Model.Origin.Lottery;
import com.example.couple.R;
import com.example.couple.View.Agent.AgentChatHistoryActivity;
import com.example.couple.View.Agent.AiJackpotStatisticsActivity;
import com.example.couple.View.Bridge.AfterDoubleBridgeActivity;
import com.example.couple.View.Bridge.BridgeCombinationActivity;
import com.example.couple.View.Bridge.ConnectedBridgeActivity;
import com.example.couple.View.Bridge.EstimatedBridgeActivity;
import com.example.couple.View.Bridge.MappingBridgeActivity;
import com.example.couple.View.Bridge.TouchBridgeActivity;
import com.example.couple.View.BridgeHistory.JackpotNumberSetRhythmActivity;
import com.example.couple.View.BridgeHistory.SexagenaryCycleActivity;
import com.example.couple.View.Couple.BalanceCoupleActivity;
import com.example.couple.View.Couple.CoupleByWeekActivity;
import com.example.couple.View.Jackpot.JackpotByYearActivity;
import com.example.couple.View.Jackpot.JackpotNextDayActivity;
import com.example.couple.View.JackpotStatistics.CurrentYearJackpotStatisticsActivity;
import com.example.couple.View.Lottery.LotteryActivity;
import com.example.couple.View.Main.MainActivity;
import com.example.couple.View.Notification.NewBridgeActivity;
import com.example.couple.View.Notification.NotificationActivity;
import com.example.couple.View.Search.SearchActivity;

import java.util.Calendar;
import java.util.List;

public class HomePageFragment extends Fragment implements HomePageView, UpdateDataView {
    TextView tvCalendar;
    ImageView imgPersonal;
    LinearLayout layoutSearch;
    ImageView imgNotification;
    ImageView imgNavigationMenu;
    LinearLayout layoutRefreshAll;
    TextView tvJackpotToday;
    TextView tvJackpotLastDay;
    LinearLayout layoutRecentJackpots;
    LinearLayout layoutQuickGroups;

    UpdateDataService updateDataService;

    MainActivity activity;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        activity = (MainActivity) context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home_page, container, false);

        tvCalendar = view.findViewById(R.id.tvCalendar);
        imgPersonal = view.findViewById(R.id.imgPersonal);
        layoutSearch = view.findViewById(R.id.layoutSearch);
        imgNotification = view.findViewById(R.id.imgNotification);
        imgNavigationMenu = view.findViewById(R.id.imgNavigationMenu);
        layoutRefreshAll = view.findViewById(R.id.layoutRefreshAll);
        tvJackpotToday = view.findViewById(R.id.tvJackpotToday);
        tvJackpotLastDay = view.findViewById(R.id.tvJackpotLastDay);
        layoutRecentJackpots = view.findViewById(R.id.layoutRecentJackpots);
        layoutQuickGroups = view.findViewById(R.id.layoutQuickGroups);

        updateDataService = new UpdateDataService(this, getActivity());
        bindQuickGroups();
        showJackpotList(null);

        activity.getTime().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String time) {
                tvCalendar.setText(formatCalendarText(time));
            }
        });

        activity.getJackpotList().observe(getViewLifecycleOwner(), new Observer<List<Jackpot>>() {
            @Override
            public void onChanged(List<Jackpot> jackpotList) {
                showJackpotList(jackpotList);
            }
        });

        imgPersonal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                activity.openPersonalTab();
            }
        });

        layoutSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), SearchActivity.class));
            }
        });

        imgNotification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), NotificationActivity.class));
            }
        });

        imgNavigationMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                activity.showNavigationMenu(view);
            }
        });

        layoutRefreshAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showUpdateMenu(v);
            }
        });

        return view;
    }

    private void bindQuickGroups() {
        layoutQuickGroups.removeAllViews();

        addQuickGroup("Thông tin", new QuickItem[]{
                new QuickItem("Xổ số\nMiền Bắc", LotteryActivity.class, R.drawable.ic_home_lottery_north),
                new QuickItem("Đặc Biệt\ntheo năm", JackpotByYearActivity.class, R.drawable.ic_home_jackpot_year),
                new QuickItem("Đặc Biệt\ntheo tuần", CoupleByWeekActivity.class, R.drawable.ic_home_jackpot_week),
                new QuickItem("Đặc Biệt\nngày mai", JackpotNextDayActivity.class, R.drawable.ic_home_jackpot_tomorrow),
                new QuickItem("Lịch sử\ncan chi", SexagenaryCycleActivity.class, R.drawable.ic_home_cycle_history),
                new QuickItem("Bộ số\ncân bằng", BalanceCoupleActivity.class, R.drawable.ic_home_balance_set),
                new QuickItem("Nhịp chạy\ncác bộ", JackpotNumberSetRhythmActivity.class, R.drawable.ic_home_set_rhythm),
                new QuickItem("Thống kê ĐB\nnăm nay", CurrentYearJackpotStatisticsActivity.class, R.drawable.ic_home_current_year_stats)
        });

        addQuickGroup("Phân tích", new QuickItem[]{
                new QuickItem("Bộ số\ncân bằng", BalanceCoupleActivity.class, R.drawable.ic_home_balance_analysis),
                new QuickItem("Chat\nvới AI", AgentChatHistoryActivity.class, R.drawable.ic_home_ai_chat),
                new QuickItem("Phân tích\ntừ AI", AiJackpotStatisticsActivity.class, R.drawable.ic_home_ai_stats)
        });

        addQuickGroup("Soi cầu", new QuickItem[]{
                new QuickItem("Cầu\nmới", NewBridgeActivity.class, R.drawable.ic_home_new_bridge),
                new QuickItem("Cầu\nliên thông", ConnectedBridgeActivity.class, R.drawable.ic_home_connected_bridge),
                new QuickItem("Cầu sau\nra kép", AfterDoubleBridgeActivity.class, R.drawable.ic_home_after_double_bridge),
                new QuickItem("Cầu\nchạm", TouchBridgeActivity.class, R.drawable.ic_home_touch_bridge),
                new QuickItem("Cầu\nánh xạ", MappingBridgeActivity.class, R.drawable.ic_home_mapping_bridge),
                new QuickItem("Cầu\nước lượng", EstimatedBridgeActivity.class, R.drawable.ic_home_estimated_bridge),
                new QuickItem("Tổng hợp\ncầu", BridgeCombinationActivity.class, R.drawable.ic_home_bridge_combo)
        });
    }

    private void addQuickGroup(String titleText, QuickItem[] items) {
        TextView title = new TextView(getContext());
        title.setText(titleText);
        title.setTextColor(getResources().getColor(R.color.colorPrimary));
        title.setTextSize(15);
        title.setTypeface(android.graphics.Typeface.DEFAULT_BOLD);
        title.setPadding(dp(3), dp(8), dp(3), dp(5));
        layoutQuickGroups.addView(title, new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));

        LinearLayout row = null;
        for (int i = 0; i < items.length; i++) {
            if (i % 4 == 0) {
                row = new LinearLayout(getContext());
                row.setOrientation(LinearLayout.HORIZONTAL);
                layoutQuickGroups.addView(row, new LinearLayout.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            }
            row.addView(createQuickItemView(items[i]));
        }

        if (items.length % 4 != 0 && row != null) {
            for (int i = items.length % 4; i < 4; i++) {
                View spacer = new View(getContext());
                row.addView(spacer, new LinearLayout.LayoutParams(0, dp(82), 1));
            }
        }
    }

    private View createQuickItemView(QuickItem item) {
        CardView cardView = new CardView(getContext());
        cardView.setCardBackgroundColor(getResources().getColor(R.color.colorSurface));
        cardView.setCardElevation(dp(1));
        cardView.setRadius(dp(7));
        cardView.setUseCompatPadding(false);

        LinearLayout content = new LinearLayout(getContext());
        content.setGravity(android.view.Gravity.CENTER);
        content.setOrientation(LinearLayout.VERTICAL);
        content.setPadding(dp(3), dp(6), dp(3), dp(6));

        ImageView icon = new ImageView(getContext());
        icon.setImageResource(item.iconRes);
        icon.setScaleType(ImageView.ScaleType.FIT_CENTER);
        content.addView(icon, new LinearLayout.LayoutParams(dp(31), dp(31)));

        TextView text = new TextView(getContext());
        text.setText(item.title);
        text.setGravity(android.view.Gravity.CENTER);
        text.setMaxLines(2);
        text.setTextColor(getResources().getColor(R.color.colorText));
        text.setTextSize(13);
        text.setIncludeFontPadding(false);
        LinearLayout.LayoutParams textParams = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        textParams.setMargins(0, dp(5), 0, 0);
        content.addView(text, textParams);

        cardView.addView(content, new CardView.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        cardView.setOnClickListener(v -> startActivity(new Intent(getActivity(), item.targetClass)));

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(0, dp(86), 1);
        params.setMargins(dp(3), dp(3), dp(3), dp(3));
        cardView.setLayoutParams(params);
        return cardView;
    }

    private int dp(int value) {
        return (int) (value * getResources().getDisplayMetrics().density + 0.5f);
    }

    private void showUpdateMenu(View anchor) {
        PopupMenu popupMenu = new PopupMenu(getActivity(), anchor);
        popupMenu.getMenu().add("Cập nhật tất cả");
        popupMenu.getMenu().add("Cập nhật XSMB");
        popupMenu.getMenu().add("Cập nhật XS Đặc Biệt");
        popupMenu.setOnMenuItemClickListener(item -> {
            String title = item.getTitle().toString();
            if (title.equals("Cập nhật tất cả")) {
                confirmUpdateAll();
                return true;
            }
            if (title.equals("Cập nhật XSMB")) {
                confirmUpdateLottery();
                return true;
            }
            if (title.equals("Cập nhật XS Đặc Biệt")) {
                confirmUpdateJackpot();
                return true;
            }
            return false;
        });
        popupMenu.show();
    }

    private void confirmUpdateAll() {
        String title = "Cập nhật tất cả?";
        String message = "Bạn có muốn cập nhật XS Đặc Biệt và XSMB không?";
        DialogBase.showWithConfirmation(getActivity(), title, message, () -> {
            new ThreadBase<>((param) -> {
                updateDataService.updateAllData(true, false);
                return null;
            }, "").start();
        });
    }

    private void confirmUpdateLottery() {
        String title = "Cập nhật XSMB?";
        String message = "Bạn có muốn cập nhật XSMB trong vòng " +
                Const.MAX_DAYS_TO_GET_LOTTERY + " ngày không?";
        DialogBase.showWithConfirmation(getActivity(), title, message, () -> {
            new ThreadBase<>((param) -> {
                updateDataService.updateLottery(Const.MAX_DAYS_TO_GET_LOTTERY,
                        true, false);
                return null;
            }, "").start();
        });
    }

    private void confirmUpdateJackpot() {
        String title = "Cập nhật XS Đặc Biệt?";
        String message = "Bạn có muốn cập nhật XS Đặc Biệt của năm nay không?";
        DialogBase.showWithConfirmation(getActivity(), title, message, () -> {
            new ThreadBase<>((param) -> {
                updateDataService.updateJackpot(true, false);
                return null;
            }, "").start();
        });
    }

    private void showJackpotList(List<Jackpot> jackpotList) {
        if (layoutRecentJackpots != null) {
            layoutRecentJackpots.removeAllViews();
            for (int i = 0; i < 4; i++) {
                Jackpot jackpot = jackpotList != null && jackpotList.size() > i
                        ? jackpotList.get(i) : Jackpot.getEmpty();
                layoutRecentJackpots.addView(createRecentJackpotView(jackpot, i));
            }
            return;
        }

        if (jackpotList == null || jackpotList.isEmpty()) {
            tvJackpotToday.setText("Chưa có dữ liệu Xổ số Đặc Biệt");
            tvJackpotLastDay.setText("");
            return;
        }

        tvJackpotToday.setText(formatJackpotResult(jackpotList.get(0)));
        if (jackpotList.size() > 1) {
            tvJackpotLastDay.setText(formatJackpotResult(jackpotList.get(1)));
        } else {
            tvJackpotLastDay.setText("");
        }
    }

    private String formatJackpotResult(Jackpot jackpot) {
        if (jackpot == null || jackpot.isEmpty()) {
            return "Chưa có dữ liệu Xổ số Đặc Biệt";
        }
        return "Xổ số Đặc Biệt Ngày " + jackpot.getDateBase().showDDMM("/")
                + ": " + jackpot.getJackpot();
    }

    private View createRecentJackpotView(Jackpot jackpot, int index) {
        CardView cardView = new CardView(getContext());
        cardView.setCardBackgroundColor(index == 0
                ? getResources().getColor(R.color.colorLightGreen)
                : getResources().getColor(R.color.colorSurface));
        cardView.setCardElevation(dp(1));
        cardView.setRadius(dp(8));
        cardView.setUseCompatPadding(false);

        LinearLayout content = new LinearLayout(getContext());
        content.setGravity(android.view.Gravity.CENTER);
        content.setOrientation(LinearLayout.VERTICAL);
        content.setPadding(dp(3), dp(6), dp(3), dp(6));

        TextView date = new TextView(getContext());
        date.setText(formatJackpotDate(jackpot, index));
        date.setGravity(android.view.Gravity.CENTER);
        date.setSingleLine(true);
        date.setTextColor(getResources().getColor(R.color.colorPrimaryDark));
        date.setTextSize(10);
        date.setTypeface(Typeface.DEFAULT_BOLD);
        date.setIncludeFontPadding(false);
        content.addView(date, new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));

        TextView number = new TextView(getContext());
        number.setText(formatJackpotNumber(jackpot));
        number.setGravity(android.view.Gravity.CENTER);
        number.setSingleLine(true);
        number.setTextColor(getResources().getColor(R.color.colorTextJackpot));
        number.setTextSize(15);
        number.setTypeface(Typeface.DEFAULT_BOLD);
        number.setIncludeFontPadding(false);
        LinearLayout.LayoutParams numberParams = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        numberParams.setMargins(0, dp(6), 0, dp(5));
        content.addView(number, numberParams);

        TextView tag = new TextView(getContext());
        tag.setText(formatJackpotTag(jackpot, index));
        tag.setGravity(android.view.Gravity.CENTER);
        tag.setSingleLine(true);
        tag.setTextColor(getResources().getColor(R.color.colorPrimary));
        tag.setTextSize(9);
        tag.setIncludeFontPadding(false);
        tag.setPadding(dp(6), dp(3), dp(6), dp(3));
        tag.setBackground(createTagBackground(index));
        content.addView(tag, new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));

        cardView.addView(content, new CardView.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(0, dp(84), 1);
        params.setMargins(dp(3), dp(3), dp(3), dp(3));
        cardView.setLayoutParams(params);
        return cardView;
    }

    private GradientDrawable createTagBackground(int index) {
        GradientDrawable drawable = new GradientDrawable();
        drawable.setColor(getResources().getColor(index == 0 ? R.color.colorSurface : R.color.colorSurfaceSoft));
        drawable.setCornerRadius(dp(10));
        drawable.setStroke(dp(1), getResources().getColor(R.color.colorSectionBorder));
        return drawable;
    }

    private String formatJackpotDate(Jackpot jackpot, int index) {
        if (jackpot == null || jackpot.isEmpty()) {
            return "--/--";
        }
        return (index == 0 ? "\u2022 " : "") + jackpot.getDateBase().showDDMM("/");
    }

    private String formatJackpotNumber(Jackpot jackpot) {
        if (jackpot == null || jackpot.isEmpty()) {
            return "-----";
        }
        return jackpot.getJackpot();
    }

    private String formatJackpotTag(Jackpot jackpot, int index) {
        if (jackpot == null || jackpot.isEmpty()) {
            return "\u0110ang ch\u1edd";
        }
        if (index == 0) {
            return "M\u1edbi nh\u1ea5t";
        }
        return "C\u1eb7p " + formatJackpotCouple(jackpot);
    }

    private String formatJackpotCouple(Jackpot jackpot) {
        String number = jackpot.getJackpot();
        if (number == null || number.length() < 2) {
            return "--";
        }
        return number.substring(number.length() - 2);
    }

    private String formatCalendarText(String fallback) {
        DateData today = DateHandler.getDateDataToday(getActivity());
        DateBase dateBase = today.getDateBase();
        DateLunar dateLunar = today.getDateLunar();

        if (dateBase.isEmpty() || !dateBase.isToday()) {
            dateBase = DateHandler.getDateBase(getActivity());
        }
        if (dateBase.isEmpty() || !dateBase.isToday()) {
            dateBase = DateBase.today();
        }

        String text = getWeekdayText(dateBase) + ", Ngày " + formatDate(dateBase);
        if (today.getDateBase().isToday() && hasLunarDate(dateLunar)) {
            text += "\nNgày " + dateLunar.showDDMM("/") + " Âm Lịch";
        } else if (DateHandler.getDateBase(getActivity()).isToday()
                && fallback != null && !fallback.trim().isEmpty()) {
            text = fallback.replace("\n", " ").replace(" - Âm lịch", " Âm Lịch");
        }
        return text;
    }

    private String getWeekdayText(DateBase dateBase) {
        switch (dateBase.getDayOfWeek()) {
            case Calendar.MONDAY:
                return "Thứ Hai";
            case Calendar.TUESDAY:
                return "Thứ Ba";
            case Calendar.WEDNESDAY:
                return "Thứ Tư";
            case Calendar.THURSDAY:
                return "Thứ Năm";
            case Calendar.FRIDAY:
                return "Thứ Sáu";
            case Calendar.SATURDAY:
                return "Thứ Bảy";
            case Calendar.SUNDAY:
                return "Chủ Nhật";
            default:
                return "Hôm nay";
        }
    }

    private String formatDate(DateBase dateBase) {
        return pad2(dateBase.getDay()) + "/" + pad2(dateBase.getMonth()) + "/" + dateBase.getYear();
    }

    private boolean hasLunarDate(DateLunar dateLunar) {
        return dateLunar != null && dateLunar.getDay() > 0 && dateLunar.getMonth() > 0;
    }

    private String pad2(int value) {
        return value < 10 ? "0" + value : String.valueOf(value);
    }

    @Override
    public void showMessage(String message) {
        Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showLongMessage(String message) {
        Toast.makeText(getActivity(), message, Toast.LENGTH_LONG).show();
    }

    @Override
    public void showTimeData(String time) {
        activity.showTimeData(time);
        // không làm gì vì đã được observe từ MainActivity
    }

    @Override
    public void showJackpotData(List<Jackpot> jackpotList) {
        activity.showJackpotData(jackpotList);
        //activity.getJackpotList().setValue(jackpotList);
    }

    @Override
    public void showLotteryData(List<Lottery> lotteries) {
        activity.showLotteryData(lotteries);
        // ko có observe
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void showNote(String note) {
        // Trang chủ không hiển thị ghi chú.
    }

    private static class QuickItem {
        final String title;
        final Class<?> targetClass;
        final int iconRes;

        QuickItem(String title, Class<?> targetClass, int iconRes) {
            this.title = title;
            this.targetClass = targetClass;
            this.iconRes = iconRes;
        }
    }
}

