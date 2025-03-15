package com.example.couple.View.Main.CreateNumberArray;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.example.couple.Model.Origin.Jackpot;
import com.example.couple.R;
import com.example.couple.View.Main.MainActivity;
import com.example.couple.ViewModel.Main.CreateNumberArray.QuickNumberGeneratorViewModel;

import java.util.List;
import java.util.Objects;

public class QuickNumberGeneratorDialog extends DialogFragment implements QuickNumberGeneratorView {
    TextView tvLongBeatBridge, tvFixedKB2Bridge, tvLongBeatKB2Bridge, tvRepeatKB2Bridge, tvBranchNextDayBridge;
    TextView tvRecentCouple, tvRecentBranch, tvConnectedTouchState, tvCombineTouchState, tvMappingState, tvBigDoubleState, tvDoubleState;
    EditText edtBranch;
    CheckBox cboCombineTouchBridge, cboConnectedBridge, cboMappingBridge, cboEstimatedBridge, cboBigDoubleSet, cboSameDoubleSet;
    TextView tvCopy, tvCancel;
    MainActivity activity;
    QuickNumberGeneratorViewModel viewModel;

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

        // Ánh xạ EditText
        edtBranch = view.findViewById(R.id.edtBranch);

        // Ánh xạ CheckBox
        cboCombineTouchBridge = view.findViewById(R.id.cboCombineTouchBridge);
        cboConnectedBridge = view.findViewById(R.id.cboConnectedBridge);
        cboMappingBridge = view.findViewById(R.id.cboMappingBridge);
        cboEstimatedBridge = view.findViewById(R.id.cboEstimatedBridge);
        cboBigDoubleSet = view.findViewById(R.id.cboBigDoubleSet);
        cboSameDoubleSet = view.findViewById(R.id.cboSameDoubleSet);

        // Ánh xạ các nút bấm
        tvCopy = view.findViewById(R.id.tvCopy);
        tvCancel = view.findViewById(R.id.tvCancel);

        viewModel = new QuickNumberGeneratorViewModel(this, activity);

        List<Jackpot> jackpotList = activity.getJackpotList().getValue();
        if (jackpotList == null) return view;
        showRecentCouple(jackpotList);
        showRecentBranch(jackpotList);
        //viewModel.getCombineTouchState(jackpotList);

        return view;
    }

    private void showRecentCouple(List<Jackpot> jackpotList) {
        if (jackpotList == null) return;
        if (jackpotList.isEmpty()) {
            tvRecentCouple.setVisibility(View.GONE);
        } else {
            tvRecentCouple.setVisibility(View.VISIBLE);
            int min = Math.min(jackpotList.size(), 3);
            String show = "Các số gần đây:";
            for (int i = min; i >= 0; i--) {
                show += jackpotList.get(i).getCouple().show() + (i == 0 ? "" : ",");
            }
            tvRecentCouple.setText(show);
        }
    }

    private void showRecentBranch(List<Jackpot> jackpotList) {
        if (jackpotList == null) return;
        if (jackpotList.isEmpty()) {
            tvRecentBranch.setVisibility(View.GONE);
        } else {
            tvRecentBranch.setVisibility(View.VISIBLE);
            int min = Math.min(jackpotList.size(), 3);
            String show = "Các chi gần đây:";
            for (int i = min; i >= 0; i--) {
                show += jackpotList.get(i).getDayCycle().getBranch().getPosition() + (i == 0 ? "" : ",");
            }
            tvRecentCouple.setText(show);
        }
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

    @Override
    public void showMessage(String message) {
        Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
    }
}