package com.example.couple.View.Main.HomePage;

import android.app.AlertDialog;
import android.content.DialogInterface;
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

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import com.example.couple.Custom.Const.Const;
import com.example.couple.Model.Display.BSingle;
import com.example.couple.Model.Display.NearestTime;
import com.example.couple.Model.Origin.Jackpot;
import com.example.couple.Model.Origin.Lottery;
import com.example.couple.R;
import com.example.couple.View.Bridge.SearchingBridgeActivity;
import com.example.couple.View.Couple.BanlanceCoupleActivity;
import com.example.couple.View.JackpotStatistics.JackpotByYearActivity;
import com.example.couple.View.Lottery.LotteryActivity;
import com.example.couple.View.SubScreen.CalculatingBalanceCoupleActivity;
import com.example.couple.View.SubScreen.NoteActivity;
import com.example.couple.ViewModel.Main.HomePage.HomePageViewModel;

import java.util.List;

public class HomePageFragment extends Fragment implements HomePageView {
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

        homePageViewModel.setUrlAndParamsIfNoData();
        homePageViewModel.registerBackgoundRuntime();
        homePageViewModel.UpdateAllDataIfNeeded();
        homePageViewModel.GetTimeDataFromFile();
        homePageViewModel.GetJackpotDataFromFile();
        homePageViewModel.GetLotteryList(Const.MAX_DAYS_TO_GET_LOTTERY + "");

        homePageViewModel.GetNote();

        imgViewLottery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), LotteryActivity.class));
            }
        });

        imgFindingBridge.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), SearchingBridgeActivity.class));
            }
        });

        layoutRefreshAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(getActivity())
                        .setTitle("Cập nhật tất cả?")
                        .setMessage("Bạn có muốn cập nhật thời gian, XS Đặc biệt và XSMB không?")
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                homePageViewModel.UpdateAllData();
                            }
                        })
                        .setNegativeButton(android.R.string.no, null)
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .show();
            }
        });

        layoutRefreshLottery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(getActivity())
                        .setTitle("Cập nhật XSMB?")
                        .setMessage("Bạn có muốn cập nhật XSMB trong vòng " +
                                Const.MAX_DAYS_TO_GET_LOTTERY + " ngày không?")
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                homePageViewModel.UpdateLottery(Const.MAX_DAYS_TO_GET_LOTTERY, true);
                            }
                        })
                        .setNegativeButton(android.R.string.no, null)
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .show();
            }
        });

        layoutRefreshJackpots.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(getActivity())
                        .setTitle("Cập nhật XS Đặc biệt?")
                        .setMessage("Bạn có muốn cập nhật XS Đặc biệt của năm nay không?")
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                homePageViewModel.UpdateJackpot(true);
                            }
                        })
                        .setNegativeButton(android.R.string.no, null)
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .show();
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
                startActivity(new Intent(getActivity(), BanlanceCoupleActivity.class));
            }
        });

        cvMoreInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EnterPasswordDialog dialog = new EnterPasswordDialog(getActivity());
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
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

    @Override
    public void ShowError(String message) {
        Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void ShowAllDataStatus(String message) {
        Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void UpdateJackpotSuccess(String message) {
        if (!message.equals("")) Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
        homePageViewModel.GetJackpotDataFromFile();
    }

    @Override
    public void UpdateLotterySuccess(String message) {
        if (!message.equals("")) Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void ShowTimeDataFromFile(String time) {
        tvCalendar.setText(time);
    }

    @Override
    public void ShowJackpotDataFromFile(List<Jackpot> jackpotList) {
        tvJackpotToday.setText("Xổ số Đặc Biệt hôm nay về: " + jackpotList.get(0).getJackpot());
        tvJackpotLastDay.setText("Xổ số Đ.Biệt ngày trước đó: " + jackpotList.get(1).getJackpot());
        homePageViewModel.GetHeadAndTailInLongestTime(jackpotList);
        homePageViewModel.GetTouchBridge(jackpotList);
        homePageViewModel.GetSpecialTouchBridge(jackpotList);
        int size_to_show = (jackpotList.size() > 7) ? 7 : jackpotList.size();
        String de2Cang7Ngay = "Kết quả: ";
        for (int i = 0; i < size_to_show - 1; i++) {
            de2Cang7Ngay += jackpotList.get(i).getCouple().show() + ", ";
        }
        de2Cang7Ngay += jackpotList.get(size_to_show - 1).getCouple().show();
        tvSubJackpot.setText(de2Cang7Ngay);
    }

    @Override
    public void ShowLotteryList(List<Lottery> lotteries) {
    }

    @Override
    public void ShowHeadAndTailInLongestTime(List<NearestTime> nearestTimeList) {
        String show = "Những đầu đuôi đã chạy lâu nhất: ";
        int length = nearestTimeList.size() < 1 ? nearestTimeList.size() : 1;
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
    public void ShowTouchBridge(List<BSingle> touchList) {
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
    public void ShowSpecialTouchBridge(List<Integer> touchList) {
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
    public void ShowNote(String note) {
        if (note.equals("")) {
            tvNote.setText("Bạn chưa lưu ghi chú nào cả.");
        } else {
            tvNote.setText(note);
        }
    }
}
