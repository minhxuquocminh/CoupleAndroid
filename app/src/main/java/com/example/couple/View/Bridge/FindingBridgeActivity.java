package com.example.couple.View.Bridge;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.couple.Base.View.DialogBase;
import com.example.couple.Base.View.WidgetBase;
import com.example.couple.Custom.Const.Const;
import com.example.couple.Custom.Widget.SpeechToTextActivity;
import com.example.couple.Model.Bridge.Connected.ConnectedSupport;
import com.example.couple.Model.Bridge.Connected.PairConnectedSupport;
import com.example.couple.Model.Bridge.Connected.TriadBridge;
import com.example.couple.Model.Bridge.Touch.ConnectedBridge;
import com.example.couple.Model.Bridge.NumberSet.SetBase;
import com.example.couple.Model.Origin.Jackpot;
import com.example.couple.Model.Origin.Lottery;
import com.example.couple.Model.Bridge.Connected.ClawSupport;
import com.example.couple.Model.Bridge.TriadClaw.Single;
import com.example.couple.Model.Bridge.Connected.TriangleConnectedSupport;
import com.example.couple.R;
import com.example.couple.View.Lottery.LotteryActivity;
import com.example.couple.ViewModel.Bridge.FindingBridgeViewModel;

import java.util.ArrayList;
import java.util.List;

public class FindingBridgeActivity extends SpeechToTextActivity implements FindingBridgeView {
    EditText edtDayNumberBefore;
    TextView tvUpdate1;
    TextView tvViewLottery1;
    TextView tvJackpotNextDay1;
    TextView tvConnectedBridge;
    TextView tvSub2;
    TextView tvEnoughTouchs;
    TextView tvPlus2;
    TextView tvViewLottery2;
    CheckBox cboSortBySet;
    TextView tvJackpotNextDay2;
    ImageView imgGetBridgeStatus;
    TextView tvTriadBridge;
    EditText edtFindingDays;
    TextView tvUpdate3;
    TextView tvViewLottery3;
    TextView tvJackpotNextDay3;
    TextView tvFirstClaw;
    TextView tvSecondClaw;
    TextView tvThirdClaw;
    TextView tvJackpotThirdClawTitle;
    TextView tvJackpotThirdClaw;
    LinearLayout linearDayNumberBefore;
    TextView tvSub1;
    TextView tvDayNumberBefore;
    TextView tvPlus1;

    TextView tvTestConnectedBridge;
    TextView tvTest2ConnectedBridge;

