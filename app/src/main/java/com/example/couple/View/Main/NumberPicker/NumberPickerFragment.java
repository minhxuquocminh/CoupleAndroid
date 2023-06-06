package com.example.couple.View.Main.NumberPicker;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.example.couple.Base.Handler.IOFileBase;
import com.example.couple.Base.Handler.NumberBase;
import com.example.couple.Base.View.WidgetBase;
import com.example.couple.Custom.Const.Const;
import com.example.couple.Custom.Const.IdStart;
import com.example.couple.Custom.Widget.CustomTableLayout;
import com.example.couple.Model.Display.Number;
import com.example.couple.Model.Origin.Couple;
import com.example.couple.R;
import com.example.couple.View.Couple.BanlanceCoupleActivity;
import com.example.couple.View.Couple.CoupleByWeekActivity;
import com.example.couple.View.JackpotStatistics.JackpotByYearActivity;
import com.example.couple.View.JackpotStatistics.JackpotNextDayActivity;
import com.example.couple.View.Main.CreateNumberArray.CreateNumberArrayFragment;
import com.example.couple.View.Main.MainActivity;
import com.example.couple.ViewModel.Main.NumberPicker.NumberPickerViewModel;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.List;


public class NumberPickerFragment extends Fragment implements NumberPickerView {
    TextView tvViewCoupleWeekly;
    TextView tvBalanceCouple;
    TextView tvJackpotByYear;
    TextView tvCreateNumberArray;
    LinearLayout linearCouple;
    LinearLayout linearCoupleNextDay;
    LinearLayout linearCoupleLastMonth;
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
    boolean coupleTableIsExist = false;
    boolean coupleNextDayTableIsExist = false;
    private static final int RC_CREATING_NUMBER_ARRAY = 111;

    Drawable greenDrawable;
    Drawable redDrawable;
    Drawable pinkDrawable;

    // for table type 2, matrix dùng để lưu các danh sách số bị phân mảnh và đếm số đc chọn
    int matrix[] = new int[Const.MAX_ROW_COUNT_TABLE];


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        viewParent = inflater.inflate(R.layout.fragment_number_picker, container, false);

        tvViewCoupleWeekly = viewParent.findViewById(R.id.tvViewCoupleWeekly);
        tvBalanceCouple = viewParent.findViewById(R.id.tvBalanceCouple);
        tvJackpotByYear = viewParent.findViewById(R.id.tvJackpotByYear);
        tvCreateNumberArray = viewParent.findViewById(R.id.tvCreateNumberArray);

        linearCouple = viewParent.findViewById(R.id.linearCouple);
        linearCoupleNextDay = viewParent.findViewById(R.id.linearCoupleNextDay);
        linearCoupleLastMonth = viewParent.findViewById(R.id.linearCoupleLastMonth);
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

        viewModel = new NumberPickerViewModel(this, getActivity());
        viewModel.GetSubCoupleList(4);

        pinkDrawable = WidgetBase.getDrawable(getActivity(), R.drawable.cell_pink_table);
        greenDrawable = WidgetBase.getDrawable(getActivity(), R.drawable.cell_light_green_table);
        redDrawable = WidgetBase.getDrawable(getActivity(), R.drawable.cell_red_table);

        FirstNumberPickerIsSelected();

