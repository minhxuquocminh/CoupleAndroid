package com.example.couple.View.SubScreen;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.couple.Base.View.DialogBase;
import com.example.couple.Custom.Widget.SpeechToTextActivity;
import com.example.couple.R;
import com.example.couple.View.Adapter.NoteAdapter;
import com.example.couple.ViewModel.SubScreen.NoteViewModel;

import java.util.List;

public class NoteActivity extends SpeechToTextActivity implements NoteView {
    TextView tvTitle;
    ImageView imgAdd;
    RecyclerView rvNote;
    ImageView imgDelete;

    NoteViewModel viewModel;
    boolean isChanged = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note);

        tvTitle = findViewById(R.id.tvTitle);
        imgAdd = findViewById(R.id.imgAdd);
        rvNote = findViewById(R.id.rvNote);
        imgDelete = findViewById(R.id.imgDelete);

        viewModel = new NoteViewModel(this, this);
        viewModel.getNoteList();

        imgAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(NoteActivity.this, NoteInfoActivity.class));
            }
        });

        imgDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String message = "Bạn có muốn xóa tất cả các Ghi chú không?";
                DialogBase.showWithConfirmation(NoteActivity.this, "Xóa?", message, () -> {
                    viewModel.deleteNoteList();
                });
            }
        });

    }

    @Override
    public void showMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showNoteList(List<String> notes) {
        tvTitle.setText("Các ghi chú bạn đã lưu:");
        rvNote.setVisibility(View.VISIBLE);
        imgDelete.setVisibility(View.VISIBLE);
        NoteAdapter adapter = new NoteAdapter(this, R.layout.custom_item_rv_note, notes);
        rvNote.removeAllViews();
        rvNote.setLayoutManager(new LinearLayoutManager(this));
        rvNote.setAdapter(adapter);
    }

    @Override
    public void hideNoteList() {
        tvTitle.setText("Không có Ghi chú nào!");
        rvNote.setVisibility(View.GONE);
        imgDelete.setVisibility(View.GONE);
    }

    @Override
    public void deleteNoteListSuccess(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
        hideNoteList();
        isChanged = true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            viewModel.getNoteList();
            isChanged = true;
        }
    }

    @Override
    public Context getContext() {
        return this;
    }
}
