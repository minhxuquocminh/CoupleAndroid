package com.example.couple.View.Main.CreateNumberArray;

import android.content.Context;
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

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Observer;

import com.example.couple.Base.Handler.NumberBase;
import com.example.couple.Base.View.DialogBase;
import com.example.couple.Base.View.WidgetBase;
import com.example.couple.Custom.Const.Const;
import com.example.couple.Custom.Const.IdStart;
import com.example.couple.Custom.Widget.CustomTableLayout;
import com.example.couple.Model.Couple.CoupleType;
import com.example.couple.Model.Display.Picker;
import com.example.couple.Model.Handler.Input;
import com.example.couple.Model.Origin.Jackpot;
import com.example.couple.Model.Support.PeriodHistory;
import com.example.couple.R;
import com.example.couple.View.Bridge.BridgeCombinationActivity;
import com.example.couple.View.Bridge.SelectiveBridgeActivity;
import com.example.couple.View.BridgeHistory.SexagenaryCycleActivity;
import com.example.couple.View.JackpotStatistics.JackpotByYearActivity;
import com.example.couple.View.Main.MainActivity;
import com.example.couple.ViewModel.Main.CreateNumberArray.CreateNumberArrayViewModel;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.List;

public class CreateNumberArrayFragment extends Fragment implements CreateNumberArrayView {
    TextView tvViewBridge;
    TextView tvViewHistory;
    TextView tvViewCycle;
    TextView tvReference;
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
    int[] matrix = new int[1000];
    public static Boolean RECEIVE_DATA;

    MainActivity activity;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        activity = (MainActivity) context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        viewParent = inflater.inflate(R.layout.fragment_create_number_array, container, false);

        tvViewBridge = viewParent.findViewById(R.id.tvViewBridge);
        tvViewHistory = viewParent.findViewById(R.id.tvViewHistory);
        tvViewCycle = viewParent.findViewById(R.id.tvViewCycle);
        tvReference = viewParent.findViewById(R.id.tvReference);
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

        activity.getJackpotList().observe(getViewLifecycleOwner(), new Observer<List<Jackpot>>() {
            @Override
            public void onChanged(List<Jackpot> jackpotList) {
                showJackpotList(jackpotList);
            }
        });

        viewModel = new CreateNumberArrayViewModel(this, getActivity());
        RECEIVE_DATA = false;
        viewModel.getTriadTable();

        pink = R.drawable.cell_pink_table;
        green = R.drawable.cell_light_green_table;
        red = R.drawable.cell_red_table;

