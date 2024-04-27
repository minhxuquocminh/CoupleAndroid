package com.example.couple.View.Main.FunctionDisplay;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import com.example.couple.R;
import com.example.couple.View.Bridge.FindingBridgeActivity;
import com.example.couple.View.BridgeHistory.SpecialSetsHistoryActivity;
import com.example.couple.View.Couple.BalanceCoupleActivity;
import com.example.couple.View.JackpotStatistics.CoupleByYearActivity;
import com.example.couple.View.JackpotStatistics.JackpotAllYearActivity;
import com.example.couple.View.JackpotStatistics.JackpotByYearActivity;
import com.example.couple.View.JackpotStatistics.JackpotNextDayActivity;
import com.example.couple.View.JackpotStatistics.JackpotThisYearActivity;
import com.example.couple.View.Lottery.LotteryActivity;

public class FunctionDisplayFragment extends Fragment {
    CardView cvViewLottery;
    CardView cvLotteryFindingBridge;
    CardView cvViewJackpot;
    CardView cvBalanceCouple;
    CardView cvJackpotReference;
    CardView cvJackpotNextDay;
    CardView cvAppearanceJackpot;
    CardView cvJackpotThisYear;
    CardView cvGeneralJackpot;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_function_display, container, false);

        cvViewLottery = view.findViewById(R.id.cvViewLottery);
        cvLotteryFindingBridge = view.findViewById(R.id.cvLotteryFindingBridge);
        cvViewJackpot = view.findViewById(R.id.cvViewJackpot);
        cvBalanceCouple = view.findViewById(R.id.cvBalanceCouple);
        cvJackpotReference = view.findViewById(R.id.cvJackpotReference);
        cvJackpotNextDay = view.findViewById(R.id.cvJackpotNextDay);
        cvAppearanceJackpot = view.findViewById(R.id.cvAppearanceJackpot);
        cvJackpotThisYear = view.findViewById(R.id.cvJackpotThisYear);
        cvGeneralJackpot = view.findViewById(R.id.cvGeneralJackpot);

        cvViewLottery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), LotteryActivity.class));
            }
        });

        cvLotteryFindingBridge.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), FindingBridgeActivity.class));
            }
        });

        cvViewJackpot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), JackpotByYearActivity.class));
            }
        });

        cvBalanceCouple.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), BalanceCoupleActivity.class));
            }
        });

        cvJackpotReference.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), SpecialSetsHistoryActivity.class));
            }
        });

        cvJackpotNextDay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), JackpotNextDayActivity.class));
            }
        });

        cvAppearanceJackpot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), CoupleByYearActivity.class));
            }
        });

        cvJackpotThisYear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), JackpotThisYearActivity.class));
            }
        });

        cvGeneralJackpot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), JackpotAllYearActivity.class));
            }
        });

        return view;
    }
}
