package com.example.couple.View.SubScreen;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.couple.R;
import com.example.couple.ViewModel.SubScreen.NoteInfoViewModel;

public class NoteInfoActivity extends AppCompatActivity implements NoteInfoView {
    EditText edtNote;
    Button btnAddNote;
    Button btnCancel;

    NoteInfoViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_info);

        edtNote = findViewById(R.id.edtNote);
        btnAddNote = findViewById(R.id.btnAddNote);
        btnCancel = findViewById(R.id.tvCancel);

        viewModel = new NoteInfoViewModel(this, this);

        btnAddNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String note = edtNote.getText().toString().trim();
                viewModel.addNote(note);
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
    public void addNoteSuccess(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
        setResult(RESULT_OK);
        finish();
    }

    @Override
    public void showMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}
