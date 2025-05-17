package com.example.couple.View.SubScreen;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.couple.Base.View.ActivityBase;
import com.example.couple.R;
import com.example.couple.ViewModel.SubScreen.NoteInfoViewModel;

public class EditNoteActivity extends ActivityBase implements EditNoteView {
    EditText edtNote;
    Button btnUpdateNote;
    Button btnCancel;

    NoteInfoViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_note);

        edtNote = findViewById(R.id.edtNote);
        btnUpdateNote = findViewById(R.id.btnUpdateNote);
        btnCancel = findViewById(R.id.tvCancel);

        viewModel = new NoteInfoViewModel(this, this);
        viewModel.getNote();

        btnUpdateNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String note = edtNote.getText().toString().trim();
                viewModel.updateNote(note);
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    public void updateNoteSuccess(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
        setResult(RESULT_OK);
        finish();
    }

    @Override
    public void showMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showNote(String note) {
        edtNote.setText(note);
        edtNote.setSelection(note.length());
    }

    @Override
    public Context getContext() {
        return this;
    }
}