        tvViewCoupleWeekly.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), CoupleByWeekActivity.class));
            }
        });

        tvBalanceCouple.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), BanlanceCoupleActivity.class));
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
                    SaveListIsChecked();
                } else {
                    if (!cboNumberPicker.isChecked()) {
                        HideAllContentIsSelected();
                    }
                }
            }
        });

        cboNumberPicker.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    if (isFirstNumberPicker) {
                        FirstNumberPickerIsSelected();
                    } else {
                        SecondNumberPickerIsSelected();
                    }
                } else {
                    if (!cboSavedList.isChecked()) {
                        HideAllContentIsSelected();
                    }
                }
            }
        });

        cboNumberPicker.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                String newType = isFirstNumberPicker ? "2" : "1";
                new AlertDialog.Builder(getActivity())
                        .setTitle("Chuyển kiểu chọn?")
                        .setMessage("Bạn có muốn chuyển kiểu chọn số sang kiểu " + newType + " không?")
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                if (isFirstNumberPicker) {
                                    SecondNumberPickerIsSelected();
                                } else {
                                    FirstNumberPickerIsSelected();
                                }
                            }
                        })
                        .setNegativeButton(android.R.string.no, null)
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .show();
                return false;
            }
        });

        cboTableA.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    cboTableB.setChecked(false);
                    if (isFirstNumberPicker) {
                        viewModel.GetTableType1(true);
                    } else {
                        viewModel.GetTableType2(true);
                    }
                } else {
                    if (!cboTableB.isChecked()) {
                        HideAllContentIsSelected();
                    }
                }
            }
        });

        cboTableB.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    cboTableA.setChecked(false);
                    if (isFirstNumberPicker) {
                        viewModel.GetTableType1(false);
                    } else {
                        viewModel.GetTableType2(false);
                    }
                } else {
                    if (!cboTableA.isChecked()) {
                        HideAllContentIsSelected();
                    }
                }
            }
        });

        imgClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isFirstNumberPicker) {
                    for (int i = 0; i < Const.MAX_ROW_COUNT_TABLE; i++) {
                        TextView textView = viewParent.findViewById(i);
                        textView.setBackground(pinkDrawable);
                        SetCounterForTableType1();
                    }
                } else {
                    ShowTableType2(new ArrayList<>());
                }
            }
        });

        imgSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tableName = cboTableA.isChecked() ? "A" : "B";
                new AlertDialog.Builder(getActivity())
                        .setTitle("Lưu?")
                        .setMessage("Bạn có muốn lưu dữ liệu vào bảng " + tableName + " không?")
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                if (isFirstNumberPicker) {
                                    List<Number> numbers = new ArrayList<>();
                                    for (int i = 0; i < Const.MAX_ROW_COUNT_TABLE; i++) {
                                        TextView textView = viewParent.findViewById(i);
                                        if (textView.getBackground() == greenDrawable) {
                                            numbers.add(new Number(i, 1));
                                        }
                                        if (textView.getBackground() == redDrawable) {
                                            numbers.add(new Number(i, 2));
                                        }
                                    }
                                    viewModel.SaveDataToFile(numbers, cboTableA.isChecked());
                                } else {
                                    List<Number> numbers = new ArrayList<>();
                                    for (int i = 0; i < Const.MAX_ROW_COUNT_TABLE; i++) {
                                        if (matrix[i] > 0) {
                                            numbers.add(new Number(i, matrix[i]));
                                        }
                                    }
                                    viewModel.SaveDataToFile(numbers, cboTableA.isChecked());
                                }
                            }
                        })
                        .setNegativeButton(android.R.string.no, null)
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .show();
            }
        });

        imgExport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(getActivity())
                        .setTitle("Xuất?")
                        .setMessage("Bạn có muốn xuất dữ liệu ra clipboard không?")
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                String data = "";
                                if (isFirstNumberPicker) {
                                    for (int i = 0; i < Const.MAX_ROW_COUNT_TABLE; i++) {
                                        TextView textView = viewParent.findViewById(i);
                                        if (textView.getBackground() == greenDrawable ||
                                                textView.getBackground() == redDrawable) {
                                            data += i < 10 ? "0" + i + " " : i + " ";
                                        }
                                    }
                                } else {
                                    for (int i = 0; i < Const.MAX_ROW_COUNT_TABLE; i++) {
                                        if (matrix[i] != 0) {
                                            data += i < 10 ? "0" + i + " " : i + " ";
                                        }
                                    }
                                }
                                WidgetBase.copyToClipboard(getActivity(), "numbers", data);
                                Toast.makeText(getActivity(), "Đã xuất dữ liệu ra clipboard.",
                                        Toast.LENGTH_SHORT).show();
                            }
                        })
                        .setNegativeButton(android.R.string.no, null)
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .show();
            }
        });

        BottomNavigationView navigationView = getActivity().findViewById(R.id.bottom_navigation);
        FragmentManager fm = getActivity().getSupportFragmentManager();
        navigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.itHome:
                        if (MainActivity.active != MainActivity.fragment1) {
                            fm.beginTransaction().show(MainActivity.fragment1).hide(MainActivity.active).commit();
                            MainActivity.active = MainActivity.fragment1;
                        }
                        return true;
                    case R.id.itCreateNumbers:
                        if (MainActivity.active != MainActivity.fragment2) {
                            fm.beginTransaction().show(MainActivity.fragment2).hide(MainActivity.active).commit();
                            MainActivity.active = MainActivity.fragment2;
                            if (CreateNumberArrayFragment.RECEIVE_DATA) {
                                setDataToTable();
                                CreateNumberArrayFragment.RECEIVE_DATA = false;
                            }
                        }
                        return true;
                    case R.id.itPredictionBridge:
                        if (MainActivity.active != MainActivity.fragment3) {
                            fm.beginTransaction().show(MainActivity.fragment3).hide(MainActivity.active).commit();
                            MainActivity.active = MainActivity.fragment3;
                        }