        tvViewBridge.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                WidgetBase.hideKeyboard(requireActivity());
                startActivity(new Intent(getActivity(), BridgeCombinationActivity.class));
            }
        });

        tvViewHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                WidgetBase.hideKeyboard(requireActivity());
                viewModel.getPeriodHistory();
            }
        });

        tvViewCycle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                WidgetBase.hideKeyboard(requireActivity());
                startActivity(new Intent(getActivity(), SexagenaryCycleActivity.class));
            }
        });

        tvReference.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                WidgetBase.hideKeyboard(requireActivity());
                startActivity(new Intent(getActivity(), SelectiveBridgeActivity.class));
            }
        });

        tvCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                WidgetBase.hideKeyboard(requireActivity());
                List<Input> inputs = new ArrayList<>();
                inputs.add(new Input(CoupleType.SET, edtSet.getText().toString().trim(), 2));
                inputs.add(new Input(CoupleType.TOUCH, edtTouch.getText().toString().trim(), 1));
                inputs.add(new Input(CoupleType.SUM, edtSum.getText().toString().trim(), 1));
                inputs.add(new Input(CoupleType.ADD_TRIAD, edtThirdClaw.getText().toString().trim(), 1));
                inputs.add(new Input(CoupleType.HEAD, edtHead.getText().toString().trim(), 1));
                inputs.add(new Input(CoupleType.TAIL, edtTail.getText().toString().trim(), 1));
                inputs.add(new Input(CoupleType.ADD, edtAddingNumber.getText().toString().trim(), 2));
                inputs.add(new Input(CoupleType.REMOVE, edtRemovingNumber.getText().toString().trim(), 2));
                inputs.add(new Input(CoupleType.COMBINE, edtCombineNumber.getText().toString().trim(), 2));
                viewModel.createNumberArray(inputs);
            }
        });

        tvClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                WidgetBase.hideKeyboard(requireActivity());
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
                viewModel.getNumberArrayCounter(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        tvExportCouple.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                WidgetBase.hideKeyboard(requireActivity());
                String numberArray = edtNumberArray.getText().toString().trim();
                viewModel.verifyCoupleArray(numberArray);
            }
        });

        tvExportTriad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                WidgetBase.hideKeyboard(requireActivity());
                String numberArray = edtNumberArray.getText().toString().trim();
                viewModel.verifyTriadArray(numberArray);
            }
        });

        tvExport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                WidgetBase.hideKeyboard(requireActivity());
                String message = "Bạn có muốn xuất dữ liệu ra clipboard không?";
                DialogBase.showWithConfirmation(getActivity(), "Xuất?", message, () -> {
                    String numberArray = edtNumberArray.getText().toString().trim();
                    viewModel.verifyString(numberArray);
                });
            }
        });

        tvViewJackpot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                WidgetBase.hideKeyboard(requireActivity());
                startActivity(new Intent(getActivity(),
                        JackpotByYearActivity.class));
            }
        });

        imgClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showTriadTable(new ArrayList<>());
            }
        });

        imgSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String title = "Lưu?";
                String message = "Bạn có muốn lưu dữ liệu vào CSDL không?";
                DialogBase.showWithConfirmation(getActivity(), title, message, () -> {
                    List<Picker> pickers = new ArrayList<>();
                    for (int i = 0; i < 1000; i++) {
                        if (matrix[i] > 0) {
                            pickers.add(new Picker(i, matrix[i]));
                        }
                    }
                    viewModel.saveDataToFile(pickers);
                });
            }
        });

        imgExport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String data = "";
                for (int i = 0; i < 1000; i++) {
                    if (matrix[i] > 0) {
                        data += NumberBase.showNumberString(i, 3) + " ";
                    }
                }
                String title = "Xuất?";
                String message = "Bạn có muốn xuất dữ liệu ra clipboard không?";
                DialogBase.showWithCopiedText(getActivity(), title, message, data.trim(), "dữ liệu");
            }
        });

        return viewParent;
    }

    private void showJackpotList(List<Jackpot> jackpotList) {
        subJackpot = jackpotList.subList(0, 5);
        SetTextForSubJackpot(subJackpot, -1);
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

    @Override
    public void showNumberArray(List<Integer> numbers, int typeOfNumber) {
        String show = "";
        for (int i = 0; i < numbers.size(); i++) {
            show += NumberBase.showNumberString(numbers.get(i), typeOfNumber) + " ";
        }
        edtNumberArray.setText(show);
    }

    @Override
    public void showNumberArrayCounter(int size) {
        tvArrayCounter.setText(size + " số");
    }

    @Override
    public void verifyCoupleArraySuccess(List<Integer> couples) {
        activity.getCouplesToTransfer().setValue(couples);
        FragmentManager fm = getParentFragmentManager();
        fm.beginTransaction().show(MainActivity.fragment2).hide(MainActivity.active).commit();
        BottomNavigationView navigationView = activity.findViewById(R.id.bottom_navigation);
        navigationView.setSelectedItemId(R.id.itNumberPicker);
    }

    @Override
    public void verifyTriadArraySuccess(List<Integer> numbers) {
        List<Picker> pickers = new ArrayList<>();
        for (int num : numbers) {
            pickers.add(new Picker(num, 1));
        }
        showTriadTable(pickers);
        Toast.makeText(getActivity(), "Nạp dữ liệu thành công!", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showVerifyStringSuccess(List<Integer> numbers, int typeOfNumber) {
        String data = "";
        for (int i = 0; i < numbers.size(); i++) {
            data += NumberBase.showNumberString(numbers.get(i), typeOfNumber) + " ";
        }
        WidgetBase.copyToClipboard(requireActivity(), "numbers", data.trim());
        Toast.makeText(getActivity(), "Đã xuất dữ liệu ra clipboard!", Toast.LENGTH_SHORT).show();
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
    public void showTriadTable(List<Picker> pickers) {
        hsThirdClaw.removeAllViews();
        hsThirdClaw.addView(CustomTableLayout.getChooseThirdClawTableLayout(getActivity()));
        hsNumberTable.removeAllViews();
        hsNumberTable.addView(CustomTableLayout.getNumberByThirdClawTableLayout(getActivity()));
        SetStartMatrix(pickers);
        SetCounterForAll();
        SetColorForThirdClawTextView(0);
        SetColorForNumberTextView(0);
        SetOnClickForNumberTextView(0);
        SetOnCLickForThirdClawTextView();
        viewModel.getTriadList();
    }

    private void SetStartMatrix(List<Picker> pickers) {
        matrix = new int[1000];
        for (int i = 0; i < pickers.size(); i++) {
            int number = pickers.get(i).getNumber();
            matrix[number] = pickers.get(i).getLevel();
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
                    String title = "Hành động.";
                    String mess = matrix[number] == 2 ? "Bạn muốn hủy số đặc biệt " + number +
                            " không?" : "Bạn có muốn chọn số " + number + " làm số đặc biệt không?";
                    DialogBase.showWithConfirmation(getActivity(), title, mess, () -> {
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
                    });
                    return false;
                }
            });
        }
    }

    private void SetCounterForAll() {
        int countAll = 0;
        int[] countThirdClaw = new int[10];
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
    public void saveDataSuccess(String message) {
        Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
        viewModel.getTriadList();
    }

    @Override
    public void showTriadList(List<Picker> pickers) {
        String show = "Dàn số 3 càng (đã lưu): \n";
        for (int i = 0; i < 10; i++) {
            show += " - Đầu " + i + ": ";
            for (int j = 0; j < pickers.size(); j++) {
                int number = pickers.get(j).getNumber();
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
