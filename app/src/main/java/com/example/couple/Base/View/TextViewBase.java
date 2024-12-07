package com.example.couple.Base.View;

import android.content.Context;
import android.graphics.Typeface;
import android.widget.TextView;

import lombok.Builder;
import lombok.NonNull;

@Builder
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

    public TextView get() {
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

}
