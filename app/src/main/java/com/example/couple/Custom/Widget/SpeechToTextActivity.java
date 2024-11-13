package com.example.couple.Custom.Widget;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;

import com.example.couple.Base.Handler.SpeechToTextBase;
import com.example.couple.R;

public abstract class SpeechToTextActivity extends SpeechToTextBase {

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

    @Override
    public void post(String resultText) {
        CustomAction.changeActivity(getContext(), resultText);
    }

}
