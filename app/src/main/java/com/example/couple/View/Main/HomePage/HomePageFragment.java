package com.example.couple.View.Main.HomePage;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;

import com.example.couple.Base.Handler.ThreadBase;
import com.example.couple.Base.View.DialogBase;
import com.example.couple.Custom.Const.Const;
import com.example.couple.Custom.Service.UpdateDataService;
import com.example.couple.Custom.Service.UpdateDataView;
import com.example.couple.Model.Display.BSingle;
import com.example.couple.Model.Display.NearestTime;
import com.example.couple.Model.Origin.Jackpot;
import com.example.couple.Model.Origin.Lottery;
import com.example.couple.R;
import com.example.couple.View.Bridge.FindingBridgeActivity;
import com.example.couple.View.Couple.BalanceCoupleActivity;
import com.example.couple.View.JackpotStatistics.JackpotByYearActivity;
import com.example.couple.View.Lottery.LotteryActivity;
import com.example.couple.View.Main.MainActivity;
import com.example.couple.View.SubScreen.CalculatingBalanceCoupleActivity;
import com.example.couple.View.SubScreen.NoteActivity;
import com.example.couple.ViewModel.Main.HomePage.HomePageViewModel;

import java.util.List;
import java.util.Objects;

public class HomePageFragment extends Fragment implements HomePageView, UpdateDataView {
    ImageView imgViewLottery;
    ImageView imgFindingBridge;
    TextView tvCalendar;
    LinearLayout layoutRefreshAll;
    LinearLayout layoutRefreshLottery;
    LinearLayout layoutRefreshJackpots;
    TextView tvJackpotToday;
    TextView tvJackpotLastDay;
    TextView tvSuggest;
    CardView cvNote;
    CardView cvBalanceCouple;
    CardView cvMoreInfo;
    CardView cvJackpotByYear;
    CardView cvCalculatingBalanceCouple;
    TextView tvPredition;
    TextView tvTouchBridge;
    TextView tvSpecialTouchBridge;
    TextView tvNote;
    TextView tvSubJackpot;

    HomePageViewModel homePageViewModel;
    UpdateDataService updateDataService;