//                        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
//                        if (firebaseUser == null) {
//                            Toast.makeText(MainActivity.this,
//                                    "Bạn cần phải đăng nhập trước khi sử dụng chức năng này!",
//                                    Toast.LENGTH_SHORT).show();
//                            return false;
//                        } else {
//
//                        }
                        return true;
                    case R.id.itFunction:
                        if (MainActivity.active != MainActivity.fragment4) {
                            fm.beginTransaction().show(MainActivity.fragment4).hide(MainActivity.active).commit();
                            MainActivity.active = MainActivity.fragment4;
                        }
                        return true;
                    case R.id.itPersonal:
                        if (MainActivity.active != MainActivity.fragment5) {
                            fm.beginTransaction().show(MainActivity.fragment5).hide(MainActivity.active).commit();
                            MainActivity.active = MainActivity.fragment5;
                        }
                        return true;
                }
                return false;
            }
        });

        return viewParent;
    }

    private void HideAllContentIsSelected() {
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

    private void SaveListIsChecked() {
        isFirstNumberPicker = false;
        cboNumberPicker.setChecked(false);
        viewModel.GetTableAList();
        viewModel.GetTableBList();

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

    private void FirstNumberPickerIsSelected() {
        isFirstNumberPicker = true;
        cboNumberPicker.setText("Bảng chọn số K1");
        cboSavedList.setChecked(false);
        cboNumberPicker.setChecked(true);
        cboTableA.setChecked(true);
        viewModel.GetTableType1(true);

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

    private void SecondNumberPickerIsSelected() {
        isFirstNumberPicker = false;
        cboNumberPicker.setText("Bảng chọn số K2");
        cboSavedList.setChecked(false);
        cboNumberPicker.setChecked(true);
        cboTableA.setChecked(true);
        viewModel.GetTableType2(true);

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
    public void ShowError(String message) {
        Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void ShowSubCoupleList(List<Couple> subCoupleList) {
        TableLayout tableLayout = CustomTableLayout.
                getCoupleTableLayout(getActivity(), subCoupleList, 0);
        linearCouple.removeAllViews();
        linearCouple.addView(tableLayout);
        coupleTableIsExist = true;
        Couple lastCouple = subCoupleList.get(subCoupleList.size() - 1);
        viewModel.GetSubCoupleNextDay(lastCouple.getCoupleInt(), 4);
        viewModel.GetSubCoupleLastMonth(lastCouple.getDateBase());
        listMP = "";
        for (int i = subCoupleList.size() - 1; i >= 0; i--) {
            listMP += subCoupleList.get(i).toString();
            if (i != 0) {
                listMP += ", ";
            }
        }
        UpdateMyPrediction(-1, listMP);
    }

    private void UpdateMyPrediction(int mp, String listMP) {
        if (listMP.equals("")) {
            tvShowPrediction.setText("m: ");
        } else {
            String mpStr = "";
            if (mp < 0) {
                mpStr = "??";
            } else if (mp < 10) {
                mpStr = "0" + mp;
            } else {
                mpStr = mp + "";
            }
            if (coupleTableIsExist) {
                TextView textView = viewParent.findViewById(IdStart.MY_PREDICTION);
                textView.setText(mpStr);
            }
            if (coupleNextDayTableIsExist) {
                TextView textView = viewParent.findViewById(IdStart.MY_PREDICTION + 1);
                textView.setText(mpStr);
            }
            tvShowPrediction.setVisibility(View.VISIBLE);
            tvShowPrediction.setText("m: " + mpStr + ", " + listMP);

            String selected = mp < 0 ? "" : mpStr;
            IOFileBase.saveDataToFile(getActivity(), "selected.txt", selected, 0);
        }
    }

    @Override
    public void ShowSubCoupleNextDay(List<Couple> subCoupleList) {
        TableLayout tableLayout = CustomTableLayout.getCoupleTableLayout(getActivity(),
                subCoupleList, 1);
        linearCoupleNextDay.removeAllViews();
        linearCoupleNextDay.addView(tableLayout);
        coupleNextDayTableIsExist = true;
    }

    @Override
    public void ShowSubCoupleLastMonth(List<Couple> subCoupleList) {
        TableLayout tableLayout = CustomTableLayout.getCoupleTableLayout(getActivity(),
                subCoupleList, -1);
        linearCoupleLastMonth.removeAllViews();
        linearCoupleLastMonth.addView(tableLayout);
    }

    @Override
    public void ShowTableType1(List<Number> numbers) {
        hsNumberTable.removeAllViews();
        hsNumberTable.addView(CustomTableLayout.getNumberTableLayout(getActivity()));
        hsChooseHeadTailTable.removeAllViews();
        hsChooseHeadTailTable.addView(CustomTableLayout.getChooseHeadTailTableLayout(getActivity()));
        SetOnClickForTextViewType1();
        SetOnClickForHeadTail();
        for (int i = 0; i < numbers.size(); i++) {
            TextView textView = viewParent.findViewById(numbers.get(i).getNumber());
            if (numbers.get(i).getLevel() == 1) {
                textView.setBackground(greenDrawable);
            }
            if (numbers.get(i).getLevel() == 2) {
                textView.setBackground(redDrawable);
            }
        }
        SetCounterForTableType1();
    }

    private void SetOnClickForTextViewType1() {
        // từ pink nếu nhấn => xanh, nhấn giữ => đỏ
        // từ xanh nếu nhấn => pink, nhấn giữ => đỏ
        // từ đỏ nếu nhấn => pink, nhấn giữ => pink
        for (int i = 0; i < Const.MAX_ROW_COUNT_TABLE; i++) {
            TextView textView = viewParent.findViewById(i);
            int finalI = i;
            textView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (textView.getBackground() == greenDrawable) {
                        textView.setBackground(pinkDrawable);
                        UpdateMyPrediction(-1, listMP);
                        SetCounterForTableType1();
                    } else {
                        textView.setBackground(greenDrawable);
                        UpdateMyPrediction(finalI, listMP);
                        SetCounterForTableType1();
                    }
                }
            });
            textView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    String mess = textView.getBackground() == redDrawable ? "Bạn muốn hủy số đặc biệt " + finalI +
                            " không?" : "Bạn có muốn chọn số " + finalI + " làm số đặc biệt không?";
                    new AlertDialog.Builder(getActivity())
                            .setTitle("Hành động.")
                            .setMessage(mess)
                            .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    if (textView.getBackground() == redDrawable) {
                                        textView.setBackground(pinkDrawable);
                                        UpdateMyPrediction(-1, listMP);
                                        SetCounterForTableType1();
                                    } else {
                                        textView.setBackground(redDrawable);
                                        UpdateMyPrediction(finalI, listMP);
                                        SetCounterForTableType1();
                                    }
                                }
                            })
                            .setNegativeButton(android.R.string.no, null)
                            .setIcon(android.R.drawable.ic_dialog_alert)
                            .show();

                    return false;
                }
            });
        }
    }

    private void SetOnClickForHeadTail() {
        for (int i = 0; i < 10; i++) {
            TextView tvHead = viewParent.findViewById(IdStart.HEAD + i);
            int finalI = i;
            tvHead.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    for (int j = 0; j < 10; j++) {
                        TextView tv = viewParent.findViewById(finalI * 10 + j);
                        if (tv.getBackground() != redDrawable) {
                            tv.setBackground(greenDrawable);
                        }
                    }
                    SetCounterForTableType1();
                }
            });
            TextView tvTail = viewParent.findViewById(IdStart.TAIL + i);
            tvTail.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    for (int j = 0; j < 10; j++) {
                        TextView tv = viewParent.findViewById(j * 10 + finalI);
                        if (tv.getBackground() != redDrawable) {
                            tv.setBackground(greenDrawable);
                        }
                    }
                    SetCounterForTableType1();
                }
            });
        }
    }

    private void SetCounterForTableType1() {
        int count = 0;
        for (int i = 0; i < Const.MAX_ROW_COUNT_TABLE; i++) {
            TextView textView = viewParent.findViewById(i);
            if (textView.getBackground() == greenDrawable) {
                count++;
            }
            if (textView.getBackground() == redDrawable) {
                count++;
            }
        }
        tvNumberCounter.setText(count + " số được chọn");
    }

    @Override
    public void ShowTableType2(List<Number> numbers) {
        hsFilteredNumberTable.removeAllViews();
        hsFilteredNumberTable.addView(CustomTableLayout.getFilteredNumberTableLayout(getActivity(), 0));
        hsTableToChooseNumber.removeAllViews();
        hsTableToChooseNumber.addView(CustomTableLayout.getChooseNumberTableLayout(getActivity()));
        SetStartMatrix(numbers);
        SetColorForChoosingNumberTextView();
        SetColorForFilteredNumberTextView(0);
        SetOnClickForTextViewType2(0);
        for (int i = 0; i < 10; i++) {
            TextView textView = viewParent.findViewById(IdStart.TOUCH + i);
            int finalI = i;
            textView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    hsFilteredNumberTable.removeAllViews();
                    hsFilteredNumberTable.addView(CustomTableLayout.getFilteredNumberTableLayout(getActivity(),
                            finalI));
                    SetColorForFilteredNumberTextView(finalI);
                    SetOnClickForTextViewType2(finalI);
                }
            });
        }
        SetCounterForTableType2();
    }

    private void SetStartMatrix(List<Number> numbers) {
        matrix = new int[Const.MAX_ROW_COUNT_TABLE];
        for (int i = 0; i < numbers.size(); i++) {
            int number = numbers.get(i).getNumber();
            if (numbers.get(i).getLevel() == 1) {
                matrix[number] = 1;
            }
            if (numbers.get(i).getLevel() == 2) {
                matrix[number] = 2;
            }
        }
    }

    private void SetColorForChoosingNumberTextView() {
        for (int i = 0; i < 10; i++) {
            int count = 0;
            int count2 = 0;
            for (int j = 0; j < 10; j++) {
                int number1 = Integer.parseInt(i + "" + j);
                int number2 = Integer.parseInt(j + "" + i);
                if (matrix[number1] == 1 || matrix[number2] == 1) {
                    count++;
                }
                if (matrix[number1] == 2 || matrix[number2] == 2) {
                    count2++;
                }
            }
            TextView textView = viewParent.findViewById(IdStart.TOUCH + i);
            if (count == 0) {
                textView.setBackground(pinkDrawable);
            } else {
                textView.setBackground(greenDrawable);
            }
            if (count2 == 0) {
                textView.setBackground(pinkDrawable);
            } else {
                textView.setBackground(redDrawable);
            }
        }
    }

    private void SetColorForFilteredNumberTextView(int touch) {
        for (int i = 0; i < 10; i++) {
            int number1 = Integer.parseInt(i + "" + touch);
            int number2 = Integer.parseInt(touch + "" + i);
            TextView textView1 = viewParent.findViewById(IdStart.TOUCHED_NUMBER + number1);
            TextView textView2 = viewParent.findViewById(IdStart.TOUCHED_NUMBER + number2);
            if (matrix[number1] == 0) {
                textView1.setBackground(pinkDrawable);
            }
            if (matrix[number2] == 0) {
                textView2.setBackground(pinkDrawable);
            }
            if (matrix[number1] == 1) {
                textView1.setBackground(greenDrawable);
            }
            if (matrix[number2] == 1) {
                textView2.setBackground(greenDrawable);
            }
            if (matrix[number1] == 2) {
                textView1.setBackground(redDrawable);
            }
            if (matrix[number2] == 2) {
                textView2.setBackground(redDrawable);
            }
        }
    }

    private void SetOnClickForTextViewType2(int touch) {
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
                        textView.setBackground(pinkDrawable);
                        matrix[number] = 0;
                        SetColorForChoosingNumberTextView();
                        UpdateMyPrediction(-1, listMP);
                        SetCounterForTableType2();
                    } else {
                        textView.setBackground(greenDrawable);
                        matrix[number] = 1;
                        SetColorForChoosingNumberTextView();
                        UpdateMyPrediction(number, listMP);
                        SetCounterForTableType2();
                    }
                }
            });
            textView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    String mess = textView.getBackground() == redDrawable ? "Bạn muốn hủy số đặc biệt " + number +
                            " không?" : "Bạn có muốn chọn số " + number + " làm số đặc biệt không?";
                    new AlertDialog.Builder(getActivity())
                            .setTitle("Hành động.")
                            .setMessage(mess)
                            .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    if (textView.getBackground() == redDrawable) {
                                        textView.setBackground(pinkDrawable);
                                        matrix[number] = 0;
                                        SetColorForChoosingNumberTextView();
                                        UpdateMyPrediction(-1, listMP);
                                        SetCounterForTableType2();
                                    } else {
                                        textView.setBackground(redDrawable);
                                        matrix[number] = 2;
                                        SetColorForChoosingNumberTextView();
                                        UpdateMyPrediction(number, listMP);
                                        SetCounterForTableType2();
                                    }
                                }
                            })
                            .setNegativeButton(android.R.string.no, null)
                            .setIcon(android.R.drawable.ic_dialog_alert)
                            .show();
                    return false;
                }
            });
        }

    }

    private void SetCounterForTableType2() {
        int count = 0;
        for (int i = 0; i < Const.MAX_ROW_COUNT_TABLE; i++) {
            if (matrix[i] != 0) {
                count++;
            }
        }
        tvNumberCounter.setText(count + " số được chọn");
    }

    private static Spannable getColorTextBySpanable(String title, List<Number> numbers) {
        String data = title;
        for (int i = 0; i < numbers.size(); i++) {
            data += numbers.get(i).show();
            if (i != numbers.size() - 1) {
                data += ", ";
            }
        }
        // vd:     01, 05, 12, 16 => mỗi number chiếm 4 kí tự
        Spannable wordtoSpan = new SpannableString(data);
        int sizeOfTitle = title.length();
        for (int i = 0; i < numbers.size(); i++) {
            if (numbers.get(i).getLevel() == 2)
                wordtoSpan.setSpan(new ForegroundColorSpan(Color.BLUE), sizeOfTitle + (i * 4),
                        sizeOfTitle + (i * 4 + 2), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        }
        return wordtoSpan;
    }

    @Override
    public void SaveDataSuccess(String message) {
        Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void ShowTableAList(List<Number> numbers) {
        if (numbers.isEmpty()) {
            tvTableA.setText("Bảng A trống.");
            imgCancelA.setVisibility(View.GONE);
            imgExportA.setVisibility(View.GONE);
        } else {
            String title = "Bảng A (" + numbers.size() + " số): ";
            Spannable spannableText = getColorTextBySpanable(title, numbers);
            tvTableA.setText(spannableText);
            imgCancelA.setVisibility(View.VISIBLE);
            imgExportA.setVisibility(View.VISIBLE);
            imgCancelA.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    new AlertDialog.Builder(getActivity())
                            .setTitle("Xóa?")
                            .setMessage("Bạn có muốn xóa hết dữ liệu trong bảng A không?")
                            .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    viewModel.DeleteAllData(true);
                                }
                            })
                            .setNegativeButton(android.R.string.no, null)
                            .setIcon(android.R.drawable.ic_dialog_alert)
                            .show();
                }
            });
            imgExportA.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    new AlertDialog.Builder(getActivity())
                            .setTitle("Xuất?")
                            .setMessage("Bạn có muốn xuất dữ liệu ra clipboard không?")
                            .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    WidgetBase.copyToClipboard(getActivity(), "numbers",
                                            spannableText.subSequence(title.length() - 1,
                                                    spannableText.length()).toString());
                                    Toast.makeText(getActivity(), "Đã xuất dữ liệu ra clipboard.",
                                            Toast.LENGTH_SHORT).show();
                                }
                            })
                            .setNegativeButton(android.R.string.no, null)
                            .setIcon(android.R.drawable.ic_dialog_alert)
                            .show();
                }
            });
        }
    }

    @Override
    public void ShowTableBList(List<Number> numbers) {
        if (numbers.isEmpty()) {
            tvTableB.setText("Bảng B trống.");
            imgCancelB.setVisibility(View.GONE);
            imgExportB.setVisibility(View.GONE);
        } else {
            String title = "Bảng B (" + numbers.size() + " số): ";
            Spannable spannableText = getColorTextBySpanable(title, numbers);
            tvTableB.setText(spannableText);
            imgCancelB.setVisibility(View.VISIBLE);
            imgExportB.setVisibility(View.VISIBLE);
            imgCancelB.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    new AlertDialog.Builder(getActivity())
                            .setTitle("Xóa?")
                            .setMessage("Bạn có muốn xóa hết dữ liệu trong bảng B không?")
                            .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    viewModel.DeleteAllData(false);
                                }
                            })
                            .setNegativeButton(android.R.string.no, null)
                            .setIcon(android.R.drawable.ic_dialog_alert)
                            .show();
                }
            });
            imgExportB.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    new AlertDialog.Builder(getActivity())
                            .setTitle("Xuất?")
                            .setMessage("Bạn có muốn xuất dữ liệu ra clipboard không?")
                            .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    WidgetBase.copyToClipboard(getActivity(), "numbers",
                                            spannableText.subSequence(title.length() - 1,
                                                    spannableText.length()).toString());
                                    Toast.makeText(getActivity(), "Đã xuất dữ liệu ra clipboard.",
                                            Toast.LENGTH_SHORT).show();
                                }
                            })
                            .setNegativeButton(android.R.string.no, null)
                            .setIcon(android.R.drawable.ic_dialog_alert)
                            .show();
                }
            });
        }
    }

    @Override
    public void DeleteAllDataSuccess(String message, boolean isTableA) {
        Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
        if (isTableA) {
            viewModel.GetTableAList();
        } else {
            viewModel.GetTableBList();
        }
    }

    public void setDataToTable() {
        String numbersStr = IOFileBase.readDataFromFile(getActivity(), "numberarray.txt");
        IOFileBase.saveDataToFile(getActivity(), "numberarray.txt", "", 0);
        if (isFirstNumberPicker) {
            List<Number> numbers = NumberBase.verifyNumberArr(numbersStr, 2);
            if (numbers.size() != 0) {
                ShowTableType1(numbers);
                Toast.makeText(getActivity(), "Đã nạp dữ liệu!", Toast.LENGTH_LONG).show();
            }
        } else {
            Toast.makeText(getActivity(), "Dữ liệu chỉ nên nạp vào bảng chọn số K1 rồi lưu để dùng lại!",
                    Toast.LENGTH_LONG).show();
        }
    }
}
