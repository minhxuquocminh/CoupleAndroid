package com.example.couple.View.Couple;

import android.content.Context;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.example.couple.Base.Handler.CoupleBase;
import com.example.couple.Base.Handler.SingleBase;
import com.example.couple.Base.View.WidgetBase;
import com.example.couple.Base.View.ActivityBase;
import com.example.couple.Model.Origin.Jackpot;
import com.example.couple.R;
import com.example.couple.ViewModel.Couple.CoupleByWeekViewModel;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;

public class CoupleByWeekActivity extends ActivityBase implements CoupleByWeekView {
    private static final String[] DAY_HEADERS = {"T2", "T3", "T4", "T5", "T6", "T7", "CN"};

    EditText edtWeekNumber;
    TextView tvGetData;
    LinearLayout linearCoupleByWeek;

    CoupleByWeekViewModel viewModel;

    private static final int WEEK_NUMBER_START = 4;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_couple_by_week);

        edtWeekNumber = findViewById(R.id.edtWeekNumber);
        tvGetData = findViewById(R.id.tvGetData);
        linearCoupleByWeek = findViewById(R.id.linearCoupleByWeek);

        viewModel = new CoupleByWeekViewModel(this, this);
        edtWeekNumber.setText(WEEK_NUMBER_START + "");
        edtWeekNumber.setSelection(edtWeekNumber.getText().length());
        viewModel.getJackpotByWeek(WEEK_NUMBER_START);
        tvGetData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                WidgetBase.hideKeyboard(CoupleByWeekActivity.this);
                String dayNumber = edtWeekNumber.getText().toString().trim();
                if (dayNumber.isEmpty()) {
                    showMessage("Bạn chưa nhập số tuần.");
                } else {
                    viewModel.getJackpotByWeek(Integer.parseInt(dayNumber));
                }
            }
        });
    }

    @Override
    public void showMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showJackpotByWeek(List<Jackpot> jackpotList, int weekNumber) {
        linearCoupleByWeek.removeAllViews();
        if (jackpotList.isEmpty()) {
            showMessage("Không có dữ liệu XS Đặc biệt.");
            return;
        }

        linearCoupleByWeek.addView(getWeekTable(jackpotList, weekNumber));
    }

    private TableLayout getWeekTable(List<Jackpot> jackpotList, int weekNumber) {
        TableLayout tableLayout = new TableLayout(this);
        tableLayout.setStretchAllColumns(true);
        tableLayout.setShrinkAllColumns(true);
        tableLayout.setBackground(WidgetBase.getDrawable(this, R.drawable.border_table));
        tableLayout.setLayoutParams(new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT));
        tableLayout.addView(getHeaderRow());
        tableLayout.addView(getBottomBorder());

        List<Jackpot> sortedJackpots = new ArrayList<>(jackpotList);
        Collections.sort(sortedJackpots, (first, second) ->
                first.getDateBase().compareTo(second.getDateBase()));

        List<Jackpot> weekJackpots = new ArrayList<>();
        int currentWeek = -1;
        int currentYear = -1;
        for (Jackpot jackpot : sortedJackpots) {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(jackpot.getDateBase().toDate());
            int week = calendar.get(Calendar.WEEK_OF_YEAR);
            int year = calendar.get(Calendar.YEAR);
            if (!weekJackpots.isEmpty() && (week != currentWeek || year != currentYear)) {
                tableLayout.addView(getWeekRow(weekJackpots));
                tableLayout.addView(getBottomBorder());
                weekJackpots = new ArrayList<>();
            }
            currentWeek = week;
            currentYear = year;
            weekJackpots.add(jackpot);
        }

        if (!weekJackpots.isEmpty()) {
            tableLayout.addView(getWeekRow(weekJackpots));
            tableLayout.addView(getBottomBorder());
        }

        return tableLayout;
    }

    private TableRow getHeaderRow() {
        TableRow row = new TableRow(this);
        row.setLayoutParams(new TableLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT));
        for (int i = 0; i < DAY_HEADERS.length; i++) {
            row.addView(getHeaderCell(DAY_HEADERS[i], i < DAY_HEADERS.length - 1));
        }
        return row;
    }

    private TableRow getWeekRow(List<Jackpot> jackpotList) {
        TableRow row = new TableRow(this);
        row.setLayoutParams(new TableLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT));

        Jackpot[] jackpotsByDay = new Jackpot[7];
        for (Jackpot jackpot : jackpotList) {
            int dayIndex = getDayIndex(jackpot);
            if (dayIndex >= 0 && dayIndex < 7) {
                jackpotsByDay[dayIndex] = jackpot;
            }
        }

        for (int i = 0; i < jackpotsByDay.length; i++) {
            Jackpot jackpot = jackpotsByDay[i];
            row.addView(jackpot == null ? getEmptyCell(i < jackpotsByDay.length - 1) :
                    getJackpotCell(jackpot, i < jackpotsByDay.length - 1));
        }

        return row;
    }

    private FrameLayout getHeaderCell(String text, boolean showRightBorder) {
        FrameLayout cell = getCellContainer(showRightBorder);
        TextView textView = getTextView(text, 15, R.color.colorPrimary, true);
        textView.setPadding(4, 8, 4, 8);
        cell.addView(textView);
        return cell;
    }

    private FrameLayout getJackpotCell(Jackpot jackpot, boolean showRightBorder) {
        FrameLayout cell = getCellContainer(showRightBorder);
        LinearLayout content = new LinearLayout(this);
        content.setOrientation(LinearLayout.VERTICAL);
        content.setGravity(Gravity.CENTER);
        content.setPadding(2, 6, 2, 6);
        content.setLayoutParams(new FrameLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT,
                Gravity.CENTER));

        content.addView(getTextView(jackpot.getDateBase().showDDMM("/"), 11,
                R.color.colorText, false));
        content.addView(getTextView(jackpot.getCouple().show(), 20,
                R.color.colorTextJackpot, true));
        content.addView(getNegativeShadowRow(jackpot));
        cell.addView(content);

        return cell;
    }

    private LinearLayout getNegativeShadowRow(Jackpot jackpot) {
        LinearLayout row = new LinearLayout(this);
        row.setOrientation(LinearLayout.HORIZONTAL);
        row.setGravity(Gravity.CENTER);
        row.setWeightSum(2);
        row.setLayoutParams(new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT));

        int firstShadow = SingleBase.getNegativeShadow(jackpot.getCouple().getFirst());
        int secondShadow = SingleBase.getNegativeShadow(jackpot.getCouple().getSecond());
        row.addView(getShadowTextView(CoupleBase.showCouple(firstShadow).substring(1)));
        row.addView(getShadowTextView(CoupleBase.showCouple(secondShadow).substring(1)));
        return row;
    }

    private TextView getShadowTextView(String text) {
        TextView textView = getTextView(text, 12, R.color.colorPrimary, true);
        textView.setLayoutParams(new LinearLayout.LayoutParams(
                0,
                ViewGroup.LayoutParams.WRAP_CONTENT,
                1));
        return textView;
    }

    private TextView getTextView(String text, int textSize, int colorId, boolean bold) {
        TextView textView = new TextView(this);
        textView.setText(text);
        textView.setTextSize(textSize);
        textView.setGravity(Gravity.CENTER);
        textView.setTextColor(WidgetBase.getColorId(this, colorId));
        if (bold) {
            textView.setTypeface(android.graphics.Typeface.DEFAULT_BOLD);
        }
        return textView;
    }

    private FrameLayout getEmptyCell(boolean showRightBorder) {
        FrameLayout cell = getCellContainer(showRightBorder);
        cell.setMinimumHeight(84);
        return cell;
    }

    private FrameLayout getCellContainer(boolean showRightBorder) {
        FrameLayout cell = new FrameLayout(this);
        cell.setLayoutParams(getCellLayoutParams());
        if (showRightBorder) {
            View borderRight = new View(this);
            FrameLayout.LayoutParams borderParams = new FrameLayout.LayoutParams(
                    1,
                    FrameLayout.LayoutParams.MATCH_PARENT);
            borderParams.gravity = Gravity.RIGHT;
            borderRight.setLayoutParams(borderParams);
            borderRight.setBackgroundColor(WidgetBase.getColorId(this, R.color.colorDivider));
            cell.addView(borderRight);
        }
        return cell;
    }

    private View getBottomBorder() {
        View borderBottom = new View(this);
        borderBottom.setLayoutParams(new TableLayout.LayoutParams(
                TableLayout.LayoutParams.MATCH_PARENT,
                1));
        borderBottom.setBackgroundColor(WidgetBase.getColorId(this, R.color.colorDivider));
        return borderBottom;
    }

    private int getDayIndex(Jackpot jackpot) {
        int dayOfWeek = jackpot.getDateBase().getDayOfWeek();
        if (dayOfWeek == Calendar.SUNDAY) return 6;
        return dayOfWeek - Calendar.MONDAY;
    }

    private TableRow.LayoutParams getCellLayoutParams() {
        return new TableRow.LayoutParams(
                0,
                ViewGroup.LayoutParams.WRAP_CONTENT,
                1);
    }

    @Override
    public Context getContext() {
        return this;
    }
}
