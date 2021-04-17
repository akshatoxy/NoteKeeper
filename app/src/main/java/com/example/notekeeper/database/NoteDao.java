package com.example.notekeeper.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.notekeeper.model.Note;

import java.util.List;

@Dao
public interface NoteDao {

    @Insert
    void insert(Note note);

    @Update
    void update(Note note);

    @Delete
    void delete(Note note);

    @Query("DELETE FROM note_table")
    void deleteAllNotes();

    @Query("SELECT * FROM note_table WHERE id = :id")
    LiveData<Note> getNote(int id);

    @Query("SELECT * FROM note_table ORDER BY id DESC")
    LiveData<List<Note>> getAllNotesOrderByID();

    @Query("SELECT * FROM note_table ORDER BY priority")
    LiveData<List<Note>> getAllNotesOrderByPriority();
}
