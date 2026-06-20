package com.example.couple.View.Notification;

import android.content.Context;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.example.couple.Base.Handler.CoupleBase;
import com.example.couple.Base.Handler.NumberBase;
import com.example.couple.Base.Handler.SingleBase;
import com.example.couple.Base.View.ActivityBase;
import com.example.couple.Base.View.WidgetBase;
import com.example.couple.Custom.Const.Const;
import com.example.couple.Custom.Handler.Bridge.AfterDoubleBridgeHandler;
import com.example.couple.Custom.Handler.Bridge.ConnectedBridgeHandler;
import com.example.couple.Custom.Handler.Bridge.CycleBridgeHandler;
import com.example.couple.Custom.Handler.Bridge.EstimatedBridgeHandler;
import com.example.couple.Custom.Handler.Bridge.NumberSetBridgeHandler;
import com.example.couple.Custom.Handler.JackpotHandler;
import com.example.couple.Custom.Handler.LotteryHandler;
import com.example.couple.Model.Bridge.AfterDouble.AfterDoubleCoupleBridge;
import com.example.couple.Model.Bridge.AfterDouble.AfterDoubleCoupleSupport;
import com.example.couple.Model.Bridge.Cycle.BranchInTwoDaysBridge;
import com.example.couple.Model.Bridge.Estimated.EstimatedBridge;
import com.example.couple.Model.Bridge.NumberSet.GeneralNumberSetBridge;
import com.example.couple.Model.Bridge.NumberSet.NumberSetHistory;
import com.example.couple.Model.Bridge.Touch.ConnectedBridge;
import com.example.couple.Model.DateTime.Date.DateBase;
import com.example.couple.Model.Origin.Jackpot;
import com.example.couple.Model.Origin.Lottery;
import com.example.couple.R;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class NewBridgeActivity extends ActivityBase {
    public static final String EXTRA_TARGET_DATE = "TARGET_DATE";
    private static final int MAX_RECENT_BEAT_DISPLAY = 5;
    private static final int MAX_NUMBER_SET_PER_TYPE = 2;

    TextView tvToolbar;
    TextView tvJackpotResult;
    TextView tvEmptyBridge;
    TextView tvNumberSetTitle;
    TextView tvAfterDoubleTitle;
    TextView tvCycleTitle;
    TextView tvSuggestion;
    TextView tvDailyBridgeTitle;
    TextView tvConnectedTitle;
    TextView tvEstimatedTitle;
    LinearLayout linearNumberSetBridge;
    LinearLayout linearAfterDoubleBridge;
    LinearLayout linearCycleBridge;
    LinearLayout linearConnectedBridge;
    LinearLayout linearEstimatedBridge;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_bridge);

        tvToolbar = findViewById(R.id.tvToolbar);
        tvJackpotResult = findViewById(R.id.tvJackpotResult);
        tvEmptyBridge = findViewById(R.id.tvEmptyBridge);
        tvNumberSetTitle = findViewById(R.id.tvNumberSetTitle);
        tvAfterDoubleTitle = findViewById(R.id.tvAfterDoubleTitle);
        tvCycleTitle = findViewById(R.id.tvCycleTitle);
        tvSuggestion = findViewById(R.id.tvSuggestion);
        tvDailyBridgeTitle = findViewById(R.id.tvDailyBridgeTitle);
        tvConnectedTitle = findViewById(R.id.tvConnectedTitle);
        tvEstimatedTitle = findViewById(R.id.tvEstimatedTitle);
        tvEmptyBridge.setTextColor(WidgetBase.getColorId(this, R.color.colorText));
        tvConnectedTitle.setTextColor(WidgetBase.getColorId(this, R.color.colorPrimary));
        tvEstimatedTitle.setTextColor(WidgetBase.getColorId(this, R.color.colorPrimary));
        linearNumberSetBridge = findViewById(R.id.linearNumberSetBridge);
        linearAfterDoubleBridge = findViewById(R.id.linearAfterDoubleBridge);
        linearCycleBridge = findViewById(R.id.linearCycleBridge);
        linearConnectedBridge = findViewById(R.id.linearConnectedBridge);
        linearEstimatedBridge = findViewById(R.id.linearEstimatedBridge);

        showNewBridge();
    }

    private void showNewBridge() {
        List<Jackpot> jackpotList = JackpotHandler.getJackpotListByDays(this, Const.DAY_NUMBER_TO_GET_JACKPOT);
        if (jackpotList.isEmpty()) {
            tvEmptyBridge.setText("Không có dữ liệu XSĐB.");
            tvEmptyBridge.setVisibility(View.VISIBLE);
            return;
        }

        DateBase targetDate = getTargetDate(jackpotList);
        int jackpotDayNumberBefore = getDayNumberBefore(jackpotList, targetDate);
        List<Lottery> lotteries = LotteryHandler.getLotteryListFromFile(this, Const.MAX_DAYS_TO_GET_LOTTERY);
        int lotteryDayNumberBefore = getLotteryDayNumberBefore(lotteries, targetDate);
        tvToolbar.setText("Cầu cho ngày " + targetDate.showFullChars());

        showJackpotResult(jackpotList, targetDate);

        int mainBridgeCount = 0;
        mainBridgeCount += showNumberSetBridge(jackpotList, jackpotDayNumberBefore) ? 1 : 0;
        mainBridgeCount += showAfterDoubleBridge(jackpotList, jackpotDayNumberBefore) ? 1 : 0;
        mainBridgeCount += showCycleBridge(jackpotList, jackpotDayNumberBefore) ? 1 : 0;
        boolean hasDailyBridge = showConnectedBridge(lotteries, lotteryDayNumberBefore)
                | showEstimatedBridge(jackpotList, jackpotDayNumberBefore);

        tvEmptyBridge.setVisibility(mainBridgeCount == 0 ? View.VISIBLE : View.GONE);
        tvSuggestion.setVisibility(mainBridgeCount == 0 ? View.GONE : View.VISIBLE);
        tvDailyBridgeTitle.setVisibility(View.GONE);
    }

    private DateBase getTargetDate(List<Jackpot> jackpotList) {
        String targetDateText = getIntent().getStringExtra(EXTRA_TARGET_DATE);
        if (targetDateText != null && !targetDateText.trim().isEmpty()) {
            DateBase targetDate = DateBase.fromString(targetDateText.trim(), "-");
            if (!targetDate.isEmpty()) return targetDate;
        }
        return jackpotList.get(0).getDateBase().addDays(1);
    }

    private int getDayNumberBefore(List<Jackpot> jackpotList, DateBase targetDate) {
        DateBase nextDate = jackpotList.get(0).getDateBase().addDays(1);
        if (nextDate.equals(targetDate)) return 0;
        for (int i = 0; i < jackpotList.size(); i++) {
            if (jackpotList.get(i).getDateBase().equals(targetDate)) return i + 1;
        }
        return 0;
    }

    private int getLotteryDayNumberBefore(List<Lottery> lotteries, DateBase targetDate) {
        if (lotteries.isEmpty()) return 0;
        DateBase nextDate = lotteries.get(0).getDateBase().addDays(1);
        if (nextDate.equals(targetDate)) return 0;
        for (int i = 0; i < lotteries.size(); i++) {
            if (lotteries.get(i).getDateBase().equals(targetDate)) return i + 1;
        }
        return 0;
    }

    private void showJackpotResult(List<Jackpot> jackpotList, DateBase targetDate) {
        for (Jackpot jackpot : jackpotList) {
            if (jackpot.getDateBase().equals(targetDate)) {
                tvJackpotResult.setText("Kết quả đã ra: " + jackpot.getJackpot());
                tvJackpotResult.setVisibility(View.VISIBLE);
                return;
            }
        }
        tvJackpotResult.setText("Kết quả đã ra: chưa có");
        tvJackpotResult.setVisibility(View.VISIBLE);
    }

    private boolean showNumberSetBridge(List<Jackpot> jackpotList, int dayNumberBefore) {
        List<NumberSetHistory> histories = getNumberSetHistories(jackpotList, dayNumberBefore);
        if (histories.isEmpty()) {
            hideSection(tvNumberSetTitle, linearNumberSetBridge);
            return false;
        }

        showSection(tvNumberSetTitle, linearNumberSetBridge);
        for (NumberSetHistory history : histories) {
            addCopyRow(
                    linearNumberSetBridge,
                    showNumberSetHistoryDetail(history),
                    toCopyNumbers(history.getNumberSet().getNumbers()),
                    "number_set_bridge"
            );
        }
        return true;
    }

    private List<NumberSetHistory> getNumberSetHistories(List<Jackpot> jackpotList, int dayNumberBefore) {
        int newIndex = Math.min(Math.max(dayNumberBefore, 0), jackpotList.size());
        List<Jackpot> checkList = dayNumberBefore <= 0 ? jackpotList : jackpotList.subList(newIndex, jackpotList.size());
        GeneralNumberSetBridge bridge = NumberSetBridgeHandler.getGeneralNumberSetBridges(checkList);
        List<NumberSetHistory> histories = new ArrayList<>();
        for (List<NumberSetHistory> typeHistories : bridge.getHistoriesByType().values()) {
            histories.addAll(typeHistories.stream()
                    .limit(MAX_NUMBER_SET_PER_TYPE)
                    .collect(Collectors.toList()));
        }
        return histories;
    }

    private String showNumberSetHistoryDetail(NumberSetHistory history) {
        return history.getNumberSet().getName()
                + " (" + history.getDayNumberBefore() + " ngày)"
                + " (" + history.getAppearanceTimes() + " lần)"
                + "\n5 nhịp cuối: " + showRecentBeats(history.getBeatList());
    }

    private boolean showAfterDoubleBridge(List<Jackpot> jackpotList, int dayNumberBefore) {
        AfterDoubleCoupleBridge bridge = AfterDoubleBridgeHandler.getAfterDoubleCoupleBridge(jackpotList, dayNumberBefore);
        if (bridge.isEmpty()) {
            hideSection(tvAfterDoubleTitle, linearAfterDoubleBridge);
            return false;
        }

        showSection(tvAfterDoubleTitle, linearAfterDoubleBridge);
        List<Integer> allNumbers = new ArrayList<>();
        for (AfterDoubleCoupleSupport support : bridge.getSupports()) {
            int number = support.getPredictedCouple().getInt();
            int reverseNumber = CoupleBase.reverse(number);
            addIfAbsent(allNumbers, number);
            addIfAbsent(allNumbers, reverseNumber);
        }
        if (bridge.getSupports().size() > 1) {
            addCopyRow(linearAfterDoubleBridge, "Tất cả các số", toCopyNumbers(allNumbers), "after_double_bridge_all");
        }
        for (AfterDoubleCoupleSupport support : bridge.getSupports()) {
            int number = support.getPredictedCouple().getInt();
            int reverseNumber = CoupleBase.reverse(number);
            String copy = reverseNumber == number
                    ? CoupleBase.showCouple(number)
                    : CoupleBase.showCouple(number) + " " + CoupleBase.showCouple(reverseNumber);
            addCopyRow(linearAfterDoubleBridge, support.show().trim(), copy, "after_double_bridge");
        }
        return true;
    }

    private boolean showCycleBridge(List<Jackpot> jackpotList, int dayNumberBefore) {
        if (dayNumberBefore >= jackpotList.size()) {
            hideSection(tvCycleTitle, linearCycleBridge);
            return false;
        }
        BranchInTwoDaysBridge bridge = CycleBridgeHandler.getBranchInTwoDaysBridge(jackpotList, dayNumberBefore);
        if (bridge.getRunningTimes() == 0) {
            hideSection(tvCycleTitle, linearCycleBridge);
            return false;
        }

        showSection(tvCycleTitle, linearCycleBridge);
        String info = "Đã chạy " + bridge.getRunningTimes() + " ngày liên tiếp\n"
                + "Thông tin: " + bridge.showCompactInfo() + "\n"
                + "Dàn số: " + NumberBase.showNumbers(bridge.getNumbers(), 2, ", ");
        addCopyRow(linearCycleBridge, info, toCopyNumbers(bridge.getNumbers()), "cycle_bridge");
        return true;
    }

    private boolean showConnectedBridge(List<Lottery> lotteries, int dayNumberBefore) {
        ConnectedBridge bridge = ConnectedBridgeHandler.getConnectedBridge(lotteries, dayNumberBefore,
                Const.CONNECTED_BRIDGE_FINDING_DAYS, Const.CONNECTED_BRIDGE_MAX_DISPLAY);
        if (bridge.isEmpty() || bridge.getTouches().isEmpty()) {
            hideSection(tvConnectedTitle, linearConnectedBridge);
            return false;
        }

        showSection(tvConnectedTitle, linearConnectedBridge);
        String info = "4 chạm trọng tâm: " + SingleBase.showTouches(bridge.getTouches(), ", ");
        addCopyRow(linearConnectedBridge, info, toCopyNumbers(bridge.getNumbers()), "connected_bridge");
        return true;
    }

    private boolean showEstimatedBridge(List<Jackpot> jackpotList, int dayNumberBefore) {
        EstimatedBridge bridge = EstimatedBridgeHandler.getEstimatedBridge(jackpotList, dayNumberBefore);
        if (bridge.isEmpty()) {
            hideSection(tvEstimatedTitle, linearEstimatedBridge);
            return false;
        }

        showSection(tvEstimatedTitle, linearEstimatedBridge);
        String info = "Số trọng tâm: " + bridge.showCompactInfo()
                + "\nTổng số: " + bridge.getNumbers().size() + " số";
        addCopyRow(linearEstimatedBridge, info, toCopyNumbers(bridge.getNumbers()), "estimated_bridge");
        return true;
    }

    private void addCopyRow(LinearLayout container, String info, String copyText, String label) {
        TableLayout tableLayout = getCopyTable(container);
        TableRow row = new TableRow(this);
        row.setGravity(Gravity.CENTER_VERTICAL);

        FrameLayout infoCell = new FrameLayout(this);
        infoCell.addView(getInfoTextView(info));
        infoCell.addView(getRightBorder());
        row.addView(infoCell, new TableRow.LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT, 1));

        FrameLayout copyCell = new FrameLayout(this);
        copyCell.addView(getCopyButton(copyText, label));
        row.addView(copyCell, new TableRow.LayoutParams(dp(42), ViewGroup.LayoutParams.MATCH_PARENT));

        tableLayout.addView(row, new TableLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
        ));
        tableLayout.addView(getBottomBorder());
    }

    private TextView getInfoTextView(String info) {
        TextView tvInfo = new TextView(this);
        tvInfo.setText(info);
        tvInfo.setTextColor(getResources().getColor(R.color.colorText));
        tvInfo.setTextSize(15);
        tvInfo.setLineSpacing(3, 1);
        tvInfo.setGravity(Gravity.CENTER_VERTICAL);
        tvInfo.setPadding(dp(6), dp(5), dp(6), dp(5));
        tvInfo.setLayoutParams(new FrameLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT,
                Gravity.CENTER_VERTICAL
        ));
        return tvInfo;
    }

    private ImageButton getCopyButton(String copyText, String label) {
        ImageButton imgCopy = new ImageButton(this);
        imgCopy.setImageResource(R.drawable.ic_copy);
        imgCopy.setBackgroundResource(android.R.color.transparent);
        imgCopy.setPadding(dp(5), dp(5), dp(5), dp(5));
        imgCopy.setScaleType(ImageButton.ScaleType.CENTER_INSIDE);
        imgCopy.setContentDescription("Copy");
        imgCopy.setOnClickListener(v -> {
            WidgetBase.copyToClipboard(NewBridgeActivity.this, label, copyText);
            Toast.makeText(NewBridgeActivity.this, "Đã copy.", Toast.LENGTH_SHORT).show();
        });
        imgCopy.setLayoutParams(new FrameLayout.LayoutParams(
                dp(32),
                dp(32),
                Gravity.CENTER
        ));
        return imgCopy;
    }

    private View getRightBorder() {
        View borderRight = new View(this);
        FrameLayout.LayoutParams borderParams = new FrameLayout.LayoutParams(
                1, FrameLayout.LayoutParams.MATCH_PARENT);
        borderParams.gravity = Gravity.END;
        borderRight.setLayoutParams(borderParams);
        borderRight.setBackgroundColor(WidgetBase.getColorId(this, R.color.colorDivider));
        return borderRight;
    }

    private View getBottomBorder() {
        View borderBottom = new View(this);
        borderBottom.setLayoutParams(new TableLayout.LayoutParams(
                TableLayout.LayoutParams.MATCH_PARENT, 1));
        borderBottom.setBackgroundColor(WidgetBase.getColorId(this, R.color.colorDivider));
        return borderBottom;
    }

    private TableLayout getCopyTable(LinearLayout container) {
        if (container.getChildCount() > 0 && container.getChildAt(0) instanceof TableLayout) {
            return (TableLayout) container.getChildAt(0);
        }

        TableLayout tableLayout = new TableLayout(this);
        tableLayout.setShrinkAllColumns(true);
        tableLayout.setBackgroundResource(R.drawable.border_table);
        container.addView(tableLayout, new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
        ));
        return tableLayout;
    }

    private void showSection(TextView title, LinearLayout container) {
        title.setVisibility(View.VISIBLE);
        container.setVisibility(View.VISIBLE);
        container.removeAllViews();
    }

    private void hideSection(TextView title, LinearLayout container) {
        title.setVisibility(View.GONE);
        container.setVisibility(View.GONE);
        container.removeAllViews();
    }

    private String showRecentBeats(List<Integer> beatList) {
        if (beatList.isEmpty()) return "";
        int fromIndex = Math.max(0, beatList.size() - MAX_RECENT_BEAT_DISPLAY);
        return NumberBase.showNumbers(beatList.subList(fromIndex, beatList.size()), ", ");
    }

    private String toCopyNumbers(List<Integer> numbers) {
        List<Integer> sortedNumbers = new ArrayList<>(numbers);
        Collections.sort(sortedNumbers);
        return NumberBase.showNumbers(sortedNumbers, 2, " ");
    }

    private void addIfAbsent(List<Integer> numbers, int number) {
        if (!numbers.contains(number)) numbers.add(number);
    }

    private int dp(int value) {
        return (int) (value * getResources().getDisplayMetrics().density + 0.5f);
    }

    @Override
    public Context getContext() {
        return this;
    }
}
