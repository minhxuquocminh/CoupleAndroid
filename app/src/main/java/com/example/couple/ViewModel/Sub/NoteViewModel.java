package com.example.couple.ViewModel.Sub;

import android.content.Context;

import com.example.couple.Base.Handler.IOFileBase;
import com.example.couple.View.Sub.NoteView;

import java.util.ArrayList;
import java.util.List;

public class NoteViewModel {
    NoteView noteView;
    Context context;

    public NoteViewModel(NoteView noteView, Context context) {
        this.noteView = noteView;
        this.context = context;
    }

    public void GetNoteList() {
        String data = IOFileBase.readDataFromFile(context, "note.txt");
        if (data.equals("")) {
            noteView.HideNoteList();
        } else {
            String arr[] = data.split("===");
            List<String> notes = new ArrayList<>();
            for (int i = 0; i < arr.length; i++) {
                if (!arr[i].trim().equals("")) {
                    notes.add(arr[i].trim());
                }
            }
            noteView.ShowNoteList(notes);
        }
    }

    public void DeleteNoteList() {
        IOFileBase.saveDataToFile(context, "note.txt", "", 0);
        noteView.DeleteNoteListSuccess("Xóa Ghi chú thành công!");
    }
}
