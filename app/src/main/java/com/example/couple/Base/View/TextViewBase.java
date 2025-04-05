package com.example.couple.Base.View;

import android.content.Context;
import android.graphics.Typeface;
import android.view.Gravity;
import android.widget.TextView;

import com.example.couple.R;

import lombok.Builder;
import lombok.NonNull;
import lombok.Setter;

@Builder
@Setter
public class TextViewBase {
    @NonNull
    Context context;

    Integer id;
    Object tag;
    String text;
    Integer textSize;
    Integer gravity;
    Spacing padding;
    Spacing margin;
    boolean bold;
    boolean italic;
    Integer textColor;
    Integer background;

    public TextView toTextView() {
        TextView tv = new TextView(context);
        if (id != null) tv.setId(id);
        if (tag != null) tv.setTag(tag);
        if (text != null) tv.setText(text);
        if (textSize != null) tv.setTextSize(textSize);
        if (gravity != null) tv.setGravity(gravity);
        if (padding != null)
            tv.setPadding(padding.left, padding.top, padding.right, padding.bottom);
        if (bold) tv.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
        if (italic) tv.setTypeface(Typeface.defaultFromStyle(Typeface.ITALIC));
        if (textColor != null) tv.setTextColor(WidgetBase.getColorId(context, textColor));
        if (background != null) tv.setBackground(WidgetBase.getDrawable(context, background));
        return tv;
    }

    public static TextView getNormalTextView(Context context) {
        return TextViewBase.builder().context(context).textSize(15).gravity(Gravity.CENTER)
                .padding(Spacing.by(10, 5, 10, 5))
                .textColor(R.color.colorText).build().toTextView();
    }

    public static TextView getNormalTextView(Context context, String text) {
        return TextViewBase.builder().context(context).text(text).textSize(15).gravity(Gravity.CENTER)
                .padding(Spacing.by(10, 5, 10, 5))
                .textColor(R.color.colorText).build().toTextView();
    }

    public static TextView getPickerTextView(Context context, String text, Integer id) {
        return TextViewBase.builder().context(context).id(id).tag(id).text(text).textSize(18).gravity(Gravity.CENTER)
                .padding(Spacing.by(35, 30, 35, 30))
                .textColor(R.color.colorText).build().toTextView();
    }

    public static TextView getCoupleTextView(Context context, String text, Integer id) {
        return TextViewBase.builder().context(context).id(id).tag(id).text(text).textSize(18).gravity(Gravity.CENTER)
                .padding(Spacing.by(20, 15, 20, 15)).bold(true)
                .textColor(R.color.colorTextJackpot).build().toTextView();
    }

    public static TextView getNoteTextView(Context context, String text) {
        return TextViewBase.builder().context(context).text(text).gravity(Gravity.CENTER).italic(true)
                .padding(Spacing.by(0, 12, 0, 12))
                .textColor(android.R.color.holo_red_dark).build().toTextView();
    }

}
