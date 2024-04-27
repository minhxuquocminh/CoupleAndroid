package com.example.couple.ViewModel.SubScreen;

import android.content.Context;

import com.example.couple.Base.Handler.IOFileBase;
import com.example.couple.Custom.Const.FileName;
import com.example.couple.View.SubScreen.NoteView;

import java.util.ArrayList;
import java.util.List;

public class NoteViewModel {
    NoteView noteView;
    Context context;

    public NoteViewModel(NoteView noteView, Context context) {
        this.noteView = noteView;
        this.context = context;
    }

    public void getNoteList() {
        String data = IOFileBase.readDataFromFile(context, FileName.NOTE);
        if (data.isEmpty()) {
            noteView.hideNoteList();
        } else {
            String[] arr = data.split("===");
            List<String> notes = new ArrayList<>();
            for (String note : arr) {
                if (!note.trim().isEmpty()) {
                    notes.add(note.trim());
                }
            }
            noteView.showNoteList(notes);
        }
    }

    public void deleteNoteList() {
        IOFileBase.saveDataToFile(context, FileName.NOTE, "", 0);
        noteView.deleteNoteListSuccess("Xóa Ghi chú thành công!");
    }
}
