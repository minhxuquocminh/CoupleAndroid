package com.example.couple.Base.View;

import android.content.Intent;
import android.speech.RecognizerIntent;

import androidx.appcompat.app.AppCompatActivity;

import com.example.couple.Custom.Const.RequestCode;

import java.util.ArrayList;
import java.util.Locale;

public abstract class SpeechToTextActivityBase extends AppCompatActivity {

    public abstract void post(String resultText);

    public void startListening() {
        // Sử dụng Intent để gọi API nhận dạng giọng nói
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Nói gì đó...");

        // Bắt đầu intent để nhận dạng giọng nói
        startActivityForResult(intent, RequestCode.SPEECH_TO_TEXT);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RequestCode.SPEECH_TO_TEXT) {
            if (resultCode == RESULT_OK && null != data) {
                ArrayList<String> result = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                String recognizedText = result == null || result.isEmpty() ? "" : result.get(0);
                post(recognizedText);
            }
        }
    }

}
