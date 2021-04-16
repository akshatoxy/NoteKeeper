package com.example.notekeeper.viewmodel;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.notekeeper.model.Note;
import com.example.notekeeper.repository.NoteRepository;

public class NoteViewModel extends AndroidViewModel {
    private LiveData<Note> note;
    private NoteRepository noteRepository;

    public NoteViewModel(@NonNull Application application, int pos) {
        super(application);
        noteRepository = new NoteRepository(getApplication());
        note =  noteRepository.getNoteById(pos);
    }

    public LiveData<Note> getNote() {
        return note;
    }
}
