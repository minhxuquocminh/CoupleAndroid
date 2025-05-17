package com.example.couple.ViewModel.SubScreen;

import android.content.Context;

import com.example.couple.Base.Handler.StorageBase;
import com.example.couple.Custom.Enum.StorageType;
import com.example.couple.View.SubScreen.EditNoteView;

public class NoteInfoViewModel {
    EditNoteView editNoteView;
    Context context;

    public NoteInfoViewModel(EditNoteView editNoteView, Context context) {
        this.editNoteView = editNoteView;
        this.context = context;
    }

    public void getNote() {
        String note = StorageBase.getString(context, StorageType.STRING_OF_NOTE);
        editNoteView.showNote(note);
    }
    public void updateNote(String note) {
        StorageBase.setString(context, StorageType.STRING_OF_NOTE, note);
        editNoteView.updateNoteSuccess("Cập nhật thành công!");
    }
}
