package com.example.couple.View.Search;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.couple.Base.View.ActivityBase;
import com.example.couple.R;
import com.example.couple.View.Agent.AgentChatHistoryActivity;
import com.example.couple.View.Agent.AgentModelManageActivity;
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
import com.example.couple.View.JackpotStatistics.YearlyJackpotStatisticsActivity;
import com.example.couple.View.Lottery.LotteryActivity;
import com.example.couple.View.Notification.NewBridgeActivity;
import com.example.couple.View.Notification.NotificationActivity;
import com.example.couple.View.Setting.NotificationSettingActivity;
import com.example.couple.View.SubScreen.CalculatingBalanceCoupleActivity;
import com.example.couple.View.SubScreen.CycleByYearActivity;
import com.example.couple.View.SubScreen.NoteActivity;
import com.example.couple.View.UpdateDataInfo.AddJackpotManyYearsActivity;
import com.example.couple.View.UpdateDataInfo.UrlAndParamsActivity;

import java.text.Normalizer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

public class SearchActivity extends ActivityBase {
    EditText edtSearch;
    LinearLayout linearSearchResults;
    List<SearchShortcut> allShortcuts;
    List<SearchShortcut> defaultShortcuts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        edtSearch = findViewById(R.id.edtSearch);
        linearSearchResults = findViewById(R.id.linearSearchResults);

