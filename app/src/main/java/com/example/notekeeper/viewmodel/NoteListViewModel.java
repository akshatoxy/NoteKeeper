package com.example.notekeeper.viewmodel;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.notekeeper.model.Note;
import com.example.notekeeper.repository.NoteRepository;

import java.util.List;

public class NoteListViewModel extends AndroidViewModel {

    private NoteRepository noteRepository;
    private LiveData<List<Note>> allNotes;

    public NoteListViewModel(@NonNull Application application) {
        super(application);
        initialize();
    }

    private void initialize(){
        noteRepository = new NoteRepository(getApplication());
        allNotes = noteRepository.getAllNotes();
    }

    public LiveData<List<Note>> getAllNotes() {
        return allNotes;
    }
}
