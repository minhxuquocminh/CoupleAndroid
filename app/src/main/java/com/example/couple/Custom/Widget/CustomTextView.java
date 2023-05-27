package com.example.couple.Custom.Widget;

import android.content.Context;
import android.graphics.Typeface;
import android.view.Gravity;
import android.widget.TextView;

import com.example.couple.Base.View.WidgetBase;

public class CustomTextView {

    public static TextView getTextViewNote(Context context) {
        TextView tv = new TextView(context);
        tv.setText("Note: Các ô màu xanh ứng với ngày chủ nhật.");
        tv.setPadding(0, 12, 0, 12);
        tv.setGravity(Gravity.CENTER);
        tv.setTypeface(Typeface.defaultFromStyle(Typeface.ITALIC));
        tv.setTextColor(WidgetBase.getColorId(context, android.R.color.holo_red_dark));
        return tv;
    }

}
