package com.example.keepnote.db;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

@Entity(tableName = "notes",
        indices = {@Index(value = "note_text", unique = false)})
public class NoteEntity {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "idNote")
    public int id;

    @ColumnInfo(name = "note_text")
    @NonNull
    public String noteText;

    public NoteEntity(@NonNull String noteText) {
        this.noteText = noteText;
    }
}