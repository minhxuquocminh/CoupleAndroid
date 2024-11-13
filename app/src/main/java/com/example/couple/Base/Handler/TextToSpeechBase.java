package com.example.couple.Base.Handler;

import android.content.Context;
import android.speech.tts.TextToSpeech;
import android.speech.tts.UtteranceProgressListener;

import java.util.Locale;

public class TextToSpeechBase implements TextToSpeech.OnInitListener {

    private final TextToSpeech textToSpeech;
    private final String text;

    public TextToSpeechBase(Context context, String text) {
        this.text = text;
        this.textToSpeech = new TextToSpeech(context, this);
    }

    @Override
    public void onInit(int status) {
        if (status == TextToSpeech.SUCCESS) {
            int result = textToSpeech.setLanguage(Locale.getDefault());

            // Đọc văn bản
            textToSpeech.speak(text, TextToSpeech.QUEUE_FLUSH, null, null);
            // Hủy TextToSpeech sau khi đọc xong
            textToSpeech.setOnUtteranceProgressListener(new UtteranceProgressListener() {
                @Override
                public void onStart(String s) {

                }

                @Override
                public void onDone(String utteranceId) {
                    textToSpeech.shutdown();
                }

                @Override
                public void onError(String s) {

                }
            });

            if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                // Handle language data missing or not supported
            }
        } else {
            // Handle initialization failure
        }
    }

}
