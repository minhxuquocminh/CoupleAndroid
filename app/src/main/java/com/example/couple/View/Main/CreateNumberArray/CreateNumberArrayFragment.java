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
import com.example.couple.Base.Handler.StorageBase;
import com.example.couple.Base.View.DialogBase;
import com.example.couple.Base.View.Table.TableLayoutBase;
import com.example.couple.Base.View.WidgetBase;
import com.example.couple.Custom.Const.Const;
import com.example.couple.Custom.Const.IdStart;
import com.example.couple.Custom.Enum.StorageType;
import com.example.couple.Custom.Handler.Display.ArrayUtil;
import com.example.couple.Custom.Handler.Display.MatrixUtil;
import com.example.couple.Model.Handler.Input;
import com.example.couple.Model.Handler.InputType;
import com.example.couple.Model.Handler.Picker;
import com.example.couple.Model.Origin.Jackpot;
import com.example.couple.R;
import com.example.couple.View.Bridge.BridgeCombinationActivity;
import com.example.couple.View.Bridge.SelectiveBridgeActivity;
import com.example.couple.View.BridgeHistory.SexagenaryCycleActivity;
import com.example.couple.View.JackpotStatistics.JackpotByYearActivity;
import com.example.couple.View.Main.MainActivity;
import com.example.couple.ViewModel.Main.CreateNumberArray.CreateNumberArrayViewModel;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class CreateNumberArrayFragment extends Fragment implements CreateNumberArrayView {
    TextView tvViewCombineBridge;
    TextView tvViewCycle;
    TextView tvReference;
    TextView tvQuickCreate;
    EditText edtSet;
    EditText edtTouch;
    EditText edtSum;
    EditText edtBranch;
    EditText edtHead;
    EditText edtTail;
    EditText edtCombineNumber;
    EditText edtAddingNumber;
    EditText edtRemovingNumber;
    EditText edtThirdClaw;
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

    private static final int def = 0;
    private static final int green = R.color.colorLightGreen;
    private static final int red = R.color.colorImportantText;

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

        tvViewCombineBridge = viewParent.findViewById(R.id.tvViewCombineBridge);
        tvViewCycle = viewParent.findViewById(R.id.tvViewCycle);
        tvReference = viewParent.findViewById(R.id.tvReference);
        tvQuickCreate = viewParent.findViewById(R.id.tvQuickCreate);
        edtSet = viewParent.findViewById(R.id.edtSet);
        edtTouch = viewParent.findViewById(R.id.edtTouch);
        edtSum = viewParent.findViewById(R.id.edtSum);
        edtBranch = viewParent.findViewById(R.id.edtBranch);
        edtHead = viewParent.findViewById(R.id.edtHead);
        edtTail = viewParent.findViewById(R.id.edtTail);
        edtCombineNumber = viewParent.findViewById(R.id.edtCombineNumber);
        edtAddingNumber = viewParent.findViewById(R.id.edtAddingNumber);
        edtRemovingNumber = viewParent.findViewById(R.id.edtRemovingNumber);
        edtThirdClaw = viewParent.findViewById(R.id.edtThirdClaw);
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

        setInputData();
        clearEditText();

        viewModel = new CreateNumberArrayViewModel(this, getActivity());
        RECEIVE_DATA = false;
        viewModel.getTriadTable();

        tvQuickCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                WidgetBase.hideKeyboard(requireActivity());
                QuickNumberGeneratorDialog dialog = new QuickNumberGeneratorDialog();
                dialog.setOnDialogDismissListener(new QuickNumberGeneratorDialog.OnDialogDismissListener() {
                    @Override
                    public void onDialogDismiss() {
                        setInputData();
                    }
                });
                dialog.show(activity.getSupportFragmentManager(), "QuickNumberGeneratorDialog");
            }
        });

        tvViewCombineBridge.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                WidgetBase.hideKeyboard(requireActivity());
                startActivity(new Intent(getActivity(), BridgeCombinationActivity.class));
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
                inputs.add(new Input(InputType.SET, edtSet.getText().toString().trim(), 2));
                inputs.add(new Input(InputType.TOUCH, edtTouch.getText().toString().trim(), 1));
                inputs.add(new Input(InputType.SUM, edtSum.getText().toString().trim(), 1));
                inputs.add(new Input(InputType.BRANCH, edtBranch.getText().toString().trim(), 2));
                inputs.add(new Input(InputType.HEAD, edtHead.getText().toString().trim(), 1));
                inputs.add(new Input(InputType.TAIL, edtTail.getText().toString().trim(), 1));
                inputs.add(new Input(InputType.COMBINE, edtCombineNumber.getText().toString().trim(), 2));
                inputs.add(new Input(InputType.ADD, edtAddingNumber.getText().toString().trim(), 2));
                inputs.add(new Input(InputType.REMOVE, edtRemovingNumber.getText().toString().trim(), 2));
                inputs.add(new Input(InputType.ADD_TRIAD, edtThirdClaw.getText().toString().trim(), 1));
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
                edtBranch.setText("");
                edtHead.setText("");
                edtTail.setText("");
                edtCombineNumber.setText("");
                edtAddingNumber.setText("");
                edtRemovingNumber.setText("");
                edtThirdClaw.setText("");
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

    private void clearEditText() {
        Arrays.asList(
                edtSet,
                edtTouch,
                edtSum,
                edtBranch,
                edtHead,
                edtTail,
                edtCombineNumber,
                edtAddingNumber,
                edtRemovingNumber,
                edtThirdClaw
        ).forEach(editText -> {
            editText.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    DialogBase.showWithConfirmation(requireActivity(), "Xoá", "Bạn có muốn xóa tất cả dữ liệu ko?", () -> {
                        editText.setText("");
                    });
                    return false;
                }
            });
        });
    }

    private void setInputData() {
        List<Integer> touches = StorageBase.getNumberList(requireActivity(), StorageType.LIST_OF_TOUCHES);
        edtTouch.setText(touches.stream().map(x -> x + "").collect(Collectors.joining(" ")));
        List<Integer> branches = StorageBase.getNumberList(requireActivity(), StorageType.LIST_OF_BRANCHES);
        edtBranch.setText(branches.stream().map(x -> x + "").collect(Collectors.joining(" ")));
    }

    private void showJackpotList(List<Jackpot> jackpotList) {
        subJackpot = jackpotList.subList(0, 5);
        setTextForSubJackpot(subJackpot, -1);
    }

    @Override
    public void showMessage(String message) {
        Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
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

    private void setTextForSubJackpot(List<Jackpot> jackpotList, int myJackpot) {
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
        hsThirdClaw.addView(TableLayoutBase.getPickerTableLayoutHaveSuperscript(getActivity(),
                ArrayUtil.getTenIntArray(IdStart.THIRD_CLAW_PARENT), ArrayUtil.getTenStringArray(),
                ArrayUtil.getTenIntArray(IdStart.THIRD_CLAW), ArrayUtil.getZeroStringArray(),
                ArrayUtil.getTenIntArray(IdStart.THIRD_CLAW_COUNTER), 10, false));
        hsNumberTable.removeAllViews();
        hsNumberTable.addView(TableLayoutBase.getPickerTableLayout(getActivity(), MatrixUtil.getTenTenStringMatrix(),
                MatrixUtil.getTenTenIntMatrix(IdStart.NUMBERS_BY_THIRD_CLAW), 10, 10, false));
        setStartMatrix(pickers);
        setCounterForAll();
        setColorForThirdClawTextView(0);
        setColorForNumberTextView(0);
        setOnClickForNumberTextView(0);
        setOnCLickForThirdClawTextView();
        viewModel.getTriadList();
    }

    private void setStartMatrix(List<Picker> pickers) {
        matrix = new int[1000];
        for (int i = 0; i < pickers.size(); i++) {
            int number = pickers.get(i).getNumber();
            matrix[number] = pickers.get(i).getLevel();
        }
    }

    private void setColorForThirdClawTextView(int thirdClaw) {
        // màu của 3 càng chỉ để hiển thị nên dùng setBackgroundResource
        TextView textView = viewParent.findViewById(IdStart.THIRD_CLAW + thirdClaw);
        textView.setBackgroundResource(green);
        for (int i = 0; i < 10; i++) {
            if (i != thirdClaw) {
                TextView other = viewParent.findViewById(IdStart.THIRD_CLAW + i);
                other.setBackgroundResource(def);
            }
        }
    }

    private void setOnCLickForThirdClawTextView() {
        for (int i = 0; i < 10; i++) {
            FrameLayout frameLayout = viewParent.findViewById(IdStart.THIRD_CLAW_PARENT + i);
            int thirdClaw = i;
            frameLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    setColorForThirdClawTextView(thirdClaw);
                    setColorForNumberTextView(thirdClaw);
                    setOnClickForNumberTextView(thirdClaw);
                }
            });
        }
    }

    private void setColorForNumberTextView(int thirdClaw) {
        for (int i = 0; i < Const.MAX_ROW_COUNT_TABLE; i++) {
            TextView textView = viewParent.findViewById(IdStart.NUMBERS_BY_THIRD_CLAW + i);
            int element = matrix[thirdClaw * 100 + i];
            if (element == 1) {
                textView.setBackgroundResource(green);
            } else if (element == 2) {
                textView.setBackgroundResource(red);
            } else {
                textView.setBackgroundResource(def);
            }
        }
    }

    private void setOnClickForNumberTextView(int thirdClaw) {
        for (int i = 0; i < Const.MAX_ROW_COUNT_TABLE; i++) {
            TextView textView = viewParent.findViewById(IdStart.NUMBERS_BY_THIRD_CLAW + i);
            int number = thirdClaw * 100 + i;
            textView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (matrix[number] == 1) {
                        textView.setBackgroundResource(def);
                        setTextForSubJackpot(subJackpot, -1);
                        matrix[number] = 0;
                        setCounterForAll();
                    } else {
                        textView.setBackgroundResource(green);
                        setTextForSubJackpot(subJackpot, number);
                        matrix[number] = 1;
                        setCounterForAll();
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
                            textView.setBackgroundResource(def);
                            setTextForSubJackpot(subJackpot, -1);
                            matrix[number] = 0;
                            setCounterForAll();
                        } else {
                            textView.setBackgroundResource(red);
                            setTextForSubJackpot(subJackpot, number);
                            matrix[number] = 2;
                            setCounterForAll();
                        }
                    });
                    return false;
                }
            });
        }
    }

    private void setCounterForAll() {
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
