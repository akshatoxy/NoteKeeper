package com.example.notekeeper.database;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.example.notekeeper.model.Note;

@Database(entities = Note.class, version = 1, exportSchema = false)
public abstract class NoteDatabase extends RoomDatabase {

    private static NoteDatabase instance;

    public abstract NoteDao noteDao();

    public static NoteDatabase getInstance(Context context){
        synchronized (NoteDatabase.class){
            if(instance == null){
                instance = Room.databaseBuilder(context.getApplicationContext(),
                        NoteDatabase.class, "note_database")
                        .fallbackToDestructiveMigration()
                        .addCallback(roomCallback) // To populate the database when created
                        .build();
            }
        }
        return instance;
    }


    private static RoomDatabase.Callback roomCallback = new RoomDatabase.Callback(){
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            new PopulateDbTask(instance).execute();
        }
    };

    private static class PopulateDbTask extends AsyncTask<Void, Void, Void>{
        private NoteDao noteDao;

        private PopulateDbTask(NoteDatabase db){
            this.noteDao = db.noteDao();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            noteDao.insert(new Note("Title 1", "Description 1", 0, "12/04/21"));
            noteDao.insert(new Note("Title 2", "Description 2", 1, "13/04/21"));
            noteDao.insert(new Note("Title 3", "Description 3", 2, "14/04/21"));
            noteDao.insert(new Note("Title 4", "Description 4", 3, "15/04/21"));
            noteDao.insert(new Note("Title 5", "Description 5", 4, "16/04/21"));
            return null;
        }
    }

}
