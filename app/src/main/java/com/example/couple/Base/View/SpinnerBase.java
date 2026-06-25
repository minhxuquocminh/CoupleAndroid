package com.example.couple.Base.View;

import android.content.Context;
import android.widget.Spinner;

import com.example.couple.R;

import java.util.Arrays;
import java.util.List;

public class SpinnerBase {
    public static SelectedSpinnerAdapter bindFilterSpinner(Context context, Spinner spinner, String[] values) {
        return bindFilterSpinner(context, spinner, Arrays.asList(values));
    }

    public static SelectedSpinnerAdapter bindFilterSpinner(Context context, Spinner spinner, List<String> values) {
        SelectedSpinnerAdapter adapter = new SelectedSpinnerAdapter(
                context, spinner, values,
                R.layout.custom_item_statistic_spinner, R.id.tvItemStatisticSpinner);
        spinner.setAdapter(adapter);
        return adapter;
    }
}