    MainActivity activity;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        activity = (MainActivity) context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home_page, container, false);

        imgViewLottery = view.findViewById(R.id.imgViewLottery);
        imgFindingBridge = view.findViewById(R.id.imgFindingBridge);
        tvCalendar = view.findViewById(R.id.tvCalendar);
        layoutRefreshAll = view.findViewById(R.id.layoutRefreshAll);
        layoutRefreshLottery = view.findViewById(R.id.layoutRefreshLottery);
        layoutRefreshJackpots = view.findViewById(R.id.layoutRefreshJackpots);
        tvJackpotToday = view.findViewById(R.id.tvJackpotToday);
        tvJackpotLastDay = view.findViewById(R.id.tvJackpotLastDay);
        tvSuggest = view.findViewById(R.id.tvSuggest);
        tvCalendar = view.findViewById(R.id.tvCalendar);
        cvNote = view.findViewById(R.id.cvNote);
        cvBalanceCouple = view.findViewById(R.id.cvBalanceCouple);
        cvMoreInfo = view.findViewById(R.id.cvNumberExperience);
        cvJackpotByYear = view.findViewById(R.id.cvJackpotByYear);
        cvCalculatingBalanceCouple = view.findViewById(R.id.cvCalculatingBalanceCouple);
        tvPredition = view.findViewById(R.id.tvPredition);
        tvTouchBridge = view.findViewById(R.id.tvTouchBridge);
        tvSpecialTouchBridge = view.findViewById(R.id.tvSpecialTouchBridge);
        tvNote = view.findViewById(R.id.tvNote);
        tvSubJackpot = view.findViewById(R.id.tvSubJackpot);

        homePageViewModel = new HomePageViewModel(this, getActivity());
        updateDataService = new UpdateDataService(this, getActivity());

        activity.getTime().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String time) {
                tvCalendar.setText(time);
            }
        });

        activity.getJackpotList().observe(getViewLifecycleOwner(), new Observer<List<Jackpot>>() {
            @Override
            public void onChanged(List<Jackpot> jackpotList) {
                showJackpotList(jackpotList);
            }
        });

        homePageViewModel.getNote();

        imgViewLottery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), LotteryActivity.class));
            }
        });

        imgFindingBridge.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), FindingBridgeActivity.class));
            }
        });

        layoutRefreshAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String title = "Cập nhật tất cả?";
                String message = "Bạn có muốn cập nhật thời gian, XS Đặc biệt và XSMB không?";
                DialogBase.showWithConfirmation(getActivity(), title, message, () -> {
                    new ThreadBase<>((param) -> {
                        updateDataService.updateAllData(false);
                        return null;
                    }, "").start();
                });
            }
        });

        layoutRefreshLottery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String title = "Cập nhật XSMB?";
                String message = "Bạn có muốn cập nhật XSMB trong vòng " +
                        Const.MAX_DAYS_TO_GET_LOTTERY + " ngày không?";
                DialogBase.showWithConfirmation(getActivity(), title, message, () -> {
                    new ThreadBase<>((param) -> {
                        updateDataService.updateLottery(Const.MAX_DAYS_TO_GET_LOTTERY,
                                true, false);
                        return null;
                    }, "").start();
                });
            }
        });

        layoutRefreshJackpots.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String title = "Cập nhật XS Đặc biệt?";
                String message = "Bạn có muốn cập nhật XS Đặc biệt của năm nay không?";
                DialogBase.showWithConfirmation(getActivity(), title, message, () -> {
                    new ThreadBase<>((param) -> {
                        updateDataService.updateJackpot(true, false);
                        return null;
                    }, "").start();
                });
            }
        });

        cvNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), NoteActivity.class));
            }
        });

        cvBalanceCouple.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), BalanceCoupleActivity.class));
            }
        });

        cvMoreInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EnterPasswordDialog dialog = new EnterPasswordDialog(getActivity());
                Objects.requireNonNull(dialog.getWindow())
                        .setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }
        });

        cvJackpotByYear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), JackpotByYearActivity.class));
            }
        });

        cvCalculatingBalanceCouple.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), CalculatingBalanceCoupleActivity.class));
            }
        });

        return view;
    }

    private void showJackpotList(List<Jackpot> jackpotList) {
        tvJackpotToday.setText("Xổ số Đặc Biệt hôm nay về: " + jackpotList.get(0).getJackpot());
        tvJackpotLastDay.setText("Xổ số Đ.Biệt ngày trước đó: " + jackpotList.get(1).getJackpot());
        homePageViewModel.getHeadAndTailInLongestTime(jackpotList);
        int size_to_show = Math.min(jackpotList.size(), 7);
        String show = "Kết quả: ";
        for (int i = 0; i < size_to_show - 1; i++) {
            show += jackpotList.get(i).getCouple().show() + ", ";
        }
        show += jackpotList.get(size_to_show - 1).getCouple().show();
        tvSubJackpot.setText(show);
    }

    @Override
    public void showMessage(String message) {
        Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showTimeData(String time) {
        // không làm gì vì đã được observe từ MainActivity
    }

    @Override
    public void showJackpotData(List<Jackpot> jackpotList) {
        activity.getJackpotList().setValue(jackpotList);
    }

    @Override
    public void showLotteryData(List<Lottery> lotteries) {
        // ko có observe
    }

    @Override
    public void showHeadAndTailInLongestTime(List<NearestTime> nearestTimeList) {
        String show = "Những đầu đuôi đã chạy lâu nhất: ";
        int length = Math.min(nearestTimeList.size(), 1);
        for (int i = 0; i < 1; i++) {
            NearestTime nearestTime = nearestTimeList.get(i);
            show += nearestTime.getType() + " " + nearestTime.getNumber() +
                    "(" + nearestTime.getDayNumberBefore() + " ngày)";
            if (i != length - 1) {
                show += ", ";
            }
        }
        tvSuggest.setText(".");
    }

    @Override
    public void showTouchBridge(List<BSingle> touchList) {
        String touchBridge = "Từ bộ số CB lấy các chạm: ";
        for (int i = 0; i < touchList.size(); i++) {
            touchBridge += touchList.get(i).showTouchBalanceCouple();
            if (i != touchList.size() - 1) {
                touchBridge += ", ";
            }
        }
        tvTouchBridge.setText(touchBridge);
    }

    @Override
    public void showSpecialTouchBridge(List<Integer> touchList) {
        String touchBridge = "Các chạm đặc biệt từ BSCB: ";
        for (int i = 0; i < touchList.size(); i++) {
            touchBridge += touchList.get(i);
            if (i != touchList.size() - 1) {
                touchBridge += ", ";
            }
        }
        tvSpecialTouchBridge.setText(touchBridge);
    }

    @Override
    public void showNote(String note) {
        if (note.isEmpty()) {
            tvNote.setText("Bạn chưa lưu ghi chú nào cả.");
        } else {
            tvNote.setText(note);
        }
    }
}
