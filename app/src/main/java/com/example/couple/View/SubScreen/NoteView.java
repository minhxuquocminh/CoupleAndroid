package com.example.couple.View.SubScreen;

import java.util.List;

public interface NoteView {
    void ShowError(String message);
    void ShowNoteList(List<String> notes);
    void HideNoteList();
    void DeleteNoteListSuccess(String message);
}
