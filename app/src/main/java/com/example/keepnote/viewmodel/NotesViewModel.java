package com.example.keepnote.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import com.example.keepnote.db.AppDatabase;
import com.example.keepnote.db.NoteDao;
import com.example.keepnote.db.NoteEntity;

import java.util.List;

public class NotesViewModel extends AndroidViewModel {
    private NoteDao noteDao;
    private LiveData<List<NoteEntity>> notesLiveData;

    public NotesViewModel(@NonNull Application application) {
        super(application);
        noteDao = AppDatabase.getDatabase(application).noteDao();
        notesLiveData = noteDao.loadAllNotes();
    }
    public LiveData<List<NoteEntity>> getNoteList() {
        return notesLiveData;
    }

    public LiveData<List<NoteEntity>> getNoteListFilter(String query) {
        return  noteDao.loadFilterNotes(query);
    }


    public void insert(NoteEntity... noteEntities) {
        noteDao.insert(noteEntities);
    }

    public void update(NoteEntity note) {
        noteDao.update(note);
    }

    public NoteEntity getNoteById(int noteId){
        NoteEntity noteEntity = null;
        noteEntity = noteDao.loadNoteById(noteId);
        return noteEntity;
    }

    public void deleteNote(NoteEntity noteEntity) {
        noteDao.deleteNote(noteEntity);
    }

    public void deleteAll() {
        noteDao.deleteAll();
    }
}
