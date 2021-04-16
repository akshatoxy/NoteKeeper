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
                        .build();
            }
        }
        return instance;
    }
}
