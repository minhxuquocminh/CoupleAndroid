package com.example.couple.ViewModel.SubScreen;

import android.content.Context;

import com.example.couple.Base.Handler.StorageBase;
import com.example.couple.Custom.Enum.StorageType;
import com.example.couple.View.SubScreen.NoteView;

import java.util.HashSet;
import java.util.Set;

public class NoteViewModel {
    NoteView noteView;
    Context context;

    public NoteViewModel(NoteView noteView, Context context) {
        this.noteView = noteView;
        this.context = context;
    }

    public void getNoteList() {
        Set<String> notes = StorageBase.getStringSet(context, StorageType.STRING_OF_NOTES);
        if (notes.isEmpty()) {
            noteView.hideNoteList();
        } else {
            noteView.showNoteList(notes);
        }
    }

    public void deleteNoteList() {
        StorageBase.setStringSet(context, StorageType.STRING_OF_NOTES, new HashSet<>());
        noteView.deleteNoteListSuccess("Xóa Ghi chú thành công!");
    }
}
