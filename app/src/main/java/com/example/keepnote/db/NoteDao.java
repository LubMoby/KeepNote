package com.example.keepnote.db;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.TypeConverters;
import android.arch.persistence.room.Update;

import java.util.List;

import static android.arch.persistence.room.OnConflictStrategy.REPLACE;

@Dao
@TypeConverters(DateConverter.class)
public interface NoteDao {

    @Query("SELECT * FROM notes")
    LiveData<List<NoteEntity>> loadAllNotes();

    @Query("SELECT * FROM notes WHERE note_text LIKE :query ORDER BY note_text ASC")
    LiveData<List<NoteEntity>> loadFilterNotes(String query);

    @Query("SELECT * FROM notes")
    List<NoteEntity> findAllNotesSync();

    @Query("select * from notes where idNote = :notesId")
    LiveData<NoteEntity> loadNote(int notesId);

    @Query("select * from notes where idNote = :id")
    NoteEntity loadNoteById(int id);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<NoteEntity> notes);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertNote(NoteEntity noteEntity);

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(NoteEntity... noteEntity);

    @Update(onConflict = REPLACE)
    void updateNote(NoteEntity noteEntity);

    @Update(onConflict = OnConflictStrategy.IGNORE)
    void update(NoteEntity noteEntity);

    @Delete
    void deleteNote(NoteEntity noteEntity);

    @Query("DELETE FROM notes")
    void deleteAll();
}
