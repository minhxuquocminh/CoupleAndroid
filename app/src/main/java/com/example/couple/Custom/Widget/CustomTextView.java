package com.example.couple.Custom.Widget;

import android.content.Context;
import android.view.Gravity;
import android.widget.TextView;

import com.example.couple.Base.View.Spacing;
import com.example.couple.Base.View.TextViewBase;
import com.example.couple.R;


public class CustomTextView {

    protected static TextView getMatrixByYearHeaderCell(Context context, String text) {
        return TextViewBase.builder().context(context).text(text).textSize(15).gravity(Gravity.CENTER)
                .padding(Spacing.by(10, 5, 10, 5)).bold(true)
                .textColor(R.color.colorPrimary).background(R.drawable.cell_pink_table).build().get();
    }

    protected static TextView getMatrixByYearCell(Context context, String text) {
        return TextViewBase.builder().context(context).text(text).textSize(15).gravity(Gravity.CENTER)
                .padding(Spacing.by(10, 5, 10, 5))
                .textColor(R.color.colorText).background(R.drawable.cell_pink_table).build().get();
    }

    protected static TextView getCellOfCoupleTable(Context context, String text) {
        return TextViewBase.builder().context(context).text(text).textSize(18).gravity(Gravity.CENTER)
                .padding(Spacing.by(20, 15, 20, 15)).bold(true)
                .textColor(R.color.colorTextJackpot).background(R.drawable.cell_pink_table).build().get();
    }

    protected static TextView getCellOfChooseNumberTable(Context context, int id, String text) {
        return TextViewBase.builder().context(context).id(id).tag(id).text(text).textSize(18).gravity(Gravity.CENTER)
                .padding(Spacing.by(40, 30, 40, 30))
                .textColor(R.color.colorText).background(R.drawable.cell_pink_table).build().get();
    }

    protected static TextView getCellOfNumberTable(Context context, int id, String text) {
        return TextViewBase.builder().context(context).id(id).tag(id).text(text).textSize(18).gravity(Gravity.CENTER)
                .padding(Spacing.by(35, 30, 35, 30))
                .textColor(R.color.colorText).background(R.drawable.cell_pink_table).build().get();
    }

    protected static TextView getHeaderCell(Context context, String text) {
        return TextViewBase.builder().context(context).text(text).textSize(15).gravity(Gravity.CENTER)
                .padding(Spacing.by(0, 5, 0, 5)).bold(true)
                .textColor(R.color.colorPrimary).background(R.drawable.cell_pink_table).build().get();
    }

    protected static TextView getCell(Context context, String text) {
        return TextViewBase.builder().context(context).text(text).textSize(15).gravity(Gravity.CENTER)
                .textColor(R.color.colorText).background(R.drawable.cell_pink_table).build().get();
    }

    public static TextView getTextViewNote(Context context) {
        return TextViewBase.builder().context(context).text("Note: Các ô màu xanh ứng với ngày chủ nhật.")
                .gravity(Gravity.CENTER).italic(true).padding(Spacing.by(0, 12, 0, 12))
                .textColor(android.R.color.holo_red_dark).build().get();
    }

}
