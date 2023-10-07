package com.example.couple.ViewModel.SubScreen;

import android.content.Context;

import com.example.couple.Base.Handler.IOFileBase;
import com.example.couple.Custom.Const.FileName;
import com.example.couple.View.SubScreen.NoteInfoView;

public class NoteInfoViewModel {
    NoteInfoView noteInfoView;
    Context context;

    public NoteInfoViewModel(NoteInfoView noteInfoView, Context context) {
        this.noteInfoView = noteInfoView;
        this.context = context;
    }

    public void AddNote(String note) {
        IOFileBase.saveDataToFile(context, FileName.NOTE, note + "===", 1);
        noteInfoView.AddNoteSuccess("Thêm ghi chú thành công!");
    }
}
