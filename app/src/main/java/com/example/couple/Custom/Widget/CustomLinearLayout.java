package com.example.couple.Custom.Widget;

import android.content.Context;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.example.couple.Base.View.WidgetBase;
import com.example.couple.R;

public class CustomLinearLayout {
    public static LinearLayout GetItemCoupleByWeekLinearLayout(Context context, String couple,
                                                               int firstNegativeShadow,
                                                               int secondNegativeShadow) {
        LinearLayout linearLayout = new LinearLayout(context);
        linearLayout.setLayoutParams(new TableRow.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT));
        linearLayout.setGravity(Gravity.CENTER);
        FrameLayout frameLayout = new FrameLayout(context);
        frameLayout.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT));
        TextView tvCouple = new TextView(context);
        tvCouple.setText(couple);
        tvCouple.setTextSize(18);
        tvCouple.setPadding(16, 16, 16, 16);
        tvCouple.setTextColor(WidgetBase.getColorId(context, R.color.colorText));
        frameLayout.addView(tvCouple);

        TextView tvFirst = new TextView(context);
        tvFirst.setText(firstNegativeShadow + "");
        tvFirst.setTextSize(12);
        FrameLayout.LayoutParams params1 = new FrameLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params1.gravity = Gravity.BOTTOM | Gravity.LEFT;
        tvFirst.setLayoutParams(params1);
        tvFirst.setTextColor(WidgetBase.getColorId(context, R.color.colorText));
        frameLayout.addView(tvFirst);

        TextView tvSecond = new TextView(context);
        tvSecond.setText(secondNegativeShadow + "");
        tvSecond.setTextSize(12);
        FrameLayout.LayoutParams params2 = new FrameLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params2.gravity = Gravity.BOTTOM | Gravity.RIGHT;
        tvSecond.setLayoutParams(params2);
        tvSecond.setTextColor(WidgetBase.getColorId(context, R.color.colorText));
        frameLayout.addView(tvSecond);
        linearLayout.addView(frameLayout);
        return linearLayout;
    }

    public static LinearLayout GetEmptyLinearLayout(Context context) {
        LinearLayout linearLayout = new LinearLayout(context);
        linearLayout.setLayoutParams(new TableRow.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT));
        linearLayout.setGravity(Gravity.CENTER);
        return linearLayout;
    }
}