    FindingBridgeViewModel viewModel;
    List<Jackpot> jackpotList = new ArrayList<>();
    int dayNumberBefore = 0;
    boolean isFastView = true;
    public final int TRIAD_BRIDGE_FINDING_DAYS = 12;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_finding_bridge);

        edtDayNumberBefore = findViewById(R.id.edtDayNumberBefore);
        tvUpdate1 = findViewById(R.id.tvUpdate1);
        tvViewLottery1 = findViewById(R.id.tvViewLottery1);
        tvJackpotNextDay1 = findViewById(R.id.tvJackpotNextDay1);
        tvConnectedBridge = findViewById(R.id.tvConnectedBridge);
        tvSub1 = findViewById(R.id.tvSub1);
        tvEnoughTouchs = findViewById(R.id.tvEnoughTouchs);
        tvPlus1 = findViewById(R.id.tvPlus1);
        tvViewLottery2 = findViewById(R.id.tvViewLottery2);
        cboSortBySet = findViewById(R.id.cboSortBySet);
        tvJackpotNextDay2 = findViewById(R.id.tvJackpotNextDay2);
        imgGetBridgeStatus = findViewById(R.id.imgGetBridgeStatus);
        tvTriadBridge = findViewById(R.id.tvTriadBridge);
        edtFindingDays = findViewById(R.id.edtFindingDays);
        tvUpdate3 = findViewById(R.id.tvUpdate3);
        tvViewLottery3 = findViewById(R.id.tvViewLottery3);
        tvJackpotNextDay3 = findViewById(R.id.tvJackpotNextDay3);
        tvFirstClaw = findViewById(R.id.tvFirstClaw);
        tvSecondClaw = findViewById(R.id.tvSecondClaw);
        tvThirdClaw = findViewById(R.id.tvThirdClaw);
        tvJackpotThirdClawTitle = findViewById(R.id.tvJackpotThirdClawTitle);
        tvJackpotThirdClaw = findViewById(R.id.tvJackpotThirdClaw);
        linearDayNumberBefore = findViewById(R.id.linearDayNumberBefore);
        tvSub2 = findViewById(R.id.tvSub2);
        tvDayNumberBefore = findViewById(R.id.tvDayNumberBefore);
        tvPlus2 = findViewById(R.id.tvPlus2);

        tvTestConnectedBridge = findViewById(R.id.tvTestConnectedBridge);
        tvTest2ConnectedBridge = findViewById(R.id.tvTest2ConnectedBridge);

        viewModel = new FindingBridgeViewModel(this, this);
        viewModel.getLotteryListAndJackpotList();

        dayNumberBefore = 0;
        edtDayNumberBefore.setText(dayNumberBefore + "");
        edtDayNumberBefore.setEnabled(false);
        edtDayNumberBefore.setSelection(edtDayNumberBefore.length());
        tvEnoughTouchs.setText("0");
        cboSortBySet.setChecked(false);
        edtFindingDays.setText("12");
        isFastView = false;

        tvViewLottery1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(FindingBridgeActivity.this, LotteryActivity.class));
            }
        });

        tvViewLottery2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(FindingBridgeActivity.this, LotteryActivity.class));
            }
        });

        tvViewLottery3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(FindingBridgeActivity.this, LotteryActivity.class));
            }
        });
    }

    @Override
    public void showMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showLotteryList(List<Lottery> lotteries) {
        viewModel.getConnectedBridge(lotteries, Const.CONNECTED_BRIDGE_FINDING_DAYS, dayNumberBefore);
        viewModel.test(lotteries, Const.CONNECTED_BRIDGE_FINDING_DAYS, dayNumberBefore);
        viewModel.test2(lotteries, Const.CONNECTED_BRIDGE_FINDING_DAYS, dayNumberBefore);
        viewModel.getTriadBridge(lotteries,
                TRIAD_BRIDGE_FINDING_DAYS, dayNumberBefore);
        viewModel.findingFirstClawBridge(lotteries, 12, dayNumberBefore);
        viewModel.findingSecondClawBridge(lotteries, 12, dayNumberBefore);
        viewModel.findingThirdClawBridge(lotteries, 12, dayNumberBefore);
        tvUpdate1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                WidgetBase.hideKeyboard(FindingBridgeActivity.this);
                String dayNumberBeforeStr = edtDayNumberBefore.getText().toString().trim();
                String enoughTouchsStr = tvEnoughTouchs.getText().toString().trim();
                String findingDaysStr = edtFindingDays.getText().toString().trim();
                if (dayNumberBeforeStr.isEmpty() || enoughTouchsStr.isEmpty() || findingDaysStr.isEmpty()) {
                    showMessage("Vui lòng nhập đầy đủ dữ liệu!");
                } else if (Integer.parseInt(enoughTouchsStr) > 2) {
                    showMessage("Số ngày chạm đầy đủ không được lớn hơn 2!");
                } else if (Integer.parseInt(dayNumberBeforeStr) > 50) {
                    showMessage("Số ngày trước đó không được lớn hơn 50!");
                } else {
                    dayNumberBefore = Integer.parseInt(dayNumberBeforeStr);
                    tvDayNumberBefore.setText(dayNumberBefore + "");
                    int findingDays = Integer.parseInt(findingDaysStr);
                    int count = 0;
                    viewModel.test(lotteries, Const.CONNECTED_BRIDGE_FINDING_DAYS, dayNumberBefore);
                    viewModel.test2(lotteries, Const.CONNECTED_BRIDGE_FINDING_DAYS, dayNumberBefore);
                    if (viewModel.getConnectedBridge(lotteries,
                            Const.CONNECTED_BRIDGE_FINDING_DAYS, dayNumberBefore)) {
                        count++;
                    }
                    if (viewModel.getTriadBridge(lotteries,
                            TRIAD_BRIDGE_FINDING_DAYS, dayNumberBefore)) {
                        count++;
                    }
                    if (viewModel.findingFirstClawBridge(lotteries, findingDays, dayNumberBefore)) {
                        count++;
                    }
                    if (viewModel.findingSecondClawBridge(lotteries, findingDays, dayNumberBefore)) {
                        count++;
                    }
                    if (viewModel.findingThirdClawBridge(lotteries, findingDays, dayNumberBefore)) {
                        count++;
                    }
                    viewModel.findingJackpotThirdClawBridge(jackpotList, dayNumberBefore);
                    if (count < 5) {
                        showMessage("Đã xảy ra lỗi trong quá trình xử lý dữ liệu!");
                    } else {
                        Toast.makeText(FindingBridgeActivity.this, "Cập nhật thành công!",
                                Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        tvUpdate1.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                String statusFastView = isFastView ? "tắt" : "bật";
                String title = "Chế độ xem nhanh";
                String mesage = "Bạn có muốn " + statusFastView +
                        " chế độ xem nhanh dữ liệu theo số ngày trước đó không?";
                DialogBase.showWithConfirmation(FindingBridgeActivity.this, title, mesage, () -> {
                    if (isFastView) {
                        isFastView = false;
                        linearDayNumberBefore.setVisibility(View.GONE);
                        edtDayNumberBefore.setSelection(edtDayNumberBefore.length());
                        edtDayNumberBefore.setEnabled(true);
                    } else {
                        isFastView = true;
                        linearDayNumberBefore.setVisibility(View.VISIBLE);
                        edtDayNumberBefore.setEnabled(false);
                    }
                });
                return false;
            }
        });

        tvUpdate3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                WidgetBase.hideKeyboard(FindingBridgeActivity.this);
                String findingDaysStr = edtFindingDays.getText().toString().trim();
                if (findingDaysStr.isEmpty()) {
                    showMessage("Vui lòng nhập số ngày soi cầu!");
                } else if (Integer.parseInt(findingDaysStr) > 30) {
                    showMessage("Số ngày không được quá 30!");
                } else {
                    int findingDays = Integer.parseInt(findingDaysStr);
                    int count = 0;
                    if (viewModel.findingFirstClawBridge(lotteries, findingDays, dayNumberBefore)) {
                        count++;
                    }
                    if (viewModel.findingSecondClawBridge(lotteries, findingDays, dayNumberBefore)) {
                        count++;
                    }
                    if (viewModel.findingThirdClawBridge(lotteries, findingDays, dayNumberBefore)) {
                        count++;
                    }
                    if (count < 3) {
                        showMessage("Đã xảy ra lỗi trong quá trình xử lý dữ liệu!");
                    } else {
                        Toast.makeText(FindingBridgeActivity.this, "Cập nhật thành công!",
                                Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        tvSub2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                WidgetBase.hideKeyboard(FindingBridgeActivity.this);
                String dayNumberBeforeStr = tvDayNumberBefore.getText().toString().trim();
                String enoughTouchsStr = tvEnoughTouchs.getText().toString().trim();
                String findingDaysStr = edtFindingDays.getText().toString().trim();
                if (enoughTouchsStr.isEmpty() || findingDaysStr.isEmpty()) {
                    showMessage("Vui lòng nhập đầy đủ dữ liệu!");
                } else if (Integer.parseInt(enoughTouchsStr) > 2) {
                    showMessage("Số ngày chạm đầy đủ không được lớn hơn 2!");
                } else {
                    dayNumberBefore = Integer.parseInt(dayNumberBeforeStr);
                    if (dayNumberBefore - 1 >= 0) {
                        dayNumberBefore--;
                        tvDayNumberBefore.setText(dayNumberBefore + "");
                        edtDayNumberBefore.setText(dayNumberBefore + "");
                        edtDayNumberBefore.setSelection(edtDayNumberBefore.length());
                        int findingDays = Integer.parseInt(findingDaysStr);
                        int count = 0;
                        viewModel.test(lotteries, Const.CONNECTED_BRIDGE_FINDING_DAYS, dayNumberBefore);
                        viewModel.test2(lotteries, Const.CONNECTED_BRIDGE_FINDING_DAYS, dayNumberBefore);
                        if (viewModel.getConnectedBridge(lotteries,
                                Const.CONNECTED_BRIDGE_FINDING_DAYS, dayNumberBefore)) {
                            count++;
                        }
                        if (viewModel.getTriadBridge(lotteries,
                                TRIAD_BRIDGE_FINDING_DAYS, dayNumberBefore)) {
                            count++;
                        }
                        if (viewModel.findingFirstClawBridge(lotteries, findingDays, dayNumberBefore)) {
                            count++;
                        }
                        if (viewModel.findingSecondClawBridge(lotteries, findingDays, dayNumberBefore)) {
                            count++;
                        }
                        if (viewModel.findingThirdClawBridge(lotteries, findingDays, dayNumberBefore)) {
                            count++;
                        }
                        viewModel.findingJackpotThirdClawBridge(jackpotList, dayNumberBefore);
                        if (count < 5) {
                            showMessage("Đã xảy ra lỗi trong quá trình xử lý dữ liệu!");
                        }
                    }
                }
            }
        });

        tvPlus2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                WidgetBase.hideKeyboard(FindingBridgeActivity.this);
                String dayNumberBeforeStr = tvDayNumberBefore.getText().toString().trim();
                String enoughTouchsStr = tvEnoughTouchs.getText().toString().trim();
                String findingDaysStr = edtFindingDays.getText().toString().trim();
                if (enoughTouchsStr.isEmpty() || findingDaysStr.isEmpty()) {
                    showMessage("Vui lòng nhập đầy đủ dữ liệu!");
                } else if (Integer.parseInt(enoughTouchsStr) > 2) {
                    showMessage("Số ngày chạm đầy đủ không được lớn hơn 2!");
                } else {
                    dayNumberBefore = Integer.parseInt(dayNumberBeforeStr);
                    if (dayNumberBefore + 1 <= 50) {
                        dayNumberBefore++;
                        tvDayNumberBefore.setText(dayNumberBefore + "");
                        edtDayNumberBefore.setText(dayNumberBefore + "");
                        edtDayNumberBefore.setSelection(edtDayNumberBefore.length());
                        int findingDays = Integer.parseInt(findingDaysStr);
                        int count = 0;
                        viewModel.test(lotteries, Const.CONNECTED_BRIDGE_FINDING_DAYS, dayNumberBefore);
                        viewModel.test2(lotteries, Const.CONNECTED_BRIDGE_FINDING_DAYS, dayNumberBefore);
                        if (viewModel.getConnectedBridge(lotteries,
                                Const.CONNECTED_BRIDGE_FINDING_DAYS, dayNumberBefore)) {
                            count++;
                        }
                        if (viewModel.getTriadBridge(lotteries,
                                TRIAD_BRIDGE_FINDING_DAYS, dayNumberBefore)) {
                            count++;
                        }
                        if (viewModel.findingFirstClawBridge(lotteries, findingDays, dayNumberBefore)) {
                            count++;
                        }
                        if (viewModel.findingSecondClawBridge(lotteries, findingDays, dayNumberBefore)) {
                            count++;
                        }
                        if (viewModel.findingThirdClawBridge(lotteries, findingDays, dayNumberBefore)) {
                            count++;
                        }
                        viewModel.findingJackpotThirdClawBridge(jackpotList, dayNumberBefore);
                        if (count < 5) {
                            showMessage("Đã xảy ra lỗi trong quá trình xử lý dữ liệu!");
                        }
                    }
                }
            }
        });

        imgGetBridgeStatus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewModel.getThreeSetBridgeStatus(lotteries, TRIAD_BRIDGE_FINDING_DAYS, dayNumberBefore);
            }
        });

    }

    @Override
    public void showJackpotList(List<Jackpot> jackpots) {
        jackpotList = jackpots;
        viewModel.findingJackpotThirdClawBridge(jackpotList, 0);
    }

    @Override
    public void showConnectedBridge(ConnectedBridge connectedBridge, String jackpotThatDay) {
        tvJackpotNextDay1.setText(" * Kết quả: " + jackpotThatDay);
        tvJackpotNextDay2.setText(" * Kết quả: " + jackpotThatDay);
        tvJackpotNextDay3.setText(" * Kết quả: " + jackpotThatDay);
        String info = "";
        List<ConnectedSupport> connectedSupports = connectedBridge.getConnectedSupports();
        for (int i = 0; i < connectedSupports.size(); i++) {
            info += " - " + connectedSupports.get(i).show();
            if (i != connectedSupports.size() - 1) {
                info += "\n";
            }
        }
        tvConnectedBridge.setText(info);
    }

    @Override
    public void showTriadBridge(List<TriadBridge> triadBridges) {
        String enoughTouchsStr = tvEnoughTouchs.getText().toString().trim();
        int enoughTouchs = Integer.parseInt(enoughTouchsStr);
        boolean sortBySet = cboSortBySet.isChecked();
        viewModel.getTriadBridgeWithCondition(triadBridges, enoughTouchs, sortBySet);
        tvSub1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                WidgetBase.hideKeyboard(FindingBridgeActivity.this);
                String enoughTouchsStr = tvEnoughTouchs.getText().toString().trim();
                int enoughTouchs = Integer.parseInt(enoughTouchsStr);
                boolean sortBySet = cboSortBySet.isChecked();
                if (enoughTouchs - 1 >= 0) {
                    enoughTouchs--;
                    tvEnoughTouchs.setText(enoughTouchs + "");
                    viewModel.getTriadBridgeWithCondition(triadBridges, enoughTouchs, sortBySet);
                }
            }
        });

        tvPlus1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                WidgetBase.hideKeyboard(FindingBridgeActivity.this);
                String enoughTouchsStr = tvEnoughTouchs.getText().toString().trim();
                int enoughTouchs = Integer.parseInt(enoughTouchsStr);
                boolean sortBySet = cboSortBySet.isChecked();
                if (enoughTouchs + 1 <= 2) {
                    enoughTouchs++;
                    tvEnoughTouchs.setText(enoughTouchs + "");
                    viewModel.getTriadBridgeWithCondition(triadBridges, enoughTouchs, sortBySet);
                }
            }
        });

        cboSortBySet.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                String enoughTouchsStr = tvEnoughTouchs.getText().toString().trim();
                int enoughTouchs = Integer.parseInt(enoughTouchsStr);
                viewModel.getTriadBridgeWithCondition(triadBridges, enoughTouchs, isChecked);
            }
        });


    }

    @Override
    public void showTriadBridgeWithCondition(List<TriadBridge> triadBridgeList, List<SetBase> mainSetBases,
                                             List<SetBase> longestSetBases, List<SetBase> cancelSetBases, int enoughTouchs) {
        String show = " * Bao gồm các bộ: ";
        for (int i = 0; i < mainSetBases.size(); i++) {
            show += mainSetBases.get(i).show();
            if (i != mainSetBases.size() - 1) {
                show += ", ";
            }
        }
        if (!longestSetBases.isEmpty()) {
            show += "\n * Các bộ về hơn 6 lần: ";
            for (int i = 0; i < longestSetBases.size(); i++) {
                show += longestSetBases.get(i).show();
                if (i != longestSetBases.size() - 1) {
                    show += ", ";
                }
            }
        }
        if (!cancelSetBases.isEmpty()) {
            show += "\n * Các bộ khác có từ " + (enoughTouchs - 1) + " chạm đầy đủ: ";
            for (int i = 0; i < cancelSetBases.size(); i++) {
                show += cancelSetBases.get(i).show();
                if (i != cancelSetBases.size() - 1) {
                    show += ", ";
                }
            }
        }
        show += "\n * Chi tiết cầu:\n";
        for (int i = 0; i < triadBridgeList.size(); i++) {
            show += triadBridgeList.get(i).show();
            if (i != triadBridgeList.size() - 1) {
                show += "\n";
            }
        }
        tvTriadBridge.setText(show);
    }

    @Override
    public void showFirstClawBridge(List<ClawSupport> clawSupportList) {
        String show = "";
        for (int i = 0; i < clawSupportList.size(); i++) {
            show += " - " + clawSupportList.get(i).showStatus();
            if (i != clawSupportList.size() - 1) {
                show += "\n";
            }
        }
        tvFirstClaw.setText(show);
    }

    @Override
    public void showSecondClawBridge(List<ClawSupport> clawSupportList) {
        String show = "";
        for (int i = 0; i < clawSupportList.size(); i++) {
            show += " - " + clawSupportList.get(i).showStatus();
            if (i != clawSupportList.size() - 1) {
                show += "\n";
            }
        }
        tvSecondClaw.setText(show);
    }

    @Override
    public void showThirdClawBridge(List<ClawSupport> clawSupportList) {
        String show = "";
        for (int i = 0; i < clawSupportList.size(); i++) {
            show += " - " + clawSupportList.get(i).showStatus();
            if (i != clawSupportList.size() - 1) {
                show += "\n";
            }
        }
        tvThirdClaw.setText(show);
    }

    @Override
    public void showJackpotThirdClawBridge(List<Single> singles, int frame) {
        tvJackpotThirdClawTitle.setText("Cầu càng 3 giải ĐB (khung " + frame + " ngày): ");
        String show = " - Các số: ";
        for (int i = 0; i < singles.size(); i++) {
            show += singles.get(i).show();
            if (i != singles.size() - 1) {
                show += ", ";
            }
        }
        tvJackpotThirdClaw.setText(show);
    }

    @Override
    public void showThreeSetBridgeStatus(List<Integer> statusList) {
        String show = "";
        for (int n : statusList) {
            show += n + " ";
        }
        String title = "Thứ tự";
        String mesage = "Thứ tự trúng cầu bộ 3 từ sau ra trước: " + show;
        DialogBase.showWithCopiedText(this, title, mesage, show, "thứ tự");
    }

    @Override
    public void showTest(List<TriangleConnectedSupport> supports) {
        String info = "";
        for (int i = 0; i < supports.size(); i++) {
            info += " - " + supports.get(i).show();
            if (i != supports.size() - 1) {
                info += "\n";
            }
        }
        tvTestConnectedBridge.setText(info);
    }

    @Override
    public void showTest2(List<PairConnectedSupport> supports) {
        String info = "";
        for (int i = 0; i < supports.size(); i++) {
            info += " - " + supports.get(i).show();
            if (i != supports.size() - 1) {
                info += "\n";
            }
        }
        tvTest2ConnectedBridge.setText(info);
    }

    @Override
    public Context getContext() {
        return this;
    }
}
