package com.example.notekeeper.repository;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.example.notekeeper.database.NoteDao;
import com.example.notekeeper.database.NoteDatabase;
import com.example.notekeeper.model.Note;

import java.util.List;

public class NoteRepository {
    private NoteDao noteDao;
    private LiveData<List<Note>> allNotes;

    public NoteRepository(Application application) {
        NoteDatabase noteDatabase = NoteDatabase.getInstance(application);
        this.noteDao = noteDatabase.noteDao();
        this.allNotes = noteDao.getAllNotes();
    }

    public void insert(Note note){
        new InsertNoteTask(noteDao).execute(note);
    }

    public void update(Note note){
        new UpdateNoteTask(noteDao).execute(note);
    }

    public void delete(Note note){
        new DeleteNoteTask(noteDao).execute(note);
    }

    public void deleteAll(){
        new DeleteAllNotesTask(noteDao).execute();
    }

    public LiveData<Note> getNoteById(int noteId) {
        return noteDao.getNote(noteId);
    }

    public LiveData<List<Note>> getAllNotes() {
        return allNotes;
    }

    private static class InsertNoteTask extends AsyncTask<Note, Void, Void>{
        private NoteDao noteDao;

        private InsertNoteTask(NoteDao noteDao){
            this.noteDao = noteDao;
        }

        @Override
        protected Void doInBackground(Note... notes) {
            noteDao.insert(notes[0]);
            return null;
        }
    }

    private static class UpdateNoteTask extends AsyncTask<Note, Void, Void>{
        private NoteDao noteDao;

        private UpdateNoteTask(NoteDao noteDao){
            this.noteDao = noteDao;
        }

        @Override
        protected Void doInBackground(Note... notes) {
            noteDao.update(notes[0]);
            return null;
        }
    }

    private static class DeleteNoteTask extends AsyncTask<Note, Void, Void>{
        private NoteDao noteDao;

        private DeleteNoteTask(NoteDao noteDao){
            this.noteDao = noteDao;
        }

        @Override
        protected Void doInBackground(Note... notes) {
            noteDao.delete(notes[0]);
            return null;
        }
    }

    private static class DeleteAllNotesTask extends AsyncTask<Void, Void, Void>{
        private NoteDao noteDao;

        private DeleteAllNotesTask(NoteDao noteDao){
            this.noteDao = noteDao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            noteDao.deleteAllNotes();
            return null;
        }
    }
}
