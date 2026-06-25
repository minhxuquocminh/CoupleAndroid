package com.example.couple.View.Main.FunctionDisplay;

import android.content.Intent;
import android.content.Context;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.couple.R;
import com.example.couple.View.Agent.AgentChatHistoryActivity;
import com.example.couple.View.Agent.AgentModelManageActivity;
import com.example.couple.View.Agent.AiJackpotStatisticsActivity;
import com.example.couple.View.Agent.AiPredictionHistoryActivity;
import com.example.couple.View.Bridge.AfterDoubleBridgeActivity;
import com.example.couple.View.Bridge.BridgeCombinationActivity;
import com.example.couple.View.Bridge.ConnectedBridgeActivity;
import com.example.couple.View.Bridge.EstimatedBridgeActivity;
import com.example.couple.View.Bridge.MappingBridgeActivity;
import com.example.couple.View.Bridge.TouchBridgeActivity;
import com.example.couple.View.BridgeHistory.JackpotNumberSetRhythmActivity;
import com.example.couple.View.BridgeHistory.SexagenaryCycleActivity;
import com.example.couple.View.Couple.BalanceCoupleActivity;
import com.example.couple.View.Couple.CoupleByWeekActivity;
import com.example.couple.View.Jackpot.JackpotByYearActivity;
import com.example.couple.View.Jackpot.JackpotNextDayActivity;
import com.example.couple.View.JackpotStatistics.CurrentYearJackpotStatisticsActivity;
import com.example.couple.View.JackpotStatistics.YearlyJackpotStatisticsActivity;
import com.example.couple.View.Lottery.LotteryActivity;
import com.example.couple.View.Main.MainActivity;
import com.example.couple.View.Notification.NewBridgeActivity;
import com.example.couple.View.Notification.NotificationActivity;
import com.example.couple.View.Search.SearchActivity;
import com.example.couple.View.Setting.NotificationSettingActivity;
import com.example.couple.View.SubScreen.CalculatingBalanceCoupleActivity;
import com.example.couple.View.SubScreen.CycleByYearActivity;
import com.example.couple.View.SubScreen.NoteActivity;
import com.example.couple.View.UpdateDataInfo.AddJackpotManyYearsActivity;
import com.example.couple.View.UpdateDataInfo.UrlAndParamsActivity;

