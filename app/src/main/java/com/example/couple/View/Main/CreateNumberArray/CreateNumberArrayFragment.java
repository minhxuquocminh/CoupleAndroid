package com.example.couple.View.Main.CreateNumberArray;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.example.couple.Base.Handler.IOFileBase;
import com.example.couple.Base.Handler.NumberBase;
import com.example.couple.Base.View.WidgetBase;
import com.example.couple.Custom.Const.Const;
import com.example.couple.Custom.Const.FileName;
import com.example.couple.Custom.Const.IdStart;
import com.example.couple.Custom.Widget.CustomTableLayout;
import com.example.couple.Model.Display.Number;
import com.example.couple.Model.Display.SpecialSetHistory;
import com.example.couple.Model.Origin.Jackpot;
import com.example.couple.Model.Origin.Lottery;
import com.example.couple.R;
import com.example.couple.View.Bridge.BridgeCombinationActivity;
import com.example.couple.View.BridgeHistory.SexagenaryCycleActivity;
import com.example.couple.View.JackpotStatistics.JackpotByYearActivity;
import com.example.couple.View.Main.MainActivity;
import com.example.couple.View.SubScreen.CycleByYearActivity;
import com.example.couple.ViewModel.Main.CreateNumberArray.CreateNumberArrayViewModel;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.List;

public class CreateNumberArrayFragment extends Fragment implements CreateNumberArrayView {
    TextView tvViewBridge;
    TextView tvSpecialNumbersHistory;
    TextView tvViewCycle;
    TextView tvViewCycleByYear;
    EditText edtSet;
    EditText edtTouch;
    EditText edtSum;
    EditText edtThirdClaw;
    EditText edtHead;
    EditText edtTail;
    EditText edtCombineNumber;
    EditText edtAddingNumber;
    EditText edtRemovingNumber;
    TextView tvCreate;
    TextView tvArrayCounter;
    EditText edtNumberArray;
    TextView tvExportCouple;
    TextView tvExportTriad;
    TextView tvExport;
    TextView tvClear;
    TextView tvSubJackpot;
    TextView tvViewJackpot;
    TextView tvNumberCounter;
    ImageView imgClear;
    ImageView imgSave;
    ImageView imgExport;
    HorizontalScrollView hsThirdClaw;
    HorizontalScrollView hsNumberTable;
    TextView tvShowTriadList;

    View viewParent;

    int pink;
    int green;
    int red;

    CreateNumberArrayViewModel viewModel;
    List<Jackpot> subJackpot = new ArrayList<>();
    int matrix[] = new int[1000];
    public static Boolean RECEIVE_DATA;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        viewParent = inflater.inflate(R.layout.fragment_create_number_array, container, false);

        tvViewBridge = viewParent.findViewById(R.id.tvViewBridge);
        tvSpecialNumbersHistory = viewParent.findViewById(R.id.tvSpecialNumbersHistory);
        tvViewCycle = viewParent.findViewById(R.id.tvViewCycle);
        tvViewCycleByYear = viewParent.findViewById(R.id.tvViewCycleByYear);
        edtSet = viewParent.findViewById(R.id.edtSet);
        edtTouch = viewParent.findViewById(R.id.edtTouch);
        edtSum = viewParent.findViewById(R.id.edtSum);
        edtThirdClaw = viewParent.findViewById(R.id.edtThirdClaw);
        edtHead = viewParent.findViewById(R.id.edtHead);
        edtTail = viewParent.findViewById(R.id.edtTail);
        edtCombineNumber = viewParent.findViewById(R.id.edtCombineNumber);
        edtAddingNumber = viewParent.findViewById(R.id.edtAddingNumber);
        edtRemovingNumber = viewParent.findViewById(R.id.edtRemovingNumber);
        tvCreate = viewParent.findViewById(R.id.tvCreate);
        tvArrayCounter = viewParent.findViewById(R.id.tvArrayCounter);
        edtNumberArray = viewParent.findViewById(R.id.edtNumberArray);
        tvExportCouple = viewParent.findViewById(R.id.tvExportCouple);
        tvExportTriad = viewParent.findViewById(R.id.tvExportTriad);
        tvExport = viewParent.findViewById(R.id.tvExport);
        tvClear = viewParent.findViewById(R.id.tvClear);
        tvSubJackpot = viewParent.findViewById(R.id.tvSubJackpot);
        tvViewJackpot = viewParent.findViewById(R.id.tvViewJackpot);
        tvNumberCounter = viewParent.findViewById(R.id.tvNumberCounter);
        imgClear = viewParent.findViewById(R.id.imgClear);
        imgSave = viewParent.findViewById(R.id.imgSave);
        imgExport = viewParent.findViewById(R.id.imgExport);
        hsThirdClaw = viewParent.findViewById(R.id.hsThirdClaw);
        hsNumberTable = viewParent.findViewById(R.id.hsNumberTable1);
        tvShowTriadList = viewParent.findViewById(R.id.tvShowTriadList);

