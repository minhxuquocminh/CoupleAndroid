package com.example.couple.View.Bridge;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.couple.Custom.Widget.SpeechToTextActivity;
import com.example.couple.Model.Bridge.Touch.ConnectedBridge;
import com.example.couple.Model.Bridge.Double.JackpotSign;
import com.example.couple.Model.Bridge.LongBeat.NearestTime;
import com.example.couple.Model.Bridge.Double.NumberDouble;
import com.example.couple.Model.Bridge.NumberSet.Set;
import com.example.couple.Model.Origin.Jackpot;
import com.example.couple.Model.Origin.Lottery;
import com.example.couple.Model.Bridge.Connected.ClawSupport;
import com.example.couple.Model.Bridge.TriadClaw.Single;
import com.example.couple.R;
import com.example.couple.View.Couple.BalanceCoupleActivity;
import com.example.couple.View.JackpotStatistics.CoupleByYearActivity;
import com.example.couple.ViewModel.Bridge.ReferenceBridgeViewModel;

import java.util.List;

public class ReferenceBridgeActivity extends SpeechToTextActivity implements ReferenceBridgeView {
    TextView tvJackpotToday;
    TextView tvLastJackpot;
    TextView tvViewBalanceCouple;
    TextView tvTouchBridge;
    TextView tvSpecialTouchBridge;
    TextView tvTouchThirdClaw;
    TextView tvViewFindingBridge;
    TextView tvConnectedBridge;
    TextView tvThirdClaw;
    TextView tvTriadBridge;
    TextView tvCancelTriadBridge;
    TextView tvSameDoubleTitle;
    TextView tvViewJackpot;
    TextView tvBeatSDB;
    TextView tvSignInLotterySDB;
    TextView tvSignInJackpot;
    TextView tvRareDoubleSame;
    TextView tvViewStatistics;
    TextView tvLastCouple;
    TextView tvRareNumberThisYear;
    TextView tvRareNumberLastYear;
    TextView tvRareHead;
    TextView tvRareTail;
    TextView tvHead;
    TextView tvTail;

    ReferenceBridgeViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reference_bridge);

        tvJackpotToday = findViewById(R.id.tvJackpotToday);
        tvLastJackpot = findViewById(R.id.tvLastJackpot);
        tvViewBalanceCouple = findViewById(R.id.tvViewBalanceCouple);
        tvTouchBridge = findViewById(R.id.tvTouchBridge);
        tvSpecialTouchBridge = findViewById(R.id.tvSpecialTouchBridge);
        tvTouchThirdClaw = findViewById(R.id.tvTouchThirdClaw);
        tvViewFindingBridge = findViewById(R.id.tvViewFindingBridge);
        tvConnectedBridge = findViewById(R.id.tvConnectedBridge);
        tvThirdClaw = findViewById(R.id.tvThirdClaw);
        tvTriadBridge = findViewById(R.id.tvTriadBridge);
        tvCancelTriadBridge = findViewById(R.id.tvCancelTriadBridge);
        tvSameDoubleTitle = findViewById(R.id.tvSameDoubleTitle);
        tvViewJackpot = findViewById(R.id.tvViewJackpot);
        tvBeatSDB = findViewById(R.id.tvBeatSDB);
        tvSignInLotterySDB = findViewById(R.id.tvSignInLotterySDB);
        tvSignInJackpot = findViewById(R.id.tvSignInJackpot);
        tvRareDoubleSame = findViewById(R.id.tvRareDoubleSame);
        tvViewStatistics = findViewById(R.id.tvViewStatistics);
        tvLastCouple = findViewById(R.id.tvLastCouple);
        tvRareNumberThisYear = findViewById(R.id.tvRareNumberThisYear);
        tvRareNumberLastYear = findViewById(R.id.tvRareNumberLastYear);
        tvRareHead = findViewById(R.id.tvRareHead);
        tvRareTail = findViewById(R.id.tvRareTail);
        tvHead = findViewById(R.id.tvHead);
        tvTail = findViewById(R.id.tvTail);

        viewModel = new ReferenceBridgeViewModel(this, this);

        viewModel.getJackpotList(365);
        viewModel.getJackpotListThisYear();
        viewModel.getJackpotListLastYear();
        viewModel.getLotteryList(22);
        viewModel.getJackpotListInManyDays(150);

        tvViewBalanceCouple.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ReferenceBridgeActivity.this,
                        BalanceCoupleActivity.class));
            }
        });

        tvViewFindingBridge.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ReferenceBridgeActivity.this,
                        FindingBridgeActivity.class));
            }
        });

        tvViewJackpot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ReferenceBridgeActivity.this,
                        FindingBridgeActivity.class));
            }
        });

        tvViewStatistics.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ReferenceBridgeActivity.this,
                        CoupleByYearActivity.class));
            }
        });


    }

    @Override
    public void showMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showJackpotList(List<Jackpot> jackpotList) {
        tvJackpotToday.setText("Xổ số Đặc Biệt hôm nay về: " + jackpotList.get(0).getJackpot());
        tvLastJackpot.setText("Xổ số Đ.Biệt ngày trước đó: " + jackpotList.get(1).getJackpot());
        viewModel.getTouchThirdClawBridge(jackpotList);
    }

    @Override
    public void showTouchThirdClawBridge(List<Single> singles, int frame) {
        String show = "Cầu càng 3 giải ĐB (khung " + frame + " ngày): ";
        for (int i = 0; i < singles.size(); i++) {
            show += singles.get(i).show();
            if (i != singles.size() - 1) {
                show += ", ";
            }
        }
        tvTouchThirdClaw.setText(show);
    }

    @Override
    public void showJackpotListThisYear(List<Jackpot> jackpotList) {
        viewModel.getRareSameDoubleList(jackpotList);
        viewModel.getCoupleDoNotAppearThisYear(jackpotList);
    }

    @Override
    public void showRareSameDoubleList(List<NearestTime> subNearestTimeList) {
        String show = "Kép bằng ít về trong năm nay: ";
        for (int i = 0; i < subNearestTimeList.size(); i++) {
            show += "\n - Kép " + subNearestTimeList.get(i).show();
            if (i != subNearestTimeList.size() - 1) {
                show += "; ";
            }
        }
        tvRareDoubleSame.setText(show);
    }

    @Override
    public void showCoupleDoNotAppearThisYear(List<Integer> numbers) {
        String show = "Các số chưa về trong năm nay: ";
        for (int i = 0; i < numbers.size(); i++) {
            show += numbers.get(i);
            if (i != numbers.size() - 1) {
                show += ", ";
            }
        }
        tvRareNumberThisYear.setText(show);
    }

    @Override
    public void showJackpotListLastYear(List<Jackpot> jackpotList) {
        viewModel.getRareCoupleLastYear(jackpotList);
    }

    @Override
    public void showRareCoupleLastYear(List<Integer> noAppearanceList, List<Integer> oneAppearanceList) {
        String show = "Các số ít về vào năm ngoái: ";
        show += "\n - Các số không về lần nào: ";
        for (int i = 0; i < noAppearanceList.size(); i++) {
            show += noAppearanceList.get(i);
            if (i != noAppearanceList.size() - 1) {
                show += ", ";
            }
        }
        show += "\n - Các số về 1 lần: ";
        for (int i = 0; i < oneAppearanceList.size(); i++) {
            show += oneAppearanceList.get(i);
            if (i != oneAppearanceList.size() - 1) {
                show += ", ";
            }
        }
        tvRareNumberLastYear.setText(show);
    }

    @Override
    public void showLotteryList(List<Lottery> lotteryList) {
        viewModel.getConnectedBridge(lotteryList);
        viewModel.getTriadClawBridge(lotteryList);
        viewModel.getTriadBridge(lotteryList); // bao gồm triad và cancel triad bridge
        viewModel.getSignInLottery(lotteryList.get(0));
    }

    @Override
    public void showConnectedBridge(ConnectedBridge connectedBridge) {
        String show = "Cầu liên thông (*): ";
        for (int i = 0; i < connectedBridge.getConnectedSupports().size(); i++) {
            show += connectedBridge.getConnectedSupports().get(i).showShort();
            if (i != connectedBridge.getConnectedSupports().size() - 1) {
                show += ", ";
            }
        }
        tvConnectedBridge.setText(show);
    }

    @Override
    public void showThirdClawBridge(List<ClawSupport> clawSupportList) {
        String show = "Cầu càng 3 (*): ";
        for (int i = 0; i < clawSupportList.size(); i++) {
            if (clawSupportList.get(i).getConnectedBeat() > 0) {
                show += clawSupportList.get(i).showShort();
                if (i != clawSupportList.size() - 1) {
                    show += ", ";
                }
            }
        }
        tvThirdClaw.setText(show);
    }

    @Override
    public void showTriadBridge(List<Set> triadSetList, List<Set> cancelSetList) {
        String show = "Cầu bộ 3 (*): ";
        for (int i = 0; i < triadSetList.size(); i++) {
            show += triadSetList.get(i).show();
            if (i != triadSetList.size() - 1) {
                show += ", ";
            }
        }
        tvTriadBridge.setText(show);
        show = "Cầu bộ 3 về hơn 6 lần (*): ";
        if (!cancelSetList.isEmpty()) {
            for (int i = 0; i < cancelSetList.size(); i++) {
                show += cancelSetList.get(i).show();
                if (i != cancelSetList.size() - 1) {
                    show += ", ";
                }
            }
        }
        tvCancelTriadBridge.setText(show);
    }

    @Override
    public void showSignInLottery(List<Integer> numberList) {
        String signInLottery = "Dấu hiệu trong XSMB: ";
        for (int i = 0; i < numberList.size(); i++) {
            signInLottery += numberList.get(i) + "";
            if (i != numberList.size() - 1) {
                signInLottery += ", ";
            }
        }
        signInLottery += " (từ 5 kép lệch trở lên là dấu hiệu tốt) ";
        tvSignInLotterySDB.setText(signInLottery);
    }

    @Override
    public void showJackpotListInManyDays(List<Jackpot> jackpotList) {
        viewModel.getNumberOfDaysBeforeSDB(jackpotList);
        viewModel.getBeatOfSameDouble(jackpotList);
        viewModel.getSignInJackpot(jackpotList);
        viewModel.getNumberBeforeSameDoubleAppear(jackpotList);
        viewModel.getHeadForALongTime(jackpotList);
        viewModel.getTailForALongTime(jackpotList);
    }

    @Override
    public void showNumberOfDaysBeforeSDB(int numberOfDays) {
        String sameDoubleTitle = "Cầu kép bằng (" + numberOfDays + " ngày trở lại)";
        tvSameDoubleTitle.setText(sameDoubleTitle);
    }

    @Override
    public void showBeatOfSameDouble(List<Integer> beatList) {
        String beatOfSameDouble = "Nhịp của kép bằng: ";
        for (int i = 0; i < beatList.size(); i++) {
            beatOfSameDouble += beatList.get(i) + "";
            if (i != beatList.size() - 1) {
                beatOfSameDouble += ", ";
            }
        }
        tvBeatSDB.setText(beatOfSameDouble);
    }

    @Override
    public void showSignInJackpot(List<JackpotSign> jackpotSignList) {
        String signInJackpot = "Dấu hiệu trong giải ĐB: ";
        for (int i = 0; i < jackpotSignList.size(); i++) {
            signInJackpot += "\n - " + jackpotSignList.get(i).show();
            if (i != jackpotSignList.size() - 1) {
                signInJackpot += "; ";
            }
        }
        tvSignInJackpot.setText(signInJackpot);
    }

    @Override
    public void showNumberBeforeSameDoubleAppear(List<NumberDouble> numberDoubleList) {
        String show = "Các số đề trước khi về kép: ";
        for (int i = 0; i < numberDoubleList.size(); i++) {
            show += "\n - " + numberDoubleList.get(i).show() + "";
            if (i != numberDoubleList.size() - 1) {
                show += "; ";
            }
        }
        tvLastCouple.setText(show);
    }

    @Override
    public void showHeadForALongTime(int runningDayNumber, List<NearestTime> subNearestTimeList) {
        String show = "Trong vòng " + runningDayNumber + " ngày: ";
        for (int i = 0; i < subNearestTimeList.size(); i++) {
            show += "\n - Đầu " + subNearestTimeList.get(i).show();
            if (i != 0) {
                show += "; ";
            }
        }
        tvRareHead.setText(show);
    }

    @Override
    public void showTailForALongTime(int runningDayNumber, List<NearestTime> subNearestTimeList) {
        String show = "Trong vòng " + runningDayNumber + " ngày: ";
        for (int i = 0; i < subNearestTimeList.size(); i++) {
            show += "\n - Đuôi " + subNearestTimeList.get(i).show();
            if (i != subNearestTimeList.size() - 1) {
                show += "; ";
            }
        }
        tvRareTail.setText(show);
    }

    @Override
    public Context getContext() {
        return this;
    }
}
