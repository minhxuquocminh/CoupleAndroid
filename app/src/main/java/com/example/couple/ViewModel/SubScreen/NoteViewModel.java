package com.example.couple.ViewModel.SubScreen;

import android.content.Context;

import com.example.couple.Base.Handler.StorageBase;
import com.example.couple.Custom.Enum.StorageType;
import com.example.couple.View.SubScreen.NoteView;

public class NoteViewModel {
    NoteView noteView;
    Context context;

    public NoteViewModel(NoteView noteView, Context context) {
        this.noteView = noteView;
        this.context = context;
    }

    public void getNote() {
        String note = StorageBase.getString(context, StorageType.STRING_OF_NOTE);
        noteView.showNote(note);
    }
}
