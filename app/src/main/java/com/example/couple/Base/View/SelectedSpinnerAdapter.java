package com.example.couple.Base.View;

import android.content.Context;
import android.graphics.Typeface;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.couple.R;

import java.util.Arrays;
import java.util.List;

public class SelectedSpinnerAdapter extends ArrayAdapter<String> {
    private final Spinner spinner;
    private final int textViewId;

    public SelectedSpinnerAdapter(Context context, Spinner spinner, String[] values,
                                  int resource, int textViewId) {
        this(context, spinner, Arrays.asList(values), resource, textViewId);
    }

    public SelectedSpinnerAdapter(Context context, Spinner spinner, List<String> values,
                                  int resource, int textViewId) {
        super(context, resource, textViewId, values);
        this.spinner = spinner;
        this.textViewId = textViewId;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = super.getView(position, convertView, parent);
        bindText(view, position, true);
        return view;
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        View view = super.getDropDownView(position, convertView, parent);
        boolean selected = spinner != null && position == spinner.getSelectedItemPosition();
        bindText(view, position, selected);
        view.setBackgroundColor(getContext().getColor(
                selected ? R.color.colorSurfaceSoft : R.color.colorThemeLight));
        return view;
    }

    private void bindText(View view, int position, boolean selected) {
        TextView textView = view instanceof TextView
                ? (TextView) view
                : view.findViewById(textViewId);
        if (textView == null) return;
        String text = getItem(position);
        textView.setText(text);
        textView.setTextColor(getContext().getColor(
                selected ? R.color.colorPrimary : R.color.colorText));
        textView.setTypeface(null, selected ? Typeface.BOLD : Typeface.NORMAL);
    }
}
