package com.example.couple.View.SubScreen;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.couple.R;
import com.example.couple.View.Adapter.NoteAdapter;
import com.example.couple.ViewModel.SubScreen.NoteViewModel;

import java.util.List;

public class NoteActivity extends AppCompatActivity implements NoteView {
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
        viewModel.GetNoteList();

        imgAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(NoteActivity.this, NoteInfoActivity.class));
            }
        });

        imgDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(NoteActivity.this)
                        .setTitle("Xóa?")
                        .setMessage("Bạn có muốn xóa tất cả các Ghi chú không?")
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                viewModel.DeleteNoteList();
                            }
                        })
                        .setNegativeButton(android.R.string.no, null)
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .show();
            }
        });

    }

    @Override
    public void ShowError(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void ShowNoteList(List<String> notes) {
        tvTitle.setText("Các ghi chú bạn đã lưu:");
        rvNote.setVisibility(View.VISIBLE);
        imgDelete.setVisibility(View.VISIBLE);
        NoteAdapter adapter = new NoteAdapter(this, R.layout.custom_item_rv_note, notes);
        rvNote.removeAllViews();
        rvNote.setLayoutManager(new LinearLayoutManager(this));
        rvNote.setAdapter(adapter);
    }

    @Override
    public void HideNoteList() {
        tvTitle.setText("Không có Ghi chú nào!");
        rvNote.setVisibility(View.GONE);
        imgDelete.setVisibility(View.GONE);
    }

    @Override
    public void DeleteNoteListSuccess(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
        HideNoteList();
        isChanged = true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            viewModel.GetNoteList();
            isChanged = true;
        }
    }

}
