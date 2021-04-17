package com.example.notekeeper.viewmodel;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.notekeeper.model.Note;
import com.example.notekeeper.repository.NoteRepository;

import java.util.List;

public class NoteListViewModel extends AndroidViewModel {

    private NoteRepository noteRepository;
    private LiveData<List<Note>> allNotes;
    private MutableLiveData<Note> deleteNote = new MutableLiveData<Note>();

    public NoteListViewModel(@NonNull Application application) {
        super(application);
        this.noteRepository = new NoteRepository(getApplication());
        this.allNotes = noteRepository.getAllNotes();
        this.deleteNote.setValue(null);
    }

    public void deleteNote(Note note){
        noteRepository.delete(note);
    }

    public MutableLiveData<Note> getDeleteNote() {
        return deleteNote;
    }

    public LiveData<List<Note>> getAllNotes() {
        return allNotes;
    }
}