        initShortcuts();
        bindEvents();
        showShortcuts(defaultShortcuts);
        edtSearch.requestFocus();
    }

    private void initShortcuts() {
        SearchShortcut newBridge = new SearchShortcut("Cầu mới", "Xem các cầu mới đã lưu.",
                NewBridgeActivity.class, "cau moi", "hien cau", "cau hom nay", "cau ngay mai");
        SearchShortcut notificationSetting = new SearchShortcut("Cài đặt thông báo", "Bật tắt thông báo và chọn giờ báo.",
                NotificationSettingActivity.class, "cai dat", "thong bao", "gio bao", "notification");
        SearchShortcut connectedBridge = new SearchShortcut("Cầu liên thông", "Xem cầu liên thông đang chạy.",
                ConnectedBridgeActivity.class, "cau lien thong", "lien thong");
        SearchShortcut afterDoubleBridge = new SearchShortcut("Cầu sau khi ra kép", "Xem cầu sau khi ĐB ra kép.",
                AfterDoubleBridgeActivity.class, "kep", "cau kep", "sau khi ra kep");
        SearchShortcut jackpotHistory = new SearchShortcut("Lịch sử ĐB", "Xem lịch sử XS Đặc Biệt theo năm.",
                JackpotByYearActivity.class, "lich su db", "xsdb", "dac biet", "db");
        SearchShortcut yearlyStatistic = new SearchShortcut("Thống kê ĐB theo năm", "Xem thống kê ĐB theo năm.",
                YearlyJackpotStatisticsActivity.class, "thong ke db theo nam", "thong ke", "theo nam", "db theo nam");

        defaultShortcuts = Arrays.asList(newBridge, notificationSetting, connectedBridge, afterDoubleBridge,
                jackpotHistory, yearlyStatistic);

        allShortcuts = new ArrayList<>(defaultShortcuts);
        allShortcuts.add(new SearchShortcut("XSMB", "Xem kết quả xổ số miền Bắc.",
                LotteryActivity.class, "xsmb", "xo so", "lottery"));
        allShortcuts.add(new SearchShortcut("XS Đặc Biệt", "Xem XS Đặc Biệt theo năm.",
                JackpotByYearActivity.class, "xs dac biet", "xsdb", "db", "dac biet"));
        allShortcuts.add(new SearchShortcut("ĐB hôm sau", "Xem thống kê ĐB hôm sau.",
                JackpotNextDayActivity.class, "db hom sau", "dac biet hom sau", "ngay mai", "hom sau"));
        allShortcuts.add(new SearchShortcut("ĐB theo tuần", "Xem ĐB theo tuần.",
                CoupleByWeekActivity.class, "db theo tuan", "theo tuan", "tuan"));
        allShortcuts.add(new SearchShortcut("BSCB", "Xem bảng số cân bằng.",
                BalanceCoupleActivity.class, "bscb", "bang so can bang", "can bang"));
        allShortcuts.add(new SearchShortcut("Cầu gan", "Xem nhịp chạy và lịch sử bộ số.",
                JackpotNumberSetRhythmActivity.class, "cau gan", "gan", "nhip chay", "bo so"));
        allShortcuts.add(new SearchShortcut("Can chi", "Xem thông tin can chi.",
                SexagenaryCycleActivity.class, "can chi", "can", "chi"));
        allShortcuts.add(new SearchShortcut("Cầu chạm", "Xem các cầu chạm.",
                TouchBridgeActivity.class, "cau cham", "cham"));
        allShortcuts.add(new SearchShortcut("Cầu ánh xạ", "Xem cầu ánh xạ.",
                MappingBridgeActivity.class, "cau anh xa", "anh xa", "mapping"));
        allShortcuts.add(new SearchShortcut("Cầu ước lượng", "Xem cầu ước lượng.",
                EstimatedBridgeActivity.class, "cau uoc luong", "uoc luong"));
        allShortcuts.add(new SearchShortcut("Tổng hợp cầu", "Xem tổng hợp các loại cầu.",
                BridgeCombinationActivity.class, "tong hop cau", "tong hop", "cau tong hop"));
        allShortcuts.add(new SearchShortcut("Thống kê ĐB năm nay", "Xem thống kê ĐB năm nay.",
                CurrentYearJackpotStatisticsActivity.class, "dac biet nam nay", "db nam nay", "nam nay"));
        allShortcuts.add(new SearchShortcut("Nhịp chạy ĐB", "Xem nhịp chạy ĐB theo bộ số.",
                JackpotNumberSetRhythmActivity.class, "nhip chay db", "nhip chay", "bo so"));
        allShortcuts.add(new SearchShortcut("Sửa URL và Param", "Cấu hình URL và tham số lấy dữ liệu.",
                UrlAndParamsActivity.class, "sua url", "param", "url", "cau hinh"));
        allShortcuts.add(new SearchShortcut("Lưu dữ liệu ĐB", "Thêm hoặc lưu dữ liệu ĐB nhiều năm.",
                AddJackpotManyYearsActivity.class, "luu du lieu db", "them du lieu", "du lieu db"));
        allShortcuts.add(new SearchShortcut("Quản lý model", "Cấu hình provider, model và API key cho agent.",
                AgentModelManageActivity.class, "manage models", "model", "api key", "agent settings", "cau hinh agent"));
        allShortcuts.add(new SearchShortcut("Can chi theo năm", "Xem can chi theo năm.",
                CycleByYearActivity.class, "can chi theo nam", "theo nam"));
        allShortcuts.add(new SearchShortcut("Chat bot", "Trò chuyện với agent của app.",
                AgentChatHistoryActivity.class, "chat bot", "agent", "tro chuyen", "ai"));
        allShortcuts.add(new SearchShortcut("Note", "Xem và quản lý ghi chú.",
                NoteActivity.class, "note", "ghi chu"));
        allShortcuts.add(new SearchShortcut("Tính BSCB", "Tính bảng số cân bằng.",
                CalculatingBalanceCoupleActivity.class, "tinh bscb", "tinh can bang", "bscb"));
        allShortcuts.add(new SearchShortcut("Thông báo", "Xem các thông báo đang hiển thị.",
                NotificationActivity.class, "thong bao", "notification"));
    }

    private void bindEvents() {
        edtSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int start, int before, int count) {
                filterShortcuts(charSequence.toString());
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });
    }

    private void filterShortcuts(String query) {
        String normalizedQuery = normalize(query);
        if (normalizedQuery.isEmpty()) {
            showShortcuts(defaultShortcuts);
            return;
        }

        List<SearchShortcut> results = new ArrayList<>();
        for (SearchShortcut shortcut : allShortcuts) {
            if (shortcut.matches(normalizedQuery)) results.add(shortcut);
        }
        showShortcuts(results);
    }

    private void showShortcuts(List<SearchShortcut> shortcuts) {
        linearSearchResults.removeAllViews();

        if (shortcuts.isEmpty()) {
            linearSearchResults.addView(createTextView("Không có kết quả phù hợp.", null), createItemParams());
            return;
        }

        for (SearchShortcut shortcut : shortcuts) {
            linearSearchResults.addView(createShortcutView(shortcut), createItemParams());
        }
    }

    private View createShortcutView(SearchShortcut shortcut) {
        LinearLayout item = new LinearLayout(this);
        item.setOrientation(LinearLayout.VERTICAL);
        item.setBackgroundResource(R.drawable.bg_bridge_combination_panel);
        int padding = getResources().getDimensionPixelSize(R.dimen.padding_10dp);
        item.setPadding(padding, padding, padding, padding);
        item.setOnClickListener(view -> startActivity(new Intent(SearchActivity.this, shortcut.targetActivity)));

        TextView title = createTextView(shortcut.title, R.color.colorPrimary);
        title.setTextSize(17);
        title.setTypeface(null, Typeface.BOLD);

        TextView description = createTextView(shortcut.description, R.color.colorText);
        description.setTextSize(14);

        item.addView(title);
        item.addView(description);
        return item;
    }

    private TextView createTextView(String text, Integer colorId) {
        TextView textView = new TextView(this);
        textView.setText(text);
        textView.setTextSize(15);
        textView.setTextColor(getResources().getColor(colorId == null ? R.color.colorText : colorId));
        return textView;
    }

    private LinearLayout.LayoutParams createItemParams() {
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        params.setMargins(0, 0, 0, getResources().getDimensionPixelSize(R.dimen.margin_5dp));
        return params;
    }

    private static String normalize(String value) {
        String normalized = Normalizer.normalize(value == null ? "" : value, Normalizer.Form.NFD)
                .replaceAll("\\p{InCombiningDiacriticalMarks}+", "")
                .toLowerCase(Locale.US)
                .trim();
        return normalized.replace("đ", "d");
    }

    @Override
    public Context getContext() {
        return this;
    }

    static class SearchShortcut {
        String title;
        String description;
        Class<?> targetActivity;
        List<String> keywords;

        SearchShortcut(String title, String description, Class<?> targetActivity, String... keywords) {
            this.title = title;
            this.description = description;
            this.targetActivity = targetActivity;
            this.keywords = Arrays.asList(keywords);
        }

        boolean matches(String query) {
            if (normalize(title).contains(query) || normalize(description).contains(query)) return true;
            for (String keyword : keywords) {
                if (normalize(keyword).contains(query)) return true;
            }
            return false;
        }
    }
}