public class FunctionDisplayFragment extends Fragment {
    ImageView imgPersonal;
    LinearLayout layoutSearch;
    ImageView imgNotification;
    ImageView imgNavigationMenu;
    LinearLayout linearLeftGroups;
    LinearLayout linearRightGroups;
    MainActivity activity;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        activity = (MainActivity) context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_function_display, container, false);
        imgPersonal = view.findViewById(R.id.imgPersonal);
        layoutSearch = view.findViewById(R.id.layoutSearch);
        imgNotification = view.findViewById(R.id.imgNotification);
        imgNavigationMenu = view.findViewById(R.id.imgNavigationMenu);
        linearLeftGroups = view.findViewById(R.id.linearLeftGroups);
        linearRightGroups = view.findViewById(R.id.linearRightGroups);

        bindHeaderEvents();
        bindGroups();
        return view;
    }

    private void bindHeaderEvents() {
        imgPersonal.setOnClickListener(view -> activity.openPersonalTab());
        layoutSearch.setOnClickListener(view ->
                startActivity(new Intent(getActivity(), SearchActivity.class)));
        imgNotification.setOnClickListener(view ->
                startActivity(new Intent(getActivity(), NotificationActivity.class)));
        imgNavigationMenu.setOnClickListener(view -> activity.showNavigationMenu(view));
    }

    private void bindGroups() {
        linearLeftGroups.removeAllViews();
        linearRightGroups.removeAllViews();

        addGroup(linearLeftGroups, new FunctionGroup("Xem th\u00f4ng tin", new FunctionItem[]{
                new FunctionItem("XSMB", LotteryActivity.class, R.drawable.ic_lottery),
                new FunctionItem("XS \u0110\u1eb7c Bi\u1ec7t", JackpotByYearActivity.class, R.drawable.ic_jackpot_by_year_2),
                new FunctionItem("\u0110B h\u00f4m sau", JackpotNextDayActivity.class, R.drawable.ic_jackpot_next_day),
                new FunctionItem("\u0110B theo tu\u1ea7n", CoupleByWeekActivity.class, R.drawable.ic_jackpot_counter),
                new FunctionItem("BSCB", BalanceCoupleActivity.class, R.drawable.ic_trigrams),
                new FunctionItem("Can Chi", SexagenaryCycleActivity.class, R.drawable.ic_statistics)
        }));

        addGroup(linearLeftGroups, new FunctionGroup("Th\u1ed1ng k\u00ea", new FunctionItem[]{
                new FunctionItem("Th\u1ed1ng k\u00ea \u0110B n\u0103m nay", CurrentYearJackpotStatisticsActivity.class, R.drawable.ic_jackpot_this_year),
                new FunctionItem("Th\u1ed1ng k\u00ea \u0110B theo n\u0103m", YearlyJackpotStatisticsActivity.class, R.drawable.ic_statistics_jackpot),
                new FunctionItem("L\u1ecbch s\u1eed AI \u0110B", AiPredictionHistoryActivity.class, R.drawable.ic_prediction_bridge),
                new FunctionItem("Nh\u1ecbp ch\u1ea1y \u0110B", JackpotNumberSetRhythmActivity.class, R.drawable.ic_reference_jackpot)
        }));

        addGroup(linearLeftGroups, new FunctionGroup("C\u00e0i \u0111\u1eb7t", new FunctionItem[]{
                new FunctionItem("S\u1eeda URL v\u00e0 Param", UrlAndParamsActivity.class, R.drawable.ic_info),
                new FunctionItem("L\u01b0u d\u1eef li\u1ec7u \u0110B", AddJackpotManyYearsActivity.class, R.drawable.ic_save),
                new FunctionItem("Qu\u1ea3n l\u00fd model", AgentModelManageActivity.class, R.drawable.ic_agent_chat_card),
                new FunctionItem("C\u00e0i \u0111\u1eb7t th\u00f4ng b\u00e1o", NotificationSettingActivity.class, R.drawable.ic_notification)
        }));

        addGroup(linearRightGroups, new FunctionGroup("Xem c\u1ea7u", new FunctionItem[]{
                new FunctionItem("AI soi c\u1ea7u \u0110B", AiJackpotStatisticsActivity.class, R.drawable.ic_agent_chat_card),
                new FunctionItem("C\u1ea7u m\u1edbi", NewBridgeActivity.class, R.drawable.ic_notification),
                new FunctionItem("C\u1ea7u li\u00ean th\u00f4ng", ConnectedBridgeActivity.class, R.drawable.ic_finding_bridge),
                new FunctionItem("C\u1ea7u sau khi ra k\u00e9p", AfterDoubleBridgeActivity.class, R.drawable.ic_jackpot_counter),
                new FunctionItem("C\u1ea7u ch\u1ea1m", TouchBridgeActivity.class, R.drawable.ic_reference_jackpot),
                new FunctionItem("C\u1ea7u \u00e1nh x\u1ea1", MappingBridgeActivity.class, R.drawable.ic_swap_horiz),
                new FunctionItem("C\u1ea7u \u01b0\u1edbc l\u01b0\u1ee3ng", EstimatedBridgeActivity.class, R.drawable.ic_statistics),
                new FunctionItem("T\u1ed5ng h\u1ee3p c\u1ea7u", BridgeCombinationActivity.class, R.drawable.ic_more_vert)
        }));

        addGroup(linearRightGroups, new FunctionGroup("Kh\u00e1c", new FunctionItem[]{
                new FunctionItem("Chat v\u1edbi AI", AgentChatHistoryActivity.class, R.drawable.ic_agent_chat_card),
                new FunctionItem("Can chi theo n\u0103m", CycleByYearActivity.class, R.drawable.ic_statistics),
                new FunctionItem("Note", NoteActivity.class, R.drawable.ic_keep),
                new FunctionItem("T\u00ednh BSCB", CalculatingBalanceCoupleActivity.class, R.drawable.ic_trigrams)
        }));
    }

    private void addGroup(LinearLayout parent, FunctionGroup group) {
        LinearLayout groupLayout = new LinearLayout(getContext());
        groupLayout.setOrientation(LinearLayout.VERTICAL);
        groupLayout.setBackgroundResource(R.drawable.bg_function_group);
        groupLayout.setPadding(dp(8), dp(8), dp(8), dp(8));

        LinearLayout.LayoutParams groupParams = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        groupParams.setMargins(0, 0, 0, dp(10));
        parent.addView(groupLayout, groupParams);

        TextView title = new TextView(getContext());
        title.setText(group.title);
        title.setTextColor(getResources().getColor(R.color.colorPrimary));
        title.setTextSize(16);
        title.setTypeface(Typeface.DEFAULT_BOLD);
        title.setPadding(dp(3), 0, dp(3), dp(8));
        groupLayout.addView(title, new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));

        for (FunctionItem item : group.items) {
            groupLayout.addView(createItemView(item));
        }
    }

    private View createItemView(final FunctionItem item) {
        LinearLayout itemLayout = new LinearLayout(getContext());
        itemLayout.setOrientation(LinearLayout.HORIZONTAL);
        itemLayout.setGravity(android.view.Gravity.CENTER_VERTICAL);
        itemLayout.setBackgroundResource(R.drawable.bg_function_item);
        itemLayout.setMinimumHeight(dp(58));
        itemLayout.setPadding(dp(10), dp(8), dp(10), dp(8));

        ImageView icon = new ImageView(getContext());
        icon.setImageResource(item.iconRes);
        icon.setScaleType(ImageView.ScaleType.FIT_CENTER);
        LinearLayout.LayoutParams iconParams = new LinearLayout.LayoutParams(dp(32), dp(32));
        iconParams.setMargins(0, 0, dp(10), 0);
        itemLayout.addView(icon, iconParams);

        TextView text = new TextView(getContext());
        text.setText(item.title);
        text.setTextColor(getResources().getColor(R.color.colorText));
        text.setTextSize(15);
        text.setMaxLines(2);
        itemLayout.addView(text, new LinearLayout.LayoutParams(
                0, ViewGroup.LayoutParams.WRAP_CONTENT, 1));

        LinearLayout.LayoutParams itemParams = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        itemParams.setMargins(0, 0, 0, dp(8));
        itemLayout.setLayoutParams(itemParams);
        itemLayout.setOnClickListener(v -> startActivity(new Intent(getActivity(), item.targetClass)));
        return itemLayout;
    }

    private int dp(int value) {
        return (int) (value * getResources().getDisplayMetrics().density + 0.5f);
    }

    private static class FunctionGroup {
        final String title;
        final FunctionItem[] items;

        FunctionGroup(String title, FunctionItem[] items) {
            this.title = title;
            this.items = items;
        }
    }

    private static class FunctionItem {
        final String title;
        final Class<?> targetClass;
        final int iconRes;

        FunctionItem(String title, Class<?> targetClass, int iconRes) {
            this.title = title;
            this.targetClass = targetClass;
            this.iconRes = iconRes;
        }
    }
}
