package com.example.couple.ViewModel.SubScreen;

import android.content.Context;

import com.example.couple.Base.Handler.StorageBase;
import com.example.couple.Custom.Enum.StorageType;
import com.example.couple.View.SubScreen.NoteInfoView;

import java.util.Set;

public class NoteInfoViewModel {
    NoteInfoView noteInfoView;
    Context context;

    public NoteInfoViewModel(NoteInfoView noteInfoView, Context context) {
        this.noteInfoView = noteInfoView;
        this.context = context;
    }

    public void addNote(String note) {
        Set<String> notes = StorageBase.getStringSet(context, StorageType.STRING_OF_NOTES);
        notes.add(note);
        StorageBase.setStringSet(context, StorageType.STRING_OF_NOTES, notes);
        noteInfoView.addNoteSuccess("Thêm ghi chú thành công!");
    }
}
