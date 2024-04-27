package com.example.couple.View.SubScreen;

import java.util.List;

public interface NoteView {
    void showMessage(String message);
    void showNoteList(List<String> notes);
    void hideNoteList();
    void deleteNoteListSuccess(String message);
}
