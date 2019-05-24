package com.example.keepnote.db;

import android.arch.lifecycle.MutableLiveData;
import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.TypeConverters;
import android.content.Context;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.util.Log;

@Database(entities = {NoteEntity.class}, version = 1, exportSchema = false)
@TypeConverters(DateConverter.class)
public abstract class AppDatabase extends RoomDatabase {

    public abstract NoteDao noteDao();

    private static AppDatabase sInstance;

    public static final String DATABASE_NAME = "basic-note-db";

    private final MutableLiveData<Boolean> mIsDatabaseCreated = new MutableLiveData<>();

    public static AppDatabase getDatabase(final Context context) {
        if (sInstance == null) {
            synchronized (AppDatabase.class) {
                if (sInstance == null) {
                    sInstance = Room.databaseBuilder(context.getApplicationContext(),
                            AppDatabase.class, DATABASE_NAME)
                            .allowMainThreadQueries() // SHOULD NOT BE USED IN PRODUCTION !!!
                            .addCallback(new RoomDatabase.Callback() {
                                @Override
                                public void onCreate(@NonNull SupportSQLiteDatabase db) {
                                    super.onCreate(db);
                                    Log.d("NotesDatabase", "populating with data...");
                                    new PopulateDbAsync(sInstance).execute();
                                }
                            })
                            .build();
                }
            }
        }
        return sInstance;
    }

    public void clearDb() {
        if (sInstance != null) {
            new PopulateDbAsync(sInstance).execute();
        }
    }

    private static class PopulateDbAsync extends AsyncTask<Void, Void, Void> {
        private final NoteDao noteDao;
        public PopulateDbAsync(AppDatabase instance) {
            noteDao = instance.noteDao();
        }
        @Override
        protected Void doInBackground(Void... voids) {
            noteDao.deleteAll();
            NoteEntity noteOne = new NoteEntity("Adam McKay");
            NoteEntity noteTwo = new NoteEntity("Denis Villeneuve");
            NoteEntity noteThree = new NoteEntity("Morten Tyldum");
            NoteEntity noteFour = new NoteEntity("Arrival");
            NoteEntity note5 = new NoteEntity("Arrival");
            NoteEntity note7 = new NoteEntity("Arrival");
            NoteEntity note8 = new NoteEntity("Arrival");
            NoteEntity note9 = new NoteEntity("Arrival");
            noteDao.insert(noteOne, noteTwo, noteThree, noteFour,note5,note7,note8,note9);
            return null;
        }
    }
}
