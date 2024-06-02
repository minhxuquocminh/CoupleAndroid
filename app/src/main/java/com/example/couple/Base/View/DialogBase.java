package com.example.couple.Base.View;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.widget.Toast;

import com.example.couple.Base.Basic.NoParamFunction;

public class DialogBase {

    public static void showWithConfirmation(Context context, String title,
                                            String message, NoParamFunction agreeFunction) {
        new AlertDialog.Builder(context)
                .setTitle(title).setMessage(message)
                .setPositiveButton(android.R.string.yes, (dialog, which) -> agreeFunction.execute())
                .setNegativeButton(android.R.string.no, null)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }

    public static void showWithCopiedText(Context context, String title, String message, String copyText) {
        new AlertDialog.Builder(context)
                .setTitle(title)
                .setMessage(message)
                .setPositiveButton("Copy KQ", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        WidgetBase.copyToClipboard(context, "number_array", copyText);
                        Toast.makeText(context, "Đã copy KQ.", Toast.LENGTH_SHORT).show();
                    }
                })
                .setNegativeButton("OK", null)
                .setIcon(android.R.drawable.ic_dialog_alert).show();
    }

    public static void showBasic(Context context, String title, String message) {
        new AlertDialog.Builder(context)
                .setTitle(title)
                .setMessage(message)
                .setNegativeButton("OK", null)
                .setIcon(android.R.drawable.ic_dialog_alert).show();
    }

    public static void show(Context context, String positiveName, String negativeName, String title,
                            String message, NoParamFunction positiveFunction) {
        new AlertDialog.Builder(context)
                .setTitle(title)
                .setMessage(message)
                .setPositiveButton(positiveName, (dialog, which) -> positiveFunction.execute())
                .setNegativeButton(negativeName, null)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }

}
