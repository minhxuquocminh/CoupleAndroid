package com.example.couple.View.Main.NumberPicker;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;

import com.example.couple.Base.Handler.CoupleBase;
import com.example.couple.Base.Handler.StorageBase;
import com.example.couple.Base.View.DialogBase;
import com.example.couple.Base.View.Table.TableLayoutBase;
import com.example.couple.Base.View.WidgetBase;
import com.example.couple.Custom.Const.Const;
import com.example.couple.Custom.Const.IdStart;
import com.example.couple.Custom.Const.TimeInfo;
import com.example.couple.Custom.Enum.StorageType;
import com.example.couple.Custom.Handler.Display.ArrayUtil;
import com.example.couple.Custom.Handler.Display.MatrixUtil;
import com.example.couple.Custom.Handler.JackpotHandler;
import com.example.couple.Model.Bridge.Estimated.PeriodHistory;
import com.example.couple.Model.Handler.Priority;
import com.example.couple.Model.Origin.Jackpot;
import com.example.couple.R;
import com.example.couple.View.Couple.BalanceCoupleActivity;
import com.example.couple.View.JackpotStatistics.JackpotByYearActivity;
import com.example.couple.View.JackpotStatistics.JackpotNextDayActivity;
import com.example.couple.View.Main.MainActivity;
import com.example.couple.ViewModel.Main.NumberPicker.NumberPickerViewModel;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;


public class NumberPickerFragment extends Fragment implements NumberPickerView {
    TextView tvViewHistory;
    TextView tvBalanceCouple;
    TextView tvJackpotByYear;
    TextView tvCreateNumberArray;
    LinearLayout linearSubCoupleTable;
    CheckBox cboSavedList;
    CheckBox cboNumberPicker;
    CheckBox cboTableA;
    CheckBox cboTableB;
    TextView tvShowPrediction;
    TextView tvNumberCounter;
    ImageView imgClear;
    ImageView imgSave;
    ImageView imgExport;
    LinearLayout linearTableList;
    TextView tvTableA;
    ImageView imgCancelA;
    ImageView imgExportA;
    TextView tvTableB;
    ImageView imgCancelB;
    ImageView imgExportB;
    HorizontalScrollView hsNumberTable;
    HorizontalScrollView hsFilteredNumberTable;
    HorizontalScrollView hsChooseHeadTailTable;
    HorizontalScrollView hsTableToChooseNumber;

    View viewParent;

    NumberPickerViewModel viewModel;

    boolean isFirstNumberPicker = true;
    String listMP = "";

    Drawable greenDrawable;
    Drawable redDrawable;
    Drawable defaultDrawable;

    // for table type 2, matrix dùng để lưu các danh sách số bị phân mảnh và đếm số đc chọn
    int[] pickerMatrix = new int[Const.MAX_ROW_COUNT_TABLE];

    MainActivity activity;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        activity = (MainActivity) context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        viewParent = inflater.inflate(R.layout.fragment_number_picker, container, false);

        tvViewHistory = viewParent.findViewById(R.id.tvViewHistory);
        tvBalanceCouple = viewParent.findViewById(R.id.tvBalanceCouple);
        tvJackpotByYear = viewParent.findViewById(R.id.tvJackpotByYear);
        tvCreateNumberArray = viewParent.findViewById(R.id.tvCreateNumberArray);

        linearSubCoupleTable = viewParent.findViewById(R.id.linearSubCoupleTable);
        cboSavedList = viewParent.findViewById(R.id.cboSavedList);
        cboNumberPicker = viewParent.findViewById(R.id.cboNumberPicker);
        cboTableA = viewParent.findViewById(R.id.cboTableA);
        cboTableB = viewParent.findViewById(R.id.cboTableB);
        tvShowPrediction = viewParent.findViewById(R.id.tvShowPrediction);
        tvNumberCounter = viewParent.findViewById(R.id.tvNumberCounter);
        imgClear = viewParent.findViewById(R.id.imgClear);
        imgSave = viewParent.findViewById(R.id.imgSave);
        imgExport = viewParent.findViewById(R.id.imgExport);

        linearTableList = viewParent.findViewById(R.id.linearTableList);
        tvTableA = viewParent.findViewById(R.id.tvTableA);
        imgCancelA = viewParent.findViewById(R.id.imgCancelA);
        imgExportA = viewParent.findViewById(R.id.imgExportA);
        tvTableB = viewParent.findViewById(R.id.tvTableB);
        imgCancelB = viewParent.findViewById(R.id.imgCancelB);
        imgExportB = viewParent.findViewById(R.id.imgExportB);

