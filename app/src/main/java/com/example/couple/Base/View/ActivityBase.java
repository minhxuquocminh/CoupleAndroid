package com.example.couple.Base.View;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.speech.RecognizerIntent;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.couple.R;

import java.util.ArrayList;
import java.util.Locale;

public abstract class ActivityBase extends AppCompatActivity {

    public abstract Context getContext();

    private boolean isEventBound = false;

    @Override
    protected void onStart() {
        super.onStart();
        TextView tvToolbar = findViewById(R.id.tvToolbar);
        if (tvToolbar == null) return;
        if (!isEventBound) {
            tvToolbar.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    startListening();
                    return false;
                }
            });
            isEventBound = true;
        }
    }

    private static final int REQUEST_CODE_SPEECH_INPUT = 100;

    public void startListening() {
        // Sử dụng Intent để gọi API nhận dạng giọng nói
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Nói gì đó...");

        // Bắt đầu intent để nhận dạng giọng nói
        startActivityForResult(intent, REQUEST_CODE_SPEECH_INPUT);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_CODE_SPEECH_INPUT) {
            if (resultCode == RESULT_OK && null != data) {
                ArrayList<String> result = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                String recognizedText = result == null || result.isEmpty() ? "" : result.get(0);
                changeActivity(getContext(), recognizedText);
            }
        }
    }

    public void changeActivity(Context context, String actionText) {
        if (!(context instanceof Activity)) return;
        Activity activity = (Activity) context;
        if (actionText.contains("quay lại")) {
            activity.onBackPressed();
            return;
        }

        for (ActivityType targetClass : ActivityType.values()) {
            if (!activity.getClass().equals(targetClass.targetClass) && actionText.contains(targetClass.key)) {
                Intent intent = new Intent(context, targetClass.targetClass);
                context.startActivity(intent);
            }
        }
    }

}
