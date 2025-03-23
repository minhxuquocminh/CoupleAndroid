package com.example.couple.View.Main.CreateNumberArray;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.example.couple.Base.Handler.CoupleBase;
import com.example.couple.Base.Handler.NumberBase;
import com.example.couple.Base.Handler.StorageBase;
import com.example.couple.Base.View.WidgetBase;
import com.example.couple.Custom.Enum.StorageType;
import com.example.couple.Custom.Handler.NumberArrayHandler;
import com.example.couple.Model.Bridge.Bridge;
import com.example.couple.Model.Bridge.BridgeType;
import com.example.couple.Model.Bridge.CombineBridge;
import com.example.couple.Model.Bridge.NumberSet.NumberSetHistory;
import com.example.couple.Model.DateTime.Date.Cycle.Branch;
import com.example.couple.Model.Origin.Jackpot;
import com.example.couple.Model.Origin.Lottery;
import com.example.couple.R;
import com.example.couple.View.Main.MainActivity;
import com.example.couple.ViewModel.Main.CreateNumberArray.QuickNumberGeneratorViewModel;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class QuickNumberGeneratorDialog extends DialogFragment implements QuickNumberGeneratorView {
    TextView tvLongBeatBridge, tvFixedKB2Bridge, tvLongBeatKB2Bridge, tvRepeatKB2Bridge, tvBranchNextDayBridge;
    TextView tvRecentCouple, tvRecentBranch, tvConnectedTouchState, tvCombineTouchState, tvMappingState, tvBigDoubleState, tvDoubleState;
    CheckBox[] cboBranches = new CheckBox[12];
    CheckBox cboCombineTouchBridge, cboConnectedBridge, cboMappingBridge, cboEstimatedBridge, cboBigDoubleSet, cboSameDoubleSet;
    TextView tvNumberSize;
    TextView tvCopy, tvCancel;
    MainActivity activity;
    QuickNumberGeneratorViewModel viewModel;
    Map<BridgeType, Bridge> bridgeMap = new HashMap<>();

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        activity = (MainActivity) context;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_quick_number_generator, container, false);

        // Ánh xạ các TextView
        tvLongBeatBridge = view.findViewById(R.id.tvLongBeatBridge);
        tvFixedKB2Bridge = view.findViewById(R.id.tvFixedKB2Bridge);
        tvLongBeatKB2Bridge = view.findViewById(R.id.tvLongBeatKB2Bridge);
        tvRepeatKB2Bridge = view.findViewById(R.id.tvRepeatKB2Bridge);
        tvBranchNextDayBridge = view.findViewById(R.id.tvBranchNextDayBridge);

        tvRecentCouple = view.findViewById(R.id.tvRecentCouple);
        tvRecentBranch = view.findViewById(R.id.tvRecentBranch);
        tvConnectedTouchState = view.findViewById(R.id.tvConnectedTouchState);
        tvCombineTouchState = view.findViewById(R.id.tvCombineTouchState);
        tvMappingState = view.findViewById(R.id.tvMappingState);
        tvBigDoubleState = view.findViewById(R.id.tvBigDoubleState);
        tvDoubleState = view.findViewById(R.id.tvDoubleState);

        // Ánh xạ Checkbox
        for (int i = 0; i < 12; i++) {
            int resId = getResources().getIdentifier("cboBranch" + i, "id", requireActivity().getPackageName());
            cboBranches[i] = view.findViewById(resId);
        }

        // Ánh xạ CheckBox
        cboCombineTouchBridge = view.findViewById(R.id.cboCombineTouchBridge);
        cboConnectedBridge = view.findViewById(R.id.cboConnectedBridge);
        cboMappingBridge = view.findViewById(R.id.cboMappingBridge);
        cboEstimatedBridge = view.findViewById(R.id.cboEstimatedBridge);
        cboBigDoubleSet = view.findViewById(R.id.cboBigDoubleSet);
        cboSameDoubleSet = view.findViewById(R.id.cboSameDoubleSet);

        tvNumberSize = view.findViewById(R.id.tvNumberSize);

        // Ánh xạ các nút bấm
        tvCopy = view.findViewById(R.id.tvCopy);
        tvCancel = view.findViewById(R.id.tvCancel);

        viewModel = new QuickNumberGeneratorViewModel(this, activity);
        bridgeMap = new HashMap<>();
        setAllCheckbox();

        List<Jackpot> jackpotList = activity.getJackpotList().getValue();
        List<Lottery> lotteries = activity.getLotteryList().getValue();
        if (jackpotList == null || lotteries == null) return view;
        showRecentCouple(jackpotList);
        showRecentBranch(jackpotList);
        viewModel.getCombineBridgesToday(jackpotList, lotteries,
                new HashSet<>(Arrays.asList(BridgeType.COMBINE_TOUCH, BridgeType.CONNECTED, BridgeType.MAPPING,
                        BridgeType.ESTIMATED, BridgeType.BIG_DOUBLE, BridgeType.SAME_DOUBLE)));
        viewModel.getLongBeatNumbers(jackpotList);
        viewModel.getMappingAndTouchState(jackpotList, lotteries);
        viewModel.getSetsState(jackpotList);

        IntStream.range(0, 12).forEach(i -> {
            cboBranches[i].setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                    getNumbers();
                }
            });
        });

        cboConnectedBridge.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                getNumbers();
            }
        });

        cboCombineTouchBridge.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                getNumbers();
            }
        });

        cboMappingBridge.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                getNumbers();
            }
        });

        cboEstimatedBridge.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                getNumbers();
            }
        });

        cboBigDoubleSet.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                getNumbers();
            }
        });

        cboSameDoubleSet.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                getNumbers();
            }
        });

        tvCopy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                WidgetBase.hideKeyboard(requireActivity());
                List<Integer> numbers = getNumbers();
                if (numbers.isEmpty()) {
                    showMessage("Không tìm thấy số nào.");
                    return;
                }
                WidgetBase.copyToClipboard(requireActivity(), "numbers",
                        numbers.stream().map(CoupleBase::showCouple).collect(Collectors.joining(" ")));
                dismiss();
            }
        });

        tvCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                WidgetBase.hideKeyboard(requireActivity());
                dismiss();
            }
        });

        return view;
    }

    private void setAllCheckbox() {
        Set<Integer> branches = StorageBase.getNumberSet(requireActivity(), StorageType.SET_OF_BRANCHES);
        branches.forEach(branch -> cboBranches[branch].setChecked(true));
        Set<Integer> bridgeNumbers = StorageBase.getNumberSet(requireActivity(), StorageType.SET_OF_BRIDGES);
        if (bridgeNumbers.contains(BridgeType.COMBINE_TOUCH.value)) {
            cboCombineTouchBridge.setChecked(true);
        }
        if (bridgeNumbers.contains(BridgeType.CONNECTED.value)) {
            cboConnectedBridge.setChecked(true);
        }
        if (bridgeNumbers.contains(BridgeType.MAPPING.value)) {
            cboMappingBridge.setChecked(true);
        }
        if (bridgeNumbers.contains(BridgeType.ESTIMATED.value)) {
            cboEstimatedBridge.setChecked(true);
        }
        if (bridgeNumbers.contains(BridgeType.BIG_DOUBLE.value)) {
            cboBigDoubleSet.setChecked(true);
        }
        if (bridgeNumbers.contains(BridgeType.SAME_DOUBLE.value)) {
            cboSameDoubleSet.setChecked(true);
        }
        getNumbers();
    }

    private void showRecentCouple(List<Jackpot> jackpotList) {
        if (jackpotList == null) return;
        if (jackpotList.isEmpty()) {
            tvRecentCouple.setVisibility(View.GONE);
        } else {
            tvRecentCouple.setVisibility(View.VISIBLE);
            int min = Math.min(jackpotList.size(), 6);
            String show = "Các số gần đây: ";
            for (int i = min; i >= 0; i--) {
                show += jackpotList.get(i).getCouple().show() + ", ";
            }
            show += "xx";
            tvRecentCouple.setText(show);
        }
    }

    private void showRecentBranch(List<Jackpot> jackpotList) {
        if (jackpotList == null) return;
        if (jackpotList.isEmpty()) {
            tvRecentBranch.setVisibility(View.GONE);
        } else {
            tvRecentBranch.setVisibility(View.VISIBLE);
            int min = Math.min(jackpotList.size(), 8);
            String show = "Các chi gần đây: ";
            for (int i = min; i >= 0; i--) {
                Branch branch = new Branch(jackpotList.get(i).getCoupleInt());
                show += branch.getPosition() + ", ";
            }
            show += "x";
            tvRecentBranch.setText(show);
        }
    }

    private List<Integer> getNumbers() {
        Set<BridgeType> bridgeTypes = new HashSet<>();
        if (cboCombineTouchBridge.isChecked()) bridgeTypes.add(BridgeType.COMBINE_TOUCH);
        if (cboConnectedBridge.isChecked()) bridgeTypes.add(BridgeType.CONNECTED);
        if (cboMappingBridge.isChecked()) bridgeTypes.add(BridgeType.MAPPING);
        if (cboEstimatedBridge.isChecked()) bridgeTypes.add(BridgeType.ESTIMATED);
        if (cboBigDoubleSet.isChecked()) bridgeTypes.add(BridgeType.BIG_DOUBLE);
        if (cboSameDoubleSet.isChecked()) bridgeTypes.add(BridgeType.SAME_DOUBLE);

        List<Integer> matchs = new ArrayList<>();
        for (BridgeType bridgeType : bridgeTypes) {
            Bridge bridge = bridgeMap.get(bridgeType);
            if (bridge == null) continue;
            matchs = NumberBase.getMatchNumbers(matchs, bridge.getNumbers());
        }

        List<Integer> branches = IntStream.range(0, 12)
                .filter(i -> cboBranches[i].isChecked())
                .boxed()
                .collect(Collectors.toList());
        matchs = NumberBase.getMatchNumbers(matchs, NumberArrayHandler.getBranches(branches));
        tvNumberSize.setText(matchs.size() + " số được chọn.");
        return matchs;
    }

    @Override
    public void onStart() {
        super.onStart();
        // Lấy Window của Dialog
        Window window = Objects.requireNonNull(getDialog()).getWindow();
        if (window != null) {
            // Thiết lập chiều rộng 90% màn hình
            window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        }
    }

    public interface OnDialogDismissListener {
        void onDialogDismiss();
    }

    private OnDialogDismissListener dismissListener;

    public void setOnDialogDismissListener(OnDialogDismissListener listener) {
        this.dismissListener = listener;
    }

    @Override
    public void onDismiss(@NonNull DialogInterface dialog) {
        super.onDismiss(dialog);
        Set<Integer> branches = IntStream.range(0, 12)
                .filter(i -> cboBranches[i].isChecked())
                .boxed()
                .collect(Collectors.toSet());
        StorageBase.setNumberSet(requireActivity(), StorageType.SET_OF_BRANCHES, branches);
        Set<Integer> bridgeNumberSet = new HashSet<>();
        if (cboCombineTouchBridge.isChecked()) bridgeNumberSet.add(BridgeType.COMBINE_TOUCH.value);
        if (cboConnectedBridge.isChecked()) bridgeNumberSet.add(BridgeType.CONNECTED.value);
        if (cboMappingBridge.isChecked()) bridgeNumberSet.add(BridgeType.MAPPING.value);
        if (cboEstimatedBridge.isChecked()) bridgeNumberSet.add(BridgeType.ESTIMATED.value);
        if (cboBigDoubleSet.isChecked()) bridgeNumberSet.add(BridgeType.BIG_DOUBLE.value);
        if (cboSameDoubleSet.isChecked()) bridgeNumberSet.add(BridgeType.SAME_DOUBLE.value);
        StorageBase.setNumberSet(requireActivity(), StorageType.SET_OF_BRIDGES, bridgeNumberSet);
        if (dismissListener != null) {
            dismissListener.onDialogDismiss();
        }
    }

    @Override
    public void showMessage(String message) {
        Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showCombineBridgesToday(CombineBridge combineBridge) {
        bridgeMap = combineBridge.getBridgeMap();
        String combine = "kết hợp " + Objects.requireNonNull(bridgeMap.get(BridgeType.COMBINE_TOUCH)).showCompactNumbers();
        String connected = "liên thông " + Objects.requireNonNull(bridgeMap.get(BridgeType.CONNECTED)).showCompactNumbers();
        String mapping = "ánh xạ " + Objects.requireNonNull(bridgeMap.get(BridgeType.MAPPING)).getNumbers().size();
        String estimated = "ước lượng " + Objects.requireNonNull(bridgeMap.get(BridgeType.ESTIMATED)).getNumbers().size();
        cboCombineTouchBridge.setText(combine);
        cboConnectedBridge.setText(connected);
        cboMappingBridge.setText(mapping);
        cboEstimatedBridge.setText(estimated);
    }

    @Override
    public void showLongBeatNumbers(List<NumberSetHistory> histories) {
        if (histories.isEmpty()) {
            tvLongBeatBridge.setVisibility(View.GONE);
        } else {
            String show = "Các bộ đang gan: " +
                    histories.stream().map(NumberSetHistory::showCompact).collect(Collectors.joining(", "));
            tvLongBeatBridge.setText(show);
        }
    }

    @Override
    public void showMappingAndTouchState(List<CombineBridge> combineBridges) {
        String showConnected = "Cầu chạm 1: ";
        String showCombine = "Cầu chạm 2: ";
        String showMapping = "Cầu ánh xạ: ";
        for (int i = combineBridges.size() - 1; i >= 0; i--) {
            Bridge combine = combineBridges.get(i).getBridgeMap().get(BridgeType.COMBINE_TOUCH);
            showCombine += combine == null ? "" : (combine.isUncheckable() ? "x" : (combine.isWin() ? "1 " : "0 "));
            Bridge connected = combineBridges.get(i).getBridgeMap().get(BridgeType.CONNECTED);
            showConnected += connected == null ? "" : (connected.isUncheckable() ? "x" : (connected.isWin() ? "1 " : "0 "));
            if (i >= 8) continue;
            Bridge mapping = combineBridges.get(i).getBridgeMap().get(BridgeType.MAPPING);
            showMapping += mapping == null ? "" : (mapping.isUncheckable() ? mapping.getNumbers().size() + "?" :
                    (mapping.isWin() ? mapping.getNumbers().size() + " " : mapping.getNumbers().size() + "x "));
        }
        tvCombineTouchState.setText(showCombine);
        tvConnectedTouchState.setText(showConnected);
        tvMappingState.setText(showMapping);
    }

    @Override
    public void showSetsState(List<CombineBridge> combineBridges) {
        int count1 = 0;
        int count2 = 0;
        List<Integer> beats1 = new ArrayList<>();
        List<Integer> beats2 = new ArrayList<>();
        for (int i = combineBridges.size() - 1; i >= 0; i--) {
            count1++;
            Bridge doubleFlag = combineBridges.get(i).getBridgeMap().get(BridgeType.SAME_DOUBLE);
            if (doubleFlag != null && doubleFlag.isWin()) {
                beats1.add(count1);
                count1 = 0;
            }
            if (i >= 30) continue;
            count2++;
            Bridge bigDoubleFlag = combineBridges.get(i).getBridgeMap().get(BridgeType.BIG_DOUBLE);
            if (bigDoubleFlag != null && bigDoubleFlag.isWin()) {
                beats2.add(count2);
                count2 = 0;
            }
        }
        tvDoubleState.setText("Bộ kép bằng: " + beats1.stream().map(String::valueOf).collect(Collectors.joining(" ")) + "?");
        tvBigDoubleState.setText("Bộ kép to: " + beats2.stream().map(String::valueOf).collect(Collectors.joining(" ")) + "?");
    }

}