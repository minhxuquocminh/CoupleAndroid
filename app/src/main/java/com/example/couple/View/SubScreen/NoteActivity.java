package com.example.couple.View.SubScreen;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.couple.Base.View.ActivityBase;
import com.example.couple.R;
import com.example.couple.ViewModel.SubScreen.NoteViewModel;

public class NoteActivity extends ActivityBase implements NoteView {
    TextView tvTitle;
    ImageView imgEdit;
    TextView tvNote;

    NoteViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note);

        tvTitle = findViewById(R.id.tvTitle);
        imgEdit = findViewById(R.id.imgEdit);
        tvNote = findViewById(R.id.tvNote);

        viewModel = new NoteViewModel(this, this);

        imgEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(NoteActivity.this, EditNoteActivity.class));
            }
        });
    }

    @Override
    public void showMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showNote(String note) {
        tvTitle.setText("Ghi chú đã lưu:");
        tvNote.setVisibility(View.VISIBLE);
        tvNote.setText(note);
    }

    @Override
    protected void onResume() {
        super.onResume();
        viewModel.getNote();
    }

    @Override
    public Context getContext() {
        return this;
    }
}
