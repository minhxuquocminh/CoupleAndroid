package com.example.couple.View.SubScreen;

import java.util.Set;

public interface NoteView {
    void showMessage(String message);
    void showNoteList(Set<String> notes);
    void hideNoteList();
    void deleteNoteListSuccess(String message);
}