        hsNumberTable = viewParent.findViewById(R.id.hsNumberTable);
        hsFilteredNumberTable = viewParent.findViewById(R.id.hsFilteredNumberTable);
        hsChooseHeadTailTable = viewParent.findViewById(R.id.hsChooseHeadTailTable);
        hsTableToChooseNumber = viewParent.findViewById(R.id.hsTableToChooseNumber);

        activity.getJackpotList().observe(getViewLifecycleOwner(), new Observer<List<Jackpot>>() {
            @Override
            public void onChanged(List<Jackpot> jackpotList) {
                showJackpotList(jackpotList);
            }
        });

        activity.getCouplesToTransfer().observe(getViewLifecycleOwner(), new Observer<List<Integer>>() {
            @Override
            public void onChanged(List<Integer> couples) {
                if (!isFirstNumberPicker) {
                    showMessage("Dữ liệu chỉ nên nạp vào bảng chọn số K1 rồi lưu để dùng lại!");
                } else {
                    if (!couples.isEmpty()) {
                        showTableType1(couples, new ArrayList<>());
                        Toast.makeText(getActivity(), "Đã nạp dữ liệu!", Toast.LENGTH_LONG).show();
                    }
                }
            }
        });

        defaultDrawable = null;
        greenDrawable = WidgetBase.getDrawable(getActivity(), R.color.colorLightGreen);
        redDrawable = WidgetBase.getDrawable(getActivity(), R.color.colorImportantText);

        viewModel = new NumberPickerViewModel(this, getActivity());

        onFirstNumberPickerIsSelected();

        tvViewHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewModel.getPeriodHistory();
            }
        });

        tvBalanceCouple.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), BalanceCoupleActivity.class));
            }
        });

        tvJackpotByYear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), JackpotByYearActivity.class));
            }
        });

        tvCreateNumberArray.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), JackpotNextDayActivity.class));
            }
        });

        cboSavedList.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    saveListIsChecked();
                } else {
                    if (!cboNumberPicker.isChecked()) {
                        hideAllContentIsSelected();
                    }
                }
            }
        });

        cboNumberPicker.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    if (isFirstNumberPicker) {
                        onFirstNumberPickerIsSelected();
                    } else {
                        onSecondNumberPickerIsSelected();
                    }
                } else {
                    if (!cboSavedList.isChecked()) {
                        hideAllContentIsSelected();
                    }
                }
            }
        });

        cboNumberPicker.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                String newType = isFirstNumberPicker ? "2" : "1";
                String title = "Chuyển kiểu chọn?";
                String message = "Bạn có muốn chuyển kiểu chọn số sang kiểu " + newType + " không?";
                DialogBase.showWithConfirmation(getActivity(), title, message, () -> {
                    if (isFirstNumberPicker) {
                        onSecondNumberPickerIsSelected();
                    } else {
                        onFirstNumberPickerIsSelected();
                    }
                });
                return false;
            }
        });

        cboTableA.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    cboTableB.setChecked(false);
                    viewModel.getSavedNumbers(isFirstNumberPicker, true);
                } else {
                    if (!cboTableB.isChecked()) {
                        hideAllContentIsSelected();
                    }
                }
            }
        });

        cboTableB.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    cboTableA.setChecked(false);
                    viewModel.getSavedNumbers(isFirstNumberPicker, false);
                } else {
                    if (!cboTableA.isChecked()) {
                        hideAllContentIsSelected();
                    }
                }
            }
        });

        imgClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isFirstNumberPicker) {
                    showTableType1(new ArrayList<>(), new ArrayList<>());
                } else {
                    showTableType2(new ArrayList<>(), new ArrayList<>());
                }
            }
        });

        imgSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tableName = cboTableA.isChecked() ? "A" : "B";
                String title = "Lưu?";
                String message = "Bạn có muốn lưu dữ liệu vào bảng " + tableName + " không?";
                DialogBase.showWithConfirmation(getActivity(), title, message, () -> {
                    List<Integer> normals = IntStream.range(0, Const.MAX_ROW_COUNT_TABLE)
                            .filter(i -> pickerMatrix[i] == Priority.NORMAL.getLevel()).boxed().collect(Collectors.toList());
                    List<Integer> imports = IntStream.range(0, Const.MAX_ROW_COUNT_TABLE)
                            .filter(i -> pickerMatrix[i] == Priority.IMPORTANT.getLevel()).boxed().collect(Collectors.toList());
                    viewModel.saveDataToFile(normals, imports, cboTableA.isChecked());
                });
            }
        });

        imgExport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String data = IntStream.range(0, Const.MAX_ROW_COUNT_TABLE)
                        .filter(i -> pickerMatrix[i] != Priority.NONE.getLevel())
                        .mapToObj(CoupleBase::showCouple).collect(Collectors.joining(" "));
                String message = "Bạn có muốn xuất dữ liệu ra clipboard không?";
                DialogBase.showWithCopiedText(getActivity(), "Xuất?", message, data, "dữ liệu");
            }
        });

        return viewParent;
    }

    private void hideAllContentIsSelected() {
        isFirstNumberPicker = false;

        cboSavedList.setChecked(false);
        cboNumberPicker.setChecked(false);
        cboTableA.setChecked(false);
        cboTableB.setChecked(false);

        cboTableA.setVisibility(View.GONE);
        cboTableB.setVisibility(View.GONE);
        tvShowPrediction.setVisibility(View.GONE);
        tvNumberCounter.setVisibility(View.GONE);
        imgClear.setVisibility(View.GONE);
        imgSave.setVisibility(View.GONE);
        imgExport.setVisibility(View.GONE);

        hsNumberTable.setVisibility(View.GONE);
        hsChooseHeadTailTable.setVisibility(View.GONE);

        hsFilteredNumberTable.setVisibility(View.GONE);
        hsTableToChooseNumber.setVisibility(View.GONE);

        linearTableList.setVisibility(View.GONE);
    }

    private void saveListIsChecked() {
        isFirstNumberPicker = false;
        cboNumberPicker.setChecked(false);
        viewModel.getTableAList();
        viewModel.getTableBList();

        cboTableA.setVisibility(View.GONE);
        cboTableB.setVisibility(View.GONE);
        tvShowPrediction.setVisibility(View.GONE);
        tvNumberCounter.setVisibility(View.GONE);
        imgClear.setVisibility(View.GONE);
        imgSave.setVisibility(View.GONE);
        imgExport.setVisibility(View.GONE);

        hsNumberTable.setVisibility(View.GONE);
        hsChooseHeadTailTable.setVisibility(View.GONE);

        hsFilteredNumberTable.setVisibility(View.GONE);
        hsTableToChooseNumber.setVisibility(View.GONE);

        linearTableList.setVisibility(View.VISIBLE);
    }

    private void onFirstNumberPickerIsSelected() {
        isFirstNumberPicker = true;
        cboNumberPicker.setText("Bảng chọn số K1");
        cboSavedList.setChecked(false);
        cboNumberPicker.setChecked(true);
        cboTableA.setChecked(true);
        viewModel.getSavedNumbers(isFirstNumberPicker, true);

        cboTableA.setVisibility(View.VISIBLE);
        cboTableB.setVisibility(View.VISIBLE);
        tvShowPrediction.setVisibility(View.VISIBLE);
        tvNumberCounter.setVisibility(View.VISIBLE);
        imgClear.setVisibility(View.VISIBLE);
        imgSave.setVisibility(View.VISIBLE);
        imgExport.setVisibility(View.VISIBLE);

        hsNumberTable.setVisibility(View.VISIBLE);
        hsChooseHeadTailTable.setVisibility(View.VISIBLE);

        hsFilteredNumberTable.setVisibility(View.GONE);
        hsTableToChooseNumber.setVisibility(View.GONE);

        linearTableList.setVisibility(View.GONE);
    }

    private void onSecondNumberPickerIsSelected() {
        isFirstNumberPicker = false;
        cboNumberPicker.setText("Bảng chọn số K2");
        cboSavedList.setChecked(false);
        cboNumberPicker.setChecked(true);
        cboTableA.setChecked(true);
        viewModel.getSavedNumbers(isFirstNumberPicker, true);

        cboTableA.setVisibility(View.VISIBLE);
        cboTableB.setVisibility(View.VISIBLE);
        tvShowPrediction.setVisibility(View.VISIBLE);
        tvNumberCounter.setVisibility(View.VISIBLE);
        imgClear.setVisibility(View.VISIBLE);
        imgSave.setVisibility(View.VISIBLE);
        imgExport.setVisibility(View.VISIBLE);

        hsFilteredNumberTable.setVisibility(View.VISIBLE);
        hsTableToChooseNumber.setVisibility(View.VISIBLE);

        hsNumberTable.setVisibility(View.GONE);
        hsChooseHeadTailTable.setVisibility(View.GONE);

        linearTableList.setVisibility(View.GONE);
    }

    @Override
    public void showMessage(String message) {
        Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showPeriodHistory(List<PeriodHistory> periodHistoryList) {
        String show = "Lịch sử các cách chạy gần giống khoảng gần đây:\n";
        for (PeriodHistory periodHistory : periodHistoryList) {
            show += periodHistory.show() + "\n";
        }
        DialogBase.showBasic(getActivity(), "Lịch sử", show);
    }

    boolean isLess2Years = true;

    private void showJackpotList(List<Jackpot> jackpotList) {
        viewModel.getSubJackpotTable(jackpotList);
        isLess2Years = checkJackpotDataLessThanTwoYears();
    }

    private boolean checkJackpotDataLessThanTwoYears() {
        int currentYear = TimeInfo.CURRENT_YEAR;
        List<Integer> years = JackpotHandler.getUpdatedYears(requireActivity());
        return !years.contains(currentYear) || !years.contains(currentYear - 1);
    }

    @Override
    public void onResume() {
        super.onResume();
        List<Jackpot> jackpotList = activity.getJackpotList().getValue();
        if (jackpotList == null || jackpotList.isEmpty()) return;
        if (isLess2Years && !checkJackpotDataLessThanTwoYears()) {
            viewModel.getSubJackpotTable(jackpotList);
        }
    }

    private void onPickNumber(int number) {
        if (listMP.isEmpty()) {
            tvShowPrediction.setText("m: ");
        } else {
            String mpStr = CoupleBase.showCouple(number);
            if (mpStr.isEmpty()) {
                mpStr = "??";
            }
            for (int i = 0; i < 3; i++) {
                TextView textView = viewParent.findViewById(IdStart.MY_PREDICTION + i);
                textView.setText(mpStr);
            }
            tvShowPrediction.setVisibility(View.VISIBLE);
            tvShowPrediction.setText("m: " + mpStr + ", " + listMP);
            StorageBase.setNumber(requireActivity(), StorageType.NUMBER_OF_PICKER, number < 0 ? Const.EMPTY_VALUE : number);
        }
    }

    @Override
    public void showSubJackpotTable(List<Jackpot> jackpotsLastWeek, List<Jackpot> jackpotsNextDay, List<Jackpot> subJackpots) {
        String[][] matrix = new String[5][3];
        Integer[][] idMatrix = new Integer[5][3];
        for (int i = 0; i < 3; i++) {
            idMatrix[4][i] = IdStart.MY_PREDICTION + i;
        }
        int count = 0;
        for (int i = 3; i >= 0; i--) {
            matrix[i][0] = count < jackpotsLastWeek.size() ? jackpotsLastWeek.get(count).getCouple().show() : "";
            matrix[i][1] = count < jackpotsNextDay.size() ? jackpotsNextDay.get(count).getCouple().show() : "";
            matrix[i][2] = count < subJackpots.size() ? subJackpots.get(count).getCouple().show() : "";
            count++;
        }
        linearSubCoupleTable.removeAllViews();
        linearSubCoupleTable.addView(TableLayoutBase.getCoupleTableLayout(getActivity(), matrix, idMatrix, 5, 3, true));
        listMP = subJackpots.stream().map(x -> x.getCouple().show()).collect(Collectors.joining(", "));
        onPickNumber(-1);
    }

    @Override
    public void showTableType1(List<Integer> normals, List<Integer> imports) {
        hsNumberTable.removeAllViews();
        hsNumberTable.addView(TableLayoutBase.getPickerTableLayout(getActivity(), MatrixUtil.getTenTenStringMatrix(),
                MatrixUtil.getTenTenIntMatrix(IdStart.PICKER), 10, 10, false));
        hsChooseHeadTailTable.removeAllViews();
        String[][] matrix = new String[2][10];
        Integer[][] idMatrix = new Integer[2][10];
        IntStream.range(0, 10).forEach(i -> {
            matrix[0][i] = i + "x";
            matrix[1][i] = "x" + i;
            idMatrix[0][i] = IdStart.HEAD + i;
            idMatrix[1][i] = IdStart.TAIL + i;
        });
        hsChooseHeadTailTable.addView(TableLayoutBase.getPickerTableLayout(getActivity(), matrix, idMatrix, 2, 10, false));

        setStartMatrix(normals, imports);
        setColorForTextViewType1(normals, imports);
        setOnClickForTextViewType1();
        setOnClickForHeadTail();
        setCounter();
    }

    private void setStartMatrix(List<Integer> normals, List<Integer> imports) {
        pickerMatrix = new int[Const.MAX_ROW_COUNT_TABLE];
        normals.forEach(number -> pickerMatrix[number] = Priority.NORMAL.getLevel());
        imports.forEach(number -> pickerMatrix[number] = Priority.IMPORTANT.getLevel());
    }

    private void setColorForTextViewType1(List<Integer> normals, List<Integer> imports) {
        normals.forEach(number -> {
            TextView textView = viewParent.findViewById(IdStart.PICKER + number);
            textView.setBackground(greenDrawable);
        });
        imports.forEach(number -> {
            TextView textView = viewParent.findViewById(IdStart.PICKER + number);
            textView.setBackground(redDrawable);
        });
    }

    private void setOnClickForTextViewType1() {
        // từ pink nếu nhấn => xanh, nhấn giữ => đỏ
        // từ xanh nếu nhấn => pink, nhấn giữ => đỏ
        // từ đỏ nếu nhấn => pink, nhấn giữ => pink
        for (int i = 0; i < Const.MAX_ROW_COUNT_TABLE; i++) {
            TextView textView = viewParent.findViewById(IdStart.PICKER + i);
            int number = i;
            textView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (textView.getBackground() == greenDrawable) {
                        textView.setBackground(defaultDrawable);
                        onPickNumber(-1);
                        setCounter();
                        pickerMatrix[number] = Priority.NONE.getLevel();
                    } else {
                        textView.setBackground(greenDrawable);
                        onPickNumber(number);
                        setCounter();
                        pickerMatrix[number] = Priority.NORMAL.getLevel();
                    }
                }
            });
            textView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    String title = "Hành động.";
                    String mess = textView.getBackground() == redDrawable ? "Bạn muốn hủy số đặc biệt " + number +
                            " không?" : "Bạn có muốn chọn số " + number + " làm số đặc biệt không?";
                    DialogBase.showWithConfirmation(getActivity(), title, mess, () -> {
                        if (textView.getBackground() == redDrawable) {
                            textView.setBackground(defaultDrawable);
                            onPickNumber(-1);
                            setCounter();
                            pickerMatrix[number] = Priority.NONE.getLevel();
                        } else {
                            textView.setBackground(redDrawable);
                            onPickNumber(number);
                            setCounter();
                            pickerMatrix[number] = Priority.IMPORTANT.getLevel();
                        }
                    });
                    return false;
                }
            });
        }
    }

    private void setOnClickForHeadTail() {
        for (int i = 0; i < 10; i++) {
            TextView tvHead = viewParent.findViewById(IdStart.HEAD + i);
            int finalI = i;
            tvHead.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    for (int j = 0; j < 10; j++) {
                        TextView tv = viewParent.findViewById(IdStart.PICKER + finalI * 10 + j);
                        if (tv.getBackground() != redDrawable) {
                            tv.setBackground(greenDrawable);
                        }
                    }
                    setCounter();
                }
            });
            TextView tvTail = viewParent.findViewById(IdStart.TAIL + i);
            tvTail.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    for (int j = 0; j < 10; j++) {
                        TextView tv = viewParent.findViewById(IdStart.PICKER + j * 10 + finalI);
                        if (tv.getBackground() != redDrawable) {
                            tv.setBackground(greenDrawable);
                        }
                    }
                    setCounter();
                }
            });
        }
    }

    private void setCounter() {
        int count = 0;
        for (int i = 0; i < Const.MAX_ROW_COUNT_TABLE; i++) {
            if (pickerMatrix[i] != Priority.NONE.getLevel()) {
                count++;
            }
        }
        tvNumberCounter.setText(count + " số được chọn");
    }

    @Override
    public void showTableType2(List<Integer> normals, List<Integer> imports) {
        hsFilteredNumberTable.removeAllViews();
        hsFilteredNumberTable.addView(TableLayoutBase.getPickerTableLayout(getActivity(),
                MatrixUtil.getTouchStringMatrix(0), MatrixUtil.getTouchIntMatrix(IdStart.TOUCHED_NUMBER, 0),
                2, 10, false));
        hsTableToChooseNumber.removeAllViews();
        hsTableToChooseNumber.addView(TableLayoutBase.getPickerTableLayout(getActivity(),
                ArrayUtil.getTenStringArray(), ArrayUtil.getTenIntArray(IdStart.TOUCH), 10, false));
        setStartMatrix(normals, imports);
        setColorForFilteredNumberTextView(0);
        setOnClickForTextViewType2(0);
        setColorForTouchTextView(0);
        for (int i = 0; i < 10; i++) {
            TextView textView = viewParent.findViewById(IdStart.TOUCH + i);
            int finalI = i;
            textView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    hsFilteredNumberTable.removeAllViews();
                    hsFilteredNumberTable.addView(TableLayoutBase.getPickerTableLayout(getActivity(),
                            MatrixUtil.getTouchStringMatrix(finalI), MatrixUtil.getTouchIntMatrix(IdStart.TOUCHED_NUMBER, finalI),
                            2, 10, false));
                    setColorForFilteredNumberTextView(finalI);
                    setOnClickForTextViewType2(finalI);
                    setColorForTouchTextView(finalI);
                }
            });
        }
        setCounter();
    }

    private void setColorForFilteredNumberTextView(int touch) {
        for (int i = 0; i < 10; i++) {
            int number1 = Integer.parseInt(i + "" + touch);
            int number2 = Integer.parseInt(touch + "" + i);
            TextView textView1 = viewParent.findViewById(IdStart.TOUCHED_NUMBER + number1);
            TextView textView2 = viewParent.findViewById(IdStart.TOUCHED_NUMBER + number2);
            if (pickerMatrix[number1] == Priority.NONE.getLevel()) {
                textView1.setBackground(defaultDrawable);
            }
            if (pickerMatrix[number2] == Priority.NONE.getLevel()) {
                textView2.setBackground(defaultDrawable);
            }
            if (pickerMatrix[number1] == Priority.NORMAL.getLevel()) {
                textView1.setBackground(greenDrawable);
            }
            if (pickerMatrix[number2] == Priority.NORMAL.getLevel()) {
                textView2.setBackground(greenDrawable);
            }
            if (pickerMatrix[number1] == Priority.IMPORTANT.getLevel()) {
                textView1.setBackground(redDrawable);
            }
            if (pickerMatrix[number2] == Priority.IMPORTANT.getLevel()) {
                textView2.setBackground(redDrawable);
            }
        }
    }

    private void setColorForTouchTextView(int touch) {
        TextView textView = viewParent.findViewById(IdStart.TOUCH + touch);
        textView.setBackground(greenDrawable);
        for (int i = 0; i < 10; i++) {
            if (i != touch) {
                TextView other = viewParent.findViewById(IdStart.TOUCH + i);
                other.setBackground(defaultDrawable);
            }
        }
    }

    private void setOnClickForTextViewType2(int touch) {
        // từ pink nếu nhấn => xanh, nhấn giữ => đỏ
        // từ xanh nếu nhấn => pink, nhấn giữ => đỏ
        // từ đỏ nếu nhấn => pink, nhấn giữ => pink
        for (int i = 0; i < 20; i++) {
            TextView textView;
            int number;
            if (i / 10 == 0) {
                number = touch * 10 + i;
                textView = viewParent.findViewById(IdStart.TOUCHED_NUMBER + number);
            } else {
                number = (i % 10) * 10 + touch;
                textView = viewParent.findViewById(IdStart.TOUCHED_NUMBER + number);
            }
            textView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (textView.getBackground() == greenDrawable) {
                        textView.setBackground(defaultDrawable);
                        pickerMatrix[number] = Priority.NONE.getLevel();
                        onPickNumber(-1);
                        setCounter();
                    } else {
                        textView.setBackground(greenDrawable);
                        pickerMatrix[number] = Priority.NORMAL.getLevel();
                        onPickNumber(number);
                        setCounter();
                    }
                }
            });
            textView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    String title = "Hành động.";
                    String mess = textView.getBackground() == redDrawable ? "Bạn muốn hủy số đặc biệt " + number +
                            " không?" : "Bạn có muốn chọn số " + number + " làm số đặc biệt không?";
                    DialogBase.showWithConfirmation(getActivity(), title, mess, () -> {
                        if (textView.getBackground() == redDrawable) {
                            textView.setBackground(defaultDrawable);
                            pickerMatrix[number] = Priority.NONE.getLevel();
                            onPickNumber(-1);
                            setCounter();
                        } else {
                            textView.setBackground(redDrawable);
                            pickerMatrix[number] = Priority.IMPORTANT.getLevel();
                            onPickNumber(number);
                            setCounter();
                        }
                    });
                    return false;
                }
            });
        }

    }

    private static SpannableStringBuilder getColorTextBySpanable(String title, List<Integer> normals, List<Integer> imports) {
        List<Integer> allNumbers = Stream.concat(normals.stream(), imports.stream()).distinct().sorted().collect(Collectors.toList());
        SpannableStringBuilder builder = new SpannableStringBuilder();
        builder.append(title);
        for (int i = 0; i < allNumbers.size(); i++) {
            int number = allNumbers.get(i);
            String numberStr = CoupleBase.showCouple(number);
            int start = builder.length();
            builder.append(numberStr);
            if (imports.contains(number)) {
                builder.setSpan(new ForegroundColorSpan(Color.RED), start, start + numberStr.length(),
                        Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            }
            if (i < allNumbers.size() - 1) {
                builder.append(", ");
            }
        }
        return builder;
    }

    @Override
    public void saveDataSuccess(String message) {
        Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showTableAList(List<Integer> normals, List<Integer> imports) {
        if (normals.isEmpty() && imports.isEmpty()) {
            tvTableA.setText("Bảng A trống.");
            imgCancelA.setVisibility(View.GONE);
            imgExportA.setVisibility(View.GONE);
        } else {
            int size = normals.size() + imports.size();
            String title = "Bảng A (" + size + " số): ";
            SpannableStringBuilder spannableText = getColorTextBySpanable(title, normals, imports);
            tvTableA.setText(spannableText);
            imgCancelA.setVisibility(View.VISIBLE);
            imgExportA.setVisibility(View.VISIBLE);
            imgCancelA.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String title = "Xóa?";
                    String message = "Bạn có muốn xóa hết dữ liệu trong bảng A không?";
                    DialogBase.showWithConfirmation(getActivity(), title, message, () -> {
                        viewModel.deleteAllData(true);
                    });
                }
            });
            imgExportA.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String message = "Bạn có muốn xuất dữ liệu ra clipboard không?";
                    DialogBase.showWithCopiedText(getActivity(), "Xuất?", message,
                            spannableText.subSequence(title.length() - 1,
                                    spannableText.length()).toString(), "dữ liệu");
                }
            });
        }
    }

    @Override
    public void showTableBList(List<Integer> normals, List<Integer> imports) {
        if (normals.isEmpty() && imports.isEmpty()) {
            tvTableB.setText("Bảng B trống.");
            imgCancelB.setVisibility(View.GONE);
            imgExportB.setVisibility(View.GONE);
        } else {
            int size = normals.size() + imports.size();
            String title = "Bảng B (" + size + " số): ";
            SpannableStringBuilder spannableText = getColorTextBySpanable(title, normals, imports);
            tvTableB.setText(spannableText);
            imgCancelB.setVisibility(View.VISIBLE);
            imgExportB.setVisibility(View.VISIBLE);
            imgCancelB.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String message = "Bạn có muốn xóa hết dữ liệu trong bảng B không?";
                    DialogBase.showWithConfirmation(getActivity(), "Xóa?", message, () -> {
                        viewModel.deleteAllData(false);
                    });
                }
            });
            imgExportB.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String message = "Bạn có muốn xuất dữ liệu ra clipboard không?";
                    DialogBase.showWithCopiedText(getActivity(), "Xuất?", message,
                            spannableText.subSequence(title.length() - 1,
                                    spannableText.length()).toString(), "dữ liệu");
                }
            });
        }
    }

    @Override
    public void deleteAllDataSuccess(String message, boolean isTableA) {
        Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
        if (isTableA) {
            viewModel.getTableAList();
        } else {
            viewModel.getTableBList();
        }
    }

}
