package com.example.couple.Base.View;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import androidx.core.content.ContextCompat;

public class WidgetBase {

    public static void showDialogCanBeCopied(Context context, String title, String message, String copyText) {
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

    public static void showDialog(Context context, String title, String message) {
        new AlertDialog.Builder(context)
                .setTitle(title)
                .setMessage(message)
                .setNegativeButton("OK", null)
                .setIcon(android.R.drawable.ic_dialog_alert).show();
    }


    public static void copyToClipboard(Context context, String label, String text) {
        ClipboardManager clipboard = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
        ClipData clip = ClipData.newPlainText(label, text);
        clipboard.setPrimaryClip(clip);
    }

    public static void hideKeyboard(Activity activity) {
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        //Find the currently focused view, so we can grab the correct window token from it.
        View view = activity.getCurrentFocus();
        //If no view currently has focus, create a new one, just so we can grab a window token from it
        if (view == null) {
            view = new View(activity);
        }
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    public static void hideKeyboardFrom(Context context, View view) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Activity.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    public static Drawable getDrawable(Context context, int drawableId) {
        Drawable drawable;
        if (Build.VERSION.SDK_INT > 21) {
            drawable = ContextCompat.getDrawable(context, drawableId);
        } else {
            drawable = context.getResources().getDrawable(drawableId);
        }
        return drawable;
    }

    public static int getColorId(Context context, int colorId) {
        int color = 0;
        if (Build.VERSION.SDK_INT > 21) {
            color = ContextCompat.getColor(context, colorId);
        } else {
            color = context.getResources().getColor(colorId);
        }
        return color;
    }
}
