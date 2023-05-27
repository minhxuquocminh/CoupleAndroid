package com.example.couple.View.Sub;

import java.util.List;

public interface NoteView {
    void ShowError(String message);
    void ShowNoteList(List<String> notes);
    void HideNoteList();
    void DeleteNoteListSuccess(String message);
}
