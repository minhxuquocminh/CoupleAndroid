package com.example.couple.Base.View;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.TextView;

import com.example.couple.R;

public abstract class ActivityBase extends SpeechToTextActivityBase {

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
                    ActivityBase.super.startListening();
                    return false;
                }
            });
            isEventBound = true;
        }
    }

    @Override
    public void post(String resultText) {
        changeActivity(getContext(), resultText);
    }

    public void changeActivity(Context context, String actionText) {
        if (!(context instanceof Activity)) return;
        Activity activity = (Activity) context;
        if (actionText.contains("quay lại")) {
            activity.onBackPressed();
            return;
        }

        for (ActivityType activityType : ActivityType.values()) {
            if (!activity.getClass().equals(activityType.targetClass) && actionText.contains(activityType.key)) {
                Intent intent = new Intent(context, activityType.targetClass);
                context.startActivity(intent);
            }
        }
    }

}