        viewModel = new CreateNumberArrayViewModel(this, getActivity());
        viewModel.GetLotteryAndJackpotList();
        RECEIVE_DATA = false;
        viewModel.GetSubJackpotList(5);
        viewModel.GetTriadTable();

        pink = R.drawable.cell_pink_table;
        green = R.drawable.cell_light_green_table;
        red = R.drawable.cell_red_table;

        tvViewBridge.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                WidgetBase.hideKeyboard(getActivity());
                startActivity(new Intent(getActivity(), BridgeCombinationActivity.class));
            }
        });

        tvCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                WidgetBase.hideKeyboard(getActivity());
                String set = edtSet.getText().toString().trim();
                String touch = edtTouch.getText().toString().trim();
                String sum = edtSum.getText().toString().trim();
                String thirdClaw = edtThirdClaw.getText().toString().trim();
                String head = edtHead.getText().toString().trim();
                String tail = edtTail.getText().toString().trim();
                String combine = edtCombineNumber.getText().toString().trim();
                String add = edtAddingNumber.getText().toString().trim();
                String remove = edtRemovingNumber.getText().toString().trim();
                viewModel.CreateNumberArray(set, touch, sum, thirdClaw, head, tail, combine, add, remove);
            }
        });

        tvClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                WidgetBase.hideKeyboard(getActivity());
                edtSet.setText("");
                edtTouch.setText("");
                edtSum.setText("");
                edtThirdClaw.setText("");
                edtHead.setText("");
                edtTail.setText("");
                edtCombineNumber.setText("");
                edtAddingNumber.setText("");
                edtRemovingNumber.setText("");
                edtNumberArray.setText("");
            }
        });

        edtNumberArray.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                viewModel.GetNumberArrayCounter(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        tvExportCouple.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                WidgetBase.hideKeyboard(getActivity());
                String numberArray = edtNumberArray.getText().toString().trim();
                viewModel.VerifyCoupleArray(numberArray);
            }
        });

        tvExportTriad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                WidgetBase.hideKeyboard(getActivity());
                String numberArray = edtNumberArray.getText().toString().trim();
                viewModel.VerifyTriadArray(numberArray);
            }
        });

        tvExport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                WidgetBase.hideKeyboard(getActivity());
                new AlertDialog.Builder(getActivity())
                        .setTitle("Xuất?")
                        .setMessage("Bạn có muốn xuất dữ liệu ra clipboard không?")
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                String numberArray = edtNumberArray.getText().toString().trim();
                                viewModel.VerifyString(numberArray);
                            }
                        })
                        .setNegativeButton(android.R.string.no, null)
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .show();
            }
        });

        tvViewJackpot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                WidgetBase.hideKeyboard(getActivity());
                startActivity(new Intent(getActivity(),
                        JackpotByYearActivity.class));
            }
        });

        imgClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShowTriadTable(new ArrayList<>());
            }
        });

        imgSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(getActivity())
                        .setTitle("Lưu?")
                        .setMessage("Bạn có muốn lưu dữ liệu vào CSDL không?")
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                List<Number> numbers = new ArrayList<>();
                                for (int i = 0; i < 1000; i++) {
                                    if (matrix[i] > 0) {
                                        numbers.add(new Number(i, matrix[i]));
                                    }
                                }
                                viewModel.SaveDataToFile(numbers);
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
                                for (int i = 0; i < 1000; i++) {
                                    if (matrix[i] > 0) {
                                        data += NumberBase.showNumberString(i, 3) + " ";
                                    }
                                }
                                WidgetBase.copyToClipboard(getActivity(),
                                        "numbers", data.trim());
                                Toast.makeText(getActivity(),
                                        "Đã xuất dữ liệu ra clipboard.", Toast.LENGTH_SHORT).show();
                            }
                        })
                        .setNegativeButton(android.R.string.no, null)
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .show();
            }
        });

        return viewParent;
    }

    @Override
    public void ShowError(String message) {
        Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void ShowLotteryAndJackpotList(List<Jackpot> jackpotList, List<Lottery> lotteryList) {
        tvSpecialNumbersHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                WidgetBase.hideKeyboard(getActivity());
                viewModel.GetSpecialNumbersHistory(jackpotList);
            }
        });

        tvViewCycle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                WidgetBase.hideKeyboard(getActivity());
                startActivity(new Intent(getActivity(), SexagenaryCycleActivity.class));
            }
        });

        tvViewCycleByYear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                WidgetBase.hideKeyboard(getActivity());
                startActivity(new Intent(getActivity(), CycleByYearActivity.class));
            }
        });
    }

    @Override
    public void ShowSpecialNumbersHistory(List<SpecialSetHistory> histories) {
        String title = "Lịch sử các số đặc biệt";
        String content = " * Lịch sử các số đặc biệt trong ngày:\n";
        for (SpecialSetHistory history : histories) {
            content += history.showHistory() + "\n";
        }
        WidgetBase.showDialogCanBeCopied(getActivity(),
                title, content.trim(), histories.get(0).getSpecialSet().showNumbers());
    }

    @Override
    public void ShowNumberArray(List<Integer> numbers, int typeOfNumber) {
        String show = "";
        for (int i = 0; i < numbers.size(); i++) {
            show += NumberBase.showNumberString(numbers.get(i), typeOfNumber) + " ";
        }
        edtNumberArray.setText(show);
    }

    @Override
    public void ShowNumberArrayCounter(int size) {
        tvArrayCounter.setText(size + " số");
    }

    @Override
    public void VerifyCoupleArraySuccess(String numbersArr) {
        IOFileBase.saveDataToFile(getActivity(), FileName.NUMBER_ARRAY, numbersArr, 0);
        RECEIVE_DATA = true;
        FragmentManager fm = getActivity().getSupportFragmentManager();
        fm.beginTransaction().show(MainActivity.fragment2).hide(MainActivity.active).commit();
        BottomNavigationView navigationView = getActivity().findViewById(R.id.bottom_navigation);
        navigationView.setSelectedItemId(R.id.itCreateNumbers);
        //finish();
    }

    @Override
    public void VerifyTriadArraySuccess(List<Number> numbers) {
        ShowTriadTable(numbers);
        Toast.makeText(getActivity(), "Nạp dữ liệu thành công!", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void ShowVerifyStringSuccess(List<Integer> numbers, int typeOfNumber) {
        String data = "";
        for (int i = 0; i < numbers.size(); i++) {
            data += NumberBase.showNumberString(numbers.get(i), typeOfNumber) + " ";
        }
        WidgetBase.copyToClipboard(getActivity(), "numbers", data.trim());
        Toast.makeText(getActivity(), "Đã xuất dữ liệu ra clipboard!", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void ShowSubJackpotList(List<Jackpot> jackpotList) {
        subJackpot = jackpotList;
        SetTextForSubJackpot(subJackpot, -1);
    }

    private void SetTextForSubJackpot(List<Jackpot> jackpotList, int myJackpot) {
        if (jackpotList.isEmpty()) {
            tvSubJackpot.setText("");
            return;
        }
        String myJackpotStr = "";
        if (myJackpot < 0) {
            myJackpotStr = "88???";
        } else {
            myJackpotStr = "88" + NumberBase.showNumberString(myJackpot, 3);
        }
        String show = "";
        for (int i = jackpotList.size() - 1; i >= 0; i--) {
            show += jackpotList.get(i).getJackpot() + "\n";
        }
        show += myJackpotStr;
        tvSubJackpot.setText(show);
    }

    @Override
    public void ShowTriadTable(List<Number> numbers) {
        hsThirdClaw.removeAllViews();
        hsThirdClaw.addView(CustomTableLayout.getChooseThirdClawTableLayout(getActivity()));
        hsNumberTable.removeAllViews();
        hsNumberTable.addView(CustomTableLayout.getNumberByThirdClawTableLayout(getActivity()));
        SetStartMatrix(numbers);
        SetCounterForAll();
        SetColorForThirdClawTextView(0);
        SetColorForNumberTextView(0);
        SetOnClickForNumberTextView(0);
        SetOnCLickForThirdClawTextView();
        viewModel.GetTriadList();
    }

    private void SetStartMatrix(List<Number> numbers) {
        matrix = new int[1000];
        for (int i = 0; i < numbers.size(); i++) {
            int number = numbers.get(i).getNumber();
            matrix[number] = numbers.get(i).getLevel();
        }
    }

    private void SetColorForThirdClawTextView(int thirdClaw) {
        // màu của 3 càng chỉ để hiển thị nên dùng setBackgroundResource
        TextView textView = viewParent.findViewById(IdStart.THIRD_CLAW + thirdClaw);
        textView.setBackgroundResource(R.drawable.cell_light_green_table);
        for (int i = 0; i < 10; i++) {
            if (i != thirdClaw) {
                TextView other = viewParent.findViewById(IdStart.THIRD_CLAW + i);
                other.setBackgroundResource(R.drawable.cell_pink_table);
            }
        }
    }

    private void SetOnCLickForThirdClawTextView() {
        for (int i = 0; i < 10; i++) {
            FrameLayout frameLayout = viewParent.findViewById(IdStart.THIRD_CLAW_PARENT + i);
            int thirdClaw = i;
            frameLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    hsNumberTable.removeAllViews();
                    hsNumberTable.addView(CustomTableLayout.
                            getNumberByThirdClawTableLayout(getActivity()));
                    SetColorForThirdClawTextView(thirdClaw);
                    SetColorForNumberTextView(thirdClaw);
                    SetOnClickForNumberTextView(thirdClaw);
                }
            });
        }
    }

    private void SetColorForNumberTextView(int thirdClaw) {
        for (int i = 0; i < Const.MAX_ROW_COUNT_TABLE; i++) {
            TextView textView = viewParent.findViewById(IdStart.NUMBERS_BY_THIRD_CLAW + i);
            int element = matrix[thirdClaw * 100 + i];
            if (element == 1) {
                // phải xét thẳng R.drawable lúc nạp giao diện đầu tiên
                textView.setBackgroundResource(R.drawable.cell_light_green_table);
            }
            if (element == 2) {
                textView.setBackgroundResource(R.drawable.cell_red_table);
            }
        }
    }

    private void SetOnClickForNumberTextView(int thirdClaw) {
        for (int i = 0; i < Const.MAX_ROW_COUNT_TABLE; i++) {
            TextView textView = viewParent.findViewById(IdStart.NUMBERS_BY_THIRD_CLAW + i);
            int number = thirdClaw * 100 + i;
            textView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (matrix[number] == 1) {
                        textView.setBackgroundResource(pink);
                        SetTextForSubJackpot(subJackpot, -1);
                        matrix[number] = 0;
                        SetCounterForAll();
                    } else {
                        textView.setBackgroundResource(green);
                        SetTextForSubJackpot(subJackpot, number);
                        matrix[number] = 1;
                        SetCounterForAll();
                    }
                }
            });
            textView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    String mess = matrix[number] == 2 ? "Bạn muốn hủy số đặc biệt " + number +
                            " không?" : "Bạn có muốn chọn số " + number + " làm số đặc biệt không?";
                    new AlertDialog.Builder(getActivity())
                            .setTitle("Hành động.")
                            .setMessage(mess)
                            .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    if (matrix[number] == 2) {
                                        textView.setBackgroundResource(pink);
                                        SetTextForSubJackpot(subJackpot, -1);
                                        matrix[number] = 0;
                                        SetCounterForAll();
                                    } else {
                                        textView.setBackgroundResource(red);
                                        SetTextForSubJackpot(subJackpot, number);
                                        matrix[number] = 2;
                                        SetCounterForAll();
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

    private void SetCounterForAll() {
        int countAll = 0;
        int countThirdClaw[] = new int[10];
        for (int i = 0; i < 1000; i++) {
            if (matrix[i] > 0) {
                countAll++;
                countThirdClaw[i / 100]++;
            }
        }
        tvNumberCounter.setText(countAll + " số được chọn");
        for (int i = 0; i < 10; i++) {
            TextView textView = viewParent.findViewById(IdStart.THIRD_CLAW_COUNTER + i);
            textView.setText(countThirdClaw[i] + "");
        }
    }

    @Override
    public void SaveDataSuccess(String message) {
        Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
        viewModel.GetTriadList();
    }

    @Override
    public void ShowTriadList(List<Number> numbers) {
        String show = "Dàn số 3 càng (đã lưu): \n";
        for (int i = 0; i < 10; i++) {
            show += " - Đầu " + i + ": ";
            for (int j = 0; j < numbers.size(); j++) {
                int number = numbers.get(j).getNumber();
                if (number / 100 == i) {
                    show += NumberBase.showNumberString(number, 3) + " ";
                }
            }
            if (i != 9) {
                show += "\n";
            }
        }
        tvShowTriadList.setText(show);
    }

}
